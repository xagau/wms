/**
 * Copyright (c) 2023 AGUA.SAT, Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ai.genpen;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.primefaces.diamond.Property;
import org.primefaces.diamond.shared.Request;
import org.primefaces.diamond.shared.Response;
import org.primefaces.diamond.tasks.GPAIProducer;
import org.primefaces.diamond.util.Globals;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Brain {

    private String email;
    private String sessionId;
    ConnectionFactory factory = null; //new ConnectionFactory();
    Connection connection = null; //factory.newConnection();
    Channel channel = null; //connection.createChannel();

    public Brain() {

        try {
            factory = new ConnectionFactory();
            factory.setUri(Property.getProperty("cloudamqp_url"));
            factory.setConnectionTimeout(Globals.timeout);
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch(Exception ex) {
            for(int i =0; i < 1000; i++ ) {
                System.out.println("BRAIN DEAD");
            }
            ex.printStackTrace();
        }

    }



    public Response analyse(Request request) {
        Response response = new Response();
        request.setResponseUUID(response.getUuid());
        System.out.println("analyse:" + request.getRequest());
        String prompt = request.getRequest();
        request.setRequest(prompt);
        String res = evaluate(prompt, request.getRequestor());
        double score = score(prompt, res);
        boolean good = good(prompt, res);
        boolean bad = bad(prompt, res);

        response.setScore(score);
        response.setResponse(res);
        return response;
    }

    private boolean good(String prompt, String res) {
        if (score(prompt, res) > 0.5) {
            return true;
        }
        return false;
    }

    private boolean bad(String prompt, String res) {
        if (score(prompt, res) <= 0.5) {
            return true;
        }
        return false;
    }

    public double score(String prompt, String res) {
        if (prompt == null || prompt.isEmpty() || res == null || res.isEmpty()) {
            return 0.5;
        }
        return 0.95;
    }

    public String evaluate(String prompt, String requester) {

        try {
            System.out.println("evaluate(String prompt, String requester) called");
            GPAIProducer producer = new GPAIProducer();
            System.out.println("sending prompt:" + prompt);
            producer.produce(prompt, requester);
            System.out.println("waiting for a reply");

            boolean flag = true;
            String res = "nack!";

            connection = factory.newConnection();
            channel = connection.createChannel();
            do {
                GetResponse gr = null;
                try {

                    channel = connection.createChannel();
                    channel.queueDeclare("responses_" + email, false, false, false, null);

                    gr = channel.basicGet("responses_" + requester, true);
                } catch(Exception ex) {

                }
                if( gr != null ) {
                    res = new String(gr.getBody(), "UTF8");
                    long tag = gr.getEnvelope().getDeliveryTag();
                    channel.basicAck(tag, false);
                    System.out.println("Got message:" + res + " for " + requester);
                    flag = false;
                }
                Thread.sleep(300);
                Thread.yield();
            } while (flag);
            System.out.println("returning " + res + " from evaluate outside do-while loop " + email);

            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "that ack!";
    }

    public String request(String requester) {

        try {

            boolean flag = true;
            String res = "nack!";

            connection = factory.newConnection();
            channel = connection.createChannel();
            do {
                GetResponse gr = channel.basicGet("responses_" + requester, true);
                if( gr != null ) {
                    res = new String(gr.getBody(), "UTF8");
                    long tag = gr.getEnvelope().getDeliveryTag();
                    channel.basicAck(tag, false);
                    System.out.println("Got message:" + res + " for " + requester);
                    flag = false;
                }
                Thread.sleep(300);
                Thread.yield();
            } while (flag);
            System.out.println("returning " + res + " from evaluate outside do-while loop " + email);

            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return "this ack!";
    }

    public long messageCount(String requester) {
        try {
            String QUEUE_NAME = "responses";

            connection = factory.newConnection();
            System.out.println("Waiting for a message in QUEUE:" + QUEUE_NAME + "_" + requester);
            channel = connection.createChannel();
            long count = channel.messageCount(QUEUE_NAME + "_" + requester) ;
            System.out.println("Found " + count + " message in QUEUE:" + QUEUE_NAME + "_" + requester);
            channel.close();
            connection.close();
            return count;

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

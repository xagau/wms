package org.primefaces.diamond.tasks;

import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.diamond.Property;
import org.primefaces.diamond.util.Globals;
import org.primefaces.diamond.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GPAIProducer {



    public GPAIProducer(String QUEUE_NAME, String email) {
        this.QUEUE_NAME = QUEUE_NAME;
        this.email = email;
    }

    public GPAIProducer() {
    }

    private String QUEUE_NAME = "requests";
    private String email = "";


    static HashMap<String, String> hm = new HashMap<>();
    public void produce(String request, String email)  {
        try {

            // Set up the connection factory
            if (request == null || request.isEmpty() || email == null || email.isEmpty()) {
                System.out.println("Request or email is empty or null");
                return;
            }
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(Property.getProperty("cloudamqp_url"));
            factory.setConnectionTimeout(Globals.timeout);
            factory.setAutomaticRecoveryEnabled(true);
            // Create a new connection and channel
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // Declare the queue
            channel.queueDeclare(QUEUE_NAME+ "_" + email, false, false, false, null);

            channel.basicPublish("", QUEUE_NAME+ "_" + email, null, request.getBytes());
            System.out.println("Sent message: " + request + " to " + QUEUE_NAME + "_" + email);

            Thread.yield();
            Thread.sleep(300);

            // Close the channel and connection
            channel.close();
            connection.close();
        } catch(Exception ex) {ex.printStackTrace();}
    }
    public static void main(String[] args){
        GPAIProducer producer = new GPAIProducer("requests", "seanbeecroft@gmail.com");
        producer.produce("hello this is a test", "seanbeecroft@gmail.com");
        //GPAIConsumer consumer = new GPAIConsumer("responses");
    }

}

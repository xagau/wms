package org.primefaces.diamond.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.URI;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.diamond.Server;
import org.primefaces.diamond.domain.NotificationType;
import org.primefaces.diamond.util.Utility;

public class MessageService {

    // Find your Account Sid and Token at twilio.com/console 
    private static final String ACCOUNT_SID = Server.getProperty("ACCOUNT_SID");
    private static final String AUTH_TOKEN = Server.getProperty("AUTH_TOKEN"); 
    private static final String MG = Server.getProperty("MG"); 
    private static final String SEND_GRID_KEY = Server.getProperty("SEND_GRID_KEY"); 

    public static void test() {
        try {
            String code = Utility.randomNumber();
            String message = "your code is " + code;
            String phone = "+351914965068";
            send(message, phone);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testPassword(String password) {
        try {
            String msg = "your reset password is " + password;
            String phone = "+351914965068";
            send(msg, phone);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void send(String msg, String phone) {
        try {
            boolean disable = true;
            if( disable ){
                return;
            }

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(new com.twilio.type.PhoneNumber(phone), MG, msg).create();

            System.out.println(message.getSid());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //
    public static void send(final String subject, final String message, final String phone, final String email, final NotificationType type) {
        try {
            boolean disable = true;
            if( disable ){
                return;
            }
            Thread t = new Thread() {
                public void run() {
                    if (type.equals(NotificationType.SMS) || type.equals(NotificationType.ALL)) {
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(new FileOutputStream(new File("./send-log.txt")), true);
                            String _msg = new Date(System.currentTimeMillis()).toString() + " " + message + " " + phone + " " + email;
                            writer.write(_msg);
                            writer.flush();
                            writer.close();
                            System.out.println(_msg);
                            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                            Message msg = Message.creator(new com.twilio.type.PhoneNumber(phone), MG, message).create();
                            System.out.println(msg.getSid());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            writer.close();
                        }
                    }

                    if (type.equals(NotificationType.EMAIL) || type.equals(NotificationType.ALL)) {
                         PrintWriter writer = null;
                        
                        try {
                            writer = new PrintWriter(new FileOutputStream(new File("./send-log.txt")), true);
                            String _msg = new Date(System.currentTimeMillis()).toString() + " " + message + " " + phone + " " + email;
                            writer.write(_msg);
                            writer.flush();
                            writer.close();
                            System.out.println(_msg);
                            Email from = new Email("no-reply@espacofacil.pt");
                            Email to = new Email(email);
                            Content content = new Content("text/plain", message);
                            Mail mail = new Mail(from, subject, to, content);

                            SendGrid sg = new SendGrid(SEND_GRID_KEY);
                            Request request = new Request();
                            try {
                                request.setMethod(Method.POST);
                                request.setEndpoint("mail/send");
                                request.setBody(mail.build());
                                Response response = sg.api(request);
                                System.out.println(response.getStatusCode());
                                System.out.println(response.getBody());
                                System.out.println(response.getHeaders());
                            } catch (IOException ex) {
                                throw ex;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }; t.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        boolean disable = true;
        if( disable ){
            return;
        }
        send( "Message from AGUA.SAT", "This is a test message arriving from AGUA.SAT", null, "seanbeecroft@gmail.com", NotificationType.EMAIL);
    }

}

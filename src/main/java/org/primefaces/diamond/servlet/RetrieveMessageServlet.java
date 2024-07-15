package org.primefaces.diamond.servlet;

import ai.genpen.Brain;
import ai.genpen.GPAI;
import org.primefaces.diamond.util.Utility;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "RetrieveMessageServlet", value = "/RetrieveMessageServlet")
public class RetrieveMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    public void process(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            final String u = request.getParameter("email");
            String email = (String) request.getSession().getAttribute("userid");
            if (!Utility.isValidEmail(u)) {
                System.out.println("u:" + u + " is not a valid to email");
                return;
            }
            if (!Utility.isValidEmail(email)) {
                System.out.println("email:" + u + " is not a valid to email");
                return;
            }
            if (!u.equalsIgnoreCase(email)) {
                System.out.println("u:" + u + " and email " + email + " do not match");
                return;
            }
            Brain brain = new Brain();
            final String res = brain.request(email.toLowerCase()).trim();

            PrintWriter writer = response.getWriter();
            //gpai.setEmail(email);


            //System.out.println("In SandboxServlet:" + req + " request sent out for " + email);

            String spinner = "<div class=\"card\" style=\"width:100%; background-color: #dee2e6;\"><p> processing request </p></div>\n";
            writer.write(spinner);
            writer.flush();

            SwingUtilities.invokeAndWait(new Runnable() {
                int height = 98;
                @Override
                public void run() {

                    String em = email;
                    em = em.toLowerCase();



                    //String res = gpai.request(req, em).getResponse();
                    boolean flag = true;
                    //do {
                    System.out.println("Sandbox response:" + res + " received for: " + em);
                    String nres = res.trim();

                    if (Utility.isComputerCode(res)) {
                        System.out.println("It is computer code most likely");
                        int lines = res.split("\n").length;
                        int fontSize = 14;
                        height = fontSize * lines; //
                        nres = "<br><textarea class=\"ui-inputfield ui-input textarea ui-widget ui-state-default ui-corner-all line-numbered ui-input textarea-resizable\" style=\"width:100%; height:" + height + "px;\" readonly>\n" + res + "</textarea>";
                    }

                    if (!Utility.isComputerCode(nres)) {
                        nres = res.replaceAll("\n", "<br/>");
                    }
                    String message = "<div id=\"op" + System.nanoTime() + 1 + "\" class=\"card\" style=\"width:100%; background-color: #cae6fc;\"><p> On " + new Date(System.currentTimeMillis()).toString() + " <b>" + em + "</b> requested: [lost in queue]... </p></div>\n";
                    message += "<div id=\"op" + (System.nanoTime() + 2) + "\" class=\"card\" style=\"width:100%; height:\"" + height + "\"px; background-color: #dee2e6;\"><p> On " + new Date(System.currentTimeMillis()).toString() + " <b>AGUA.SAT</b> responded: " + nres + "</p></div>\n";
                    writer.write(message);
                    writer.flush();

                    //} while(flag);
                    writer.close();

                }
            });

        } catch(Exception ex) {
            try {
                ex.printStackTrace();
                response.getWriter().write("Oops something went wrong...\n");
                response.getWriter().flush();
                response.getWriter().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            process(request,response);
    }


}

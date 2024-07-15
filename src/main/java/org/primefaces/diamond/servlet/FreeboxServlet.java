package org.primefaces.diamond.servlet;

import ai.genpen.GPAI;
import org.primefaces.diamond.shared.Response;
import org.primefaces.diamond.util.Globals;
import org.primefaces.diamond.util.Utility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "FreeboxServlet", value = "/FreeboxServlet")
public class FreeboxServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            String sid = request.getSession().getId();
            String email = (String) request.getSession().getAttribute("userid");
            if( email == null || email.isEmpty() || !Utility.isValidEmail(email) ){
                email = "anon-" + sid.substring(0,8) + "@genpen.ai";
            }

            //String sid = request.getSession().getId();
            Globals.um.put(sid, email);
            PrintWriter writer = response.getWriter();
            String req = request.getParameter("prompt");
            GPAI gpai = new GPAI();
            gpai.setSessionId(sid);
            gpai.setEmail(email);

            System.out.println("In FreeboxServlet:" + req + " request sent out for " + email);

            String spinner = "<div class=\"card\" style=\"width:100%; background-color: #dee2e6;\"><p> processing request </p></div>\n";
            writer.write(spinner);
            writer.flush();

            String finalEmail = email;
            SwingUtilities.invokeAndWait(new Runnable() {
                int height = 98;

                @Override
                public void run() {

                    String em = finalEmail;
                    em = em.toLowerCase();

                    Response resp = gpai.request(req, em);
                    String res = resp.getResponse();

                    System.out.println("FreeboxServlet response:" + res + " received for: " + em);
                    res = res.trim();

                    if (Utility.isComputerCode(res)) {
                        System.out.println("It is computer code most likely");
                        int lines = res.split("\n").length;
                        int fontSize = 14;
                        height = fontSize * lines;
                        res = "<br/><textarea class=\"ui-inputfield ui-input textarea ui-widget ui-state-default ui-corner-all line-numbered ui-input textarea-resizable\" style=\"background-color: #00212C; color:#fff; width:100%; height:" + height + "px;\" readonly>\n" + res + "</textarea>";
                    }

                    String _prompt = req;
                    _prompt = _prompt.replaceAll("\n", "<br/>");
                    if (!Utility.isComputerCode(res)) {
                        res = res.replaceAll("\n", "<br/>");
                    }
                    String message = "<div id=\"op" + System.nanoTime() + 1 + "\" class=\"card\" style=\"width:100%; background-color: #cae6fc;\"><p> On " + new Date(System.currentTimeMillis()).toString() + " <b>" + em + "</b> requested: </p><br/><p>" + _prompt + "</p></div>\n";
                    message += "<div id=\"op" + (System.nanoTime() + 2) + "\" class=\"card\" style=\"width:100%; height:\"" + height + "\"px; background-color: #dee2e6;\"><p> On " + new Date(System.currentTimeMillis()).toString() + " <b>AGUA.SAT</b> responded: </p><br/><p>" + res + "</p></div>\n";
                    writer.write(message);
                    writer.flush();

                    writer.close();

                }
            });

        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                response.getWriter().write("Oops something went wrong...\n");
                response.getWriter().flush();
                response.getWriter().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                response.getWriter().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

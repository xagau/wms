package org.primefaces.diamond.servlet;

import ai.genpen.Brain;
import ai.genpen.GPAI;
import com.google.gson.Gson;
import org.primefaces.component.api.UITable;
import org.primefaces.diamond.util.Utility;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PollMessageServlet", value = "/PollMessageServlet")
public class PollMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    public void process(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("plain/text");
        try {
            final String u = request.getParameter("email");
            final String email = (String)request.getSession().getAttribute("userid");
            if( !Utility.isValidEmail(u)){
                System.out.println("u:" + u + " is not a valid to email");
                return;
            }
            if(!Utility.isValidEmail(email)){
                System.out.println("email:" + u + " is not a valid to email");
                return;
            }
            if( !u.equalsIgnoreCase(email)){
                System.out.println("u:" + u + " and email " + email + " do not match");
                return;
            }
            Brain gpai = new Brain();
            long mc = gpai.messageCount(email);
            PrintWriter writer = response.getWriter();
            writer.write("{\"message_count\":\"" + mc + "\"}");
            writer.flush();
            writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);

    }
}

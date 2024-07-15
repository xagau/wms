package org.primefaces.diamond.servlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.SecurityEventDAO;
import org.primefaces.diamond.dao.UserDAO;
import org.primefaces.diamond.domain.NotificationType;
import org.primefaces.diamond.domain.SecurityAction;
import org.primefaces.diamond.domain.SecurityEvent;
import org.primefaces.diamond.domain.User;
import org.primefaces.diamond.service.MessageService;
import org.primefaces.diamond.util.Utility;

/**
 *
 * @author Sean Beecrot <sean@genpen.ai> <seanbeecroft@gmail.com> 1-416-878-5282 
 */
public class ChangePasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = DatabaseManager.getConnection();
        //response.setContentType("text/html;charset=UTF-8");
        try  {
            PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            String userid = (String)request.getSession().getAttribute("userid");
            String newPassword = request.getParameter("new_password");
            String confirmPassword = request.getParameter("confirm_password");
            

            UserDAO dao = new UserDAO(connection);
            try {
                User u = dao.find(userid);
                if( u != null ) {
                    
                    // Refactor for minimum password requirements.
                    if( newPassword != null && !newPassword.isEmpty() && newPassword.equals(confirmPassword) && !Utility.hashString(newPassword).equals(u.getPassword()) ){
                        if(!Utility.isValidPassword(newPassword)){
                            String pr = "<ul>\n" +
                                         "            <li>Password not changed successfully.</li>\n" +
                                         "            <li>Must be more than 8 and less than 21 alphanumeric upper and lower characters.</li>\n" +
                                         "            <li>Must not contain any special characters or whitespace.</li>\n" +
                                         "            <li>Should not be similar or a repeat of your old password.</li>\n" +
                                         "</ul>\n";
                            request.setAttribute("error", pr);
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.xhtml");
                            dispatcher.forward(request, response);
                        }
                        u.setPassword(Utility.hashString(newPassword));
                        dao.update(u);
                        SecurityEventDAO securityDAO = new SecurityEventDAO(connection);
                        String ip = Utility.getIp(request);
                        String country = Utility.getCountry(request);
                        String device = Utility.getDevice(request);

                        Timestamp ts = new Timestamp(System.currentTimeMillis());
                        SecurityEvent sec = new SecurityEvent(userid, ip, country, SecurityAction.CHANGE_PASSWORD.name(), ts, device);
                        securityDAO.insert(sec);

                        try {
                            String message = "Your password has been changed.\n\nIf you did not initiate this request contact security@espacofacil.pt immediately to freeze your account from unauthorized access.\n";
                            MessageService.send("Forgot Password Request from AGUA.SAT", message, null, userid, NotificationType.EMAIL);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        request.setAttribute("message", "Password changed successfully");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.xhtml");
                        dispatcher.forward(request, response);      
                        


                    } else {
                        request.setAttribute("error", "Password was not able to be changed successfully. Either the password is too short, too insecure or the same as the last password.");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.xhtml");
                        dispatcher.forward(request, response);      
                    }
                    
                }
            } catch (SQLException ex) {
                request.setAttribute("error", "Password was not able to be changed successfully because the user record was expired or not found.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.xhtml");
                dispatcher.forward(request, response);      
                Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Utility.closeQuietly(connection, null, null);
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

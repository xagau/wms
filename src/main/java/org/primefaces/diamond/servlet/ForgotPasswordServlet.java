package org.primefaces.diamond.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.diamond.controllers.UserController;
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
public class ForgotPasswordServlet extends HttpServlet {

    public String resetPassword(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        Connection connection = DatabaseManager.getConnection();

        try {
             String challenge = request.getParameter("challenge");
            if( challenge == null || challenge.isEmpty() || !challenge.endsWith("EE")){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error403.xhtml");
                dispatcher.forward(request, response);  
                return "failure";
            }
            
            UserDAO dao = new UserDAO(connection);
            if( email == null ) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error403.xhtml");
                dispatcher.forward(request, response);
            }
            email = email.toLowerCase();
            User u = dao.findByEmail(email);
            if (u != null) {
                String np = Utility.randomPassword();
                u.setPassword(Utility.hashString(np));
                dao.update(u);
                try {
                    String message = "You have made a request to change your password.\n\nYour new password is:" + np + "\n\nPlease log in and change your password as soon as possible. If you did not initiate this request contact security@genpen.ai immediately to freeze your account from unauthorized access.\n";
                    MessageService.send("Forgot Password Request from AGUA.SAT", message, null, email, NotificationType.EMAIL);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                SecurityEventDAO securityDAO = new SecurityEventDAO(connection);
                String ip = Utility.getIp(request);
                String country = Utility.getCountry(request);
                String device = Utility.getDevice(request);

                Timestamp ts = new Timestamp(System.currentTimeMillis());
                SecurityEvent sec = new SecurityEvent(email, ip, country, SecurityAction.FORGOT_PASSWORD.name(), ts, device);
                securityDAO.insert(sec);
                request.setAttribute("message", "A new password has been sent to the email on file: " + Utility.redact(email));
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forgot_password.xhtml");
                try {
                    dispatcher.forward(request, response);
                } catch (ServletException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                return "success";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (email == null || email.isEmpty() || !Utility.isValidEmail(email)) {
                request.setAttribute("error", "Unable to reset password for the email provided");
            } else {
                request.setAttribute("error", "Unable to reset password for the email " + Utility.redact(email));
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forgot_password.xhtml");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException exx) {
                exx.printStackTrace();
            } catch (IOException exx) {
                exx.printStackTrace();
            } catch (Exception exx) {
                exx.printStackTrace();
            }
        } finally {
            Utility.closeQuietly(connection, null, null);
        }
        return "failure";
    }

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
        //response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            resetPassword(request, response);
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

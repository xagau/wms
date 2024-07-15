/**
 * Copyright (c) 2023 AGUA.SAT, Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
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
import org.primefaces.diamond.controllers.SignupController;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.DeploymentDAO;
import org.primefaces.diamond.dao.SecurityEventDAO;
import org.primefaces.diamond.dao.UsageDAO;
import org.primefaces.diamond.dao.UserDAO;
import org.primefaces.diamond.domain.Deployment;
import org.primefaces.diamond.domain.NotificationType;
import org.primefaces.diamond.domain.SecurityAction;
import org.primefaces.diamond.domain.SecurityEvent;
import org.primefaces.diamond.domain.Usage;
import org.primefaces.diamond.domain.User;
import org.primefaces.diamond.service.MessageService;
import org.primefaces.diamond.util.Utility;

public class SignupServlet extends HttpServlet {

    //validate signup
    public String enroll(HttpServletRequest request, HttpServletResponse response) {
            Connection connection = DatabaseManager.getConnection();

            try {
                String email = request.getParameter("email");
                if (email == null) {
                    email = "null@genpen.ai";
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error403.xhtml");
                    dispatcher.forward(request, response);
                    return "failure";
                }
                String challenge = request.getParameter("challenge");
                if( challenge == null || challenge.isEmpty() || !challenge.endsWith("EE")){
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error403.xhtml");
                    dispatcher.forward(request, response);  
                    return "failure";
                }
                email = email.toLowerCase();
                    
                UserDAO userDAO = new UserDAO(connection);
                try {
                    User u = userDAO.find(email);
                    if (u != null) {
                        try {
                            request.setAttribute("error", 
                                            "This email is already in use. Please enter a new email.");
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signup.xhtml");
                            dispatcher.forward(request, response);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.xhtml");
                            dispatcher.forward(request, response);
                            return "failure";
                        }
                    } else {

                        request.getSession(true).setAttribute("userid", email);
                        //TODO: Update/remove this date once sandboxes are installed automatically.
                        String password = Utility.randomPassword();
                        String message
                                = "Thank you for joining the AGUA.SAT community.\n"
                                + "Your account can be accessed by logging in with the following information:\n\n"
                                + "username: " + email + "\n"
                                + "password: " + password + "\n\n"
                                + "Please login at https://www.genpen.ai/login.xhtml \n\n"
                                + "Your account will remain unverified until you log in.\n"
                                + "Unverified accounts will be removed after 30 days.\n"
                                + "Please change your password as soon as you log in.\n"
                                + "\n\nBy logging in you agree to our terms of service. \n"
                                + "\n\nWe have added 100 FREE compute credits (INK) to your account.\n"
                                + "You are now in a queue to have your sandbox activated.\n "
                                + "Upon activation you will be notified via email.\n "
                                + "Logging in will validate and verify your email and activate your account. \n"
                                + "If you did not initiate this sign-up request please contact us at security@genpen.ai. \n";

                        User u1 = new User();
                        u1.setEmail(email);
                        u1.setUsername(email);
                        u1.setPassword(Utility.hashString(password));
                        u1.setUserStatus(User.USER_STATUS_EMAIL_UNVERIFIED);
                        userDAO.insert(u1);

                        try {
                            UsageDAO ud = new UsageDAO(connection);
                            if( ud.findByAccount(email) == null ){
                                Usage usage = new Usage();
                                usage.setAccount(email);
                                usage.setComputeCredits(100);
                                ud.insert(usage);
                                connection = DatabaseManager.getConnection();
                                DeploymentDAO ddao = new DeploymentDAO(connection);
                                Deployment deployment = new Deployment();
                                deployment.setCategory("Sandbox-Account");
                                deployment.setOwner(email);
                                String ep = "192.119.80.171";
                                deployment.setEndPoint(ep);
                                deployment.setName("Development Sandbox");
                                deployment.setQuantity(1);
                                deployment.setDescription("This is a general purpose endpoint for development");
                                deployment.setImage("deployment-image-docker-sandbox");
                                deployment.setSha("00000000000000000000000000000000000000000000000000000000000");                                
                                ddao.insert(deployment);
                            }
                        } catch(Exception ex) {}
                        
                        try {
                            MessageService.send("Welcome to AGUA.SAT", message, null, email.toLowerCase(), NotificationType.EMAIL);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        try {
                            SecurityEventDAO securityDAO = new SecurityEventDAO(connection);
                            String ip = Utility.getIp(request); //request.getRemoteHost().toString();
                            String country = Utility.getCountry(request);
                            String device = Utility.getDevice(request);

                            Timestamp ts = new Timestamp(System.currentTimeMillis());
                            SecurityEvent sec = new SecurityEvent(email, ip, country, SecurityAction.SIGNUP.name(), ts, device);
                            securityDAO.insert(sec);
                        } catch(Exception ex) { 
                            ex.printStackTrace();
                        }
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/verification.xhtml");
                        dispatcher.forward(request, response);
                        return "success";
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    request.setAttribute("error",  "An error occured while trying to log you in.");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signup.xhtml");
                    dispatcher.forward(request, response);
                    return "failure";
                }
            } catch (Exception ex) {
                try {
                    ex.printStackTrace();
                    request.setAttribute("error",  "An error occured while trying to log you in. Please try again or contact");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signup.xhtml");
                    dispatcher.forward(request, response);
                    return "failure";
                } catch (ServletException ex1) {
                    ex1.printStackTrace();
                } catch (IOException ex1) {
                    Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex1);
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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            enroll(request, response);
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

package org.primefaces.diamond.servlet;

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

public class LoginServlet extends HttpServlet {

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
        Connection connection = DatabaseManager.getConnection();

        try  {
            PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            //FacesContext context = FacesContext.getCurrentInstance();
            String username = request.getParameter("email");
            String password = request.getParameter("password");
            if(!Utility.credentialCheck(username, password)){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error403.xhtml");
                dispatcher.forward(request, response);
                return;
            }

            String challenge = request.getParameter("challenge");
            if( challenge == null || challenge.isEmpty() || !challenge.endsWith("EE")){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error403.xhtml");
                dispatcher.forward(request, response);  
                return;
            }
            
            UserDAO userDAO = new UserDAO(connection);
            try {

                String email = username.toLowerCase();
                User u = userDAO.findByEmail(email);

                if (u ==null || !u.getPassword().equals(Utility.hashString(password))) {
                    request.setAttribute("error", "Invalid username or password");
                    SecurityEventDAO securityDAO = new SecurityEventDAO(connection);
                    String ip = Utility.getIp(request);
                    String country = Utility.getCountry(request);
                    String device = Utility.getDevice(request);
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    SecurityEvent sec = new SecurityEvent(username, ip, country, SecurityAction.LOGIN_FAILURE.name(), ts, device);
                    securityDAO.insert(sec);
                    request.getSession(true).setAttribute("userid", "");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.xhtml");
                    dispatcher.forward(request, response);
                    return;    
                    
                } else {
                    try {
                        u.setUserStatus(User.USER_STATUS_EMAIL_VERIFIED);
                        userDAO.update(u);
                        SecurityEventDAO securityDAO = new SecurityEventDAO(connection);
                        String ip = Utility.getIp(request);
                        String country = Utility.getCountry(request);
                        String device = Utility.getDevice(request);

                        Timestamp ts = new Timestamp(System.currentTimeMillis());
                        SecurityEvent sec = new SecurityEvent(username, ip, country, SecurityAction.LOGIN_SUCCESS.name(), ts, device);
                        securityDAO.insert(sec);
                        try {
                            String message = "A device has just logged into your account.\n\nThe device is:" + device + "\nCountry:" + country + "\nIP Address:" + ip + "\nIf you did not initiate this login contact security@genpen.ai immediately to freeze your account from unauthorized access.\n";
                            MessageService.send("Login Successful @ AGUA.SAT", message, null, username, NotificationType.EMAIL);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                    request.getSession(true).setAttribute("userid", username);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.xhtml");
                    dispatcher.forward(request, response);
                    return;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("error", "Database problem occurred during login");
            SecurityEventDAO securityDAO = new SecurityEventDAO(connection);
            String ip = Utility.getIp(request);
            String country = Utility.getCountry(request);
            String device = Utility.getDevice(request);

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            SecurityEvent sec = new SecurityEvent(username, ip, country, SecurityAction.LOGIN_FAILURE.name(), ts, device);
            try {
                securityDAO.insert(sec);
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.xhtml");
            dispatcher.forward(request, response);                
            return;
        } finally {
            Utility.closeQuietly(connection, null, null);
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

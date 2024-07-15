package org.primefaces.diamond.servlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.ProfileDAO;
import org.primefaces.diamond.util.Utility;
import org.primefaces.diamond.view.ProfileView;

/**
 *
 * @author Sean Beecrot <sean@genpen.ai> <seanbeecroft@gmail.com> 1-416-878-5282 
 */
public class UpdateProfileServlet extends HttpServlet {

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

        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            //String email = request.getParameter("email");
            String email = (String)request.getSession().getAttribute("userid");
            String address1 = request.getParameter("address1");
            String address2 = request.getParameter("address2");
            String phone = request.getParameter("phoneNumber");
            String country = request.getParameter("country");
            String province = request.getParameter("state");
            String birthday = request.getParameter("date_birth");
            String zipCode = request.getParameter("zip");
            String lastName = request.getParameter("lastname");
            String secondary = request.getParameter("email");
            String firstName = request.getParameter("firstname");
            String description = request.getParameter("description");
            String photo = "";
            String title = request.getParameter("title");
            String city = request.getParameter("city");
            System.out.println("Email:" + email);
            System.out.println("city:" + city);
            System.out.println("First Name:" + firstName);
            System.out.println("Last Name:" + lastName);

            int rank = 3;
            try { rank = Integer.parseInt(request.getParameter("rank"));} catch(Exception ex) {}
            int id = 0;
            try { id = Integer.parseInt(request.getParameter("id")); } catch(Exception ex) {}
            

            ProfileDAO dao = new ProfileDAO(connection);
            try {
                ProfileView pv = dao.find(email);
                boolean first = true;
                if( pv == null ) {
                    System.out.println("First is true");
                    pv = new ProfileView();
                } else {
                    System.out.println("First is false");
                    first = false;
                }
                pv.setFirstName(firstName);
                pv.setLastName(lastName);
                pv.setCity(city);
                pv.setZipcode(zipCode);
                pv.setCountry(country);
                pv.setTitle(title);
                pv.setDescription(description);
                pv.setPhoneNumber(phone);
                pv.setBirthday(birthday);
                pv.setId(id);
                pv.setState(province);
                //pv.setEmail(email);
                pv.setAddress1(address1);
                pv.setAddress2(address2);
                if( first ){
                    dao.insert(pv);      
                    System.out.println("First is true, insert");
                    
                } else {
                    dao.update(email, pv);
                    System.out.println("First is false, update");
                }
                request.setAttribute("message", "Profile updated");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.xhtml");
                dispatcher.forward(request, response);
                return;
            } catch (SQLException ex) {
                Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("error", "Profile update failed");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.xhtml");
            dispatcher.forward(request, response);
            
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

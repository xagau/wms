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
package org.primefaces.diamond.controllers;

import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.SecurityEventDAO;
import org.primefaces.diamond.dao.UserDAO;
import org.primefaces.diamond.domain.Activity;
import org.primefaces.diamond.domain.SecurityEvent;
import org.primefaces.diamond.domain.User;
import org.primefaces.diamond.util.Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.primefaces.diamond.domain.NotificationType;
import org.primefaces.diamond.service.MessageService;

@Named
@ManagedBean(name = "userController", eager = true)
@SessionScoped
public class UserController {
    
    public String resetPassword()
    {
        Connection connection = DatabaseManager.getConnection();
        try {
            UserDAO dao = new UserDAO(connection);
            String email = Utility.getParameterByName("email");
            User u = dao.findByEmail(email);
            if( u != null ){
                try {
                    String np = Utility.randomPassword();
                    u.setPassword(np);
                    dao.update(u);
                    return "success";
                } catch (SQLException ex) {
                    
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return "failure";
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Utility.closeQuietly(connection, null, null);
        }
        return "failure";
    }
    
    public User login(String username, String password){

        if(!Utility.credentialCheck(username, password)){
            return null;
        }
        DatabaseManager db = new DatabaseManager();
        Connection connection = DatabaseManager.getConnection();
        UserDAO dao = new UserDAO(connection);
        SecurityEventDAO securityDAO = new SecurityEventDAO(connection);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        SecurityEvent event = null;
        event = new SecurityEvent(username, "0.0.0.0", "CA", Activity.LOGIN.toString(), ts, "unknown" );
        try {
            securityDAO.insert(event);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        User user = null;
        try {
            String email = username.toLowerCase();
            user = dao.findByEmail(username);
            if( user.getPassword().equalsIgnoreCase(Utility.hashString(password))) {
                return user;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        SecurityEvent failure = null;
        failure = new SecurityEvent(username, "0.0.0.0", "CA", Activity.LOGIN_FAILURE.toString(), ts, "unknown" );
        try {
            securityDAO.insert(event);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

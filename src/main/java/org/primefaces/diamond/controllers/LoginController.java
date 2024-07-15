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

import java.io.IOException;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.UserDAO;
import org.primefaces.diamond.domain.User;


import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.diamond.dao.SecurityEventDAO;
import org.primefaces.diamond.domain.Activity;
import org.primefaces.diamond.domain.SecurityEvent;
import org.primefaces.diamond.util.Utility;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ManagedBean (name="loginController", eager=true)
@SessionScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String password;
    private String message;
    private String user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user.toLowerCase();
    }
    
    public User login(String username, String password){

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
            user = dao.find(username);
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
        } finally {
            Utility.closeQuietly(connection, null, null);
        }
        return null;
    }

    
    //validate login
    public String validate() {
        try {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                String username = Utility.getParameterByName("email");
                String password = Utility.getParameterByName("password");
                
                Connection connection= DatabaseManager.getConnection();
                UserDAO userDAO = new UserDAO(connection);
                try {
                    User u = userDAO.find(username.toLowerCase());
                    if(!Utility.hashString(u.getPassword()).equals(Utility.hashString(password))){
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Incorrect Username and Passowrd",
                                        "Please enter correct username and Password"));
                        HttpServletResponse response = Utility.getResponse();
                        response.sendRedirect("/login.xhtml");
                        return "failure";
                    } else {
                        u.setUserStatus(User.USER_STATUS_EMAIL_VERIFIED);
                        userDAO.update(u);
                        HttpServletResponse response = Utility.getResponse();
                        response.sendRedirect("/dashboard.xhtml");
                        
                        return "success";
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    HttpServletResponse response = Utility.getResponse();
                    response.sendRedirect("/login.xhtml");
                    return "failure";
                }
                
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            HttpServletResponse response = Utility.getResponse();
            response.sendRedirect("/login.xhtml");
            
            return "failure";
            
        } catch(IOException ex) { 
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
            return "failure";
        
    }


    public String logout() {
        return "logout";
    }
}

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
package org.primefaces.diamond.view;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.ProfileDAO;
import org.primefaces.diamond.util.Utility;

@Named
@ViewScoped
public class ProfileView implements Serializable {
    private int id;
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String birthday = "";
    private String country = "";
    private String phoneNumber = "";
    private String address1 = "";
    private String address2 = "";
    private String title = "";
    private String description = "";

    private String state = "";
    private String city = "";
    private String zipcode = "";
    @PostConstruct
    public void init() {
                Connection connection = DatabaseManager.getConnection();
                try {
                    ProfileDAO dao = null;
                    try {
                        FacesContext facesContext = FacesContext.getCurrentInstance();
                        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                        email = (String)session.getAttribute("userid");
                        System.out.println("Email for Session:" + email);
                        dao = new ProfileDAO(connection);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    ProfileView pv = null;
                    if(dao != null && ((pv = dao.find(email)) != null) ){

                        this.firstName = pv.getFirstName();
                        this.lastName = pv.getLastName();
                        this.email = pv.getEmail();
                        Date myDate = new Date();
                        this.birthday = new SimpleDateFormat("MM-dd-yyyy").format(myDate);
                        this.country = pv.getCountry();
                        this.phoneNumber = pv.getPhoneNumber();
                        this.address1 = pv.getAddress1();
                        this.address2 = pv.getAddress2();
                        this.state = pv.getState();
                        this.city = pv.getCity();
                        this.zipcode = pv.getZipcode();
                        return;
                    } 
                } catch(SQLException ex) { 
                    Logger.getLogger(ProfileView.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Utility.closeQuietly(connection, null, null);
                }
                this.firstName = "Change";
                this.lastName = "Me";
                this.email = "changeme@espacofacil.pt";
                Date myDate = new Date(System.currentTimeMillis());
                this.birthday = new SimpleDateFormat("MM-dd-yyyy").format(myDate);
                this.country = "Portugal";
                this.phoneNumber = "(123) 456 7890";
                this.address1 = "4702";
                this.address2 = "Av De Berna";
                this.state = "Lisbon";
                this.city = "Lisbon";
                this.zipcode = "1500-037";
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getBirthday() {
        if( birthday.equals("") ) {
            Date myDate = new Date(System.currentTimeMillis());
            this.birthday = new SimpleDateFormat("MM-dd-yyyy").format(myDate);
        }
        return birthday;
    }
    public String getCountry() {
        return country;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }
    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }
    public String getZipcode() {
        return zipcode;
    }

    public void setAddress1(String address1) {
        if( address1 != null ) { 
            this.address1 = address1;
        }
    }

    public void setAddress2(String address2) {
        if( address2 != null ) { 
            this.address2 = address2;
        }
    }

    public void setBirthday(String birthday) {
        if( birthday != null ) { 
            this.birthday = birthday;
        }
    }

    public void setCity(String city) {
        if( city != null ) { 
            this.city = city;
        }
    }

    public void setCountry(String country) {
        if( country != null ) { 
            this.country = country;
        }
    }

    public void setEmail(String email) {
        if( email != null ) { 
            this.email = email;
        }
    }

    public void setFirstName(String first_name) {
        if( first_name != null ){
            this.firstName = first_name;
        }
    }

    public void setLastName(String last_name) {
        if( last_name != null ){
            this.lastName = last_name;
        }
    }

    public void setPhoneNumber(String phone_number) {
        if( phone_number != null ){
            this.phoneNumber = phone_number;
        }
    }

    public void setState(String state) {
        if( state != null ){
            this.state = state;
        }
    }

    public void setZipcode(String zipcode) {
        if( zipcode != null ) { 
            this.zipcode = zipcode;
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        if( title != null ) { 
            this.title = title;
        }
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        if( description != null ) { 
            this.description = description;
        }
    }
}

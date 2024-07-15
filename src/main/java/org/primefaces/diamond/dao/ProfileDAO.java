/*
   Copyright 2022-2023 AGUA.SAT.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.primefaces.diamond.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.diamond.util.Utility;
import org.primefaces.diamond.view.ProfileView;

public class ProfileDAO {
    private Connection connection;

    public ProfileDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(ProfileView profile) throws SQLException {
        String sql = "INSERT INTO profiles (first_name, last_name, email, birthday, country, phone_number, address1, address2, state, city, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        Date d = new Date(0);
        try {
            d = new SimpleDateFormat("MM-dd-yyyy").parse(profile.getBirthday());
        } catch (ParseException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());
        statement.setString(3, profile.getEmail());
        statement.setDate(4, new java.sql.Date( d.getTime() ));
        statement.setString(5, profile.getCountry());
        statement.setString(6, profile.getPhoneNumber());
        statement.setString(7, profile.getAddress1());
        statement.setString(8, profile.getAddress2());
        statement.setString(9, profile.getState());
        statement.setString(10, profile.getCity());
        statement.setString(11, profile.getZipcode());
        statement.executeUpdate();
    }

    public ProfileView find(String email) throws SQLException {
        String sql = "SELECT * FROM profiles WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            ProfileView profile = new ProfileView();
            profile.setId(resultSet.getInt("id"));
            profile.setFirstName(resultSet.getString("first_name"));
            profile.setLastName(resultSet.getString("last_name"));
            profile.setEmail(resultSet.getString("email"));
            profile.setBirthday(resultSet.getString("birthday"));
            profile.setCountry(resultSet.getString("country"));
            profile.setPhoneNumber(resultSet.getString("phone_number"));
            profile.setAddress1(resultSet.getString("address1"));
            profile.setAddress2(resultSet.getString("address2"));
            profile.setState(resultSet.getString("state"));
            profile.setCity(resultSet.getString("city"));
            profile.setZipcode(resultSet.getString("zipcode"));
            return profile;
        }
        return null;
    }
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM profiles WHERE id = ?";
        Connection connection = DatabaseManager.getConnection();
        try (
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            Utility.closeQuietly(connection, statement, null);
        } catch(SQLException ex) { 
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(connection, null, null);
        }
    }

    public void update(String email, ProfileView profile) throws SQLException {
        String sql = "UPDATE profiles SET first_name = ?, last_name = ?, country = ?, phone_number = ?, address1 = ?, address2 = ?, state = ?, city = ?, zipcode = ? WHERE email = ?";
        Connection connection = DatabaseManager.getConnection();
        try {
             //System.out.println(id);
             PreparedStatement statement = connection.prepareStatement(sql);
             Date d = new Date(0);
                try {
                    d = new SimpleDateFormat("MM-dd-yyyy").parse(profile.getBirthday());
                } catch (ParseException ex) {
                    Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            statement.setString(1, profile.getFirstName());
            statement.setString(2, profile.getLastName());
            //statement.setString(3, profile.getEmail());
            //statement.setDate(4, new java.sql.Date(d.getTime()));
            statement.setString(3, profile.getCountry());
            statement.setString(4, profile.getPhoneNumber());
            statement.setString(5, profile.getAddress1());
            statement.setString(6, profile.getAddress2());
            statement.setString(7, profile.getState());
            statement.setString(8, profile.getCity());
            statement.setString(9, profile.getZipcode());
            statement.setString(10, email);
            int n = statement.executeUpdate();
            System.out.println("We updated: " + n + " records");
        }
        catch(SQLException ex) {
            System.out.println("Error: " + ex.getMessage() + " records");
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(connection, null, null);
        }
    }
    
    public List<ProfileView> findAll() {
        List<ProfileView> profiles = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DatabaseManager.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM profiles");
            while (rs.next()) {
                ProfileView profile = new ProfileView();
                profile.setId(rs.getInt("id"));
                profile.setFirstName(rs.getString("first_name"));
                profile.setLastName(rs.getString("last_name"));
                profile.setEmail(rs.getString("email"));
                profile.setBirthday(rs.getString("birthday"));
                profile.setCountry(rs.getString("country"));
                profile.setPhoneNumber(rs.getString("phone_number"));
                profile.setAddress1(rs.getString("address1"));
                profile.setAddress2(rs.getString("address2"));
                profile.setState(rs.getString("state"));
                profile.setCity(rs.getString("city"));
                profile.setZipcode(rs.getString("zipcode"));
                profiles.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return profiles;
    }
}




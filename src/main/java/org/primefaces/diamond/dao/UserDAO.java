
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

import org.primefaces.diamond.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.diamond.util.Utility;

import javax.xml.crypto.Data;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO users (id, username, first_name, last_name, email, password, phone, user_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getPhone());
            statement.setInt(8, user.getUserStatus());
            statement.executeUpdate();
            //Utility.closeQuietly(statement, connection, null);
        }
        
    }
    
    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ?, email = ?, password = ?, phone = ?, user_status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhone());
            statement.setInt(7, user.getUserStatus());
            statement.setInt(8, user.getId());
            statement.executeUpdate();
            //Utility.closeQuietly(statement, connection, null);
        }
    }

    public User find(int id) throws SQLException {
        User user = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhone(rs.getString("phone"));
            user.setUserStatus(rs.getInt("user_status"));
        }
        //Utility.closeQuietly(stmt, connection, rs);
        return user;
    }
    public User findByEmail(String email) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        User user = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(email);
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setUserStatus(rs.getInt("user_status"));
            }
        } finally {
            Utility.closeQuietly( connection, stmt, rs);
        }
        return user;
    }

    public User find(String username) throws SQLException {
        User user = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhone(rs.getString("phone"));
            user.setUserStatus(rs.getInt("user_status"));
        }
        //Utility.closeQuietly(stmt, connection, rs);
        return user;
    }
    

    public List<User> getAll() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserStatus(resultSet.getInt("user_status"));
                users.add(user);
            }
        } finally {
            Utility.closeQuietly(statement, connection, resultSet);
        }
        return users;
    }

    public void deleteByUsername(String username) throws SQLException {
        Connection conn = connection  = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            stmt.setString(1, username);
            stmt.executeUpdate();
        } finally {
            Utility.closeQuietly(stmt, connection, null);
        
        }
    }

    public void deleteByEmail(String email) throws SQLException {
        Connection conn = connection = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM users WHERE email = ?");
            stmt.setString(1, email);
            stmt.executeUpdate();
        } finally {
            Utility.closeQuietly(stmt, connection, null);

        }
    }
    public void delete(int id) throws SQLException {
        Connection conn = connection  = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            Utility.closeQuietly(stmt, connection, null);
        }
    }

}
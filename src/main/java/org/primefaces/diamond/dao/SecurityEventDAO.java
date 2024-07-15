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

import org.primefaces.diamond.domain.SecurityEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.diamond.util.Utility;

public class SecurityEventDAO {

    private Connection connection;

    public SecurityEventDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(SecurityEvent securityEvent) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt= null;;
        try {
            stmt = connection.prepareStatement("INSERT INTO security_events (ip, country_code, action, ts, device, owner) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, securityEvent.getIp());
            stmt.setString(2, securityEvent.getCountryCode());
            stmt.setString(3, securityEvent.getAction());
            stmt.setTimestamp(4, securityEvent.getTs());
            stmt.setString(5, securityEvent.getDevice());
            stmt.setString(6, securityEvent.getOwner());
            stmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(stmt, connection, null);

        }

    }

    public void update(SecurityEvent securityEvent) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE security_event SET ip = ?, country_code = ?, action = ?, ts = ?, device = ? WHERE id = ?");
        try {
            stmt.setString(1, securityEvent.getIp());
            stmt.setString(2, securityEvent.getCountryCode());
            stmt.setString(3, securityEvent.getAction());
            stmt.setTimestamp(4, securityEvent.getTs());
            stmt.setString(5, securityEvent.getDevice());
            stmt.setString(6, securityEvent.getOwner());
            stmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(stmt, connection, null);
        }

    }

    public void delete(int id) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM security_event WHERE id = ?");
        try {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(stmt, connection, null);
        }

    }

    public SecurityEvent find(int id) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM security_events WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        try {
            if (rs.next()) {
                String owner = rs.getString("owner");
                String ip = rs.getString("ip");
                String countryCode = rs.getString("country_code");
                String action = rs.getString("action");
                Timestamp ts = rs.getTimestamp("ts");
                String device = rs.getString("device");
                return new SecurityEvent(owner, ip, countryCode, action, ts, device);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(stmt, connection, rs);
        }


        return null;
    }
    
    public List<SecurityEvent> findByOwner(String owner) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM security_events WHERE owner = ?");
        stmt.setString(1, owner);
        ResultSet rs = stmt.executeQuery();
        List<SecurityEvent> securityEvents = new ArrayList<>();

        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String o = rs.getString("owner");
                String ip = rs.getString("ip");
                String countryCode = rs.getString("country_code");
                String action = rs.getString("action");
                Timestamp ts = rs.getTimestamp("ts");
                String device = rs.getString("device");
                SecurityEvent se = new SecurityEvent(owner, ip, countryCode, action, ts, device);
                securityEvents.add(se);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(stmt, connection, rs);
        }

        return securityEvents;
    }

    public List<SecurityEvent> findAll() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM security_events");
        ResultSet rs = stmt.executeQuery();
        List<SecurityEvent> securityEvents = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String owner = rs.getString("owner");
                String ip = rs.getString("ip");
                String countryCode = rs.getString("country_code");
                String action = rs.getString("action");
                Timestamp ts = rs.getTimestamp("ts");
                String device = rs.getString("device");
                SecurityEvent se = new SecurityEvent(owner, ip, countryCode, action, ts, device);
                securityEvents.add(se);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Utility.closeQuietly(stmt, connection, rs);
        }
        return securityEvents;
    }

}

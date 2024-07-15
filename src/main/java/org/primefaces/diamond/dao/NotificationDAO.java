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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.diamond.domain.Notification;
import org.primefaces.diamond.domain.NotificationType;

public class NotificationDAO {

    private static final String INSERT_SQL = "INSERT INTO notifications (ntype, email, phone, owner, ts, owner, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE notifications SET ntype=?, email=?, phone=?, owner=?, ts=?, status=?, content=? WHERE id=?";
    private static final String FIND_SQL = "SELECT * FROM notifications WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM notifications WHERE id=?";
    private static final String FIND_ALL_SQL = "SELECT * FROM notifications";
    private static final String FIND_BY_OWNER_SQL = "SELECT * FROM notifications WHERE owner=?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM notifications WHERE email=?";

    private Connection connection;

    public NotificationDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Notification notification) {
        try ( PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, notification.getType().name());
            statement.setString(2, notification.getEmail());
            statement.setString(3, notification.getPhone());
            statement.setString(4, notification.getOwner());
            statement.setTimestamp(5, notification.getTs());
            statement.setInt(6, notification.getStatus());
            statement.setString(7, notification.getContent());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Notification notification) {
        try ( PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, notification.getType().name());
            statement.setString(2, notification.getEmail());
            statement.setString(3, notification.getPhone());
            statement.setString(4, notification.getOwner());
            statement.setTimestamp(5, notification.getTs());
            statement.setInt(6, notification.getStatus());
            statement.setString(7, notification.getContent());
            statement.setInt(8, notification.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Notification find(int id) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Notification notification = new Notification();
                notification.setId(resultSet.getInt("id"));
                notification.setType(NotificationType.valueOf(resultSet.getString("type")));
                notification.setEmail(resultSet.getString("email"));
                notification.setPhone(resultSet.getString("phone"));
                notification.setOwner(resultSet.getString("owner"));
                notification.setTs(resultSet.getTimestamp("ts"));
                notification.setStatus(resultSet.getInt("status"));
                notification.setContent(resultSet.getString("content"));
                return notification;
            }
        }
        return null;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM notifications WHERE id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Notification> findAll() throws SQLException {
        String sql = "SELECT * FROM notifications";
        List<Notification> notifications = new ArrayList<>();
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setId(resultSet.getInt("id"));
                notification.setType(NotificationType.valueOf(resultSet.getString("type")));
                notification.setEmail(resultSet.getString("email"));
                notification.setPhone(resultSet.getString("phone"));
                notification.setOwner(resultSet.getString("owner"));
                notification.setTs(resultSet.getTimestamp("ts"));
                notification.setStatus(resultSet.getInt("status"));
                notification.setContent(resultSet.getString("content"));
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public List<Notification> findByOwner(String owner) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE owner = ?";
        List<Notification> notifications = new ArrayList<>();
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, owner);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setId(resultSet.getInt("id"));
                notification.setType(NotificationType.valueOf(resultSet.getString("type")));
                notification.setEmail(resultSet.getString("email"));
                notification.setPhone(resultSet.getString("phone"));
                notification.setOwner(resultSet.getString("owner"));
                notification.setTs(resultSet.getTimestamp("ts"));
                notification.setStatus(resultSet.getInt("status"));
                notification.setContent(resultSet.getString("content"));
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public List<Notification> findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE email = ?";
        List<Notification> notifications = new ArrayList<>();
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setId(resultSet.getInt("id"));
                notification.setType(NotificationType.valueOf(resultSet.getString("type")));
                notification.setEmail(resultSet.getString("email"));
                notification.setPhone(resultSet.getString("phone"));
                notification.setOwner(resultSet.getString("owner"));
                notification.setTs(resultSet.getTimestamp("ts"));
                notification.setStatus(resultSet.getInt("status"));
                notification.setContent(resultSet.getString("content"));
                notifications.add(notification);
            }
        }
        return notifications;
    }

}

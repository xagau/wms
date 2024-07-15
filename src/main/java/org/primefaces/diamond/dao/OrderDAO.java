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

import org.primefaces.diamond.domain.Order;
import org.primefaces.diamond.domain.OrderStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Order order) throws SQLException {
        String sql = "INSERT INTO orders (id, product_code, date, amount, quantity, customer, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, order.getId());
        preparedStatement.setString(2, order.getProductCode());
        preparedStatement.setDate(3, Date.valueOf(order.getDate()));
        preparedStatement.setDouble(4, order.getAmount());
        preparedStatement.setInt(5, order.getQuantity());
        preparedStatement.setString(6, order.getCustomer());
        preparedStatement.setString(7, order.getStatus().name());
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected != 1) {
            throw new SQLException("Error inserting order");
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected != 1) {
            throw new SQLException("Error deleting order");
        }
    }

    public Order find(int id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int orderId = resultSet.getInt("id");
            String productCode = resultSet.getString("product_code");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            double amount = resultSet.getDouble("amount");
            int quantity = resultSet.getInt("quantity");
            String customer = resultSet.getString("customer");
            OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
            return new Order(orderId, productCode, date, amount, quantity, customer, status);
        } else {
            return null;
        }
    }

    public List<Order> findAll() throws SQLException {
        String sql = "SELECT * FROM orders";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            int orderId = resultSet.getInt("id");
            String productCode = resultSet.getString("product_code");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            double amount = resultSet.getDouble("amount");
            int quantity = resultSet.getInt("quantity");
            String customer = resultSet.getString("customer");
            OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
            orders.add(new Order(orderId, productCode, date, amount, quantity, customer, status));
        }
        return orders;
    }

    public void update(Order order) throws SQLException {
        String sql = "UPDATE orders SET product_code = ?, date = ?, amount = ?, quantity = ?, customer = ?, status = ?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, order.getProductCode());
        preparedStatement.setDate(2, Date.valueOf(order.getDate()));
        preparedStatement.setDouble(3, order.getAmount());
        preparedStatement.setInt(4, order.getQuantity());
        preparedStatement.setString(5, order.getCustomer());
        preparedStatement.setString(6, order.getStatus().name());
        preparedStatement.setInt(7, order.getId());
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected != 1) {
            throw new SQLException("Error updating order");
        }
    }
}
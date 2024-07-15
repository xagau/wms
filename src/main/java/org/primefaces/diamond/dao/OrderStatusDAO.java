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

import org.primefaces.diamond.domain.OrderStatus;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.sql.*;

public class OrderStatusDAO {

    private Connection connection;

    public OrderStatusDAO(Connection connection) {
        this.connection = connection;
    }

    public OrderStatus insert(OrderStatus orderStatus) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO order_status VALUES (?, ?)");
        ps.setInt(1, orderStatus.ordinal());
        ps.setString(2, orderStatus.name());
        ps.executeUpdate();

        return orderStatus;
    }

    public OrderStatus delete(OrderStatus orderStatus) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM order_status WHERE id = ?");
        ps.setInt(1, orderStatus.ordinal());
        ps.executeUpdate();

        return orderStatus;
    }

    public OrderStatus find(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM order_status WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return OrderStatus.valueOf(rs.getString("name"));
        }

        return null;
    }

    public OrderStatus[] findAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM order_status");
        ResultSet rs = ps.executeQuery();

        OrderStatus[] orderStatus = new OrderStatus[rs.getFetchSize()];
        int index = 0;
        while (rs.next()) {
            orderStatus[index] = OrderStatus.valueOf(rs.getString("name"));
            index++;
        }

        return orderStatus;
    }

    public OrderStatus update(OrderStatus orderStatus) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE order_status SET name = ? WHERE id = ?");
        ps.setString(1, orderStatus.name());
        ps.setInt(2, orderStatus.ordinal());
        ps.executeUpdate();

        return orderStatus;
    }

    public static OrderStatus random() {
        Random random = new Random();
        return OrderStatus.values()[random.nextInt(OrderStatus.values().length)];
    }
}

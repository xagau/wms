
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
package org.primefaces.diamond.dao;

import org.primefaces.diamond.domain.CustomerStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerStatusDAO {

    private Connection connection;

    public CustomerStatusDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(CustomerStatus customerStatus) throws SQLException {
        String sql = "INSERT INTO customer_status (status) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerStatus.toString());
            statement.executeUpdate();
        }
    }

    public void update(CustomerStatus customerStatus) throws SQLException {
        String sql = "UPDATE customer_status SET status = ? WHERE status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerStatus.toString());
            statement.setString(2, customerStatus.toString());
            statement.executeUpdate();
        }
    }

    public void delete(CustomerStatus customerStatus) throws SQLException {
        String sql = "DELETE FROM customer_status WHERE status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerStatus.toString());
            statement.executeUpdate();
        }
    }

    public CustomerStatus find(String status) throws SQLException {
        String sql = "SELECT * FROM customer_status WHERE status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return CustomerStatus.valueOf(resultSet.getString("status"));
                } else {
                    return null;
                }
            }
        }
    }

    public List<CustomerStatus> findAll() throws SQLException {
        String sql = "SELECT * FROM customer_status";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<CustomerStatus> customerStatuses = new ArrayList<>();
                while (resultSet.next()) {
                    customerStatuses.add(CustomerStatus.valueOf(resultSet.getString("status")));
                }
                return customerStatuses;
            }
        }
    }

    public CustomerStatus random() {
        Random random = new Random();
        return CustomerStatus.values()[random.nextInt(CustomerStatus.values().length)];
    }
}
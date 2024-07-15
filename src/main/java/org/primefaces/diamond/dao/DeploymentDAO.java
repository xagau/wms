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


/*
We have this class called DeploymentDAO as DAO for POJO DeploymentDAO
*/

import org.primefaces.diamond.domain.Deployment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeploymentDAO {

    private Connection connection;

    public DeploymentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Deployment> findAll() throws SQLException {
        List<Deployment> deployments  = new ArrayList<>();

        String query = "SELECT * FROM deployments";

        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String owner = resultSet.getString("owner");
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String image = resultSet.getString("image");
            double price = resultSet.getDouble("price");
            String category = resultSet.getString("category");
            int quantity = resultSet.getInt("quantity");
            int rating = resultSet.getInt("rating");

            deployments.add(new Deployment(id, owner, code, name, description, image, price, category, quantity, rating));
        }

        return deployments;
    }
    
    public List<Deployment> findByOwner(String owner) throws SQLException {
        List<Deployment> deployments  = new ArrayList<>();

        String query = "SELECT * FROM deployments WHERE owner = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, owner);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String o = resultSet.getString("owner");
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String image = resultSet.getString("image");
            double price = resultSet.getDouble("price");
            String category = resultSet.getString("category");
            int quantity = resultSet.getInt("quantity");
            int rating = resultSet.getInt("rating");

            deployments.add(new Deployment(id, owner, code, name, description, image, price, category, quantity, rating));
        }

        return deployments;
    }


    public int insert(Deployment deployment) throws SQLException {
        String query = "INSERT INTO deployments (code, name, description, image, price, category, quantity, rating, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, deployment.getCode());
        statement.setString(2, deployment.getName());
        statement.setString(3, deployment.getDescription());
        statement.setString(4, deployment.getImage());
        statement.setDouble(5, deployment.getPrice());
        statement.setString(6, deployment.getCategory());
        statement.setInt(7, deployment.getQuantity());
        statement.setInt(8, deployment.getRating());
        statement.setString(9, deployment.getOwner());

        return statement.executeUpdate();
    }

/*
below is a function that is part of our DAO which can find an deployment by id
*/

    public Deployment find(int id) throws SQLException {
        String query = "SELECT * FROM deployments WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String owner = resultSet.getString("owner");
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String image = resultSet.getString("image");
            double price = resultSet.getDouble("price");
            String category = resultSet.getString("category");
            int quantity = resultSet.getInt("quantity");
            int rating = resultSet.getInt("rating");

            return new Deployment(id, owner, code, name, description, image, price, category, quantity, rating);
        }

        return null;
    }
    /* here we can update a deployment by id */

    public int update(int id, Deployment deployment) throws SQLException {
        String query = "UPDATE deployments SET code = ?, name = ?, description = ?, image = ?, price = ?, category = ?, quantity = ?, rating = ?, owner = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, deployment.getCode());
        statement.setString(2, deployment.getName());
        statement.setString(3, deployment.getDescription());
        statement.setString(4, deployment.getImage());
        statement.setDouble(5, deployment.getPrice());
        statement.setString(6, deployment.getCategory());
        statement.setInt(7, deployment.getQuantity());
        statement.setInt(8, deployment.getRating());
        statement.setString(9, deployment.getOwner());
        statement.setInt(10, id);

        return statement.executeUpdate();
    }

    /* here we can delete a deployment by id */

    public int delete(int id) throws SQLException {
        String query = "DELETE FROM deployments WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        return statement.executeUpdate();
    }

}

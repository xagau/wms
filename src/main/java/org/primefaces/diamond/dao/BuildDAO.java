
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

import org.primefaces.diamond.domain.Build;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildDAO {

    private Connection connection;

    public BuildDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Build build) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO builds (buildNumber, project, owner, cpu, ram, storage, transformations) VALUES (?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, build.getBuildNumber());
        statement.setString(2, build.getProject());
        statement.setString(3, build.getOwner());
        statement.setInt(4, build.getCpu());
        statement.setInt(5, build.getRam());
        statement.setInt(6, build.getStorage());
        statement.setInt(7, build.getTransformations());
        statement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM builds WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public Build find(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM builds WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int buildNumber = resultSet.getInt("buildNumber");
            String project = resultSet.getString("project");
            String owner = resultSet.getString("owner");
            int cpu = resultSet.getInt("cpu");
            int ram = resultSet.getInt("ram");
            int storage = resultSet.getInt("storage");
            int transformations = resultSet.getInt("transformations");
            Build build = new Build(buildNumber, project, owner, cpu, ram, storage, transformations);
            return build;
        }
        return null;
    }

    public List<Build> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM builds");
        List<Build> builds = new ArrayList<>();
        while (resultSet.next()) {
            int buildNumber = resultSet.getInt("buildNumber");
            String project = resultSet.getString("project");
            String owner = resultSet.getString("owner");
            int cpu = resultSet.getInt("cpu");
            int ram = resultSet.getInt("ram");
            int storage = resultSet.getInt("storage");
            int transformations = resultSet.getInt("transformations");
            Build build = new Build(buildNumber, project, owner, cpu, ram, storage, transformations);
            builds.add(build);
        }
        return builds;
    }
    
    
    public void update(Build build) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE builds SET buildNumber = ?, project = ?, owner = ?, cpu = ?, ram = ?, storage = ?, transformations = ? WHERE id = ?");
        statement.setInt(1, build.getBuildNumber());
        statement.setString(2, build.getProject());
        statement.setString(3, build.getOwner());
        statement.setInt(4, build.getCpu());
        statement.setInt(5, build.getRam());
        statement.setInt(6, build.getStorage());
        statement.setInt(7, build.getTransformations());
        statement.setInt(8, build.getId());
        statement.executeUpdate();
    }

    public List<Build> findByOwner(String owner) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM builds WHERE owner = ?");
        statement.setString(1, owner);
        ResultSet resultSet = statement.executeQuery();
        List<Build> builds = new ArrayList<>();
        while (resultSet.next()) {
            int buildNumber = resultSet.getInt("buildNumber");
            String project = resultSet.getString("project");
            int cpu = resultSet.getInt("cpu");
            int ram = resultSet.getInt("ram");
            int storage = resultSet.getInt("storage");
            int transformations = resultSet.getInt("transformations");
            Build build = new Build(buildNumber, project, owner, cpu, ram, storage, transformations);
            builds.add(build);
        }
        return builds;
    }
}
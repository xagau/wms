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



import org.primefaces.diamond.domain.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.primefaces.diamond.util.Utility;

public class ProjectDAO {

    private Connection connection;

    public ProjectDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Project project) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO projects (name, owner, creation_date, size, description, endpoint) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, project.getName());
        stmt.setString(2, project.getOwner());
        stmt.setDate(3, new java.sql.Date(project.getCreationDate().getTime()));
        stmt.setDouble(4, project.getSize());
        stmt.setString(5, project.getDescription());
        stmt.setString(6, project.getEndPoint());
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public void update(Project project) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE projects SET name = ?, owner = ?, creation_date = ?, size = ?, description = ?, endpoint = ? WHERE id = ?");
        stmt.setString(1, project.getName());
        stmt.setString(2, project.getOwner());
        stmt.setDate(3, new java.sql.Date(project.getCreationDate().getTime()));
        stmt.setDouble(4, project.getSize());
        stmt.setString(5, project.getDescription());
        stmt.setString(6, project.getEndPoint());
        stmt.setInt(7, project.getId());
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM projects WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public Project find(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String owner = rs.getString("owner");
            Date creationDate = rs.getDate("creation_date");
            double size = rs.getDouble("size");
            String description = rs.getString("description");
            String endpoint = rs.getString("endpoint");
            return new Project(id, name, owner, size, description, endpoint);
        }
        //Utility.closeQuietly(stmt, connection, rs);

        return null;
    }
    
    public List<Project> findByOwner(String owner) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
        stmt.setString(1, owner);
        ResultSet rs = stmt.executeQuery();
        List<Project> projects = new ArrayList<>();
        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String o = rs.getString("owner");
            Date creationDate = rs.getDate("creation_date");
            double size = rs.getDouble("size");
            String description = rs.getString("description");
            String endpoint = rs.getString("endpoint");
            projects.add( new Project(id, name, owner, size, description, endpoint));
        }
        //Utility.closeQuietly(stmt, connection, rs);
        
        return projects;
    }

    public List<Project> findAll() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM projects");
        ResultSet rs = stmt.executeQuery();
        List<Project> projects = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String owner = rs.getString("owner");
            Date creationDate = rs.getDate("creation_date");
            double size = rs.getDouble("size");
            String description = rs.getString("description");
            String endpoint = rs.getString("endpoint");
            projects.add( new Project(id, name, owner, size, description, endpoint));
        }
        //Utility.closeQuietly(stmt, connection, rs);
        
        return projects;
    }
}
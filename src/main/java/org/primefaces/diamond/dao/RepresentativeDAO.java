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

import org.primefaces.diamond.domain.Representative;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.diamond.util.Utility;

public class RepresentativeDAO {

    private Connection connection;

    public RepresentativeDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Representative representative) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO representatives (name, image) VALUES (?, ?)");
        stmt.setString(1, representative.getName());
        stmt.setString(2, representative.getImage());
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public void update(Representative representative) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE representatives SET name = ?, image = ? WHERE id = ?");
        stmt.setString(1, representative.getName());
        stmt.setString(2, representative.getImage());
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM representatives WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
       // Utility.closeQuietly(stmt, connection, null);

    }

    public Representative find(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM representatives WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String image = rs.getString("image");
            return new Representative(name, image);
        }
        //Utility.closeQuietly(stmt, connection, rs);

        return null;
    }

    public List<Representative> findAll() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM representatives");
        ResultSet rs = stmt.executeQuery();
        List<Representative> representatives = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String image = rs.getString("image");
            representatives.add(new Representative(name, image));
        }
        //Utility.closeQuietly(stmt, connection, rs);

        return representatives;
    }
}

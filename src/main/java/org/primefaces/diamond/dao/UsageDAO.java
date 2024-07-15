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

import org.primefaces.diamond.domain.Usage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.diamond.util.Utility;

public class UsageDAO {

    private Connection connection;

    public UsageDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Usage usage) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO `usage` (account, minutes, cpu, ram, storage, compute_credits, price) VALUES (?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, usage.getAccount());
        stmt.setInt(2, usage.getMinutes());
        stmt.setInt(3, usage.getCpu());
        stmt.setInt(4, usage.getRam());
        stmt.setInt(5, usage.getStorage());
        stmt.setInt(6, usage.getComputeCredits());
        stmt.setDouble(7, usage.getPrice());
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public void update(Usage usage) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE `usage` SET account = ?, minutes = ?, cpu = ?, ram = ?, storage = ?, price = ?, compute_credits = ? WHERE id = ?");
        stmt.setString(1, usage.getAccount());
        stmt.setInt(2, usage.getMinutes());
        stmt.setInt(3, usage.getCpu());
        stmt.setInt(4, usage.getRam());
        stmt.setInt(5, usage.getStorage());
        stmt.setDouble(6, usage.getPrice());
        stmt.setInt(7, usage.getComputeCredits());
        stmt.setInt(8, usage.getId());
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM `usage` WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        //Utility.closeQuietly(stmt, connection, null);

    }

    public Usage find(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `usage` WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String account = rs.getString("account");
            int minutes = rs.getInt("minutes");
            int cc = rs.getInt("compute_credits");
            int cpu = rs.getInt("cpu");
            int ram = rs.getInt("ram");
            int storage = rs.getInt("storage");
            double price = rs.getDouble("price");
            return new Usage(account, cc, minutes, cpu, ram, storage, price);
        }
        //Utility.closeQuietly(stmt, connection, rs);

        return null;
    }
    
    public Usage findByAccount(String acc) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `usage` WHERE account = ?");
        stmt.setString(1, acc);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String account = rs.getString("account");
            int minutes = rs.getInt("minutes");
            int cc = rs.getInt("compute_credits");
            
            int cpu = rs.getInt("cpu");
            int ram = rs.getInt("ram");
            int storage = rs.getInt("storage");
            double price = rs.getDouble("price");
            int id = rs.getInt("id");
            Usage u = new Usage(account, cc, minutes, cpu, ram, storage, price);
            u.setId(id);
            return u;
        }
        //Utility.closeQuietly(stmt, connection, rs);

        return null;
    }


    public List<Usage> findAll() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `usage`");
        ResultSet rs = stmt.executeQuery();
        List<Usage> usages = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String account = rs.getString("account");
            int minutes = rs.getInt("minutes");
            int cc = rs.getInt("compute_credits");
            
            int cpu = rs.getInt("cpu");
            int ram = rs.getInt("ram");
            int storage = rs.getInt("storage");
            double price = rs.getDouble("price");
            usages.add(new Usage(account,cc, minutes, cpu, ram, storage, price));
        }
        //Utility.closeQuietly(stmt, connection, rs);

        return usages;
    }
}


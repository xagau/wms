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


import org.primefaces.diamond.domain.Debit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DebitDAO {
    private Connection conn = null;

    public DebitDAO(Connection conn) {
        this.conn = conn;
    }

    //insert
    public int insert(Debit debit) throws SQLException {
        String sql = "INSERT INTO debit (amount,ts,owner,memo) VALUES (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setDouble(1, debit.getAmount());
        pstmt.setTimestamp(2, debit.getTs());
        pstmt.setString(3, debit.getOwner());
        pstmt.setString(4, debit.getMemo());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating debit failed, no rows affected.");
        }

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                debit.setId(generatedKeys.getInt("id"));
            } else {
                throw new SQLException("Creating debit failed, no ID obtained.");
            }
        }
        return debit.getId();
    }

    //update
    public int update(Debit debit) throws SQLException {
        String sql = "UPDATE debit SET amount=?, ts=?, owner=?, memo=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDouble(1, debit.getAmount());
        pstmt.setTimestamp(2, debit.getTs());
        pstmt.setString(3, debit.getOwner());
        pstmt.setString(4, debit.getMemo());
        pstmt.setInt(5, debit.getId());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Updating debit failed, no rows affected.");
        }
        return debit.getId();
    }

    //delete
    public void delete(Debit debit) throws SQLException {
        String sql = "DELETE FROM debit WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, debit.getId());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Deleting debit failed, no rows affected.");
        }
    }

    //find
    public Debit find(int id) throws SQLException {
        String sql = "SELECT * FROM debit WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Debit debit = new Debit();
        debit.setId(rs.getInt("id"));
        debit.setAmount(rs.getDouble("amount"));
        debit.setTs(rs.getTimestamp("ts"));
        debit.setOwner(rs.getString("owner"));
        debit.setMemo(rs.getString("memo"));
        return debit;
    }

    //findAll
    public List<Debit> findAll() throws SQLException {
        List<Debit> debits = new ArrayList<>();
        String sql = "SELECT * FROM debit";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Debit debit = new Debit();
            debit.setId(rs.getInt("id"));
            debit.setAmount(rs.getDouble("amount"));
            debit.setTs(rs.getTimestamp("ts"));
            debit.setOwner(rs.getString("owner"));
            debit.setMemo(rs.getString("memo"));
            debits.add(debit);
        }
        return debits;
    }

    //findByOwner
    public List<Debit> findByOwner(String owner) throws SQLException {
        List<Debit> debits = new ArrayList<>();
        String sql = "SELECT * FROM debit WHERE owner=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, owner);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Debit debit = new Debit();
            debit.setId(rs.getInt("id"));
            debit.setAmount(rs.getDouble("amount"));
            debit.setTs(rs.getTimestamp("ts"));
            debit.setOwner(rs.getString("owner"));
            debit.setMemo(rs.getString("memo"));
            debits.add(debit);
        }
        return debits;
    }
}


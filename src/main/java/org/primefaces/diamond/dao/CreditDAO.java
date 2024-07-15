
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


import org.primefaces.diamond.domain.Credit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDAO {
    private Connection connection;

    public CreditDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Credit credit) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO credit (owner, amount, ts, memo) VALUES (?, ?, ?, ?)");
            stmt.setString(1, credit.getOwner());
            stmt.setDouble(2, credit.getAmount());
            stmt.setTimestamp(3, credit.getTs());
            stmt.setString(4, credit.getMemo());
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void update(Credit credit) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE credit SET owner = ?, amount = ?, ts = ?, memo = ? WHERE id = ?");
            stmt.setString(1, credit.getOwner());
            stmt.setDouble(2, credit.getAmount());
            stmt.setTimestamp(3, credit.getTs());
            stmt.setString(4, credit.getMemo());
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void delete(Credit credit) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM credit WHERE id = ?");
            stmt.setInt(1, credit.getId());
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Credit findCredit(int id) throws SQLException {
        Credit credit = null;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM credit WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                credit = new Credit();
                credit.setId(rs.getInt("id"));
                credit.setOwner(rs.getString("owner"));
                credit.setAmount(rs.getDouble("amount"));
                credit.setTs(rs.getTimestamp("ts"));
                credit.setMemo(rs.getString("memo"));
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return credit;
    }

    public List<Credit> findAllCredits() throws SQLException {
        List<Credit> credits = new ArrayList<Credit>();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM credit");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Credit credit = new Credit();
                credit.setId(rs.getInt("id"));
                credit.setOwner(rs.getString("owner"));
                credit.setAmount(rs.getDouble("amount"));
                credit.setTs(rs.getTimestamp("ts"));
                credit.setMemo(rs.getString("memo"));
                credits.add(credit);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return credits;
    }

    public List<Credit> findCreditsByOwner(String owner) throws SQLException {
        List<Credit> credits = new ArrayList<Credit>();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM credit WHERE owner = ?");
            stmt.setString(1, owner);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Credit credit = new Credit();
                credit.setId(rs.getInt("id"));
                credit.setOwner(rs.getString("owner"));
                credit.setAmount(rs.getDouble("amount"));
                credit.setTs(rs.getTimestamp("ts"));
                credit.setMemo(rs.getString("memo"));
                credits.add(credit);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return credits;
    }
}


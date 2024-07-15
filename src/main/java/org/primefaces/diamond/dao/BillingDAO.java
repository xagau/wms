
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

import org.primefaces.diamond.domain.Billing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    private Connection conn;

    public BillingDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Billing billing) throws SQLException {
        String sql = "INSERT INTO billing (account, frozen) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, billing.getAccount());
        stmt.setBoolean(2, billing.isFrozen());
        stmt.executeUpdate();
    }

    public void update(Billing billing) throws SQLException {
        String sql = "UPDATE billing SET account=?, frozen=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, billing.getAccount());
        stmt.setBoolean(2, billing.isFrozen());
        stmt.setInt(3, billing.getId());
        stmt.executeUpdate();
    }

    public void delete(Billing billing) throws SQLException {
        String sql = "DELETE FROM billing WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, billing.getId());
        stmt.executeUpdate();
    }

    public Billing find(int id) throws SQLException {
        String sql = "SELECT * FROM billing WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Billing billing = new Billing();
            billing.setId(rs.getInt("id"));
            billing.setAccount(rs.getString("account"));
            billing.setFrozen(rs.getBoolean("frozen"));
            return billing;
        }
        return null;
    }

    public List<Billing> findAll() throws SQLException {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Billing billing = new Billing();
            billing.setId(rs.getInt("id"));
            billing.setAccount(rs.getString("account"));
            billing.setFrozen(rs.getBoolean("frozen"));
            billings.add(billing);
        }
        return billings;
    }

    public List<Billing> findByAccount(String account) throws SQLException {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE account=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, account);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Billing billing = new Billing();
            billing.setId(rs.getInt("id"));
            billing.setAccount(rs.getString("account"));
            billing.setFrozen(rs.getBoolean("frozen"));
            billings.add(billing);
        }
        return billings;
    }
}

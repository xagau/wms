
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


import org.primefaces.diamond.domain.Debit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.diamond.domain.Action;
import org.primefaces.diamond.service.MessageService;

public class ActionDAO {
    private Connection conn = null;

    public ActionDAO(Connection conn) {
        this.conn = conn;
    }

    //insert
    public int insert(Action action) throws SQLException {

        String sql = "INSERT INTO actions (action, action_date, owner, device, ip ) VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, action.getAction());
        pstmt.setDate(2, new Date(action.getActionDate().getTime()));
        pstmt.setString(3, action.getOwner());
        pstmt.setString(4, action.getDevice());
        pstmt.setString(5, action.getUserIpAddress());
        

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating action failed, no rows affected.");
        }

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                action.setId(generatedKeys.getInt("id"));
            } else {
                throw new SQLException("Creating action failed, no ID obtained.");
            }
        }
        return action.getId();
    }

    //update
    public int update(Action action) throws SQLException {
        String sql = "UPDATE actions SET action=?, action_date=?, device=?, owner=?, ip=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, action.getAction());
        pstmt.setDate(2, new Date(action.getActionDate().getTime()));
        pstmt.setString(3, action.getDevice());
        pstmt.setString(4, action.getOwner());
        pstmt.setString(5, action.getUserIpAddress());
        pstmt.setInt(6, action.getId());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Updating action failed, no rows affected.");
        }
        return action.getId();
    }

    //delete
    public void delete(Action action) throws SQLException {
        String sql = "DELETE FROM actions WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, action.getId());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Deleting action failed, no rows affected.");
        }
    }

    //find
    public Action find(int id) throws SQLException {
        String sql = "SELECT * FROM actions WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Action action = new Action();
        action.setId(rs.getInt("id"));
        action.setActionDate(rs.getDate("action_date"));
        action.setDevice(rs.getString("device"));
        action.setUserIpAddress(rs.getString("ip"));
        action.setOwner(rs.getString("owner"));
        action.setAction(rs.getString("action"));
        return action;
    }

    //findAll
    public List<Action> findAll() throws SQLException {
        List<Action> actions = new ArrayList<>();
        String sql = "SELECT * FROM actions";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Action a = new Action();
        a.setAction("List Security");
        a.setActionDate(new Date(System.currentTimeMillis()));
        a.setDevice("N/A");
        a.setOwner("N/A");
        a.setUserIpAddress("N/A");
        actions.add(a);

        while (rs.next()) {
            Action action = new Action();
            action.setId(rs.getInt("id"));
            action.setActionDate(rs.getDate("action_date"));
            action.setDevice(rs.getString("device"));
            action.setUserIpAddress(rs.getString("ip"));
            action.setOwner(rs.getString("owner"));
            action.setAction(rs.getString("action"));
            actions.add(action);
        }
        return actions;
    }

    //findByOwner
    public List<Action> findByOwner(String owner) throws SQLException {
        List<Action> actions = new ArrayList<>();
        String sql = "SELECT * FROM actions WHERE owner=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, owner);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Action action = new Action();
            action.setId(rs.getInt("id"));
            action.setActionDate(rs.getDate("action_date"));
            action.setDevice(rs.getString("device"));
            action.setUserIpAddress(rs.getString("ip"));
            action.setOwner(rs.getString("owner"));
            action.setAction(rs.getString("action"));
            actions.add(action);
        }
        return actions;
    }
    //findByIp
    public List<Action> findByIp(String ip) throws SQLException {
        List<Action> actions = new ArrayList<>();
        String sql = "SELECT * FROM actions WHERE ip=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, ip);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Action action = new Action();
            action.setId(rs.getInt("id"));
            action.setActionDate(rs.getDate("action_date"));
            action.setDevice(rs.getString("device"));
            action.setUserIpAddress(rs.getString("ip"));
            action.setOwner(rs.getString("owner"));
            action.setAction(rs.getString("action"));
            actions.add(action);
        }
        return actions;
    }
}


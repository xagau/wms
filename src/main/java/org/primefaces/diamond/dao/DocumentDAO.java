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

import org.primefaces.diamond.domain.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    private Connection conn;

    public DocumentDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Document document) throws SQLException {
        String sql = "INSERT INTO Document (name, size, type) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, document.getName());
        pstmt.setString(2, document.getSize());
        pstmt.setString(3, document.getType());
        pstmt.executeUpdate();
    }

    public void delete(Document document) throws SQLException {
        String sql = "DELETE FROM Document WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, document.getName());
        pstmt.executeUpdate();
    }

    public Document find(String name) throws SQLException {
        String sql = "SELECT * FROM Document WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String size = rs.getString("size");
            String type = rs.getString("type");
            return new Document(name, size, type);
        }
        return null;
    }

    public List<Document> findAll() throws SQLException {
        String sql = "SELECT * FROM Document";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Document> documents = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            String size = rs.getString("size");
            String type = rs.getString("type");
            documents.add(new Document(name, size, type));
        }
        return documents;
    }

    public void update(Document document) throws SQLException {
        String sql = "UPDATE Document SET size = ?, type = ? WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, document.getSize());
        pstmt.setString(2, document.getType());
        pstmt.setString(3, document.getName());
        pstmt.executeUpdate();
    }
}
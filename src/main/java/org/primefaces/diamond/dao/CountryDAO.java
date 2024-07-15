
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

import org.primefaces.diamond.domain.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    private Connection connection;

    public CountryDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Country country) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO countrys (name, code) VALUES (?, ?)");
        stmt.setString(1, country.getName());
        stmt.setString(2, country.getCode());
        stmt.executeUpdate();
    }

    public void update(Country country) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE countrys SET name = ?, code = ? WHERE id = ?");
        stmt.setString(1, country.getName());
        stmt.setString(2, country.getCode());
        stmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM countrys WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public Country find(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM countrys WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String code = rs.getString("code");
            return new Country(name, code);
        }
        return null;
    }

    public List<Country> findAll() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM country");
        ResultSet rs = stmt.executeQuery();
        List<Country> countries = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String code = rs.getString("code");
            countries.add(new Country(name, code));
        }
        return countries;
    }
}
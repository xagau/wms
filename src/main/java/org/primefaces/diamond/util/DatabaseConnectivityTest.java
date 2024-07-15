package org.primefaces.diamond.util;

import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.domain.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseConnectivityTest {
    public static void main(String[] args){
        Connection connection = DatabaseManager.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM projects limit 1");
            stmt.executeUpdate();
            stmt.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }  finally {
            Utility.closeQuietly(connection, null, null);
        }

    }
}

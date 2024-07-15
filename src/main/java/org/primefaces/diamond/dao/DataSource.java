
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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.primefaces.diamond.Server;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class DataSource {

    private static DataSource datasource;
    private ComboPooledDataSource cpds;

    private DataSource() throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass(Server.getProperty("driver")); //loads the jdbc driver
        cpds.setJdbcUrl(Server.getProperty("connectionString"));
        cpds.setUser(Server.getProperty("userid"));
        cpds.setPassword(Server.getProperty("password"));
        cpds.setTestConnectionOnCheckout(true);
        try {
            //cpds.setStatementCacheNumDeferredCloseThreads(1);
        } catch(Exception ex) { ex.printStackTrace(); } catch(Error er) { er.printStackTrace(); }
        try {
            //cpds.setPrivilegeSpawnedThreads(true);
        } catch(Exception ex) { ex.printStackTrace(); } catch(Error er) { er.printStackTrace(); }
        cpds.setMaxPoolSize(300);
        cpds.setAcquireIncrement(10);
        cpds.setMaxIdleTime(300);
    }

    public ComboPooledDataSource getComboPooledDataSource() {
        return this.cpds;
    }

    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {

        if (datasource == null) {
            synchronized (DataSource.class) {
                if (datasource == null) {
                    datasource = new DataSource();
                }
            }
        }
        return datasource;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = this.cpds.getConnection();
        return connection;
    }

}

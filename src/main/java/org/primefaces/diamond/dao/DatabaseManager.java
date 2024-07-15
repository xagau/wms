
package org.primefaces.diamond.dao;

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


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author xagau
 */
public class DatabaseManager {

    static long calls = 0;

    static Lock lock = new ReentrantLock();

    static Connection source = null;

    public static Connection getConnection() {

        try {
            lock.lock();
            boolean verbose = false;
            calls++;
            try {
                Exception e = new Exception();
                StackTraceElement[] list = e.getStackTrace();
                String from = list[1].getClassName() + "." + list[1].getMethodName();
                if (verbose) {
                    System.out.println("Call###" + calls + " to getConnection() from: " + from);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                DataSource ds = null;
                ds = DataSource.getInstance();
                source = ds.getConnection();
                if (verbose) {
                    ComboPooledDataSource cpds = ds.getComboPooledDataSource();
                    System.out.println("num_connections: " + cpds.getNumConnectionsDefaultUser());
                    System.out.println("num_busy_connections: " + cpds.getNumBusyConnectionsDefaultUser());
                    System.out.println("num_idle_connections: " + cpds.getNumIdleConnectionsDefaultUser());
                }
                return source;
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void heartbeat() {
        Connection conn = null;
        try {
            conn = getConnection();
            try {
                Statement statement = conn.createStatement();
                ResultSet r = statement.executeQuery("select * from users limit 1;");
                r.close();
                statement.close();
            } catch(Exception ex) { } 
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

}

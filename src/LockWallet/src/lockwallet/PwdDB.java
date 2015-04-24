/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockwallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author giovanni
 */
public class PwdDB {

    private Connection connection;

    public void OpenDB(String dbname) {
        //check connection opened
        if (this.connection != null) {
            System.out.println("Database already connected\nClose it first");
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbname);
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void CloseDB() {
        if (this.connection == null) {
            return;
        }

        try {
            this.connection.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    //creates the database
    public void CreateDB() {
        if (this.connection == null) {
            return;
        }

        try {
            Statement stmt = null;
            stmt = this.connection.createStatement();
            String sql = "CREATE TABLE PASSWD "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " DOMAIN           TEXT    NOT NULL, "
                    + " USERNAME            TEXT     NOT NULL, "
                    + " PASSWORD        TEXT, "
                    + " COMMENT         TEXT)";
            stmt.executeUpdate(sql);
            this.connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    //Insert a password in the database
    public void InsertPWD(PwdItem encpwd) {
        if (this.connection == null || encpwd == null) {
            return;
        }

        try {
            int maxkey = 0;
            Statement stmt = null;
            stmt = this.connection.createStatement();

            //find the max id
            ResultSet rs = stmt.executeQuery("SELECT * FROM PASSWD;");
            while (rs.next()) {
                int id = rs.getInt("ID");
                if (id >= maxkey) {
                    maxkey = id + 1;
                }
            }
            rs.close();

            //add the element into the database
            String sql = "INSERT INTO PASSWD (ID,DOMAIN,USERNAME,PASSWORD,COMMENT) VALUES"
                    + "(" + maxkey + ","
                    + " '" + encpwd.getDomain() + "', "
                    + " '" + encpwd.getUsername() + "', "
                    + " '" + encpwd.getPassword() + "', "
                    + " '" + encpwd.getComment() + "') ";
            stmt.executeUpdate(sql);
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    //delete an element in the database
    public void DeletePWD(int id) {
        if (this.connection == null) {
            return;
        }

        try {
            Statement stmt = null;
            //delete the element into the database
            stmt = this.connection.createStatement();
            String sql = "DELETE FROM PASSWD WHERE ID=" + id + ";";
            stmt.executeUpdate(sql);
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    //get an entry by id 
    public PwdItem GetItem(int id) {
        PwdItem item = new PwdItem();

        if (this.connection == null) {
            return null;
        }

        try {
            Statement stmt = null;
            stmt = this.connection.createStatement();
            int count = 0;

            //find the max id
            ResultSet rs = stmt.executeQuery("SELECT * FROM PASSWD WHERE ID=" + id + ";");
            while (rs.next()) {
                item.setId(rs.getInt("ID"));
                item.setDomain(rs.getString("DOMAIN"));
                item.setUsername(rs.getString("USERNAME"));
                item.setPassword(rs.getString("PASSWORD"));
                item.setComment(rs.getString("COMMENT"));

                count++;
            }
            rs.close();
            stmt.close();

            //no item found
            if (count == 0) {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return item;
    }

    //get all entries 
    public List<PwdItem> GetItems() {
        
        List<PwdItem> pwdlist=new ArrayList<PwdItem>();

        if (this.connection == null) {
            return null;
        }

        try {
            Statement stmt = null;
            stmt = this.connection.createStatement();
            int count = 0;

            //find the max id
            ResultSet rs = stmt.executeQuery("SELECT * FROM PASSWD;");
            while (rs.next()) {
                PwdItem item = new PwdItem();
                item.setId(rs.getInt("ID"));
                item.setDomain(rs.getString("DOMAIN"));
                item.setUsername(rs.getString("USERNAME"));
                item.setPassword(rs.getString("PASSWORD"));
                item.setComment(rs.getString("COMMENT"));
                
                pwdlist.add(item);

                count++;
            }
            rs.close();
            stmt.close();

            //no item found
            if (count == 0) {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return pwdlist;
    }

}

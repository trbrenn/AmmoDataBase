/*
 * DataBaseConn.java
 *
 * Created on July 1, 2007, 11:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseClasses;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import javax.swing.JFrame;

/**
 *
 * @author Todd Brenneman
 */
public class DataBaseConn {
    
    private Connection Conn; 	
    private Statement Stmt;		
    private ResultSet RS;
    private DataBaseConnData DBCD;

    //Standard constructor
    public DataBaseConn()
    {
        //DataBaseConnDataDialog dbcdd = new DataBaseConnDataDialog();
        //DBCD = dbcdd.getDataBaseConnData();
        DBCD = new DataBaseConnData();
    }

    public DataBaseConn(String url, String cl)
    {
        //DataBaseConnDataDialog dbcdd = new DataBaseConnDataDialog(url, cl);
        //DBCD = dbcdd.getDataBaseConnData();
         DBCD = new DataBaseConnData(url, cl);
    }

    public DataBaseConn(DataBaseConnData dbcd)
    {
        DBCD = dbcd;
    }

    // Connects the class to the database.
    public void connect() throws Exception
    {
        Class.forName(DBCD.getCL());
        Conn = DriverManager.getConnection(DBCD.getURL(), DBCD.getUserName(), DBCD.getPassWord());
        Stmt = Conn.createStatement();
    }

    public void connect(String url, String usr, String passwd, String cl) throws Exception
    {
        Class.forName(cl);
        Conn = DriverManager.getConnection(url, usr, passwd);
        Stmt = Conn.createStatement();
    }

    public void connect(DataBaseConnData dbc) throws Exception
    {
        Class.forName(dbc.getCL());
        Conn = DriverManager.getConnection(dbc.getURL(), dbc.getUserName(), dbc.getPassWord());
        Stmt = Conn.createStatement();
    }

    public Connection getConnection()
    {
        return Conn;
    }

    public void setConnection(Connection c)
    {
        Conn=c;
    }

    public Statement getStatement()
    {
        return Stmt;
    }

    public ResultSet getResultSet()
    {
        return RS;
    }

    // Close the connection to the database.
    public void close()
    {
        try {
            RS.close();
            Stmt.close();
            Conn.close();
        } catch(Exception e) {		
        }
    }

    // runs query and returns the result set.
    public ResultSet runQuery(String query) throws Exception
    {
        //System.out.println("Query = "+query+"\n");
        RS = Stmt.executeQuery(query);
        return RS;
    }

    public int runUpdate(String update) throws Exception
    {
        //System.out.println("Update = "+update+"\n");
        return Stmt.executeUpdate(update);
    }    
}

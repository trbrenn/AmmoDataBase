/*
 * ReloadingDiesQuery.java
 *
 * Created on August 19, 2007, 5:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
// +----------+--------------+------+-----+---------+----------------+
// | Field    | Type         | Null | Key | Default | Extra          |
// +----------+--------------+------+-----+---------+----------------+
// | ID       | int(11)      |      | PRI | NULL    | auto_increment |
// | Maker    | varchar(50)  |      |     |         |                |
// | Caliber  | varchar(100) |      |     |         |                |
// | Type     | varchar(50)  |      |     |         |                |
// | FLCSD    | tinyint(1)   |      |     | 0       |                |
// | Expander | tinyint(1)   |      |     | 0       |                |
// | Seater   | tinyint(1)   |      |     | 0       |                |
// | Roll     | tinyint(1)   |      |     | 0       |                |
// | Factory  | tinyint(1)   |      |     | 0       |                |
// | FLSSD    | tinyint(1)   |      |     | 0       |                |
// | Trim     | tinyint(1)   |      |     | 0       |                |
// +----------+--------------+------+-----+---------+----------------+
package ReloadingDiesEvent;
import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Todd Brenneman
 * +----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| ID       | int(11)      | NO   | PRI | NULL    | auto_increment |
| Maker    | varchar(50)  | NO   |     |         |                |
| Caliber  | varchar(100) | NO   |     |         |                |
| Type     | varchar(50)  | NO   |     |         |                |
| FLCSD    | tinyint(1)   | NO   |     | 0       |                |
| Expander | tinyint(1)   | NO   |     | 0       |                |
| Seater   | tinyint(1)   | NO   |     | 0       |                |
| Roll     | tinyint(1)   | NO   |     | 0       |                |
| Factory  | tinyint(1)   | NO   |     | 0       |                |
| FLSSD    | tinyint(1)   | NO   |     | 0       |                |
| Trim     | tinyint(1)   | NO   |     | 0       |                |
+----------+--------------+------+-----+---------+----------------+
11 rows in set (0.00 sec)
 */
public class ReloadingDiesQuery 
{
    private DataBaseConn DBConn;
    private ReloadingDies rd;
    private List<ReloadingDies> hrd;
    
    /** Creates a new instance of ReloadingDiesQuery */
    public ReloadingDiesQuery(DataBaseConn dbc) {
    	DBConn = dbc;
    }
    
    public ReloadingDies getID(int ln) throws Exception    {
        return this.getID(String.valueOf(ln)); 
    }
    
    public ReloadingDies getID(String ln) throws Exception    {
        String QueryString = "SELECT * FROM AmmoDataBase.ReloadingDies WHERE ID = "+ln+"";
            ResultSet rs = DBConn.runQuery(QueryString);

            rs.next();
            rd = new ReloadingDies();
            rd.setID(rs.getInt("ID"));
            rd.setMaker(rs.getString("Maker"));
            rd.setCaliber(rs.getString("Caliber"));
            rd.setType(rs.getString("Type"));
            rd.setFLCSD(rs.getBoolean("FLCSD"));
            rd.setExpander(rs.getBoolean("Expander"));
            rd.setSeater(rs.getBoolean("Seater"));
            rd.setRoll(rs.getBoolean("Roll"));
            rd.setFactory(rs.getBoolean("Factory"));
            rd.setFLSSD(rs.getBoolean("FLSSD"));
            rd.setTrim(rs.getBoolean("Trim"));

            rs.close();
            return rd;
    }
	
    public List<ReloadingDies> getAll() throws Exception    {
        String QueryString = "SELECT * FROM AmmoDataBase.ReloadingDies";

        ResultSet rs = DBConn.runQuery(QueryString);

        hrd = new ArrayList<ReloadingDies> ();
        while(rs.next()){
            rd = new ReloadingDies();
            rd.setID(rs.getInt("ID"));
            rd.setMaker(rs.getString("Maker"));
            rd.setCaliber(rs.getString("Caliber"));
            rd.setType(rs.getString("Type"));
            rd.setFLCSD(rs.getBoolean("FLCSD"));
            rd.setExpander(rs.getBoolean("Expander"));
            rd.setSeater(rs.getBoolean("Seater"));
            rd.setRoll(rs.getBoolean("Roll"));
            rd.setFactory(rs.getBoolean("Factory"));
            rd.setFLSSD(rs.getBoolean("FLSSD"));
            rd.setTrim(rs.getBoolean("Trim"));
            hrd.add(rd);
            rd = null;
        }

        rs.close();
        return hrd;
    }

    public List<ReloadingDies> getAllCaliber(String cal) throws Exception    {
        String QueryString = "SELECT * FROM AmmoDataBase.ReloadingDies WHERE Caliber like \""+cal+"\"";

        ResultSet rs = DBConn.runQuery(QueryString);

        hrd = new ArrayList<ReloadingDies> ();
        while(rs.next()){
            rd = new ReloadingDies();
            rd.setID(rs.getInt("ID"));
            rd.setMaker(rs.getString("Maker"));
            rd.setCaliber(rs.getString("Caliber"));
            rd.setType(rs.getString("Type"));
            rd.setFLCSD(rs.getBoolean("FLCSD"));
            rd.setExpander(rs.getBoolean("Expander"));
            rd.setSeater(rs.getBoolean("Seater"));
            rd.setRoll(rs.getBoolean("Roll"));
            rd.setFactory(rs.getBoolean("Factory"));
            rd.setFLSSD(rs.getBoolean("FLSSD"));
            rd.setTrim(rs.getBoolean("Trim"));
            hrd.add(rd);
            rd = null;
        }
        rs.close();

        return hrd;
    }
	
    public int insertID(ReloadingDies rd) throws Exception    {
        String QueryString = "INSERT INTO AmmoDataBase.ReloadingDies (Maker,Caliber,Type,";
        QueryString = QueryString + "FLCSD,Expander,Seater,Roll,Factory,FLSSD,Trim) VALUES (";
        QueryString = QueryString + "\""+rd.getMaker()+"\", ";
        QueryString = QueryString + "\""+rd.getCaliber()+"\", ";		
        QueryString = QueryString + "\""+rd.getType()+"\", ";
        if(rd.getFLCSD())
            QueryString = QueryString +"1, ";
        else 
            QueryString = QueryString +"0, ";
        if(rd.getExpander())
            QueryString = QueryString +"1, ";
        else 
            QueryString = QueryString +"0, ";
        if(rd.getSeater())
            QueryString = QueryString +"1, ";
        else 
            QueryString = QueryString +"0, ";
        if(rd.getRoll())
            QueryString = QueryString +"1, ";
        else 
            QueryString = QueryString +"0, ";
        if(rd.getFactory())
            QueryString = QueryString +"1, ";
        else 
            QueryString = QueryString +"0, ";
        if(rd.getFLSSD())
            QueryString = QueryString +"1, ";
        else 
            QueryString = QueryString +"0, ";
        if(rd.getTrim())
            QueryString = QueryString +"1) ";
        else 
            QueryString = QueryString +"0) ";

        return DBConn.runUpdate(QueryString);
    }

    public int updateID(ReloadingDies rd) throws Exception    {
        String QueryString = "UPDATE AmmoDataBase.ReloadingDies SET ";
        QueryString = QueryString + "ID = "+rd.getID()+", ";
        QueryString = QueryString + "Maker = \""+rd.getMaker()+"\", ";
        QueryString = QueryString + "Caliber = \""+rd.getCaliber()+"\", ";
        QueryString = QueryString + "Type = \""+rd.getType()+"\", ";
        if(rd.getFLCSD())
            QueryString = QueryString +"FLCSD = 1, ";
        else 
            QueryString = QueryString +"FLCSD = 0, ";
        if(rd.getExpander())
            QueryString = QueryString +"Expander = 1, ";
        else 
            QueryString = QueryString +"Expander = 0, ";
        if(rd.getSeater())
            QueryString = QueryString +"Seater = 1, ";
        else 
            QueryString = QueryString +"Seater = 0, ";
        if(rd.getRoll())
            QueryString = QueryString +"Roll = 1, ";
        else 
            QueryString = QueryString +"Roll = 0, ";
        if(rd.getFactory())
            QueryString = QueryString +"Factory = 1, ";
        else 
            QueryString = QueryString +"Factory = 0, ";
        if(rd.getFLSSD())
            QueryString = QueryString +"FLSSD = 1, ";
        else 
            QueryString = QueryString +"FLSSD = 0, ";
        if(rd.getTrim())
            QueryString = QueryString +"Trim = 1 ";
        else 
            QueryString = QueryString +"Trim = 0 ";

        QueryString = QueryString + "WHERE ID = "+rd.getID();
        return DBConn.runUpdate(QueryString);
    }    
    
    public int deleteID(ReloadingDies rd) throws Exception    {
        String QueryString = "Delete from AmmoDataBase.ReloadingDies ";
        QueryString = QueryString + "WHERE ID = "+rd.getID();
        return DBConn.runUpdate(QueryString);
    } 
    
    public void connect() throws Exception    {
        DBConn.connect();
    }
	
    public void close() throws Exception    {
        DBConn.close();
    }
    
    public List<String> getMaker() throws Exception {
        String QueryString = "SELECT DISTINCT Maker FROM ammodatabase.reloadingdies order by Maker";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Maker"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getCaliber() throws Exception {
        String QueryString = "SELECT DISTINCT Caliber FROM ammodatabase.reloadingdies order by Caliber";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Caliber"));
        }
        rs.close();
        return ac;
    }
    
    public List<ReloadingDies> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.reloadingdies Where "+tableName+" = \""+name+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<ReloadingDies> pList = new ArrayList<ReloadingDies>();
        while(rs.next()){
            rd = new ReloadingDies();
            rd.setID(rs.getInt("ID"));
            rd.setMaker(rs.getString("Maker"));
            rd.setCaliber(rs.getString("Caliber"));
            rd.setType(rs.getString("Type"));
            rd.setFLCSD(rs.getBoolean("FLCSD"));
            rd.setExpander(rs.getBoolean("Expander"));
            rd.setSeater(rs.getBoolean("Seater"));
            rd.setRoll(rs.getBoolean("Roll"));
            rd.setFactory(rs.getBoolean("Factory"));
            rd.setFLSSD(rs.getBoolean("FLSSD"));
            rd.setTrim(rs.getBoolean("Trim"));			
            pList.add(rd);
            rd = null;
	}
        rs.close();
        return pList;
    }
}

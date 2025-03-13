/*
 * PowderQuery.java
 *
 * Created on July 1, 2007, 12:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
// +-----------------+-------------+------+-----+---------+-------+
// | Field           | Type        | Null | Key | Default | Extra |
// +-----------------+-------------+------+-----+---------+-------+
// | PowderLotNumber | varchar(50) |      | PRI |         |       |
// | PowerMaker      | varchar(50) |      |     |         |       |
// | PowderName      | varchar(50) |      |     |         |       |
// | LotWeight       | int(11)     |      |     | 0       |       |
// | LotCost         | float       |      |     | 0       |       |
// | CostPerGrain    | float       |      |     | 0       |       |
// | empty1          | tinyint(1)  |      |     | 0       |       |
// | PurchaseDate    | date        |      |     |         |       |
// +-----------------+-------------+------+-----+---------+-------+
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PowderEvent;
import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Todd Brenneman
 */
public class PowderQuery {
    private DataBaseConn DBConn;
    private Powder       PW;
    private List<Powder> hpw;
    private boolean	 Ignoreempty1;

    public PowderQuery(DataBaseConn dbc)
    {
        DBConn = dbc;
        Ignoreempty1 = false;
    }

    public PowderQuery(DataBaseConn dbc, boolean ie)
    {
        DBConn = dbc;
        Ignoreempty1 = ie;
    }

    public boolean isIgnoreempty1() {
        return Ignoreempty1;
    }

    public void setIgnoreempty1(boolean ignoreempty1) {
        Ignoreempty1 = ignoreempty1;
    }

    public Powder getLotNumber(String ln) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Powder WHERE PowderLotNumber = \""+ln+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        rs.next();
        PW = new Powder();
        PW.setPowderLotNumber(rs.getString("PowderLotNumber"));
        PW.setPowderMaker(rs.getString("PowderMaker"));
        PW.setPowderName(rs.getString("PowderName"));
        PW.setLotWeightInLbs(rs.getInt("LotWeight"));
        PW.setLotCost(rs.getFloat("LotCost"));
        PW.setCostPerGrain(rs.getFloat("CostPerGrain"));
        PW.setDatePurchased(rs.getString("PurchaseDate"));
        PW.setEmpty(rs.getBoolean("empty1"));
        rs.close();
        return PW;
    }

    public List<Powder> getAll() throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Powder";
        if(Ignoreempty1)
            QueryString = QueryString + " WHERE empty1 = 0";
        QueryString = QueryString + " order by PurchaseDate";
        ResultSet rs = DBConn.runQuery(QueryString);

        hpw = new ArrayList<Powder> ();
        while(rs.next())
        {
            PW = new Powder();
            PW.setPowderLotNumber(rs.getString("PowderLotNumber"));
            PW.setPowderMaker(rs.getString("PowderMaker"));
            PW.setPowderName(rs.getString("PowderName"));
            PW.setLotWeightInLbs(rs.getInt("LotWeight"));
            PW.setLotCost(rs.getFloat("LotCost"));
            PW.setCostPerGrain(rs.getFloat("CostPerGrain"));
            PW.setDatePurchased(rs.getString("PurchaseDate"));
            PW.setEmpty(rs.getBoolean("empty1"));
            hpw.add(PW);
            PW = null;
        }
        rs.close();
        return hpw;
    }

    public int insertLotNumber(Powder pw) throws Exception
    {
    System.out.println("DatePurchased = "+pw.getDatePurchased());
        String QueryString = "INSERT INTO AmmoDataBase.Powder (PowderLotNumber,PowderMaker,PowderName,LotWeight,LotCost,CostPerGrain,PurchaseDate,empty1) VALUES (";
        QueryString = QueryString + "\""+ pw.getPowderLotNumber() +"\",";
        QueryString = QueryString + "\""+ pw.getPowderMaker() +"\",";
        QueryString = QueryString + "\""+ pw.getPowderName() +"\",";
        QueryString = QueryString + pw.getLotWeight() +",";
        QueryString = QueryString + pw.getLotCost() +",";
        QueryString = QueryString + pw.getCostPerGrain() +",";
        QueryString = QueryString + "\""+ pw.getDatePurchased() +"\",";
        if(pw.isEmpty())
            QueryString = QueryString + "1)";
        else
            QueryString = QueryString + "0)";

        return DBConn.runUpdate(QueryString);
    }

    public int updateLotNumber(Powder pw) throws Exception
    {
        String QueryString = "UPDATE AmmoDataBase.Powder SET ";
        QueryString = QueryString + "PowderMaker = \""+ pw.getPowderMaker() +"\",";
        QueryString = QueryString + "PowderName = \""+ pw.getPowderName() +"\",";
        QueryString = QueryString + "LotWeight = "+pw.getLotWeight() +",";
        QueryString = QueryString + "LotCost = "+pw.getLotCost() +",";
        QueryString = QueryString + "CostPerGrain = " +pw.getCostPerGrain() +",";
        QueryString = QueryString + "PurchaseDate = \"" +pw.getDatePurchased()+"\",";
        if(pw.isEmpty())
            QueryString = QueryString + "empty1 = 1";
        else
            QueryString = QueryString + "empty1 = 0";

        QueryString = QueryString + " WHERE PowderLotNumber = \""+ pw.getPowderLotNumber() +"\"";

        return DBConn.runUpdate(QueryString);
    }

    public int deleteLotNumber(Powder pw) throws Exception
    {
        String QueryString = "Delete from AmmoDataBase.Powder where PowderLotNumber = \""+ pw.getPowderLotNumber() +"\" ";
        return DBConn.runUpdate(QueryString);
    }
        
    public void connect() throws Exception
    {
    	DBConn.connect();
    }
    	
    public void close() throws Exception
    {
    	DBConn.close();
    }
    	
    public List<String> getAllPowderLotNums() throws Exception {
        String QueryString = "SELECT * FROM AmmoDataBase.Powder";
        if(Ignoreempty1)
            QueryString = QueryString + " WHERE empty1 = 0";

        
        ResultSet rs = DBConn.runQuery(QueryString);

        List<String> pd = new ArrayList<String>();
        while(rs.next())
        {
            String data = new String(rs.getString("PowderLotNumber") +", "+ rs.getString("PowderName"));
            pd.add(data);
        }
        rs.close();
        return pd;
    }

    public List<String> getMaker() throws Exception {
        String QueryString = "SELECT DISTINCT PowderMaker FROM ammodatabase.powder order by PowderMaker";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("PowderMaker"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getName() throws Exception {
        String QueryString = "SELECT DISTINCT PowderName FROM ammodatabase.powder order by PowderName";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("PowderName"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getDate() throws Exception {
        String QueryString = "SELECT DISTINCT PurchaseDate FROM ammodatabase.powder order by PurchaseDate";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("PurchaseDate"));
        }
        rs.close();
        return ac;
    }
    
    public List<Powder> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.powder Where "+tableName+" = \""+name+"\"";
        QueryString = QueryString + " order by PurchaseDate";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<Powder> pList = new ArrayList<Powder>();
        while(rs.next()){
            PW = new Powder();
            PW.setPowderLotNumber(rs.getString("PowderLotNumber"));
            PW.setPowderMaker(rs.getString("PowderMaker"));
            PW.setPowderName(rs.getString("PowderName"));
            PW.setLotWeightInLbs(rs.getInt("LotWeight"));
            PW.setLotCost(rs.getFloat("LotCost"));
            PW.setCostPerGrain(rs.getFloat("CostPerGrain"));
            PW.setDatePurchased(rs.getString("PurchaseDate"));
            PW.setEmpty(rs.getBoolean("empty1"));
            pList.add(PW);
            PW = null;
        }
        rs.close();
        return pList;
    }
  }

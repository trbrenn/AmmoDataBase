/*
 * CasesQuery.java
 *
 * Created on July 1, 2007, 12:48 PM
 *
 * Used to query the Ammo data from the database. 
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CasesEvent;
import BaseClasses.DataBaseConn;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Todd Brenneman
 */
public class CasesQuery {
    private DataBaseConn DBConn;
    private Cases cs;
    private List<Cases> hcs;
    private boolean	 Ignoreempty1;
	
    public CasesQuery(DataBaseConn dbc) {
        DBConn = dbc;
        Ignoreempty1 = false;
    }

    public Cases getLotNumber(String ln) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Cases WHERE LotNumber = \""+ln+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        rs.next();
        cs = new Cases();
        cs.setCaliber(rs.getString("Caliber"));
        cs.setCaseMaker(rs.getString("CaseMaker"));
        cs.setType(rs.getString("Type"));
        cs.setLotCost(rs.getFloat("LotCost"));
        cs.setLotCount(rs.getInt("LotCount"));
        cs.setLotNumber(rs.getString("LotNumber"));
        cs.setCostPerCase(rs.getFloat("CostPerCase"));
        rs.close();
        return cs;
    }

    public List<Cases> getAll() throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Cases";
        if(Ignoreempty1)
            QueryString = QueryString + " where isEmpty = 0";
        ResultSet rs = DBConn.runQuery(QueryString);

        hcs = new ArrayList<Cases>();
        while(rs.next()){
            cs = new Cases();
            cs.setCaliber(rs.getString("Caliber"));
            cs.setCaseMaker(rs.getString("CaseMaker"));
            cs.setType(rs.getString("Type"));
            cs.setLotCost(rs.getFloat("LotCost"));
            cs.setLotCount(rs.getInt("LotCount"));
            cs.setLotNumber(rs.getString("LotNumber"));
            cs.setCostPerCase(rs.getFloat("CostPerCase"));
            hcs.add(cs);
            cs = null;
        }
        rs.close();
        return hcs;
    }

    public List<Cases> getAllCaliber(String cal) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Cases WHERE Caliber = \""+cal+"\"";
        if(Ignoreempty1)
            QueryString = QueryString + " where isEmpty = 0";
        ResultSet rs = DBConn.runQuery(QueryString);

        hcs = new ArrayList<Cases>();
        while(rs.next())
        {
            cs = new Cases();
            cs.setCaliber(rs.getString("Caliber"));
            cs.setCaseMaker(rs.getString("CaseMaker"));
            cs.setType(rs.getString("Type"));
            cs.setLotCost(rs.getFloat("LotCost"));
            cs.setLotCount(rs.getInt("LotCount"));
            cs.setLotNumber(rs.getString("LotNumber"));
            cs.setCostPerCase(rs.getFloat("CostPerCase"));
            hcs.add(cs);
            cs = null;
        }
        rs.close();
        return hcs;
    }
	
    public int insertLotNumber(Cases cs) throws Exception
    {
        String QueryString = "INSERT INTO AmmoDataBase.Cases (CaseMaker,Caliber,LotNumber,";
        QueryString = QueryString + "Type,LotCount,LotCost,CostPerCase) VALUES (";
        QueryString = QueryString + "\""+cs.getCaseMaker()+"\",";
        QueryString = QueryString + "\""+cs.getCaliber()+"\",";
        QueryString = QueryString + "\""+cs.getLotNumber()+"\",";		
        QueryString = QueryString + "\""+cs.getType()+"\",";
        QueryString = QueryString +cs.getLotCount()+",";
        QueryString = QueryString +cs.getLotCost()+",";
        QueryString = QueryString +cs.getCostPerCase()+")";	
        return DBConn.runUpdate(QueryString);
    }

    public int updateLotNumber(Cases cs) throws Exception
    {
        String QueryString = "UPDATE AmmoDataBase.Cases SET ";
        QueryString = QueryString + "CaseMaker = \""+cs.getCaseMaker()+"\",";
        QueryString = QueryString + "Caliber = \""+cs.getCaliber()+"\",";
        QueryString = QueryString + "LotCost = "+cs.getLotCost()+",";
        QueryString = QueryString + "Type = \""+cs.getType()+"\",";
        QueryString = QueryString + "LotCount = "+cs.getLotCount()+",";
        QueryString = QueryString + "LotCost = "+cs.getLotCost()+",";
        QueryString = QueryString + "CostPerCase = "+cs.getCostPerCase()+"";
        QueryString = QueryString + " WHERE LotNumber = \""+cs.getLotNumber()+"\"";
        return DBConn.runUpdate(QueryString);
    }
        
    public int deleteLotNumber(Cases cs) throws Exception
    {
        String QueryString = "Delete from AmmoDataBase.Cases Where LotNumber = \""+cs.getLotNumber()+"\"";
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

    public boolean isIgnoreempty1() {
        return Ignoreempty1;
    }

    public void setIgnoreempty1(boolean Ignoreempty1) {
        this.Ignoreempty1 = Ignoreempty1;
    }
	
    public List<String> getAllCaliber() throws Exception {
        String QueryString = "SELECT distinct Caliber FROM ammodatabase.cases";
        if(Ignoreempty1)
            QueryString = QueryString + "where isEmpty = 0";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Caliber"));
        }
        rs.close();
        return ac;
    }

    public List<String> getAllCaseLotNums() throws Exception {
        String QueryString = "SELECT * FROM ammodatabase.cases";
        String data;
        if(Ignoreempty1)
            QueryString = QueryString + " where isEmpty = 0";
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            data = new String(rs.getString("LotNumber")+", "+rs.getString("Caliber"));
            ac.add(data);
        }
        rs.close();
        return ac;
    }

    public List<String> getMaker() throws Exception {
        String QueryString = "SELECT DISTINCT CaseMaker FROM ammodatabase.cases order by CaseMaker";
        String data;
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("CaseMaker"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getCaliber() throws Exception {
        String QueryString = "SELECT DISTINCT Caliber FROM ammodatabase.cases order by Caliber";
        String data;
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Caliber"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getType() throws Exception {
        String QueryString = "SELECT DISTINCT Type FROM ammodatabase.cases order by Type";
        String data;
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Type"));
        }
        rs.close();
        return ac;
    }
    
    public List<Cases> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.cases Where "+tableName+" = \""+name+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<Cases> cList = new ArrayList<Cases>();
        while(rs.next()){
            cs = new Cases();
            cs.setCaliber(rs.getString("Caliber"));
            cs.setCaseMaker(rs.getString("CaseMaker"));
            cs.setType(rs.getString("Type"));
            cs.setLotCost(rs.getFloat("LotCost"));
            cs.setLotCount(rs.getInt("LotCount"));
            cs.setLotNumber(rs.getString("LotNumber"));
            cs.setCostPerCase(rs.getFloat("CostPerCase"));
            cList.add(cs);
            cs = null;
        }
        rs.close();
        return cList;
    }
}

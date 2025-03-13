/*
 * PrimerQuery.java
 *
 * Created on July 1, 2007, 1:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrimerEvent;
import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Todd Brenneman
 */
public class PrimerQuery {
    private DataBaseConn DBConn;
    private Primer       PR;
    private List<Primer> hpr;
    private boolean	 IgnoreEmpty1;

    public PrimerQuery(DataBaseConn dbc)
    {
        DBConn = dbc;
        IgnoreEmpty1 = false;
    }

    public PrimerQuery(DataBaseConn dbc, boolean ie)
    {
        DBConn = dbc;
        IgnoreEmpty1 = ie;
    }

    public boolean isIgnoreEmpty1() {
        return IgnoreEmpty1;
    }

    public void setIgnoreEmpty1(boolean ignoreEmpty1) {
        IgnoreEmpty1 = ignoreEmpty1;
    }

    public Primer getLotNumber(String ln) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Primer WHERE LotNumber = \""+ln+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        rs.next();
        PR = new Primer();
        PR.setPrimerMaker(rs.getString("PrimerMaker"));
        PR.setPrimerSize(rs.getString("Size"));
        PR.setModelNumber(rs.getString("ModelNumber"));
        PR.setLotNumber(rs.getString("LotNumber"));
        PR.setLotCount(rs.getInt("LotCount"));
        PR.setLotCost(rs.getFloat("LotCost"));
        PR.setCostPerPrimer(rs.getFloat("CostPerPrimer"));
        PR.setEmpty(rs.getBoolean("Empty1"));

        rs.close();
        return PR;
    }

    public List<Primer> getAll() throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Primer";
        if(IgnoreEmpty1)
            QueryString = QueryString + " WHERE Empty1 = 0";

        ResultSet rs = DBConn.runQuery(QueryString);

        hpr = new ArrayList<Primer> ();
        while(rs.next())
        {
            PR = new Primer();
            PR.setPrimerMaker(rs.getString("PrimerMaker"));
            PR.setPrimerSize(rs.getString("Size"));
            PR.setModelNumber(rs.getString("ModelNumber"));
            PR.setLotNumber(rs.getString("LotNumber"));
            PR.setLotCount(rs.getInt("LotCount"));
            PR.setLotCost(rs.getFloat("LotCost"));
            PR.setCostPerPrimer(rs.getFloat("CostPerPrimer"));
            PR.setEmpty(rs.getBoolean("Empty1"));
            hpr.add(PR);
            PR = null;
        }
        rs.close();
        return hpr;
    }

    public List<Primer> getSize(String sz) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.Primer WHERE Size = \"" + sz + "\"";
        if(IgnoreEmpty1)
            QueryString = QueryString + " AND Empty1 = 0";

        ResultSet rs = DBConn.runQuery(QueryString);

        hpr = new ArrayList<Primer> ();
        while(rs.next())
        {
            PR = new Primer();
            PR.setPrimerMaker(rs.getString("PrimerMaker"));
            PR.setPrimerSize(rs.getString("Size"));
            PR.setModelNumber(rs.getString("ModelNumber"));
            PR.setLotNumber(rs.getString("LotNumber"));
            PR.setLotCount(rs.getInt("LotCount"));
            PR.setLotCost(rs.getFloat("LotCost"));
            PR.setCostPerPrimer(rs.getFloat("CostPerPrimer"));
            PR.setEmpty(rs.getBoolean("Empty1"));
            hpr.add(PR);
            PR = null;
        }
        rs.close();
        return hpr;
    }

    public int insertLotNumber(Primer prs) throws Exception
    {
        String QueryString = "INSERT INTO AmmoDataBase.Primer (PrimerMaker, Size, ModelNumber,";
        QueryString = QueryString + "LotNumber, LotCount, LotCost, CostPerPrimer, Empty1) VALUES (";
        QueryString = QueryString + "\""+prs.getPrimerMaker()+"\",";
        QueryString = QueryString + "\""+prs.getPrimerSize()+"\",";
        QueryString = QueryString + "\""+prs.getModelNumber() +"\",";
        QueryString = QueryString + "\""+prs.getLotNumber()+"\",";
        QueryString = QueryString + ""+prs.getLotCount()+",";
        QueryString = QueryString + ""+prs.getLotCost()+",";
        QueryString = QueryString + ""+prs.getCostPerPrimer()+",";
        if(prs.isEmpty())
            QueryString = QueryString + "1)";
        else
            QueryString = QueryString + "0)";

        return DBConn.runUpdate(QueryString);
    }

    public int updateLotNumber(Primer prs) throws Exception
    {
        String QueryString = "UPDATE AmmoDataBase.Primer SET ";
        QueryString = QueryString + "PrimerMaker = \"" + prs.getPrimerMaker()+"\", ";
        QueryString = QueryString + "Size = \"" + prs.getPrimerSize()+"\",";
        QueryString = QueryString + "ModelNumber = \"" + prs.getModelNumber() +"\", ";
        QueryString = QueryString + "LotCount = " + prs.getLotCount()+", ";
        QueryString = QueryString + "LotCost = " + prs.getLotCost()+", ";
        QueryString = QueryString + "CostPerPrimer = " + prs.getCostPerPrimer()+", ";
        if(prs.isEmpty())
            QueryString = QueryString + "Empty1 = 1";
        else
            QueryString = QueryString + "Empty1 = 0";

        QueryString = QueryString + " Where LotNumber = \"" + prs.getLotNumber()+"\"";

        return DBConn.runUpdate(QueryString);
    }

    public int deleteLotNumber(Primer prs) throws Exception
    {
        String QueryString = "Delete from AmmoDataBase.Primer where LotNumber = \"" + prs.getLotNumber()+"\"";
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

    public List<String> getAllPrimerLotNums() throws Exception {
        String QueryString = "SELECT * FROM ammodatabase.Primer";
        if(IgnoreEmpty1)
            QueryString = QueryString + " WHERE empty1 = 0";

        ResultSet rs = DBConn.runQuery(QueryString);

        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            String data = new String(rs.getString("LotNumber")+", "+rs.getString("Size"));
            ac.add(data);
        }
        rs.close();
        return ac;
    }
    
    public List<String> getMaker() throws Exception {
        String QueryString = "SELECT DISTINCT PrimerMaker FROM ammodatabase.primer order by PrimerMaker";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("PrimerMaker"));
        }
        rs.close();
        return ac;
    }

    public List<String> getSize() throws Exception {
        String QueryString = "SELECT DISTINCT Size FROM ammodatabase.primer order by Size";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Size"));
        }
        rs.close();
        return ac;
    }

    public List<String> getModel() throws Exception {
        String QueryString = "SELECT DISTINCT ModelNumber FROM ammodatabase.primer order by ModelNumber";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("ModelNumber"));
        }
        rs.close();
        return ac;
    }

    public List<Primer> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.Primer Where "+tableName+" = \""+name+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<Primer> pList = new ArrayList<Primer>();
        while(rs.next()){
            PR = new Primer();
            PR.setPrimerMaker(rs.getString("PrimerMaker"));
            PR.setPrimerSize(rs.getString("Size"));
            PR.setModelNumber(rs.getString("ModelNumber"));
            PR.setLotNumber(rs.getString("LotNumber"));
            PR.setLotCount(rs.getInt("LotCount"));
            PR.setLotCost(rs.getFloat("LotCost"));
            PR.setCostPerPrimer(rs.getFloat("CostPerPrimer"));
            PR.setEmpty(rs.getBoolean("Empty1"));
            pList.add(PR);
            PR = null;
 
        }
        rs.close();
        return pList;
    }}

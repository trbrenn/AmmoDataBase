/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BulletMoldEvent;

import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BulletMoldQuery {
    private DataBaseConn DBConn;
    private BulletMold mold;
    private List<BulletMold> hmold;

    public BulletMoldQuery(DataBaseConn dbc) {
	DBConn = dbc;
    }

    public BulletMold getMoldID(int ln) throws Exception
    {
	String QueryString = "SELECT * FROM AmmoDataBase.bulletmold WHERE ID = \""+ln+"\"";
	ResultSet rs = DBConn.runQuery(QueryString);
		
	rs.next();
	mold = new BulletMold();
	mold.setID(rs.getInt("ID"));
	mold.setMaker(rs.getString("Maker"));
	mold.setNumber(rs.getString("Number"));
	mold.setDescription(rs.getString("Description"));
	mold.setDiameter(rs.getFloat("Diameter"));
	mold.setWeight(rs.getFloat("Weight"));
	mold.setTopPunch(rs.getString("TopPunch"));
	mold.setGasCheck(rs.getBoolean("GasCheck"));
	rs.close();
		
	return mold;
    }

    public BulletMold getMoldNumber(String ln) throws Exception
    {
	String QueryString = "SELECT * FROM AmmoDataBase.bulletmold WHERE Number = \""+ln+"\"";
	ResultSet rs = DBConn.runQuery(QueryString);
		
	rs.next();
	mold = new BulletMold();
	mold.setID(rs.getInt("ID"));
	mold.setMaker(rs.getString("Maker"));
	mold.setNumber(rs.getString("Number"));
	mold.setDescription(rs.getString("Description"));
	mold.setDiameter(rs.getFloat("Diameter"));
	mold.setWeight(rs.getFloat("Weight"));
	mold.setTopPunch(rs.getString("TopPunch"));
	mold.setGasCheck(rs.getBoolean("GasCheck"));
	rs.close();
		
	return mold;
    }

    public List<BulletMold> getAll() throws Exception {
	String QueryString = "SELECT * FROM AmmoDataBase.bulletmold";
	ResultSet rs = DBConn.runQuery(QueryString);
		
	hmold = new ArrayList<BulletMold> ();
	while(rs.next())
	{
            mold = new BulletMold();
            mold.setID(rs.getInt("ID"));
            mold.setMaker(rs.getString("Maker"));
            mold.setNumber(rs.getString("Number"));
            mold.setDescription(rs.getString("Description"));
            mold.setDiameter(rs.getFloat("Diameter"));
            mold.setWeight(rs.getFloat("Weight"));
            mold.setTopPunch(rs.getString("TopPunch"));
            mold.setGasCheck(rs.getBoolean("GasCheck"));
			
            hmold.add(mold);
            mold = null;
	}
		
	rs.close();
	return hmold;
    }

    public int insertMoldID(BulletMold bm)  throws Exception {
        String QueryString = "INSERT INTO AmmoDataBase.bulletmold (Maker,Number,Description,Diameter,Weight,TopPunch,GasCheck) VALUES (";
	QueryString = QueryString + "\""+bm.getMaker() + "\",";
	QueryString = QueryString + "\"" + bm.getNumber() + "\",";
	QueryString = QueryString + "\"" + bm.getDescription() + "\",";
        QueryString = QueryString + bm.getDiameter() + ",";
	QueryString = QueryString + bm.getWeight() + ",";
	QueryString = QueryString + bm.getTopPunch();
	if(bm.isGasCheck())
            QueryString = QueryString + "1)";
	else
            QueryString = QueryString + "0)";
						
        return DBConn.runUpdate(QueryString);
    }
	
    public int updateMoldID(BulletMold bm)  throws Exception {
	String QueryString = "UPDATE AmmoDataBase.bulletmold SET ";
	QueryString = QueryString + "Maker = \"" + bm.getMaker() + "\",";
	QueryString = QueryString + "Number = \"" + bm.getNumber() + "\",";
	QueryString = QueryString + "Description = \"" + bm.getDescription() + "\",";
        QueryString = QueryString + "Diameter = " + bm.getDiameter() + ",";
	QueryString = QueryString + "Weight = " + bm.getWeight() + ",";
	QueryString = QueryString + bm.getTopPunch();
	if(bm.isGasCheck())
            QueryString = QueryString + "GasCheck = 1";
	else
            QueryString = QueryString + "GasCheck = 0";
				
	QueryString = QueryString + " WHERE ID = \"" + bm.getID() + "\"";
		
	return DBConn.runUpdate(QueryString);		
    }
	
    public int deleteMoldID(int i) throws Exception
    {
        String QueryString = "Delete from AmmoDataBase.BulletMold where ID = \""+i+"\"";
        return DBConn.runUpdate(QueryString);               
    }
    
    public void connect() throws Exception {
	DBConn.connect();
    }
	
    public void close() throws Exception
    {
	DBConn.close();
    }	
    
    public List<String> getMaker() throws Exception {
        String QueryString = "SELECT DISTINCT Maker FROM ammodatabase.bulletmold order by Maker";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Maker"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getDescription() throws Exception {
        String QueryString = "SELECT DISTINCT Description FROM ammodatabase.bulletmold order by Description";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Description"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getDiameter() throws Exception {
        String QueryString = "SELECT DISTINCT Diameter FROM ammodatabase.bulletmold order by Diameter";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Diameter"));
        }
        rs.close();
        return ac;
    }
    
    public List<BulletMold> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.bulletmold Where "+tableName+" like \""+name+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<BulletMold> pList = new ArrayList<BulletMold>();
        while(rs.next()){
            mold = new BulletMold();
            mold.setID(rs.getInt("ID"));
            mold.setMaker(rs.getString("Maker"));
            mold.setNumber(rs.getString("Number"));
            mold.setDescription(rs.getString("Description"));
            mold.setDiameter(rs.getFloat("Diameter"));
            mold.setWeight(rs.getFloat("Weight"));
            mold.setTopPunch(rs.getString("TopPunch"));
            mold.setGasCheck(rs.getBoolean("GasCheck"));
			
            pList.add(mold);
            mold = null;
	}
        rs.close();
        return pList;
    }    
}

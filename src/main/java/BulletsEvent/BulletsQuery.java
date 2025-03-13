/*
 * BulletsQuery.java
 *
 * Created on July 1, 2007, 12:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
//	+----------------------+-------------+------+-----+---------+-------+
//	| Field                | Type        | Null | Key | Default | Extra |
//	+----------------------+-------------+------+-----+---------+-------+
//	| BulletMaker          | varchar(50) |      |     |         |       |
//	| BallisticCoefficient | float       |      |     | 0       |       |
//	| Caliber              | varchar(50) |      |     |         |       |
//	| Weight               | varchar(20) |      |     |         |       |
//	| Description          | varchar(50) |      |     |         |       |
//	| LotNumber            | varchar(50) |      | PRI |         |       |
//	| LotCount             | int(11)     |      |     | 0       |       |
//	| LotCost              | float       |      |     | 0       |       |
//	| CostPerBullet        | float       |      |     | 0       |       |
//	| CastAlloy            | varchar(35) |      |     |         |       |
//	| Empty                | tinyint(1)  |      |     | 0       |       |
//      | MoldNumber           | integer     | yes  |     |         |       |
//	+----------------------+-------------+------+-----+---------+-------+

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BulletsEvent;

import BaseClasses.DataBaseConn;
import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Todd Brenneman
 */
public class BulletsQuery {
    private DataBaseConn    DBConn;
    private Bullets         bul;
    private List            <Bullets> lbul;
    private boolean         IgnoreEmpty;

    public BulletsQuery(DataBaseConn dbc) {
	DBConn = dbc;
	this.IgnoreEmpty = false;
    }

    public BulletsQuery(DataBaseConn dbc, boolean ignoreEmpty) {
	DBConn = dbc;
	this.IgnoreEmpty = ignoreEmpty;
    }

    public boolean isIgnoreEmpty() {
	return IgnoreEmpty;
    }

    public void setIgnoreEmpty(boolean ignoreEmpty) {
	this.IgnoreEmpty = ignoreEmpty;
    }
	
    public Bullets getLotNumber(String ln) throws Exception {
	String QueryString = "SELECT * FROM AmmoDataBase.Bullets WHERE LotNumber = \""+ln+"\"";
	ResultSet rs = DBConn.runQuery(QueryString);
		
	rs.next();
	bul = new Bullets();
	bul.setBulletMaker(rs.getString("BulletMaker"));
	bul.setBC(rs.getFloat("BallisticCoefficient"));
	bul.setCaliber(rs.getString("Caliber"));
	bul.setWeight(rs.getString("Weight"));
	bul.setDescription(rs.getString("Description"));
	bul.setLotNumber(rs.getString("LotNumber"));
	bul.setLotCount(rs.getInt("LotCount"));
	bul.setLotCost(rs.getFloat("LotCost"));
	bul.setCostPerBullet(rs.getFloat("CostPerBullet"));
	bul.setCastAlloy(rs.getString("CastAlloy"));
	bul.setEmpty(rs.getBoolean("Empty1"));
	bul.setMoldNumber(rs.getInt("MoldNumber"));
		
	rs.close();
	return bul;
    }

    public List<Bullets> getAll() throws Exception {
	String QueryString = "SELECT * FROM AmmoDataBase.Bullets";
	if(IgnoreEmpty)
            QueryString = QueryString + " WHERE Empty1 = 0";
	QueryString = QueryString + " order by LotNumber";
        //System.out.println("QueryString = "+QueryString);
        ResultSet rs = DBConn.runQuery(QueryString);
		
        lbul = new ArrayList<Bullets> ();
        while(rs.next()) {
            bul = new Bullets();
            bul.setBulletMaker(rs.getString("BulletMaker"));
            bul.setBC(rs.getFloat("BallisticCoefficient"));
            bul.setCaliber(rs.getString("Caliber"));
            bul.setWeight(rs.getString("Weight"));
            bul.setDescription(rs.getString("Description"));
            bul.setLotNumber(rs.getString("LotNumber"));
            bul.setLotCount(rs.getInt("LotCount"));
            bul.setLotCost(rs.getFloat("LotCost"));
            bul.setCostPerBullet(rs.getFloat("CostPerBullet"));
            bul.setCastAlloy(rs.getString("CastAlloy"));
            bul.setEmpty(rs.getBoolean("Empty1"));
            bul.setMoldNumber(rs.getInt("MoldNumber"));
			
            lbul.add(bul);
            bul = null;
        }
		
        rs.close();
        return lbul;
    }

    public List<Bullets> getAllCaliber(String caliber) throws Exception {
	String QueryString = "SELECT * FROM AmmoDataBase.Bullets order by LotNumber WHERE Caliber = \""+caliber+"\"";
	if(IgnoreEmpty)
            QueryString = QueryString + " AND Empty = 0";
		
	ResultSet rs = DBConn.runQuery(QueryString);
		
	lbul = new ArrayList<Bullets>();
	while(rs.next())
	{
            bul = new Bullets();
            bul.setBulletMaker(rs.getString("BulletMaker"));
            bul.setBC(rs.getFloat("BallisticCoefficient"));
            bul.setCaliber(rs.getString("Caliber"));
            bul.setWeight(rs.getString("Weight"));
            bul.setDescription(rs.getString("Description"));
            bul.setLotNumber(rs.getString("LotNumber"));
            bul.setLotCount(rs.getInt("LotCount"));
            bul.setLotCost(rs.getFloat("LotCost"));
            bul.setCostPerBullet(rs.getFloat("CostPerBullet"));
            bul.setCastAlloy(rs.getString("CastAlloy"));
            bul.setEmpty(rs.getBoolean("Empty1"));
            bul.setMoldNumber(rs.getInt("MoldNumber"));
			
            lbul.add(bul);
            bul = null;
	}
		
	rs.close();
	return lbul;
    }

    public int insertLotNumber(Bullets bullet) throws Exception {
	String QueryString = "INSERT INTO AmmoDataBase.Bullets (BulletMaker,BallisticCoefficient,Caliber,Weight,Description,LotNumber,LotCount,";
	QueryString = QueryString + "LotCost,CostPerBullet,CastAlloy,Empty1,MoldNumber) VALUES (";
	QueryString = QueryString + "\"" + bullet.getBulletMaker() + "\",";
	QueryString = QueryString + "" + bullet.getBC() + ",";
	QueryString = QueryString + "\"" + bullet.getCaliber() + "\",";
	QueryString = QueryString + "\"" + bullet.getWeight() + "\",";
	QueryString = QueryString + "\"" + bullet.getDescription() + "\",";
	QueryString = QueryString + "\"" + bullet.getLotNumber() + "\",";
	QueryString = QueryString + "" + bullet.getLotCount() + ",";
	QueryString = QueryString + "" + bullet.getLotCost() + ",";
	QueryString = QueryString + "" + bullet.getCostPerBullet() + ",";
	QueryString = QueryString + "\"" + bullet.getCastAlloy() + "\",";
        QueryString = QueryString + "\"" + bullet.getMoldNumber() + "\",";
	if(bullet.isEmpty())
            QueryString = QueryString + "1)";
	else
            QueryString = QueryString + "0)";
 
	return DBConn.runUpdate(QueryString);
    }

    public int updateLotNumber(Bullets bullet) throws Exception {
	String QueryString = "UPDATE AmmoDataBase.Bullets SET ";
	QueryString = QueryString + "BulletMaker = \"" + bullet.getBulletMaker() + "\",";
	QueryString = QueryString + "BallisticCoefficient = " + bullet.getBC() + ",";
	QueryString = QueryString + "Caliber = \"" + bullet.getCaliber() + "\",";
	QueryString = QueryString + "Weight = \"" + bullet.getWeight() + "\",";
	QueryString = QueryString + "Description = \"" + bullet.getDescription() + "\",";
	QueryString = QueryString + "LotCount = " + bullet.getLotCount() + ",";
	QueryString = QueryString + "LotCost = " + bullet.getLotCost() + ",";
	QueryString = QueryString + "CostPerBullet = " + bullet.getCostPerBullet() + ",";
	QueryString = QueryString + "CastAlloy = \"" + bullet.getCastAlloy() + "\",";
        QueryString = QueryString + "MoldNumber = \"" + bullet.getMoldNumber() + "\",";
	if(bullet.isEmpty())
            QueryString = QueryString + "Empty1 = 1";
	else
            QueryString = QueryString + "Empty1 = 0";
		
	QueryString = QueryString + " WHERE LotNumber = \"" + bullet.getLotNumber() + "\"";
		
	return DBConn.runUpdate(QueryString);
    }
        
    public int deleteLotNumber(Bullets bullet) throws Exception {
        String QueryString = "Delete from AmmoDataBase.Bullets where LotNumber = \""+bullet.getLotNumber()+"\"";
        return DBConn.runUpdate(QueryString);
    }
    
    public void connect() throws Exception {
	DBConn.connect();
    }
	
    public void close() throws Exception {
	DBConn.close();
    }
	
    public List<String> getAllBulletLotNums() throws Exception {
	String QueryString = "SELECT * FROM ammodatabase.bullets";
	if(IgnoreEmpty)
            QueryString = QueryString + " WHERE Empty1 = 0";
	ResultSet rs = DBConn.runQuery(QueryString);

	List<String> ac = new ArrayList<String>();
	while(rs.next()) {
            String data = new String(rs.getString("LotNumber")+", "+rs.getString("Caliber")+" "+rs.getString("Weight")+" "+rs.getString("Description"));
            ac.add(data);
	}
	rs.close();
	return ac;
    }
    
    public List<String> getAllBulletMakers() throws Exception {
        String QueryString = "SELECT DISTINCT BulletMaker FROM ammodatabase.bullets order by BulletMaker";
        ResultSet rs = DBConn.runQuery(QueryString);
        
        List<String> ac = new ArrayList<String>();
	while(rs.next()) {
            String data = new String(rs.getString("BulletMaker"));
            ac.add(data);
        }
        rs.close();
        return ac;
    }
    
    public List<String> getAllCaliber() throws Exception {
        String QueryString = "SELECT DISTINCT Caliber FROM ammodatabase.bullets order by caliber";
        ResultSet rs = DBConn.runQuery(QueryString);
        
        List<String> ac = new ArrayList<String>();
	while(rs.next()) {
            String data = new String(rs.getString("Caliber"));
            ac.add(data);
        }
        rs.close();
        return ac;
    }
    
    public List<String> getAllWeight() throws Exception {
        String QueryString = "SELECT DISTINCT Weight FROM ammodatabase.bullets order by Weight";
        ResultSet rs = DBConn.runQuery(QueryString);
        
        List<String> ac = new ArrayList<String>();
	while(rs.next()) {
            String data = new String(rs.getString("Weight"));
            ac.add(data);
        }
        rs.close();
        return ac;
    }
    
    public List<String> getAllDescription() throws Exception {
        String QueryString = "SELECT DISTINCT Description FROM ammodatabase.bullets order by Description";
        ResultSet rs = DBConn.runQuery(QueryString);
        
        List<String> ac = new ArrayList<String>();
	while(rs.next()) {
            String data = new String(rs.getString("Description"));
            ac.add(data);
        }
        rs.close();
        return ac;
    }
    
    public List<Bullets> getList(String tableName, String name) throws Exception {
        String QueryString = "SELECT * FROM ammodatabase.bullets Where "+tableName+" = \""+name+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);
        
        List<Bullets> ac = new ArrayList<Bullets>();
	        
        while(rs.next()) {
            bul = new Bullets();
            bul.setBulletMaker(rs.getString("BulletMaker"));
            bul.setBC(rs.getFloat("BallisticCoefficient"));
            bul.setCaliber(rs.getString("Caliber"));
            bul.setWeight(rs.getString("Weight"));
            bul.setDescription(rs.getString("Description"));
            bul.setLotNumber(rs.getString("LotNumber"));
            bul.setLotCount(rs.getInt("LotCount"));
            bul.setLotCost(rs.getFloat("LotCost"));
            bul.setCostPerBullet(rs.getFloat("CostPerBullet"));
            bul.setCastAlloy(rs.getString("CastAlloy"));
            bul.setEmpty(rs.getBoolean("Empty1"));
            bul.setMoldNumber(rs.getInt("MoldNumber"));
			
            ac.add(bul);
            bul = null;
        }
		
        rs.close();
        return ac;        
    }
}

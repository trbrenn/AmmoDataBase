/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * AmmoListQuery.java
 *
 * Created on July 1, 2007, 11:27 AM
 *
 * This class handles all database functions for the AmmoList object.  
 */

package ReloadEvent;

import BaseClasses.CalendarTest;
import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Todd Brenneman
 */
public class ReloadListQuery {
    private DataBaseConn DBConn;
    private ReloadList ammo;
    private List<ReloadList> hammo;
    private CalendarTest cal;
    private boolean IgnoreEmpty = true;

    // The constructor requires a DataBaseConn to already be instancated and ready for use. 
    public ReloadListQuery(DataBaseConn dbc) {
        DBConn = dbc;
	cal = new CalendarTest();
    }

    public void connect() throws Exception {
        DBConn.connect();
    }
	
    // This function will return an AmmoList object for a specific LotNumber. 
    public ReloadList getLotNumber(String ln) throws Exception {
	String QueryString = "SELECT * FROM AmmoDataBase.AmmoList WHERE LotNumber = \""+ln+"\"";

	ResultSet rs = DBConn.runQuery(QueryString);
		
	rs.next();
	ammo = new ReloadList();
	ammo.setLotNumber(rs.getString("LotNumber"));
	ammo.setCaliber(rs.getString("Caliber"));
	ammo.setLoadDate(cal.convertDate(rs.getString("Date")));	
	ammo.setBulletLotNumber(rs.getString("BulletLotNumber"));
	ammo.setCaseLotNumber(rs.getString("CaseLotNumber"));
	ammo.setPowderLotNumber(rs.getString("PowderLotNumber"));
	ammo.setPowderWeight(rs.getFloat("PowderWeight"));
	ammo.setPrimerLotNumber(rs.getString("PrimerLotNumber"));
	ammo.setTimesLoaded(rs.getInt("TimesLoaded"));
	ammo.setCaseLength(rs.getFloat("CaseLength"));
	ammo.setOverAllLength(rs.getFloat("OverAllLength"));
	ammo.setFireArmID(rs.getString("FireArmID"));
	ammo.setChronoGraphDataID(rs.getString("ChronoGraphDataID"));
	ammo.setCrimp(rs.getString("Crimp"));
	ammo.setCount(rs.getInt("Count"));
	ammo.setNotes(rs.getString("Notes"));
	ammo.setManufacturer(rs.getString("Manufacturer"));
		
	rs.close();
	return ammo;
    }
	
    // This function will return a hashtable containing all the AmmoList items in the database.
    public List<ReloadList> getAll() throws Exception
    {

        String QueryString = "SELECT * FROM AmmoDataBase.AmmoList";
        if(IgnoreEmpty)
            QueryString = QueryString + " WHERE isEmpty = 0";
        
        QueryString = QueryString + " order by date";

	ResultSet rs = DBConn.runQuery(QueryString);
		
	hammo = new ArrayList<ReloadList> ();
	while(rs.next())
	{
            ammo = new ReloadList();
            ammo.setLotNumber(rs.getString("LotNumber"));
            ammo.setCaliber(rs.getString("Caliber"));
            ammo.setLoadDate(cal.convertDate(rs.getString("Date")));
            ammo.setBulletLotNumber(rs.getString("BulletLotNumber"));
            ammo.setCaseLotNumber(rs.getString("CaseLotNumber"));
            ammo.setPowderLotNumber(rs.getString("PowderLotNumber"));
            ammo.setPowderWeight(rs.getFloat("PowderWeight"));
            ammo.setPrimerLotNumber(rs.getString("PrimerLotNumber"));
            ammo.setTimesLoaded(rs.getInt("TimesLoaded"));
            ammo.setCaseLength(rs.getFloat("CaseLength"));
            ammo.setOverAllLength(rs.getFloat("OverAllLength"));
            ammo.setFireArmID(rs.getString("FireArmID"));
            ammo.setChronoGraphDataID(rs.getString("ChronoGraphDataID"));
            ammo.setCrimp(rs.getString("Crimp"));
            ammo.setCount(rs.getInt("Count"));
            ammo.setNotes(rs.getString("Notes"));
            ammo.setManufacturer(rs.getString("Manufacturer"));
			
            hammo.add(ammo);
            ammo = null;
	}
	rs.close();	
	return hammo;
    }

    // This fuction is not really used but it will return a hashtable of all AmmoList items with the specified caliber.
    public List<ReloadList> getAllCaliber(String caliber) throws Exception {
	String QueryString = "SELECT * FROM AmmoDataBase.AmmoList WHERE Caliber = \""+caliber+"\"";
        if(IgnoreEmpty)
            QueryString = QueryString + " and isEmpty = 0";
	ResultSet rs = DBConn.runQuery(QueryString);
		
	hammo = new ArrayList<ReloadList> ();
	while(rs.next())
	{
            ammo = new ReloadList();
            ammo.setLotNumber(rs.getString("LotNumber"));
            ammo.setCaliber(rs.getString("Caliber"));
            ammo.setLoadDate(cal.convertDate(rs.getString("Date")));
            ammo.setBulletLotNumber(rs.getString("BulletLotNumber"));
            ammo.setCaseLotNumber(rs.getString("CaseLotNumber"));
            ammo.setPowderLotNumber(rs.getString("PowderLotNumber"));
            ammo.setPowderWeight(rs.getFloat("PowderWeight"));
            ammo.setPrimerLotNumber(rs.getString("PrimerLotNumber"));
            ammo.setTimesLoaded(rs.getInt("TimesLoaded"));
            ammo.setCaseLength(rs.getFloat("CaseLength"));
            ammo.setOverAllLength(rs.getFloat("OverAllLength"));
            ammo.setFireArmID(rs.getString("FireArmID"));
            ammo.setChronoGraphDataID(rs.getString("ChronoGraphDataID"));
            ammo.setCrimp(rs.getString("Crimp"));
            ammo.setCount(rs.getInt("Count"));
            ammo.setNotes(rs.getString("Notes"));
            ammo.setManufacturer(rs.getString("Manufacturer"));
			
            hammo.add(ammo);
            ammo = null;
	}
	rs.close();
	return hammo;
    }

    // This functin inserts a new ammolist item. 
    public int insertLotNumber(ReloadList ammo) throws Exception {
	String QueryString = "INSERT INTO AmmoDataBase.AmmoList (LotNumber,Caliber,Date,BulletLotNumber,CaseLotNumber,PowderLotNumber,PowderWeight,";
	QueryString = QueryString + "PrimerLotNumber,TimesLoaded,CaseLength,OverAllLength,FireArmID,ChronoGraphDataID,Crimp,Count,Notes,Manufacturer) VALUES (";
	QueryString = QueryString + "\"" + ammo.getLotNumber() + "\","; 				//required
	QueryString = QueryString + "\"" + ammo.getCaliber() + "\",";   				//required
	QueryString = QueryString + "\"" + cal.convertDate(ammo.getLoadDate()) + "\","; //required
	QueryString = QueryString + "\"" + ammo.getBulletLotNumber() + "\",";			//not required
	QueryString = QueryString + "\"" + ammo.getCaseLotNumber() + "\",";				//not required
	QueryString = QueryString + "\"" + ammo.getPowderLotNumber() + "\",";			//not required
	QueryString = QueryString + "" + ammo.getPowderWeight() + ",";					//required
	QueryString = QueryString + "\"" + ammo.getPrimerLotNumber() + "\",";			//not required
	QueryString = QueryString + "" + ammo.getTimesLoaded() + ",";					//required
	QueryString = QueryString + "" + ammo.getCaseLength() + ",";					//required
	QueryString = QueryString + "" + ammo.getOverAllLength() + ",";					//required
	QueryString = QueryString + "\"" + ammo.getFireArmID() + "\",";					//not required
	QueryString = QueryString + "\"" + ammo.getChronoGraphDataID() + "\",";			//not required
	QueryString = QueryString + "\"" + ammo.getCrimp() + "\",";						//not required
	QueryString = QueryString + "" + ammo.getCount() + ",";							//required
	QueryString = QueryString + "\"" + ammo.getNotes() + "\",";						//not required
	QueryString = QueryString + "\"" + ammo.getManufacturer() + "\")";				//required

        return DBConn.runUpdate(QueryString);
    }

    // this function updates a specific ammolist item in the datadase.
    public int updateLotNumber(ReloadList ammo) throws Exception {
	String QueryString = "UPDATE AmmoDataBase.AmmoList SET ";
	QueryString = QueryString + "Caliber = \"" + ammo.getCaliber() + "\",";
	QueryString = QueryString + "Date = \"" + cal.convertDate(ammo.getLoadDate()) + "\",";
	QueryString = QueryString + "BulletLotNumber = \"" + ammo.getBulletLotNumber() + "\",";
	QueryString = QueryString + "CaseLotNumber = \"" + ammo.getCaseLotNumber() + "\",";
	QueryString = QueryString + "PowderLotNumber = \"" + ammo.getPowderLotNumber() + "\",";
	QueryString = QueryString + "PowderWeight = " + ammo.getPowderWeight() + ",";
	QueryString = QueryString + "PrimerLotNumber = \"" + ammo.getPrimerLotNumber() + "\",";
	QueryString = QueryString + "TimesLoaded = " + ammo.getTimesLoaded() + ",";
	QueryString = QueryString + "CaseLength = " + ammo.getCaseLength() + ",";
	QueryString = QueryString + "OverAllLength = " + ammo.getOverAllLength() + ",";
	QueryString = QueryString + "FireArmID = \"" + ammo.getFireArmID() + "\",";
	QueryString = QueryString + "ChronoGraphDataID = \"" + ammo.getChronoGraphDataID() + "\",";
	QueryString = QueryString + "Crimp = \"" + ammo.getCrimp() + "\",";
	QueryString = QueryString + "Count = " + ammo.getCount() + ",";
	QueryString = QueryString + "Notes = \"" + ammo.getNotes() + "\",";
	QueryString = QueryString + "Manufacturer = \"" + ammo.getManufacturer() + "\"";
	QueryString = QueryString + " WHERE LotNumber = \""+ ammo.getLotNumber() +"\"";
		
        return DBConn.runUpdate(QueryString);
    }
        
    // This function will remove an ammoList item from the database.
    public int deleteLotNumber(ReloadList ammo) throws Exception
    {
        String QueryString = "Delete from AmmoDataBase.AmmoList where LotNumber = \""+ammo.getLotNumber()+"\"";
        return DBConn.runUpdate(QueryString);               
    }
    
    public int deleteLotNumber(String ammo) throws Exception
    {
        String QueryString = "Delete from AmmoDataBase.AmmoList where LotNumber = \""+ammo+"\"";
        return DBConn.runUpdate(QueryString);               
    }
    
    public void close() throws Exception
    {
	DBConn.close();
    }
    
    public List<String> getAllLotNumbers() throws Exception 
    {
	String QueryString = "SELECT LotNumber FROM AmmoDataBase.AmmoList";
	ResultSet rs = DBConn.runQuery(QueryString);
		
	List<String> ammoItem = new ArrayList<String>();
	while(rs.next())
	{
            ammoItem.add(rs.getString("LotNumber"));
	}
	rs.close();
    	return ammoItem;
    }

    public boolean isIgnoreEmpty() {
        return IgnoreEmpty;
    }

    public void setIgnoreEmpty(boolean IgnoreEmpty) {
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public List<String> getCaliber() throws Exception {
        String QueryString = "SELECT DISTINCT Caliber FROM ammodatabase.ammolist";
        if(IgnoreEmpty)
            QueryString = QueryString + " where isempty = 0";
        QueryString = QueryString + " order by Caliber";

        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Caliber"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getDate() throws Exception {
        String QueryString = "SELECT  distinct year(date) FROM ammodatabase.ammolist";
        if(IgnoreEmpty)
            QueryString = QueryString + " where isempty = 0";
        QueryString = QueryString + " order by year(date)";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        
        while(rs.next())
        {
            ac.add(rs.getString("year(date)"));
        }
        rs.close();
        ac.sort(Comparator.naturalOrder());
        return ac;
    }
    
    public List<ReloadList> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.ammolist Where "+tableName+" = \""+name+"\"";
        if(IgnoreEmpty)
            QueryString = QueryString + " AND isempty = 0";
        QueryString = QueryString + " order by date";
        ResultSet rs = DBConn.runQuery(QueryString);


        List<ReloadList> rList = new ArrayList<ReloadList>();
        while(rs.next()){
            ammo = new ReloadList();
            ammo.setLotNumber(rs.getString("LotNumber"));
            ammo.setCaliber(rs.getString("Caliber"));
            ammo.setLoadDate(cal.convertDate(rs.getString("Date")));
            ammo.setBulletLotNumber(rs.getString("BulletLotNumber"));
            ammo.setCaseLotNumber(rs.getString("CaseLotNumber"));
            ammo.setPowderLotNumber(rs.getString("PowderLotNumber"));
            ammo.setPowderWeight(rs.getFloat("PowderWeight"));
            ammo.setPrimerLotNumber(rs.getString("PrimerLotNumber"));
            ammo.setTimesLoaded(rs.getInt("TimesLoaded"));
            ammo.setCaseLength(rs.getFloat("CaseLength"));
            ammo.setOverAllLength(rs.getFloat("OverAllLength"));
            ammo.setFireArmID(rs.getString("FireArmID"));
            ammo.setChronoGraphDataID(rs.getString("ChronoGraphDataID"));
            ammo.setCrimp(rs.getString("Crimp"));
            ammo.setCount(rs.getInt("Count"));
            ammo.setNotes(rs.getString("Notes"));
            ammo.setManufacturer(rs.getString("Manufacturer"));
			
            rList.add(ammo);
            ammo = null;
        }
        rs.close();
        return rList;
    }  
    
    public List<ReloadList> getListLike(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.ammolist Where "+tableName+" like \""+name+"%\"";
        if(IgnoreEmpty)
            QueryString = QueryString + " AND isempty = 0"; 
        QueryString = QueryString + " order by date";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<ReloadList> rList = new ArrayList<ReloadList>();
        while(rs.next()){
            ammo = new ReloadList();
            ammo.setLotNumber(rs.getString("LotNumber"));
            ammo.setCaliber(rs.getString("Caliber"));
            ammo.setLoadDate(cal.convertDate(rs.getString("Date")));
            ammo.setBulletLotNumber(rs.getString("BulletLotNumber"));
            ammo.setCaseLotNumber(rs.getString("CaseLotNumber"));
            ammo.setPowderLotNumber(rs.getString("PowderLotNumber"));
            ammo.setPowderWeight(rs.getFloat("PowderWeight"));
            ammo.setPrimerLotNumber(rs.getString("PrimerLotNumber"));
            ammo.setTimesLoaded(rs.getInt("TimesLoaded"));
            ammo.setCaseLength(rs.getFloat("CaseLength"));
            ammo.setOverAllLength(rs.getFloat("OverAllLength"));
            ammo.setFireArmID(rs.getString("FireArmID"));
            ammo.setChronoGraphDataID(rs.getString("ChronoGraphDataID"));
            ammo.setCrimp(rs.getString("Crimp"));
            ammo.setCount(rs.getInt("Count"));
            ammo.setNotes(rs.getString("Notes"));
            ammo.setManufacturer(rs.getString("Manufacturer"));
			
            rList.add(ammo);
            ammo = null;
        }
        rs.close();
        return rList;
    }
}

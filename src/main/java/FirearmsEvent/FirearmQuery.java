/*
 * FirearmQuery.java
 *
 * Created on July 15, 2007, 5:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirearmsEvent;
import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Todd Brenneman
 */
public class FirearmQuery 
{
    DataBaseConn dbc;
    
    /** Creates a new instance of FirearmQuery */
    public FirearmQuery(DataBaseConn dbc1) 
    {
        dbc = dbc1; 
    }
   
    // Find a single gun in the database.
    public Firearm getFirearmData(String SN) throws Exception
    {
        Firearm gun = new Firearm();
        
	try 
	{
            String QueryString = "SELECT * FROM BoundBook.BoundBookPage WHERE SerialNumber = \""+SN+"\"";

            ResultSet rs = dbc.runQuery(QueryString);

            rs.next();
            gun.setManufacturer(rs.getString("Manufacturer"));
            gun.setModelName(rs.getString("ModelName"));
            gun.setSerialNumber(rs.getString("SerialNumber"));	
            gun.setType(rs.getString("Type"));
            gun.setCaliber(rs.getString("Caliber"));
            gun.setPicture(rs.getString("Picture"));
            gun.setDatabaseName("BoundBook");
            gun.setRecievedFrom(rs.getString("RecievedFrom"));
            gun.setDateDisposed(rs.getString("DateDisposed"));
            gun.setTransferedTo(rs.getString("TransferredTo"));
            gun.setPrice(rs.getString("Price"));
            gun.setDateRecieved(rs.getString("DateRecieved"));

            rs.close();
            return gun;
        }
	catch(Exception e)
	{
            String QueryString = "SELECT * FROM GunList.GunsInList WHERE SerialNumber = \""+SN+"\"";
            ResultSet rs = dbc.runQuery(QueryString);
		
            rs.next();
            gun.setManufacturer(rs.getString("Manufacturer"));
            gun.setModelName(rs.getString("ModelName"));
            gun.setSerialNumber(rs.getString("SerialNumber"));	
            gun.setType(rs.getString("Type"));
            gun.setCaliber(rs.getString("Caliber"));
            gun.setPicture(rs.getString("Picture"));
            gun.setDatabaseName("GunsInList");
            rs.close();
            return gun;
     	}
    } 
    
    public List<Firearm> getAll() throws Exception
    {
        String QueryString = "SELECT * FROM GunList.GunsInList";
        ResultSet rs = dbc.runQuery(QueryString);
        Firearm gun = new Firearm();

        List<Firearm> hpw = new ArrayList<Firearm> ();
        while(rs.next())
        {
            gun = new Firearm();
            gun.setManufacturer(rs.getString("Manufacturer"));
            gun.setModelName(rs.getString("ModelName"));
            gun.setSerialNumber(rs.getString("SerialNumber"));	
            gun.setType(rs.getString("Type"));
            gun.setCaliber(rs.getString("Caliber"));
            gun.setPicture(rs.getString("Picture"));
            gun.setDatabaseName("GunsInList");
            
            hpw.add(gun);
            gun = null;
        }
        
        QueryString = "SELECT * FROM BoundBook.BoundBookPage";
        rs = dbc.runQuery(QueryString);
        while(rs.next())
        {
            gun = new Firearm();
            gun.setManufacturer(rs.getString("Manufacturer"));
            gun.setModelName(rs.getString("ModelName"));
            gun.setSerialNumber(rs.getString("SerialNumber"));	
            gun.setType(rs.getString("Type"));
            gun.setCaliber(rs.getString("Caliber"));
            gun.setPicture(rs.getString("Picture"));
            gun.setDatabaseName("BoundBook");
            gun.setRecievedFrom(rs.getString("RecievedFrom"));
            gun.setDateDisposed(rs.getString("DateDisposed"));
            gun.setTransferedTo(rs.getString("TransferredTo"));
            gun.setPrice(rs.getString("Price"));
            gun.setDateRecieved(rs.getString("DateRecieved"));
            
            hpw.add(gun);
            gun = null;
        }
        
	rs.close();
        return hpw;
    }
    
    public int insertFirearm(Firearm fs) throws Exception
    {
        String QueryString;
        if(fs.getDatabaseName().trim().equalsIgnoreCase("GunsInList")) {
            QueryString = "INSERT into GunList.GunsInList (Caliber,Manufacturer,ModelName,Picture,Type,SerialNumber) Values (";
            QueryString = QueryString + "\""+fs.getCaliber()+"\",";
            QueryString = QueryString + "\""+fs.getManufacturer()+"\",";
            QueryString = QueryString + "\""+fs.getModelName()+"\",";
            QueryString = QueryString + "\""+fs.getPicture()+"\",";
            QueryString = QueryString + "\""+fs.getType()+"\",";
            QueryString = QueryString + "\""+fs.getSerialNumber()+"\")";
        } else {
            QueryString = "INSERT BoundBook.BoundBookPage (Caliber,Manufacturer,ModelName,Picture,Type,SerialNumber,RecievedFrom,DateDisposed,TransferedTo,Price,DateRecieved) Values (";
            QueryString = QueryString + "\""+fs.getCaliber()+"\",";
            QueryString = QueryString + "\""+fs.getManufacturer()+"\",";
            QueryString = QueryString + "\""+fs.getModelName()+"\",";
            QueryString = QueryString + "\""+fs.getPicture()+"\",";
            QueryString = QueryString + "\""+fs.getType()+"\",";
            QueryString = QueryString + "\""+fs.getSerialNumber()+"\"";
            QueryString = QueryString + "\""+fs.getRecievedFrom()+"\"";
            QueryString = QueryString + "\""+fs.getDateDisposed()+"\"";
            QueryString = QueryString + "\""+fs.getTransferedTo()+"\"";
            QueryString = QueryString + "\""+fs.getPrice()+"\"";
            QueryString = QueryString + "\""+fs.getDateRecieved()+"\")";
        }
        return dbc.runUpdate(QueryString);
    }

    public int updateFirearm(Firearm fs) throws Exception
    {
        String QueryString;
        if(fs.getDatabaseName().equalsIgnoreCase("GunsInList")) {
            QueryString = "UPDATE GunList.GunsInList SET";
            QueryString = QueryString + " Caliber = \""+fs.getCaliber()+"\",";
            QueryString = QueryString + " Manufacturer = \""+fs.getManufacturer()+"\",";
            QueryString = QueryString + " ModelName = \""+fs.getModelName()+"\",";
            QueryString = QueryString + " Picture = \""+fs.getPicture()+"\",";
            QueryString = QueryString + " Type = \""+fs.getType()+"\"";
            QueryString = QueryString + " WHERE SerialNumber = \""+fs.getSerialNumber()+"\"";
        } else {
            QueryString = "UPDATE BoundBook.BoundBookPage SET ";
            QueryString = QueryString + " Caliber = \""+fs.getCaliber()+"\",";
            QueryString = QueryString + " Manufacturer = \""+fs.getManufacturer()+"\",";
            QueryString = QueryString + " ModelName = \""+fs.getModelName()+"\",";
            QueryString = QueryString + " Picture = \""+fs.getPicture()+"\",";
            QueryString = QueryString + " Type = \""+fs.getType()+"\",";
            QueryString = QueryString + " RecievedFrom = \""+fs.getRecievedFrom()+"\",";
            QueryString = QueryString + " DateDisposed = \""+fs.getDateDisposed()+"\",";
            QueryString = QueryString + " TransferedTo = \""+fs.getTransferedTo()+"\",";
            QueryString = QueryString + " Price = \""+fs.getPrice()+"\",";
            QueryString = QueryString + " DateRecieved = \""+fs.getDateRecieved()+"\"";
            QueryString = QueryString + " WHERE SerialNumber = \""+fs.getSerialNumber()+"\"";
        }	
        return dbc.runUpdate(QueryString);
    }
        
    public int deleteFirearm(Firearm fs) throws Exception
    {
    	String QueryString;
    	
        if(fs.getDatabaseName().equalsIgnoreCase("GunsInList")) {
            QueryString = "Delete from GunList.GunsInList WHERE SerialNumber = \""+fs.getSerialNumber()+"\"";
            return dbc.runUpdate(QueryString);
        }

        return -1;
    }
    
    
    public void connect() throws Exception
    {
        dbc.connect();
    }
	
    public void close() throws Exception
    {
        dbc.close();
    }
	
    public List<String> getAllSerialNumbers() throws Exception 
    {
        String QueryString = "SELECT SerialNumber FROM gunlist.gunsinlist";
        ResultSet rs = dbc.runQuery(QueryString);

        List<String> ammoItem = new ArrayList<String>();
        while(rs.next())
        {
            ammoItem.add(rs.getString("SerialNumber"));
        }
        rs.close();

        QueryString = "SELECT SerialNumber FROM boundBook.boundbookpage";
        rs = dbc.runQuery(QueryString);

        while(rs.next())
        {
            ammoItem.add(rs.getString("SerialNumber"));
        }
        rs.close();
		
    	return ammoItem;
    }
    public List<String> getMaker() throws Exception {
        String QueryString = "SELECT DISTINCT Manufacturer FROM gunlist.gunsinlist order by Manufacturer";
        ResultSet rs = dbc.runQuery(QueryString);

        List<String> gunList = new ArrayList<String>();
        while(rs.next())
        {
            gunList.add(rs.getString("Manufacturer"));
        }
        rs.close();

        QueryString = "SELECT DISTINCT Manufacturer FROM boundBook.boundbookpage order by Manufacturer";
        rs = dbc.runQuery(QueryString);

        while(rs.next())
        {
            gunList.add(rs.getString("Manufacturer"));
        }
        rs.close();
		
    	return gunList;
    }
    
    public List<String> getModel() throws Exception {
        String QueryString = "SELECT DISTINCT ModelName FROM gunlist.gunsinlist order by ModelName";
        ResultSet rs = dbc.runQuery(QueryString);

        List<String> gunList = new ArrayList<String>();
        while(rs.next())
        {
            gunList.add(rs.getString("ModelName"));
        }
        rs.close();

        QueryString = "SELECT DISTINCT ModelName FROM boundBook.boundbookpage order by ModelName";
        rs = dbc.runQuery(QueryString);

        while(rs.next())
        {
            gunList.add(rs.getString("ModelName"));
        }
        rs.close();
		
    	return gunList;

    }
    
    public List<String> getType() throws Exception {
        String QueryString = "SELECT DISTINCT Type FROM gunlist.gunsinlist order by Type";
        ResultSet rs = dbc.runQuery(QueryString);

        List<String> gunList = new ArrayList<String>();
        while(rs.next())
        {
            gunList.add(rs.getString("Type"));
        }
        rs.close();

        QueryString = "SELECT DISTINCT Type FROM boundBook.boundbookpage order by Type";
        rs = dbc.runQuery(QueryString);

        while(rs.next())
        {
            gunList.add(rs.getString("Type"));
        }
        rs.close();
		
    	return gunList;

    }
    
    public List<String> getBullet() throws Exception {
        String QueryString = "SELECT DISTINCT Caliber FROM gunlist.gunsinlist order by Caliber";
        ResultSet rs = dbc.runQuery(QueryString);

        List<String> gunList = new ArrayList<String>();
        while(rs.next())
        {
            gunList.add(rs.getString("Caliber"));
        }
        rs.close();

        QueryString = "SELECT DISTINCT Caliber FROM boundBook.boundbookpage order by Caliber";
        rs = dbc.runQuery(QueryString);

        while(rs.next())
        {
            gunList.add(rs.getString("Caliber"));
        }
        rs.close();
		
    	return gunList;

    }
    
    public List<Firearm> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM gunlist.gunsinlist Where "+tableName+" = \""+name+"\"";
        ResultSet rs = dbc.runQuery(QueryString);

        List<Firearm> gunList = new ArrayList<Firearm>();
        while(rs.next())
        {
            Firearm gun = new Firearm();
            gun.setManufacturer(rs.getString("Manufacturer"));
            gun.setModelName(rs.getString("ModelName"));
            gun.setSerialNumber(rs.getString("SerialNumber"));	
            gun.setType(rs.getString("Type"));
            gun.setCaliber(rs.getString("Caliber"));
            gun.setPicture(rs.getString("Picture"));
            gun.setDatabaseName("GunsInList");
            
            gunList.add(gun);
            gun = null;

        }
        rs.close();

        QueryString = "SELECT * FROM boundBook.boundbookpage Where "+tableName+" = \""+name+"\"";
        rs = dbc.runQuery(QueryString);

        while(rs.next())
        {
            Firearm gun = new Firearm();
            gun.setManufacturer(rs.getString("Manufacturer"));
            gun.setModelName(rs.getString("ModelName"));
            gun.setSerialNumber(rs.getString("SerialNumber"));	
            gun.setType(rs.getString("Type"));
            gun.setCaliber(rs.getString("Caliber"));
            gun.setPicture(rs.getString("Picture"));
            gun.setDatabaseName("BoundBook");
            gun.setRecievedFrom(rs.getString("RecievedFrom"));
            gun.setDateDisposed(rs.getString("DateDisposed"));
            gun.setTransferedTo(rs.getString("TransferredTo"));
            gun.setPrice(rs.getString("Price"));
            gun.setDateRecieved(rs.getString("DateRecieved"));
            
            gunList.add(gun);
            gun = null;
        }
        rs.close();
        return gunList;
    }
}

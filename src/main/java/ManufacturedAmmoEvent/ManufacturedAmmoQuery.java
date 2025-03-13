/************************************************************************************************
 * ManufacturedAmmo.java																		*
 * 																								*
 * Author: Todd Brenneman																		*
 * Date: 5/24/2024																				*
 * 																								*
 * This is the base class for querying the ammunition made by a external manufacturer from the  *
 * database.															  						*
 * 																								*
 * 	+---------------------+-------------+------+-----+---------+-------+						*
 *	| Field               | Type        | Null | Key | Default | Extra |						*
 *	+---------------------+-------------+------+-----+---------+-------+						*
 *	| LotNumber           | int(11)     | NO   | PRI | NULL    |       |						*
 *	| Caliber             | varchar(45) | NO   |     | NULL    |       |						*
 *	| Manufacturer        | varchar(45) | NO   |     | NULL    |       |						*
 *	| DatePurchased       | date        | NO   |     | NULL    |       |						*
 *	| Bullet              | varchar(45) | YES  |     | NULL    |       |						*
 *      | BulletWeight        | Float       | NO   |     |         |       |
 *	| Count               | int(11)     | YES  |     | NULL    |       |						*
 *	| Empty1              | tinyint(4)  | YES  |     | NULL    |       |						*
 *	+---------------------+-------------+------+-----+---------+-------+	
 *																								*
 ***********************************************************************************************/

package ManufacturedAmmoEvent;

import BaseClasses.DataBaseConn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ManufacturedAmmoQuery {
    private DataBaseConn DBConn;
    private ManufacturedAmmo ma;
    private List<ManufacturedAmmo> hma;
    private boolean notEmpty = false;

    /** Creates a new instance of ReloadingDiesQuery */
    public ManufacturedAmmoQuery(DataBaseConn dbc) {
    	this.DBConn = dbc;
    }
        
    public ManufacturedAmmo getLotNumber(String ln) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.ManufacturedAmmo WHERE LotNumber = \""+ln+"\"";
        ResultSet rs = DBConn.runQuery(QueryString);

        rs.next();
        ma = new ManufacturedAmmo();
        ma.setLotNumber(rs.getString("LotNumber"));
        ma.setManufacturer(rs.getString("Manufacturer"));
        ma.setCaliber(rs.getString("Caliber"));
        ma.setDatePurchased(rs.getString("DatePurchased"));
        ma.setBullet(rs.getString("Bullet"));
        ma.setBulletWeight(rs.getFloat("BulletWeight"));
        ma.setCount(rs.getInt("Count"));
        ma.setEmpty(rs.getBoolean("Empty1"));

        rs.close();
        return ma;
    }
 
    public List<ManufacturedAmmo> getAll() throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.ManufacturedAmmo";
        
        if(notEmpty) {
            QueryString = QueryString + " where empty1 = 0";
        }
        QueryString = QueryString + " order by DatePurchased";

        ResultSet rs = DBConn.runQuery(QueryString);

        hma = new ArrayList<ManufacturedAmmo> ();
        while(rs.next()){
            ma = new ManufacturedAmmo();
            ma.setLotNumber(rs.getString("LotNumber"));
            ma.setManufacturer(rs.getString("Manufacturer"));
            ma.setCaliber(rs.getString("Caliber"));
            ma.setDatePurchased(rs.getString("DatePurchased"));
            ma.setBullet(rs.getString("Bullet"));
            ma.setBulletWeight(rs.getFloat("BulletWeight"));
            ma.setCount(rs.getInt("Count"));
            ma.setEmpty(rs.getBoolean("Empty1"));
            hma.add(ma);
            ma = null;
        }

        rs.close();
        return hma;
    }
    
    public List<ManufacturedAmmo> getAllCaliber(String cal) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.ManufacturedAmmo WHERE Caliber like \""+cal+"\"";

        if(notEmpty) {
            QueryString = QueryString + " and empty1 = 0";
        }
        QueryString = QueryString + " order by DatePurchased";
                
        ResultSet rs = DBConn.runQuery(QueryString);

        hma = new ArrayList<ManufacturedAmmo> ();
        while(rs.next()){
            ma = new ManufacturedAmmo();
            ma.setLotNumber(rs.getString("LotNumber"));
            ma.setManufacturer(rs.getString("Manufacturer"));
            ma.setCaliber(rs.getString("Caliber"));
            ma.setDatePurchased(rs.getString("DatePurchased"));
            ma.setBullet(rs.getString("Bullet"));
            ma.setBulletWeight(rs.getFloat("BulletWeight"));
            ma.setCount(rs.getInt("Count"));
            ma.setEmpty(rs.getBoolean("Empty1"));
            hma.add(ma);
            ma = null;
        }

        rs.close();
        return hma;
    }
    
    public int insertLotNumber(ManufacturedAmmo rd) throws Exception
    {
        String QueryString = "INSERT INTO AmmoDataBase.ManufacturedAmmo (LotNumber,Manufacturer,Caliber,";
        QueryString = QueryString + "DatePurchased,Bullet,BulletWeight,Count,Empty1) VALUES (";
        QueryString = QueryString + "\""+rd.getLotNumber()+"\", ";
        QueryString = QueryString + "\""+rd.getManufacturer()+"\", ";
        QueryString = QueryString + "\""+rd.getCaliber()+"\", ";		
        QueryString = QueryString + "\""+rd.getDatePurchased()+"\", ";
        QueryString = QueryString + ""+rd.getBulletWeight()+"";
        QueryString = QueryString + "\""+rd.getBullet()+"\", ";		
        QueryString = QueryString + "\""+rd.getCount()+"\", ";		
        if(rd.isEmpty())
            QueryString = QueryString +"1) ";
        else 
            QueryString = QueryString +"0) ";

        return DBConn.runUpdate(QueryString);
    }

    public int updateLotNumber(ManufacturedAmmo rd) throws Exception
    {
        String QueryString = "UPDATE AmmoDataBase.ManufacturedAmmo SET ";
        QueryString = QueryString + "Manufacturer = \""+rd.getManufacturer()+"\"";
        QueryString = QueryString + ", Caliber = \""+rd.getCaliber()+"\"";		
        QueryString = QueryString + ", DatePurchased = \""+rd.getDatePurchased()+"\"";
        QueryString = QueryString + ", Bullet = \""+rd.getBullet()+"\" ";
        QueryString = QueryString + ", BulletWeight = "+rd.getBulletWeight()+"";
        QueryString = QueryString + ", Count = "+rd.getCount()+"";		
        if(rd.isEmpty())
            QueryString = QueryString +", empty1 = 1 ";
        else 
            QueryString = QueryString +", empty1 = 0 ";

        QueryString = QueryString + "WHERE LotNumber = \""+rd.getLotNumber()+"\"";
        //System.out.println("updateLotNumber QueryString = "+QueryString);
        return DBConn.runUpdate(QueryString);
    }    
    
    public int deleteLotNumber(ManufacturedAmmo rd) throws Exception
    {
        String QueryString = "Delete from AmmoDataBase.ManufacturedAmmo ";
        QueryString = QueryString + "WHERE LotNumber = "+rd.getLotNumber();
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

    public boolean isNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }
      
    public List<String> getMaker() throws Exception {
        String QueryString = "SELECT DISTINCT Manufacturer FROM ammodatabase.manufacturedammo order by Manufacturer";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Manufacturer"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getCaliber() throws Exception {
        String QueryString = "SELECT DISTINCT Caliber FROM ammodatabase.manufacturedammo order by Caliber";
     
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
        String QueryString = "SELECT DISTINCT year(DatePurchased) FROM ammodatabase.manufacturedammo order by year(DatePurchased)";
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("year(DatePurchased)"));
        }
        rs.close();
        return ac;
    }
    
    public List<String> getBullet() throws Exception {
        String QueryString = "SELECT DISTINCT Bullet FROM ammodatabase.manufacturedammo order by Bullet";
     
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("Bullet"));
        }
        rs.close();
        return ac;
    }
    
    public List<ManufacturedAmmo> getList(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.manufacturedammo Where "+tableName+" = \""+name+"\"";
        QueryString = QueryString + " order by DatePurchased";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<ManufacturedAmmo> maList = new ArrayList<ManufacturedAmmo>();
        while(rs.next()){
            ma = new ManufacturedAmmo();
            ma.setLotNumber(rs.getString("LotNumber"));
            ma.setManufacturer(rs.getString("Manufacturer"));
            ma.setCaliber(rs.getString("Caliber"));
            ma.setDatePurchased(rs.getString("DatePurchased"));
            ma.setBullet(rs.getString("Bullet"));
            ma.setBulletWeight(rs.getFloat("BulletWeight"));
            ma.setCount(rs.getInt("Count"));
            ma.setEmpty(rs.getBoolean("Empty1"));
            maList.add(ma);
            ma = null;
        }
        rs.close();
        return maList;
    }

    public List<ManufacturedAmmo> getListLike(String tableName, String name) throws Exception
    {
        String QueryString = "SELECT * FROM ammodatabase.manufacturedammo Where "+tableName+" like \""+name+"%\"";
        QueryString = QueryString + " order by DatePurchased";
        ResultSet rs = DBConn.runQuery(QueryString);

        List<ManufacturedAmmo> maList = new ArrayList<ManufacturedAmmo>();
        while(rs.next()){
            ma = new ManufacturedAmmo();
            ma.setLotNumber(rs.getString("LotNumber"));
            ma.setManufacturer(rs.getString("Manufacturer"));
            ma.setCaliber(rs.getString("Caliber"));
            ma.setDatePurchased(rs.getString("DatePurchased"));
            ma.setBullet(rs.getString("Bullet"));
            ma.setBulletWeight(rs.getFloat("BulletWeight"));
            ma.setCount(rs.getInt("Count"));
            ma.setEmpty(rs.getBoolean("Empty1"));
            maList.add(ma);
            ma = null;
        }
        rs.close();
        return maList;
    }
}

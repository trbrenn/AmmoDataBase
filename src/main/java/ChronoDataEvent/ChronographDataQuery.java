/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChronoDataEvent;
import BaseClasses.CalendarTest;
import BaseClasses.DataBaseConn;
import BaseClasses.DataBaseConnData;
import BaseClasses.FormatFloat;
import BulletsEvent.Bullets;
import BulletsEvent.BulletsQuery;
import FirearmsEvent.Firearm;
import ManufacturedAmmoEvent.ManufacturedAmmo;
import ManufacturedAmmoEvent.ManufacturedAmmoQuery;
import PowderEvent.Powder;
import PowderEvent.PowderQuery;
import ReloadEvent.ReloadList;
import ReloadEvent.ReloadListQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChronographDataQuery 
{
    private FormatFloat ff = new FormatFloat();
    private DataBaseConn DBConn;
    private ChronographData cd;
    private List<ChronographData> hcd;
    private CalendarTest cal;
    private DataBaseConn dbc;

    public ChronographDataQuery(DataBaseConn dbc)
    {
        DBConn = dbc;
        this.dbc = dbc;
        cal = new CalendarTest();
    }

    public List<ChronographData> getAll() throws Exception
    {
        String QueryString = "SELECT * FROM ammodataBase.chronographdata order by TestNumber";
        ResultSet rs = DBConn.runQuery(QueryString);
        
        hcd = new ArrayList<ChronographData>();
        while(rs.next())
        {
            cd = new ChronographData();
            cd.setVelocity1(rs.getFloat("Velocity1"));
            cd.setVelocity2(rs.getFloat("Velocity2"));
            cd.setVelocity3(rs.getFloat("Velocity3"));
            cd.setVelocity4(rs.getFloat("Velocity4"));
            cd.setVelocity5(rs.getFloat("Velocity5"));
            cd.setVelocity6(rs.getFloat("Velocity6"));
            cd.setVelocity7(rs.getFloat("Velocity7"));
            cd.setVelocity8(rs.getFloat("Velocity8"));
            cd.setVelocity9(rs.getFloat("Velocity9"));
            cd.setVelocity10(rs.getFloat("Velocity10"));
            cd.setShotsInString(rs.getInt("ShotsInString"));
            cd.setShooter(rs.getString("Shooter"));
            cd.setTestNumber(rs.getInt("TestNumber"));
            cd.setShotDate(cal.convertDate(rs.getString("Date")));
            cd.setReloadLotNumber(rs.getString("AmmoLotNumber"));
            cd.setFirearmID(rs.getString("FirearmID"));
            cd.setShotTime(rs.getString("Time"));
            cd.setTempature(rs.getInt("Tempature"));
            cd.setLocation(rs.getString("Location"));
            cd.setWindDirection(rs.getString("WindDirection"));
            cd.setWindSpeed(rs.getFloat("WindSpeed"));
            cd.setElevation(rs.getFloat("Elevation"));
            cd.setTargetIDNumber(rs.getInt("TargetIDNumber"));
            cd.setDistanceToChrony(rs.getInt("DistanceToChrony"));
            if(cd.getReloadLotNumber().equalsIgnoreCase("No Lot Number")) {
                cd.setReloadList(null);
                cd.setBullet(null);
                cd.calculateValues((float)0.0);				
            } else {
                try{   
                //Get Reload List Data ***********************************************************
                    DataBaseConnData dbcd1 = new DataBaseConnData();
                    DataBaseConn DBConn1 = new DataBaseConn(dbcd1);
                    ReloadListQuery rlq = new ReloadListQuery(DBConn1);
                    rlq.connect();
                    ReloadList rl = rlq.getLotNumber(cd.getReloadLotNumber());
                    DBConn1.close();
                    //Get Powder Data ************************************************************
                    if(!rl.getPowderLotNumber().isBlank()){
                        DataBaseConnData dbcd4 = new DataBaseConnData();
                        DataBaseConn DBConn4 = new DataBaseConn(dbcd4);
                        PowderQuery pq = new PowderQuery(DBConn4);
                        pq.connect();
                        cd.setPowder(pq.getLotNumber(rl.getPowderLotNumber()));
                        pq.close();
                        DBConn4.close();
                    } else {
                        System.err.println("PowderLotNumber is blank!");
                    }
                    //Get Bullet Data ************************************************************
                    if(!rl.getBulletLotNumber().isBlank()){
                        DataBaseConnData dbcd5 = new DataBaseConnData();
                        DataBaseConn DBConn5 = new DataBaseConn(dbcd1);
                        BulletsQuery bq = new BulletsQuery(DBConn5);
                        bq.connect();
                        cd.setBullet(bq.getLotNumber(rl.getBulletLotNumber()));
                        cd.calculateValues(cd.getBullet().getWeightFloat());
                        bq.close();
                        DBConn5.close();
                    } else {
                        System.err.println("BulletLotNumber is blank!");
                    }
                } catch (Exception rle) {
                    try{
                        DataBaseConnData dbcd15 = new DataBaseConnData();
                        DataBaseConn DBConn15 = new DataBaseConn(dbcd15);
                        ManufacturedAmmoQuery maq = new ManufacturedAmmoQuery(DBConn15);
                        maq.connect();
                        ManufacturedAmmo ma = maq.getLotNumber(cd.getReloadLotNumber());
                        Bullets bul = new Bullets();
                        bul.setWeight(ff.floatConvert(ma.getBulletWeight(), 0, 1));
                        bul.setDescription(ma.getBullet());
                        bul.setCaliber(ma.getCaliber());
                        bul.setBulletMaker(ma.getManufacturer());
                        cd.setBullet(bul);
                        cd.calculateValues(bul.getWeightFloat());
                        maq.close();
                        DBConn15.close();           
                    }catch (Exception mae) {
                        System.out.println("Manfactured Ammo lot number is blank");
                    }            
                } 
            }           
            if(cd.getFirearmID().trim().isBlank()){
                cd.setFirearmID("No Firearm");
            } else {
                DataBaseConnData dbcd2 = new DataBaseConnData();
                DataBaseConn DBConn2 = new DataBaseConn(dbcd2);
                FirearmsEvent.FirearmQuery fq = new FirearmsEvent.FirearmQuery(DBConn2);
                fq.connect();
                cd.setFirearm(fq.getFirearmData(cd.getFirearmID()));
                fq.close();
                DBConn2.close();
            }
            hcd.add(cd);
            cd = null;
        }
        return hcd;
    }

    public ChronographData getTestNumber(int testnum) throws Exception
    {
        String QueryString = "SELECT * FROM AmmoDataBase.ChronographData WHERE TestNumber = "+testnum;
        ResultSet rs = DBConn.runQuery(QueryString);

        rs.next();
        cd = new ChronographData();
        cd.setVelocity1(rs.getFloat("Velocity1"));
        cd.setVelocity2(rs.getFloat("Velocity2"));
        cd.setVelocity3(rs.getFloat("Velocity3"));
        cd.setVelocity4(rs.getFloat("Velocity4"));
        cd.setVelocity5(rs.getFloat("Velocity5"));
        cd.setVelocity6(rs.getFloat("Velocity6"));
        cd.setVelocity7(rs.getFloat("Velocity7"));
        cd.setVelocity8(rs.getFloat("Velocity8"));
        cd.setVelocity9(rs.getFloat("Velocity9"));
        cd.setVelocity10(rs.getFloat("Velocity10"));
        cd.setShotsInString(rs.getInt("ShotsInString"));
        cd.setShooter(rs.getString("Shooter"));
        cd.setTestNumber(rs.getInt("TestNumber"));
        cd.setShotDate(cal.convertDate(rs.getString("Date")));
        cd.setReloadLotNumber(rs.getString("AmmoLotNumber"));
        cd.setFirearmID(rs.getString("FirearmID"));
        cd.setShotTime(rs.getString("Time"));
        cd.setTempature(rs.getInt("Tempature"));
        cd.setLocation(rs.getString("Location"));
        cd.setWindDirection(rs.getString("WindDirection"));
        cd.setWindSpeed(rs.getFloat("WindSpeed"));
        cd.setElevation(rs.getFloat("Elevation"));
        cd.setTargetIDNumber(rs.getInt("TargetIDNumber"));
        cd.setDistanceToChrony(rs.getInt("DistanceToChrony"));
        if(cd.getReloadLotNumber().equalsIgnoreCase("No Lot Number")) {
            cd.setReloadList(null);
            cd.setBullet(null);
            cd.calculateValues((float)0.0);				
        } else {
            try{   
            //Get Reload List Data ***********************************************************
                DataBaseConnData dbcd1 = new DataBaseConnData();
                DataBaseConn DBConn1 = new DataBaseConn(dbcd1);
                ReloadListQuery rlq = new ReloadListQuery(DBConn1);
                rlq.connect();
                ReloadList rl = rlq.getLotNumber(cd.getReloadLotNumber());
                DBConn1.close();
                //Get Powder Data ************************************************************
                if(!rl.getPowderLotNumber().isBlank()){
                    DataBaseConnData dbcd4 = new DataBaseConnData();
                    DataBaseConn DBConn4 = new DataBaseConn(dbcd4);
                    PowderQuery pq = new PowderQuery(DBConn4);
                    pq.connect();
                    cd.setPowder(pq.getLotNumber(rl.getPowderLotNumber()));
                    pq.close();
                    DBConn4.close();
                } else {
                    System.err.println("PowderLotNumber is blank!");
                }
                //Get Bullet Data ************************************************************
                if(!rl.getBulletLotNumber().isBlank()){
                    DataBaseConnData dbcd5 = new DataBaseConnData();
                    DataBaseConn DBConn5 = new DataBaseConn(dbcd1);
                    BulletsQuery bq = new BulletsQuery(DBConn5);
                    bq.connect();
                    cd.setBullet(bq.getLotNumber(rl.getBulletLotNumber()));
                    cd.calculateValues(cd.getBullet().getWeightFloat());
                    bq.close();
                    DBConn5.close();
                } else {
                    System.err.println("BulletLotNumber is blank!");
                }
            } catch (Exception rle) {
                try{
                    DataBaseConnData dbcd15 = new DataBaseConnData();
                    DataBaseConn DBConn15 = new DataBaseConn(dbcd15);
                    ManufacturedAmmoQuery maq = new ManufacturedAmmoQuery(DBConn15);
                    maq.connect();
                    ManufacturedAmmo ma = maq.getLotNumber(cd.getReloadLotNumber());
                    Bullets bul = new Bullets();
                    bul.setWeight(ff.floatConvert(ma.getBulletWeight(), 0, 1));
                    bul.setDescription(ma.getBullet());
                    bul.setCaliber(ma.getCaliber());
                    bul.setBulletMaker(ma.getManufacturer());
                    cd.setBullet(bul);
                    cd.calculateValues(bul.getWeightFloat());
                    maq.close();
                    DBConn15.close();           
                }catch (Exception mae) {
                    System.out.println("Manfactured Ammo lot number is blank");
                }            
            } 
        }           
        if(cd.getFirearmID().trim().isBlank()){
            cd.setFirearmID("No Firearm");
        } else {
            DataBaseConnData dbcd2 = new DataBaseConnData();
            DataBaseConn DBConn2 = new DataBaseConn(dbcd2);
            FirearmsEvent.FirearmQuery fq = new FirearmsEvent.FirearmQuery(DBConn2);
            fq.connect();
            cd.setFirearm(fq.getFirearmData(cd.getFirearmID()));
            fq.close();
            DBConn2.close();
        }
        return cd;
    }

    public int insertTestNumber(ChronographData chronoData) throws Exception
    {
        String StringQuery = "INSERT INTO AmmoDataBase.ChronographData (Velocity1,Velocity2,Velocity3,Velocity4,Velocity5,Velocity6,Velocity7,";
        StringQuery = StringQuery + "Velocity8,Velocity9,Velocity10,ShotsInString,Shooter,TestNumber,Date,AmmoLotNumber,FirearmID,Time,Tempature,";
        StringQuery = StringQuery + "Location,WindDirection,WindSpeed,Elevation,TargetIDNumber,DistanceToChrony, Caliber) VALUES (";
        StringQuery = StringQuery + "" + chronoData.getVelocity1() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity2() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity3() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity4() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity5() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity6() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity7() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity8() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity9() +",";
        StringQuery = StringQuery + "" + chronoData.getVelocity10() +",";
        StringQuery = StringQuery + "" + chronoData.getShotsInString() +",";
        StringQuery = StringQuery + "\"" + chronoData.getShooter() +"\",";
        StringQuery = StringQuery + "" + chronoData.getTestNumber() +",";
        StringQuery = StringQuery + "\"" + cal.convertDate(chronoData.getShotDate()) + "\",";
        StringQuery = StringQuery + "\"" + chronoData.getReloadLotNumber() +"\",";
        StringQuery = StringQuery + "\"" + chronoData.getFirearmID() +"\",";
        StringQuery = StringQuery + "\"" + chronoData.getShotTime() +"\",";
        StringQuery = StringQuery + "" + chronoData.getTempature() +",";
        StringQuery = StringQuery + "\"" + chronoData.getLocation() +"\",";
        StringQuery = StringQuery + "\"" + chronoData.getWindDirection() +"\",";
        StringQuery = StringQuery + "" + chronoData.getWindSpeed() +",";
        StringQuery = StringQuery + "" + chronoData.getElevation() +",";
        StringQuery = StringQuery + "" + chronoData.getTargetIDNumber() +",";
        StringQuery = StringQuery + "" + chronoData.getDistanceToChrony() +",";
        StringQuery = StringQuery + "\"" + getCaliber() + "\")";
        System.err.println(StringQuery);
        return DBConn.runUpdate(StringQuery);
    }

    public int updateTestNumber(ChronographData chronoData) throws Exception
    {
        String StringQuery = "UPDATE AmmoDataBase.ChronographData SET ";
        StringQuery = StringQuery + "Velocity1 = " + chronoData.getVelocity1() +",";
        StringQuery = StringQuery + "Velocity2 = " + chronoData.getVelocity2() +",";
        StringQuery = StringQuery + "Velocity3 = " + chronoData.getVelocity3() +",";
        StringQuery = StringQuery + "Velocity4 = " + chronoData.getVelocity4() +",";
        StringQuery = StringQuery + "Velocity5 = " + chronoData.getVelocity5() +",";
        StringQuery = StringQuery + "Velocity6 = " + chronoData.getVelocity6() +",";
        StringQuery = StringQuery + "Velocity7 = " + chronoData.getVelocity7() +",";
        StringQuery = StringQuery + "Velocity8 = " + chronoData.getVelocity8() +",";
        StringQuery = StringQuery + "Velocity9 = " + chronoData.getVelocity9() +",";
        StringQuery = StringQuery + "Velocity10 = " + chronoData.getVelocity10() +",";
        StringQuery = StringQuery + "ShotsInString = " + chronoData.getShotsInString() +",";
        StringQuery = StringQuery + "Shooter = \"" + chronoData.getShooter() +"\",";
        StringQuery = StringQuery + "TestNumber = " + generateTestNumber() +",";
        StringQuery = StringQuery + "Date = \"" + cal.convertDate(chronoData.getShotDate()) + "\",";
        StringQuery = StringQuery + "AmmoLotNumber = \"" + chronoData.getReloadLotNumber() +"\",";
        StringQuery = StringQuery + "FirearmID = \"" + chronoData.getFirearmID() +"\",";
        StringQuery = StringQuery + "Time = \"" + chronoData.getShotTime() +"\",";
        StringQuery = StringQuery + "Tempature = " + chronoData.getTempature() +",";
        StringQuery = StringQuery + "Location = \"" + chronoData.getLocation() +"\",";
        StringQuery = StringQuery + "WindDirection = \"" + chronoData.getWindDirection() +"\",";
        StringQuery = StringQuery + "WindSpeed = " + chronoData.getWindSpeed() +",";
        StringQuery = StringQuery + "Elevation = " + chronoData.getElevation() +",";
        StringQuery = StringQuery + "TargetIDNumber = " + chronoData.getTargetIDNumber() +",";
        StringQuery = StringQuery + "DistanceToChrony = " + chronoData.getDistanceToChrony() +",";
        StringQuery = StringQuery + "Caliber = \"" + getCaliber() + "\" ";	
        StringQuery = StringQuery + "WHERE TestNumber = " + chronoData.getTestNumber();

        return DBConn.runUpdate(StringQuery);
    }

    public int deleteTestNumber(ChronographData chronoData) throws Exception {
        String StringQuery = "Delete from AmmoDataBase.ChronographData where TestNumber = " + chronoData.getTestNumber();
        return DBConn.runUpdate(StringQuery);
    }
        
    public void connect() throws Exception {
    	DBConn.connect();
    }
    	
    public void close() throws Exception {
    	DBConn.close();
    }
    	    
    public int generateTestNumber() {
    	int test = 0;
        String QueryString = "SELECT testnumber FROM ammodataBase.chronographdata";
        try {
            ResultSet rs = DBConn.runQuery(QueryString);

            while(rs.next())
            {
                int r = rs.getInt("testnumber");
                if( r > test)
                    test = r;
            }
            test++;
            return test;
        } catch(Exception e) {
            return -1;
        }
    }
    
    public List<String> getDateList() throws Exception {
        String QueryString = "SELECT DISTINCT year(Date) FROM ammodataBase.chronographdata order by year(Date)";
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("year(Date)"));
        }
        rs.close();
        return ac;
    }

    public List<String> getCaliberList() throws Exception {
        String QueryString = "SELECT DISTINCT caliber FROM ammodataBase.chronographdata order by caliber";
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("caliber"));
        }
        rs.close();
        return ac;
      }
    
    public List<String> getFirearmList() throws Exception {
        String QueryString = "SELECT DISTINCT FirearmID FROM ammodataBase.chronographdata order by FirearmID";
        ResultSet rs = DBConn.runQuery(QueryString);
        List<String> ac = new ArrayList<String>();
        while(rs.next())
        {
            ac.add(rs.getString("FirearmID"));
        }
        rs.close();
        
        int count = ac.size();
        List<String> fl = new ArrayList<String>();
        DataBaseConnData dbcd2 = new DataBaseConnData();
        DataBaseConn DBConn2 = new DataBaseConn(dbcd2);
        FirearmsEvent.FirearmQuery fq = new FirearmsEvent.FirearmQuery(DBConn2);
        fq.connect();
        Firearm fa = new Firearm();
        for(int i = 0; i < count; i++){
            fa = fq.getFirearmData(ac.get(i));
            fl.add(fa.getSerialNumber()+","+fa.getManufacturer()+" "+fa.getModelName());
        }
        return fl;
    }

    private String getCaliber() {
        try{   
            //Get Reload List Data ***********************************************************
            DataBaseConnData dbcd1 = new DataBaseConnData();
            DataBaseConn DBConn1 = new DataBaseConn(dbcd1);
            ReloadListQuery rlq = new ReloadListQuery(DBConn1);
            rlq.connect();
            ReloadList rl = rlq.getLotNumber(cd.getReloadLotNumber());
            rlq.close();
            DBConn1.close();
            return rl.getCaliber();            
        } catch (Exception rlqe){
            try {
                DataBaseConnData dbcd15 = new DataBaseConnData();
                DataBaseConn DBConn15 = new DataBaseConn(dbcd15);
                ManufacturedAmmoQuery maq = new ManufacturedAmmoQuery(DBConn15);
                maq.connect();
                ManufacturedAmmo ma = maq.getLotNumber(cd.getReloadLotNumber());
                maq.close();
                DBConn15.close();           
                return ma.getCaliber();
            }catch (Exception maqe) {
                return "Shit!";
            }                
        }
    }
    
    public List<ChronographData> getListLike(String tableName, String name) throws Exception  {
        String QueryString = "SELECT * FROM ammodataBase.chronographdata Where "+tableName+" like \""+name+"%\" order by date";
        ResultSet rs = DBConn.runQuery(QueryString);
        
        hcd = new ArrayList<ChronographData>();
        while(rs.next())
        {
            cd = new ChronographData();
            cd.setVelocity1(rs.getFloat("Velocity1"));
            cd.setVelocity2(rs.getFloat("Velocity2"));
            cd.setVelocity3(rs.getFloat("Velocity3"));
            cd.setVelocity4(rs.getFloat("Velocity4"));
            cd.setVelocity5(rs.getFloat("Velocity5"));
            cd.setVelocity6(rs.getFloat("Velocity6"));
            cd.setVelocity7(rs.getFloat("Velocity7"));
            cd.setVelocity8(rs.getFloat("Velocity8"));
            cd.setVelocity9(rs.getFloat("Velocity9"));
            cd.setVelocity10(rs.getFloat("Velocity10"));
            cd.setShotsInString(rs.getInt("ShotsInString"));
            cd.setShooter(rs.getString("Shooter"));
            cd.setTestNumber(rs.getInt("TestNumber"));
            cd.setShotDate(cal.convertDate(rs.getString("Date")));
            cd.setReloadLotNumber(rs.getString("AmmoLotNumber"));
            cd.setFirearmID(rs.getString("FirearmID"));
            cd.setShotTime(rs.getString("Time"));
            cd.setTempature(rs.getInt("Tempature"));
            cd.setLocation(rs.getString("Location"));
            cd.setWindDirection(rs.getString("WindDirection"));
            cd.setWindSpeed(rs.getFloat("WindSpeed"));
            cd.setElevation(rs.getFloat("Elevation"));
            cd.setTargetIDNumber(rs.getInt("TargetIDNumber"));
            cd.setDistanceToChrony(rs.getInt("DistanceToChrony"));
            
            if(cd.getReloadLotNumber().equalsIgnoreCase("No Lot Number")) {
                cd.setReloadList(null);
                cd.setBullet(null);
                cd.calculateValues((float)0.0);				
            } else {
                try{   
                //Get Reload List Data ***********************************************************
                    DataBaseConnData dbcd1 = new DataBaseConnData();
                    DataBaseConn DBConn1 = new DataBaseConn(dbcd1);
                    ReloadListQuery rlq = new ReloadListQuery(DBConn1);
                    rlq.connect();
                    ReloadList rl = rlq.getLotNumber(cd.getReloadLotNumber());
                    DBConn1.close();
                    //Get Powder Data ************************************************************
                    if(!rl.getPowderLotNumber().isBlank()){
                        DataBaseConnData dbcd4 = new DataBaseConnData();
                        DataBaseConn DBConn4 = new DataBaseConn(dbcd4);
                        PowderQuery pq = new PowderQuery(DBConn4);
                        pq.connect();
                        cd.setPowder(pq.getLotNumber(rl.getPowderLotNumber()));
                        pq.close();
                        DBConn4.close();
                    } else {
                        System.err.println("PowderLotNumber is blank!");
                    }
                    //Get Bullet Data ************************************************************
                    if(!rl.getBulletLotNumber().isBlank()){
                        DataBaseConnData dbcd5 = new DataBaseConnData();
                        DataBaseConn DBConn5 = new DataBaseConn(dbcd1);
                        BulletsQuery bq = new BulletsQuery(DBConn5);
                        bq.connect();
                        cd.setBullet(bq.getLotNumber(rl.getBulletLotNumber()));
                        cd.calculateValues(cd.getBullet().getWeightFloat());
                        bq.close();
                        DBConn5.close();
                    } else {
                        System.err.println("BulletLotNumber is blank!");
                    }
                } catch (Exception rle) {
                    try{
                        DataBaseConnData dbcd15 = new DataBaseConnData();
                        DataBaseConn DBConn15 = new DataBaseConn(dbcd15);
                        ManufacturedAmmoQuery maq = new ManufacturedAmmoQuery(DBConn15);
                        maq.connect();
                        ManufacturedAmmo ma = maq.getLotNumber(cd.getReloadLotNumber());
                        Bullets bul = new Bullets();
                        bul.setWeight(ff.floatConvert(ma.getBulletWeight(), 0, 1));
                        bul.setDescription(ma.getBullet());
                        bul.setCaliber(ma.getCaliber());
                        bul.setBulletMaker(ma.getManufacturer());
                        cd.setBullet(bul);
                        cd.calculateValues(bul.getWeightFloat());
                        maq.close();
                        DBConn15.close();           
                    }catch (Exception mae) {
                        System.out.println("Manfactured Ammo lot number is blank");
                    }            
                } 
            }           
            if(cd.getFirearmID().trim().isBlank()){
                cd.setFirearmID("No Firearm");
            } else {
                DataBaseConnData dbcd2 = new DataBaseConnData();
                DataBaseConn DBConn2 = new DataBaseConn(dbcd2);
                FirearmsEvent.FirearmQuery fq = new FirearmsEvent.FirearmQuery(DBConn2);
                fq.connect();
                cd.setFirearm(fq.getFirearmData(cd.getFirearmID()));
                fq.close();
                DBConn2.close();
            }
            hcd.add(cd);
            cd = null;
        }
        return hcd;
    }
    
}

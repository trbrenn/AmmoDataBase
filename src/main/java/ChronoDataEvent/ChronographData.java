/*
 * ChronographData.java
 *
 * Created on July 1, 2007, 12:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/*
+------------------+--------------+------+-----+-----------------------------------+----------------+
| Field            | Type         | Null | Key | Default                           | Extra          |
+------------------+--------------+------+-----+-----------------------------------+----------------+
| Velocity1        | float        |      |     | 0                                 |                |
| Velocity2        | float        |      |     | 0                                 |                |
| Velocity3        | float        |      |     | 0                                 |                |
| Velocity4        | float        |      |     | 0                                 |                |
| Velocity5        | float        |      |     | 0                                 |                |
| Velocity6        | float        |      |     | 0                                 |                |
| Velocity7        | float        |      |     | 0                                 |                |
| Velocity8        | float        |      |     | 0                                 |                |
| Velocity9        | float        |      |     | 0                                 |                |
| Velocity10       | float        |      |     | 0                                 |                |
| ShotsInString    | int(11)      |      |     | 0                                 |                |
| Shooter          | varchar(50)  |      |     | Todd Brenneman                    |                |
| TestNumber       | int(11)      |      | PRI | NULL                              | auto_increment |
| Date             | date         |      |     | 0000-00-00                        |                |
| AmmoLotNumber    | varchar(50)  |      |     |                                   |                |
| FirearmID        | varchar(100) |      |     |                                   |                |
| Time             | time         | YES  |     | 00:00:00                          |                |
| Tempature        | int(11)      | YES  |     | 0                                 |                |
| Location         | varchar(100) |      |     | Bridgeville Rifle and Pistol Club |                |
| WindDirection    | varchar(5)   | YES  |     | 0                                 |                |
| WindSpeed        | float        | YES  |     | 0                                 |                |
| Elevation        | float        |      |     | 35                                |                |
| TargetIDNumber   | double       | YES  |     | 0                                 |                |
| DistanceToChrony | int(11)      | YES  |     | 0                                 |                |
+------------------+--------------+------+-----+-----------------------------------+----------------+
*/
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChronoDataEvent;

import BaseClasses.CalendarTest;
import BulletsEvent.Bullets;
import FirearmsEvent.Firearm;
import PowderEvent.Powder;
import ReloadEvent.ReloadList;
import java.util.Calendar;
import java.lang.Math;

/**
 *
 * @author Todd Brenneman
 */
public class ChronographData {
    private float	Velocity1;
    private float	Velocity2;
    private float	Velocity3;
    private float	Velocity4;
    private float	Velocity5;
    private float	Velocity6;
    private float	Velocity7;
    private float	Velocity8;
    private float	Velocity9;
    private float	Velocity10;
    private float	devVelocity1;
    private float	devVelocity2;
    private float	devVelocity3;
    private float	devVelocity4;
    private float	devVelocity5;
    private float	devVelocity6;
    private float	devVelocity7;
    private float	devVelocity8;
    private float	devVelocity9;
    private float	devVelocity10;
    private float	totalVelocity;
    private float	totalDevVelocity;
    private float	highVel;
    private float	lowVel;
    private float	AvgVel;
    private float	extermeSpread;
    private float	avgDev;
    private float	stdDev;
    private float	engery;
    private float	WindSpeed;
    private float	Elevation;
    private int		ShotsInString;
    private int		TestNumber;
    private int		Tempature;
    private int		TargetIDNumber;
    private int		DistanceToChrony;
    private Calendar	ShotDate;
    private String	Location;
    private String	WindDirection;
    private String	Shooter;
    private String	ReloadLotNumber;
    private String	FirearmID;
    private String	ShotTime;
    private Firearm	firearm;
    private Bullets	bullet;
    private Powder	powder;
    private ReloadList	reload;

    public ChronographData() {
        super();
    }

    public ChronographData(String xml) {

        if(this.xmlCheck(xml)) {
            String xmlData = (xml.substring(xml.indexOf("<ChronographData>"),xml.indexOf("</ChronographData>")));
            setVelocity1(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity1>") + 11), xmlData.indexOf("</Velocity1>"))));
            setVelocity2(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity2>") + 11), xmlData.indexOf("</Velocity2>"))));
            setVelocity3(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity3>") + 11), xmlData.indexOf("</Velocity3>"))));
            setVelocity4(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity4>") + 11), xmlData.indexOf("</Velocity4>"))));
            setVelocity5(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity5>") + 11), xmlData.indexOf("</Velocity5>"))));
            setVelocity6(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity6>") + 11), xmlData.indexOf("</Velocity6>"))));
            setVelocity7(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity7>") + 11), xmlData.indexOf("</Velocity7>"))));
            setVelocity8(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity8>") + 11), xmlData.indexOf("</Velocity8>"))));
            setVelocity9(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity9>") + 11), xmlData.indexOf("</Velocity9>"))));
            setVelocity10(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Velocity10>") + 12), xmlData.indexOf("</Velocity10>"))));
            setShotsInString(Integer.parseInt(xmlData.substring((xmlData.indexOf("<ShotsInString>") + 15), xmlData.indexOf("</ShotsInString>"))));
            setShooter((xmlData.substring((xmlData.indexOf("<Shooter>") + 9), xmlData.indexOf("</Shooter>"))));
            setTestNumber(Integer.parseInt(xmlData.substring((xmlData.indexOf("<TestNumber>") + 12), xmlData.indexOf("</TestNumber>"))));
            CalendarTest ct = new CalendarTest(xmlData.substring((xmlData.indexOf("<ShotDate>") + 10), xmlData.indexOf("</ShotDate>")));
            setShotDate(ct.getCal());
            setReloadLotNumber((xmlData.substring((xmlData.indexOf("<AmmoLotNumber>") + 15), xmlData.indexOf("</AmmoLotNumber>"))));
            setFirearmID((xmlData.substring((xmlData.indexOf("<FirearmID>") + 11), xmlData.indexOf("</FirearmID>"))));
            setShotTime((xmlData.substring((xmlData.indexOf("<ShotTime>") + 10), xmlData.indexOf("</ShotTime>"))));
            setTempature(Integer.parseInt(xmlData.substring((xmlData.indexOf("<Tempature>") + 11), xmlData.indexOf("</Tempature>"))));
            setLocation((xmlData.substring((xmlData.indexOf("<Location>") + 10), xmlData.indexOf("</Location>"))));
            setWindDirection((xmlData.substring((xmlData.indexOf("<WindDirection>") + 15), xmlData.indexOf("</WindDirection>"))));
            setWindSpeed(Float.parseFloat(xmlData.substring((xmlData.indexOf("<WindSpeed>") + 11), xmlData.indexOf("</WindSpeed>"))));
            setElevation(Float.parseFloat(xmlData.substring((xmlData.indexOf("<Elevation>") + 11), xmlData.indexOf("</Elevation>"))));
            setTargetIDNumber(Integer.parseInt(xmlData.substring((xmlData.indexOf("<TargetIDNumber>") + 16), xmlData.indexOf("</TargetIDNumber>"))));
            setDistanceToChrony(Integer.parseInt(xmlData.substring((xmlData.indexOf("<DistanceToChrony>") + 18), xmlData.indexOf("</DistanceToChrony>"))));
        }
        else
            System.err.println("Error in XML format");
    }

    /**
     * @return Returns the ammoLotNumber.
     */
    public String getReloadLotNumber() {
        return ReloadLotNumber;
    }

    /**
     * @param ammoLotNumber The ammoLotNumber to set.
     */
    public void setReloadLotNumber(String ammoLotNumber) {
        ReloadLotNumber = ammoLotNumber;
    }

    /**
     * @return Returns the distanceToChrony.
     */
    public int getDistanceToChrony() {
        return DistanceToChrony;
    }

    /**
     * @param distanceToChrony The distanceToChrony to set.
     */
    public void setDistanceToChrony(int distanceToChrony) {
        DistanceToChrony = distanceToChrony;
    }

    public String getNewLotNumber() {
        CalendarTest ct = new CalendarTest();
        return ct.timeStamp();		
    }

    /**
     * @return Returns the elevation.
     */
    public float getElevation() {
        return Elevation;
    }

    /**
     * @param elevation The elevation to set.
     */
    public void setElevation(float elevation) {
        Elevation = elevation;
    }

    /**
     * @return Returns the firearmID.
     */
    public String getFirearmID() {
        return FirearmID;
    }

    /**
     * @param firearmID The firearmID to set.
     */
    public void setFirearmID(String firearmID) {
        FirearmID = firearmID;
    }

    /**
     * @return Returns the location.
     */
    public String getLocation() {
        return Location;
    }

    /**
     * @param location The location to set.
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * @return Returns the shooter.
     */
    public String getShooter() {
        return Shooter;
    }

    /**
     * @param shooter The shooter to set.
     */
    public void setShooter(String shooter) {
        Shooter = shooter;
    }

    /**
     * @return Returns the shotDate.
     */
    public Calendar getShotDate() {
        return ShotDate;
    }

    /**
     * @param shotDate The shotDate to set.
     */
    public void setShotDate(Calendar shotDate) {
        ShotDate = shotDate;
    }

    /**
     * @return Returns the shotsInString.
     */
    public int getShotsInString() {
        return ShotsInString;
    }

    /**
     * @param shotsInString The shotsInString to set.
     */
    public void setShotsInString(int shotsInString) {
        ShotsInString = shotsInString;
    }

    /**
     * @return Returns the shotTime.
     */
    public String getShotTime() {
        return ShotTime;
    }

    /**
     * @param shotTime The shotTime to set.
     */
    public void setShotTime(String shotTime) {
        ShotTime = shotTime;
    }

    /**
     * @return Returns the targetIDNumber.
     */
    public int getTargetIDNumber() {
        return TargetIDNumber;
    }

    /**
     * @param targetIDNumber The targetIDNumber to set.
     */
    public void setTargetIDNumber(int targetIDNumber) {
        TargetIDNumber = targetIDNumber;
    }

    /**
     * @return Returns the tempature.
     */
    public int getTempature() {
        return Tempature;
    }

    /**
     * @param tempature The tempature to set.
     */
    public void setTempature(int tempature) {
        Tempature = tempature;
    }

    /**
     * @return Returns the testNumber.
     */
    public int getTestNumber() {
        return TestNumber;
    }

    /**
     * @param testNumber The testNumber to set.
     */
    public void setTestNumber(int testNumber) {
        TestNumber = testNumber;
    }

    /**
     * @return Returns the velocity1.
     */
    public float getVelocity1() {
        return Velocity1;
    }

    /**
     * @param velocity1 The velocity1 to set.
     */
    public void setVelocity1(float velocity1) {
        Velocity1 = velocity1;
    }

    /**
     * @return Returns the velocity10.
     */
    public float getVelocity10() {
        return Velocity10;
    }

    /**
     * @param velocity10 The velocity10 to set.
     */
    public void setVelocity10(float velocity10) {
        Velocity10 = velocity10;
    }

    /**
     * @return Returns the velocity2.
     */
    public float getVelocity2() {
        return Velocity2;
    }

    /**
     * @param velocity2 The velocity2 to set.
     */
    public void setVelocity2(float velocity2) {
        Velocity2 = velocity2;
    }

    /**
     * @return Returns the velocity3.
     */
    public float getVelocity3() {
        return Velocity3;
    }

    /**
     * @param velocity3 The velocity3 to set.
     */
    public void setVelocity3(float velocity3) {
        Velocity3 = velocity3;
    }

    /**
     * @return Returns the velocity4.
     */
    public float getVelocity4() {
        return Velocity4;
    }

    /**
     * @param velocity4 The velocity4 to set.
     */
    public void setVelocity4(float velocity4) {
        Velocity4 = velocity4;
    }

    /**
     * @return Returns the velocity5.
     */
    public float getVelocity5() {
        return Velocity5;
    }

    /**
     * @param velocity5 The velocity5 to set.
     */
    public void setVelocity5(float velocity5) {
        Velocity5 = velocity5;
    }

    /**
     * @return Returns the velocity6.
     */
    public float getVelocity6() {
        return Velocity6;
    }

    /**
     * @param velocity6 The velocity6 to set.
     */
    public void setVelocity6(float velocity6) {
        Velocity6 = velocity6;
    }

    /**
     * @return Returns the velocity7.
     */
    public float getVelocity7() {
        return Velocity7;
    }

    /**
     * @param velocity7 The velocity7 to set.
     */
    public void setVelocity7(float velocity7) {
        Velocity7 = velocity7;
    }

    /**
     * @return Returns the velocity8.
     */
    public float getVelocity8() {
        return Velocity8;
    }

    /**
     * @param velocity8 The velocity8 to set.
     */
    public void setVelocity8(float velocity8) {
        Velocity8 = velocity8;
    }

    /**
     * @return Returns the velocity9.
     */
    public float getVelocity9() {
        return Velocity9;
    }

    /**
     * @param velocity9 The velocity9 to set.
     */
    public void setVelocity9(float velocity9) {
        Velocity9 = velocity9;
    }

    /**
     * @return Returns the windDirection.
     */
    public String getWindDirection() {
        return WindDirection;
    }

    /**
     * @param windDirection The windDirection to set.
     */
    public void setWindDirection(String windDirection) {
        WindDirection = windDirection;
    }

    /**
     * @return Returns the windSpeed.
     */
    public float getWindSpeed() {
        return WindSpeed;
    }

    /**
     * @param windSpeed The windSpeed to set.
     */
    public void setWindSpeed(float windSpeed) {
        WindSpeed = windSpeed;
    }

    public void setFirearm(Firearm fd) {
        firearm = fd;
    }

    public Firearm getFirearm() {
        return firearm;
    }

    public void setBullet(Bullets bs) {
        bullet = bs;
    }

    public Bullets getBullet() {
        return bullet;
    }

    public void setPowder(Powder ps) {
        powder = ps;
    }

    public Powder getPowder() {
        return powder;
    }

    public void setReloadList(ReloadList as) {
        reload = as;
    }

    public ReloadList getReloadList() {
        return reload;
    }

    public String toXML() {
        //System.err.println("ShotDate = "+ShotDate);
        CalendarTest cal = new CalendarTest(ShotDate);

        String output = new String("<ChronographData>\n");
        output = output + "		<Velocity1>"+Velocity1+"</Velocity1>\n";
        output = output + "		<Velocity2>"+Velocity2+"</Velocity2>\n";
        output = output + "		<Velocity3>"+Velocity3+"</Velocity3>\n";
        output = output + "		<Velocity4>"+Velocity4+"</Velocity4>\n";
        output = output + "		<Velocity5>"+Velocity5+"</Velocity5>\n";
        output = output + "		<Velocity6>"+Velocity6+"</Velocity6>\n";
        output = output + "		<Velocity7>"+Velocity7+"</Velocity7>\n";
        output = output + "		<Velocity8>"+Velocity8+"</Velocity8>\n";
        output = output + "		<Velocity9>"+Velocity9+"</Velocity9>\n";
        output = output + "		<Velocity10>"+Velocity10+"</Velocity10>\n";
        output = output + "		<ShotsInString>"+ShotsInString+"</ShotsInString>\n";
        output = output + "		<Shooter>"+Shooter+"</Shooter>\n";
        output = output + "		<TestNumber>"+TestNumber+"</TestNumber>\n";
        output = output + "		<ShotDate>"+cal.toString()+"</ShotDate>\n";
        output = output + "		<AmmoLotNumber>"+ReloadLotNumber+"</AmmoLotNumber>\n";
        output = output + "		<FirearmID>"+FirearmID+"</FirearmID>\n";
        output = output + "		<ShotTime>"+ShotTime+"</ShotTime>\n";
        output = output + "		<Tempature>"+Tempature+"</Tempature>\n";
        output = output + "		<Location>"+Location+"</Location>\n";
        output = output + "		<WindDirection>"+WindDirection+"</WindDirection>\n";
        output = output + "		<WindSpeed>"+WindSpeed+"</WindSpeed>\n";
        output = output + "		<Elevation>"+Elevation+"</Elevation>\n";
        output = output + "		<TargetIDNumber>"+TargetIDNumber+"</TargetIDNumber>\n";
        output = output + "		<DistanceToChrony>"+DistanceToChrony+"</DistanceToChrony>\n";
        output = output + "</ChronographData>\n";
        return output;
    }

    @SuppressWarnings("unused")
    @Override
    public String toString()
    {
        CalendarTest cal = null;

        if(this.ShotDate!=null)			
            cal = new CalendarTest(this.ShotDate);

        String output = new String("");
        output = "        Velocity1 : "+Velocity1+"\n";
        output = output + "        Velocity2 : "+Velocity2+"\n";
        output = output + "        Velocity3 : "+Velocity3+"\n";
        output = output + "        Velocity4 : "+Velocity4+"\n";
        output = output + "        Velocity5 : "+Velocity5+"\n";
        output = output + "        Velocity6 : "+Velocity6+"\n";
        output = output + "        Velocity7 : "+Velocity7+"\n";
        output = output + "        Velocity8 : "+Velocity8+"\n";
        output = output + "        Velocity9 : "+Velocity9+"\n";
        output = output + "       Velocity10 : "+Velocity10+"\n";
        output = output + "    ShotsInString : "+ShotsInString+"\n";
        output = output + "          Shooter : "+Shooter+"\n";
        output = output + "       TestNumber : "+TestNumber+"\n";
        if(cal==null)
            output = output + "         ShotDate : blank\n";
        else
            output = output + "         ShotDate : "+cal.toString()+"\n";

        output = output + "    AmmoLotNumber : "+ReloadLotNumber+"\n";
        output = output + "        FirearmID : "+FirearmID+"\n";
        output = output + "         ShotTime : "+ShotTime+"\n";
        output = output + "        Tempature : "+Tempature+"\n";
        output = output + "         Location : "+Location+"\n";
        output = output + "    WindDirection : "+WindDirection+"\n";
        output = output + "        WindSpeed : "+WindSpeed+"\n";
        output = output + "        Elevation : "+Elevation+"\n";
        output = output + "   TargetIDNumber : "+TargetIDNumber+"\n";
        output = output + " DistanceToChrony : "+DistanceToChrony+"\n\n";
        if(firearm != null){
            output = output + "           Firearm:---------------------------------------------------------------------------\n";
            output = output + "                     Manufacturer: "+firearm.getManufacturer()+"\n";
            output = output + "                        ModelName: "+firearm.getModelName()+"\n";
            output = output + "                     SerialNumber: "+firearm.getSerialNumber()+"\n";	
            output = output + "                             Type: "+firearm.getType()+"\n";
            output = output + "                          Caliber: "+firearm.getCaliber()+"\n";
            output = output + "                          Picture: "+firearm.getPicture()+"\n";
            output = output + "                    Database Name: "+firearm.getDatabaseName()+"\n";
            output = output + "                    Date Recieved: "+firearm.getDateRecieved()+"\n";
            output = output + "                            Price: "+firearm.getPrice()+"\n";
            output = output + "                    Recieved From: "+firearm.getRecievedFrom()+"\n";
            output = output + "                    Date Disposed: "+firearm.getDateDisposed()+"\n";
            output = output + "                    Transfered To: "+firearm.getTransferedTo()+"\n\n";
        }
        if(bullet != null){
            output = output + "            Bullet:---------------------------------------------------------------------------\n";
            output = output + "                      BulletMaker: "+bullet.getBulletMaker()+"\n";
            output = output + "            Ballistic Coefficient: "+bullet.getBC()+"\n";
            output = output + "                          Caliber: "+bullet.getCaliber()+"\n";
            output = output + "                           Weight: "+bullet.getWeight()+"\n";
            output = output + "                      Description: "+bullet.getDescription()+"\n";
            output = output + "                        LotNumber: "+bullet.getLotNumber()+"\n";
            output = output + "                         LotCount: "+bullet.getLotCount()+"\n";
            output = output + "                          LotCost: "+bullet.getLotCost()+"\n";
            output = output + "                    CostPerBullet: "+bullet.getCostPerBullet()+"\n";
            output = output + "                        CastAlloy: "+bullet.getCastAlloy()+"\n";
            output = output + "                            Empty: "+bullet.isEmpty()+"\n";
            output = output + "                       MoldNumber: "+bullet.getMoldNumber()+"\n\n";
        }
        if(powder != null){
            output = output + "            Powder:---------------------------------------------------------------------------\n";
            output = output + "                            Maker: "+powder.getPowderMaker()+"\n";
            output = output + "                             Name: "+powder.getPowderName()+"\n";
            output = output + "                            Lot #: "+powder.getPowderLotNumber()+"\n";
            output = output + "                       Lot Weight: "+powder.getLotWeight()+"\n";
            output = output + "                         Lot Cost: "+powder.getLotCost()+"\n";
            output = output + "                       Cost/Grain: "+powder.getCostPerGrain()+"\n";
            output = output + "                   Date Purchased: "+powder.getDatePurchased()+"\n\n";
        }
        if(powder != null){
            output = output + "         ReloadList:---------------------------------------------------------------------------\n";
            output = output + "                        LotNumber: " + reload.getLotNumber()+"\n";      
            output = output + "                          Caliber: " + reload.getCaliber()+"\n";           
            output = output + "                         LoadDate: " + reload.getLoadDateString() + "\n";          
            output = output + "                  BulletLotNumber: " + reload.getBulletLotNumber()+"\n";
            output = output + "                    CaseLotNumber: " + reload.getCaseLotNumber()+"\n";   
            output = output + "                  PowderLotNumber: " + reload.getPowderLotNumber()+"\n";   
            output = output + "                     PowderWeight: " + reload.getPowderWeight()+"\n";
            output = output + "                  PrimerLotNumber: " + reload.getPrimerLotNumber()+"\n";
            output = output + "                      TimesLoaded: " + reload.getTimesLoaded()+"\n";     
            output = output + "                       CaseLength: " + reload.getCaseLength()+"\n";     
            output = output + "                    OverAllLength: " + reload.getOverAllLength()+"\n";     
            output = output + "                        FireArmID: " + reload.getFireArmID()+"\n";         
            output = output + "                ChronoGraphDataID: " + reload.getChronoGraphDataID()+"\n"; 
            output = output + "                            Crimp: " + reload.getCrimp()+"\n";            
            output = output + "                            Count: " + reload.getCount()+"\n";            
            output = output + "                            Notes: " + reload.getNotes()+"\n";            
            output = output + "                     Manufacturer: " + reload.getManufacturer()+"\n";
        }
        
        return output;
    }

    public void calculateValues(float bulletWeight)
    {
        float sdStep1 = 0;
        totalVelocity = Velocity1 + Velocity2 + Velocity3 + Velocity4 + Velocity5 + Velocity6 + Velocity7 + Velocity8 + Velocity9 + Velocity10;
        AvgVel = totalVelocity / ShotsInString;

        if(ShotsInString >= 1)
        {
            highVel = Velocity1;
            lowVel = Velocity1;
            sdStep1 = Velocity1 * Velocity1;
            devVelocity1 = Math.abs(AvgVel - Velocity1);
        }
        if(ShotsInString >= 2)
        {
            highVel = Math.max(highVel, Velocity2);
            lowVel = Math.min(lowVel, Velocity2);
            sdStep1 = sdStep1 + (Velocity2 * Velocity2);
            devVelocity2 = Math.abs(AvgVel-Velocity2);
        }
        if(ShotsInString >= 3){
            highVel = Math.max(highVel, Velocity3);
            lowVel = Math.min(lowVel, Velocity3);
            sdStep1 = sdStep1 + (Velocity3 * Velocity3);
            devVelocity3 = Math.abs(AvgVel-Velocity3);
        }
        if(ShotsInString >= 4){
            highVel = Math.max(highVel, Velocity4);
            lowVel = Math.min(lowVel, Velocity4);
            sdStep1 = sdStep1 + (Velocity4 * Velocity4);
            devVelocity4 = Math.abs(AvgVel-Velocity4);
        }
        if(ShotsInString >= 5){
            highVel = Math.max(highVel, Velocity5);
            lowVel = Math.min(lowVel, Velocity5);
            sdStep1 = sdStep1 + (Velocity5 * Velocity5);
            devVelocity5 = Math.abs(AvgVel-Velocity5);
        }
        if(ShotsInString >= 6){
            highVel = Math.max(highVel, Velocity6);
            lowVel = Math.min(lowVel, Velocity6);
            sdStep1 = sdStep1 + (Velocity6 * Velocity6);
            devVelocity6 = Math.abs(AvgVel-Velocity6);
        }
        if(ShotsInString >= 7){
            highVel = Math.max(highVel, Velocity7);
            lowVel = Math.min(lowVel, Velocity7);
            sdStep1 = sdStep1 + (Velocity7 * Velocity7);
            devVelocity7 = Math.abs(AvgVel-Velocity7);
        }
        if(ShotsInString >= 8){
            highVel = Math.max(highVel, Velocity8);
            lowVel = Math.min(lowVel, Velocity8);
            sdStep1 = sdStep1 + (Velocity8 * Velocity8);
            devVelocity8 = Math.abs(AvgVel-Velocity8);
        }
        if(ShotsInString >= 9){
            highVel = Math.max(highVel, Velocity9);
            lowVel = Math.min(lowVel, Velocity9);
            sdStep1 = sdStep1 + (Velocity9 * Velocity9);
            devVelocity9 = Math.abs(AvgVel-Velocity9);
        }
        if(ShotsInString >= 10){
            highVel = Math.max(highVel, Velocity10);
            lowVel = Math.min(lowVel, Velocity10);
            sdStep1 = sdStep1 + (Velocity10 * Velocity10);
            devVelocity10 = Math.abs(AvgVel-Velocity10);
        }

        totalDevVelocity = devVelocity1 + devVelocity2 + devVelocity3 + devVelocity4 + devVelocity5 + devVelocity6 + devVelocity7 + devVelocity8 + devVelocity9 + devVelocity10;
        extermeSpread = highVel - lowVel;
        avgDev = totalDevVelocity / ShotsInString;
        float sdStep1a = AvgVel * AvgVel;
        float sdStep2 = (ShotsInString * sdStep1a);
        float sdStep3 = sdStep1 - sdStep2;
        float sdStep4 = sdStep3 / (ShotsInString - 1);

        stdDev = (float)Math.sqrt(sdStep4);
        engery = (AvgVel * AvgVel * bulletWeight)/450240;

    }

    public String outputCalc() {
        String output = new String("");
        output =          "     High Velocity : "+highVel+" ft/s\n";        
        output = output + "      Low Velocity : "+lowVel+" ft/s\n";
        output = output + "    Exterme Spread : "+extermeSpread+"\n";
        output = output + " Average Deviation : "+avgDev+"\n";
        output = output + "Standard Deviation : "+stdDev+"\n";
        output = output + "            Engery : "+engery+" ft/lb\n";
        return output;		
    }

    public float getAvgDev() {
        return avgDev;
    }

    public float getAvgVel() {
        return AvgVel;
    }

    public float getDevVelocity1() {
        return devVelocity1;
    }

    public float getDevVelocity10() {
        return devVelocity10;
    }

    public float getDevVelocity2() {
        return devVelocity2;
    }

    public float getDevVelocity3() {
        return devVelocity3;
    }

    public float getDevVelocity4() {
        return devVelocity4;
    }

    public float getDevVelocity5() {
        return devVelocity5;
    }

    public float getDevVelocity6() {
        return devVelocity6;
    }

    public float getDevVelocity7() {
            return devVelocity7;
    }

    public float getDevVelocity8() {
        return devVelocity8;
    }

    public float getDevVelocity9() {
        return devVelocity9;
    }

    public float getEngery() {
            return engery;
    }

    public float getExtermeSpread() {
        return extermeSpread;
    }

    public float getHighVel() {
        return highVel;
    }

    public float getLowVel() {
        return lowVel;
    }

    public float getStdDev() {
        return stdDev;
    }

    public float getTotalDevVelocity() {
        return totalDevVelocity;
    }

    public float getTotalVelocity() {
        return totalVelocity;
    }

    Object getTimesLoaded() {
        return null;
    }
    
    public boolean xmlCheck(String xml) {
        if(xml.indexOf("<ChronographData>") == -1) {
        	System.err.println("Error in XML Check step 1");
            return false;
        }
        if(xml.indexOf("<Velocity1>") == -1){
        	System.err.println("Error in XML Check step 2");
            return false;
        }
        if(xml.indexOf("</Velocity1>") == -1){
        	System.err.println("Error in XML Check step 3");
            return false;
        }
        if(xml.indexOf("<Velocity2>") == -1){
        	System.err.println("Error in XML Check step 4");
            return false;
        }
        if(xml.indexOf("</Velocity2>") == -1){
        	System.err.println("Error in XML Check step 5");
            return false;
        }
        if(xml.indexOf("<Velocity3>") == -1){
        	System.err.println("Error in XML Check step 6");
            return false;
        }
        if(xml.indexOf("</Velocity3>") == -1){
        	System.err.println("Error in XML Check step 7");
            return false;
        }
        if(xml.indexOf("<Velocity4>") == -1){
        	System.err.println("Error in XML Check step 8");
            return false;
        }
        if(xml.indexOf("</Velocity4>") == -1){
        	System.err.println("Error in XML Check step 9");
            return false;
        }
        if(xml.indexOf("<Velocity5>") == -1){
        	System.err.println("Error in XML Check step 10");
            return false;
        }
        if(xml.indexOf("</Velocity5>") == -1){
        	System.err.println("Error in XML Check step 11");
            return false;
        }
        if(xml.indexOf("<Velocity6>") == -1){
        	System.err.println("Error in XML Check step 12");
            return false;
        }
        if(xml.indexOf("</Velocity6>") == -1){
        	System.err.println("Error in XML Check step 13");
            return false;
        }
        if(xml.indexOf("<Velocity7>") == -1){
        	System.err.println("Error in XML Check step 14");
            return false;
        }
        if(xml.indexOf("</Velocity7>") == -1){
        	System.err.println("Error in XML Check step 15");
            return false;
        }
        if(xml.indexOf("<Velocity8>") == -1){
        	System.err.println("Error in XML Check step 16");
            return false;
        }
        if(xml.indexOf("</Velocity8>") == -1){
        	System.err.println("Error in XML Check step 17");
            return false;
        }
        if(xml.indexOf("<Velocity9>") == -1){
        	System.err.println("Error in XML Check step 18");
            return false;
        }
        if(xml.indexOf("</Velocity9>") == -1){
        	System.err.println("Error in XML Check step 19");
            return false;
        }
        if(xml.indexOf("<Velocity10>") == -1){
        	System.err.println("Error in XML Check step 20");
            return false;
        }
        if(xml.indexOf("</Velocity10>") == -1){
        	System.err.println("Error in XML Check step 21");
            return false;
        }
        if(xml.indexOf("<ShotsInString>") == -1){
        	System.err.println("Error in XML Check step 22");
            return false;
        }
        if(xml.indexOf("</ShotsInString>") == -1){
        	System.err.println("Error in XML Check step 23");
            return false;
        }
        if(xml.indexOf("<Shooter>") == -1){
        	System.err.println("Error in XML Check step 24");
            return false;
        }
        if(xml.indexOf("</Shooter>") == -1){
        	System.err.println("Error in XML Check step 25");
            return false;
        }
        if(xml.indexOf("<TestNumber>") == -1){
        	System.err.println("Error in XML Check step 26");
            return false;
        }
        if(xml.indexOf("</TestNumber>") == -1){
        	System.err.println("Error in XML Check step 27");
            return false;
        }
        if(xml.indexOf("<ShotDate>") == -1){
        	System.err.println("Error in XML Check step 28");
            return false;
        }
        if(xml.indexOf("</ShotDate>") == -1){
        	System.err.println("Error in XML Check step 29");
            return false;
        }
        if(xml.indexOf("<AmmoLotNumber>") == -1){
        	System.err.println("Error in XML Check step 30");
            return false;
        }
        if(xml.indexOf("</AmmoLotNumber>") == -1){
        	System.err.println("Error in XML Check step 31");
            return false;
        }
        if(xml.indexOf("<FirearmID>") == -1){
        	System.err.println("Error in XML Check step 32");
            return false;
        }
        if(xml.indexOf("</FirearmID>") == -1){
        	System.err.println("Error in XML Check step 34");
            return false;
        }
        if(xml.indexOf("<ShotTime>") == -1){
        	System.err.println("Error in XML Check step 35");
            return false;
        }
        if(xml.indexOf("</ShotTime>") == -1){
        	System.err.println("Error in XML Check step 36");
            return false;
        }
        if(xml.indexOf("<Tempature>") == -1){
        	System.err.println("Error in XML Check step 37");
            return false;
        }
        if(xml.indexOf("</Tempature>") == -1){
        	System.err.println("Error in XML Check step 38");
            return false;
        }
        if(xml.indexOf("<Location>") == -1){
        	System.err.println("Error in XML Check step 39");
            return false;
        }
        if(xml.indexOf("</Location>") == -1){
        	System.err.println("Error in XML Check step 40");
            return false;
        }
        if(xml.indexOf("<WindDirection>") == -1){
        	System.err.println("Error in XML Check step 41");
            return false;
        }
        if(xml.indexOf("</WindDirection>") == -1){
        	System.err.println("Error in XML Check step 42");
            return false;
        }
        if(xml.indexOf("<WindSpeed>") == -1){
        	System.err.println("Error in XML Check step 43");
            return false;
        }
        if(xml.indexOf("</WindSpeed>") == -1){
        	System.err.println("Error in XML Check step 44");
            return false;
        }
        if(xml.indexOf("<Elevation>") == -1){
        	System.err.println("Error in XML Check step 45");
            return false;
        }
        if(xml.indexOf("</Elevation>") == -1){
        	System.err.println("Error in XML Check step 46");
            return false;
        }
        if(xml.indexOf("<TargetIDNumber>") == -1){
        	System.err.println("Error in XML Check step 47");
            return false;
        }
        if(xml.indexOf("</TargetIDNumber>") == -1){
        	System.err.println("Error in XML Check step 48");
            return false;
        }
        if(xml.indexOf("<DistanceToChrony>") == -1){
        	System.err.println("Error in XML Check step 49");
            return false;
        }
        if(xml.indexOf("</DistanceToChrony>") == -1){
        	System.err.println("Error in XML Check step 50");
            return false;
        }
       if(xml.indexOf("</ChronographData>") == -1){
       	System.err.println("Error in XML Check step 51");
        return false;
       }
        
       return true;
    }

}

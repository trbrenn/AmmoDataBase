/*
 * Firearm.java
 *
 * Created on July 1, 2007, 12:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/*
 * mysql> describe BoundBookPage;
+---------------+--------------+------+-----+------------+-------+
| Field         | Type         | Null | Key | Default    | Extra |
+---------------+--------------+------+-----+------------+-------+
| DateRecieved  | date         |      |     | 0000-00-00 |       |
| Manufacturer  | varchar(100) |      |     |            |       |
| ModelName     | varchar(100) |      |     |            |       |
| SerialNumber  | varchar(100) |      |     |            |       |
| Type          | varchar(100) |      |     |            |       |
| Caliber       | varchar(100) |      |     |            |       |
| Price         | float        |      |     | 0          |       |
| RecievedFrom  | varchar(100) |      |     |            |       |
| DateDisposed  | date         | YES  |     | 0000-00-00 |       |
| TransferredTo | varchar(100) | YES  |     |            |       |
| PageNumber    | int(11)      | YES  |     | NULL       |       |
| Picture       | varchar(100) |      |     |            |       |
+---------------+--------------+------+-----+------------+-------+

mysql> describe GunsInList;
+--------------+--------------+------+-----+---------+-------+
| Field        | Type         | Null | Key | Default | Extra |
+--------------+--------------+------+-----+---------+-------+
| Manufacturer | varchar(100) |      |     |         |       |
| Model        | varchar(100) |      |     |         |       |
| SerialNumber | varchar(100) |      |     |         |       |
| Type         | varchar(100) |      |     |         |       |
| Caliber      | varchar(100) |      |     |         |       |
| Picture      | varchar(100) |      |     |         |       |
+--------------+--------------+------+-----+---------+-------+

 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirearmsEvent;


/**
 *
 * @author Todd Brenneman
 */
public class Firearm {
    private String Manufacturer;
    private String ModelName;
    private String SerialNumber;
    private String Type;
    private String Caliber;
    private String Picture;
    private String DatabaseName;
    private String DateRecieved;
    private String Price;
    private String RecievedFrom;
    private String DateDisposed;
    private String TransferedTo;

    public Firearm()
    {	
    }

    public Firearm(String m, String mn, String sn, String t, String c, String p, String db)
    {	
        Manufacturer = m;
        ModelName = mn;
        SerialNumber = sn;	
        Type = t;
        Caliber = c;
        Picture = p;
        DatabaseName = db;
        DateRecieved = "N/A";
        Price = "N/A";
        RecievedFrom = "N/A";
        DateDisposed = "N/A";
        TransferedTo = "N/A";
    }

    public Firearm(String m, String mn, String sn, String t, String c, String p, String db, String dr, String pr, String rf, String dd, String tt) {
        Manufacturer = m;
        ModelName= mn;
        SerialNumber = sn;
        Type = t;
        Caliber = c;
        Picture = p;
        DatabaseName = db;
        DateRecieved = dr;
        Price = pr;
        RecievedFrom = rf;
        DateDisposed = dd;
        TransferedTo = tt;
    }

    public Firearm(String xml)
{
        if(xmlCheck(xml))
        {
            String xmlData = xml.substring(xml.indexOf("<Firearm>"),xml.indexOf("</Firearm>"));
            setManufacturer(xmlData.substring((xmlData.indexOf("<Manufacturer>") + 14), xmlData.indexOf("</Manufacturer>")));
            setModelName(xmlData.substring((xmlData.indexOf("<ModelName>") + 11), xmlData.indexOf("</ModelName>")));
            setCaliber(xmlData.substring((xmlData.indexOf("<Caliber>") + 9), xmlData.indexOf("</Caliber>")));
            setType(xmlData.substring((xmlData.indexOf("<Type>") + 6), xmlData.indexOf("</Type>")));
            setSerialNumber(xmlData.substring((xmlData.indexOf("<SerialNumber>") + 14), xmlData.indexOf("</SerialNumber>")));
            setPicture(xmlData.substring((xmlData.indexOf("<Picture>") + 9), xmlData.indexOf("</Picture>")));
            setDatabaseName(xmlData.substring((xmlData.indexOf("<DatabaseName>") + 14), xmlData.indexOf("</DatabaseName>")));
            if(((xmlData.indexOf("<DateRecieved>") + 14) - xmlData.indexOf("</DateRecieved>")) >= 0)
                setDateRecieved(xmlData.substring((xmlData.indexOf("<DateRecieved>") + 14), xmlData.indexOf("</DateRecieved>")));
            else 
                setDateRecieved("N/A");

            if(((xmlData.indexOf("<Price>") + 9) - xmlData.indexOf("</Price>")) >= 0)
                setPrice(xmlData.substring((xmlData.indexOf("<Price>") + 9), xmlData.indexOf("</Price>")));	            	
            else 
                setPrice("N/A");

            if(((xmlData.indexOf("<RecievedFrom>") + 14) - xmlData.indexOf("</RecievedFrom>")) >= 0)
                setRecievedFrom(xmlData.substring((xmlData.indexOf("<RecievedFrom>") + 14) - xmlData.indexOf("</RecievedFrom>")));
            else
                setRecievedFrom("N/A");

            if(((xmlData.indexOf("<DateDisposed>") + 14) - xmlData.indexOf("</DateDisposed>")) >= 0)
                setDateDisposed(xmlData.substring((xmlData.indexOf("<DateDisposed>") + 14), xmlData.indexOf("</DateDisposed>")));
            else
                setDateDisposed("N/A");

            if(((xmlData.indexOf("<TransferedTo>") + 14) - xmlData.indexOf("</TransferedTo>")) >= 0)
                setTransferedTo(xmlData.substring((xmlData.indexOf("<TransferedTo>") + 14), xmlData.indexOf("</TransferedTo>")));
            else
                setTransferedTo("N/A");

        }
    }

    public String getCaliber() {
        return Caliber;
    }

    public void setCaliber(String caliber) {
        Caliber = caliber;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String P) {
        Picture = P;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDatabaseName() {
        return DatabaseName;
    }

    public void setDatabaseName(String db) {
        DatabaseName = db;
    }

    public String getDateRecieved() {
        if(DateRecieved != null)
            return DateRecieved;
        else
            return " ";
    }

    public void setDateRecieved(String dateRecieved) {
        DateRecieved = dateRecieved;
    }

    public String getPrice() {
        if(Price != null)
            return Price;
        else
            return " ";
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRecievedFrom() {
        if(RecievedFrom != null)
            return RecievedFrom;
        else
            return " ";
    }

    public void setRecievedFrom(String recievedFrom) {
        RecievedFrom = recievedFrom;
    }

    public String getDateDisposed() {
        if(DateDisposed != null)
            if(DateDisposed.trim().equals("0000-00-00"))
                return " ";
            else
                return DateDisposed;
        else
            return " ";
    }

    public void setDateDisposed(String dateDisposed) {
        DateDisposed = dateDisposed;
    }

    public String getTransferedTo() {
        if(TransferedTo != null)
            return TransferedTo;
        else
            return " ";
    }

    public void setTransferedTo(String transferedTo) {
        TransferedTo = transferedTo;
    }

  public String toString()
    {
        String temp = " Manufacturer: "+Manufacturer+"\n";
        temp = temp + "    ModelName: "+ModelName+"\n";
        temp = temp + " SerialNumber: "+SerialNumber+"\n";	
        temp = temp + "         Type: "+Type+"\n";
        temp = temp + "      Caliber: "+Caliber+"\n";
        temp = temp + "      Picture: "+Picture+"\n";
        temp = temp + "Database Name: "+DatabaseName+"\n";
        temp = temp + "Date Recieved: "+getDateRecieved()+"\n";
        temp = temp + "        Price: "+getPrice()+"\n";
        temp = temp + "Recieved From: "+getRecievedFrom()+"\n";
        temp = temp + "Date Disposed: "+getDateDisposed()+"\n";
        temp = temp + "Transfered To: "+getTransferedTo()+"\n";

        return temp;
    }
        
    public String toXML()
    {
        String xmlData = "<Firearm>\n";
        xmlData = xmlData + "\t<Manufacturer>"+Manufacturer+"</Manufacturer>\n";
        xmlData = xmlData + "\t<ModelName>"+ModelName+"</ModelName>\n";
        xmlData = xmlData + "\t<SerialNumber>"+SerialNumber+"</SerialNumber>\n";
        xmlData = xmlData + "\t<Type>"+Type+"</Type>\n";
        xmlData = xmlData + "\t<Caliber>"+Caliber+"</Caliber>\n";
        xmlData = xmlData + "\t<Picture>"+Picture+"</Picture>\n";
        xmlData = xmlData + "\t<DatabaseName>"+DatabaseName+"</DatabaseName>\n";
        xmlData = xmlData + "\t<DateRecieved>"+DateRecieved+"</DateRecieved>\n";
        xmlData = xmlData + "\t<Price>"+Price+"</Price>\n";
        xmlData = xmlData + "\t<RecievedFrom>"+RecievedFrom+"</RecievedFrom>\n";
        xmlData = xmlData + "\t<DateDisposed>"+DateDisposed+"</DateDisposed>\n";
        xmlData = xmlData + "\t<TransferedTo>"+TransferedTo+"</TransferedTo>\n";
        xmlData = xmlData + "</Firearm>\n";

        return xmlData;
    }
        
    public boolean xmlCheck(String xml)
    {
        if(xml.indexOf("<Firearm>") == -1)
            return false;
        if(xml.indexOf("<Manufacturer>") == -1)
            return false;
        if(xml.indexOf("</Manufacturer>") == -1)
            return false;
        if(xml.indexOf("<ModelName>") == -1)
            return false;
        if(xml.indexOf("</ModelName>") == -1)
            return false;
        if(xml.indexOf("<SerialNumber>") == -1)
            return false;
        if(xml.indexOf("</SerialNumber>") == -1)
            return false;
        if(xml.indexOf("<Type>") == -1)
            return false;
        if(xml.indexOf("</Type>") == -1)
            return false;
        if(xml.indexOf("<Caliber>") == -1)
            return false;
        if(xml.indexOf("</Caliber>") == -1)
            return false;
        if(xml.indexOf("<Picture>") == -1)
            return false;
        if(xml.indexOf("</Picture>") == -1)
            return false;
        if(xml.indexOf("<DatabaseName>") == -1)
            return false;
        if(xml.indexOf("</DatabaseName>") == -1)
            return false;
        if(xml.indexOf("</Firearm>") == -1)
            return false;
        if(xml.indexOf("<DateRecieved>") == -1)
            return false;
        if(xml.indexOf("</DateRecieved>") == -1)
            return false;
        if(xml.indexOf("<Price>") == -1)
            return false;
        if(xml.indexOf("</Price>") == -1)
            return false;
        if(xml.indexOf("<RecievedFrom>") == -1)
            return false;
        if(xml.indexOf("</RecievedFrom>") == -1)
            return false;
        if(xml.indexOf("<DateDisposed>") == -1)
            return false;
        if(xml.indexOf("</DateDisposed>") == -1)
            return false;
        if(xml.indexOf("<TransferedTo>") == -1)
            return false;
        if(xml.indexOf("</TransferedTo>") == -1)
            return false;

        return true;
    }
    
    public boolean isValidFirearm() {
        if(Manufacturer.isBlank())
            return false;
        if(ModelName.isBlank())
            return false;
        if(SerialNumber.isBlank())
            return false;	
        if(Type.isBlank())
            return false;
        if(Caliber.isBlank())
            return false;
        if(Picture.isBlank())
            return false;
    	return true;
    }
        
}

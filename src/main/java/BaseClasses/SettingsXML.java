/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseClasses;

/**
 * @author trbrenn
 *File[] configFiles = new File(
    this.getClass().getResource(".").getPath()).listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".xml");
        }
});
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class SettingsXML {
    private static final String filename = "AmmoDatabaseSettings.xml";

    private URL 	resource;
    private String      filepath;
    private String 	DBurl;
    private String 	UserName;
    private String 	PassWord;
    private String 	CL;
    private String 	GunListPicURL;
    private String 	BoundBookPicURL;
    private String      ignoreEmpty = "True";
    private String 	SettingsXML = new String();
    private BufferedReader reader;
	
    public SettingsXML() {
    }
	
    public void readData() throws Exception {
        filepath = this.getClass().getResource(".").getPath();
        int index = filepath.indexOf("target");
        filepath = filepath.substring(0, index);
        filepath = filepath + filename;
        File myObj = new File(filepath);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            SettingsXML = SettingsXML + myReader.nextLine();
            //System.out.println(SettingsXML);
        }
        myReader.close();
	parseXML(SettingsXML);
    }
	
    public String getDBurl() {
        return DBurl;
    }

    public void setDBurl(String dBurl) {
	DBurl = dBurl;
	SettingsXML = this.toXML();
    }

    public String getUserName() {
	return UserName;
    }

    public void setUserName(String userName) {
	UserName = userName;
	SettingsXML = this.toXML();
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
    	PassWord = passWord;
	SettingsXML = this.toXML();
    }

    public void setPassWord(char[] passWord) {
	String temp = new String(passWord);
	PassWord = temp;
	SettingsXML = this.toXML();
    }

    public String getCL() {
	return CL;
    }

    public void setCL(String cL) {
	CL = cL;
	SettingsXML = this.toXML();
    }

    public String getGunListPicURL() {
	return GunListPicURL;
    }

    public void setGunListPicURL(String gunListPicURL) {
	GunListPicURL = gunListPicURL;
	SettingsXML = this.toXML();
    }

    public String getBoundBookPicURL() {
        return BoundBookPicURL;
    }

    public void setBoundBookPicURL(String boundBookPicURL) {
	BoundBookPicURL = boundBookPicURL;
	SettingsXML = this.toXML();
    }
	
    public void setIgnoreEmpty(String ie){
        ignoreEmpty = ie;
        SettingsXML = this.toXML();
    }
    
    public String getIgnoreEmpty(){
        return ignoreEmpty;
    }
    
    public void parseXML(String xml)    {
        if(xmlCheck(xml))
        {
            String xmlData = xml.substring(xml.indexOf("<Settings>"),xml.indexOf("</Settings>"));
            DBurl= (xmlData.substring((xmlData.indexOf("<dburl>") + 7), xmlData.indexOf("</dburl>")));
            UserName = (xmlData.substring((xmlData.indexOf("<Username>") + 10), xmlData.indexOf("</Username>")));
            PassWord = (xmlData.substring((xmlData.indexOf("<Password>") + 10), xmlData.indexOf("</Password>")));
            CL = (xmlData.substring((xmlData.indexOf("<CL>") + 4), xmlData.indexOf("</CL>")));
            BoundBookPicURL = (xmlData.substring((xmlData.indexOf("<BoundBookPicURL>") + 17), xmlData.indexOf("</BoundBookPicURL>")));
            GunListPicURL = (xmlData.substring((xmlData.indexOf("<GunListPicURL>") + 15), xmlData.indexOf("</GunListPicURL>")));
            ignoreEmpty = (xmlData.substring((xmlData.indexOf("<IgnoreEmpty>") + 15), xmlData.indexOf("</IgnoreEmpty>")));
        }
    }

    public void writeSettings() throws Exception {
        FileWriter myWriter = new FileWriter(filepath);
        myWriter.write(SettingsXML);
        myWriter.close();
        //System.out.println("Successfully wrote to the file.");
	//filepath = fp.convertToPath();
	//FileWriter myWriter = new FileWriter(filepath);
	//myWriter.write(SettingsXML);
	//myWriter.close();
	//System.out.println("Successfully wrote to the file.");
    }
	
    public boolean isValidSetting()    {
        if (DBurl.trim().equals("")){
            return false;}
        else if (UserName.trim().equals("")){
            return false;}
        else if (PassWord.trim().equals("")){
            return false;}
        else if (CL.trim().equals("")){
            return false;}
        else if (GunListPicURL.trim().equals("")){
            return false;}
        else if (BoundBookPicURL.trim().equals("")){
            return false;}
        else
            return true;
    }
    
    public String toXML()    {
        String XMLout = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Settings>\n";
        XMLout = XMLout + "\t<dburl>"+DBurl+"</dburl>\n";
        XMLout = XMLout + "\t<Username>"+UserName+"</Username>\n";
        XMLout = XMLout + "\t<Password>"+PassWord+"</Password>\n";
        XMLout = XMLout + "\t<CL>"+CL+"</CL>\n";
        XMLout = XMLout + "\t<BoundBookPicURL>"+BoundBookPicURL+"</BoundBookPicURL>\n";
        XMLout = XMLout + "\t<GunListPicURL>"+GunListPicURL+"</GunListPicURL>\n";
        XMLout = XMLout + "\t<IgnoreEmpty>"+ignoreEmpty+"</IgnoreEmpty>\n";
        XMLout = XMLout + "</Settings>\n";    
        return XMLout;
    }
   
    public String toString() {
    	return SettingsXML;
    }
    
    public boolean xmlCheck(String xml)    {
        if(xml.indexOf("<dburl>") == -1)
            return false;
        if(xml.indexOf("</dburl>") == -1)
            return false;
        if(xml.indexOf("<Username>") == -1)
            return false;
        if(xml.indexOf("</Username>") == -1)
            return false;
        if(xml.indexOf("<Password>") == -1)
            return false;
        if(xml.indexOf("</Password>") == -1)
            return false;
        if(xml.indexOf("<CL>") == -1)
            return false;
        if(xml.indexOf("</CL>") == -1)
            return false;
        if(xml.indexOf("<BoundBookPicURL>") == -1)
            return false;
        if(xml.indexOf("</BoundBookPicURL>") == -1)
            return false;
        if(xml.indexOf("<GunListPicURL>") == -1)
            return false;
        if(xml.indexOf("</GunListPicURL>") == -1)
            return false;
        if(xml.indexOf("<IgnoreEmpty>") == -1)
            return false;
        if(xml.indexOf("</IgnoreEmpty>") == -1)
            return false;
       
        return true;
    }
    
    public static void main(String argv[]) {
	SettingsXML sxml = new SettingsXML();
	try {
            sxml.readData();
            sxml.setGunListPicURL("http://192.168.21.2/php/GunList/Pictures/");
            sxml.setIgnoreEmpty("True");
            System.err.println(sxml.toXML());
            sxml.writeSettings();
	} catch (Exception e) {
            System.err.println("Error: "+e);
	}
	System.out.println(sxml);
    }
}

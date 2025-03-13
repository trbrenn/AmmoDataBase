/*
 * AmmoList.java
 *
 * Created on July 1, 2007, 11:09 AM
 *
 * This class packages the Ammolist database object. I enclosed the current database
 * descripiton.
 +-------------------+--------------+------+-----+------------+-------+
 | Field             | Type         | Null | Key | Default    | Extra |
 +-------------------+--------------+------+-----+------------+-------+
 | LotNumber         | varchar(50)  |      | PRI |            |       |
 | Caliber           | varchar(25)  |      |     |            |       |
 | Date              | date         |      |     | 0000-00-00 |       |
 | BulletLotNumber   | varchar(50)  |      |     |            |       |
 | CaseLotNumber     | varchar(50)  |      |     |            |       |
 | PowderLotNumber   | varchar(50)  |      |     |            |       |
 | PowderWeight      | float        |      |     | 0          |       |
 | PrimerLotNumber   | varchar(50)  |      |     |            |       |
 | TimesLoaded       | int(11)      |      |     | 0          |       |
 | CaseLength        | float        |      |     | 0          |       |
 | OverAllLength     | float        |      |     | 0          |       |
 | FireArmID         | varchar(100) |      |     |            |       |
 | ChronoGraphDataID | varchar(100) |      |     |            |       |
 | Crimp             | varchar(25)  |      |     |            |       |
 | Count             | int(11)      |      |     | 0          |       |
 | Notes             | text         |      |     |            |       |
 +-------------------+--------------+------+-----+------------+-------+
 */

package ReloadEvent;

import BaseClasses.CalendarTest;
import java.util.Calendar;

/**
 *
 * @author Todd Brenneman
 */
public class ReloadList {
    private String	LotNumber;   //The lot number of the ammunition item. This is the key item in the database.      
    private String	Caliber;           
    private Calendar	LoadDate;          
    private String	BulletLotNumber;   
    private String	CaseLotNumber;     
    private String	PowderLotNumber;   
    private float	PowderWeight;      
    private String	PrimerLotNumber;   
    private int		TimesLoaded;       
    private float	CaseLength;        
    private float	OverAllLength;     
    private String	FireArmID;         
    private String	ChronoGraphDataID; 
    private String	Crimp;             
    private int		Count;             
    private String	Notes; 
    private String      Manufacturer;
    private boolean     isEmpty;

    public ReloadList() {
    }

    // This Constructor will convert a PROPERLY formated XML string into an AmmoList object. 
    public ReloadList(String xml)  {
        if (xmlCheck(xml)) // Checks to make sure all tags are included.
        {
            String xmlData = xml.substring(xml.indexOf("<AmmoList>"), xml.indexOf("</AmmoList>"));
            setLotNumber(xmlData.substring((xmlData.indexOf("<LotNumber>")+11), xmlData.indexOf("</LotNumber>")));
            setCaliber(xmlData.substring((xmlData.indexOf("<Caliber>")+9), xmlData.indexOf("</Caliber>")));
            setLoadDate(xmlData.substring((xmlData.indexOf("<LoadDate>")+10), xmlData.indexOf("</LoadDate>")));
            setBulletLotNumber(xmlData.substring((xmlData.indexOf("<BulletLotNumber>")+17), xmlData.indexOf("</BulletLotNumber>")));
            setCaseLotNumber(xmlData.substring((xmlData.indexOf("<CaseLotNumber>")+15), xmlData.indexOf("</CaseLotNumber>")));
            setPowderLotNumber(xmlData.substring((xmlData.indexOf("<PowderLotNumber>")+17), xmlData.indexOf("</PowderLotNumber>")));
            setPowderWeight(xmlData.substring((xmlData.indexOf("<PowderWeight>")+14), xmlData.indexOf("</PowderWeight>")));
            setPrimerLotNumber(xmlData.substring((xmlData.indexOf("<PrimerLotNumber>")+17), xmlData.indexOf("</PrimerLotNumber>")));
            setTimesLoaded(xmlData.substring((xmlData.indexOf("<TimesLoaded>")+13), xmlData.indexOf("</TimesLoaded>")));
            setCaseLength(xmlData.substring((xmlData.indexOf("<CaseLength>")+12), xmlData.indexOf("</CaseLength>")));
            setOverAllLength(xmlData.substring((xmlData.indexOf("<OverAllLength>")+15), xmlData.indexOf("</OverAllLength>")));
            setFireArmID(xmlData.substring((xmlData.indexOf("<FireArmID>")+11), xmlData.indexOf("</FireArmID>")));
            setChronoGraphDataID(xmlData.substring((xmlData.indexOf("<ChronoGraphDataID>")+19), xmlData.indexOf("</ChronoGraphDataID>")));
            setCrimp(xmlData.substring((xmlData.indexOf("<Crimp>")+7), xmlData.indexOf("</Crimp>")));
            setCount(xmlData.substring((xmlData.indexOf("<Count>")+7), xmlData.indexOf("</Count>")));
            setNotes(xmlData.substring((xmlData.indexOf("<Notes>")+7), xmlData.indexOf("</Notes>")));  
            setIsEmpty(xmlData.substring((xmlData.indexOf("<isEmpty>")+7), xmlData.indexOf("</isEmpty>")));
        }
   }
        
    // Converts a string date to a Calendar object.
    public void setLoadDate(String data)
    {
        CalendarTest ct = new CalendarTest(); //CalendarTest handles all date functions. I should really change the name.            
        LoadDate = ct.convertDate(data); // Uses calendar date to get a calendar object.
    }

    // Converts a string to float to use to set the powder wieght. If there is an error PowderWeight gets set to 0 by default. 
    public void setPowderWeight(String data) 
    {
        Float fbc = Float.parseFloat(data); //Convert the string data to float.
        try{
            PowderWeight = fbc.floatValue(); 
        }catch(NumberFormatException nfe){ // if the conversion failed set it to 0. 
            PowderWeight = 0;
        }
    }

    // Converts a string to float to use to set the case length. If there is an error CaseLength gets set to 0 by default. 
    public void setCaseLength(String data) 
    {
        Float fbc = Float.parseFloat(data);
        try{
            CaseLength = fbc.floatValue();
        }catch(NumberFormatException nfe){
            CaseLength = 0;
        }
    }

    // Converts a string to float to use to set the over all length. If there is an error OverAllLength gets set to 0 by default. 
    public void setOverAllLength(String data)
    {
       Float fbc = Float.parseFloat(data);
        try{
            OverAllLength = fbc.floatValue();
        }catch(NumberFormatException nfe){
            OverAllLength = 0;
        }
    }

    // Converts a string to int to use to set the Times loaded. If there is an error TimesLoaded gets set to 0 by default. 
    public void setTimesLoaded(String lotCount)
    {
        Integer itg = Integer.parseInt(lotCount);
        try{
            TimesLoaded = itg.intValue();
        }catch(NumberFormatException nfe){
            TimesLoaded = 0;
        }
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        if(isEmpty.equals("1"))
            this.isEmpty = true;
        else
            this.isEmpty = false;
    }

    public boolean IsEmpty() {
        return isEmpty;
    }

    // Converts a string to int to use to set the count. If there is an error Count gets set to 0 by default. 
    public void setCount(String lotCount) {
        Integer itg = Integer.parseInt(lotCount);
        try{
            Count = itg.intValue();
        }catch(NumberFormatException nfe){
            Count = 0;
        }
    }

    /**
     * @return Returns the bulletLotNumber.
     */
    public String getBulletLotNumber() {
        return BulletLotNumber;
    }

    public String getNewLotNumber() {
        CalendarTest ct = new CalendarTest();
        return ct.timeStamp();		
    }

    /**
     * @param bulletLotNumber The bulletLotNumber to set.
     */
    public void setBulletLotNumber(String bulletLotNumber) {
        BulletLotNumber = bulletLotNumber;
    }

    /**
     * @return Returns the caliber.
     */
    public String getCaliber() {
        return Caliber;
    }

    /**
     * @param caliber The caliber to set.
     */
    public void setCaliber(String caliber) {
        Caliber = caliber;
    }

    /**
     * @return Returns the caseLength.
     */
    public float getCaseLength() {
        return CaseLength;
    }

    /**
     * @param caseLength The caseLength to set.
     */
    public void setCaseLength(float caseLength) {
        CaseLength = caseLength;
    }

    /**
     * @return Returns the caseLotNumber.
     */
    public String getCaseLotNumber() {
        return CaseLotNumber;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    /**
     * @param caseLotNumber The caseLotNumber to set.
     */
    public void setCaseLotNumber(String caseLotNumber) {
        CaseLotNumber = caseLotNumber;
    }

    /**
     * @return Returns the chronoGraphDataID.
     */
    public String getChronoGraphDataID() {
        return ChronoGraphDataID;
    }

    /**
     * @param chronoGraphDataID The chronoGraphDataID to set.
     */
    public void setChronoGraphDataID(String chronoGraphDataID) {
        ChronoGraphDataID = chronoGraphDataID;
    }

    /**
     * @return Returns the count.
     */
    public int getCount() {
        return Count;
    }

    /**
     * @param count The count to set.
     */
    public void setCount(int count) {
        Count = count;
    }

    /**
     * @return Returns the crimp.
     */
    public String getCrimp() {
        return Crimp;
    }

    /**
     * @param crimp The crimp to set.
     */
    public void setCrimp(String crimp) {
        Crimp = crimp;
    }

    public String getFireArmID() {
        return FireArmID;
    }

    /**
     * @param fireArmID The fireArmID to set.
     */
    public void setFireArmID(String fireArmID) {
        FireArmID = fireArmID;
    }

    /**
     * @return Returns the loadDate.
     */
    public Calendar getLoadDate() {
        return LoadDate;
    }

    // used to get a formated string date. My standard is yyyy-mm-dd.
    public String getLoadDateString(){
    CalendarTest cal = new CalendarTest();
        return cal.convertDate(LoadDate);
    }

    /**
     * @param loadDate The loadDate to set.
     */
    public void setLoadDate(Calendar loadDate) {
        LoadDate = loadDate;
    }

    /**
     * @return Returns the lotNumber.
     */
    public String getLotNumber() {
        return LotNumber;
    }

    /**
     * @param lotNumber The lotNumber to set.
     */
    public void setLotNumber(String lotNumber) {
        LotNumber = lotNumber;
    }

    /**
     * @return Returns the notes.
     */
    public String getNotes() {
        return Notes;
    }

    /**
     * @param notes The notes to set.
     */
    public void setNotes(String notes) {
        Notes = notes;
    }

    /**
     * @return Returns the overAllLength.
     */
    public float getOverAllLength() {
        return OverAllLength;
    }

    /**
     * @param overAllLength The overAllLength to set.
     */
    public void setOverAllLength(float overAllLength) {
        OverAllLength = overAllLength;
    }

    /**
     * @return Returns the powderLotNumber.
     */
    public String getPowderLotNumber() {
        return PowderLotNumber;
    }

    /**
     * @param powderLotNumber The powderLotNumber to set.
     */
    public void setPowderLotNumber(String powderLotNumber) {
        PowderLotNumber = powderLotNumber;
    }

    /**
     * @return Returns the powderWeight.
     */
    public float getPowderWeight() {
        return PowderWeight;
    }

    /**
     * @param powderWeight The powderWeight to set.
     */
    public void setPowderWeight(float powderWeight) {
        PowderWeight = powderWeight;
    }

    /**
     * @return Returns the primerLotNumber.
     */
    public String getPrimerLotNumber() {
        return PrimerLotNumber;
    }

    /**
     * @param primerLotNumber The primerLotNumber to set.
     */
    public void setPrimerLotNumber(String primerLotNumber) {
        PrimerLotNumber = primerLotNumber;
    }

    /**
     * @return Returns the timesLoaded.
     */
    public int getTimesLoaded() {
        return TimesLoaded;
    }

    /**
     * @param timesLoaded The timesLoaded to set.
     */
    public void setTimesLoaded(int timesLoaded) {
        TimesLoaded = timesLoaded;
    }

    //Outputs the AmmoList into a formatted string.
    public String toString() {
        String output = new String();

        output = "        LotNumber : " + LotNumber + "\n";         
        output = output + "          Caliber : " + Caliber + "\n";           
        output = output + "         LoadDate : " + getLoadDateString() + "\n";          
        output = output + "  BulletLotNumber : " + BulletLotNumber + "\n";   
        output = output + "    CaseLotNumber : " + CaseLotNumber + "\n";     
        output = output + "  PowderLotNumber : " + PowderLotNumber + "\n";   
        output = output + "     PowderWeight : " + PowderWeight + "\n";      
        output = output + "  PrimerLotNumber : " + PrimerLotNumber + "\n";   
        output = output + "      TimesLoaded : " + TimesLoaded + "\n";       
        output = output + "       CaseLength : " + CaseLength + "\n";        
        output = output + "    OverAllLength : " + OverAllLength + "\n";     
        output = output + "        FireArmID : " + FireArmID + "\n";         
        output = output + "ChronoGraphDataID : " + ChronoGraphDataID + "\n"; 
        output = output + "            Crimp : " + Crimp + "\n";             
        output = output + "            Count : " + Count + "\n";             
        output = output + "            Notes : " + Notes + "\n";             
        output = output + "     Manufacturer : " + Manufacturer + "\n"; 
        output = output + "         Is Empty : " + isEmpty + "\n"; 

        return output;
    }

    //returns the ammolist object Properly formatted as an XML string. This should be useable after adding the XML version data. 
    public String toXML(){
        String xmlData = "<AmmoList>\n";
        xmlData = xmlData + "\t<LotNumber>"+LotNumber+"</LotNumber>\n";
        xmlData = xmlData + "\t<Caliber>"+Caliber+"</Caliber>\n";
        xmlData = xmlData + "\t<LoadDate>"+getLoadDateString()+"</LoadDate>\n";
        xmlData = xmlData + "\t<BulletLotNumber>"+BulletLotNumber+"</BulletLotNumber>\n";
        xmlData = xmlData + "\t<CaseLotNumber>"+CaseLotNumber+"</CaseLotNumber>\n";
        xmlData = xmlData + "\t<PowderLotNumber>"+PowderLotNumber+"</PowderLotNumber>\n";
        xmlData = xmlData + "\t<PowderWeight>"+PowderWeight+"</PowderWeight>\n";
        xmlData = xmlData + "\t<PrimerLotNumber>"+PrimerLotNumber+"</PrimerLotNumber>\n";
        xmlData = xmlData + "\t<TimesLoaded>"+TimesLoaded+"</TimesLoaded>\n";
        xmlData = xmlData + "\t<CaseLength>"+CaseLength+"</CaseLength>\n";
        xmlData = xmlData + "\t<OverAllLength>"+OverAllLength+"</OverAllLength>\n";
        xmlData = xmlData + "\t<FireArmID>"+FireArmID+"</FireArmID>\n";
        xmlData = xmlData + "\t<ChronoGraphDataID>"+ChronoGraphDataID+"</ChronoGraphDataID>\n";
        xmlData = xmlData + "\t<Crimp>"+Crimp+"</Crimp>\n";
        xmlData = xmlData + "\t<Count>"+Count+"</Count>\n";
        xmlData = xmlData + "\t<Notes>"+Notes+"</Notes>\n";
        xmlData = xmlData + "\t<Manufacturer>"+Manufacturer+"</Manufacturer>\n";
        xmlData = xmlData + "\t<isEmpty>"+isEmpty+"</isEmpty>\n";
        xmlData = xmlData + "</AmmoList>\n";

        return xmlData;
    }

    public boolean isValid(){
        if(LotNumber.isBlank())
            return false;
        if(Caliber.isBlank())
            return false;
        if(BulletLotNumber.isBlank())
            return false;
        if(CaseLotNumber.isBlank())
            return false;
        if(PowderLotNumber.isBlank())
            return false;
        if(PowderWeight <= 0)
            return false;
        if(PrimerLotNumber.isBlank())
            return false;
        if(TimesLoaded <= 0)
            return false;
        if(CaseLength <= 0)
            return false;
        if(OverAllLength <= 0)
            return false; 
        if(Count <= 0)
            return false;
        return true;
    }
    
    // Checks to insure the XML data has all required fields.
    public boolean xmlCheck(String xml) {
        if(xml.indexOf("<AmmoList>") == -1)
            return false;
        if(xml.indexOf("<LotNumber>") == -1)
            return false;
        if(xml.indexOf("</LotNumber>") == -1)
            return false;
        if(xml.indexOf("<Caliber>") == -1)
            return false;
        if(xml.indexOf("</Caliber>") == -1)
            return false;
        if(xml.indexOf("<LoadDate>") == -1)
            return false;
        if(xml.indexOf("</LoadDate>") == -1)
            return false;
        if(xml.indexOf("<BulletLotNumber>") == -1)
            return false;
        if(xml.indexOf("</BulletLotNumber>") == -1)
            return false;
        if(xml.indexOf("<CaseLotNumber>") == -1)
            return false;
        if(xml.indexOf("</CaseLotNumber>") == -1)
            return false;
        if(xml.indexOf("<PowderLotNumber>") == -1)
            return false;
        if(xml.indexOf("</PowderLotNumber>") == -1)
            return false;
        if(xml.indexOf("<PowderWeight>") == -1)
            return false;
        if(xml.indexOf("</PowderWeight>") == -1)
            return false;
        if(xml.indexOf("<PrimerLotNumber>") == -1)
            return false;
        if(xml.indexOf("</PrimerLotNumber>") == -1)
            return false;
        if(xml.indexOf("<TimesLoaded>") == -1)
            return false;
        if(xml.indexOf("</TimesLoaded>") == -1)
            return false;
        if(xml.indexOf("<CaseLength>") == -1)
            return false;
        if(xml.indexOf("</CaseLength>") == -1)
            return false;
        if(xml.indexOf("<OverAllLength>") == -1)
            return false;
        if(xml.indexOf("</OverAllLength>") == -1)
            return false;
        if(xml.indexOf("<FireArmID>") == -1)
            return false;
        if(xml.indexOf("</FireArmID>") == -1)
            return false;
        if(xml.indexOf("<ChronoGraphDataID>") == -1)
            return false;
        if(xml.indexOf("</ChronoGraphDataID>") == -1)
            return false;
        if(xml.indexOf("<Crimp>") == -1)
            return false;
        if(xml.indexOf("</Crimp>") == -1)
            return false;
        if(xml.indexOf("<Count>") == -1)
            return false;
        if(xml.indexOf("</Count>") == -1)
            return false;
        if(xml.indexOf("<Notes>") == -1)
            return false;
        if(xml.indexOf("</Notes>") == -1)
            return false;
        if(xml.indexOf("<Manufacturer>") == -1)
            return false;
        if(xml.indexOf("</Manufacturer>") == -1)
            return false;
        if(xml.indexOf("</AmmoList>") == -1)
            return false;           

        return true;
    }
        
}


/*
 * Powder.java
 *
 * Created on July 1, 2007, 12:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
// +-----------------+-------------+------+-----+---------+-------+
// | Field           | Type        | Null | Key | Default | Extra |
// +-----------------+-------------+------+-----+---------+-------+
// | PowderLotNumber | varchar(50) |      | PRI |         |       |
// | PowerMaker      | varchar(50) |      |     |         |       |
// | PowderName      | varchar(50) |      |     |         |       |
// | LotWeight       | int(11)     |      |     | 0       |       |
// | LotCost         | float       |      |     | 0       |       |
// | CostPerGrain    | float       |      |     | 0       |       |
// | Empty           | tinyint(1)  |      |     | 0       |       |
// +-----------------+-------------+------+-----+---------+-------+
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PowderEvent;

/**
 *
 * @author Todd Brenneman
 */
public class Powder {
    private String  PowderLotNumber;
    private String  PowderMaker;
    private String  PowderName;
    private int     LotWeight;
    private float   LotCost;
    private float   CostPerGrain;
    private String  DatePurchased;

    private boolean Empty;
	
    public Powder() {
            PowderLotNumber = "";
            PowderMaker = "";
            PowderName = "";
            LotWeight = 0;
            LotCost = 0;
            CostPerGrain = 0;
            DatePurchased = "2001-01-01";
            
            Empty = false;
    }
        
    public Powder(String xml) {
        if(xmlCheck(xml))
        {
            String xmlData = xml.substring(xml.indexOf("<Powder>"),xml.indexOf("</Powder>"));
            setPowderLotNumber(xmlData.substring((xmlData.indexOf("<PowderLotNumber>") + 17), xmlData.indexOf("</PowderLotNumber>")));
            setPowderMaker(xmlData.substring((xmlData.indexOf("<PowderMaker>") + 13), xmlData.indexOf("</PowderMaker>")));
            setPowderName(xmlData.substring((xmlData.indexOf("<PowderName>") + 12), xmlData.indexOf("</PowderName>")));
            setLotCost(xmlData.substring((xmlData.indexOf("<LotCost>") + 9), xmlData.indexOf("</LotCost>")));
            setLotWeightInLbs(xmlData.substring((xmlData.indexOf("<LotWeightlbs>") + 14), xmlData.indexOf("</LotWeightlbs>")));
            setCostPerGrain(xmlData.substring((xmlData.indexOf("<CostPerGrain>") + 14), xmlData.indexOf("</CostPerGrain>")));
            setDatePurchased(xmlData.substring((xmlData.indexOf("<PurchaseDate>") + 14), xmlData.indexOf("</PurchaseDate>")));
            setEmpty(xmlData.substring((xmlData.indexOf("<isEmpty>")+9), xmlData.indexOf("</isEmpty>")).equalsIgnoreCase("true")); 
        }
    }

    public void setDatePurchased(String DatePurchased) {
        this.DatePurchased = DatePurchased;
    }

    public String getDatePurchased() {
        return DatePurchased;
    }

    // return the lot number.
    public String getPowderLotNumber() {
	return PowderLotNumber;
    }
	
	// sets the lot number 
    public void setPowderLotNumber(String pln) {
	PowderLotNumber = pln;
    }
	
	// Retruns the name of the powder maker.
    public String getPowderMaker() {
	return PowderMaker;
    }
	
    // Set the name of the powder maker.
    public void setPowderMaker(String pm) {
	PowderMaker = pm;
    }
	
	//returns the name of the powder.
    public String getPowderName() {
        return PowderName;
    }
	
	// Sets the name of the powder.
    public void setPowderName(String pn) {
	PowderName = pn;
    }
	
	// Get the lot weight in grains.
    public int getLotWeight() {
	return LotWeight;
    }
	
	// Sets the total weight of the lot in grains.
    public void setLotWeightInGrains(String lotCount) {
        int itg = Integer.parseInt(lotCount);
        try{
            LotWeight = itg;
            LotWeight = LotWeight / 7000;
        }catch(NumberFormatException nfe){
            LotWeight = 0;
        }
    }
 
    public void setLotWeightInGrains(int lw) {
	LotWeight = lw / 7000;
    }
	
	// Sets the total weight of the lot in Lbs.
    public void setLotWeightInLbs(String lotCount) {
        int itg = Integer.parseInt(lotCount);
        try{
            LotWeight = itg;
        }catch(NumberFormatException nfe){
            LotWeight = 0;
        }
    }
 
    public void setLotWeightInLbs(int lw) {
	LotWeight = lw;
    }
	
	// Returns the total cost of the lot.
    public float getLotCost() {
	return LotCost;
    }
	
	// Sets how much the lot cost.
    public void setLotCost(String bc) {
       	if(bc.contains("$")) {
    		bc = bc.substring(2);
    	}
	
        Float fbc = Float.parseFloat(bc);
        try{
            LotCost = fbc.floatValue();
        }catch(NumberFormatException nfe){
            LotCost = 0;
        }
    }

    public void setLotCost(float lc) {
	LotCost = lc;
    }
	
	// This is the cost per grain.
    public float getCostPerGrain() {
	return CostPerGrain;
    }
	
	// Returns if the state of empty.
    public boolean getEmpty() {
	return Empty;
    }
	
	// Set the lot as empty.
    public void setEmpty (boolean e) {
	Empty = e;
    }
	
	// is the powder lot all used up.
    public boolean isEmpty() {
	return Empty;
    }
	
    // Calculates the cost per grain.
    public float setCostPerGrain()
	{
            if(LotCost <= 0)
		return 0;
            if(LotWeight <= 0)
		return 0;
            CostPerGrain = LotCost / (LotWeight * 7000);
            return CostPerGrain;
	}
	
    public void setCostPerGrain(String bc) {
       	if(bc.contains("$")) {
    		bc = bc.substring(2);
    	}

        Float fbc = Float.parseFloat(bc);
        try{
            CostPerGrain = fbc.floatValue();
        } catch(NumberFormatException nfe){
            CostPerGrain = 0;
        }
    }

    public void setCostPerGrain(float cpg)
    {
        CostPerGrain = cpg;
    }
	
	// Format printout on the class.
    public String toString() {
	String output =   "   Powder Maker: "+PowderMaker+"\n";
	output = output + "    Powder Name: "+PowderName+"\n";
	output = output + "   Powder Lot #: "+PowderLotNumber+"\n";
	output = output + "     Lot Weight: "+LotWeight+"\n";
	output = output + "       Lot Cost: "+LotCost+"\n";
	output = output + "     Cost/Grain: "+CostPerGrain+"\n";
        output = output + " Date Purchased: "+DatePurchased+"\n";
	if(isEmpty()){ 
            output = output + "    Is empty\n"; 
        } else { 
            output = output + "   Not empty\n"; 
        }
	return output;
    }
        
    public String toXML() {
        String xmlData = "<Powder>\n";
        xmlData = xmlData + "\t<PowderMaker>"+PowderMaker+"</PowderMaker>\n";
        xmlData = xmlData + "\t<PowderName>"+PowderName+"</PowderName>\n";
        xmlData = xmlData + "\t<PowderLotNumber>"+PowderLotNumber+"</PowderLotNumber>\n";
        xmlData = xmlData + "\t<LotWeightlbs>"+LotWeight+"</LotWeightlbs>\n";
        xmlData = xmlData + "\t<LotCost>"+LotCost+"</LotCost>\n";
        xmlData = xmlData + "\t<CostPerGrain>"+CostPerGrain+"</CostPerGrain>\n";
        xmlData = xmlData + "\t<PurchaseDate>"+DatePurchased+"</PurchaseDate>\n";
        if(isEmpty())
            xmlData = xmlData + "\t<isEmpty>true</isEmpty>\n";
        else
            xmlData = xmlData + "\t<isEmpty>false</isEmpty>\n";
        xmlData = xmlData + "</Powder>\n";    
            
        return xmlData;
    }
        
    public boolean xmlCheck(String xml)
        {
        if(xml.indexOf("<Powder>") == -1)
            return false;
        if(xml.indexOf("<PowderMaker>") == -1)
            return false;
        if(xml.indexOf("</PowderMaker>") == -1)
            return false;
        if(xml.indexOf("<PowderName>") == -1)
            return false;
        if(xml.indexOf("</PowderName>") == -1)
            return false;
        if(xml.indexOf("<PowderLotNumber>") == -1)
            return false;
        if(xml.indexOf("</PowderLotNumber>") == -1)
                return false;
        if(xml.indexOf("<LotWeightlbs>") == -1)
            return false;
        if(xml.indexOf("</LotWeightlbs>") == -1)
            return false;
        if(xml.indexOf("<LotCost>") == -1)
            return false;
        if(xml.indexOf("</LotCost>") == -1)
            return false;
        if(xml.indexOf("<CostPerGrain>") == -1)
            return false;
        if(xml.indexOf("</CostPerGrain>") == -1)
            return false;
        if(xml.indexOf("<PurchaseDate>") == -1)
            return false;
        if(xml.indexOf("</PurchaseDate>") == -1)
            return false;
        if(xml.indexOf("<isEmpty>") == -1)
            return false;
        if(xml.indexOf("</isEmpty>") == -1)
            return false;
        if(xml.indexOf("</Powder>") == -1)
            return false;    

        return true;
    }
        
    public boolean isValid() {
        if (PowderMaker.trim().equals("")){
            return false;}
        else if (PowderName.trim().equals("")){
            return false;}
        else if (PowderLotNumber.trim().equals("")){
            return false;}
        else if (LotWeight < 0){
            return false;}
        else if (LotCost < 0){
            return false;}
        else if (DatePurchased.trim().equals("")){
            return false;}            
        else
            return true;
    }

}

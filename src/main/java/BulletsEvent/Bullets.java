/*
 * Bullets.java
 *
 * Created on July 1, 2007, 12:13 PM
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

package BulletsEvent;

import BaseClasses.CalendarTest;

/**
 *
 * @author Todd Brenneman
 */
public class Bullets {
    private String 	BulletMaker = "";
    private float	BC = (float)0.0;
    private String	Caliber = "";
    private String	Weight = "";
    private String	Description = "";
    private String	LotNumber = "";
    private int		LotCount = 0;
    private float	LotCost = (float)0.0;
    private float	CostPerBullet = (float)0.0;
    private String 	CastAlloy = "";
    private boolean	Empty = false;
    private int         MoldNumber = 0;
	
    public Bullets() {
    }
        
    public Bullets(String xml) {
        if(xmlCheck(xml))
        {
            String xmlData = xml.substring(xml.indexOf("<Bullets>"),xml.indexOf("</Bullets>"));
            setBulletMaker(xmlData.substring((xmlData.indexOf("<BulletMaker>") + 13), xmlData.indexOf("</BulletMaker>")));
            setBC(xmlData.substring((xmlData.indexOf("<BC>") + 4), xmlData.indexOf("</BC>")));
            setCaliber(xmlData.substring((xmlData.indexOf("<Caliber>") + 9), xmlData.indexOf("</Caliber>")));
            setWeight(xmlData.substring((xmlData.indexOf("<Weight>") + 8), xmlData.indexOf("</Weight>")));
            setDescription(xmlData.substring((xmlData.indexOf("<Description>") + 13), xmlData.indexOf("</Description>")));
            setLotNumber(xmlData.substring((xmlData.indexOf("<LotNumber>") + 11), xmlData.indexOf("</LotNumber>")));
            setLotCount(xmlData.substring((xmlData.indexOf("<LotCount>") + 10), xmlData.indexOf("</LotCount>")));
            setLotCost(xmlData.substring((xmlData.indexOf("<LotCost>") + 9), xmlData.indexOf("</LotCost>")));
            setCostPerBullet(xmlData.substring((xmlData.indexOf("<CostPerBullet>") + 15), xmlData.indexOf("</CostPerBullet>")));
            setCastAlloy(xmlData.substring((xmlData.indexOf("<CastAlloy>") + 11), xmlData.indexOf("</CastAlloy>")));
            setMoldNumber(xmlData.substring((xmlData.indexOf("<MoldNumber>") + 12), xmlData.indexOf("</MoldNumber>")));
            setEmpty(xmlData.substring((xmlData.indexOf("<Empty>")+7), xmlData.indexOf("</Empty>")).equalsIgnoreCase("true")); 
        }
    }

    /**
     * @return Returns the bC.
     */
    public float getBC() {
        return BC;
    }

    /**
     * @param bc The bC to set.
     */
    public void setBC(float bc) {
        BC = bc;
    }

    public void setBC(String bc) {
        Float fbc = Float.parseFloat(bc);
        try{
            BC = fbc.floatValue();
        }catch(NumberFormatException nfe){
            BC = 0;
        }
    }

    /**
     * @return Returns the bulletMaker.
     */
    public String getBulletMaker() {
        return BulletMaker;
    }

    public String getNewLotNumber() {
        CalendarTest ct = new CalendarTest();
        return ct.timeStamp();		
    }

    /**
     * @param bulletMaker The bulletMaker to set.
     */
    public void setBulletMaker(String bulletMaker) {
        BulletMaker = bulletMaker;
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
     * @return Returns the castAlloy.
     */
    public String getCastAlloy() {
        return CastAlloy;
    }

    /**
     * @param castAlloy The castAlloy to set.
     */
    public void setCastAlloy(String castAlloy) {
        CastAlloy = castAlloy;
    }

    /**
     * @return Returns the costPerBullet.
     */
    public float getCostPerBullet() {
        return CostPerBullet;
    }

    /**
     * @param costPerBullet The costPerBullet to set.
     */
    public float setCostPerBullet() {
        if(LotCost <= 0)
            return 0;
        if(LotCount <= 0)
            return 0;
        CostPerBullet = LotCost / LotCount;
        return CostPerBullet;
    }

    public void setCostPerBullet(float cpb)
    {
        CostPerBullet = cpb;
    }

    public void setCostPerBullet(String bc) {
        if(bc.contains("$")) {
            bc = bc.substring(2);
        }
        Float fbc = Float.parseFloat(bc);
        try{
            CostPerBullet = fbc.floatValue();
        }catch(NumberFormatException nfe){
            CostPerBullet = 0;
        }
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * @return Returns the empty.
     */
    public boolean isEmpty() {
        return Empty;
    }

    /**
     * @param empty The empty to set.
     */
    public void setEmpty(boolean empty) {
        Empty = empty;
    }

    /**
     * @return Returns the lotCost.
     */
    public float getLotCost() {
        return LotCost;
    }

    /**
     * @param lotCost The lotCost to set.
     */
    public void setLotCost(float lotCost) {
        LotCost = lotCost;
    }

    public void setLotCost(String bc) {
    	if(bc.contains("$")) {
            bc = bc.substring(1);
    	}

        try{
            LotCost = Float.parseFloat(bc);
        }catch(NumberFormatException nfe){
            LotCost = 0;
        }
    }
    /**
     * @return Returns the lotCount.
     */
    public int getLotCount() {
        return LotCount;
    }

    /**
     * @param lotCount The lotCount to set.
     */
    public void setLotCount(int lotCount) {
        LotCount = lotCount;
    }

    public void setLotCount(String lotCount) {
        Integer itg = Integer.parseInt(lotCount);
        try{
        	LotCount = itg.intValue();
        }catch(NumberFormatException nfe){
            LotCount = 0;
        }
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
     * @return Returns the weight.
     */
    public String getWeight() {
        return Weight;
    }

    public float getWeightFloat( ) {
        float t1;

        try {
            t1 = Float.parseFloat(getWeight());
        } catch (Exception e) {
            return 0;
        }
        return t1;
    }

    public int getMoldNumber()
    {
        return MoldNumber;
    }
        
    public void setMoldNumber(String num)
    {
        try{
            Integer itg = Integer.parseInt(num);
            MoldNumber = itg.intValue();
        }catch(NumberFormatException nfe){
            MoldNumber = 0;
        }
    }
        
    public void setMoldNumber(int num)
    {
        MoldNumber = num;
    }
        
    /**
     * @param weight The weight to set.
     */
    public void setWeight(String weight) {
        Weight = weight;
    }
    
     public String toString()
    {
        String   output = "          BulletMaker: "+BulletMaker+"\n";
        output = output + " BallisticCoefficient: "+BC+"\n";
        output = output + "              Caliber: "+Caliber+"\n";
        output = output + "               Weight: "+Weight+"\n";
        output = output + "          Description: "+Description+"\n";
        output = output + "            LotNumber: "+LotNumber+"\n"; 
        output = output + "             LotCount: "+LotCount+"\n"; 
        output = output + "              LotCost: "+LotCost+"\n"; 
        output = output + "        CostPerBullet: "+CostPerBullet+"\n";
        output = output + "            CastAlloy: "+CastAlloy+"\n";
        output = output + "                Empty: "+Empty+"\n";
        output = output + "           MoldNumber: "+MoldNumber+"\n";
        return output;
}
        
    public boolean isValidBullet()
    {
        if (BulletMaker.trim().equals("")){
           return false;}
        else if (Caliber.trim().equals("")){
           return false;}
        else if (Weight.trim().equals("")){
           return false;}
        else if (Description.trim().equals("")){
           return false;}
        else if (LotNumber.trim().equals("")){
           return false;}
        else if (LotCount < 0){
           return false;}
        else if (LotCost < 0){
           return false;}
        else
           return true;
    }

    public String toXML()
    {
        String XMLout = "<Bullets>\n";
        XMLout = XMLout + "\t<BulletMaker>"+BulletMaker+"</BulletMaker>\n";
        XMLout = XMLout + "\t<BC>"+BC+"</BC>\n";
        XMLout = XMLout + "\t<Caliber>"+Caliber+"</Caliber>\n";
        XMLout = XMLout + "\t<Weight>"+Weight+"</Weight>\n";
        XMLout = XMLout + "\t<Description>"+Description+"</Description>\n";
        XMLout = XMLout + "\t<LotNumber>"+LotNumber+"</LotNumber>\n";
        XMLout = XMLout + "\t<LotCount>"+LotCount+"</LotCount>\n";
        XMLout = XMLout + "\t<LotCost>"+LotCost+"</LotCost>\n";
        XMLout = XMLout + "\t<CostPerBullet>"+CostPerBullet+"</CostPerBullet>\n";
        XMLout = XMLout + "\t<CastAlloy>"+CastAlloy+"</CastAlloy>\n";
        XMLout = XMLout + "\t<MoldNumber>"+MoldNumber+"</MoldNumber>\n";
        if(Empty)
            XMLout = XMLout + "\t<Empty>true</Empty>\n";
        else
            XMLout = XMLout + "\t<Empty>false</Empty>\n";
        XMLout = XMLout + "</Bullets>\n";

        return XMLout;
    }

    public boolean xmlCheck(String xml)
    {
        if(xml.indexOf("<Bullets>") == -1)
            return false;
        if(xml.indexOf("<BulletMaker>") == -1)
            return false;
        if(xml.indexOf("</BulletMaker>") == -1)
            return false;
        if(xml.indexOf("<BC>") == -1)
            return false;
        if(xml.indexOf("</BC>") == -1)
            return false;
        if(xml.indexOf("<Caliber>") == -1)
            return false;
        if(xml.indexOf("</Caliber>") == -1)
            return false;
        if(xml.indexOf("<Weight>") == -1)
            return false;
        if(xml.indexOf("</Weight>") == -1)
            return false;
        if(xml.indexOf("<Description>") == -1)
            return false;
        if(xml.indexOf("</Description>") == -1)
            return false;
        if(xml.indexOf("<LotNumber>") == -1)
            return false;
        if(xml.indexOf("</LotNumber>") == -1)
            return false;
        if(xml.indexOf("<LotCount>") == -1)
            return false;
        if(xml.indexOf("</LotCount>") == -1)
            return false;
        if(xml.indexOf("<LotCost>") == -1)
            return false;
        if(xml.indexOf("</LotCost>") == -1)
            return false;
        if(xml.indexOf("<CostPerBullet>") == -1)
            return false;
        if(xml.indexOf("</CostPerBullet>") == -1)
            return false;
        if(xml.indexOf("<CastAlloy>") == -1)
            return false;
        if(xml.indexOf("</CastAlloy>") == -1)
            return false;
        if(xml.indexOf("<MoldNumber>") == -1)
            return false;
        if(xml.indexOf("</MoldNumber>") == -1)
            return false;
        if(xml.indexOf("<Empty>") == -1)
            return false;
        if(xml.indexOf("</Empty>") == -1)
            return false;
        if(xml.indexOf("</Bullets>") == -1)
            return false;

        return true;
    }
}

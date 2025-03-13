/*
 * Cases.java
 * 
 * Created on July 1, 2007, 12:47 PM
 * 
 * This the Base class used to hold Case data that is retrieved from the Ammo Database.
 */
/*
+-------------+-------------+------+-----+---------+-------+
| Field       | Type        | Null | Key | Default | Extra |
+-------------+-------------+------+-----+---------+-------+
| CaseMaker   | varchar(50) |      |     |         |       |
| Caliber     | varchar(25) |      |     |         |       |
| LotNumber   | varchar(30) |      | PRI |         |       |
| Type        | varchar(25) |      |     |         |       |
| LotCount    | int(11)     |      |     | 0       |       |
| LotCost     | float       |      |     | 0       |       |
| CostPerCase | float       |      |     | 0       |       |
+-------------+-------------+------+-----+---------+-------+
*/

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CasesEvent;

import BaseClasses.CalendarTest;

/**
 *
 * @author Todd Brenneman
 */
public class Cases {
    private String	CaseMaker;
    private String	Caliber;
    private String	LotNumber;
    private String	Type;
    private int         LotCount;
    private float	LotCost;
    private float	CostPerCase;
    private boolean     isEmpty;

    public Cases() 
    {
    }

    public Cases(String xml)
    {
        if (xmlCheck(xml))
        {
            String xmlData = (xml.substring(xml.indexOf("<Cases>"),xml.indexOf("</Cases>")));
            setCaseMaker(xmlData.substring((xmlData.indexOf("<CaseMaker>") + 11), xmlData.indexOf("</CaseMaker>")));
            setCaliber(xmlData.substring((xmlData.indexOf("<Caliber>") + 9), xmlData.indexOf("</Caliber>")));
            setLotNumber(xmlData.substring((xmlData.indexOf("<LotNumber>") + 11), xmlData.indexOf("</LotNumber>")));
            setType(xmlData.substring((xmlData.indexOf("<Type>") + 6), xmlData.indexOf("</Type>")));
            setLotCount(xmlData.substring((xmlData.indexOf("<LotCount>") + 10), xmlData.indexOf("</LotCount>")));
            setLotCost(xmlData.substring((xmlData.indexOf("<LotCost>") + 9), xmlData.indexOf("</LotCost>")));
            setCostPerCase(xmlData.substring((xmlData.indexOf("<CostPerCase>") + 13), xmlData.indexOf("</CostPerCase>")));
            setIsEmpty(xmlData.substring((xmlData.indexOf("<isEmpty>") + 13), xmlData.indexOf("</isEmpty>")));
        }
    }
        
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
     * @return Returns the caseMaker.
     */
    public String getCaseMaker() {
        return CaseMaker;
    }

    /**
     * @param caseMaker The caseMaker to set.
     */
    public void setCaseMaker(String caseMaker) {
        CaseMaker = caseMaker;
    }

    /**
     * @return Returns the costPerCase.
     */
    public float getCostPerCase() {
        return CostPerCase;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void setIsEmpty(String ie) {
        if(ie.equals("1"))
            this.isEmpty = true;
        else
            this.isEmpty = false;           
    }

     public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * @param costPerCase The costPerCase to set.
     */
    public void setCostPerCase(String bc) {
        if(bc.contains("$")) {
                bc=bc.substring(1);
        };

        Float fbc = Float.parseFloat(bc);
        try{
            CostPerCase = fbc.floatValue();
        }catch(NumberFormatException nfe){
            CostPerCase = 0;
        }
    }
 
    public float setCostPerCase() {
        if(LotCost <= 0)
           return 0;
        if(LotCount <= 0)
           return 0;
        CostPerCase = LotCost / LotCount;
        return CostPerCase;
    }

    public String getNewLotNumber() {
        CalendarTest ct = new CalendarTest();
        return ct.timeStamp();		
    }

    public void setCostPerCase(float cpc)
    {
        CostPerCase = cpc;
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
    public void setLotCost(String bc) {
            if(bc.contains("$")) {
                    bc = bc.substring(1);
            }
        Float fbc = Float.parseFloat(bc);
        try{
            LotCost = fbc.floatValue();
        }catch(NumberFormatException nfe){
            LotCost = 0;
        }
    }

    public void setLotCost(float lotCost) {
        LotCost = lotCost;
    }

    /**
     * @return Returns the lotCount.
     */
    public int getLotCount() {
        return LotCount;
    }

    public void setLotCount(String lc) {
        Integer itg = Integer.parseInt(lc);
        try{
            LotCount = itg.intValue();
        }catch(NumberFormatException nfe){
            LotCount = 0;
        }
    }

   public void setLotCount(int lotCount) {
        LotCount = lotCount;
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
     * @return Returns the type.
     */
    public String getType() {
        return Type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        Type = type;
    }

    public String toString()
    {
        String   output = "  CaseMaker: "+CaseMaker+"\n";
        output = output + "    Caliber: "+Caliber+"\n";
        output = output + "  LotNumber: "+LotNumber+"\n";
        output = output + "       Type: "+Type+"\n";
        output = output + "   LotCount: "+LotCount+"\n";
        output = output + "    LotCost: "+LotCost+"\n";
        output = output + "CostPerCase: "+CostPerCase+"\n";
        output = output + "   Is Empty: "+isEmpty+"\n";
        return output;
    }

    public boolean isValid() {
        if(CaseMaker.isBlank()) {
            System.err.println("CaseMaker fucked up.");
            return false;
        }
        if(Caliber.isBlank()) {
            System.err.println("Caliber fucked up.");
            return false;
        }
        if(LotNumber.isBlank()) {
            System.err.println("LotNumber fucked up.");
            return false;
        }
        if(Type.isBlank()) {
            System.err.println("Type fucked up.");
            return false;
        }
        if(LotCount < 0) {
            System.err.println("LotCount fucked up.");
            return false;
        }
        if(LotCost < 0) {
            System.err.println("LotCost fucked up.");
            return false;
        }
        this.setCostPerCase();
        return true;
    }

    public String toXML()
    {
        String xmlData = "<Cases>\n";
        xmlData = xmlData + "\t<CaseMaker>"+CaseMaker+"</CaseMaker>\n";
        xmlData = xmlData + "\t<Caliber>"+Caliber+"</Caliber>\n";
        xmlData = xmlData + "\t<LotNumber>"+LotNumber+"</LotNumber>\n";
        xmlData = xmlData + "\t<Type>"+Type+"</Type>\n";
        xmlData = xmlData + "\t<LotCount>"+LotCount+"</LotCount>\n";
        xmlData = xmlData + "\t<LotCost>"+LotCost+"</LotCost>\n";
        xmlData = xmlData + "\t<CostPerCase>"+CostPerCase+"</CostPerCase>\n";
        xmlData = xmlData + "\t<isEmpty>"+isEmpty+"</isEmpty>\n";
        xmlData = xmlData + "</Cases>\n";

        return xmlData;
    }

    public boolean xmlCheck(String xml)
    {
        if(xml.indexOf("<Cases>") == -1)
            return false;
        if(xml.indexOf("<CaseMaker>") == -1)
            return false;
        if(xml.indexOf("</CaseMaker>") == -1)
            return false;
        if(xml.indexOf("<Caliber>") == -1)
            return false;
        if(xml.indexOf("</Caliber>") == -1)
            return false;
        if(xml.indexOf("<LotNumber>") == -1)
            return false;
        if(xml.indexOf("</LotNumber>") == -1)
            return false;
        if(xml.indexOf("<Type>") == -1)
            return false;
        if(xml.indexOf("</Type>") == -1)
            return false;
        if(xml.indexOf("<LotCount>") == -1)
            return false;
        if(xml.indexOf("</LotCount>") == -1)
            return false;
        if(xml.indexOf("<LotCost>") == -1)
            return false;
        if(xml.indexOf("</LotCost>") == -1)
            return false;
        if(xml.indexOf("<CostPerCase>") == -1)
            return false;
        if(xml.indexOf("</CostPerCase>") == -1)
            return false;
        if(xml.indexOf("</Cases>") == -1)
            return false;
       return true;
    }
}

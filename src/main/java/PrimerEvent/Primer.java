/*
 * Primer.java
 *
 * Created on July 1, 2007, 12:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/*
+---------------+-------------+------+-----+---------+-------+
| Field         | Type        | Null | Key | Default | Extra |
+---------------+-------------+------+-----+---------+-------+
| PrimerMaker   | varchar(50) |      |     |         |       |
| Size          | varchar(30) |      |     |         |       |
| ModelNumber   | varchar(30) |      |     |         |       |
| LotNumber     | varchar(50) |      | PRI |         |       |
| LotCount      | int(11)     |      |     | 0       |       |
| LotCost       | float       |      |     | 0       |       |
| CostPerPrimer | float       |      |     | 0       |       |
| Empty         | tinyint(1)  |      |     | 0       |       |
+---------------+-------------+------+-----+---------+-------+
*/
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrimerEvent;


/**
 *
 * @author Todd Brenneman
 */
public class Primer {
    private String 	PrimerMaker; 
    private String 	PrimerSize;
    private String 	ModelNumber;
    private String 	LotNumber;
    private int	LotCount;
    private float	LotCost;
    private float	CostPerPrimer;
    private boolean Empty;

    public Primer() {
    }

    public Primer(String xml){
        if(xmlCheck(xml))
        {
            String xmlData = xml.substring(xml.indexOf("<Primer>"), xml.indexOf("</Primer>"));
            setPrimerMaker(xmlData.substring((xmlData.indexOf("<PrimerMaker>")+13), xmlData.indexOf("</PrimerMaker>")));
            setPrimerSize(xmlData.substring((xmlData.indexOf("<PrimerSize>")+12), xmlData.indexOf("</PrimerSize>")));
            setModelNumber(xmlData.substring((xmlData.indexOf("<ModelNumber>")+13), xmlData.indexOf("</ModelNumber>")));
            setLotNumber(xmlData.substring((xmlData.indexOf("<LotNumber>")+11), xmlData.indexOf("</LotNumber>")));
            setLotCount(xmlData.substring((xmlData.indexOf("<LotCount>")+10), xmlData.indexOf("</LotCount>")));
            setLotCost(xmlData.substring((xmlData.indexOf("<LotCost>")+9), xmlData.indexOf("</LotCost>")));
            setCostPerPrimer(xmlData.substring((xmlData.indexOf("<CostPerPrimer>")+15), xmlData.indexOf("</CostPerPrimer>")));
            setEmpty(xmlData.substring((xmlData.indexOf("<Empty>")+7), xmlData.indexOf("</Empty>")).equalsIgnoreCase("true"));
       }
    }
        
    /**
     * @return Returns the costPerPrimer.
     */
    public float getCostPerPrimer() {
        return CostPerPrimer;
    }

    /**
     * @param costPerPrimer The costPerPrimer to set.
     */
    public void setCostPerPrimer(String bc) {
        Float fbc = Float.parseFloat(bc);
        try{
            CostPerPrimer = fbc.floatValue();
        }catch(NumberFormatException nfe){
            CostPerPrimer = 0;
        }
    }

    public float setCostPerPrimer() 
    {
        if(LotCost <= 0)
                return 0;
        if(LotCount <= 0)
                return 0;
        CostPerPrimer = LotCost / LotCount;
        return CostPerPrimer;
    }

    public void setCostPerPrimer(float cpp) 
    {
        CostPerPrimer = cpp;
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

    public void setLotCost(float lotCost) {
        LotCost = lotCost;
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
    public void setLotCount(String lotCount) {
        Integer itg = Integer.parseInt(lotCount);
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
     * @return Returns the modelNumber.
     */
    public String getModelNumber() {
        return ModelNumber;
    }

    /**
     * @param modelNumber The modelNumber to set.
     */
    public void setModelNumber(String modelNumber) {
        ModelNumber = modelNumber;
    }

    /**
     * @return Returns the primerMaker.
     */
    public String getPrimerMaker() {
        return PrimerMaker;
    }

    /**
     * @param primerMaker The primerMaker to set.
     */
    public void setPrimerMaker(String primerMaker) {
        PrimerMaker = primerMaker;
    }

    /**
     * @return Returns the primerSize.
     */
    public String getPrimerSize() {
        return PrimerSize;
    }

    /**
     * @param primerSize The primerSize to set.
     */
    public void setPrimerSize(String primerSize) {
        PrimerSize = primerSize;
    }

    @Override
    public String toString()
    {
        String output = new String();
        output = output + "   PrimerMaker : "+PrimerMaker+"\n"; 
        output = output + "    PrimerSize : "+PrimerSize+"\n";
        output = output + "   ModelNumber : "+ModelNumber+"\n";
        output = output + "     LotNumber : "+LotNumber+"\n";
        output = output + "      LotCount : "+LotCount+"\n";
        output = output + "       LotCost : "+LotCost+"\n";
        output = output + " CostPerPrimer : "+CostPerPrimer+"\n";
        output = output + "         Empty : "+Empty+"\n";
        return output;
    }  
        
    public String toXML()
    {
        String xmlData = "<Primer>\n";
        xmlData = xmlData + "\t<PrimerMaker>"+PrimerMaker+"</PrimerMaker>\n";
        xmlData = xmlData + "\t<PrimerSize>"+PrimerSize+"</PrimerSize>\n";
        xmlData = xmlData + "\t<ModelNumber>"+ModelNumber+"</ModelNumber>\n";
        xmlData = xmlData + "\t<LotNumber>"+LotNumber+"</LotNumber>\n";
        xmlData = xmlData + "\t<LotCount>"+LotCount+"</LotCount>\n";
        xmlData = xmlData + "\t<LotCost>"+LotCost+"</LotCost>\n";
        xmlData = xmlData + "\t<CostPerPrimer>"+CostPerPrimer+"</CostPerPrimer>\n";
        if(Empty)
            xmlData = xmlData + "\t<Empty>"+true+"</Empty>\n";
        else
            xmlData = xmlData + "\t<Empty>"+false+"</Empty>\n";
        xmlData = xmlData + "</Primer>\n";

        return xmlData;
    }
        
        
    public boolean isValid(){
        if(PrimerMaker.trim().equals(""))
            return false; 

        if(PrimerSize.trim().equals(""))
            return false;

        if(ModelNumber.trim().equals(""))
            return false;

        if(LotNumber.trim().equals(""))
            return false;

        if(LotCount < 0)
            return false;

        if(LotCost < 0)
            return false;

        if(CostPerPrimer < 0)
            return false;

        return true;

    }
        
    public boolean xmlCheck(String xml)
    {
        if(xml.indexOf("<Primer>") == -1)
            return false;
        if(xml.indexOf("<PrimerMaker>") == -1)
            return false;
        if(xml.indexOf("</PrimerMaker>") == -1)
            return false;
        if(xml.indexOf("<PrimerSize>") == -1)
            return false;
        if(xml.indexOf("</PrimerSize>") == -1)
            return false;
        if(xml.indexOf("<ModelNumber>") == -1)
            return false;
        if(xml.indexOf("</ModelNumber>") == -1)
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
        if(xml.indexOf("<CostPerPrimer>") == -1)
            return false;
        if(xml.indexOf("</CostPerPrimer>") == -1)
            return false;
        if(xml.indexOf("<Empty>") == -1)
            return false;
        if(xml.indexOf("</Empty>") == -1)
            return false;
        if(xml.indexOf("</Primer>") == -1)
            return false;

        return true;
    }
}

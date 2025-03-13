/*
 * ReloadingDies.java
 *
 * Created on August 19, 2007, 4:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
// +----------+--------------+------+-----+---------+----------------+
// | Field    | Type         | Null | Key | Default | Extra          |
// +----------+--------------+------+-----+---------+----------------+
// | ID       | int(11)      |      | PRI | NULL    | auto_increment |
// | Maker    | varchar(50)  |      |     |         |                |
// | Caliber  | varchar(100) |      |     |         |                |
// | Type     | varchar(50)  |      |     |         |                |
// | FLCSD    | tinyint(1)   |      |     | 0       |                |
// | Expander | tinyint(1)   |      |     | 0       |                |
// | Seater   | tinyint(1)   |      |     | 0       |                |
// | Roll     | tinyint(1)   |      |     | 0       |                |
// | Factory  | tinyint(1)   |      |     | 0       |                |
// | FLSSD    | tinyint(1)   |      |     | 0       |                |
// | Trim     | tinyint(1)   |      |     | 0       |                |
// +----------+--------------+------+-----+---------+----------------+
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReloadingDiesEvent;

/**
 *
 * @author Todd Brenneman
 */
public class ReloadingDies {
    private int     ID;
    private String  Maker;
    private String  Caliber;
    private String  Type;
    private boolean FLCSD;
    private boolean Expander;
    private boolean Seater;
    private boolean Roll;
    private boolean Factory;
    private boolean FLSSD;
    private boolean Trim;
    
    /** Creates a new instance of ReloadingDies */
    public ReloadingDies() {
    }
    
    public ReloadingDies(String xml)
    {
        if(xmlCheck(xml))
        {
            String xmlData = xml.substring(xml.indexOf("<ReloadingDies>"), xml.indexOf("</ReloadingDies>"));
            setID(xmlData.substring((xmlData.indexOf("<ID>")+4), xmlData.indexOf("</ID>")));
            setMaker(xmlData.substring((xmlData.indexOf("<Maker>")+7), xmlData.indexOf("</Maker>")));
            setCaliber(xmlData.substring((xmlData.indexOf("<Caliber>") + 9), xmlData.indexOf("</Caliber>")));
            setType(xmlData.substring((xmlData.indexOf("<Type>")+6), xmlData.indexOf("</Type>")));
            setFLCSD(xmlData.substring((xmlData.indexOf("<FLCSD>")+7), xmlData.indexOf("</FLCSD>")).equalsIgnoreCase("true"));
            setExpander(xmlData.substring((xmlData.indexOf("<Expander>")+10), xmlData.indexOf("</Expander>")).equalsIgnoreCase("true"));
            setSeater(xmlData.substring((xmlData.indexOf("<Seater>")+8), xmlData.indexOf("</Seater>")).equalsIgnoreCase("true"));
            setRoll(xmlData.substring((xmlData.indexOf("<Roll>")+6), xmlData.indexOf("</Roll>")).equalsIgnoreCase("true"));
            setFactory(xmlData.substring((xmlData.indexOf("<Factory>")+9), xmlData.indexOf("</Factory>")).equalsIgnoreCase("true"));
            setTrim(xmlData.substring((xmlData.indexOf("<Trim>")+6), xmlData.indexOf("</Trim>")).equalsIgnoreCase("true"));              
        }
    }
    
    public int getID()
    {
        return ID;
    }
    
    public String getIDString()
    {
        return String.valueOf(ID);
    }
    
    public void setID(int num)
    {
        ID = num;
    }
    
    public void setID(String num)
    {
        Integer itg = Integer.parseInt(num);
        try{
            ID = itg.intValue();
        }catch(NumberFormatException nfe){
            ID = 0;
        }
    }

    public String getMaker()
    {
        return Maker;
    }

    public void setMaker(String mk)
    {
        Maker = mk;
    }
    
    public String getCaliber()
    {
        return Caliber;
    }

    public void setCaliber(String mk)
    {
        Caliber = mk;
    }
    
    public String getType()
    {
        return Type;
    }

    public void setType(String mk)
    {
        Type = mk;
    }
    
    public void setFLCSD(boolean t)
    {
        FLCSD = t;
    }

    public boolean getFLCSD()
    {
        return FLCSD;
    }
    
    public void setExpander(boolean t)
    {
        Expander = t;
    }

    public boolean getExpander()
    {
        return Expander;
    }
    
    public void setSeater(boolean t)
    {
        Seater = t;
    }

    public boolean getSeater()
    {
        return Seater;
    }
    
    public void setRoll(boolean t)
    {
        Roll = t;
    }

    public boolean getRoll()
    {
        return Roll;
    }
    
    public void setFactory(boolean t)
    {
        Factory = t;
    }

    public boolean getFactory()
    {
        return Factory;
    }
    
    public void setFLSSD(boolean t)
    {
        FLSSD = t;
    }

    public boolean getFLSSD()
    {
        return FLSSD;
    }
    
    public void setTrim(boolean t)
    {
        Trim = t;
    }

    public boolean getTrim()
    {
        return Trim;
    }
    
    public boolean isValidReloadingDie()
    {
        if (Maker.trim().equalsIgnoreCase(""))
            return false;
        else if(Caliber.trim().equalsIgnoreCase(""))
            return false;
        else if(Type.trim().equalsIgnoreCase(""))
            return false;
        else
            return true;
    }
    
    public String toString()
    {
	String   output = "        ID: "+ID+"\n";
	output = output + "     Maker: "+Maker+"\n";
	output = output + "   Caliber: "+Caliber+"\n";
	output = output + "      Type: "+Type+"\n";
	output = output + "     FLCSD: "+FLCSD+"\n"; 
        output = output + "  Expander: "+Expander+"\n";
	output = output + "    Seater: "+Seater+"\n"; 
	output = output + "      Roll: "+Roll+"\n"; 
	output = output + "   Factory: "+Factory+"\n"; 
	output = output + "     FLSSD: "+FLSSD+"\n"; 
	output = output + "      Trim: "+Trim+"\n"; 
	return output;
    }

    public String toXML()
    {
        String xmlData = "<ReloadingDies>\n";
        xmlData = xmlData + "\t<ID>"+ID+"</ID>\n";
        xmlData = xmlData + "\t<Maker>"+Maker+"</Maker>\n";
        xmlData = xmlData + "\t<Caliber>"+Caliber+"</Caliber>\n";
        xmlData = xmlData + "\t<Type>"+Type+"</Type>\n";
        xmlData = xmlData + "\t<FLCSD>"+FLCSD+"</FLCSD>\n";
        xmlData = xmlData + "\t<Expander>"+Expander+"</Expander>\n";
        xmlData = xmlData + "\t<Seater>"+Seater+"</Seater>\n";
        xmlData = xmlData + "\t<Roll>"+Roll+"</Roll>\n";
        xmlData = xmlData + "\t<Factory>"+Factory+"</Factory>\n";
        xmlData = xmlData + "\t<FLSSD>"+FLSSD+"</FLSSD>\n";
        xmlData = xmlData + "\t<Trim>"+Trim+"</Trim>\n";
        xmlData = xmlData + "</ReloadingDies>\n";
        
        return xmlData;
    }
    
    public boolean xmlCheck(String xml)
    {
        if(xml.indexOf("<ReloadingDies>") <= -1)
            return false;
        if(xml.indexOf("<ID>") <= -1)
            return false;
        if(xml.indexOf("</ID>") <= -1)
            return false;
        if(xml.indexOf("<Maker>") <= -1)
            return false;
        if(xml.indexOf("</Maker>") <= -1)
            return false;
        if(xml.indexOf("<Caliber>") <= -1)
            return false;
        if(xml.indexOf("</Caliber>") <= -1)
            return false;
        if(xml.indexOf("<Type>") <= -1)
            return false;
        if(xml.indexOf("</Type>") <= -1)
            return false;
        if(xml.indexOf("<FLCSD>") <= -1)
            return false;
        if(xml.indexOf("</FLCSD>") <= -1)
            return false;
        if(xml.indexOf("<Expander>") <= -1)
            return false;
        if(xml.indexOf("</Expander>") <= -1)
            return false;
        if(xml.indexOf("<Seater>") <= -1)
            return false;
        if(xml.indexOf("</Seater>") <= -1)
            return false;
        if(xml.indexOf("<Roll>") <= -1)
            return false;
        if(xml.indexOf("</Roll>") <= -1)
            return false;
        if(xml.indexOf("<Factory>") <= -1)
            return false;
        if(xml.indexOf("</Factory>") <= -1)
            return false;
        if(xml.indexOf("<FLSSD>") <= -1)
            return false;
        if(xml.indexOf("</FLSSD>") <= -1)
            return false;
        if(xml.indexOf("<Trim>") <= -1)
            return false;
        if(xml.indexOf("</Trim>") <= -1)
            return false;
        if(xml.indexOf("</ReloadingDies>") <= -1)
            return false;
        
        return true;
    }
}

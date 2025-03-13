/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManufacturedAmmoEvent;


/************************************************************************************************
 * ManufacturedAmmo.java																		*
 * 																								*
 * Author: Todd Brenneman																		*
 * Date: 5/24/2024																				*
 * 																								*
 * This is the base class for ammunition made by a external manufacturer.  						*
 * 																								*
 * 	+---------------------+-------------+------+-----+---------+-------+						*
 *	| Field               | Type        | Null | Key | Default | Extra |						*
 *	+---------------------+-------------+------+-----+---------+-------+						*
 *	| LotNumber           | int(11)     | NO   | PRI | NULL    |       |						*
 *	| Caliber             | varchar(45) | NO   |     | NULL    |       |						*
 *	| Manufacturer        | varchar(45) | NO   |     | NULL    |       |						*
 *	| DatePurchased       | date        | NO   |     | NULL    |       |						*
 *	| Bullet              | varchar(45) | YES  |     | NULL    |       |						*
 *      | BulletWeight        | Float       | NO   |     |         |       |
 *	| Count               | int(11)     | YES  |     | NULL    |       |						*
 *	| Empty1              | tinyint(4)  | YES  |     | NULL    |       |						*
 *	+---------------------+-------------+------+-----+---------+-------+						*
 *																								*
 ***********************************************************************************************/

public class ManufacturedAmmo {
    private String 	lotNumber; 
    private String 	caliber;
    private String 	manufacturer;
    private String 	datePurchased;
    private String	bullet;
    private float       bulletWeight;
    private int 	count;
    private boolean     empty;  

    public ManufacturedAmmo() {
        lotNumber = new String(); 
        caliber = new String();
        manufacturer = new String();
        datePurchased = new String();
        bullet = new String();
        bulletWeight = (float)0.0;
        count = 0;
        empty = true;  
    }

    public ManufacturedAmmo(String ln, String cn, String mn, String dt) {
        lotNumber = ln; 
        caliber = cn;
        manufacturer = mn;
        datePurchased = dt;
        bullet = new String();
        bulletWeight = (float)0.0;
        count = 0;
        empty = true;  	
    }
	
    public ManufacturedAmmo(String ln, String cn, String mn, String dt, String bn) {
        lotNumber = ln; 
        caliber = cn;
        manufacturer = mn;
        datePurchased = dt;
        bullet = bn;
        bulletWeight = (float)0.0;
        count = 0;
        empty = true;  	
    }

    public ManufacturedAmmo(String ln, String cn, String mn, String dt, String bn, float bw) {
        lotNumber = ln; 
        caliber = cn;
        manufacturer = mn;
        datePurchased = dt;
        bullet = bn;
        bulletWeight = bw;
        count = 0;
        empty = true;  	
    }

    public ManufacturedAmmo(String ln, String cn, String mn, String dt, String bn, float bw, int ct) {
        lotNumber = ln; 
        caliber = cn;
        manufacturer = mn;
        datePurchased = dt;
        bullet = bn;
        bulletWeight = bw;
        count = ct;
        empty = true;  	
    }

    public ManufacturedAmmo(String ln, String cn, String mn, String dt, String bn, float bw, int ct, boolean mt) {
        lotNumber = ln; 
        caliber = cn;
        manufacturer = mn;
        datePurchased = dt;
        bullet = bn;
        bulletWeight = bw;
        count = ct;
        empty = mt;  	
    }

    public ManufacturedAmmo(String xml) {
            if(xmlCheck(xml))
        {
           String xmlData = xml.substring(xml.indexOf("<ManufacturedAmmo>"), xml.indexOf("</ManufacturedAmmo>"));
           setLotNumber(xmlData.substring((xmlData.indexOf("<LotNumber>")+11), xmlData.indexOf("</LotNumber>")));
           setManufacturer(xmlData.substring((xmlData.indexOf("<Manufacturer>")+14), xmlData.indexOf("</Manufacturer>")));
           setCaliber(xmlData.substring((xmlData.indexOf("<Caliber>") + 9), xmlData.indexOf("</Caliber>")));
           setDatePurchased(xmlData.substring((xmlData.indexOf("<Date>")+6), xmlData.indexOf("</Date>")));
           setBullet(xmlData.substring((xmlData.indexOf("<Bullet>")+8), xmlData.indexOf("</Bullet>")));
           setBulletWeight(xmlData.substring((xmlData.indexOf("<BulletWeight>")+14), xmlData.indexOf("</BulletWeight>")));
           setCount(xmlData.substring((xmlData.indexOf("<Count>")+7), xmlData.indexOf("</Count>")));
           setEmpty(xmlData.substring((xmlData.indexOf("<Empty>")+7), xmlData.indexOf("</Empty>")).equalsIgnoreCase("true"));
        }
    }

    public float getBulletWeight() {
        return bulletWeight;
    }

    public void setBulletWeight(float bulletWeight) {
        this.bulletWeight = bulletWeight;
    }

    public void setBulletWeight(String bW) {
        this.bulletWeight = Float.parseFloat(bW);
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getBullet() {
        return bullet;
    }

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }
	
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount(String count) {
        this.count = Integer.parseInt(count);
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isValid() {
        return true;
    }

    @Override
    public String toString()
    {
        String   output = "    Lot Number: "+getLotNumber()+"\n";
        output = output + "  Manufacturer: "+getManufacturer()+"\n";
        output = output + "       Caliber: "+getCaliber()+"\n";
        output = output + "          Date: "+getDatePurchased()+"\n";
        output = output + "        Bullet: "+getBullet()+"\n"; 
        output = output + " Bullet Weight: "+getBulletWeight()+"\n"; 
        output = output + "         Count: "+getCount()+"\n";
        output = output + "      Is Empty: "; 
        if(isEmpty()) 
            output = output + "true\n";        	
        else
            output = output + "false\n";        	
        	
        return output;
    }

    public String toXML()
    {
        String xmlData = "<ManufacturedAmmo>\n";
        xmlData = xmlData + "\t<LotNumber>"+getLotNumber()+"</LotNumber>\n";
        xmlData = xmlData + "\t<Manufacturer>"+getManufacturer()+"</Manufacturer>\n";
        xmlData = xmlData + "\t<Caliber>"+getCaliber()+"</Caliber>\n";
        xmlData = xmlData + "\t<Date>"+getDatePurchased()+"</Date>\n";
        xmlData = xmlData + "\t<Bullet>"+getBullet()+"</Bullet>\n";
        xmlData = xmlData + "\t<BulletWeight>"+getBulletWeight()+"</BulletWeight>\n";
       xmlData = xmlData + "\t<Count>"+getCount()+"</Count>\n";
        xmlData = xmlData + "\t<Empty>";
        if(isEmpty()) 
            xmlData = xmlData + "true";        	
        else
            xmlData = xmlData + "false";        	
        	
        xmlData = xmlData + "</Empty>\n";
        xmlData = xmlData + "</ManufacturedAmmo>\n";
        
        return xmlData;
    }
    
    public boolean xmlCheck(String xml)
    {
        if(xml.indexOf("<ManufacturedAmmo>") <= -1)
            return false;
        if(xml.indexOf("<LotNumber>") <= -1)
            return false;
        if(xml.indexOf("</LotNumber>") <= -1)
            return false;
        if(xml.indexOf("<Manufacturer>") <= -1)
            return false;
        if(xml.indexOf("</Manufacturer>") <= -1)
            return false;
        if(xml.indexOf("<Caliber>") <= -1)
            return false;
        if(xml.indexOf("</Caliber>") <= -1)
            return false;
        if(xml.indexOf("<Date>") <= -1)
            return false;
        if(xml.indexOf("</Date>") <= -1)
            return false;
        if(xml.indexOf("<Bullet>") <= -1)
            return false;
        if(xml.indexOf("</Bullet>") <= -1)
            return false;
        if(xml.indexOf("<BulletWeight>") <= -1)
            return false;
        if(xml.indexOf("</BulletWeight>") <= -1)
            return false;
        if(xml.indexOf("<Count>") <= -1)
            return false;
        if(xml.indexOf("</Count>") <= -1)
            return false;
        if(xml.indexOf("<Empty>") <= -1)
            return false;
        if(xml.indexOf("</Empty>") <= -1)
            return false;
        if(xml.indexOf("</ManufacturedAmmo>") <= -1)
            return false;
        
        return true;
    }
    
    public static void main(String ARGV[]) {
    	ManufacturedAmmo ma = new ManufacturedAmmo();
    	ma.setLotNumber("1");
    	ma.setManufacturer("Ammo Inc");
    	ma.setCaliber("300 Blackout");
    	ma.setDatePurchased("2024-12-12");
    	ma.setBullet("150 gn FMJ");
        ma.setBulletWeight((float)150.0);
    	ma.setCount(20);
    	ma.setEmpty(false);
    	System.out.println(ma);
    	String xml = ma.toXML();
    	System.out.println(xml);
    	ma = new ManufacturedAmmo(xml);
    	System.out.println(ma);
    }
}

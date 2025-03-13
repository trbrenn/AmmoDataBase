/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BulletMoldEvent;

import BaseClasses.FormatFloat;

/**
 *
 * @author trbrenn
 */

public class BulletMold {
    private int ID;
    private String maker;
    private String number;
    private String description;
    private float diameter;
    private float weight;
    private String topPunch;
    private boolean gasCheck;
    private FormatFloat ff = new FormatFloat();
	
    public BulletMold() {
	ID = 0;
	maker = new String("");
	number = new String("");
	description = new String("");
	diameter = 0;
	weight = 0;
	topPunch = new String("");
	gasCheck = false;		
    }
	
    public BulletMold(int i, String m, String n, String d, String tp, float di, float w) {
	ID = i;
	maker = m;
	number = n;
	description = d;
	diameter = di;
	weight = w;
	topPunch = tp;
	gasCheck = false;		
    }
	
    public BulletMold(int i, String m, String n, String d, String tp, float di, float w, boolean gc) {
	ID = i;
	maker = m;
	number = n;
	description = d;
	diameter = di;
	weight = w;
	topPunch = tp;
	gasCheck = gc;		
    }
	
    public BulletMold(String xml) {
        if (xmlCheck(xml)) // Checks to make sure all tags are included.
        {
            String xmlData = xml.substring(xml.indexOf("<BulletMolds>"), xml.indexOf("</BulletMolds>"));
            setID(xmlData.substring((xmlData.indexOf("<ID>")+4), xmlData.indexOf("</ID>")));
            setMaker(xmlData.substring((xmlData.indexOf("<Maker>")+7), xmlData.indexOf("</Maker>")));
            setNumber(xmlData.substring((xmlData.indexOf("<Number>")+8), xmlData.indexOf("</Number>")));
            setDescription(xmlData.substring((xmlData.indexOf("<Description>")+13), xmlData.indexOf("</Description>")));
            setDiameter(xmlData.substring((xmlData.indexOf("<Diameter>")+10), xmlData.indexOf("</Diameter>")));
            setWeight(xmlData.substring((xmlData.indexOf("<Weight>")+8), xmlData.indexOf("</Weight>")));
            setTopPunch(xmlData.substring((xmlData.indexOf("<Top Punch>")+11), xmlData.indexOf("</Top Punch>")));
            String tempGS = new String(xmlData.substring((xmlData.indexOf("<Gas Check>")+11), xmlData.indexOf("</Gas Check>")));  
            if(tempGS.trim().equalsIgnoreCase("1") || tempGS.trim().equalsIgnoreCase("true"))
                setGasCheck(true);
            else
                setGasCheck(false);
        } else {
            System.out.println("An error occured in xmlCheck()");
        }
    }
	
    public int getID() {
	return ID;
    }

    public void setID(int i0d) {
	ID = i0d;
    }

    public void setID(String i0d) {
	ID = Integer.parseInt(i0d);
    }

    public String getMaker() {
	return maker;
    }

    public void setMaker(String maker) {
	this.maker = maker;
    }

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public float getDiameter() {
	return diameter;
    }

    public void setDiameter(float diameter) {
	this.diameter = diameter;
    }
	
    public void setDiameter(String bc) {
	Float fbc = Float.parseFloat(bc);
	try{
	    this.diameter = fbc.floatValue();
	}catch(NumberFormatException nfe){
	    this.diameter = 0;
	}
    }

    public float getWeight() {
	return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setWeight(String bc) {
        Float fbc = Float.parseFloat(bc);
        try{
            this.weight = fbc.floatValue();
        }catch(NumberFormatException nfe){
            this.weight = 0;
        }

    }
	
    public String getTopPunch() {
	return topPunch;
    }

    public void setTopPunch(String topPunch) {
	this.topPunch = topPunch;
    }

    public boolean isGasCheck() {
	return gasCheck;
    }

    public void setGasCheck(Boolean gasCheck) {
	this.gasCheck = gasCheck;
    }

    public void setGasCheck(int gasCheck) {
        if(gasCheck < 1)
            this.gasCheck = false;
	else
            this.gasCheck = true;
    }

    public boolean isValid()
    {
        if (maker.trim().equals("")){
           return false;}
        else if (number.trim().equals("")){
           return false;}
        else if (description.trim().equals("")){
           return false;}
        else if (diameter < (float)0.0){
           return false;}
        else if (topPunch.trim().equals("")) {
            return false;}
        else if (weight < (float)0.0){
            return false;}
        else if (topPunch.trim().equals("")){
            return false;}
        else
            return true;
    }

    @Override
    public String toString() {
	String output = new String("");
	output = 		 "ID = " + ID +"\n";
	output = output +"Maker = " + maker +"\n";
	output = output +"Number = " + number +"\n"; 
	output = output +"Description = " + description +"\n";
	output = output +"Diameter = " + ff.floatConvert(diameter, 0, 3) +"\n";
	output = output +"Weight = " + ff.floatConvert(weight, 0, 1) +"\n";
	output = output +"Top Punch = " + topPunch +"\n";
	output = output +"Gas Check = " + gasCheck +"\n";
	return output;
    }

    public String toXML() {
        String output = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<BulletMolds>\n");
	output = output +"\t<ID>" + ID +"</ID>\n";
	output = output +"\t<Maker>" + maker +"</Maker>\n";
	output = output +"\t<Number>" + number +"</Number>\n"; 
	output = output +"\t<Description>" + description +"</Description>\n";
	output = output +"\t<Diameter>" + ff.floatConvert(diameter, 0, 3) +"</Diameter>\n";
	output = output +"\t<Weight>" + ff.floatConvert(weight, 0, 1) +"</Weight>\n";
	output = output +"\t<Top Punch>" + topPunch +"</Top Punch>\n";
	output = output +"\t<Gas Check>" + gasCheck +"</Gas Check>\n";
	output = output +"</BulletMolds>\n";
	return output;
    }

    public boolean xmlCheck(String xml) {
        if(xml.indexOf("<BulletMolds>") == -1)
            return false;
        if(xml.indexOf("<ID>") == -1)
            return false;
        if(xml.indexOf("</ID>") == -1)
            return false;
        if(xml.indexOf("<Maker>") == -1)
            return false;
        if(xml.indexOf("</Maker>") == -1)
            return false;
        if(xml.indexOf("<Number>") == -1)
            return false;
        if(xml.indexOf("</Number>") == -1)
            return false;
        if(xml.indexOf("<Diameter>") == -1)
            return false;
        if(xml.indexOf("</Diameter>") == -1)
            return false;
        if(xml.indexOf("<Weight>") == -1)
            return false;
        if(xml.indexOf("</Weight>") == -1)
            return false;
        if(xml.indexOf("<Top Punch>") == -1)
            return false;
        if(xml.indexOf("</Top Punch>") == -1)
            return false;
        if(xml.indexOf("<Gas Check>") == -1)
            return false;
        if(xml.indexOf("</Gas Check>") == -1)
            return false;
        if(xml.indexOf("</BulletMolds>") == -1)
            return false;                
        return true;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
	BulletMold bm = new BulletMold(1, "Greese", "12-12-12", "Flat point","5", (float)0.232, (float)55.0);
	System.out.println(bm);
	String bmXML = new String(bm.toXML());
	System.out.println(bmXML);
	bm = new BulletMold(bmXML);
	System.out.println(bm);
    }
}

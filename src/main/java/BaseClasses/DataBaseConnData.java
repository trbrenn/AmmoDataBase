/*
 * DataBaseConnData.java
 *
 * Created on July 1, 2007, 11:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseClasses;

import BaseClasses.SettingsXML;

/**
 *
 * @author Todd Brenneman
 */
public class DataBaseConnData {
    
    private String URL;
    private String UserName;
    private String PassWord;
    private String CL;
    private SettingsXML sxml;
	
    public DataBaseConnData() 
    {
	sxml = new SettingsXML();
	try {
            sxml.readData();
	}
	catch(Exception e){
            System.err.println("Error in DataBaseConnData: "+e);
	}
	URL = sxml.getDBurl();
	UserName = sxml.getUserName();
	PassWord = sxml.getPassWord();
	CL = sxml.getCL();
    }

   public DataBaseConnData(SettingsXML sxml) 
    {
	this.sxml = sxml;
	URL = sxml.getDBurl();
	UserName = sxml.getUserName();
	PassWord = sxml.getPassWord();
	CL = sxml.getCL();
    }

    public DataBaseConnData(String url, String cl)
    {
	sxml = new SettingsXML();
	try {
            sxml.readData();
	}
	catch(Exception e){
            System.err.println("Error in DataBaseConnData: "+e);
	}
	URL = url;
	UserName = sxml.getUserName();
	PassWord = sxml.getPassWord();
	CL = cl;
    }

    public DataBaseConnData(String url, String user, String passwd, String cl) throws Exception
    {
	sxml = new SettingsXML();
        sxml.readData();
	URL = url;
	UserName = user;
        PassWord = passwd;
        CL = cl;
    }
        
    public String getCL() 
    {
	return CL;
    }

    public void setCL(String cl) 
    {
	CL = cl;
    }

    public String getPassWord() 
    {
        return PassWord;
    }

    public void setPassWord(String passWord) 
    {
	PassWord = passWord;
    }

    public String getURL() 
    {
	return URL;
    }

    public void setURL(String url) 
    {
	URL = url;
    }

    public String getUserName() 
    {
	return UserName;
    }

    public void setUserName(String userName) 
    {
	UserName = userName;
    }    
	
    public SettingsXML getSxml() {
	return sxml;
    }

}

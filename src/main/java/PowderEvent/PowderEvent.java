/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PowderEvent;

import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;
/**
 *
 * @author trbrenn
 * @date 12-24-2024
 */
public class PowderEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private Powder          powder;
    private boolean         IgnoreEmpty;
    private int             eventType;
    private Hashtable<String, Powder> powderHash;
    private List<String> powderList;
    
    public PowderEvent(Object source) {
        super(source);       
    }

    public PowderEvent(Object source, int Type) {
        super(source);       
    
        this.powder = null;
        this.eventType = Type;
        this.IgnoreEmpty = true;  
    }

    public PowderEvent(Object source, Powder powder) {
        super(source);
        
        this.powder = powder;
        this.eventType = NOT_SET;
        this.IgnoreEmpty = true;
    }
    
    public PowderEvent(Object source, Powder powder, int type) {
        super(source);
        
        this.powder = powder;
        this.eventType = type;
        this.IgnoreEmpty = true;
    }
    
    public PowderEvent(Object source, Powder powder, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.powder = powder;
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public PowderEvent(Object source, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public Powder getPowder() {
        return powder;
    }

    public void setPowder(Powder occupation) {
        this.powder = occupation;
    }

    public void setIgnoreEmpty(boolean IgnoreEmpty) {
        this.IgnoreEmpty = IgnoreEmpty;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public boolean isIgnoreEmpty() {
        return IgnoreEmpty;
    }

    public Hashtable<String, Powder> getPowderHash() {
        return powderHash;
    }

    public void setPowderHash(Hashtable<String, Powder> powderHash) {
        this.powderHash = powderHash;
    }

    public List<String> getPowderList() {
        return powderList;
    }

    public void setPowderList(List<String> powderList) {
        this.powderList = powderList;
    }
}

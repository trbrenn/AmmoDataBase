/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirearmsEvent;

import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author trbrenn
 * @date 1-2-2025
 */
public class FirearmEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private Firearm         firearm;
    private int             eventType;
    private Hashtable<String, Firearm> firearmHash;
    private List<String> firearmList;
    
    public FirearmEvent(Object source) {
        super(source);       
    }

    public FirearmEvent(Object source, int Type) {
        super(source);       
    
        this.firearm = null;
        this.eventType = Type; 
    }

    public FirearmEvent(Object source, Firearm firearm) {
        super(source);
        
        this.firearm = firearm;
        this.eventType = NOT_SET;
    }
    
    public FirearmEvent(Object source, Firearm firearm, int type) {
        super(source);
        
        this.firearm = firearm;
        this.eventType = type;
    }
    
    public Firearm getFirearm() {
        return firearm;
    }

    public void setFirearm(Firearm occupation) {
        this.firearm = occupation;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Hashtable<String, Firearm> getFirearmHash() {
        return firearmHash;
    }

    public void setFirearmHash(Hashtable<String, Firearm> firearmHash) {
        this.firearmHash = firearmHash;
    }

    public List<String> getFirearmList() {
        return firearmList;
    }

    public void setFirearmList(List<String> firearmList) {
        this.firearmList = firearmList;
    }

}

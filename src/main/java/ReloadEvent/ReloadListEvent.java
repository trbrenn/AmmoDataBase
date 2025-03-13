/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReloadEvent;

import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author trbrenn
 */
public class ReloadListEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private List<String>    allReloadsList;
    private ReloadList      reloadList;
    private boolean         IgnoreEmpty;
    private int             eventType;
    private Hashtable<String, ReloadList> allReloadsHash;
            
    public ReloadListEvent(Object source) {
        super(source);       
    }

    public ReloadListEvent(Object source, int Type) {
        super(source);       
    
        this.reloadList = null;
        this.eventType = Type;
        this.IgnoreEmpty = true;  
    }

    public ReloadListEvent(Object source, ReloadList reloadList) {
        super(source);
        
        this.reloadList = reloadList;
        this.eventType = NOT_SET;
        this.IgnoreEmpty = true;
    }
    
    public ReloadListEvent(Object source, ReloadList reloadList, int type) {
        super(source);
        
        this.reloadList = reloadList;
        this.eventType = type;
        this.IgnoreEmpty = true;
    }
    
    public ReloadListEvent(Object source, ReloadList reloadList, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.reloadList = reloadList;
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
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

    public List<String> getAllReloadsList() {
        return allReloadsList;
    }

    public void setAllReloadsList(List<String> allReloadsList) {
        this.allReloadsList = allReloadsList;
    }

    public ReloadList getReloadList() {
        return reloadList;
    }

    public void setReloadList(ReloadList reloadList) {
        this.reloadList = reloadList;
    }

    public Hashtable<String, ReloadList> getAllReloadsHash() {
        return allReloadsHash;
    }

    public void setAllReloadsHash(Hashtable<String, ReloadList> allReloadsHash) {
        this.allReloadsHash = allReloadsHash;
    }
}
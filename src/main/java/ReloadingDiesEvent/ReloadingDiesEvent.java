/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReloadingDiesEvent;

import java.util.EventObject;
import java.util.Hashtable;
/**
 *
 * @author trbrenn
 * @date 1-2-2025
 */
public class ReloadingDiesEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private ReloadingDies   reloadingDie;
    private int             eventType;
    private Hashtable<Integer, ReloadingDies> reloadingDiesHash;
    
    public ReloadingDiesEvent(Object source) {
        super(source);       
    }

    public ReloadingDiesEvent(Object source, int Type) {
        super(source);       
    
        this.reloadingDie = null;
        this.eventType = Type; 
    }

    public ReloadingDiesEvent(Object source, ReloadingDies reloadingDie) {
        super(source);
        
        this.reloadingDie = reloadingDie;
        this.eventType = NOT_SET;
    }
    
    public ReloadingDiesEvent(Object source, ReloadingDies reloadingDie, int type) {
        super(source);
        
        this.reloadingDie = reloadingDie;
        this.eventType = type;
    }
    
    public ReloadingDiesEvent(Object source, ReloadingDies reloadingDie, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.reloadingDie = reloadingDie;
        this.eventType = type;
    }
    
    public ReloadingDies getReloadingDie() {
        return reloadingDie;
    }

    public void setReloadingDie(ReloadingDies occupation) {
        this.reloadingDie = occupation;
    }

   public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Hashtable<Integer, ReloadingDies> getReloadingDiesHash() {
        return reloadingDiesHash;
    }

    public void setReloadingDiesHash(Hashtable<Integer, ReloadingDies> reloadingDiesHash) {
        this.reloadingDiesHash = reloadingDiesHash;
    }
}

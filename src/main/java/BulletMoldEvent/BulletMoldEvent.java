/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BulletMoldEvent;

import java.util.EventObject;
import java.util.Hashtable;

/**
 *
 * @author trbrenn
 * @date 1-2-2025
 */
public class BulletMoldEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private BulletMold      bulletMold;
    private boolean         IgnoreEmpty;
    private int             eventType;
    private Hashtable<String, BulletMold> moldHash;
    
    public BulletMoldEvent(Object source) {
        super(source);       
    }

    public BulletMoldEvent(Object source, int Type) {
        super(source);       
    
        this.bulletMold = null;
        this.eventType = Type;
        this.IgnoreEmpty = true;  
    }

    public BulletMoldEvent(Object source, BulletMold bulletMold) {
        super(source);
        
        this.bulletMold = bulletMold;
        this.eventType = NOT_SET;
        this.IgnoreEmpty = true;
    }
    
    public BulletMoldEvent(Object source, BulletMold bulletMold, int type) {
        super(source);
        
        this.bulletMold = bulletMold;
        this.eventType = type;
        this.IgnoreEmpty = true;
    }
    
    public BulletMoldEvent(Object source, BulletMold bulletMold, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.bulletMold = bulletMold;
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public BulletMold getBulletMold() {
        return bulletMold;
    }

    public void setBulletMold(BulletMold occupation) {
        this.bulletMold = occupation;
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

    public Hashtable<String, BulletMold> getMoldHash() {
        return moldHash;
    }

    public void setMoldHash(Hashtable<String, BulletMold> moldHash) {
        this.moldHash = moldHash;
    }
}

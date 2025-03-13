/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManufacturedAmmoEvent;

import java.util.EventObject;

/**
 *
 * @author trbrenn
 */
public class ManufacturedAmmoEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private ManufacturedAmmo    manufacturedAmmo;
    private boolean             IgnoreEmpty;
    private int                 eventType;
    
    public ManufacturedAmmoEvent(Object source) {
        super(source);       
    }

    public ManufacturedAmmoEvent(Object source, int Type) {
        super(source);       
    
        this.manufacturedAmmo = null;
        this.eventType = Type;
        this.IgnoreEmpty = true;  
    }

    public ManufacturedAmmoEvent(Object source, ManufacturedAmmo bullet) {
        super(source);
        
        this.manufacturedAmmo = bullet;
        this.eventType = NOT_SET;
        this.IgnoreEmpty = true;
    }
    
    public ManufacturedAmmoEvent(Object source, ManufacturedAmmo bullet, int type) {
        super(source);
        
        this.manufacturedAmmo = bullet;
        this.eventType = type;
        this.IgnoreEmpty = true;
    }
    
    public ManufacturedAmmoEvent(Object source, ManufacturedAmmo bullet, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.manufacturedAmmo = bullet;
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public ManufacturedAmmo getManufacturedAmmo() {
        return manufacturedAmmo;
    }

    public void setManufacturedAmmo(ManufacturedAmmo occupation) {
        this.manufacturedAmmo = occupation;
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
}

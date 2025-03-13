/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BulletsEvent;

import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;
/**
 *
 * @author trbrenn
 * @date 12-24-2024
 */
public class BulletsEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private Bullets         bullet;
    private boolean         IgnoreEmpty;
    private int             eventType;
    private List<String>    allBulletsList;
    private Hashtable<String, Bullets> allBulletsHash;
    
    public BulletsEvent(Object source) {
        super(source);       
    }

    public BulletsEvent(Object source, int Type) {
        super(source);       
    
        this.bullet = null;
        this.eventType = Type;
        this.IgnoreEmpty = true;  
    }

    public BulletsEvent(Object source, Bullets bullet) {
        super(source);
        
        this.bullet = bullet;
        this.eventType = NOT_SET;
        this.IgnoreEmpty = true;
    }
    
    public BulletsEvent(Object source, Bullets bullet, int type) {
        super(source);
        
        this.bullet = bullet;
        this.eventType = type;
        this.IgnoreEmpty = true;
    }
    
    public BulletsEvent(Object source, Bullets bullet, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.bullet = bullet;
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public BulletsEvent(Object source, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public Bullets getBullet() {
        return bullet;
    }

    public void setBullet(Bullets occupation) {
        this.bullet = occupation;
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

    public List<String> getAllBulletsList() {
        return allBulletsList;
    }

    public void setAllBulletsList(List<String> allBulletsList) {
        this.allBulletsList = allBulletsList;
    }

    public Hashtable<String, Bullets> getAllBulletsHash() {
        return allBulletsHash;
    }

    public void setAllBulletsHash(Hashtable<String, Bullets> allBulletsHash) {
        this.allBulletsHash = allBulletsHash;
    }
}

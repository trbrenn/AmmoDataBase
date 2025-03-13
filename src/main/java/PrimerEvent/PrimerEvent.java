/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrimerEvent;

import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;
/**
 *
 * @author trbrenn
 * @date 12-24-2024
 */
public class PrimerEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_LOTNUMBERS = 6;
    
    private Primer          primer;
    private boolean         IgnoreEmpty;
    private int             eventType;
    private Hashtable<String, Primer> primerHash;
    private List<String> primerList;

    public PrimerEvent(Object source) {
        super(source);       
    }

    public PrimerEvent(Object source, int Type) {
        super(source);       
    
        this.primer = null;
        this.eventType = Type;
        this.IgnoreEmpty = true;  
    }

    public PrimerEvent(Object source, Primer primer) {
        super(source);
        
        this.primer = primer;
        this.eventType = NOT_SET;
        this.IgnoreEmpty = true;
    }
    
    public PrimerEvent(Object source, Primer primer, int type) {
        super(source);
        
        this.primer = primer;
        this.eventType = type;
        this.IgnoreEmpty = true;
    }
    
    public PrimerEvent(Object source, Primer primer, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.primer = primer;
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
   public PrimerEvent(Object source, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }
    
    public Primer getPrimer() {
        return primer;
    }

    public void setPrimer(Primer occupation) {
        this.primer = occupation;
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

    public Hashtable<String, Primer> getPrimerHash() {
        return primerHash;
    }

    public void setPrimerHash(Hashtable<String, Primer> primerHash) {
        this.primerHash = primerHash;
    }

    public List<String> getPrimerList() {
        return primerList;
    }

    public void setPrimerList(List<String> primerList) {
        this.primerList = primerList;
    }
}

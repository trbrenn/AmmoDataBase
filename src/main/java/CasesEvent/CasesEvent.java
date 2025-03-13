/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CasesEvent;

import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author trbrenn
 */
public class CasesEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int UPDATE_LOTNUMBER = 0;
    public static final int INSERT_LOTNUMBER = 1;
    public static final int GET_ALL_CALIBERS = 2;
    public static final int GET_ALL = 3;
    public static final int GET_LOTNUMBER = 4;
    public static final int DELETE_LOTNUMBER = 5;
    public static final int GET_ALL_CASE_LOTNUMBERS = 6;
    
    private Cases           cases;
    private boolean         IgnoreEmpty;
    private int             eventType;
    private Hashtable<String, Cases> casesHash;
    private List<String>    casesList;
    
    public CasesEvent(Object source) {
        super(source);       
    }
    
    public CasesEvent(Object source, int Type) {
        super(source);       
    
        this.cases = null;
        this.eventType = Type;
        this.IgnoreEmpty = true;  
    }

    public CasesEvent(Object source, Cases cases) {
        super(source);
        
        this.cases = cases;
        this.eventType = NOT_SET;
        this.IgnoreEmpty = true;
    }
    
    public CasesEvent(Object source, Cases cases, int type) {
        super(source);
        
        this.cases = cases;
        this.eventType = type;
        this.IgnoreEmpty = true;
    }
    
    public CasesEvent(Object source, Cases cases, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.cases = cases;
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }    
    
    public CasesEvent(Object source, int type, boolean IgnoreEmpty) {
        super(source);
        
        this.eventType = type;
        this.IgnoreEmpty = IgnoreEmpty;
    }    
    
    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public boolean isIgnoreEmpty() {
        return IgnoreEmpty;
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

    public Hashtable<String, Cases> getCasesHash() {
        return casesHash;
    }

    public void setCasesHash(Hashtable<String, Cases> casesHash) {
        this.casesHash = casesHash;
    }

    public List<String> getCasesList() {
        return casesList;
    }

    public void setCasesList(List<String> casesList) {
        this.casesList = casesList;
    }
}

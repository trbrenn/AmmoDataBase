/****************************************************************************************************************
 * ChronoDataEvent.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 1-4-25																								*
 *  																											*
 * This is the ChronoDataEvent class. It is used to define the ChronoDataEvent class.
 *																								*
 * 																												*
 ***************************************************************************************************************/

package ChronoDataEvent;

import BulletsEvent.Bullets;
import FirearmsEvent.Firearm;
import PowderEvent.Powder;
import ReloadEvent.ReloadList;
import java.util.EventObject;

public class ChronoDataEvent extends EventObject {
    public static final int NOT_SET = -1;
    public static final int GET_ALL = 0;
    public static final int GET_TEST_NUMBER = 1;
    public static final int INSERT_TEST_NUMBER = 2;
    public static final int UPDATE_TEST_NUMBER = 3;
    public static final int DELETE_TEST_NUMBER = 4;
    public static final int GET_RELOADLIST = 5;
    public static final int GET_BULLET = 6;
    public static final int GET_POWDER = 7;
    public static final int GENERATE_TEST_NUMBER = 8;
    public static final int GET_FIREARM = 9;
   
    private ChronographData chronoData;
    private ReloadList      reloadList;
    private Bullets         bullets;
    private Powder          powder;
    private Firearm         firearm;
    private int             eventType;
    
    public ChronoDataEvent(Object source){
        super(source);
    }
    
    public ChronoDataEvent(Object source, ChronographData chronoData){
        super(source);
        this.chronoData = chronoData;
    }
    
    public ChronoDataEvent(Object source, ReloadList reloadList){
        super(source);
        this.reloadList = reloadList;
    }
    
    public ChronoDataEvent(Object source, Bullets bullets){
        super(source);
        this.bullets = bullets;
    }
    
    public ChronoDataEvent(Object source, Powder powder){
        super(source);
        this.powder = powder;
    }
    
    public ChronoDataEvent(Object source, Firearm firearm){
        super(source);
        this.firearm = firearm;
    }
    
    public ChronoDataEvent(Object source, int eventType){
        super(source);
    }
    
    public ChronoDataEvent(Object source, ChronographData chronoData, int eventType){
        super(source);
        this.chronoData = chronoData;
        this.eventType = eventType;
    }
    
    public ChronoDataEvent(Object source, ReloadList reloadList, int eventType){
        super(source);
        this.reloadList = reloadList;
        this.eventType = eventType;
    }
    
    public ChronoDataEvent(Object source, Bullets bullets, int eventType){
        super(source);
        this.bullets = bullets;
        this.eventType = eventType;
    }
    
    public ChronoDataEvent(Object source, Powder powder, int eventType){
        super(source);
        this.powder = powder;
        this.eventType = eventType;
    }
    
    public ChronoDataEvent(Object source, Firearm firearm, int eventType){
        super(source);
        this.firearm = firearm;
        this.eventType = eventType;
    }

    public ChronographData getChronoData() {
        return chronoData;
    }

    public void setChronoData(ChronographData chronoData) {
        this.chronoData = chronoData;
    }

    public ReloadList getReloadList() {
        return reloadList;
    }

    public void setReloadList(ReloadList reloadList) {
        this.reloadList = reloadList;
    }

    public Bullets getBullets() {
        return bullets;
    }

    public void setBullets(Bullets bullets) {
        this.bullets = bullets;
    }

    public Powder getPowder() {
        return powder;
    }

    public void setPowder(Powder powder) {
        this.powder = powder;
    }

    public Firearm getFirearm() {
        return firearm;
    }

    public void setFirearm(Firearm firearm) {
        this.firearm = firearm;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }   
}

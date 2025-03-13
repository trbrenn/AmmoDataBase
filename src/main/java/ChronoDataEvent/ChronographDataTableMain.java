/****************************************************************************************************************
 * ChronographDataTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 1-4-25																								*
 *  																											*
 * This is the ChronographDataTableMain program. It builds the Chronograph table and sets it in a JPanel for the main window. 																								*
 * 																												*
 ***************************************************************************************************************/

package ChronoDataEvent;
import BaseClasses.DataBaseConnData;
import BaseClasses.DataBaseConn;
import BaseClasses.CalendarTest;
import BulletsEvent.Bullets;
import BulletsEvent.BulletsEvent;
import BulletsEvent.BulletsListener;
import BulletsEvent.BulletsQuery;
import FirearmsEvent.Firearm;
import FirearmsEvent.FirearmEvent;
import FirearmsEvent.FirearmListener;
import FirearmsEvent.FirearmQuery;
import PowderEvent.Powder;
import PowderEvent.PowderEvent;
import PowderEvent.PowderListener;
import PowderEvent.PowderQuery;
import ReloadEvent.ReloadList;
import ReloadEvent.ReloadListEvent;
import ReloadEvent.ReloadListListener;
import ReloadEvent.ReloadListQuery;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ChronographDataTableMain {
    private List<ChronographData> cdl;
    private Object[][] data = {};
    private ReloadList rl;
    private Firearm fr;
    private ChronographData cd;
    private JFrame frame;
    private ChronographGUI cgui;
    private ChronographTable newContentPane;
    private int winType = ChronographGUI.NOT_SET;
    private ChronographDataQuery cdq;
    private ReloadListQuery rlq;
    private BulletsQuery bsq;
    private PowderQuery pwq;  
    private FirearmQuery frq;
    private CalendarTest cal = new CalendarTest();
    

    //only used if called for standalone operation.
    public ChronographDataTableMain(){
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        this.cdq = new ChronographDataQuery(dbc);
        DataBaseConn dbc1 = new DataBaseConn(dbcd);
        this.rlq = new ReloadListQuery(dbc1);
        DataBaseConn dbc2 = new DataBaseConn(dbcd);
        this.bsq = new BulletsQuery(dbc2);
        DataBaseConn dbc3 = new DataBaseConn(dbcd);
        this.pwq = new PowderQuery(dbc3);
        DataBaseConn dbc4 = new DataBaseConn(dbcd);
        this.frq = new FirearmQuery(dbc4);
    }
    
    //only used if called for standalone operation.
    public ChronographDataTableMain(ChronographDataQuery cdq, ReloadListQuery rlq, BulletsQuery bsq, PowderQuery pwq, FirearmQuery frq){
        this.cdq = cdq;
        this.rlq = rlq;
        this.bsq = bsq;
        this.pwq = pwq;
        this.frq = frq;
    }
    
    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("ReloadListEvent");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1500, 500));
        this.createPanel();
        frame.add(newContentPane);
        frame.pack();
    }
     
    private void createPanel() {
        //Get all the cases and store them in the array        
        try{
            int index1 = 0;
            cdq.connect();
            cdl = cdq.getAll();
            data = new Object[cdl.size()][6];
            //System.out.println(hsl.size()+" \n"+hsl);
            while(index1 < cdl.size()) {
                //System.err.println(index1);
                cd = cdl.get(index1);
                data[index1][0] = cd.getTestNumber();
                Calendar c = cd.getShotDate();
                data[index1][1] = cal.convertDate(c);
                if(cd.getReloadList() != null) {
                    data[index1][2] = cd.getReloadList().getCaliber();
                } else {
                    data[index1][2] = cd.getFirearm().getCaliber();
                }
                data[index1][3] = cd.getFirearm().getManufacturer() +" "+ cd.getFirearm().getModelName();				
                data[index1][4] = cd.getStdDev()+" ft/s";
                data[index1++][5] = cd.getEngery()+" ft/lbs";	
            }
            cdq.close();
        }catch(Exception e) {
            System.err.println("Database error in getAll(): "+e);
        }
         
        //Create and set up the content pane.
        newContentPane = new ChronographTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                int selectedTestNumber = (int)table.getValueAt(selectedTableIndex, 0);
                cd = new ChronographData();
                try{
                    cdq.connect();
                    cd = cdq.getTestNumber(selectedTestNumber);
                    cdq.close();
                } catch(Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                    return;
                }
                
                cgui = new ChronographGUI();
                cgui.setCd(cd);
                cgui.setWinType(winType);

                cgui.setChronoDataListener(new ChronoDataListener(){
                    @Override
                    public void ChronoDataEventOccurred(ChronoDataEvent cde) {
                        switch (cde.getEventType()){
                            case ChronoDataEvent.INSERT_TEST_NUMBER:
                                try{
                                    cdq.connect();
                                    cdq.insertTestNumber(cde.getChronoData());
                                    cdq.close();
                                }catch(Exception q) {
                                    AlertDialog("ChronographDataTableMain: Database error in ChronographDataTableMain:insertLotNumber()): "+q);
                                }
                                newContentPane.addChrono(cde.getChronoData());
                                newContentPane.updateTable();
                                break;
                            case ChronoDataEvent.UPDATE_TEST_NUMBER:
                                try{
                                    cdq.connect();
                                    cdq.updateTestNumber(cde.getChronoData());
                                    cdq.close();
                                }catch(Exception q) {
                                    AlertDialog("ChronographDataTableMain: Database error in ChronographDataTableMain:updateTestNumber()): "+q);
                                }
                                newContentPane.updateChrono(cde.getChronoData());
                                newContentPane.updateTable();
                                break;
                            case ChronoDataEvent.DELETE_TEST_NUMBER:
                                try{
                                    cdq.connect();
                                    cdq.deleteTestNumber(cde.getChronoData());
                                    cdq.close();
                                }catch(Exception q) {
                                    AlertDialog("ChronographDataTableMain: Database error in ChronographDataTableMain:deleteTestNumber()): "+q);
                                }
                                newContentPane.removeChrono();
                                newContentPane.updateTable();
                                break;
                        }
                    }                    
                });
                cgui.setBulletsListener(new BulletsListener(){
                    @Override
                    public void BulletsEventOccurred(BulletsEvent be){
                        List<String> bsl = new ArrayList<String>();
                        Bullets bs = new Bullets();
                        switch(be.getEventType()){
                            case BulletsEvent.GET_ALL_LOTNUMBERS:
                                try{
                                    bsq.connect();
                                    bsl = bsq.getAllBulletLotNums();
                                    bsq.close();
                                } catch (Exception bq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllBulletLotNums()): "+bq);
                                }
                                be.setAllBulletsList(bsl);
                                break;
                            case BulletsEvent.GET_LOTNUMBER:
                                try{
                                    bsq.connect();
                                    bs = bsq.getLotNumber(be.getBullet().getLotNumber());
                                    bsq.close();
                                } catch (Exception bq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+bq);
                                }
                                be.setBullet(bs);
                                break;
                        }             
                        
                    }                   
                });
                cgui.setPowderListener(new PowderListener(){
                    @Override
                    public void PowderEventOccurred(PowderEvent pwe){
                        Powder pw = new Powder();
                        List<String> lpw = new ArrayList<String>();
                        switch(pwe.getEventType()){
                            case PowderEvent.GET_ALL_LOTNUMBERS:
                                try{
                                    pwq.connect();
                                    lpw = pwq.getAllPowderLotNums();
                                    pwq.close();
                                }catch (Exception pq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllPowderLotNums()): "+pq);
                                }
                                pwe.setPowderList(lpw);
                                break;
                            case PowderEvent.GET_LOTNUMBER:
                                try{
                                    pwq.connect();
                                    pw = pwq.getLotNumber(pwe.getPowder().getPowderLotNumber());
                                    pwq.close();  
                                }catch (Exception pq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+pq);
                                }
                                pwe.setPowder(pw);
                                break;
                        }             
                    }
                });
                cgui.setFirearmListener(new FirearmListener(){
                    @Override
                    public void FirearmEventOccurred(FirearmEvent fde) {
                        Firearm fd = new Firearm();
                        List<String> lfd = new ArrayList<String>();
                        switch(fde.getEventType()){
                            case FirearmEvent.GET_ALL_LOTNUMBERS:
                                try{
                                    frq.connect();
                                    lfd = frq.getAllSerialNumbers();
                                    frq.close();
                                } catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                fde.setFirearmList(lfd);
                                break;
                            case FirearmEvent.GET_LOTNUMBER:
                                try{
                                    frq.connect();
                                    fd = frq.getFirearmData(fde.getFirearm().getSerialNumber());
                                    frq.close();
                                } catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                fde.setFirearm(fd);
                                break;
                        }
                    }
                });
                cgui.setReloadListListener(new ReloadListListener() {
                    @Override
                    public void ReloadListEventOccurred(ReloadListEvent rle) {
                        ReloadList rl = new ReloadList();
                        List<String> lrl = new ArrayList<String>();
                        switch(rle.getEventType()){
                            case ReloadListEvent.GET_ALL_LOTNUMBERS:
                                try{
                                    rlq.connect();
                                    lrl = rlq.getAllLotNumbers();
                                    rlq.close();
                                } catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
System.out.println(lrl);
                                rle.setAllReloadsList(lrl);
                                break;
                            case ReloadListEvent.GET_LOTNUMBER:
                                try{
                                    rlq.connect();
                                    rl = rlq.getLotNumber(rle.getReloadList().getLotNumber());
                                    rlq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                rle.setReloadList(rl);
                                break;
                        }
                    }
                });
                cgui.buildWindow();
                cgui.setVisible(true);
            }
        });
    }
            
    private void displayFrame(){
        //Display the window.
        frame.setVisible(true);   
    }
    
    private void AlertDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(frame, t, "ALERT!", JOptionPane.ERROR_MESSAGE);
    }
    
    public ChronographTable getPanel(){
        if(newContentPane == null) {
            createPanel();
        }
        return newContentPane;
    }

    public int getWinType() {
        return winType;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }
    
    public static void main(String[] args) {
        ChronographDataTableMain ct = new ChronographDataTableMain();
        ct.setWinType(ChronographGUI.VIEW);
        ct.createFrame();
        ct.createPanel();
        ct.displayFrame();    
    }
}

/****************************************************************************************************************
 * ReloadListTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 1-4-25																								*
 *  																											*
 * This is the ReloadListTableMain program. It builds the bullets table and sets in a JPanel for the main window. 																								*
 * 																												*
 ***************************************************************************************************************/

package ReloadEvent;

import BaseClasses.DataBaseConnData;
import BaseClasses.DataBaseConn;
import BulletsEvent.Bullets;
import BulletsEvent.BulletsEvent;
import BulletsEvent.BulletsListener;
import BulletsEvent.BulletsQuery;
import CasesEvent.Cases;
import CasesEvent.CasesEvent;
import CasesEvent.CasesListener;
import CasesEvent.CasesQuery;
import PowderEvent.Powder;
import PowderEvent.PowderEvent;
import PowderEvent.PowderListener;
import PowderEvent.PowderQuery;
import PrimerEvent.Primer;
import PrimerEvent.PrimerEvent;
import PrimerEvent.PrimerListener;
import PrimerEvent.PrimerQuery;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ReloadListTableMain {
    private ReloadListQuery rlq;
    private List<ReloadList> hrl;
    private Object[][] data = {};
    private ReloadList rl;
    private JFrame frame;
    private ReloadListGUI rgui;
    private ReloadListTable newContentPane;
    private int winType = ReloadListGUI.NOT_SET;
    private BulletsQuery bsq;
    private CasesQuery csq;
    private PrimerQuery prq;
    private PowderQuery pwq;    

    //only used if called for standalone operation.
    public ReloadListTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        rlq = new ReloadListQuery(dbc);
    }

    public ReloadListTableMain(ReloadListQuery rlq, BulletsQuery bsq, CasesQuery csq, PrimerQuery prq, PowderQuery pwq) {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        this.rlq = rlq;
        this.bsq = bsq;
        this.csq = csq;
        this.prq = prq;
        this.pwq = pwq;
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
        //Get all the AmmoList and store them in the array
        try{
            int index1 = 0;
            rlq.connect();
            hrl = rlq.getAll();
            data = new Object[hrl.size()][6];
            while(index1 < hrl.size()) {
                rl = hrl.get(index1);
                data[index1][0] = rl.getLotNumber();
                data[index1][1] = rl.getManufacturer();
                data[index1][2] = rl.getLoadDateString();
                data[index1][3] = rl.getCaliber();
                data[index1][4] = rl.getCount();
                data[index1++][5] = rl.getNotes();
            }
            rlq.close();
        }catch(Exception e) {
            System.err.println("Database error in ReloadListTable.getAll(): "+e);
        }   
        
        //Create and set up the content pane.
        newContentPane = new ReloadListTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                String selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);
                rl = new ReloadList();
                try{
                    rlq.connect();
                    rl = rlq.getLotNumber(selectedLotNumber);
                    rlq.close();
                } catch(Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                    return;
                }
    
                rgui = new ReloadListGUI();
                rgui.setAmmoList(rl);
//                rgui.buildWindow(winType);
                rgui.setReloadListListener(new ReloadListListener(){
                    @Override
                    public void ReloadListEventOccurred(ReloadListEvent e) {
                        switch (e.getEventType()) {
                            case ReloadListEvent.INSERT_LOTNUMBER:
                                try{
                                    rlq.connect();
                                    rlq.insertLotNumber(e.getReloadList());
                                    rlq.close();
                                }catch(Exception q) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:insertLotNumber()): "+q);
                                }
                                newContentPane.addReloadList(e.getReloadList());
                                newContentPane.updateTable();
                                break;
                            case ReloadListEvent.UPDATE_LOTNUMBER:
                                try{
                                    rlq.connect();
                                    rlq.updateLotNumber(e.getReloadList());
                                    rlq.close();
                                }catch(Exception q) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:updateLotNumber()): "+q);
                                }
                                newContentPane.updateReloadList(e.getReloadList());
                                newContentPane.updateTable();
                                break;
                            case ReloadListEvent.DELETE_LOTNUMBER:
                                try{
                                    rlq.connect();
                                    rlq.deleteLotNumber(e.getReloadList());
                                    rlq.close();
                                }catch(Exception q) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:deleteLotNumber()): "+q);
                                }
                                newContentPane.removeReloadList();
                                newContentPane.updateTable();
                                break;
                            case ReloadListEvent.GET_ALL_LOTNUMBERS:
                                List<String> lrl = new ArrayList<String>();
                                try{
                                    boolean temp = rlq.isIgnoreEmpty();
                                    rlq.setIgnoreEmpty(e.isIgnoreEmpty());
                                    rlq.connect();
                                    lrl = rlq.getAllLotNumbers();
                                    rlq.close();
                                    rlq.setIgnoreEmpty(temp);
                                }catch(Exception q) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllLotNumbers()): "+q);
                                }
                                e.setAllReloadsList(lrl);
                                break;
                        }
                    }
                });
                rgui.setBulletsListener(new BulletsListener(){
                    @Override
                    public void BulletsEventOccurred(BulletsEvent be){
                        List<String> bsl = new ArrayList<String>();
                        Bullets bs = new Bullets();
                        switch(be.getEventType()){
                            case BulletsEvent.GET_ALL_LOTNUMBERS:
                                try{
                                    boolean temp = bsq.isIgnoreEmpty();
                                    bsq.setIgnoreEmpty(be.isIgnoreEmpty());
                                    bsq.connect();
                                    bsl = bsq.getAllBulletLotNums();
                                    bsq.close();
                                    bsq.setIgnoreEmpty(temp);
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
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getBulletLotNumber()): "+bq);
                                }
                                be.setBullet(bs);
                                break;
                        }             
                        
                    }                   
                });
                rgui.setCasesListener(new CasesListener(){
                    @Override
                    public void CasesEventOccurred(CasesEvent ce){
                        Cases cs = new Cases();
                        List<String> csl = new ArrayList<String>();
                        switch(ce.getEventType()){
                            case CasesEvent.GET_ALL_CASE_LOTNUMBERS:
                                try{
                                    boolean temp = csq.isIgnoreempty1();
                                    bsq.setIgnoreEmpty(ce.isIgnoreEmpty());
                                    csq.connect();
                                    csl = csq.getAllCaseLotNums();
                                    csq.close();
                                    bsq.setIgnoreEmpty(temp);
                                } catch (Exception cq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllCasesLotNums()): "+cq);
                                }
                                ce.setCasesList(csl);
                                break;
                            case CasesEvent.GET_LOTNUMBER:
                                try{
                                    csq.connect();
                                    cs = csq.getLotNumber(ce.getCases().getLotNumber());
                                    csq.close();
                                } catch (Exception cq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getCaseLotNumber()): "+cq);
                                }
                                ce.setCases(cs);
                                break;
                        }             
                    }
                });
                rgui.setPrimerListener(new PrimerListener(){
                    @Override
                    public void PrimerEventOccurred(PrimerEvent pre){
                        Primer pr = new Primer();
                        List<String> lpr = new ArrayList<String>();
                        switch(pre.getEventType()){
                            case PrimerEvent.GET_ALL_LOTNUMBERS:
                                try{
                                    boolean temp = prq.isIgnoreEmpty1();
                                    prq.setIgnoreEmpty1(pre.isIgnoreEmpty());
                                    prq.connect();
                                    lpr = prq.getAllPrimerLotNums();
                                    prq.close();
                                    prq.setIgnoreEmpty1(temp);
                                }catch (Exception pq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllPrimerLotNums()): "+pq);
                                }
                                pre.setPrimerList(lpr);
                                break;
                            case PrimerEvent.GET_LOTNUMBER:
                                try{
                                    prq.connect();
                                    pr = prq.getLotNumber(pre.getPrimer().getLotNumber());
                                    prq.close();
                                }catch (Exception pq) {
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getPrimerLotNumber()): "+pq);
                                }
                                pre.setPrimer(pr);
                                break;
                        }             
                    }
                });
                rgui.setPowderListener(new PowderListener(){
                    @Override
                    public void PowderEventOccurred(PowderEvent pwe){
                        Powder pw = new Powder();
                        List<String> lpw = new ArrayList<String>();
                        switch(pwe.getEventType()){
                            case PowderEvent.GET_ALL_LOTNUMBERS:
                                try{
                                    boolean temp = pwq.isIgnoreempty1();
                                    pwq.setIgnoreempty1(pwe.isIgnoreEmpty());
                                    pwq.connect();
                                    lpw = pwq.getAllPowderLotNums();
                                    pwq.close();
                                    pwq.setIgnoreempty1(temp);
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
                                    AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getPowderLotNumber()): "+pq);
                                }
                                pwe.setPowder(pw);
                                break;
                        }             
                    }
                });
                rgui.buildWindow(winType);
                rgui.setVisible(true);
            };
               
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
    
    public ReloadListTable getPanel(){
        if(newContentPane == null) {
            this.createPanel();
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
        ReloadListTableMain bt = new ReloadListTableMain();
        bt.setWinType(ReloadListGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }
}

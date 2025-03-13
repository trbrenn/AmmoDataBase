/****************************************************************************************************************
 * ReloadingDiesTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the ReloadingDiesTableMain program. It controls the reloading dies table and ReloadingDiesGUI Event. It is the interface to the main program.											*
 * 																												*
 ***************************************************************************************************************/

package ReloadingDiesEvent;

import BaseClasses.DataBaseConnData;
import BaseClasses.DataBaseConn;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ReloadingDiesTableMain {
    private ReloadingDiesQuery rdq;
    private List<ReloadingDies> hsl;
    private Object[][] data = {};
    private ReloadingDies rd = new ReloadingDies();
    private JFrame frame;
    private ReloadingDiesGUI rgui;
    private ReloadingDiesTable newContentPane;
    private int winType = ReloadingDiesGUI.NOT_SET;
    
    //only used if called for standalone operation.
    public ReloadingDiesTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        rdq = new ReloadingDiesQuery(dbc);
   }
    
    public ReloadingDiesTableMain(ReloadingDiesQuery rdq) {
	this.rdq = rdq;	
    }
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("ReloadingDiesEvent");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1500, 500));
        this.createPanel();
        frame.add(newContentPane);
        frame.pack();
    }
    
    private void createPanel() {
        //Get all the bullets and store them in the array        
        try{
            int index1 = 0;
            rdq.connect();
            hsl = rdq.getAll();
            rdq.close();
            data = new Object[hsl.size()][4];
            while(index1 < hsl.size()) {
                rd = hsl.get(index1);
                data[index1][0] = rd.getID();
                data[index1][1] = rd.getMaker();
                data[index1][2] = rd.getCaliber();
                data[index1++][3] = rd.getType();
            }
        }catch(Exception e) {
             System.err.println("Database error in getAll(): "+e);
        }
      	        
        //Create and set up the content pane.
        newContentPane = new ReloadingDiesTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                int selectedLotNumber = (Integer)table.getValueAt(selectedTableIndex, 0);
                rd = new ReloadingDies();
                try{
                    rdq.connect();
                    rd = rdq.getID(selectedLotNumber);
                    rdq.close();
                } catch(Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                }
    
                rgui = new ReloadingDiesGUI();
                rgui.setReloadingDie(rd);
                rgui.buildWindow(winType);
                rgui.setReloadingDiesListener(new ReloadingDiesListener(){ 
                    @Override
                    public void ReloadingDiesEventOccurred(ReloadingDiesEvent e) {
                        switch (e.getEventType()) {
                            case ReloadingDiesEvent.INSERT_LOTNUMBER:
                                try{
                                    rdq.connect();
                                    rdq.insertID(e.getReloadingDie());
                                    rdq.close();
                                }catch(Exception q) {
                                    AlertDialog("ReloadingDiesTableMain: Database error in ReloadingDiesTableMain:insertReloadingDie()): "+q);
                                }
                                //System.out.println("Insert ReloadingDie into database\n"+e.getReloadingDie().toString());
                                newContentPane.addDie(e.getReloadingDie());
                                newContentPane.updateTable();
                                break;
                            case ReloadingDiesEvent.UPDATE_LOTNUMBER:
                                try{
                                    rdq.connect();
                                    rdq.updateID(e.getReloadingDie());
                                    rdq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in ReloadingDiesTableMain:updateReloadingDie()): "+q);
                                }
                                //System.out.println("Update ReloadingDie in database\n"+e.getReloadingDie().toString());
                                newContentPane.updateDie(e.getReloadingDie());
                                newContentPane.updateTable();
                                break;
                            case ReloadingDiesEvent.DELETE_LOTNUMBER:
                                try{
                                    rdq.connect();
                                    rdq.deleteID(e.getReloadingDie());
                                    rdq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in ReloadingDiesTableMain:deleteReloadingDie()): "+q);
                                }
                                //System.out.println("Delete ReloadingDie from database\n"+e.getReloadingDie().toString());
                                newContentPane.removeDie();
                                newContentPane.updateTable();
                                break;                        
                        }

                    }
                });
                rgui.setVisible(true);
           }
        });       
    }
    
    private void displayFrame(){
        //Display the window.
        frame.setVisible(true);   
    }
    
    //Alerts the user if error happen.
    private void AlertDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(frame, t, "ALERT!", JOptionPane.ERROR_MESSAGE);
    }
    
    public ReloadingDiesTable getPanel(){
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
        ReloadingDiesTableMain bt = new ReloadingDiesTableMain();
        bt.setWinType(ReloadingDiesGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }
}

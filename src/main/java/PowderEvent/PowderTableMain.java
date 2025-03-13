/****************************************************************************************************************
 * PowderTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the PowderTableMain program. It controls the powder table and PowderGUI Event. It is the interface to the main program.											*
 * 																												*
 ***************************************************************************************************************/

package PowderEvent;

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

public class PowderTableMain {
    private PowderQuery pwq;
    private List<Powder> hsl;
    private Object[][] data = {};
    private Powder pw = new Powder();
    private JFrame frame;
    private PowderGUI pgui;
    private PowderTable newContentPane;
    private int winType = PowderGUI.NOT_SET;
    
    //only used if called for standalone operation.
    public PowderTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        pwq = new PowderQuery(dbc);
   }
    public PowderTableMain(PowderQuery pwq) {
	this.pwq = pwq;	
    }
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("PowderEvent");
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
            pwq.connect();
            pwq.setIgnoreempty1(true);
            hsl = pwq.getAll();
            data = new Object[hsl.size()][6];
            while(index1 < hsl.size()) {
		pw = hsl.get(index1);
		data[index1][0] = pw.getPowderLotNumber();
		data[index1][1] = pw.getPowderMaker();
		data[index1][2] = pw.getPowderName();
		data[index1][3] = pw.getLotWeight();
		data[index1][4] = pw.getLotCost();
		data[index1++][5] = pw.getDatePurchased();
            }
	}catch(Exception e) {
            AlertDialog("Database error in getAll(): "+e);
	}
       	        
        //Create and set up the content pane.
        newContentPane = new PowderTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                String selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);
                pw = new Powder();
                try{
                    pwq.connect();
                    pw = pwq.getLotNumber(selectedLotNumber);
                    pwq.close();
                } catch(Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                    return;
                }
                
                pgui = new PowderGUI();
                pgui.setPowder(pw);
                pgui.buildWindow(winType);
                pgui.setPowderListener(new PowderListener() {
                    @Override
                    public void PowderEventOccurred(PowderEvent e) {
                        switch (e.getEventType()) {
                            case PowderEvent.INSERT_LOTNUMBER:
                                try{
                                    pwq.connect();
                                    pwq.insertLotNumber(e.getPowder());
                                    pwq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                newContentPane.addPowder(e.getPowder());
                                newContentPane.updateTable();
                                break;
                            case PowderEvent.UPDATE_LOTNUMBER:
                                try{
                                    pwq.connect();
                                    pwq.updateLotNumber(e.getPowder());
                                    pwq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                newContentPane.updatePowder(e.getPowder());
                                newContentPane.updateTable();
                                break;
                            case PowderEvent.DELETE_LOTNUMBER:
                                try{
                                    pwq.connect();
                                    pwq.deleteLotNumber(e.getPowder());
                                    pwq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                newContentPane.removePowder();
                                newContentPane.updateTable();
                                break;
                        }
                    }
                });
                pgui.setVisible(true);
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
    
    public PowderTable getPanel(){
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
        PowderTableMain bt = new PowderTableMain();
        bt.setWinType(PowderGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }    
}
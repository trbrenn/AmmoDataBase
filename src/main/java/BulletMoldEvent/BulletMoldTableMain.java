/****************************************************************************************************************
 * BulletsTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the BulletsTableMain program. It controls the bullet table and BulletGUI Event. It is the interface to the main program.											*
 * 																												*
 ***************************************************************************************************************/

package BulletMoldEvent;

import BaseClasses.DataBaseConn;
import BaseClasses.DataBaseConnData;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class BulletMoldTableMain {
    private BulletMoldQuery bmq;
    private List<BulletMold> hsl;
    private Object[][] data = {};
    private BulletMold bm = new BulletMold();
    private JFrame frame;
    private BulletMoldGUI bmgui;
    private BulletMoldTable newContentPane;
    private int winType = BulletMoldGUI.NOT_SET;
    
    //only used if called for standalone operation.
    public BulletMoldTableMain()  {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        bmq = new BulletMoldQuery(dbc);
   }
    public BulletMoldTableMain(BulletMoldQuery bmq) {
	this.bmq = bmq;	
    }
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("BulletsEvent");
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
            bmq.connect();
            hsl = bmq.getAll();
            data = new Object[hsl.size()][5];
            while(index1 < hsl.size()) {
                bm = hsl.get(index1);
                data[index1][0] = bm.getID();
                data[index1][1] = bm.getMaker();
                data[index1][2] = bm.getNumber();
                data[index1][3] = bm.getDescription();
                data[index1++][4] = bm.getDiameter();
            }
        }catch(Exception e) {
            System.err.println("Database error in getAll(): "+e);
        }
        //Create and set up the content pane.
        newContentPane = new BulletMoldTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {
                bm = new BulletMold();
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                int selectedLotNumber = (Integer)table.getValueAt(selectedTableIndex, 0);
                try{
                    bmq.connect();
                    bm = bmq.getMoldID(selectedLotNumber);
                    bmq.close();
                } catch (Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                    return;
                }
                
                bmgui = new BulletMoldGUI();
                bmgui.setBm(bm);
                bmgui.buildWindow(winType);
                bmgui.setBulletMoldListener(new BulletMoldListener() {
                    @Override
                    public void BulletMoldEventOccurred(BulletMoldEvent e) {
                        switch(e.getEventType()) {
                            case(BulletMoldEvent.INSERT_LOTNUMBER):
                                try{
                                bmq.connect();
                                bmq.insertMoldID(e.getBulletMold());
                                bmq.close();
                                } catch (Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                //System.out.println("Insert Bullet into database\n"+e.getBulletMold().toString());
                                    newContentPane.addBulletMold(e.getBulletMold());
                                    newContentPane.updateTable();
                                    break;
                            case BulletMoldEvent.UPDATE_LOTNUMBER:
                                try{
                                    bmq.connect();
                                    bmq.updateMoldID(e.getBulletMold());
                                    bmq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in BulletsTableMain:updateBullet()): "+q);
                                }
                                //System.out.println("Update Bullet in database\n"+e.getBulletMold().toString());
                                newContentPane.updateBulletMold(e.getBulletMold());
                                newContentPane.updateTable();
                                break;
                            case BulletMoldEvent.DELETE_LOTNUMBER:
                                try{
                                    bmq.connect();
                                    bmq.deleteMoldID(e.getBulletMold().getID());
                                    bmq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in BulletsTableMain:deleteBullet()): "+q);
                                }
                                //System.out.println("Delete Bullet from database\n"+e.getBulletMold().toString());
                                newContentPane.removeBulletMold();
                                newContentPane.updateTable();
                                break;                        
                        }

                    }
                });
                bmgui.setVisible(true);
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
    
    public BulletMoldTable getPanel(){
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
        BulletMoldTableMain bt = new BulletMoldTableMain();
        bt.setWinType(BulletMoldGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }}

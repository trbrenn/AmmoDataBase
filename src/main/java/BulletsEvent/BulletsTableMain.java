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

package BulletsEvent;

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

public class BulletsTableMain {
    private BulletsQuery bsq;
    private List<Bullets> hsl;
    private Object[][] data = {};
    private Bullets bs = new Bullets();
    private JFrame frame;
    private BulletsGUI bgui;
    private BulletsTable newContentPane;
    private int winType = BulletsGUI.NOT_SET;
    
    //only used if called for standalone operation.
    public BulletsTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        bsq = new BulletsQuery(dbc);
   }
    
    public BulletsTableMain(BulletsQuery bsq) {
	this.bsq = bsq;	
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
            bsq.connect();
            bsq.setIgnoreEmpty(true);
            hsl = bsq.getAll();
            bsq.close();
           
            data = new Object[hsl.size()][6];
            while(index1 < hsl.size()) {
                bs = hsl.get(index1);
                data[index1][0] = bs.getLotNumber();
                data[index1][1] = bs.getBulletMaker();
                data[index1][2] = bs.getCaliber();
                data[index1][3] = bs.getWeight();
                data[index1][4] = bs.getDescription();
                if(bs.isEmpty()) {
                    data[index1++][5] = "true";
                } else {
                    data[index1++][5] = "false";					
                }
            }
        }catch(Exception e) {
            AlertDialog("BulletsTableMain: Database error in getAll(): "+e);
        }
   
        //Create and set up the content pane.
        newContentPane = new BulletsTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                String selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);
                bs = new Bullets();
                try{
                    bsq.connect();
                    bs = bsq.getLotNumber(selectedLotNumber);
                    bsq.close();
                } catch(Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                    return;
                }
    
                bgui = new BulletsGUI();
                bgui.setBullets(bs);
                bgui.buildWindow(winType);
                bgui.setBulletsListener(new BulletsListener(){ 
                    @Override
                    public void BulletsEventOccurred(BulletsEvent e) {
                        switch (e.getEventType()) {
                            case BulletsEvent.INSERT_LOTNUMBER:
                                try{
                                    bsq.connect();
                                    bsq.insertLotNumber(e.getBullet());
                                    bsq.close();
                                }catch(Exception q) {
                                    AlertDialog("BulletsTableMain: Database error in BulletsTableMain:insertBullet()): "+q);
                                }
                                //System.out.println("Insert Bullet into database\n"+e.getBullet().toString());
                                newContentPane.addBullet(e.getBullet());
                                newContentPane.updateTable();
                                break;
                            case BulletsEvent.UPDATE_LOTNUMBER:
                                try{
                                    bsq.connect();
                                    bsq.updateLotNumber(e.getBullet());
                                    bsq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in BulletsTableMain:updateBullet()): "+q);
                                }
                                //System.out.println("Update Bullet in database\n"+e.getBullet().toString());
                                newContentPane.updateBullet(e.getBullet());
                                newContentPane.updateTable();
                                break;
                            case BulletsEvent.DELETE_LOTNUMBER:
                                try{
                                    bsq.connect();
                                    bsq.deleteLotNumber(e.getBullet());
                                    bsq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in BulletsTableMain:deleteBullet()): "+q);
                                }
                                //System.out.println("Delete Bullet from database\n"+e.getBullet().toString());
                                newContentPane.removeBullet();
                                newContentPane.updateTable();
                                break;                        
                        }

                    }
                });
                bgui.setVisible(true);
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
    
    public BulletsTable getPanel(){
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
        BulletsTableMain bt = new BulletsTableMain();
        bt.setWinType(BulletsGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }
}

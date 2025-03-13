/****************************************************************************************************************
 * ManufacturedAmmoTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the ManufacturedAmmoTableMain program. It controls the bullet table and BulletGUI Event. It is the interface to the main program.											*
 * 																												*
 ***************************************************************************************************************/
package ManufacturedAmmoEvent;

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

public class ManufacturedAmmoTableMain {
    private ManufacturedAmmoQuery maq;
    private List<ManufacturedAmmo> hsl;
    private Object[][] data = {};
    private ManufacturedAmmo ma = new ManufacturedAmmo();
    private JFrame frame;
    private ManufacturedAmmoGUI bgui;
    private ManufacturedAmmoTable newContentPane;
    private int winType = ManufacturedAmmoGUI.NOT_SET;
    
    //only used if called for standalone operation.
    public ManufacturedAmmoTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        maq = new ManufacturedAmmoQuery(dbc);
   }
    public ManufacturedAmmoTableMain(ManufacturedAmmoQuery maq) {
	this.maq = maq;	
    }
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("ManufacturedAmmoEvent");
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
            maq.connect();
            maq.setNotEmpty(true);
            hsl = maq.getAll();
            maq.close();
            data = new Object[hsl.size()][6];
            while(index1<hsl.size()) {
                ma = hsl.get(index1);
                data[index1][0] = ma.getLotNumber();
                data[index1][1] = ma.getManufacturer();
                data[index1][2] = ma.getCaliber();
                data[index1][3] = ma.getDatePurchased();
                data[index1][4] = ma.getBullet();
                data[index1++][5] = ma.getCount();
            }
        }catch(Exception e) {
            AlertDialog("ManufacturedAmmoTableMain: Database error in getAll(): "+e);
        }
   
        //Create and set up the content pane.
        newContentPane = new ManufacturedAmmoTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                String selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);
                ma = new ManufacturedAmmo();
                try{
                    maq.connect();
                    ma = maq.getLotNumber(selectedLotNumber);
                    maq.close();
                } catch(Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                    return;
                }
    
                bgui = new ManufacturedAmmoGUI();
                bgui.setManufacturedAmmo(ma);
                bgui.buildWindow(winType);
                bgui.setManufacturedAmmoListener(new ManufacturedAmmoListener(){ 
                    @Override
                    public void ManufacturedAmmoEventOccurred(ManufacturedAmmoEvent e) {
                        switch (e.getEventType()) {
                            case ManufacturedAmmoEvent.INSERT_LOTNUMBER:
                                try{
                                    maq.connect();
                                    maq.insertLotNumber(e.getManufacturedAmmo());
                                    maq.close();
                                }catch(Exception q) {
                                    AlertDialog("ManufacturedAmmoTableMain: Database error in ManufacturedAmmoTableMain:insertBullet()): "+q);
                                }
                                //System.out.println("Insert Bullet into database\n"+e.getBullet().toString());
                                newContentPane.addAmmo(e.getManufacturedAmmo());
                                newContentPane.updateTable();
                                break;
                            case ManufacturedAmmoEvent.UPDATE_LOTNUMBER:
                                try{
                                    maq.connect();
                                    maq.updateLotNumber(e.getManufacturedAmmo());
                                    maq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in ManufacturedAmmoTableMain:updateBullet()): "+q);
                                }
                                //System.out.println("Update Bullet in database\n"+e.getBullet().toString());
                                newContentPane.updateAmmo(e.getManufacturedAmmo());
                                newContentPane.updateTable();
                                break;
                            case ManufacturedAmmoEvent.DELETE_LOTNUMBER:
                                try{
                                    maq.connect();
                                    maq.deleteLotNumber(e.getManufacturedAmmo());
                                    maq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in ManufacturedAmmoTableMain:deleteBullet()): "+q);
                                }
                                //System.out.println("Delete Bullet from database\n"+e.getBullet().toString());
                                newContentPane.removeAmmo();
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
    
    public ManufacturedAmmoTable getPanel(){
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
        ManufacturedAmmoTableMain bt = new ManufacturedAmmoTableMain();
        bt.setWinType(ManufacturedAmmoGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }
}

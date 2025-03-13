/****************************************************************************************************************
 * FirearmTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the FirearmTableMain program. It controls the firearm table and FirearmGUI Event. It is the interface to the main program.											*
 * 																												*
 ***************************************************************************************************************/

package FirearmsEvent;

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

public class FirearmsTableMain {
    private FirearmQuery fsq;
    private List<Firearm> hsl;
    private Object[][] data = {};
    private Firearm fs = new Firearm();
    private JFrame frame;
    private FirearmsGUI fgui;
    private FirearmsTable newContentPane;
    private int winType = FirearmsGUI.NOT_SET;
    
    //only used if called for standalone operation.
    public FirearmsTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        fsq = new FirearmQuery(dbc);
   }
    
    public FirearmsTableMain(FirearmQuery fsq) {
	this.fsq = fsq;	
    }
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("FirearmEvent");
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
            fsq.connect();
            hsl = fsq.getAll();
            fsq.close();
            data = new Object[hsl.size()][6];
            while(index1 < hsl.size()) {
                fs = hsl.get(index1);
                data[index1][0] = fs.getManufacturer();
                data[index1][1] = fs.getModelName();
                data[index1][2] = fs.getSerialNumber();
                data[index1][3] = fs.getType();
                data[index1][4] = fs.getCaliber();
                data[index1++][5] = fs.getPicture();
            }
        }catch(Exception e) {
                 System.err.println("Database error in getAll(): "+e);
        }
   
        //Create and set up the content pane.
        newContentPane = new FirearmsTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                String selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 2);
                fs = new Firearm();
                try{
                    fsq.connect();
                    fs = fsq.getFirearmData(selectedLotNumber);
                    fsq.close();
                } catch(Exception q) {
                    AlertDialog("Database error in getAll(): "+q);
                    return;
                }
    
                fgui = new FirearmsGUI();
                fgui.setFirearm(fs);
                fgui.buildWindow(winType);
                fgui.setFirearmListener(new FirearmListener(){ 
                    @Override
                    public void FirearmEventOccurred(FirearmEvent e) {
                        switch (e.getEventType()) {
                            case FirearmEvent.INSERT_LOTNUMBER:
                                try{
                                    fsq.connect();
                                    fsq.insertFirearm(e.getFirearm());
                                    fsq.close();
                                }catch(Exception q) {
                                    AlertDialog("FirearmTableMain: Database error in FirearmTableMain:insertBullet()): "+q);
                                }
                                //System.out.println("Insert Bullet into database\n"+e.getFirearm().toString());
                                newContentPane.addFirearm(e.getFirearm());
                                newContentPane.updateTable();
                                break;
                            case FirearmEvent.UPDATE_LOTNUMBER:
                                try{
                                    fsq.connect();
                                    fsq.updateFirearm(e.getFirearm());
                                    fsq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in FirearmTableMain:updateBullet()): "+q);
                                }
                                //System.out.println("Update Bullet in database\n"+e.getFirearm().toString());
                                newContentPane.updateFirearm(e.getFirearm());
                                newContentPane.updateTable();
                                break;
                            case FirearmEvent.DELETE_LOTNUMBER:
                                try{
                                    fsq.connect();
                                    fsq.deleteFirearm(e.getFirearm());
                                    fsq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in FirearmTableMain:deleteBullet()): "+q);
                                }
                                //System.out.println("Delete Bullet from database\n"+e.getFirearm().toString());
                                newContentPane.removeFirearm();
                                newContentPane.updateTable();
                                break;                        
                        }

                    }
                });
                fgui.setVisible(true);
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
    
    public FirearmsTable getPanel(){
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

    public FirearmQuery getFsq() {
        return fsq;
    }

    public void setFsq(FirearmQuery fsq) {
        this.fsq = fsq;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public Firearm getFs() {
        return fs;
    }

    public void setFs(Firearm fs) {
        this.fs = fs;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public FirearmsTable getNewContentPane() {
        return newContentPane;
    }

    public void setNewContentPane(FirearmsTable newContentPane) {
        this.newContentPane = newContentPane;
    }
    
    public static void main(String[] args) {
        FirearmsTableMain bt = new FirearmsTableMain();
        bt.setWinType(FirearmsGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }
}

/****************************************************************************************************************
 * PrimerTableMain.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the PrimerTableMain program. It controls the primer table and PrimerGUI Event. It is the interface to the main program.											*
 * 																												*
 ***************************************************************************************************************/


package PrimerEvent;

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

/**
 *
 * @author trbrenn
 */
public class PrimerTableMain {
    private PrimerQuery prq;
    private List<Primer> hsl;
    private Object[][] data = {};
    private Primer pr = new Primer();
    private JFrame frame;
    private PrimerGUI pgui;
    private PrimerTable newContentPane;
    private int winType = PrimerGUI.NOT_SET; 
    
    public PrimerTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        prq = new PrimerQuery(dbc);
    }
    
    public PrimerTableMain(PrimerQuery prq){
        this.prq = prq;
    }
    
     /* Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("PrimerEvent");
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
            prq.connect();
            prq.setIgnoreEmpty1(true);
            hsl = prq.getAll();
            data = new Object[hsl.size()][6];
            while(index1 < hsl.size()) {
                pr = hsl.get(index1);
                data[index1][0] = pr.getLotNumber();
                data[index1][1] = pr.getPrimerMaker();
                data[index1][2] = pr.getPrimerSize();
                data[index1][3] = pr.getModelNumber();
                data[index1][4] = pr.getLotCount();
                data[index1++][5] = pr.getLotCost();					
            }
        }catch(Exception e) {
                 System.err.println("Database error in getAll(): "+e);
        }
        
        //Create and set up the content pane.
        newContentPane = new PrimerTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                String selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);
                Primer prs = new Primer();
                try{
                    prq.connect();
                    prs = prq.getLotNumber(selectedLotNumber);
                    prq.close();
                } catch(Exception q) {
                    AlertDialog("PrimeTableMain: Database error in getAll(): "+q);
                    return;
                }
                
                pgui = new PrimerGUI();
                pgui.setPrimer(prs);
                pgui.buildWindow(winType);
                pgui.setPrimerListener(new PrimerListener(){
                    @Override
                    public void PrimerEventOccurred(PrimerEvent e) {
                        switch (e.getEventType()) {
                            case PrimerEvent.INSERT_LOTNUMBER:
                                try{
                                    prq.connect();
                                    prq.insertLotNumber(e.getPrimer());
                                    prq.close();
                                } catch (Exception q) {
                                    AlertDialog("PrimerTableMain: Database error in PrimerTableMain:insertprimer(): "+q);
                                }
                                newContentPane.addPrimer(e.getPrimer());
                                newContentPane.updateTable();
                                break;
                            case PrimerEvent.UPDATE_LOTNUMBER:
                                try{
                                    prq.connect();
                                    prq.updateLotNumber(e.getPrimer());
                                    prq.close();
                                } catch (Exception q) {
                                    AlertDialog("PrimerTableMain: Database error in PrimerTableMain:updateprimer(): "+q);
                                }
                                newContentPane.updatePrimer(e.getPrimer());
                                newContentPane.updateTable();
                                break;
                            case PrimerEvent.DELETE_LOTNUMBER:
                                try{
                                    prq.connect();
                                    prq.deleteLotNumber(e.getPrimer());
                                    prq.close();
                                } catch (Exception q) {
                                    AlertDialog("PrimerTableMain: Database error in PrimerTableMain:deleteprimer(): "+q);
                                }
                                newContentPane.removePrimer();
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
    
    public PrimerTable getPanel(){
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
        PrimerTableMain bt = new PrimerTableMain();
        bt.setWinType(PrimerGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }
}

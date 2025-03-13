/****************************************************************************************************************
 * MainWindow.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the CasesTableMain program. It controls the Cases table and CasesGUI Event. It is the interface to the main program.											*
 * 																												*
 ***************************************************************************************************************/

package CasesEvent;

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

public class CasesTableMain {
    private CasesQuery csq;
    private List<Cases> hsl;
    private Object[][] data = {};
    private Cases cs = new Cases();
    private JFrame frame;
    private CasesGUI cgui;
    private CasesTable newContentPane;
    private int winType = CasesGUI.NOT_SET;
    
    //only used if called for standalone operation.
    public CasesTableMain() {
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        csq = new CasesQuery(dbc);
   }
    public CasesTableMain(CasesQuery bsq) {
	this.csq = bsq;	
    }

    private void createFrame(){
        //Create and set up the window.
        frame = new JFrame("CasesEvent");
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
            csq.setIgnoreempty1(true);
            csq.connect();
            hsl = csq.getAll();
            data = new Object[hsl.size()][6];
            while(index1 < hsl.size()) {
                cs = hsl.get(index1);
                data[index1][0] = cs.getLotNumber();
                data[index1][1] = cs.getCaseMaker();
                data[index1][2] = cs.getCaliber();
                data[index1][3] = cs.getType();
                data[index1][4] = cs.getLotCount();
                data[index1++][5] = cs.getLotCost();					
            }
        }catch(Exception e) {
            AlertDialog("CasesTableMain: Database error in getAll(): "+e);
        }
        
        //Create and set up the content pane.
        newContentPane = new CasesTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        JTable table = newContentPane.getTable();
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {                
                int selectedTableIndex = table.getSelectedRow();
                if(selectedTableIndex < 0)
                    selectedTableIndex = 0;
                String selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);
                Cases cs = new Cases();
                try {
                    csq.connect();
                    cs = csq.getLotNumber(selectedLotNumber);
                    csq.close();
                }catch(Exception q) {
                    AlertDialog("Database error in CasessTableMain:insertCases()): "+q);
                    return;
                }

                cgui = new CasesGUI();
                cgui.setCases(cs);
                cgui.buildWindow(winType);
                cgui.setCasesListener(new CasesListener(){
                    @Override
                    public void CasesEventOccurred(CasesEvent e) {
                        switch(e.getEventType()) {
                            case CasesEvent.INSERT_LOTNUMBER:
                                try{
                                    csq.connect();
                                    csq.insertLotNumber(e.getCases());
                                    csq.close();
                                } catch(Exception q) {
                                    AlertDialog("Database error in CasesTableMain:insertBullet()): "+q);
                                }
                                newContentPane.addCase(e.getCases());
                                newContentPane.updateTable();
                                break;
                            case CasesEvent.UPDATE_LOTNUMBER:
                                try{
                                    csq.connect();
                                    csq.updateLotNumber(e.getCases());
                                    csq.close();
                                } catch(Exception q) {
                                    AlertDialog("Database error in CasesTableMain:updateBullet()): "+q);
                                }
                                newContentPane.updateCase(e.getCases());
                                newContentPane.updateTable();
                                break;
                            case CasesEvent.DELETE_LOTNUMBER:
                                try{
                                    csq.connect();
                                    csq.deleteLotNumber(e.getCases());
                                    csq.close();
                                } catch(Exception q) {
                                    AlertDialog("Database error in CasesTableMain:deleteBullet()): "+q);
                                }
                                newContentPane.updateCase(e.getCases());
                                newContentPane.updateTable();
                                break;      
                        }
                    }
                });
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
    
    public CasesTable getPanel(){
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
        CasesTableMain bt = new CasesTableMain();
        bt.setWinType(CasesGUI.VIEW);
        bt.createFrame();
        bt.createPanel();
        bt.displayFrame();    
    }

}

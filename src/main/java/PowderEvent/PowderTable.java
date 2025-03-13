/****************************************************************************************************************
 * PowderTable.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the Powder Table program. It displays or collects Powder data for the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package PowderEvent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author trbrenn
 *
 */
public class PowderTable  extends JPanel {
    private String selectedLotNumber = new String();
    private int selectedTableIndex;
    private Object[][] data = {};
    private JTable table;
    

    public PowderTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
        
        String[] columnNames = {"LotNumber",
        			"Powder Maker",
        			"Powder Name",
        			"Weight",
        			"Cost",
        			"Purchase Date"};
 
        //Get all the powder and store them in the array
/*	try{
            int index1 = 0;
            pwq.connect();
            pwq.setIgnoreempty1(true);
            hsl = pwq.getAll();
            Enumeration<String> eal = hsl.keys();
            data = new Object[hsl.size()][6];
            while(eal.hasMoreElements()) {
		pw = hsl.get(eal.nextElement());
		data[index1][0] = pw.getPowderLotNumber();
		data[index1][1] = pw.getPowderMaker();
		data[index1][2] = pw.getPowderName();
		data[index1][3] = pw.getLotWeight();
		data[index1][4] = pw.getLotCost();
		data[index1++][5] = pw.getDatePurchased();
            }
	}catch(Exception e) {
            System.err.println("Database error in getAll(): "+e);
	}
*/        	        
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table = new JTable(model) {
            private static final long serialVersionUID = 1L;
            @Override
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    return false; //Disallow the editing of any cell
                }
        };
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
/*        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        	if (DEBUG) {
                    //System.out.println("Row = "+table.getSelectedRow()+", Column = "+table.getSelectedColumn());
                    //System.out.println((String)table.getValueAt(table.getSelectedRow(), 0));
                    //System.out.println(selectedCellValue);
        	}
                selectedTableIndex = table.getSelectedRow();
        	selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);
        		
        	Powder tbs = new Powder();
        	tbs.setPowderLotNumber((String)table.getValueAt(selectedTableIndex, 0));
        	PowderGUI bgui = new PowderGUI(dbc, tbs, winType, self);
        	bgui.setVisible(true);
            }
        });
*/ 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    public String getSelectedLotNumber() {
        return selectedLotNumber;
    }

    public void setSelectedLotNumber(String selectedLotNumber) {
        this.selectedLotNumber = selectedLotNumber;
    }

    public int getSelectedTableIndex() {
        return selectedTableIndex;
    }

    public void setSelectedTableIndex(int selectedTableIndex) {
        this.selectedTableIndex = selectedTableIndex;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public String getselectedLotNumber() {
        return selectedLotNumber;
    }
	
    public void updatePowder(Powder ps){
        data[selectedTableIndex][0] = ps.getPowderLotNumber();
        data[selectedTableIndex][1] = ps.getPowderMaker();
        data[selectedTableIndex][2] = ps.getPowderName();
        data[selectedTableIndex][3] = ps.getLotWeight();
        data[selectedTableIndex][4] = ps.getLotCost();
        data[selectedTableIndex][5] = ps.getDatePurchased();
    }
        
    public void removePowder(){
        int dataSize = data.length - 1;
        Object[][] newdata = new Object[dataSize][6];
        for(int x = 0, k =0; x < dataSize; x++){
            if(x == selectedTableIndex)
                continue;
            newdata[k][0] = data[x][0];
            newdata[k][1] = data[x][1];
            newdata[k][2] = data[x][2];
            newdata[k][3] = data[x][3];
            newdata[k][4] = data[x][4];
            newdata[k++][5] = data[x][5];
        }
            
        data = newdata;
    }
        
    public void addPowder(Powder ps) {
        int x = 0;
        int dataSize = data.length + 1;
        Object[][] newdata = new Object[dataSize][6];
        int w = dataSize - 1;
        for(; x < w; x++){
            newdata[x][0] = data[x][0];
            newdata[x][1] = data[x][1];
            newdata[x][2] = data[x][2];
            newdata[x][3] = data[x][3];
            newdata[x][4] = data[x][4];
            newdata[x][5] = data[x][5];
        }

        newdata[x][0] = ps.getPowderLotNumber();
        newdata[x][1] = ps.getPowderMaker();
        newdata[x][2] = ps.getPowderName();
        newdata[x][3] = ps.getLotWeight();
        newdata[x][4] = ps.getLotCost();
        newdata[x][5] = ps.getDatePurchased();
                
        data = newdata;
    }
       
    public void filterTable(List<Powder> pList){
        int x = 0;
        int dataSize = pList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            Powder ps = pList.get(x);
            newData[x][0] = ps.getPowderLotNumber();
            newData[x][1] = ps.getPowderMaker();
            newData[x][2] = ps.getPowderName();
            newData[x][3] = ps.getLotWeight();
            newData[x][4] = ps.getLotCost();
            newData[x++][5] = ps.getDatePurchased();  
        }
        data = newData;
    }
    
    public void filterTable(Hashtable<String,Powder> pList){
        int x = 0;
        Enumeration<String> eal = pList.keys();
        Object[][] newData = new Object[pList.size()][6];
        
        while(eal.hasMoreElements()) {
            Powder ps = pList.get(eal.nextElement());
            newData[x][0] = ps.getPowderLotNumber();
            newData[x][1] = ps.getPowderMaker();
            newData[x][2] = ps.getPowderName();
            newData[x][3] = ps.getLotWeight();
            newData[x][4] = ps.getLotCost();
            newData[x++][5] = ps.getDatePurchased();  
        }
        data = newData;
    }
        
    public void updateTable() {
        String[] columnNames = {"LotNumber",
                                "Powder Maker",
                                "Powder Name",
                                "Weight",
                                "Cost",
                                "Date Purchased"};
 
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }
    
}

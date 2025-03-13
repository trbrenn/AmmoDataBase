/****************************************************************************************************************
 * ReloadingDiesTable.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the ReloadingDiesTable program. It builds the reloading dies table and sets in a JPanel for the main window. 																								*
 * 																												*
 ***************************************************************************************************************/

package ReloadingDiesEvent;

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
 * @author Todd Brenneman
 * @date	1/2/2025 
 */
public class ReloadingDiesTable extends JPanel {
    private String selectedLotNumber = new String();
    private Object[][] data = {};
    private JTable table;
    private int selectedTableIndex;
	
    public ReloadingDiesTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
        
        String[] columnNames = {"ID Number",
                                "Maker",
                                "Caliber",
                                "Type"};
 
        //Get all the bullets and store them in the array
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

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    public String getselectedLotNumber() {
            return selectedLotNumber;
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

    public int getSelectedTableIndex() {
        return selectedTableIndex;
    }

    public void setSelectedTableIndex(int selectedTableIndex) {
        this.selectedTableIndex = selectedTableIndex;
    }

    public void updateDie(ReloadingDies rd) {
        data[selectedTableIndex][0] = rd.getID();
        data[selectedTableIndex][1] = rd.getMaker();
        data[selectedTableIndex][2] = rd.getCaliber();
        data[selectedTableIndex][3] = rd.getType();      
    }
    
    public void removeDie() {
        int dataSize = data.length - 1;
        Object[][] newdata = new Object[dataSize][6];
        for(int x = 0, k =0; x < dataSize; x++){
            if(x == selectedTableIndex)
                continue;
            newdata[k][0] = data[x][0];
            newdata[k][1] = data[x][1];
            newdata[k][2] = data[x][2];
            newdata[k][3] = data[x][3];
        }
            
        data = newdata;
    }
    
    public void addDie(ReloadingDies rd)  {
        int x = 0;
        int dataSize = data.length + 1;
        Object[][] newdata = new Object[dataSize][6];
        int w = dataSize - 1;
        for(; x < w; x++){
            newdata[x][0] = data[x][0];
            newdata[x][1] = data[x][1];
            newdata[x][2] = data[x][2];
            newdata[x][3] = data[x][3];
            
        }

        newdata[x][0] = rd.getID();
        newdata[x][1] = rd.getMaker();
        newdata[x][2] = rd.getCaliber();
        newdata[x][3] = rd.getType();
                
        data = newdata;
    }
    
    public void filterTable(List<ReloadingDies> cList){
        int x = 0;
        int dataSize = cList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            ReloadingDies rd = cList.get(x);
            newData[x][0] = rd.getID();
            newData[x][1] = rd.getMaker();
            newData[x][2] = rd.getCaliber();
            newData[x++][3] = rd.getType();
        }
        data = newData;
    }
    
    public void filterTable(Hashtable<Integer, ReloadingDies> hcList){
        int x = 0;
        Enumeration<Integer> eal = hcList.keys();
        Object[][] newData = new Object[hcList.size()][6];
        
        while(eal.hasMoreElements()) {
            ReloadingDies rd = hcList.get(eal.nextElement());
            newData[x][0] = rd.getID();
            newData[x][1] = rd.getMaker();
            newData[x][2] = rd.getCaliber();
            newData[x++][3] = rd.getType();
        }
        data = newData;
    }
    
    public void updateTable() {
        String[] columnNames = {"ID Number",
                                "Maker",
                                "Caliber",
                                "Type"};
 
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }	
 
}

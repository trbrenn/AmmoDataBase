/****************************************************************************************************************
 * BulletMoldTable.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 1-2-25																								*
 *  																											*
 * This is the BulletMoldTable program. It builds the Bullet Mold table and sets in a JPanel for the main window. 																								*
 * 																												*
 ***************************************************************************************************************/

package BulletMoldEvent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Todd Brenneman
 * @date	1-2-25 
 */

public class BulletMoldTable extends JPanel{
    private String selectedLotNumber = new String();
    private Object[][] data = {};
    private JTable table;
    private int selectedTableIndex;

    public BulletMoldTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
        
        String[] columnNames = {"ID Number",
                                "Maker",
                                "Mold Number",
                                "Description",
                                "Diameter"};
 
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

    public void removeBulletMold(){
        int dataSize = data.length - 1;
        Object[][] newdata = new Object[dataSize][6];
        for(int x = 0, k =0; x < dataSize; x++){
            if(x == selectedTableIndex)
                continue;
            newdata[k][0] = data[x][0];
            newdata[k][1] = data[x][1];
            newdata[k][2] = data[x][2];
            newdata[k][3] = data[x][3];
            newdata[k++][4] = data[x][4];

        }

        data = newdata;
    }
    
    public void updateBulletMold(BulletMold bms){
        data[selectedTableIndex][0] = bms.getID();
        data[selectedTableIndex][1] = bms.getMaker();
        data[selectedTableIndex][2] = bms.getNumber();
        data[selectedTableIndex][3] = bms.getWeight();
        data[selectedTableIndex][4] = bms.getDescription();
    }
        
    public void addBulletMold(BulletMold bms) {
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
        }

        newdata[x][0] = bms.getID();
        newdata[x][1] = bms.getMaker();
        newdata[x][2] = bms.getNumber();
        newdata[x][3] = bms.getDescription();
        newdata[x][4] = bms.getDiameter();

        data = newdata;
    }

    public void filterTable(List<BulletMold> bmList){
        int x = 0;
        int dataSize = bmList.size();
        Object[][] newdata = new Object[dataSize][5];
        
        while(x < dataSize) {
            BulletMold bms = bmList.get(x);
            newdata[x][0] = bms.getID();
            newdata[x][1] = bms.getMaker();
            newdata[x][2] = bms.getNumber();
            newdata[x][3] = bms.getDescription();
            newdata[x++][4] = bms.getDiameter();
        }
        data = newdata;                       
    }
    
    public void filterTable(java.util.Hashtable<String, BulletMold> bmList){
        int x = 0;
        Enumeration<String> eal = bmList.keys();
        Object[][] newdata = new Object[bmList.size()][6];
        
        while(eal.hasMoreElements()) {
            BulletMold bms = bmList.get(eal.nextElement());
            newdata[x][0] = bms.getID();
            newdata[x][1] = bms.getMaker();
            newdata[x][2] = bms.getNumber();
            newdata[x][3] = bms.getDescription();
            newdata[x++][4] = bms.getDiameter();
        }
        data = newdata;
    }

    public void updateTable() {
        String[] columnNames = {"ID Number",
                                "Maker",
                                "Mold Number",
                                "Description",
                                "Diameter"};

        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }
}

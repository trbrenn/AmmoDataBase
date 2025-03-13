/****************************************************************************************************************
 * BulletsTable.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the Bullets Table program. It builds the bullets table and sets in a JPanel for the main window. 																								*
 * 																												*
 ***************************************************************************************************************/

package BulletsEvent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BulletsTable extends JPanel {
    private String selectedLotNumber = new String();
    private Object[][] data = {};
    private JTable table;
    private int selectedTableIndex;

    public BulletsTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;

        String[] columnNames = {"LotNumber",
                                "Bullet Maker",
                                "Caliber",
                                "Weight",
                                "Description",
                                "Empty"};


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

   public void updateBullet(Bullets bs){
        for(int j = 0; j < data.length; j++){
            if(data[j][0].toString().matches(bs.getLotNumber())){
                selectedTableIndex = j;
            }
        }
        data[selectedTableIndex][0] = bs.getLotNumber();
        data[selectedTableIndex][1] = bs.getBulletMaker();
        data[selectedTableIndex][2] = bs.getCaliber();
        data[selectedTableIndex][3] = bs.getWeight();
        data[selectedTableIndex][4] = bs.getDescription();
        if(bs.isEmpty()) {
            data[selectedTableIndex][5] = "true";
        } else {
            data[selectedTableIndex][5] = "false";					
        }
    }
        
    public void removeBullet(){
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
        
    public void addBullet(Bullets bs) {
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

        newdata[x][0] = bs.getLotNumber();
        newdata[x][1] = bs.getBulletMaker();
        newdata[x][2] = bs.getCaliber();
        newdata[x][3] = bs.getWeight();
        newdata[x][4] = bs.getDescription();
        if(bs.isEmpty()) {
            newdata[x][5] = "true";
        } else {
            newdata[x][5] = "false";					
        }

        data = newdata;
    }
    
    public void filterTable(List<Bullets> bList){
        int x = 0;
        int dataSize = bList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            Bullets bs = bList.get(x);
            newData[x][0] = bs.getLotNumber();
            newData[x][1] = bs.getBulletMaker();
            newData[x][2] = bs.getCaliber();
            newData[x][3] = bs.getWeight();
            newData[x][4] = bs.getDescription();
            if(bs.isEmpty()) {
                newData[x][5] = "true";
            } else {
                newData[x][5] = "false";					
            }
            x++;
        }
        data = newData;   
    }

    public void updateTable() {
        String[] columnNames = {"LotNumber",
                                "Bullet Maker",
                                "Caliber",
                                "Weight",
                                "Description",
                                "Empty"};
        
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }
}


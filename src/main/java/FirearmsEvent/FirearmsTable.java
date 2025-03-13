/****************************************************************************************************************
 * FirearmTable.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 1-2-25																								*
 *  																											*
 * This is the FireArm Table program. It builds the Firearm table and sets in a JPanel for the main window. 																								*
 * 																												*
 ***************************************************************************************************************/


package FirearmsEvent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FirearmsTable extends JPanel {
	
    private static final long serialVersionUID = -3397011676735052259L;
    private boolean DEBUG = false;
    private Firearm al = new Firearm();
    private String selectedSerialNumber = new String();
    private int winType = -1;
    private int selectedTableIndex;
    private FirearmsTable self;
    private Object[][] data;
    private JTable table;
    
    public FirearmsTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
        
        String[] columnNames = {"Manufacturer",
                                "ModelName",
                                "Serial Number",
                                "Type",
                                "Caliber",
                                "Picture"};

        //Get all the Firearms and store them in the array
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

    public String getselectedSerialNumber() {
            return selectedSerialNumber;
    }

    public void setWindowType(int wt) {
            winType = wt;
    }

    public String getSelectedSerialNumber() {
        return selectedSerialNumber;
    }

    public void setSelectedSerialNumber(String selectedSerialNumber) {
        this.selectedSerialNumber = selectedSerialNumber;
    }

    public int getWinType() {
        return winType;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }

    public int getSelectedTableIndex() {
        return selectedTableIndex;
    }

    public void setSelectedTableIndex(int selectedTableIndex) {
        this.selectedTableIndex = selectedTableIndex;
    }

    public FirearmsTable getSelf() {
        return self;
    }

    public void setSelf(FirearmsTable self) {
        this.self = self;
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
    
    public void updateFirearm(Firearm cs){
        data[selectedTableIndex][0] = cs.getManufacturer();
        data[selectedTableIndex][1] = cs.getModelName();
        data[selectedTableIndex][2] = cs.getSerialNumber();
        data[selectedTableIndex][3] = cs.getType();
        data[selectedTableIndex][4] = cs.getCaliber();
        data[selectedTableIndex][5] = cs.getPicture();
    }
        
    public void removeFirearm(){
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

    public void addFirearm(Firearm cs) {
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

        newdata[x][0] = cs.getManufacturer();
        newdata[x][1] = cs.getModelName();
        newdata[x][2] = cs.getSerialNumber();
        newdata[x][3] = cs.getType();
        newdata[x][4] = cs.getCaliber();
        newdata[x][5] = cs.getPicture();
                
        data = newdata;
    }
        
    public void filterTable(List<Firearm> fList){
        int x = 0;
        int dataSize = fList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            Firearm cs = fList.get(x);
            newData[x][0] = cs.getManufacturer();
            newData[x][1] = cs.getModelName();
            newData[x][2] = cs.getSerialNumber();
            newData[x][3] = cs.getType();
            newData[x][4] = cs.getCaliber();
            newData[x++][5] = cs.getPicture(); 
        }
        data = newData;
    }
    
    public void updateTable() {
        String[] columnNames = {"Manufacturer",
                                "ModelName",
                                "Serial Number",
                                "Type",
                                "Caliber",
                                "Picture"};
 
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }	
}

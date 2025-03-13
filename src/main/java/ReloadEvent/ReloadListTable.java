package ReloadEvent;

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
 * @date	3/25/2023 
 */
public class ReloadListTable extends JPanel {
    private String selectedLotNumber = new String();
    private int selectedTableIndex;
    private Object[][] data = {};
    private final JTable table;

    public ReloadListTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
        
        String[] columnNames = {"LotNumber",
                                "Manafucture",
                                "Load Date",
                                "Caliber",
                                "Count",
                                "Notes"};
        
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

    public void setWindowType(int wt) {
    }
    
    public void setIgnoreEmpty(boolean ie){
    }
    
    public JTable getTable(){
        return table;
    }
    public void updateReloadList(ReloadList rl){
        data[selectedTableIndex][0] = rl.getLotNumber();
        data[selectedTableIndex][1] = rl.getManufacturer();
        data[selectedTableIndex][2] = rl.getLoadDateString();
        data[selectedTableIndex][3] = rl.getCaliber();
        data[selectedTableIndex][4] = rl.getCount();
        data[selectedTableIndex][5] = rl.getNotes();					        
    }

    public void removeReloadList(){
        int dataSize = data.length - 1;
        Object[][] newdata = new Object[dataSize-1][6];
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

    public void addReloadList(ReloadList rl) {
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

        newdata[x][0] = rl.getLotNumber();
        newdata[x][1] = rl.getManufacturer();
        newdata[x][2] = rl.getLoadDateString();
        newdata[x][3] = rl.getCaliber();
        newdata[x][4] = rl.getCount();
        newdata[x][5] = rl.getNotes();					        

        data = newdata;
    }

    public void filterTable(List<ReloadList> cList){
        int x = 0;
        int dataSize = cList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            ReloadList rl = cList.get(x);
            newData[x][0] = rl.getLotNumber();
            newData[x][1] = rl.getManufacturer();
            newData[x][2] = rl.getLoadDateString();
            newData[x][3] = rl.getCaliber();
            newData[x][4] = rl.getCount();
            newData[x++][5] = rl.getNotes();	             
        }
        data = newData;
    }
    
    public void filterTable(Hashtable<String,ReloadList> hcList){
        int x = 0;
        Enumeration<String> eal = hcList.keys();
        Object[][] newData = new Object[hcList.size()][6];
        
        while(eal.hasMoreElements()) {
            ReloadList rl = hcList.get(eal.nextElement());
            newData[x][0] = rl.getLotNumber();
            newData[x][1] = rl.getManufacturer();
            newData[x][2] = rl.getLoadDateString();
            newData[x][3] = rl.getCaliber();
            newData[x][4] = rl.getCount();
            newData[x++][5] = rl.getNotes();	             
        }
        data = newData;
    }
    
    public void updateTable() {
        String[] columnNames = {"LotNumber",
                                "Manafucture",
                                "Load Date",
                                "Caliber",
                                "Count",
                                "Notes"};

        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }
}


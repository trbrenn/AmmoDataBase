/**
 * 
 */
package CasesEvent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class CasesTable  extends JPanel {
    private String selectedLotNumber = new String();
    private int selectedTableIndex;
    private Object[][] data = {};
    private JTable table;
   
    public CasesTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;

        String[] columnNames = {"LotNumber",
                                "Case Maker",
                                "Caliber",
                                "Type",
                                "Lot Count",
                                "Lot Cost"};

        //Get all the cases and store them in the array

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
/*      table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedTableIndex = table.getSelectedRow();
                selectedLotNumber = (String)table.getValueAt(selectedTableIndex, 0);

            }
        });
*/
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
   
    public String getselectedLotNumber() {
        return selectedLotNumber;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void updateCase(Cases cs){
        data[selectedTableIndex][0] = cs.getLotNumber();
        data[selectedTableIndex][1] = cs.getCaseMaker();
        data[selectedTableIndex][2] = cs.getCaliber();
        data[selectedTableIndex][3] = cs.getType();
        data[selectedTableIndex][4] = cs.getLotCount();
        data[selectedTableIndex][5] = cs.getLotCost();
    }
        
    public void removeCase(){
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

    public void addCase(Cases cs) {
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

        newdata[x][0] = cs.getLotNumber();
        newdata[x][1] = cs.getCaseMaker();
        newdata[x][2] = cs.getCaliber();
        newdata[x][3] = cs.getType();
        newdata[x][4] = cs.getLotCount();
        newdata[x][5] = cs.getLotCost();
                
        data = newdata;
    }
        
    public void filterTable(List<Cases> cList){
        int x = 0;
        int dataSize = cList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            Cases cs = cList.get(x);
            newData[x][0] = cs.getLotNumber();
            newData[x][1] = cs.getCaseMaker();
            newData[x][2] = cs.getCaliber();
            newData[x][3] = cs.getType();
            newData[x][4] = cs.getLotCount();
            newData[x++][5] = cs.getLotCost();             
        }
        data = newData;
    }
    
    public void filterTable(Hashtable<String,Cases> hcList){
        int x = 0;
        Enumeration<String> eal = hcList.keys();
        Object[][] newData = new Object[hcList.size()][6];
        
        while(eal.hasMoreElements()) {
            Cases cs = hcList.get(eal.nextElement());
            newData[x][0] = cs.getLotNumber();
            newData[x][1] = cs.getCaseMaker();
            newData[x][2] = cs.getCaliber();
            newData[x][3] = cs.getType();
            newData[x][4] = cs.getLotCount();
            newData[x++][5] = cs.getLotCost();             
        }
        data = newData;
    }
    
    public void updateTable() {
        String[] columnNames = {"LotNumber",
                                "Case Maker",
                                "Caliber",
                                "Type",
                                "Lot Count",
                                "Lot Cost"};
 
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }
}

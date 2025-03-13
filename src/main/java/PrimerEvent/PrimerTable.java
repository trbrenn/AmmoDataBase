/****************************************************************************************************************
 * PowderTable.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the Primer Table program. It displays or collects Primer data for the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package PrimerEvent;

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
public class PrimerTable  extends JPanel {
    private String selectedLotNumber = new String();
    private int selectedTableIndex;
    private Object[][] data = {};
    private JTable table;
    
    public PrimerTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
  
        String[] columnNames = {"LotNumber",
        			"Primer Maker",
        			"Primer Size",
        			"Model Number",
        			"Lot Count",
        			"Lot Cost"};
 
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
                selectedLotNumber = (String)table.getValueAt(table.getSelectedRow(), 0);

                Primer tbs = new Primer();
                selectedTableIndex = table.getSelectedRow();
                tbs.setLotNumber((String)table.getValueAt(selectedTableIndex, 0));
                PrimerGUI bgui = new PrimerGUI(dbc, tbs, winType, self);
                bgui.setVisible(true);
            }
        });
*/ 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
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
	
    public void updatePrimer(Primer ps){
        data[selectedTableIndex][0] = ps.getLotNumber();
        data[selectedTableIndex][1] = ps.getPrimerMaker();
        data[selectedTableIndex][2] = ps.getPrimerSize();
        data[selectedTableIndex][3] = ps.getModelNumber();
        data[selectedTableIndex][4] = ps.getLotCount();
        data[selectedTableIndex][5] = ps.getLotCost();					        
    }

    public void removePrimer(){
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

    public void addPrimer(Primer ps) {
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

        newdata[x][0] = ps.getLotNumber();
        newdata[x][1] = ps.getPrimerMaker();
        newdata[x][2] = ps.getPrimerSize();
        newdata[x][3] = ps.getModelNumber();
        newdata[x][4] = ps.getLotCount();
        newdata[x][5] = ps.getLotCost();					        

        data = newdata;
    }

    public void filterTable(List<Primer> cList){
        int x = 0;
        int dataSize = cList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            Primer ps = cList.get(x);
            newData[x][0] = ps.getLotNumber();
            newData[x][1] = ps.getPrimerMaker();
            newData[x][2] = ps.getPrimerSize();
            newData[x][3] = ps.getModelNumber();
            newData[x][4] = ps.getLotCount();
            newData[x++][5] = ps.getLotCost();            
        }
        data = newData;
    }
    
    public void filterTable(Hashtable<String,Primer> cList){
        int x = 0;
        Enumeration<String> eal = cList.keys();
        Object[][] newData = new Object[cList.size()][6];
        
        while(eal.hasMoreElements()) {
            Primer ps = cList.get(eal.nextElement());
            newData[x][0] = ps.getLotNumber();
            newData[x][1] = ps.getPrimerMaker();
            newData[x][2] = ps.getPrimerSize();
            newData[x][3] = ps.getModelNumber();
            newData[x][4] = ps.getLotCount();
            newData[x++][5] = ps.getLotCost();            
        }
        data = newData;
    }
    
    public void updateTable() {
        String[] columnNames = {"LotNumber",
                                "Primer Maker",
                                "Primer Size",
                                "Model Number",
                                "Lot Count",
                                "Lot Cost"};

        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }
    
}

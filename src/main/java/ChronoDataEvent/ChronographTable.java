/**
 * 
 */
package ChronoDataEvent;

import BaseClasses.CalendarTest;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * @author trbrenn
 *
 */
public class ChronographTable extends JPanel {
    private int selectedTestNumber = 0;
    private ChronographData cd = new ChronographData();
    private Object[][] data;
    private JTable table;
    private CalendarTest cal = new CalendarTest();
    
    public ChronographTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
        
        String[] columnNames = {"Test Nummber",
        			"Shot Date",
        			"Caliber",
        			"Firearm",
        			"Standard Devivation",
        			"Energy"};
        	        
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
	
    public int getselectedLotNumber() {
            return selectedTestNumber;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
    
    public void updateChrono(ChronographData cd){
        data[selectedTestNumber][0] = cd.getTestNumber();
        data[selectedTestNumber][1] = cal.convertDate(cd.getShotDate());
        data[selectedTestNumber][2] = cd.getFirearm().getCaliber();
        data[selectedTestNumber][3] = cd.getFirearm().getManufacturer()+" "+cd.getFirearm().getModelName();
        data[selectedTestNumber][4] = cd.getStdDev();
        data[selectedTestNumber][5] = cd.getEngery();
    }
        
    public void removeChrono(){
        int dataSize = data.length - 1;
        Object[][] newdata = new Object[dataSize][6];
        for(int x = 0, k =0; x < dataSize; x++){
            if(x == selectedTestNumber)
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

    public void addChrono(ChronographData cs) {
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

        newdata[x][0] = cd.getTestNumber();
        newdata[x][1] = cal.convertDate(cd.getShotDate());
        newdata[x][2] = cd.getFirearm().getCaliber();
        newdata[x][3] = cd.getFirearm().getManufacturer()+" "+cd.getFirearm().getModelName();
        newdata[x][4] = cd.getStdDev();
        newdata[x][5] = cd.getEngery();
                
        data = newdata;
    }
    
     public void filterTable(List<ChronographData> bList){
        int x = 0;
        int dataSize = bList.size();
        Object[][] newdata = new Object[dataSize][6];
         
        while(x < dataSize){
            ChronographData cd = bList.get(x);
            newdata[x][0] = cd.getTestNumber();
            newdata[x][1] = cal.convertDate(cd.getShotDate());
            newdata[x][2] = cd.getFirearm().getCaliber();
            newdata[x][3] = cd.getFirearm().getManufacturer()+" "+cd.getFirearm().getModelName();
            newdata[x][4] = cd.getStdDev();
            newdata[x][5] = cd.getEngery();
            x++;
        }
        data = newdata;   
    }     
        
    public void updateTable() {
        String[] columnNames = {"Test Nummber",
        			"Shot Date",
        			"Caliber",
        			"Firearm",
        			"Standard Devivation",
        			"Energy"};
 
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }	 
}

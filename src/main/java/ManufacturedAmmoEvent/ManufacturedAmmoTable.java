package ManufacturedAmmoEvent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ManufacturedAmmoTable  extends JPanel {
    private String selectedLotNumber = new String();
    private ManufacturedAmmo ma = new ManufacturedAmmo();
    private int winType = -1;
    private int selectedTableIndex;
    private Object[][] data = {};
    private JTable table;
    private boolean noEmpty;

    public ManufacturedAmmoTable(Object[][] data) {
        super(new GridLayout(1,0));
        this.data = data;
        
        String[] columnNames = {"LotNumber",
                                "Manufacturer",
                                "Caliber",
                                "Date Purchased",
                                "Bullet",
                                "Count"};

        //Get all the cases and store them in the array
/*        try{
            int index1 = 0;
            maq.setNotEmpty(noEmpty);
            maq.connect();
            hsl = maq.getAll();
            Enumeration<String> eal = hsl.keys();
            data = new Object[hsl.size()][6];
            while(eal.hasMoreElements()) {
                ma = hsl.get(eal.nextElement());
                data[index1][0] = ma.getLotNumber();
                data[index1][1] = ma.getManufacturer();
                data[index1][2] = ma.getCaliber();
                data[index1][3] = ma.getDatePurchased();
                data[index1][4] = ma.getBullet();
                data[index1++][5] = ma.getCount();
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
                
                ManufacturedAmmo tbs = new ManufacturedAmmo();
                tbs.setLotNumber((String)table.getValueAt(selectedTableIndex, 0));
                ManufacturedAmmoGUI bgui = new ManufacturedAmmoGUI(dbc, tbs, winType, self);
                bgui.setVisible(true);
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

    public void setWindowType(int wt) {
        winType = wt;
    }

    public int getWinType() {
        return winType;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
    
    public void updateAmmo(ManufacturedAmmo ma){
        data[selectedTableIndex][0] = ma.getLotNumber();
        data[selectedTableIndex][1] = ma.getManufacturer();
        data[selectedTableIndex][2] = ma.getCaliber();
        data[selectedTableIndex][3] = ma.getDatePurchased();
        data[selectedTableIndex][4] = ma.getBullet();
        data[selectedTableIndex][5] = ma.getCount();
    }
        
    public void removeAmmo(){
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
        
    public void addAmmo(ManufacturedAmmo ma) {
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

        newdata[x][0] = ma.getLotNumber();
        newdata[x][1] = ma.getManufacturer();
        newdata[x][2] = ma.getCaliber();
        newdata[x][3] = ma.getDatePurchased();
        newdata[x][4] = ma.getBullet();
        newdata[x][5] = ma.getCount();
                
        data = newdata;
    }
        
    public void filterTable(List<ManufacturedAmmo> maList){
        int x = 0;
        int dataSize = maList.size();
        Object[][] newData = new Object[dataSize][6];
        
        while(x < dataSize){
            ManufacturedAmmo ma = maList.get(x);
            newData[x][0] = ma.getLotNumber();
            newData[x][1] = ma.getManufacturer();
            newData[x][2] = ma.getCaliber();
            newData[x][3] = ma.getDatePurchased();
            newData[x][4] = ma.getBullet();
            newData[x++][5] = ma.getCount();        
        }
        data = newData;
    }
    
    public void filterTable(Hashtable<String,ManufacturedAmmo> maList){
        int x = 0;
        Enumeration<String> eal = maList.keys();
        Object[][] newData = new Object[maList.size()][6];
        
        while(eal.hasMoreElements()) {
            ManufacturedAmmo ma = maList.get(eal.nextElement());
            newData[x][0] = ma.getLotNumber();
            newData[x][1] = ma.getManufacturer();
            newData[x][2] = ma.getCaliber();
            newData[x][3] = ma.getDatePurchased();
            newData[x][4] = ma.getBullet();
            newData[x++][5] = ma.getCount();        
        }
        data = newData;
    }
    
    public void updateTable() {
        String[] columnNames = {"LotNumber",
                                "Manufacturer",
                                "Caliber",
                                "Date Purchased",
                                "Bullet",
                                "Count"};

        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        table.setModel(model);
        model.fireTableDataChanged();
        //table.repaint();
    }
}

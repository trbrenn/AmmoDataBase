/****************************************************************************************************************
 * ManufacturedAmmoGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the ManufacturedAmmo GUI program. It loads or collects all the ManufacturedAmmo and displays or collects them * 																								*
 * 																												*
 ***************************************************************************************************************/
package ManufacturedAmmoEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.io.File;

public class ManufacturedAmmoGUI extends JDialog implements ActionListener  {
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    
    //Default windows size.
    private int XSIZE = 400;
    private int YSIZE = 350;

    private int winType = this.NOT_SET;
    private ManufacturedAmmo ma;
    private ManufacturedAmmoListener manufacturedAmmoListener;
           
    private JLabel lotNumberLbl = new JLabel("Lot Number:", SwingConstants.RIGHT); 
    private JLabel caliberLbl = new JLabel("Caliber:", SwingConstants.RIGHT); 
    private JLabel manufacturerLbl = new JLabel("Manufacturer:", SwingConstants.RIGHT); 
    private JLabel datePurchasedLbl = new JLabel("Date Purchased:", SwingConstants.RIGHT); 
    private JLabel bulletLbl = new JLabel("Bullet:", SwingConstants.RIGHT); 
    private JLabel bulletWeightLbl = new JLabel("Bullet Weight:", SwingConstants.RIGHT);
    private JLabel countLbl = new JLabel("Count:", SwingConstants.RIGHT); 
    private JLabel emptyLbl = new JLabel("Is Empty:", SwingConstants.RIGHT);   

    private JTextField lotNumberTxt = new JTextField();
    private JTextField caliberTxt = new JTextField();
    private JTextField manufacturerTxt = new JTextField();
    private JTextField datePurchasedTxt = new JTextField();
    private JTextField bulletTxt = new JTextField();
    private JTextField bulletWeightTxt = new JTextField();
    private JTextField countTxt = new JTextField();
    private JCheckBox  emptyTxt = new JCheckBox(); 
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();
    private ManufacturedAmmoListener ManufacturedAmmoListener;

    public ManufacturedAmmoGUI() {
        this.setTitle("Ammunition");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();	

    }

    public ManufacturedAmmoGUI(int winType) {
        this.setTitle("Ammunition");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();	
        this.winType = winType;
    }

   public ManufacturedAmmoGUI(String xml) {
        ma = new ManufacturedAmmo(xml);
        this.setTitle("Ammunition");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();	
    }

    public ManufacturedAmmoGUI(ManufacturedAmmo ammo) {
        ma = ammo;
        this.setTitle("Ammunition");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();	
    }

    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "ammo.jpg";
            File myObj = new File(ammoIconPath);
	    BufferedImage image = ImageIO.read(myObj);
	    this.setIconImage(image);
	} catch (IOException e) {
	    System.err.println("Error creating icon: "+e);
	}
    }   
	
    //Builds the window based on the winType variable.
    public void buildWindow(int wt) {
        winType = wt;		

        switch(winType) {
            case INSERT: //Build the insert window.
                buildInsert();
                break;
            case UPDATE: //Build the update window.
                if(ma.getLotNumber().trim().isBlank()) {
                        AlertDialog("Cases has not been set for ManufacturedAmmoGUI.");
                        return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(ma.getLotNumber().trim().isBlank()) {
                        AlertDialog("Cases has not been set for ManufacturedAmmoGUI.");
                        return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(ma.getLotNumber().trim().isBlank()) {
                        AlertDialog("Cases has not been set for ManufacturedAmmoGUI.");
                        return;
                }
                buildView();
                break;
            default:
                AlertDialog("The window type has not been set for ManufacturedAmmoGUI.");
                return;	    		
        }

        //Labels and text fields.
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        
        c.anchor = GridBagConstraints.FIRST_LINE_START 	;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(lotNumberLbl, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        add(lotNumberTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(caliberLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(caliberTxt, c);
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(manufacturerLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(manufacturerTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(datePurchasedLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        add(datePurchasedTxt, c); 
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(bulletLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(bulletTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(bulletWeightLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(bulletWeightTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(countLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        add(countTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(emptyLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 2;
        add(emptyTxt, c);   
  
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 3;
        add(new JLabel("  "), c);   
             
        //Page Buttons
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 9; 
        c.gridwidth = 1;
        add(centerBtn, c);

        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 9;
        c.gridwidth = 1;
        add(rightBtn, c);   
    }
	
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Ammo Lot Number "+ma.getLotNumber()) == 1) {
                        if(createAmmo()) {
                            ManufacturedAmmoEvent ev = new ManufacturedAmmoEvent(this, ma, ManufacturedAmmoEvent.INSERT_LOTNUMBER);
                
                            if(ManufacturedAmmoListener != null)
                                ManufacturedAmmoListener.ManufacturedAmmoEventOccurred(ev);
                            if(this.confirmDialog("Save the reloading label for "+ma.getLotNumber()) == 1) {
                                ManufacturedAmmoPDF malPDF = new ManufacturedAmmoPDF(ma);
                                malPDF.saveReloadningLabel("");
                            }
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Ammo Lot number "+ma.getLotNumber()) == 1) {
                        if(createAmmo()) {
                            ManufacturedAmmoEvent ev = new ManufacturedAmmoEvent(this, ma, ManufacturedAmmoEvent.UPDATE_LOTNUMBER);
                
                            if(ManufacturedAmmoListener != null)
                                ManufacturedAmmoListener.ManufacturedAmmoEventOccurred(ev);
                            if(this.confirmDialog("Save the reloading label for "+ma.getLotNumber()) == 1) {
                                ManufacturedAmmoPDF malPDF = new ManufacturedAmmoPDF(ma);
                                malPDF.saveReloadningLabel("");
                            }
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Ammo Lot Number "+ma.getLotNumber()) == 1) {
                            ManufacturedAmmoEvent ev = new ManufacturedAmmoEvent(this, ma, ManufacturedAmmoEvent.DELETE_LOTNUMBER);
                
                            if(ManufacturedAmmoListener != null)
                                ManufacturedAmmoListener.ManufacturedAmmoEventOccurred(ev);
                    }
                    exit();						
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    System.err.println("The window type has not been set for ManufacturedAmmoGUI.");
                    exit();
                    break;	    		
            }
    	} else if(actEvt.getSource() == rightBtn) {
            if(this.confirmDialog("Save the reloading label for "+ma.getLotNumber()) == 1) {
                ManufacturedAmmoPDF malPDF = new ManufacturedAmmoPDF(ma);
                malPDF.saveReloadningLabel("");
            }
            exit();
    	} 
    }
    
    public void buildView() {   	
    	lotNumberTxt.setText(""+ma.getLotNumber()); 
    	caliberTxt.setText(ma.getCaliber());
    	manufacturerTxt.setText(ma.getManufacturer());
    	datePurchasedTxt.setText(ma.getDatePurchased());
    	bulletTxt.setText(ma.getBullet());
    	bulletWeightTxt.setText(""+ma.getBulletWeight());
        countTxt.setText(""+ma.getCount());
    	emptyTxt.setSelected(ma.isEmpty());      	
    	lotNumberTxt.setEditable(false);
    	caliberTxt.setEditable(false);
    	manufacturerTxt.setEditable(false);
    	datePurchasedTxt.setEditable(false);
    	bulletTxt.setEditable(false);
    	bulletWeightTxt.setEditable(false);
        countTxt.setEditable(false);
    	emptyTxt.setEnabled(false);
    	centerBtn.setText("OK");
        rightBtn.setText("Save");
    	centerBtn.addActionListener(this);
    	rightBtn.addActionListener(this);
    }

    public void buildDelete() {
    	lotNumberTxt.setText(""+ma.getLotNumber()); 
    	caliberTxt.setText(ma.getCaliber());
    	manufacturerTxt.setText(ma.getManufacturer());
    	datePurchasedTxt.setText(ma.getDatePurchased());
    	bulletTxt.setText(ma.getBullet());
        bulletWeightTxt.setText(""+ma.getBulletWeight());
    	countTxt.setText(""+ma.getCount());
    	emptyTxt.setSelected(ma.isEmpty());      	
    	lotNumberTxt.setEditable(false);
    	caliberTxt.setEditable(false);
    	manufacturerTxt.setEditable(false);
    	datePurchasedTxt.setEditable(false);
    	bulletTxt.setEditable(false);
    	bulletWeightTxt.setEditable(false);
    	countTxt.setEditable(false);
    	emptyTxt.setEnabled(false);
    	centerBtn.setText("Delete");
    	centerBtn.addActionListener(this);
    	rightBtn.setText("Cancel");
    	rightBtn.addActionListener(this);
    }

    public void buildInsert() {
    	lotNumberTxt.setText(""); 
    	caliberTxt.setText("");
    	manufacturerTxt.setText("");
    	datePurchasedTxt.setText("");
    	bulletTxt.setText("");
        bulletWeightTxt.setText("");
    	countTxt.setText("");
    	emptyTxt.setSelected(false);      	
    	lotNumberTxt.setEditable(true);
    	caliberTxt.setEditable(true);
    	manufacturerTxt.setEditable(true);
    	datePurchasedTxt.setEditable(true);
    	bulletTxt.setEditable(true);
    	bulletWeightTxt.setEditable(false);
    	countTxt.setEditable(true);
    	emptyTxt.setEnabled(true);
    	centerBtn.setText("Insert");
    	centerBtn.addActionListener(this);
    	rightBtn.setText("Cancel");
    	rightBtn.addActionListener(this);
    }

    public void buildUpdate() {
    	lotNumberTxt.setText(""+ma.getLotNumber()); 
    	caliberTxt.setText(ma.getCaliber());
    	manufacturerTxt.setText(ma.getManufacturer());
    	datePurchasedTxt.setText(ma.getDatePurchased());
    	bulletTxt.setText(ma.getBullet());
        bulletWeightTxt.setText(""+ma.getBulletWeight());
    	countTxt.setText(""+ma.getCount());
    	emptyTxt.setSelected(ma.isEmpty());      	
    	lotNumberTxt.setEditable(false);
    	caliberTxt.setEditable(true);
    	manufacturerTxt.setEditable(true);
    	datePurchasedTxt.setEditable(true);
    	bulletTxt.setEditable(true);
    	bulletWeightTxt.setEditable(false);
    	countTxt.setEditable(true);
    	emptyTxt.setEnabled(true);
    	centerBtn.setText("Update");
    	centerBtn.addActionListener(this);
    	rightBtn.setText("Cancel");
    	rightBtn.addActionListener(this);
    }

    public void setCloseType(int cl) {
        this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
        return this.getDefaultCloseOperation();
    }
	
    private void exit() {
        switch (this.getCloseType()) {
            case WindowConstants.EXIT_ON_CLOSE:
                System.exit(0);
            case WindowConstants.DISPOSE_ON_CLOSE:
                this.dispose();
                break;
            case WindowConstants.HIDE_ON_CLOSE:
                this.setVisible(false);
                break;
            default:
                break;
        }
    }
    
    private boolean createAmmo() {
    	try {
            ma.setLotNumber(lotNumberTxt.getText());
                ma.setCaliber(caliberTxt.getText());
                ma.setManufacturer(manufacturerTxt.getText());
                ma.setDatePurchased(datePurchasedTxt.getText());
                ma.setBullet(bulletTxt.getText());
                ma.setBulletWeight(bulletWeightTxt.getText());
                ma.setCount(countTxt.getText());
                ma.setEmpty(emptyTxt.isSelected());
            } catch(Exception e) {
                this.AlertDialog("Entry Error: "+e);
                return false;
            }
            if(ma.isValid()) {
                return true;
            } else {
                this.AlertDialog("Something is wrong with this Ammunition");
                return false;
            }
    }
    //Used to make sure the user intends to make the changes.
    private int confirmDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(this, "Do you want to continue?", t,
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (response) {
            case JOptionPane.NO_OPTION:
                return 0;
            case JOptionPane.YES_OPTION:
                return 1;
            case JOptionPane.CLOSED_OPTION:
                return 2;
            default:
                break;
        }
        return -1;
    }
   
    //Alerts the user if error happen.
    private void AlertDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(this, t, "ALERT!", JOptionPane.ERROR_MESSAGE);
    }

    public int getWinType() {
        return winType;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }

    public ManufacturedAmmo getManufacturedAmmo() {
        return ma;
    }

    public void setManufacturedAmmo(ManufacturedAmmo ma) {
        this.ma = ma;
    }
    
    public void setManufacturedAmmoListener(ManufacturedAmmoListener listener) {
        this.ManufacturedAmmoListener = listener;
    }   	
}

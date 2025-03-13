/*
 * CasesGUI.java
 * 
 * Created on Feb 28, 2021
 * by Todd Brenneman
 * 
 * This is the window used to display a single case data pulled from the Ammo database.
 * Final version 1.0
 */
/*
+-------------+-------------+------+-----+---------+-------+
| Field       | Type        | Null | Key | Default | Extra |
+-------------+-------------+------+-----+---------+-------+
| CaseMaker   | varchar(50) |      |     |         |       |
| Caliber     | varchar(25) |      |     |         |       |
| LotNumber   | varchar(30) |      | PRI |         |       |
| Type        | varchar(25) |      |     |         |       |
| LotCount    | int(11)     |      |     | 0       |       |
| LotCost     | float       |      |     | 0       |       |
| CostPerCase | float       |      |     | 0       |       |
+-------------+-------------+------+-----+---------+-------+
*/
package CasesEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import BaseClasses.FormatFloat;
import java.io.File;

public class CasesGUI extends JDialog implements ActionListener, FocusListener  {
	
    private static final long serialVersionUID = -9053818377407470451L;
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 350;
    private final int YSIZE = 300;

    private Cases cs = new Cases();
    private FormatFloat ff = new FormatFloat();

    private JLabel caseMakerLbl = new JLabel("Maker:", SwingConstants.RIGHT);
    private JLabel caseCaliberLbl = new JLabel("Caliber:", SwingConstants.RIGHT);
    private JLabel caseLotNumLbl = new JLabel("Lot Number:", SwingConstants.RIGHT);
    private JLabel caseTypeLbl = new JLabel("Type:", SwingConstants.RIGHT);
    private JLabel caseLotCountLbl = new JLabel("Lot Count:", SwingConstants.RIGHT);
    private JLabel caseLotCostLbl = new JLabel("Lot Cost:", SwingConstants.RIGHT);
    private JLabel caseCostPerCaseLbl = new JLabel("Cost Per Case:", SwingConstants.RIGHT);

    private JTextArea caseMakerTxt = new JTextArea();
    private JTextArea caseCaliberTxt = new JTextArea();
    private JTextArea caseLotNumTxt = new JTextArea();
    private JTextArea caseTypeTxt = new JTextArea();
    private JTextArea caseLotCountTxt = new JTextArea();
    private JTextArea caseLotCostTxt = new JTextArea();
    private JTextArea caseCostPerCaseTxt = new JTextArea();

    private int winType = CasesGUI.NOT_SET;
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();

    private String[] caseTypeStr = { "Recycled", "New", "Junk"};
    private JComboBox <String> caseTypeList = new JComboBox<String>(caseTypeStr);
    private CasesListener CasesListener;

    //default constructor.
    public CasesGUI() {
        this.setTitle("Cases");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.makeIcon();
    }

    public CasesGUI(int winType) {
        this.setTitle("Cases");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.makeIcon();
        this.winType = winType;
    }

    //Create a new instant and set the cases data to be displayed.
    public CasesGUI(Cases ics) {
        this.setTitle("Cases");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        cs = ics;
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //Create a new instant and set the cases data from xml data.
    public CasesGUI(String xml) {
        this.setTitle("Cases");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        cs = new Cases(xml);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //Create a new window and set the cases data and window type.
    public CasesGUI(Cases ics, int wt) {
        this.setTitle("Cases");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        cs = ics;
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
        this.buildWindow(wt);
    }

    //create a new window and set the cases data from a xml string and set the wintype.
    public CasesGUI(String xml, int wt) {
        this.setTitle("Cases");
        this.setSize(XSIZE, YSIZE);
        cs = new Cases(xml);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
        this.buildWindow(wt);
    }	

    //create a new instant and set the database connection data.

    //Creates the little icon shown on the JFrame.
    private void makeIcon() {
        try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "Cases.jpg";
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
                if(cs.getLotNumber().isBlank()) {
                    AlertDialog("Cases has not been set for CaseGUI.");
                    return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(cs.getLotNumber().isBlank()) {
                    AlertDialog("Cases has not been set for CaseGUI.");
                    return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(cs.getLotNumber().isBlank()) {
                    AlertDialog("Cases has not been set for CaseGUI.");
                    return;
                }
                buildView();
                break;
            default:
                AlertDialog("The window type has not been set for CaseGUI.");
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
        add(caseMakerLbl, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        add(caseMakerTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(caseCaliberLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(caseCaliberTxt, c);
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(caseLotNumLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(caseLotNumTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(caseTypeLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        if(winType == INSERT || winType == UPDATE) {
        	add(caseTypeList, c);
        } else {
        	add(caseTypeTxt, c); 
        }
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(caseLotCountLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(caseLotCountTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(caseLotCostLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(caseLotCostTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(caseCostPerCaseLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        add(caseCostPerCaseTxt, c);   
  
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        add(new JLabel("  "), c);   
             
        //Page Buttons
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 8; 
        c.gridwidth = 1;
        add(centerBtn, c);

        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        if(winType != CasesGUI.VIEW) {
            add(rightBtn, c);   
        } else {
            add(new JLabel("        "),c);
        }
    }
	
    //used to control what happens when the window is closed. 
    public void setCloseType(int cl) {
        this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
        return this.getDefaultCloseOperation();
    }

    //builds the display items for a insert window. Everything is blank except the Type list.
    private void buildInsert() {
        caseMakerTxt.setText("");
        caseCaliberTxt.setText("");
        caseLotNumTxt.setText("");
        caseTypeList.setSelectedIndex(1);
        caseTypeList.addActionListener(this);
        caseLotCountTxt.setText("");
        caseLotCountTxt.addFocusListener(this);
        caseLotCostTxt.setText("");
        caseLotCostTxt.addFocusListener(this);
        caseCostPerCaseTxt.setText("");
        caseCostPerCaseTxt.setEditable(false);
        centerBtn.setText("Insert");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
	//builds the display items for the Update window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildUpdate() {
        caseMakerTxt.setText(cs.getCaseMaker());
        caseCaliberTxt.setText(cs.getCaliber());
        caseLotNumTxt.setText(cs.getLotNumber());
        caseLotNumTxt.setEditable(false);
        if(cs.getType().contains("cyc"))
            caseTypeList.setSelectedIndex(0);
        else if (cs.getType().contains("ew"))
            caseTypeList.setSelectedIndex(1);
        else
            caseTypeList.setSelectedIndex(2);
        caseLotCountTxt.setText(Integer.toString(cs.getLotCount()));
        caseLotCostTxt.setText("$"+ff.floatConvert(cs.getLotCost(),3,2));
        caseCostPerCaseTxt.setText("$"+ff.floatConvert(cs.getCostPerCase(), 2, 4));
        caseCostPerCaseTxt.setEditable(false);
        centerBtn.setText("Update");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //Builds the display items for the Delete window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildDelete() {
        caseMakerTxt.setText(cs.getCaseMaker());
        caseMakerTxt.setEditable(false);
        caseCaliberTxt.setText(cs.getCaliber());
        caseCaliberTxt.setEditable(false);
        caseLotNumTxt.setText(cs.getLotNumber());
        caseLotNumTxt.setEditable(false);
        caseTypeTxt.setText(cs.getType());
        caseTypeTxt.setEditable(false);
        caseLotCountTxt.setText(Integer.toString(cs.getLotCount()));
        caseLotCountTxt.setEditable(false);
        caseLotCostTxt.setText("$"+ff.floatConvert(cs.getLotCost(),3,2));
        caseLotCostTxt.setEditable(false);
        caseCostPerCaseTxt.setText("$"+ff.floatConvert(cs.getCostPerCase(), 2, 4));
        caseCostPerCaseTxt.setEditable(false);
        centerBtn.setText("Delete");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //Builds the display items for the View window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildView() {
        caseMakerTxt.setText(cs.getCaseMaker());
        caseMakerTxt.setEditable(false);
        caseCaliberTxt.setText(cs.getCaliber());
        caseCaliberTxt.setEditable(false);
        caseLotNumTxt.setText(cs.getLotNumber());
        caseLotNumTxt.setEditable(false);
        caseTypeTxt.setText(cs.getType());
        caseTypeTxt.setEditable(false);
        caseLotCountTxt.setText(Integer.toString(cs.getLotCount()));
        caseLotCountTxt.setEditable(false);
        caseLotCostTxt.setText("$"+ff.floatConvert(cs.getLotCost(),3,2));
        caseLotCostTxt.setEditable(false);
        caseCostPerCaseTxt.setText("$"+ff.floatConvert(cs.getCostPerCase(), 2, 4));
        caseCostPerCaseTxt.setEditable(false);
        centerBtn.setText("OK");
        centerBtn.addActionListener(this);
    }
	
    //allows the calling class to set whatever case data it wishes to the local private cases class.
    public void setCases(Cases c)
    {
        cs=c;
    }
	
    //Creates a cases class from the displayed window data and error checks it.
    private boolean createCase() {
        try {
            cs.setCaliber(caseCaliberTxt.getText().trim());
            cs.setCaseMaker(caseMakerTxt.getText().trim());
            cs.setLotCount(caseLotCountTxt.getText().trim());
            cs.setLotNumber(caseLotNumTxt.getText().trim());
            String cst = caseTypeTxt.getText().trim();
            if(cst.isBlank()) {
                    switch (caseTypeList.getSelectedIndex()) {
                            case 0:
                                    cst = "Recycled";
                                    break;
                            case 1:
                                    cst = "New";
                                    break;
                            default:
                                    cst = "Junk";
                                    break;
                    }
            }
            cs.setType(cst);
            cs.setLotCost(caseLotCostTxt.getText().trim());
        } catch(Exception e) {
            this.AlertDialog("Entry Error: "+e);
            return false;
        }
        if(cs.isValid()) {
            return true;
        } else {
            this.AlertDialog("Something is wrong");
            return false;
        }
    }
	
	
    //button listener to handle input from the buttons on the window.
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Case Lot Number "+cs.getLotNumber()) == 1) {
                        if(createCase()) {
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Case Lot Number "+cs.getLotNumber()) == 1) {
                        if(createCase()) {
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Case Lot Number "+cs.getLotNumber()) == 1) {
                        exit();
                    } else {
                        AlertDialog("Error?");
                    }						
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    System.err.println("The window type has not been set for CaseGUI.");
                    exit();
                    break;	    		
            }
    	} else if(actEvt.getSource() == rightBtn) {
            exit();
    	} else if(actEvt.getSource() == caseTypeList) {
            if(caseTypeList.getSelectedIndex() != 1) {
                caseLotCostTxt.setText("$0.00");
                caseCostPerCaseTxt.setText("$0.00");
            } else {
                caseLotCostTxt.setText("");
                caseCostPerCaseTxt.setText("");
            }
    	}
    }
    
    public void focusGained(FocusEvent focusEvt) {}
    
    public void focusLost(FocusEvent focusEvt) {
    	if(focusEvt.getSource() == caseLotCostTxt){   //Check if the value of caseLotCostTxt has changed and format the text box.
            String tcs = caseLotCostTxt.getText().trim();
            if(!(tcs.contains("."))) {
                tcs=tcs+".0";
            }
            if(tcs.trim().isBlank()) {
                caseLotCostTxt.setText("");
            } else if(tcs.contains("$")) { // prevents dollar signs from stacking up.
                tcs=tcs.substring(1);
                caseLotCostTxt.setText("$"+ff.floatConvert(tcs,0,2));
            } else {
                caseLotCostTxt.setText("$"+ff.floatConvert(tcs,0,2));
            }
    			
    	}
    	
    	if((focusEvt.getSource() == caseLotCostTxt) || (focusEvt.getSource() == caseLotCountTxt)) { //check if caselotCounttxt or caseLotCost has changed and update costPerCase.
            if((caseLotCountTxt.getText().trim().isBlank()) || (caseLotCostTxt.getText().trim().isBlank())) {
                caseCostPerCaseTxt.setText("$0.00");
            } else {
                cs.setLotCount(caseLotCountTxt.getText().trim());
                cs.setLotCost(caseLotCostTxt.getText().trim());
                String tcs = caseLotCostTxt.getText().trim();
                if(tcs.trim().isBlank()) {
                    caseLotCostTxt.setText("");
                } else if(tcs.contains("$")) { // prevents dollar signs from stacking up.
                    tcs=tcs.substring(1);
                    caseLotCostTxt.setText("$"+ff.floatConvert(tcs,0,2));
                } else {
                    caseLotCostTxt.setText("$"+ff.floatConvert(tcs,0,2));
                }
                cs.setCostPerCase(); 
                caseCostPerCaseTxt.setText("$"+ff.floatConvert(cs.getCostPerCase(),0,2));   			
            }
    	}
    }
    
    //handles the exit after the buttons are pressed.
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
    
    public void setCasesListener(CasesListener listener) {
        this.CasesListener = listener;
    }
}

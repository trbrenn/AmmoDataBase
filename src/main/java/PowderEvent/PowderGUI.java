/****************************************************************************************************************
 * PowderGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the Powder GUI program. It displays or collects Powder data for the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package PowderEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import BaseClasses.FormatFloat;
import java.io.File;

/**
 * @author trbrenn
 *
 */
public class PowderGUI extends JDialog implements ActionListener  {
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 350;
    private final int YSIZE = 300;

    private Powder ps = new Powder();
    private FormatFloat ff = new FormatFloat();

    private JLabel PowderMakerLbl = new JLabel("Maker:", SwingConstants.RIGHT);
    private JLabel PowderNameLbl = new JLabel("Name:", SwingConstants.RIGHT);
    private JLabel WeightLbl = new JLabel("Weight:", SwingConstants.RIGHT);
    private JLabel LotNumberLbl = new JLabel("Lot Number:", SwingConstants.RIGHT);
    private JLabel LotCostLbl = new JLabel("Lot Cost:", SwingConstants.RIGHT);
    private JLabel CostPerGrainLbl = new JLabel("Cost Per Grain:", SwingConstants.RIGHT);
    private JLabel PurchaseDateLbl = new JLabel("Purchase Date:", SwingConstants.RIGHT);
    private JLabel EmptyLbl = new JLabel("Empty:", SwingConstants.RIGHT);

    private JTextArea PowderMakerTxt = new JTextArea();
    private JTextArea PodwerNameTxt = new JTextArea();
    private JTextArea WeightTxt = new JTextArea();
    private JTextArea LotNumberTxt = new JTextArea();
    private JTextArea LotCostTxt = new JTextArea();
    private JTextArea CostPerGrainTxt = new JTextArea();
    private JTextArea PurchaseDateTxt = new JTextArea();
    private JRadioButton EmptyYesBtn= new JRadioButton("Yes", false);
    private JRadioButton EmptyNoBtn= new JRadioButton("No", false);	

    private int winType = PowderGUI.NOT_SET;
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();
    private PowderListener powderListener;

    //default constructor.
    public PowderGUI() {
        this.setTitle("Powder");
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setSize(XSIZE, YSIZE);
        this.makeIcon();
    }

    public PowderGUI(int winType) {
        this.setTitle("Powder");
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setSize(XSIZE, YSIZE);
        this.makeIcon();
        this.winType = winType;
    }

    //Create a new instant and set the cases data to be displayed.
    public PowderGUI(Powder ics) {
        this.setTitle("Powder");
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        ps = ics;
        this.makeIcon();
    }

    //Create a new instant and set the cases data from xml data.
    public PowderGUI(String xml) {
        this.setTitle("Powder");
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        ps = new Powder(xml);
        this.makeIcon();
    }

    //Create a new window and set the cases data and window type.
    public PowderGUI(Powder ics, int wt) {
        this.setTitle("Powder");
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        ps = ics;
        this.makeIcon();
        this.buildWindow(wt);
    }

    //create a new window and set the cases data from a xml string and set the wintype.
    public PowderGUI(String xml, int wt) {
        this.setTitle("Powder");
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        ps = new Powder(xml);
        this.setResizable(false);
        this.makeIcon();
        this.buildWindow(wt);
    }	

    //Creates the little icon shown on the JFrame.
    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "powder.jpg";
            File myObj = new File(ammoIconPath);
	        BufferedImage image = ImageIO.read(myObj);
	        this.setIconImage(image);
	} catch (IOException e) {
	    System.err.println("Error creating icon: "+e);
        }
    }   
	
    public void buildWindow(int wt) {
        winType = wt;		

        switch(winType) {
            case INSERT: //Build the insert window.
                buildInsert();
                break;
            case UPDATE: //Build the update window.
                if(ps.getPowderLotNumber().isBlank()) {
                    AlertDialog("Powder has not been set for PowderGUI.");
                    return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(ps.getPowderLotNumber().isBlank()) {
                    AlertDialog("Powder has not been set for PowderGUI.");
                    return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(ps.getPowderLotNumber().isBlank()) {
                    AlertDialog("Powder has not been set for PowderGUI.");
                    return;
                }
                buildView();
                break;
            default:
                AlertDialog("The window type has not been set for PowderGUI.");
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
        add(LotNumberLbl, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        add(LotNumberTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(PowderMakerLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(PowderMakerTxt, c);
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(PowderNameLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(PodwerNameTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(WeightLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
       	add(WeightTxt, c); 
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        add(new JLabel("Pounds") , c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(LotCostLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(LotCostTxt, c);       
        
       c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(LotCostLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(LotCostTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(CostPerGrainLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        add(CostPerGrainTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(PurchaseDateLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 2;
        add(PurchaseDateTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        add(EmptyLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        add(EmptyYesBtn, c);   
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        add(EmptyNoBtn, c);   

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 9;
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
        if(winType != PowderGUI.VIEW) {
	        add(rightBtn, c);   
        } else {
        	add(new JLabel("        "),c);
        }
    }
	
    //builds the display items for a insert window. Everything is blank except the Type list.
    private void buildInsert() {
        PowderMakerTxt.setText("");
        PodwerNameTxt.setText("");
        WeightTxt.setText("");
        LotNumberTxt.setText("");
        LotCostTxt.setText("");
        CostPerGrainTxt.setText("");
        CostPerGrainTxt.setEditable(false);
        PurchaseDateTxt.setText("2001-01-01");
        EmptyYesBtn= new JRadioButton("Yes", false);
        EmptyYesBtn.addActionListener(this);
        EmptyNoBtn= new JRadioButton("No", true);	
        EmptyNoBtn.addActionListener(this);
        centerBtn.setText("Insert");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //builds the display items for the Update window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildUpdate() {
        PowderMakerTxt.setText(ps.getPowderMaker());
        PodwerNameTxt.setText(ps.getPowderName());
        WeightTxt.setText(Integer.toString(ps.getLotWeight()));
        LotNumberTxt.setText(ps.getPowderLotNumber());
        LotNumberTxt.setEditable(false);
        LotCostTxt.setText("$"+ff.floatConvert(ps.getLotCost(),3,2));
        CostPerGrainTxt.setText("$"+ff.floatConvert(ps.getCostPerGrain(),3,4));
        CostPerGrainTxt.setEditable(false);
        PurchaseDateTxt.setText(ps.getDatePurchased());
        if(ps.isEmpty()) {
            EmptyYesBtn= new JRadioButton("Yes", true);
            EmptyNoBtn= new JRadioButton("No", false);	

        } else {
            EmptyYesBtn= new JRadioButton("Yes", false);
            EmptyNoBtn= new JRadioButton("No", true);
        }
        EmptyYesBtn.addActionListener(this);
        EmptyNoBtn.addActionListener(this);
        centerBtn.setText("Update");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //Builds the display items for the Delete window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildDelete() {
        PowderMakerTxt.setText(ps.getPowderMaker());
        PowderMakerTxt.setEditable(false);
        PodwerNameTxt.setText(ps.getPowderName());
        PodwerNameTxt.setEditable(false);
        WeightTxt.setText(Integer.toString(ps.getLotWeight()));
        WeightTxt.setEditable(false);
        LotNumberTxt.setText(ps.getPowderLotNumber());
        LotNumberTxt.setEditable(false);
        LotCostTxt.setText("$"+ff.floatConvert(ps.getLotCost(),3,2));
        LotCostTxt.setEditable(false);
        CostPerGrainTxt.setText("$"+ff.floatConvert(ps.getCostPerGrain(), 2, 4));
        CostPerGrainTxt.setEditable(false);
        PurchaseDateTxt.setText(ps.getDatePurchased());
        PurchaseDateTxt.setEditable(false);
        if(ps.isEmpty()) {
            EmptyYesBtn= new JRadioButton("Yes", true);
            EmptyNoBtn= new JRadioButton("No", false);	

        } else {
            EmptyYesBtn= new JRadioButton("Yes", false);
            EmptyNoBtn= new JRadioButton("No", true);
        }
        EmptyYesBtn.setEnabled(false);
        EmptyNoBtn.setEnabled(false);
        centerBtn.setText("Delete");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //Builds the display items for the View window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildView() {
        PowderMakerTxt.setText(ps.getPowderMaker());
        PowderMakerTxt.setEditable(false);
        PodwerNameTxt.setText(ps.getPowderName());
        PodwerNameTxt.setEditable(false);
        WeightTxt.setText(Integer.toString(ps.getLotWeight()));
        WeightTxt.setEditable(false);
        LotNumberTxt.setText(ps.getPowderLotNumber());
        LotNumberTxt.setEditable(false);
        LotCostTxt.setText("$"+ff.floatConvert(ps.getLotCost(),3,2));
        LotCostTxt.setEditable(false);
        CostPerGrainTxt.setText("$"+ff.floatConvert(ps.getCostPerGrain(), 2, 4));
        CostPerGrainTxt.setEditable(false);
        PurchaseDateTxt.setText(ps.getDatePurchased());
        PurchaseDateTxt.setEditable(false);
        if(ps.isEmpty()) {
            EmptyYesBtn= new JRadioButton("Yes", true);
            EmptyNoBtn= new JRadioButton("No", false);	

        } else {
            EmptyYesBtn= new JRadioButton("Yes", false);
            EmptyNoBtn= new JRadioButton("No", true);
        }
        EmptyYesBtn.setEnabled(false);
        EmptyNoBtn.setEnabled(false);

        centerBtn.setText("OK");
        centerBtn.addActionListener(this);
    }
	
    //used to control what happens when the window is closed. 
    public void setCloseType(int cl) {
        this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
        return this.getDefaultCloseOperation();
    }

    public Powder getPowders() {
        return ps;
    }

    public void setPowder(Powder b) {
        ps = b;
    }
	
    //button listener to handle input from the buttons on the window.
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Powder Lot Number "+ps.getPowderLotNumber()) == 1) {
                        if(createPowder()) {
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                   break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Powder Lot Number "+ps.getPowderLotNumber()) == 1) {
                        if(createPowder()) {
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Powder Lot Number "+ps.getPowderLotNumber()) == 1) {
                        exit();
                    } else {
                        AlertDialog("Error?");
                    }	
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    System.err.println("The window type has not been set for PowderGUI.");
                    exit();
                    break;	    		
            }
    	} else if(actEvt.getSource() == rightBtn) {
            exit();
    	} else if(actEvt.getSource() == EmptyYesBtn) {
            EmptyYesBtn.setSelected(true);
            EmptyNoBtn.setSelected(false);    			
    	} else if(actEvt.getSource() == EmptyNoBtn) { 
            EmptyYesBtn.setSelected(false);
            EmptyNoBtn.setSelected(true);  
    	}
    }
    
    private boolean createPowder() {
    	try {
            ps.setPowderMaker(PowderMakerTxt.getText());
            ps.setPowderName(PodwerNameTxt.getText().trim());
            ps.setLotWeightInLbs(WeightTxt.getText().trim());
            ps.setPowderLotNumber(LotNumberTxt.getText().trim());
            ps.setLotCost(LotCostTxt.getText().trim());
            ps.setCostPerGrain();
            if(EmptyYesBtn.isSelected()){
                ps.setEmpty(true);
            } else {
                ps.setEmpty(false);
            }
        } catch(Exception e) {
            this.AlertDialog("Entry Error: "+e);
            return false;
        }
        if(ps.isValid()) {
            return true;
        } else {
            this.AlertDialog("Something is wrong with this powder");
            return false;
        }
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
    
    public void setPowderListener(PowderListener powderListener) {
        this.powderListener = powderListener;
    }
}  	

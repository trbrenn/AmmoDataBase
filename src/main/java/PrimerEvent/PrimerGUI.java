/****************************************************************************************************************
 * PrimerGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the Primer GUI program. It displays or collects Primer data for the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package PrimerEvent;


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
public class PrimerGUI extends JDialog implements ActionListener  {

    private static final long serialVersionUID = 1L;
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 350;
    private final int YSIZE = 360;

    private Primer ps = new Primer();
    private FormatFloat ff = new FormatFloat();

    private JLabel PrimerMakerLbl = new JLabel("Maker:", SwingConstants.RIGHT);
    private JLabel PrimerSizeLbl = new JLabel("Size:", SwingConstants.RIGHT);
    private JLabel PrimerModelLbl = new JLabel("Model:", SwingConstants.RIGHT);
    private JLabel LotNumberLbl = new JLabel("Lot Number:", SwingConstants.RIGHT);
    private JLabel LotCountLbl = new JLabel("Lot Count:", SwingConstants.RIGHT);
    private JLabel LotCostLbl = new JLabel("Lot Cost:", SwingConstants.RIGHT);
    private JLabel CostPerPrimerLbl = new JLabel("Cost Per Primer:", SwingConstants.RIGHT);
    private JLabel EmptyLbl = new JLabel("Empty:", SwingConstants.RIGHT);

    private JTextArea PrimerMakerTxt = new JTextArea();
    private JTextArea PrimerSizeTxt = new JTextArea();
    private JTextArea PrimerModelTxt = new JTextArea();
    private JTextArea LotNumberTxt = new JTextArea();
    private JTextArea LotCountTxt = new JTextArea();
    private JTextArea LotCostTxt = new JTextArea();
    private JTextArea CostPerPrimerTxt = new JTextArea();
    private JRadioButton EmptyYesBtn= new JRadioButton("Yes", false);
    private JRadioButton EmptyNoBtn= new JRadioButton("No", false);	

    private int winType = PrimerGUI.NOT_SET;
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();
    private PrimerListener primerListener;

    //default constructor.
    public PrimerGUI() {
        this.setTitle("Primer");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }
    
    public PrimerGUI(int winType) {
        this.setTitle("Primer");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
        this.winType = winType;
    }
    
    //Create a new instant and set the cases data to be displayed.
    public PrimerGUI(Primer ics) {
        this.setTitle("Primer");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        ps = ics;
        this.makeIcon();
    }

    //Create a new instant and set the cases data from xml data.
    public PrimerGUI(String xml) {
        this.setTitle("Primer");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        ps = new Primer(xml);
        this.makeIcon();
    }

    //Create a new window and set the cases data and window type.
    public PrimerGUI(Primer ics, int wt) {
        this.setTitle("Primer");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        ps = ics;
        this.makeIcon();
        this.buildWindow(wt);
    }

    //create a new window and set the cases data from a xml string and set the wintype.
    public PrimerGUI(String xml, int wt) {
        this.setTitle("Primer");
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        ps = new Primer(xml);
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
            String ammoIconPath = filepath + "primer.jpg";
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
                if(ps.getLotNumber().isBlank()) {
                    AlertDialog("Primer has not been set for PrimerGUI.");
                    return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(ps.getLotNumber().isBlank()) {
                    AlertDialog("Primer has not been set for PrimerGUI.");
                    return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(ps.getLotNumber().isBlank()) {
                    AlertDialog("Primer has not been set for PrimerGUI.");
                    return;
                }
                buildView();
                break;
            default:
                AlertDialog("The window type has not been set for PrimerGUI.");
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
        add(PrimerMakerLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(PrimerMakerTxt, c);
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(PrimerSizeLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(PrimerSizeTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(PrimerModelLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
       	add(PrimerModelTxt, c); 
         
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(LotCountLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(LotCountTxt, c);       
        
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
        add(CostPerPrimerLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        add(CostPerPrimerTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(EmptyLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        add(EmptyYesBtn, c);   
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 1;
        add(EmptyNoBtn, c);   

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
        if(winType != PrimerGUI.VIEW) {
            add(rightBtn, c);   
        } else {
            add(new JLabel("        "),c);
        }
    }
	
    //builds the display items for a insert window. Everything is blank except the Type list.
    private void buildInsert() {
        PrimerMakerTxt.setText("");
        PrimerSizeTxt.setText("");
        PrimerModelTxt.setText("");
        LotNumberTxt.setText("");
        LotCountTxt.setText("");
        LotCostTxt.setText("");
        CostPerPrimerTxt.setText("");
        CostPerPrimerTxt.setEditable(false);
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
        PrimerMakerTxt.setText(ps.getPrimerMaker());
        PrimerSizeTxt.setText(ps.getPrimerSize());
        PrimerModelTxt.setText(ps.getModelNumber());
        LotNumberTxt.setText(ps.getLotNumber());
        LotNumberTxt.setEditable(false);
        LotCountTxt.setText(Integer.toString(ps.getLotCount()));
        LotCostTxt.setText("$"+ff.floatConvert(ps.getLotCost(),3,2));
        CostPerPrimerTxt.setText("$"+ff.floatConvert(ps.getCostPerPrimer(),3,4));
        CostPerPrimerTxt.setEditable(false);
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
        PrimerMakerTxt.setText(ps.getPrimerMaker());
        PrimerMakerTxt.setEditable(false);
        PrimerSizeTxt.setText(ps.getPrimerSize());
        PrimerSizeTxt.setEditable(false);
        PrimerModelTxt.setText(ps.getModelNumber());
        PrimerModelTxt.setEditable(false);
        LotNumberTxt.setText(ps.getLotNumber());
        LotNumberTxt.setEditable(false);
        LotCountTxt.setText(Integer.toString(ps.getLotCount()));
        LotCountTxt.setEditable(false);
        LotCostTxt.setText("$"+ff.floatConvert(ps.getLotCost(),3,2));
        LotCostTxt.setEditable(false);
        CostPerPrimerTxt.setText("$"+ff.floatConvert(ps.getCostPerPrimer(), 2, 4));
        CostPerPrimerTxt.setEditable(false);
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
        PrimerMakerTxt.setText(ps.getPrimerMaker());
        PrimerMakerTxt.setEditable(false);
        PrimerSizeTxt.setText(ps.getPrimerSize());
        PrimerSizeTxt.setEditable(false);
        PrimerModelTxt.setText(ps.getModelNumber());
        PrimerModelTxt.setEditable(false);
        LotNumberTxt.setText(ps.getLotNumber());
        LotNumberTxt.setEditable(false);
        LotCountTxt.setText(Integer.toString(ps.getLotCount()));
        LotCountTxt.setEditable(false);
        LotCostTxt.setText("$"+ff.floatConvert(ps.getLotCost(),3,2));
        LotCostTxt.setEditable(false);
        CostPerPrimerTxt.setText("$"+ff.floatConvert(ps.getCostPerPrimer(), 2, 4));
        CostPerPrimerTxt.setEditable(false);
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

    public Primer getPrimers() {
        return ps;
    }

    public void setPrimer(Primer b) {
        ps = b;
    }
	
	//button listener to handle input from the buttons on the window.
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Primer Lot Number "+ps.getLotNumber()) == 1) {
                        if(createPrimer()) {
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Primer Lot Number "+ps.getLotNumber()) == 1) {
                        if(createPrimer()) {
                            AlertDialog(ps.toString());
                            exit();
                        } else {
                                AlertDialog("Error?");
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Primer Lot Number "+ps.getLotNumber()) == 1) {
                        exit();
                    } else {
                        AlertDialog("Error?");
                    }						
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    System.err.println("The window type has not been set for PrimerGUI.");
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
    
    private boolean createPrimer() {
    	try {
            ps.setPrimerMaker(PrimerMakerTxt.getText());
            ps.setPrimerSize(PrimerSizeTxt.getText().trim());
            ps.setModelNumber(PrimerModelTxt.getText().trim());
            ps.setLotNumber(LotNumberTxt.getText().trim());
            ps.setLotCount(LotCountTxt.getText().trim());
            ps.setLotCost(LotCostTxt.getText().trim());
            ps.setCostPerPrimer();
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
            this.AlertDialog("Something is wrong with this Primer");
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
   	
    public void setPrimerListener(PrimerListener primerListener){
        this.primerListener = primerListener;
    }

}

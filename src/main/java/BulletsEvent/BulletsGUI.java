/****************************************************************************************************************
 * BulletsGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the Bullets GUI program. It displays or collects bullet data for the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package BulletsEvent;

import BaseClasses.FormatFloat;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.io.File;

public class BulletsGUI extends JDialog implements ActionListener, FocusListener {

    private static final long serialVersionUID = 1L;
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 350;
    private final int YSIZE = 430;

    private Bullets bs = new Bullets();
    private FormatFloat ff = new FormatFloat();

    private JLabel BulletMakerLbl = new JLabel("Bullet Maker:", SwingConstants.RIGHT);
    private JLabel BallisticCoefficientLbl = new JLabel("BallisticCoefficient:", SwingConstants.RIGHT);
    private JLabel CaliberLbl = new JLabel("Caliber:", SwingConstants.RIGHT);
    private JLabel WeightLbl = new JLabel("Weight:", SwingConstants.RIGHT);
    private JLabel DescriptionLbl = new JLabel("Description:", SwingConstants.RIGHT);
    private JLabel LotNumberLbl = new JLabel("Lot Number:", SwingConstants.RIGHT);
    private JLabel LotCountLbl = new JLabel("Lot Count:", SwingConstants.RIGHT);
    private JLabel LotCostLbl = new JLabel("Lot Cost:", SwingConstants.RIGHT);
    private JLabel CostPerBulletLbl = new JLabel("Cost Per Bullet:", SwingConstants.RIGHT);
    private JLabel CastAlloyLbl = new JLabel("Cast Alloy:", SwingConstants.RIGHT);
    private JLabel EmptyLbl = new JLabel("Empty:", SwingConstants.RIGHT);
    private JLabel MoldNumberLbl = new JLabel("Mold Number:", SwingConstants.RIGHT);

    private JTextArea BulletMakerTxt = new JTextArea();
    private JTextArea BallisticCoefficientTxt = new JTextArea();
    private JTextArea CaliberTxt = new JTextArea();
    private JTextArea WeightTxt = new JTextArea();
    private JTextArea DescriptionTxt = new JTextArea();
    private JTextArea LotNumberTxt = new JTextArea();
    private JTextArea LotCountTxt = new JTextArea();
    private JTextArea LotCostTxt = new JTextArea();
    private JTextArea CostPerBulletTxt = new JTextArea();
    private JTextArea CastAlloyTxt = new JTextArea();
    private JRadioButton EmptyYesBtn= new JRadioButton("Yes", false);
    private JRadioButton EmptyNoBtn= new JRadioButton("No", false);	
    private JTextArea MoldNumberTxt = new JTextArea();

    private int winType = this.NOT_SET;
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();
    private BulletsListener bulletsListener;

    //default constructor.
    public BulletsGUI() {
        this.setTitle("Bullets");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    public BulletsGUI(int winType) {
        this.setTitle("Bullets");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
        this.winType = winType;
    }

    //Create a new instant and set the bullets data to be displayed.
    public BulletsGUI(Bullets ibs) {
        this.setTitle("Bullets");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        bs = ibs;
        this.makeIcon();
    }

    //Create a new instant and set the bullets data from xml data.
    public BulletsGUI(String xml) {
        this.setTitle("Bullets");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        bs = new Bullets(xml);
        this.makeIcon();
    }

    //Create a new window and set the bullets data and window type.
    public BulletsGUI(Bullets ibs, int wt) {
        this.setTitle("Bullets");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        bs = ibs;
        this.makeIcon();
        this.buildWindow(wt);
    }

    //create a new window and set the bullets data from a xml string and set the wintype.
    public BulletsGUI(String xml, int wt) {
        this.setTitle("Bullets");
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        bs = new Bullets(xml);
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
            String ammoIconPath = filepath + "Bullets.jpg";
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
                if(bs.getLotNumber().isBlank()) {
                    AlertDialog("Bullets has not been set for BulletsGUI.");
                    return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(bs.getLotNumber().isBlank()) {
                    AlertDialog("Bullets has not been set for BulletsGUI.");
                    return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(bs.getLotNumber().isBlank()) {
                    AlertDialog("Bullets has not been set for BulletsGUI.");
                    return;
                }
                buildView();
                break;
            default:
                AlertDialog("The window type has not been set for BulletsGUI.");
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
        add(BulletMakerLbl, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        add(BulletMakerTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(BallisticCoefficientLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(BallisticCoefficientTxt, c);
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(CaliberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(CaliberTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(WeightLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
       	add(WeightTxt, c); 
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(DescriptionLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(DescriptionTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(LotNumberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(LotNumberTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(LotCountLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        add(LotCountTxt, c);   
  
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(LotCostLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 2;
        add(LotCostTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        add(CostPerBulletLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 2;
        add(CostPerBulletTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        add(CastAlloyLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 9;
        c.gridwidth = 2;
        add(CastAlloyTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 1;
        add(EmptyLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 10;
        c.gridwidth = 1;
        add(EmptyYesBtn, c);   
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 10;
        c.gridwidth = 1;
        add(EmptyNoBtn, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 1;
        add(MoldNumberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 11;
        c.gridwidth = 2;
        add(MoldNumberTxt, c);   

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 3;
        add(new JLabel("  "), c);   
             
        //Page Buttons
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 13; 
        c.gridwidth = 1;
        add(centerBtn, c);

        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 13;
        c.gridwidth = 1;
        if(winType != BulletsGUI.VIEW) {
            add(rightBtn, c);   
        } else {
            add(new JLabel("        "),c);
        }
    }
	
    //builds the display items for a insert window. Everything is blank except the Type list.
    private void buildInsert() {
        BulletMakerTxt.setText("");
        BallisticCoefficientTxt.setText("");
        CaliberTxt.setText("");
        WeightTxt.setText("");
        DescriptionTxt.setText("");
        LotNumberTxt.setText("");
        LotCountTxt.setText("");
        LotCountTxt.addFocusListener(this);
        LotCostTxt.setText("");
        LotCostTxt.addFocusListener(this);
        CostPerBulletTxt.setText("");
        CostPerBulletTxt.setEditable(false);
        CastAlloyTxt.setText("");
        EmptyYesBtn= new JRadioButton("Yes", false);
        EmptyYesBtn.addActionListener(this);
        EmptyNoBtn= new JRadioButton("No", true);	
        EmptyNoBtn.addActionListener(this);
        MoldNumberTxt.setText("");
        centerBtn.setText("Insert");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //builds the display items for the Update window. Everything is set by the case data that is loaded into the local bullets class.
    private void buildUpdate() {
        BulletMakerTxt.setText(bs.getBulletMaker());
        BallisticCoefficientTxt.setText(ff.floatConvert(bs.getBC(),3,2));
        CaliberTxt.setText(bs.getCaliber());
        WeightTxt.setText(bs.getWeight());
        DescriptionTxt.setText(bs.getDescription());
        LotNumberTxt.setText(bs.getLotNumber());
        LotNumberTxt.setEditable(false);
        LotCountTxt.setText(Integer.toString(bs.getLotCount()));
        LotCostTxt.setText("$"+ff.floatConvert(bs.getLotCost(),3,2));
        CostPerBulletTxt.setText("$"+ff.floatConvert(bs.getLotCost(),3,4));
        CostPerBulletTxt.setEditable(false);
        CastAlloyTxt.setText("");
        if(bs.isEmpty()) {
            EmptyYesBtn= new JRadioButton("Yes", true);
            EmptyNoBtn= new JRadioButton("No", false);	

        } else {
            EmptyYesBtn= new JRadioButton("Yes", false);
            EmptyNoBtn= new JRadioButton("No", true);
        }
        EmptyYesBtn.addActionListener(this);
        EmptyNoBtn.addActionListener(this);
        MoldNumberTxt.setText(Integer.toString(bs.getMoldNumber()));
        centerBtn.setText("Update");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
	//Builds the display items for the Delete window. Everything is set by the case data that is loaded into the local bullets class.
    private void buildDelete() {
        BulletMakerTxt.setText(bs.getBulletMaker());
        BallisticCoefficientTxt.setText(ff.floatConvert(bs.getBC(),3,2));
        BallisticCoefficientTxt.setEditable(false);
        BulletMakerTxt.setEditable(false);
        CaliberTxt.setText(bs.getCaliber());
        CaliberTxt.setEditable(false);
        WeightTxt.setText(bs.getWeight());
        WeightTxt.setEditable(false);
        DescriptionTxt.setText(bs.getDescription());
        DescriptionTxt.setEditable(false);
        LotNumberTxt.setText(bs.getLotNumber());
        LotNumberTxt.setEditable(false);
        LotCountTxt.setText(Integer.toString(bs.getLotCount()));
        LotCountTxt.setEditable(false);
        LotCostTxt.setText("$"+ff.floatConvert(bs.getLotCost(),3,2));
        LotCostTxt.setEditable(false);
        CostPerBulletTxt.setText("$"+ff.floatConvert(bs.getCostPerBullet(), 2, 4));
        CostPerBulletTxt.setEditable(false);
        CastAlloyTxt.setText("");
        CastAlloyTxt.setEditable(false);
        if(bs.isEmpty()) {
            EmptyYesBtn= new JRadioButton("Yes", true);
            EmptyNoBtn= new JRadioButton("No", false);	

        } else {
            EmptyYesBtn= new JRadioButton("Yes", false);
            EmptyNoBtn= new JRadioButton("No", true);
        }
        EmptyYesBtn.setEnabled(false);
        EmptyNoBtn.setEnabled(false);
        MoldNumberTxt.setText(Integer.toString(bs.getMoldNumber()));
        MoldNumberTxt.setEditable(false);
        centerBtn.setText("Delete");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //Builds the display items for the View window. Everything is set by the case data that is loaded into the local bullets class.
    private void buildView() {
        BulletMakerTxt.setText(bs.getBulletMaker());
        BulletMakerTxt.setEditable(false);
        BallisticCoefficientTxt.setText(ff.floatConvert(bs.getBC(),3,2));
        BallisticCoefficientTxt.setEditable(false);
        CaliberTxt.setText(bs.getCaliber());
        CaliberTxt.setEditable(false);
        WeightTxt.setText(bs.getWeight());
        WeightTxt.setEditable(false);
        DescriptionTxt.setText(bs.getDescription());
        DescriptionTxt.setEditable(false);
        LotNumberTxt.setText(bs.getLotNumber());
        LotNumberTxt.setEditable(false);
        LotCountTxt.setText(Integer.toString(bs.getLotCount()));
        LotCountTxt.setEditable(false);
        LotCostTxt.setText("$"+ff.floatConvert(bs.getLotCost(),3,2));
        LotCostTxt.setEditable(false);
        CostPerBulletTxt.setText("$"+ff.floatConvert(bs.getCostPerBullet(), 2, 4));
        CostPerBulletTxt.setEditable(false);
        CastAlloyTxt.setText(bs.getCastAlloy());
        CastAlloyTxt.setEditable(false);
        if(bs.isEmpty()) {
            EmptyYesBtn= new JRadioButton("Yes", true);
            EmptyNoBtn= new JRadioButton("No", false);	

        } else {
            EmptyYesBtn= new JRadioButton("Yes", false);
            EmptyNoBtn= new JRadioButton("No", true);
        }
        EmptyYesBtn.setEnabled(false);
        EmptyNoBtn.setEnabled(false);
        MoldNumberTxt.setText(Integer.toString(bs.getMoldNumber()));
        MoldNumberTxt.setEditable(false);

        centerBtn.setText("OK");
        centerBtn.addActionListener(this);
    }

    public int getWinType() {
        return winType;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }

    //used to control what happens when the window is closed. 
    public void setCloseType(int cl) {
        this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
        return this.getDefaultCloseOperation();
    }

    public Bullets getBullets() {
        return bs;
    }

    public void setBullets(Bullets b) {
        bs = b;
    }

    //button listener to handle input from the buttons on the window.
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Bullet Lot Number "+bs.getLotNumber()) == 1) {
                        if(createBullet()) {
                            BulletsEvent ev = new BulletsEvent(this, bs, BulletsEvent.INSERT_LOTNUMBER);
                
                            if(bulletsListener != null)
                                bulletsListener.BulletsEventOccurred(ev);
                            exit();
                        } else {
                            AlertDialog("Error: Bullet is not valid!");
                        }
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Bullet Lot Number "+bs.getLotNumber()) == 1) {
                        if(createBullet()) {
                            BulletsEvent ev = new BulletsEvent(this, bs, BulletsEvent.UPDATE_LOTNUMBER);
                
                            if(bulletsListener != null)
                                bulletsListener.BulletsEventOccurred(ev);
                        }
                        exit();
                    } else {
                        AlertDialog("Error?");
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Bullet Lot Number "+bs.getLotNumber()) == 1) {
                        BulletsEvent ev = new BulletsEvent(this, bs, BulletsEvent.DELETE_LOTNUMBER);

                        if(bulletsListener != null)
                            bulletsListener.BulletsEventOccurred(ev);
                        exit();
                    } else {
                        AlertDialog("Error: Bullet is not valid!");
                    }
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    System.err.println("The window type has not been set for BulletsGUI.");
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
    
    public void focusGained(FocusEvent e) {}
    
    public void focusLost(FocusEvent focusEvt) {
    	if(focusEvt.getSource() == LotCostTxt){   //Check if the value of LotCostTxt has changed and format the text box.
            String tbs = LotCostTxt.getText().trim();
            if(!(tbs.contains("."))) {
                tbs=tbs+".0";
            }
            if(tbs.trim().isBlank()) {
                LotCostTxt.setText("");   
            } else if(tbs.contains("$")) { // prevents dollar signs from stacking up.
                tbs=tbs.substring(1);
                LotCostTxt.setText("$"+ff.floatConvert(tbs,0,2));
            } else {
                LotCostTxt.setText("$"+ff.floatConvert(tbs,0,2));
            }	
    	}
    	
    	if((focusEvt.getSource() == LotCostTxt) || (focusEvt.getSource() == LotCountTxt)) { //check if LotCountTxt or caseLotCost has changed and update costPerCase.
            if((LotCountTxt.getText().trim().isBlank()) || (LotCostTxt.getText().trim().isBlank())) {
                CostPerBulletTxt.setText("$0.00");
            } else {
                bs.setLotCount(LotCountTxt.getText().trim());
                bs.setLotCost(LotCostTxt.getText().trim());
                String tbs = LotCostTxt.getText().trim();
                if(tbs.trim().isBlank()) {
                    LotCostTxt.setText("");
                } else if(tbs.contains("$")) { // prevents dollar signs from stacking up.
                    tbs=tbs.substring(1);
                    LotCostTxt.setText("$"+ff.floatConvert(tbs,0,2));
                } else {
                    LotCostTxt.setText("$"+ff.floatConvert(tbs,0,2));
                }
                bs.setCostPerBullet();
                bs.getCostPerBullet(); 
                CostPerBulletTxt.setText("$"+ff.floatConvert(bs.getCostPerBullet(),0,2));   			
            }
    	}
    }  
    
    private boolean createBullet() {
    	try {
            bs.setBulletMaker(BulletMakerTxt.getText());
            String bc = BallisticCoefficientTxt.getText().trim();
            if(bc.isBlank())
                    bs.setBC("0");
            else
                    bs.setBC(bc);
            bs.setCaliber(CaliberTxt.getText());
            bs.setWeight(WeightTxt.getText());
            bs.setDescription(DescriptionTxt.getText());
            bs.setLotNumber(LotNumberTxt.getText());
            bs.setLotCount(LotCountTxt.getText());
            String cost = LotCostTxt.getText();
            int commaIndex = cost.indexOf(",");
            if(commaIndex > 0)
                cost = cost.substring(0,commaIndex)+ cost.substring(commaIndex+1);
            bs.setLotCost(cost);
            bs.setCostPerBullet();
            String cpb = CastAlloyTxt.getText().trim();
            if(cpb.isBlank()) {
                    bs.setCastAlloy("N/A");
            } else {
                    bs.setCastAlloy(cpb);
            }
            if(EmptyYesBtn.isSelected()){
                    bs.setEmpty(true);
            } else {
                    bs.setEmpty(false);
            }
            String mn = MoldNumberTxt.getText().trim();
            if(mn.isBlank()) {
                    bs.setMoldNumber("0");
            } else {
                    bs.setMoldNumber(mn);
            }
        } catch(Exception e) {
            this.AlertDialog("Entry Error: "+e);
            return false;
        }
        if(bs.isValidBullet()) {
            return true;
        } else {
            this.AlertDialog("Something is wrong with this bullet");
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
        int response = JOptionPane.showConfirmDialog(this, "Do you want to continue?", t,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
    
    public void setBulletsListener(BulletsListener listener) {
        this.bulletsListener = listener;
    }
}
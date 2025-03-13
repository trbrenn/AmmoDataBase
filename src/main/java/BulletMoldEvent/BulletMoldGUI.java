/****************************************************************************************************************
 * BulletMoldGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the BulletMold GUI program. It displays or collects bullet molds data for the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package BulletMoldEvent;

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
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import BaseClasses.FormatFloat;
import java.io.File;


/**
 * 
 */
public class BulletMoldGUI extends JDialog implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 350;
    private final int YSIZE = 320;

    private BulletMold bm;
    private int winType = BulletMoldGUI.NOT_SET;
    private FormatFloat ff = new FormatFloat();

    private JLabel IDNumberLbl = new JLabel("Mold ID:", SwingConstants.RIGHT);
    private JLabel MakerLbl = new JLabel("Maker:", SwingConstants.RIGHT);
    private JLabel NumberLbl = new JLabel("Mold Number:", SwingConstants.RIGHT);
    private JLabel DescriptionLbl = new JLabel("Description:", SwingConstants.RIGHT);
    private JLabel DiameterLbl = new JLabel("Bullet Diameter", SwingConstants.RIGHT);
    private JLabel WeightLbl = new JLabel("Bullet Weight", SwingConstants.RIGHT);
    private JLabel TopPunchLbl = new JLabel("Top Punch:", SwingConstants.RIGHT);
    private JLabel GasCheckLbl = new JLabel("Gas Check:", SwingConstants.RIGHT);

    private JTextArea IDNumberTxt = new JTextArea();
    private JTextArea MakerTxt = new JTextArea();
    private JTextArea NumberTxt = new JTextArea();
    private JTextArea DescriptionTxt = new JTextArea();
    private JTextArea DiameterTxt = new JTextArea();
    private JTextArea WeightTxt = new JTextArea();
    private JTextArea TopPunchTxt = new JTextArea();
    private JRadioButton GasCheckYesBtn= new JRadioButton("Yes", false);
    private JRadioButton GasCheckNoBtn= new JRadioButton("No", false);	

    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();
    private BulletMoldListener bulletMoldListener;
    
    public BulletMoldGUI() {
            this.setTitle("Bullet Molds");
            this.setResizable(false);
            this.setModalityType(DEFAULT_MODALITY_TYPE);
            this.setSize(XSIZE, YSIZE);
            this.makeIcon();
    }

    public BulletMoldGUI(int winType) {
            this.setTitle("Bullet Molds");
            this.setResizable(false);
            this.setModalityType(DEFAULT_MODALITY_TYPE);
            this.setSize(XSIZE, YSIZE);
            this.winType = winType;
            this.makeIcon();
    }

    public BulletMoldGUI(BulletMold data) {
            this.setTitle("Bullet Molds");
            this.setResizable(false);
            this.setModalityType(DEFAULT_MODALITY_TYPE);
            this.setSize(XSIZE, YSIZE);
            this.makeIcon();
            bm = data;
    }

    public BulletMoldGUI(BulletMold data, int winType) {
            this.setTitle("Bullet Molds");
            this.setResizable(false);
            this.setModalityType(DEFAULT_MODALITY_TYPE);
            this.setSize(XSIZE, YSIZE);
            this.makeIcon();
            this.bm = data;
            this.winType = winType;
    }

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
	
    public BulletMold getBm() {
            return bm;
    }

    public void setBm(BulletMold bm) {
            this.bm = bm;
    }

    public void setCloseType(int cl) {
            this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
            return this.getDefaultCloseOperation();
    }

    public int getWinType() {
            return winType;
    }

    public void setWinType(int winType) {
            this.winType = winType;
    }

    public void buildWindow(int wt) {
        winType = wt;		

        switch(winType) {
            case INSERT: //Build the insert window.
                buildInsert();
                break;
            case UPDATE: //Build the update window.
                if(bm.getID() <= 0) {
                    AlertDialog("BulletMold ID has not been set for BulletMoldsGUI.");
                    return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(bm.getID() <= 0) {
                    AlertDialog("BulletMold ID  has not been set for BulletMoldsGUI.");
                    return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(bm.getID() <= 0) {
                    AlertDialog("BulletMold ID  has not been set for ReloadingDiesGUI.");
                    return;
                }
                buildView();
                break;
            default:
                AlertDialog("The window type has not been set for ReloadingDiesGUI.");
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
        add(IDNumberLbl, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        add(IDNumberTxt, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(MakerLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(MakerTxt, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(NumberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(NumberTxt, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(DescriptionLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        add(DescriptionTxt, c); 

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(DiameterLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(DiameterTxt, c);       
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        add(new JLabel("Inches") , c);

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(WeightLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(WeightTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(TopPunchLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridwidth = 2;
        add(TopPunchTxt, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(GasCheckLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        add(GasCheckYesBtn, c);   
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 1;
        add(GasCheckNoBtn, c);   

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
        if(winType != BulletMoldGUI.VIEW) {
            add(rightBtn, c);   
        } else {
            add(new JLabel("        "),c);
        }
    }

    private void buildInsert() {
        IDNumberTxt.setText("Auto Index");
        IDNumberTxt.setEditable(false);
        centerBtn.setText("Insert");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);		
    }

    private void buildUpdate() {
        IDNumberTxt.setText(""+bm.getID());
        IDNumberTxt.setEditable(false);
        MakerTxt.setText(bm.getMaker());
        MakerTxt.setEditable(false);
        NumberTxt.setText(bm.getNumber());
        NumberTxt.setEditable(false);
        DescriptionTxt.setText(bm.getDescription());
        DescriptionTxt.setEditable(false);
        DiameterTxt.setText(ff.floatConvert(bm.getDiameter(),0,3));
        DiameterTxt.setEditable(false);
        WeightTxt.setText(ff.floatConvert(bm.getWeight(),0,3));
        WeightTxt.setEditable(false);
        TopPunchTxt.setText(bm.getTopPunch());
        TopPunchTxt.setEditable(false);
        if(bm.isGasCheck()) {
            GasCheckYesBtn= new JRadioButton("Yes", true);
            GasCheckNoBtn= new JRadioButton("No", false);	
        } else {
            GasCheckYesBtn= new JRadioButton("yes", false);
            GasCheckNoBtn= new JRadioButton("no", true);				
        }
        GasCheckYesBtn.setEnabled(false);
        GasCheckNoBtn.setEnabled(false);
        centerBtn.setText("Update");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);		
    }

    private void buildDelete() {
        IDNumberTxt.setText(""+bm.getID());
        IDNumberTxt.setEditable(false);
        MakerTxt.setText(bm.getMaker());
        MakerTxt.setEditable(false);
        NumberTxt.setText(bm.getNumber());
        NumberTxt.setEditable(false);
        DescriptionTxt.setText(bm.getDescription());
        DescriptionTxt.setEditable(false);
        DiameterTxt.setText(ff.floatConvert(bm.getDiameter(),0,3));
        DiameterTxt.setEditable(false);
        WeightTxt.setText(ff.floatConvert(bm.getWeight(),0,3));
        WeightTxt.setEditable(false);
        TopPunchTxt.setText(bm.getTopPunch());
        TopPunchTxt.setEditable(false);
        if(bm.isGasCheck()) {
            GasCheckYesBtn= new JRadioButton("Yes", true);
            GasCheckNoBtn= new JRadioButton("No", false);	
        } else {
            GasCheckYesBtn= new JRadioButton("yes", false);
            GasCheckNoBtn= new JRadioButton("no", true);				
        }
        GasCheckYesBtn.setEnabled(false);
        GasCheckNoBtn.setEnabled(false);
        centerBtn.setText("DELETE");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);		
    }

    private void buildView() {
        IDNumberTxt.setText(""+bm.getID());
        IDNumberTxt.setEditable(false);
        MakerTxt.setText(bm.getMaker());
        MakerTxt.setEditable(false);
        NumberTxt.setText(bm.getNumber());
        NumberTxt.setEditable(false);
        DescriptionTxt.setText(bm.getDescription());
        DescriptionTxt.setEditable(false);
        DiameterTxt.setText(ff.floatConvert(bm.getDiameter(),0,3));
        DiameterTxt.setEditable(false);
        WeightTxt.setText(ff.floatConvert(bm.getWeight(),0,3));
        WeightTxt.setEditable(false);
        TopPunchTxt.setText(bm.getTopPunch());
        TopPunchTxt.setEditable(false);
        if(bm.isGasCheck()) {
            GasCheckYesBtn= new JRadioButton("Yes", true);
            GasCheckNoBtn= new JRadioButton("No", false);	
        } else {
            GasCheckYesBtn= new JRadioButton("Yes", false);
            GasCheckNoBtn= new JRadioButton("No", true);				
        }
        GasCheckYesBtn.setEnabled(false);
        GasCheckNoBtn.setEnabled(false);
        centerBtn.setText("OK");
        centerBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actEvt) {
        if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Mold ID Number "+bm.getID()) == 1) {
                        if(createMold()) {
                            BulletMoldEvent ev = new BulletMoldEvent(this, bm, BulletMoldEvent.INSERT_LOTNUMBER);

                            
                            if(bulletMoldListener != null)
                                bulletMoldListener.BulletMoldEventOccurred(ev);
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Mold ID Number "+bm.getID()) == 1) {
                        if(createMold()) {
                            BulletMoldEvent ev = new BulletMoldEvent(this, bm, BulletMoldEvent.UPDATE_LOTNUMBER);

                            
                            if(bulletMoldListener != null)
                                bulletMoldListener.BulletMoldEventOccurred(ev);
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Mold ID Number "+bm.getID()) == 1) {
                        BulletMoldEvent ev = new BulletMoldEvent(this, bm, BulletMoldEvent.DELETE_LOTNUMBER);


                        if(bulletMoldListener != null)
                            bulletMoldListener.BulletMoldEventOccurred(ev);
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
        } else if(actEvt.getSource() == GasCheckYesBtn) {
            GasCheckYesBtn.setSelected(true);
            GasCheckNoBtn.setSelected(false);    			
        } else if(actEvt.getSource() == GasCheckNoBtn) { 
            GasCheckYesBtn.setSelected(false);
            GasCheckNoBtn.setSelected(true);  
        }
    }	

    private boolean createMold(){
        if((winType == UPDATE) || (winType == DELETE)) {
            try {
                bm.setID(Integer.parseInt(IDNumberTxt.getText()));
            } catch (Exception e) {
                this.AlertDialog("Cannot get ID Number for the mold.");	    		
            }
        }
        bm.setMaker(MakerTxt.getText());
        bm.setNumber(NumberTxt.getText());
        bm.setDescription(DescriptionTxt.getText());
        bm.setDiameter(DiameterTxt.getText());
        bm.setWeight(WeightTxt.getText());
        bm.setTopPunch(TopPunchTxt.getText());
        if(GasCheckYesBtn.isSelected()){
            bm.setGasCheck(true);
        } else {
            bm.setGasCheck(false);
        }
        if(bm.isValid()) {
            return true;
        } else {
            this.AlertDialog("Something is wrong with this Bullet Mold");
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
    
    public void setBulletMoldListener(BulletMoldListener listener) {
        this.bulletMoldListener = listener;
    }
}

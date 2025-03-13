/****************************************************************************************************************
 * ReloadingDiesGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the ReloadingDiesGUI program. It displays or collects reloading dies data for the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package ReloadingDiesEvent;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.io.File;

public class ReloadingDiesGUI extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 400;
    private final int YSIZE = 500;

    private ReloadingDies rd;
    private int winType = ReloadingDiesGUI.NOT_SET;

    private JLabel IDNumberLbl = new JLabel("ID Number:", SwingConstants.RIGHT);
    private JLabel MakerLbl = new JLabel("Maker:", SwingConstants.RIGHT);
    private JLabel CaliberLbl = new JLabel("Caliber:", SwingConstants.RIGHT);
    private JLabel TypeLbl = new JLabel("Type:", SwingConstants.RIGHT);
    private JLabel FLCSDLbl = new JLabel("Full Length Carbide Sizer:", SwingConstants.RIGHT);
    private JLabel ExpanderLbl = new JLabel("Expander Die:", SwingConstants.RIGHT);
    private JLabel SeaterLbl = new JLabel("Seater Die:", SwingConstants.RIGHT);
    private JLabel RollLbl = new JLabel("Roll Crimp:", SwingConstants.RIGHT);
    private JLabel FactoryLbl = new JLabel("Factory Crimp Die:", SwingConstants.RIGHT);
    private JLabel FLSSDLbl = new JLabel("Full Length Steel Sizer:", SwingConstants.RIGHT);
    private JLabel TrimLbl = new JLabel("Trim Die:", SwingConstants.RIGHT);

    private JTextField IDNumberTxt = new JTextField();
    private JTextField MakerTxt = new JTextField();
    private JTextField CaliberTxt = new JTextField();
    String[] types = new String[]{"N/A","Rifle","Pistol"};
    private JComboBox <String> TypeComboBox = new JComboBox<String>(types);

    private JCheckBox FLCSDBox= new JCheckBox("",false);
    private JCheckBox ExpanderBox= new JCheckBox("", false);	
    private JCheckBox SeaterBox= new JCheckBox("",false);
    private JCheckBox RollBox= new JCheckBox("", false);	
    private JCheckBox FactoryBox= new JCheckBox("",false);
    private JCheckBox FLSSDBox= new JCheckBox("", false);	
    private JCheckBox TrimBox= new JCheckBox("", false);	

    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();
    private ReloadingDiesListener reloadingDiesListener;

    public ReloadingDiesGUI() {
            this.setTitle("Reloading Dies");
            this.setResizable(false);
            this.setModalityType(DEFAULT_MODALITY_TYPE);
            this.setSize(XSIZE, YSIZE);
            this.makeIcon();		
    }

    public ReloadingDiesGUI(ReloadingDies data) {
            this.setTitle("Reloading Dies");
            this.setResizable(false);
            this.setModalityType(DEFAULT_MODALITY_TYPE);
            this.setSize(XSIZE, YSIZE);
            this.makeIcon();
            rd = data;
    }

    //Creates the little icon shown on the JFrame.
    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "ReloadingDies.jpg";
            File myObj = new File(ammoIconPath);
	    BufferedImage image = ImageIO.read(myObj);
	    this.setIconImage(image);
	} catch (IOException e) {
	    System.err.println("Error creating icon: "+e);
	}
    }   

    public ReloadingDies getReloadingDie() {
        return rd;
    }

    public void setReloadingDie(ReloadingDies rd) {
        this.rd = rd;
    }

    public int getWinType() {
        return winType;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }

    public void setCloseType(int cl) {
        this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
        return this.getDefaultCloseOperation();
    }

    public void buildWindow(int wt) {
        winType = wt;		

        switch(winType) {
            case INSERT: //Build the insert window.
                buildInsert();
                break;
            case UPDATE: //Build the update window.
                if(rd.getID() <= 0) {
                    AlertDialog("Reloading ID has not been set for ReloadingDiesGUI.");
                    return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(rd.getID() <= 0) {
                        AlertDialog("Reloading ID  has not been set for ReloadingDiesGUI.");
                        return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(rd.getID()<= 0) {
                    AlertDialog("Reloading ID  has not been set for ReloadingDiesGUI.");
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
        IDNumberTxt.setEditable(false);
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
        add(TypeLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
       	add(TypeComboBox, c); 
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(FLCSDLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(FLCSDBox, c);       
        
       c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(ExpanderLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(ExpanderBox, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(SeaterLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        add(SeaterBox, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(RollLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        add(RollBox, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        add(FactoryLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        add(FactoryBox, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        add(FLSSDLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 9;
        c.gridwidth = 1;
        add(FLSSDBox, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 1;
        add(TrimLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 10;
        c.gridwidth = 1;
        add(TrimBox, c);   

       c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 3;
        add(new JLabel("  "), c);   
             
        //Page Buttons
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 12; 
        c.gridwidth = 1;
        add(centerBtn, c);

        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 12;
        c.gridwidth = 1;
        if(winType != ReloadingDiesGUI.VIEW) {
            add(rightBtn, c);   
        } else {
            add(new JLabel("        "),c);
        }
    }
	
    private void buildView() {
        IDNumberTxt.setText(""+rd.getID());
        MakerTxt.setEditable(false);
        MakerTxt.setText(rd.getMaker());
        CaliberTxt.setEditable(false);
        CaliberTxt.setText(rd.getCaliber());
        TypeComboBox.setSelectedItem(rd.getType());
        TypeComboBox.setEnabled(false);
        FLCSDBox.setSelected(rd.getFLCSD());
        FLCSDBox.setEnabled(false);
        ExpanderBox.setSelected(rd.getExpander());
        ExpanderBox.setEnabled(false);
        SeaterBox.setSelected(rd.getSeater());
        SeaterBox.setEnabled(false);
        RollBox.setSelected(rd.getRoll());
        RollBox.setEnabled(false);
        FactoryBox.setSelected(rd.getFactory());
        FactoryBox.setEnabled(false);
        FLSSDBox.setSelected(rd.getFLSSD());	
        FLSSDBox.setEnabled(false);
        TrimBox.setSelected(rd.getTrim());	
        TrimBox.setEnabled(false);
        centerBtn.setText("OK");
        centerBtn.addActionListener(this);
    }
    private void buildDelete() {
        IDNumberTxt.setText(""+rd.getID());
        MakerTxt.setEditable(false);
        MakerTxt.setText(rd.getMaker());
        CaliberTxt.setEditable(false);
        CaliberTxt.setText(rd.getCaliber());
        TypeComboBox.setSelectedItem(rd.getType());
        TypeComboBox.setEnabled(false);
        FLCSDBox.setSelected(rd.getFLCSD());
        FLCSDBox.setEnabled(false);
        ExpanderBox.setSelected(rd.getExpander());
        ExpanderBox.setEnabled(false);
        SeaterBox.setSelected(rd.getSeater());
        SeaterBox.setEnabled(false);
        RollBox.setSelected(rd.getRoll());
        RollBox.setEnabled(false);
        FactoryBox.setSelected(rd.getFactory());
        FactoryBox.setEnabled(false);
        FLSSDBox.setSelected(rd.getFLSSD());	
        FLSSDBox.setEnabled(false);
        TrimBox.setSelected(rd.getTrim());	
        TrimBox.setEnabled(false);
        centerBtn.setText("DELETE");
        centerBtn.addActionListener(this);
        rightBtn.setText("CANCEL");
        rightBtn.addActionListener(this);
    }
	
    private void buildUpdate() {
        IDNumberTxt.setText(""+rd.getID());
        MakerTxt.setEditable(false);
        MakerTxt.setText(rd.getMaker());
        CaliberTxt.setEditable(true);
        CaliberTxt.setText(rd.getCaliber());
        TypeComboBox.setSelectedItem(rd.getType());
        TypeComboBox.setEnabled(true);
        FLCSDBox.setSelected(rd.getFLCSD());
        FLCSDBox.setEnabled(true);
        ExpanderBox.setSelected(rd.getExpander());
        ExpanderBox.setEnabled(true);
        SeaterBox.setSelected(rd.getSeater());
        SeaterBox.setEnabled(true);
        RollBox.setSelected(rd.getRoll());
        RollBox.setEnabled(true);
        FactoryBox.setSelected(rd.getFactory());
        FactoryBox.setEnabled(true);
        FLSSDBox.setSelected(rd.getFLSSD());	
        FLSSDBox.setEnabled(true);
        TrimBox.setSelected(rd.getTrim());	
        TrimBox.setEnabled(true);
        centerBtn.setText("UPDATE");
        centerBtn.addActionListener(this);
        rightBtn.setText("CANCEL");
        rightBtn.addActionListener(this);
    }

    private void buildInsert() {
        IDNumberTxt.setText("Auto Increment");
        centerBtn.setText("INSERT");
        centerBtn.addActionListener(this);
        rightBtn.setText("CANCEL");
        rightBtn.addActionListener(this);		
    }
	
    @Override
    public void actionPerformed(ActionEvent actEvt) {
        if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Reloading Die") == 1) {
                        ReloadingDiesEvent ev = new ReloadingDiesEvent(this, rd, ReloadingDiesEvent.INSERT_LOTNUMBER);

                        if(reloadingDiesListener != null)
                            reloadingDiesListener.ReloadingDiesEventOccurred(ev);
                        exit();
                    } else {
                        AlertDialog("Cannot insert the Reloading Die due to error.");
                    }						
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Reloading Die ID Number "+rd.getID()) == 1) {
                        if(createReloadingDies()) {
                            ReloadingDiesEvent ev = new ReloadingDiesEvent(this, rd, ReloadingDiesEvent.UPDATE_LOTNUMBER);

                            if(reloadingDiesListener != null)
                                reloadingDiesListener.ReloadingDiesEventOccurred(ev);
                            exit();
                        } else {
                            AlertDialog("Cannot update the Reloading Die due to error.?");
                        }
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Reloading Die ID Number "+rd.getID()) == 1) {
                        ReloadingDiesEvent ev = new ReloadingDiesEvent(this, rd, ReloadingDiesEvent.DELETE_LOTNUMBER);

                        if(reloadingDiesListener != null)
                            reloadingDiesListener.ReloadingDiesEventOccurred(ev);
                        exit();
                        exit();
                    } else {
                        AlertDialog("Cannot delete the Reloading Die due to error.");
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
        }
    }

    private boolean createReloadingDies() {
        rd.setMaker(MakerTxt.getText());
        rd.setCaliber(CaliberTxt.getText());
        rd.setType((String)TypeComboBox.getSelectedItem());
        rd.setFLCSD(FLCSDBox.isSelected());
        rd.setExpander(ExpanderBox.isSelected());
        rd.setSeater(SeaterBox.isSelected());
        rd.setRoll(RollBox.isSelected());
        rd.setFactory(FactoryBox.isSelected());
        rd.setFLSSD(FLSSDBox.isSelected());
        rd.setTrim(TrimBox.isSelected());

        return rd.isValidReloadingDie();
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
    
    public void setReloadingDiesListener(ReloadingDiesListener listener) {
        this.reloadingDiesListener = listener;
    } 	
}

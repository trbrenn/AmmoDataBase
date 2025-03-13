/****************************************************************************************************************
 * SettingsGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the settings GUI program. It allows the user to modify any settings for the main program.										*
 * 																												*
 ***************************************************************************************************************/
package MainProgram;

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
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import BaseClasses.SettingsXML;
import java.io.File;
import javax.swing.JRadioButton;

/**
 * @author trbrenn
 *
 */public class SettingsGUI extends JDialog implements ActionListener {
	 
    private SettingsXML sxml = new SettingsXML();
    private static final long serialVersionUID = 1L;
    //Default windows size.
    private final int XSIZE = 450;
    private final int YSIZE = 320;
	 
    private JButton saveBtn = new JButton("Save");
    private JButton cancelBtn = new JButton("Cancel");

    private JLabel DBurlLabel = new JLabel("Database URL:", SwingConstants.RIGHT);
    private JLabel UserNameLabel = new JLabel("User Name:", SwingConstants.RIGHT);
    private JLabel PassWordLabel = new JLabel("Password:", SwingConstants.RIGHT);
    private JLabel CLLabel = new JLabel("Database Driver:", SwingConstants.RIGHT);
    private JLabel GunListPicURLLabel = new JLabel("Gun List URL:", SwingConstants.RIGHT);
    private JLabel BoundBookPicURLLabel = new JLabel("Bound Book URL:", SwingConstants.RIGHT);
    private JLabel isEmptyLabel = new JLabel("Ignore Empty:", SwingConstants.RIGHT);
	
    private JTextArea 	DBurlText = new JTextArea();
    private JTextArea 	UserNameText = new JTextArea();
    private JPasswordField PassWordText = new JPasswordField();
    private JTextArea 	CLText = new JTextArea();
    private JTextArea 	GunListPicURLText = new JTextArea();
    private JTextArea 	BoundBookPicURLText = new JTextArea();
    private JRadioButton EmptyYesBtn= new JRadioButton("Yes", false);
    private JRadioButton EmptyNoBtn= new JRadioButton("No", false);	

    public SettingsGUI() {
	this.setTitle("Settings");
	this.setSize(XSIZE, YSIZE);
	this.setResizable(false);
	this.makeIcon();
    }
	
    //Creates the little icon shown on the JFrame.
    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "Settings.jpg";
            File myObj = new File(ammoIconPath);
	    BufferedImage image = ImageIO.read(myObj);
	    this.setIconImage(image);
	} catch (IOException e) {
	    System.err.println("Error creating icon: "+e);
	}
    }   
	
    public void buildWindow() {
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
        add(DBurlLabel, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        add(DBurlText, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(UserNameLabel, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(UserNameText, c);
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(PassWordLabel, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(PassWordText, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(CLLabel , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
       	add(CLText, c); 
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(GunListPicURLLabel, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(GunListPicURLText, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(BoundBookPicURLLabel, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(BoundBookPicURLText, c);   
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(isEmptyLabel, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        add(EmptyYesBtn, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        add(EmptyNoBtn, c);   
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 1;
        add(new JLabel(" "), c);

        //Page Buttons
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 13; 
        c.gridwidth = 1;
        add(saveBtn, c);
        saveBtn.addActionListener(this);
        
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 13;
        c.gridwidth = 1;
	    add(cancelBtn, c); 
	    cancelBtn.addActionListener(this);
	    
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 13;
        c.gridwidth = 1;
	add(cancelBtn, c); 
	cancelBtn.addActionListener(this);
	try {
	    sxml.readData();
	} catch(Exception e) {
	    AlertDialog("Cannot open file.");
	}
	    
	DBurlText.setText(sxml.getDBurl());
	UserNameText.setText(sxml.getUserName());
	PassWordText.setText(sxml.getPassWord());
	CLText.setText(sxml.getCL());
	GunListPicURLText.setText(sxml.getGunListPicURL());
	BoundBookPicURLText.setText(sxml.getBoundBookPicURL());
        if(sxml.getIgnoreEmpty() == null || sxml.getIgnoreEmpty().equalsIgnoreCase("true")){
            EmptyYesBtn.setSelected(false);
            EmptyNoBtn.setSelected(true);
        } else {
            EmptyYesBtn.setSelected(true);
            EmptyNoBtn.setSelected(false);
        }
        EmptyYesBtn.addActionListener(this);
        EmptyNoBtn.addActionListener(this);

    }
	
    @Override
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == saveBtn) {
            sxml.setDBurl(DBurlText.getText());
            sxml.setUserName(UserNameText.getText());
            sxml.setPassWord(PassWordText.getPassword());
            sxml.setCL(CLText.getText());
            sxml.setGunListPicURL(GunListPicURLText.getText());
            sxml.setBoundBookPicURL(BoundBookPicURLText.getText());
            if(EmptyYesBtn.isSelected())
                sxml.setIgnoreEmpty("True");
            else
                sxml.setIgnoreEmpty("False");
            
            if(sxml.isValidSetting()) {
    		try {
    		    sxml.writeSettings();
    		} catch(Exception e) {
    		    AlertDialog("Cannot write file.");
    		}    			 
            } else {
    		AlertDialog("The data is not valid");
            }
            this.exit();
    	}
    	if(actEvt.getSource() == cancelBtn) {
    		this.exit();
    	} else if(actEvt.getSource() == EmptyYesBtn) {
            EmptyYesBtn.setSelected(true);
            EmptyNoBtn.setSelected(false);    			
    	} else if(actEvt.getSource() == EmptyNoBtn) { 
            EmptyYesBtn.setSelected(false);
            EmptyNoBtn.setSelected(true);  
    	}
    }
	
    //Alerts the user if error happen.
    private void AlertDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(this, t, "ALERT!", JOptionPane.ERROR_MESSAGE);
    }
    
    //Sets the close action to be taken by the dialog.
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
    
    public void setCloseType(int cl) {
	this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
	return this.getDefaultCloseOperation();
    }
	
    public static void main(String[] args) {
	SettingsGUI SGUI = new SettingsGUI();
	SGUI.setCloseType(DISPOSE_ON_CLOSE);
	SGUI.buildWindow();
	SGUI.setVisible(true);
    }  	
}

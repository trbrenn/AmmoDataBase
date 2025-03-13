/**
 * 
 */
package FirearmsEvent;

import BaseClasses.SettingsXML;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.io.File;


/**
 * @author trbrenn
 *
 */
public class FirearmsGUI  extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private int XSIZE = 450;
    private int YSIZE = 400;

    private Firearm fs = new Firearm();

    private JLabel PictureLbl = new JLabel("Picture:", SwingConstants.RIGHT);
    private JLabel ManufacturerLbl = new JLabel("Manufacturer:", SwingConstants.RIGHT);
    private JLabel ModelNameLbl = new JLabel("Model Name:", SwingConstants.RIGHT);
    private JLabel SerialNumberLbl = new JLabel("Serial Number:", SwingConstants.RIGHT);
    private JLabel TypeLbl = new JLabel("Type:", SwingConstants.RIGHT);
    private JLabel CaliberLbl = new JLabel("Caliber:", SwingConstants.RIGHT);
    private JLabel DatabaseNameLbl = new JLabel("Database Name", SwingConstants.RIGHT);
    private JLabel DateRecievedLbl = new JLabel("Date Recieved", SwingConstants.RIGHT);
    private JLabel PriceLbl = new JLabel("Price", SwingConstants.RIGHT);
    private JLabel RecievedFromLbl = new JLabel("Recieved From", SwingConstants.RIGHT);
    private JLabel DateDisposedLbl = new JLabel("Date Disposed", SwingConstants.RIGHT);
    private JLabel TransferedToLbl = new JLabel("Transfered To", SwingConstants.RIGHT);

    private JTextArea PictureTxt = new JTextArea();
    private JTextArea ManufacturerTxt = new JTextArea();
    private JTextArea ModelNameTxt = new JTextArea();
    private JTextArea SerialNumberTxt = new JTextArea();
    private JTextArea TypeTxt = new JTextArea();
    private JTextArea CaliberTxt = new JTextArea();
    private JTextArea DatabaseNameTxt = new JTextArea();
    private JTextArea DateRecievedTxt = new JTextArea();
    private JTextArea PriceTxt = new JTextArea();
    private JTextArea RecievedFromTxt = new JTextArea();
    private JTextArea DateDisposedTxt = new JTextArea();
    private JTextArea TransferedToTxt = new JTextArea();

    private String[] dn = new String[]{"GunsInList","BoundBook"};
    private JComboBox<String> databaseNameCB = new JComboBox<String>(dn);

    private int winType = this.NOT_SET;
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();

    private SettingsXML sxml = new SettingsXML();
    private FirearmListener FirearmListener;
    
    //default constructor.
    public FirearmsGUI() {
        this.setTitle("Firearms");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //Create a new instant and set the bullets data to be displayed.
    public FirearmsGUI(Firearm ics) {
        this.setTitle("Firearms");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        fs = ics;
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //Create a new instant and set the bullets data from xml data.
    public FirearmsGUI(String xml) {
        this.setTitle("Firearms");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        fs = new Firearm(xml);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //Create a new window and set the bullets data and window type.
    public FirearmsGUI(Firearm ics, int wt) {
        this.setTitle("Firearms");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        fs = ics;
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //create a new window and set the bullets data from a xml string and set the wintype.
    public FirearmsGUI(String xml, int wt) {
        this.setTitle("Firearms");
        this.setSize(XSIZE, YSIZE);
        fs = new Firearm(xml);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        this.makeIcon();
    }	

    //Creates the little icon shown on the JFrame.
    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "Firearm.jpg";
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
        try {
            sxml.readData();
        } catch (Exception e) {
            AlertDialog("Cannot read AmmoDatabaseSettings.xml data");			
        }
        switch(winType) {
            case INSERT: //Build the insert window.
                buildInsert();
                break;
            case UPDATE: //Build the update window.
                if(fs.getSerialNumber().isBlank()) {
                    AlertDialog("Firearm has not been set for FirearmGui.");
                    return;
                }
                buildUpdate();
                break;
            case DELETE: //Build the Delete window.
                if(fs.getSerialNumber().isBlank()) {
                    AlertDialog("Firearm has not been set for FirearmGui.");
                    return;
                }
                buildDelete();
                break;
            case VIEW: //Build the view window.
                if(fs.getSerialNumber().isBlank()) {
                    AlertDialog("Firearm has not been set for FirearmsGui.");
                    return;
                }
                buildView();
                break;
            default:
                AlertDialog("The window type has not been set for FirearmsGui.");
                return;	    		
        }

        //Labels and text fields.
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        
        if((winType == DELETE) || (winType == VIEW)) {
            c.anchor = GridBagConstraints.PAGE_START ;
            c.insets = new Insets(5,5,5,5);
            c.weightx = 0.1;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 5;
            PictureLbl.setText("");
            add(PictureLbl, c);
        } else {
            c.anchor = GridBagConstraints.PAGE_START ;
            c.insets = new Insets(5,2,5,5);
            c.weightx = 0.1;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            add(PictureLbl, c);
            c.anchor = GridBagConstraints.PAGE_START;
            c.insets = new Insets(5,2,5,15);
            c.weightx = 0.6;
            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 2;
            add(PictureTxt, c);	    	
        }
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(ManufacturerLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        add(ManufacturerTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(ModelNameLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        add(ModelNameTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(SerialNumberLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
       	add(SerialNumberTxt, c); 
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(TypeLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        add(TypeTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(CaliberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        add(CaliberTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(DatabaseNameLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        if(winType == INSERT)
        	add(databaseNameCB,c);
        else
        	add(DatabaseNameTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(DateRecievedLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 2;
        add(DateRecievedTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        add(PriceLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 2;
        add(PriceTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        add(RecievedFromLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 9;
        c.gridwidth = 2;
        add(RecievedFromTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 1;
        add(DateDisposedLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 10;
        c.gridwidth = 2;
        add(DateDisposedTxt, c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 1;
        add(TransferedToLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 11;
        c.gridwidth = 2;
        add(TransferedToTxt, c);       
        
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
        if(winType != FirearmsGUI.VIEW) {
	        add(rightBtn, c);   
        } else {
        	add(new JLabel("        "),c);
        }
    }
	
    //builds the display items for a insert window. Everything is blank except the Type list.
    private void buildInsert() {
        PictureTxt.setText("");
        ManufacturerTxt.setText("");
        ModelNameTxt.setText("");
        SerialNumberTxt.setText("");
        TypeTxt.setText("");
        CaliberTxt.setText("");
        databaseNameCB.setEditable(false);
        DateRecievedTxt.setText("");
        PriceTxt.setText("");
        RecievedFromTxt.setText("");
        DateDisposedTxt.setText("");
        TransferedToTxt.setText("");
        centerBtn.setText("Insert");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //builds the display items for the Update window. Everything is set by the case data that is loaded into the local bullets class.
    private void buildUpdate() {
        PictureTxt.setText(fs.getPicture());
        ManufacturerTxt.setText(fs.getManufacturer());
        CaliberTxt.setText(fs.getCaliber());
        SerialNumberTxt.setText(fs.getSerialNumber());
        SerialNumberTxt.setEditable(false);
        TypeTxt.setText(fs.getType());
        ModelNameTxt.setText(fs.getModelName());
        DatabaseNameTxt.setText(fs.getDatabaseName());
        DatabaseNameTxt.setEditable(false);
        DateRecievedTxt.setText(fs.getDateRecieved());
        PriceTxt.setText(fs.getPrice());
        RecievedFromTxt.setText(fs.getRecievedFrom());
        DateDisposedTxt.setText(fs.getDateDisposed());
        TransferedToTxt.setText(fs.getTransferedTo());
        centerBtn.setText("Update");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }

    //Builds the display items for the Delete window. Everything is set by the case data that is loaded into the local bullets class.
    private void buildDelete() {
        String path = new String();
        //PictureTxt.setText(fs.getPicture());
        if(fs.getDatabaseName().equalsIgnoreCase("BoundBook"))
            path = sxml.getBoundBookPicURL()+fs.getPicture();
        else
            path = sxml.getGunListPicURL()+fs.getPicture();

        BufferedImage img;
        try {
            img = ImageIO.read(new URL(path));//it must be an image file, otherwise you'll get an exception
            Image image = img.getScaledInstance(450, -1, Image.SCALE_DEFAULT);
            img.getGraphics().drawImage(image, 0, 0, null);
            int width = image.getWidth(this);
            int	height = image.getHeight(this);
            this.setSize(width, YSIZE+height);
            PictureLbl.setIcon(new ImageIcon(image));
        } catch(Exception e) {
            this.AlertDialog("Error in BuildDelete() "+e);
        }

        ManufacturerTxt.setText(fs.getManufacturer());
        CaliberTxt.setText(fs.getCaliber());
        SerialNumberTxt.setText(fs.getSerialNumber());
        TypeTxt.setText(fs.getType());
        ModelNameTxt.setText(fs.getModelName());
        DatabaseNameTxt.setText(fs.getDatabaseName());
        DateRecievedTxt.setText(fs.getDateRecieved());
        PriceTxt.setText(fs.getPrice());
        RecievedFromTxt.setText(fs.getRecievedFrom());
        DateDisposedTxt.setText(fs.getDateDisposed());
        TransferedToTxt.setText(fs.getTransferedTo());
        ManufacturerTxt.setEditable(false);
        CaliberTxt.setEditable(false);
        SerialNumberTxt.setEditable(false);
        TypeTxt.setEditable(false);
        ModelNameTxt.setEditable(false);
        DatabaseNameTxt.setEditable(false);
        DateRecievedTxt.setEditable(false);
        PriceTxt.setEditable(false);
        RecievedFromTxt.setEditable(false);
        DateDisposedTxt.setEditable(false);
        TransferedToTxt.setEditable(false);

        centerBtn.setText("Delete");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
    //Builds the display items for the View window. Everything is set by the case data that is loaded into the local bullets class.
    private void buildView() {
        String path;
        //PictureTxt.setText(fs.getPicture());
        if(fs.getDatabaseName().equalsIgnoreCase("BoundBook"))
            path = sxml.getBoundBookPicURL()+fs.getPicture();
        else
            path = sxml.getGunListPicURL()+fs.getPicture();

        BufferedImage img;
        try {
            img = ImageIO.read(new URL(path));//it must be an image file, otherwise you'll get an exception
            Image image = img.getScaledInstance(450, -1, Image.SCALE_DEFAULT);
            img.getGraphics().drawImage(image, 0, 0, null);
            int width = image.getWidth(this);
            int	height = image.getHeight(this);
            this.setSize(width, YSIZE+height);
            PictureLbl.setIcon(new ImageIcon(image));
        } catch(Exception e) {
            this.AlertDialog("Error in BuildDelete() "+e);
        }

        ManufacturerTxt.setText(fs.getManufacturer());
        CaliberTxt.setText(fs.getCaliber());
        SerialNumberTxt.setText(fs.getSerialNumber());
        TypeTxt.setText(fs.getType());
        ModelNameTxt.setText(fs.getModelName());
        DatabaseNameTxt.setText(fs.getDatabaseName());
        DateRecievedTxt.setText(fs.getDateRecieved());
        if(fs.getPrice().isBlank()) {
            PriceTxt.setText(fs.getPrice());
        } else {
            PriceTxt.setText("$"+fs.getPrice());			
        }
        RecievedFromTxt.setText(fs.getRecievedFrom());
        DateDisposedTxt.setText(fs.getDateDisposed());
        TransferedToTxt.setText(fs.getTransferedTo());
        ManufacturerTxt.setEditable(false);
        CaliberTxt.setEditable(false);
        SerialNumberTxt.setEditable(false);
        TypeTxt.setEditable(false);
        ModelNameTxt.setEditable(false);
        DatabaseNameTxt.setEditable(false);
        DateRecievedTxt.setEditable(false);
        PriceTxt.setEditable(false);
        RecievedFromTxt.setEditable(false);
        DateDisposedTxt.setEditable(false);
        TransferedToTxt.setEditable(false);

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

    public Firearm getFirearm() {
        return fs;
    }

    public void setFirearm(Firearm f) {
        fs = f;
    }
	
	//button listener to handle input from the buttons on the window.
    @Override
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Firearm Lot Number "+fs.getSerialNumber()) == 1) {
                        if(createFirearm()) {
                            FirearmEvent ev = new FirearmEvent(this, fs, FirearmEvent.INSERT_LOTNUMBER);
                
                            if(FirearmListener != null)
                                FirearmListener.FirearmEventOccurred(ev);
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Firarm serial number "+fs.getSerialNumber()) == 1) {
                        if(createFirearm()) {
                            FirearmEvent ev = new FirearmEvent(this, fs, FirearmEvent.UPDATE_LOTNUMBER);
                
                            if(FirearmListener != null)
                                FirearmListener.FirearmEventOccurred(ev);
                            exit();
                        } else {
                            AlertDialog("Error?");
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Firearm Serial Number "+fs.getSerialNumber()) == 1) {
                            FirearmEvent ev = new FirearmEvent(this, fs, FirearmEvent.DELETE_LOTNUMBER);
                
                            if(FirearmListener != null)
                                FirearmListener.FirearmEventOccurred(ev);
                        exit();
                    } else {
                        AlertDialog("Error?");
                    }						
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    System.err.println("The window type has not been set for FirearmsGUI.");
                    exit();
                    break;	    		
            }
    	} else if(actEvt.getSource() == rightBtn) {
            exit();
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
    
    private boolean createFirearm() {
        try {
            fs.setCaliber(CaliberTxt.getText());
            //fs.setDatabaseName(DatabaseNameTxt.getText());
            fs.setDatabaseName(databaseNameCB.getSelectedItem().toString());
            fs.setPicture(PictureTxt.getText());
            fs.setManufacturer(ManufacturerTxt.getText());
            fs.setSerialNumber(SerialNumberTxt.getText());
            fs.setType(TypeTxt.getText());
            fs.setModelName(ModelNameTxt.getText());
            fs.setDateRecieved(fs.getDateRecieved());
            fs.setPrice(fs.getPrice());
            fs.setRecievedFrom(fs.getRecievedFrom());
            fs.setDateDisposed(fs.getDateDisposed());
            fs.setTransferedTo(fs.getTransferedTo());
        } catch(Exception e) {
            this.AlertDialog("Entry Error: "+e);
            return false;
        }
        if(fs.isValidFirearm()) {
            return true;
        } else {
            this.AlertDialog("Something is wrong with this Firearm");
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
    public void setFirearmListener(FirearmListener listener) {
        this.FirearmListener = listener;
    }
}

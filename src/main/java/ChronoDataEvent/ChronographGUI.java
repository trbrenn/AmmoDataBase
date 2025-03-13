/**
 * 
 */
package ChronoDataEvent;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;

import ReloadEvent.ReloadList;
import BulletsEvent.Bullets;
import BaseClasses.CalendarTest;
import PowderEvent.Powder;
import FirearmsEvent.Firearm;
import BaseClasses.FormatFloat;
import BulletsEvent.BulletsEvent;
import BulletsEvent.BulletsListener;
import FirearmsEvent.FirearmEvent;
import FirearmsEvent.FirearmListener;
import PowderEvent.PowderListener;
import ReloadEvent.ReloadListEvent;
import ReloadEvent.ReloadListListener;
import java.io.File;

/**
 * @author trbrenn
 *
 */
public class ChronographGUI extends JDialog implements ActionListener  {
	
    private static final long serialVersionUID = -9053818377407470451L;
    //Static variables for setting up the window to display.
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 850;
    private final int YSIZE = 1020;

    private ChronographData cd = new ChronographData();
    private Bullets bd = new Bullets();
    private Powder pd = new Powder();
    private ReloadList ad = new ReloadList();
    private Firearm fd = new Firearm();

    private List<String> ammoList;
    private List<String> firearmList;

    private JLabel titleLbl = new JLabel("Chronograph Test Data", SwingConstants.CENTER);
    private JLabel shotLbl = new JLabel("Shot", SwingConstants.CENTER);
    private JLabel velocityLbl = new JLabel("Velocity", SwingConstants.CENTER);
    private JLabel deviationLbl = new JLabel("Deviation From", SwingConstants.CENTER);
    private JLabel numberLbl = new JLabel("Number", SwingConstants.CENTER);
    private JLabel feetSecLbl = new JLabel("ft/s", SwingConstants.CENTER);
    private JLabel avgVelLbl = new JLabel("Average Velocity", SwingConstants.CENTER);
    private JLabel oneLbl = new JLabel("1", SwingConstants.CENTER);
    private JLabel twoLbl = new JLabel("2", SwingConstants.CENTER);
    private JLabel threeLbl = new JLabel("3", SwingConstants.CENTER);
    private JLabel fourLbl = new JLabel("4", SwingConstants.CENTER);
    private JLabel fiveLbl = new JLabel("5", SwingConstants.CENTER);
    private JLabel sixLbl = new JLabel("6", SwingConstants.CENTER);
    private JLabel sevenLbl = new JLabel("7", SwingConstants.CENTER);
    private JLabel eightLbl = new JLabel("8", SwingConstants.CENTER);
    private JLabel nineLbl = new JLabel("9", SwingConstants.CENTER);
    private JLabel tenLbl = new JLabel("10", SwingConstants.CENTER);
    private JLabel totalLbl = new JLabel("Total", SwingConstants.CENTER);
    private JLabel cartridgeDataLbl = new JLabel("Cartridge Data", SwingConstants.CENTER);
    private JLabel conditionsLbl = new JLabel("Conditions", SwingConstants.CENTER);
    private JLabel shooterLbl = new JLabel("Shooter", SwingConstants.RIGHT);
    private JLabel testNumLbl = new JLabel("Test Number: ", SwingConstants.RIGHT);
    private JLabel dateLbl = new JLabel("Date: ", SwingConstants.RIGHT);
    private JLabel cartridgeLbl = new JLabel("Cartridge: ", SwingConstants.RIGHT);
    private JLabel firearmLbl = new JLabel("Firearm: ", SwingConstants.RIGHT);
    private JLabel bulletLbl = new JLabel("Bullet Type: ", SwingConstants.RIGHT);
    private JLabel castLbl = new JLabel("Cast Alloy: ", SwingConstants.RIGHT);
    private JLabel weightLbl = new JLabel("Bullet Weight: ", SwingConstants.RIGHT);
    private JLabel bcLbl = new JLabel("B.C.: ", SwingConstants.RIGHT);
    private JLabel powderLbl = new JLabel("Powder Name: ", SwingConstants.RIGHT);
    private JLabel pWeightLbl = new JLabel("Powder Weight: ", SwingConstants.RIGHT);
    private JLabel locLbl = new JLabel("Length of Cartridge: ", SwingConstants.RIGHT);
    private JLabel crimpLbl = new JLabel("Crimp: ", SwingConstants.RIGHT);
    private JLabel otherLbl = new JLabel("Other: ", SwingConstants.RIGHT);
    private JLabel timeLbl = new JLabel("Time: ", SwingConstants.RIGHT);
    private JLabel tempLbl = new JLabel("Temp: ", SwingConstants.RIGHT);
    private JLabel locationLbl = new JLabel("Location: ", SwingConstants.RIGHT);
    private JLabel wdLbl = new JLabel("Wind Direction: ", SwingConstants.RIGHT);
    private JLabel wsLbl = new JLabel("Wind Speed: ", SwingConstants.RIGHT);
    private JLabel elevationLbl = new JLabel("Elevation: ", SwingConstants.RIGHT);
    private JLabel targetIDLbl = new JLabel("Tagert ID Number: ", SwingConstants.RIGHT);
    private JLabel dtcLbl = new JLabel("Distance to Chrony: ", SwingConstants.RIGHT);
    private JLabel evaluationLbl = new JLabel("Evaluation: ", SwingConstants.CENTER);
    private JLabel hvLbl = new JLabel("High Velocity: ", SwingConstants.RIGHT);
    private JLabel lvLbl = new JLabel("Low Velocity: ", SwingConstants.RIGHT);
    private JLabel avLbl = new JLabel("Average Velocity: ", SwingConstants.RIGHT);
    private JLabel esLbl = new JLabel("Extreme Spread: ", SwingConstants.RIGHT);
    private JLabel adLbl = new JLabel("Average Deviation: ", SwingConstants.RIGHT);
    private JLabel sdLbl = new JLabel("Standard Deviation: ", SwingConstants.RIGHT);
    private JLabel engeryLbl = new JLabel("Engery: ", SwingConstants.RIGHT);
    private JLabel shotsInStrLbl = new JLabel("Total Shots in String: ", SwingConstants.RIGHT);

    private JTextArea oneVelTxt = new JTextArea("");
    private JTextArea twoVelTxt = new JTextArea("");
    private JTextArea threeVelTxt = new JTextArea("");
    private JTextArea fourVelTxt = new JTextArea("");
    private JTextArea fiveVelTxt = new JTextArea("");
    private JTextArea sixVelTxt = new JTextArea("");
    private JTextArea sevenVelTxt = new JTextArea("");
    private JTextArea eightVelTxt = new JTextArea("");
    private JTextArea nineVelTxt = new JTextArea("");
    private JTextArea tenVelTxt = new JTextArea("");
    private JTextArea totalVelTxt = new JTextArea("");
    private JTextArea oneDevTxt = new JTextArea("");
    private JTextArea twoDevTxt = new JTextArea("");
    private JTextArea threeDevTxt = new JTextArea("");
    private JTextArea fourDevTxt = new JTextArea("");
    private JTextArea fiveDevTxt = new JTextArea("");
    private JTextArea sixDevTxt = new JTextArea("");
    private JTextArea sevenDevTxt = new JTextArea("");
    private JTextArea eightDevTxt = new JTextArea("");
    private JTextArea nineDevTxt = new JTextArea("");
    private JTextArea tenDevTxt = new JTextArea("");
    private JTextArea totalDevTxt = new JTextArea("");
    private JTextArea shotsInStrTxt = new JTextArea("");
    private JTextArea shooterTxt = new JTextArea("");
    private JTextArea testNumTxt = new JTextArea("");
    private JTextArea dateTxt = new JTextArea("");
    private JTextArea cartridgeTxt = new JTextArea("");
    private JTextArea bulletTxt = new JTextArea("");
    private JTextArea castTxt = new JTextArea("");
    private JTextArea weightTxt = new JTextArea("");
    private JTextArea bcTxt = new JTextArea("");
    private JTextArea powderTxt  = new JTextArea("");
    private JTextArea pWeightTxt = new JTextArea("");
    private JTextArea locTxt = new JTextArea("");
    private JTextArea crimpTxt = new JTextArea("");
    private JTextArea otherTxt = new JTextArea("");
    private JTextArea timeTxt = new JTextArea("");
    private JTextArea tempTxt = new JTextArea("");
    private JTextArea locationTxt = new JTextArea("");
    private JTextArea wdTxt = new JTextArea("");
    private JTextArea wsTxt = new JTextArea("");
    private JTextArea elevationTxt = new JTextArea("");
    private JTextArea targetIDTxt = new JTextArea("");
    private JTextArea dtcTxt = new JTextArea("");
    private JTextArea hvTxt = new JTextArea("");
    private JTextArea lvTxt = new JTextArea("");
    private JTextArea avTxt = new JTextArea("");
    private JTextArea esTxt = new JTextArea("");
    private JTextArea adTxt = new JTextArea("");
    private JTextArea sdTxt = new JTextArea("");
    private JTextArea engeryTxt = new JTextArea("");
    private JTextArea firearmTxt = new JTextArea("");

    private JComboBox<String> ammoListCB;
    private JComboBox<String> gunListCB;

    private int winType = NOT_SET;
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();

    private ChronoDataListener  chronoDataListener;
    private ReloadListListener  reloadListListener;
    private BulletsListener     bulletsListener;
    private PowderListener      powderListener;
    private FirearmListener     firearmListener;

    //default constructor.
    public ChronographGUI() {
        this.setTitle("Chrograph");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    public ChronographGUI(int t) {
        this.setTitle("Chrograph");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
        cd.setTestNumber(t);
    }

    //Create a new instant and set the cases data to be displayed.
    public ChronographGUI(ChronographData ics) {
        this.setTitle("Chrograph");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        cd = ics;
        this.makeIcon();
    }

    //Create a new instant and set the cases data from xml data.
    public ChronographGUI(String xml) {
        this.setTitle("Cases");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        cd = new ChronographData(xml);
        this.makeIcon();
    }

    //Create a new window and set the cases data and window type.
    public ChronographGUI(ChronographData ics, int wt) {
        this.setTitle("Chrograph");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        cd = ics;
        this.makeIcon();
    }

    //create a new window and set the cases data from a xml string and set the wintype.
    public ChronographGUI(String xml, int wt) {
        this.setTitle("Chrograph");
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        cd = new ChronographData(xml);
        this.setResizable(false);
        this.makeIcon();
    }	

    //Creates the little icon shown on the JFrame.
    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "chrono.jpg";
            File myObj = new File(ammoIconPath);
	    BufferedImage image = ImageIO.read(myObj);
	    this.setIconImage(image);
	} catch (IOException e) {
	        System.err.println("Error creating icon: "+e);
	}
    }   
	
    //Builds the window based on the winType variable.
    public void buildWindow() {

        switch(winType) {
            case INSERT: //Build the insert window.
                buildInsert();
                layout2();
                break;
            case UPDATE: //Build the update window.
                if(cd.getTestNumber()<0) {
                    AlertDialog("Data has not been set for ChronographGUI.UPDATE");
                    return;
                }
                buildUpdate();
                layout2();
                break;
            case DELETE: //Build the Delete window.
                if(cd.getTestNumber()<0) {
                    AlertDialog("Testnumber has not been set for ChronographGUI.DELETE");
                    return;
                }
                buildDelete();
                layout1();
                break;
            case VIEW: //Build the view window.
                if(cd.getTestNumber()<0) {
                    AlertDialog("Testnumber has not been set for ChronographGUI.VIEW");
                    return;
                }
                buildView();
                layout1();
                break;
            default:
                AlertDialog("The window type has not been set for ChronographGUI."); 
                break;
        }
    }
        
    private void layout1() {
        //Labels and text fields.
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        
        //first line
        c.anchor = GridBagConstraints.PAGE_START 	;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        titleLbl.setFont(new Font("Calibri", Font.BOLD, 40));
        add(titleLbl, c);
        
        //Second Line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(shotLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        add(velocityLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        add(deviationLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 2;
        add(shooterLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 1;
        add(shooterTxt, c);
             
        //third line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(numberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        add(feetSecLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        add(avgVelLbl, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 2;
        add(testNumLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 2;
        c.gridwidth = 1;
        add(testNumTxt, c);
       
        //fourth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        add(oneLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        add(oneVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        add(oneDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 2;
        add(dateLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 3;
        c.gridwidth = 1;
        add(dateTxt, c);
  
        //fifth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        add(twoLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        add(twoVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        add(twoDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 4;
        c.gridwidth = 2;
        add(cartridgeLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 4;
        c.gridwidth = 1;
        add(cartridgeTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 4;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   
             
        //sixth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        add(threeLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        add(threeVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        add(threeDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 2;
        add(firearmLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 5;
        c.gridwidth = 1;
        add(firearmTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 5;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //seventh line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        add(fourLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        add(fourVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        add(fourDevTxt, c);        
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 6;
        c.gridwidth = 3;
        cartridgeDataLbl.setFont(new Font("Calibri", Font.BOLD, 14));
        add(cartridgeDataLbl, c);   

        //eighth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        add(fiveLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        add(fiveVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 1;
        add(fiveDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 7;
        c.gridwidth = 2;
        add(bulletLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 7;
        c.gridwidth = 1;
        add(bulletTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 7;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //ninth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        add(sixLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        add(sixVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        add(sixDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 8;
        c.gridwidth = 2;
        add(castLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 8;
        c.gridwidth = 1;
        add(castTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 8;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //tenth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        add(sevenLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 9;
        c.gridwidth = 1;
        add(sevenVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 9;
        c.gridwidth = 1;
        add(sevenDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 9;
        c.gridwidth = 2;
        add(weightLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 9;
        c.gridwidth = 1;
        add(weightTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 9;
        c.gridwidth = 1;
        add(new JLabel("grains"), c);   
 
        //line 11
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 1;
        add(eightLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 10;
        c.gridwidth = 1;
        add(eightVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 10;
        c.gridwidth = 1;
        add(eightDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 10;
        c.gridwidth = 2;
        add(bcLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 10;
        c.gridwidth = 1;
        add(bcTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 10;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //line 12
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 1;
        add(nineLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 11;
        c.gridwidth = 1;
        add(nineVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 11;
        c.gridwidth = 1;
        add(nineDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 11;
        c.gridwidth = 2;
        add(powderLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 11;
        c.gridwidth = 1;
        add(powderTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 11;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //line 13
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 1;
        add(tenLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 12;
        c.gridwidth = 1;
        add(tenVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 12;
        c.gridwidth = 1;
        add(tenDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 12;
        c.gridwidth = 2;
        add(pWeightLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 12;
        c.gridwidth = 1;
        add(pWeightTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 12;
        c.gridwidth = 1;
        add(new JLabel("grains"), c);   

        //line 14
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 13;
        c.gridwidth = 1;
        add(totalLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 13;
        c.gridwidth = 1;
        add(totalVelTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 13;
        c.gridwidth = 1;
        add(totalDevTxt, c);        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 13;
        c.gridwidth = 2;
        add(locLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 13;
        c.gridwidth = 1;
        add(locTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 13;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //line 15
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 14;
        c.gridwidth = 2;
        add(crimpLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 14;
        c.gridwidth = 1;
        add(crimpTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 14;
        c.gridwidth = 1;
        add(new JLabel("   "), c);   

        //line 16
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 2;
        add(shotsInStrLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 15;
        c.gridwidth = 1;
        add(shotsInStrTxt, c);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 15;
        c.gridwidth = 2;
        add(otherLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 15;
        c.gridwidth = 1;
        add(otherTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 15;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //line 17
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 16;
        c.gridwidth = 3;
        conditionsLbl.setFont(new Font("Calibri", Font.BOLD, 14));
        add(conditionsLbl, c);   
        
        //line 18
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 17;
        c.gridwidth = 2;
        add(timeLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 17;
        c.gridwidth = 1;
        add(timeTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 17;
        c.gridwidth = 1;
        add(new JLabel("  "), c);   

        //line 19
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 18;
        c.gridwidth = 2;
        add(tempLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 18;
        c.gridwidth = 1;
        add(tempTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 18;
        c.gridwidth = 1;
        add(new JLabel("degrees"), c);   

        //line 20
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 19;
        c.gridwidth = 2;
        add(locationLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 19;
        c.gridwidth = 1;
        add(locationTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 19;
        c.gridwidth = 1;
        add(new JLabel("   "), c);   

        //line 21
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 20;
        c.gridwidth = 2;
        add(wdLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 20;
        c.gridwidth = 1;
        add(wdTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 20;
        c.gridwidth = 1;
        add(new JLabel("   "), c);   

        //line 22
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 21;
        c.gridwidth = 2;
        add(wsLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 21;
        c.gridwidth = 1;
        add(wsTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 21;
        c.gridwidth = 1;
        add(new JLabel("   "), c);   

        //line 23
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 22;
        c.gridwidth = 2;
        add(elevationLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 22;
        c.gridwidth = 1;
        add(elevationTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 22;
        c.gridwidth = 1;
        add(new JLabel("   "), c);   

        //line 24
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 23;
        c.gridwidth = 2;
        add(targetIDLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 23;
        c.gridwidth = 1;
        add(targetIDTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 23;
        c.gridwidth = 1;
        add(new JLabel("   "), c);   

        //line 25
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 24;
        c.gridwidth = 2;
        add(dtcLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,15);
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 24;
        c.gridwidth = 1;
        add(dtcTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 7;
        c.gridy = 24;
        c.gridwidth = 1;
        add(new JLabel("feet"), c);   

        //line 26
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 25;
        c.gridwidth = 3;
        evaluationLbl.setFont(new Font("Calibri", Font.BOLD, 20));
        add(evaluationLbl, c);   
       
        //line 27
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 26;
        c.gridwidth = 2;
        add(hvLbl, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 26;
        c.gridwidth = 1;
        add(hvTxt, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 26;
        c.gridwidth = 1;
        add(new JLabel("fps", SwingConstants.LEFT), c);   
       
        //line 28
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 27;
        c.gridwidth = 2;
        add(lvLbl, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 27;
        c.gridwidth = 1;
        add(lvTxt, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 27;
        c.gridwidth = 1;
        add(new JLabel("fps", SwingConstants.LEFT), c);   
       
        //line 29
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 28;
        c.gridwidth = 2;
        add(avLbl, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 28;
        c.gridwidth = 1;
        add(avTxt, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 28;
        c.gridwidth = 1;
        add(new JLabel("fps", SwingConstants.LEFT), c);   
       
        //line 30
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 29;
        c.gridwidth = 2;
        add(esLbl, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 29;
        c.gridwidth = 1;
        add(esTxt, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 29;
        c.gridwidth = 1;
        add(new JLabel("fps", SwingConstants.LEFT), c);   
       
        //line 31
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 30;
        c.gridwidth = 2;
        add(adLbl, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 30;
        c.gridwidth = 1;
        add(adTxt, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 30;
        c.gridwidth = 1;
        add(new JLabel("fps", SwingConstants.LEFT), c);   
       
        //line 32
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 31;
        c.gridwidth = 2;
        add(sdLbl, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 31;
        c.gridwidth = 1;
        add(sdTxt, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 31;
        c.gridwidth = 1;
        add(new JLabel("fps", SwingConstants.LEFT), c);   
       
        //line 33
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 32;
        c.gridwidth = 2;
        add(engeryLbl, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 32;
        c.gridwidth = 1;
        add(engeryTxt, c);   
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 32;
        c.gridwidth = 1;
        add(new JLabel("ft/lbs", SwingConstants.LEFT), c);   
       
        //line 34
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 33;
        c.gridwidth = 7;
        add(new JLabel("  "), c);   

        //Page Buttons
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 34; 
        c.gridwidth = 1;
        add(centerBtn, c);

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.1;
        c.gridx = 5;
        c.gridy = 34;
        c.gridwidth = 1;
        if(winType != ChronographGUI.VIEW) {
	        add(rightBtn, c);   
        } else {
        	add(new JLabel("        "),c);
        }
        
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 35;
        c.gridwidth = 7;
        add(new JLabel("  "), c);   
	}

	private void layout2() {
		this.setSize(600, 600);

		//Labels and text fields.
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        
        //first line
        c.anchor = GridBagConstraints.PAGE_START 	;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        titleLbl.setFont(new Font("Calibri", Font.BOLD, 40));
        add(titleLbl, c);
        
        //Second Line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        add(shotLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        add(velocityLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 3;
        add(new JLabel("INFORMATION", SwingConstants.CENTER), c);
        
		//third line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        add(numberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        add(feetSecLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 1;
        add(shooterLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 2;
        c.gridwidth = 2;
        add(shooterTxt, c);
         
        //forth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        add(oneLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        add(oneVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 2;
        add(new JLabel("Ammunition Lot number: ", SwingConstants.RIGHT), c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 3;
        c.gridwidth = 2;
        add(ammoListCB, c);
        
        //fifth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        add(twoLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        add(twoVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 1;
        add(new JLabel("Firearm: ", SwingConstants.RIGHT), c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 4;
        c.gridwidth = 2;
        add(gunListCB, c);
       
        //sixth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        add(threeLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        add(threeVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 3;
        add(new JLabel("CONDITIONS", SwingConstants.CENTER), c);
       
        //sixth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        add(fourLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        add(fourVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 6;
        c.gridwidth = 1;
        add(dateLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 6;
        c.gridwidth = 2;
        add(dateTxt, c);
                    
        //seventh line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        add(fiveLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 1;
        add(fiveVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 7;
        c.gridwidth = 1;
        add(timeLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 7;
        c.gridwidth = 2;
        add(timeTxt, c);

        //eighth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        add(sixLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        add(sixVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 8;
        c.gridwidth = 1;
        add(locationLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 8;
        c.gridwidth = 2;
        add(locationTxt, c);
       
        //ninth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 9;
        c.gridwidth = 1;
        add(sevenLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 9;
        c.gridwidth = 1;
        add(sevenVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 9;
        c.gridwidth = 1;
        add(elevationLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 9;
        c.gridwidth = 1;
        add(elevationTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 6;
        c.gridy = 9;
        c.gridwidth = 1;
        add(new JLabel(" Feet  ", SwingConstants.LEFT), c);
       
        //tenth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 10;
        c.gridwidth = 1;
        add(eightLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 10;
        c.gridwidth = 1;
        add(eightVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 10;
        c.gridwidth = 1;
        add(targetIDLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 10;
        c.gridwidth = 2;
        add(targetIDTxt, c);
       
        //eleventh line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 11;
        c.gridwidth = 1;
        add(nineLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 11;
        c.gridwidth = 1;
        add(nineVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 11;
        c.gridwidth = 2;
        add(dtcLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 11;
        c.gridwidth = 1;
        add(dtcTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 6;
        c.gridy = 11;
        c.gridwidth = 1;
        add(new JLabel(" Feet  ", SwingConstants.LEFT), c);
        
        //Twelfth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 12;
        c.gridwidth = 1;
        add(tenLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 12;
        c.gridwidth = 1;
        add(tenVelTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 12;
        c.gridwidth = 2;
        add(wsLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 12;
        c.gridwidth = 1;
        add(wsTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 6;
        c.gridy = 12;
        c.gridwidth = 1;
        add(new JLabel(" MPH  ", SwingConstants.LEFT), c);
        
        //Thirteenth line
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 13;
        c.gridwidth = 3;
        add(new JLabel("   ", SwingConstants.LEFT), c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 13;
        c.gridwidth = 2;
        add(wdLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 13;
        c.gridwidth = 2;
        add(wdTxt, c);
        
        //fifteenth line
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 14;
        c.gridwidth = 2;
        add(shotsInStrLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 14;
        c.gridwidth = 1;
        add(shotsInStrTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 4;
        c.gridy = 14;
        c.gridwidth = 1;
        add(tempLbl, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 14;
        c.gridwidth = 1;
        add(tempTxt, c);
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 6;
        c.gridy = 14;
        c.gridwidth = 1;
        add(new JLabel(" Degrees  ", SwingConstants.LEFT), c);
        
        //sixteenth line
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 7;
        add(new JLabel("        ", SwingConstants.LEFT), c);
       
        //seventeenth line
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 16;
        c.gridwidth = 1;
        add(centerBtn, c);      
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.6;
        c.gridx = 5;
        c.gridy = 16;
        c.gridwidth = 1;
        add(rightBtn, c);      
        
    }
    //used to control what happens when the window is closed. 
    public void setCloseType(int cl) {
            this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
            return this.getDefaultCloseOperation();
    }

    public ChronographData getCd() {
        return cd;
    }

    public void setCd(ChronographData cd) {
        this.cd = cd;
    }

    public Bullets getBd() {
        return bd;
    }

    public void setBd(Bullets bd) {
        this.bd = bd;
    }

    public Powder getPd() {
        return pd;
    }

    public void setPd(Powder pd) {
        this.pd = pd;
    }

    public Firearm getFd() {
        return fd;
    }

    public void setFd(Firearm fd) {
        this.fd = fd;
    }

    public int getWinType() {
        return winType;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }

    @Override
    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert chronograph data "+cd.getReloadLotNumber()) == 1) {
                        if(createChronographData()) {
                            ChronoDataEvent ev = new ChronoDataEvent(this, cd, ChronoDataEvent.INSERT_TEST_NUMBER);
                            
                            if(chronoDataListener != null)
                                chronoDataListener.ChronoDataEventOccurred(ev);
                            exit();
                        } else {
                                AlertDialog("Error in createChronographData: ");
                        }						
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Powder Lot Number "+cd.getReloadLotNumber()) == 1) {
                        if(createChronographData()) {
                            ChronoDataEvent ev = new ChronoDataEvent(this, cd, ChronoDataEvent.UPDATE_TEST_NUMBER);
                            
                            if(chronoDataListener != null)
                                chronoDataListener.ChronoDataEventOccurred(ev);
                            exit();
                        } else {
                                AlertDialog("Error?");
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Chronograph Date Number "+cd.getTestNumber()) == 1) {
                        ChronoDataEvent ev = new ChronoDataEvent(this, cd, ChronoDataEvent.DELETE_TEST_NUMBER);

                        if(chronoDataListener != null)
                            chronoDataListener.ChronoDataEventOccurred(ev);
                        exit();
                    } 					
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    AlertDialog("The window type has not been set for ChronographDataGUI.");
                    exit();
                    break;	    		
            }
    	} else if(actEvt.getSource() == rightBtn) {
            exit();
    	}
    }
    
    //Alerts the user if error happen.
    private void AlertDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(this, t, "ALERT!", JOptionPane.ERROR_MESSAGE);
    }
  
    private void buildInsert() {
        String[] ammoAry;
        ReloadListEvent rev = new ReloadListEvent(this, ad, ReloadListEvent.GET_ALL_LOTNUMBERS);
        if(reloadListListener != null)
            reloadListListener.ReloadListEventOccurred(rev);

        ammoList = rev.getAllReloadsList();   
        if(ammoList == null){
            AlertDialog("Error: ChronographDataGUI.buildInsert() AmmoList is null");
            ammoAry = new String[] { "Nothing", "Fucked" };
        }else{
            int as = ammoList.size();
            ammoAry = new String[as];
            for(int x=0; x < as ;) {
                    ammoAry[x] = ammoList.get(x);
                    x++;
            }
        }
        
        FirearmEvent fev = new FirearmEvent(this, fd, FirearmEvent.GET_ALL_LOTNUMBERS);
        if(firearmListener != null)
            firearmListener.FirearmEventOccurred(fev);
        
        firearmList = fev.getFirearmList();
    	int fs = firearmList.size();
    	String[] gunAry = new String[fs];
    	for(int x=0; x < fs ;) {
    		gunAry[x] = firearmList.get(x);
    		x++;
    	}    	
    	gunListCB = new JComboBox<String>(gunAry);
    	ammoListCB = new JComboBox<String>(ammoAry);   	
    	ammoListCB.insertItemAt("No Lot Number",0);
    	ammoListCB.setSelectedIndex(0);
    	gunListCB.setEditable(false);
    	ammoListCB.setEditable(false);
    	shooterTxt.setText("Todd Brenneman");
    	locationTxt.setText("Backyard");
    	CalendarTest cal = new CalendarTest();
    	cal.getCurrentDate();
    	dateTxt.setText(cal.toString());
    	timeTxt.setText("1200");
    	centerBtn.setText("INSERT");
    	centerBtn.addActionListener(this);
    	rightBtn.setText("CANCEL");
    	rightBtn.addActionListener(this);
    }
    
    private void buildUpdate() {
        ReloadListEvent rev = new ReloadListEvent(this, ad, ReloadListEvent.GET_ALL_LOTNUMBERS);
        if(reloadListListener != null)
            reloadListListener.ReloadListEventOccurred(rev);
        
        ammoList = rev.getAllReloadsList();        
    	int as = ammoList.size();
    	String[] ammoAry = new String[as];
    	for(int x=0; x < as ;) {
    		ammoAry[x] = ammoList.get(x);
    		x++;
    	}
        
        FirearmEvent fev = new FirearmEvent(this, fd, FirearmEvent.GET_ALL_LOTNUMBERS);
        if(firearmListener != null)
            firearmListener.FirearmEventOccurred(fev);
        
        firearmList = fev.getFirearmList();
    	int fs = firearmList.size();
    	String[] gunAry = new String[fs];
    	for(int x=0; x < fs ;) {
    		gunAry[x] = firearmList.get(x);
    		x++;
    	}    	
    	gunListCB = new JComboBox<String>(gunAry);
    	ammoListCB = new JComboBox<String>(ammoAry);   	
    	ammoListCB.insertItemAt("No Lot Number",0);
    	ammoListCB.setSelectedItem(cd.getReloadLotNumber());
    	gunListCB.setSelectedItem(cd.getFirearmID());
    	gunListCB.setEditable(false);
    	ammoListCB.setEditable(false);
    	FormatFloat ff = new FormatFloat();
    	oneVelTxt.setText(ff.floatConvert(cd.getVelocity1(),1,2));
    	twoVelTxt.setText(ff.floatConvert(cd.getVelocity2(),1,2));
    	threeVelTxt.setText(ff.floatConvert(cd.getVelocity3(),1,2));
    	fourVelTxt.setText(ff.floatConvert(cd.getVelocity4(),1,2));
    	fiveVelTxt.setText(ff.floatConvert(cd.getVelocity5(),1,2));
    	sixVelTxt.setText(ff.floatConvert(cd.getVelocity6(),1,2));
    	sevenVelTxt.setText(ff.floatConvert(cd.getVelocity7(),1,2));
    	eightVelTxt.setText(ff.floatConvert(cd.getVelocity8(),1,2));
    	nineVelTxt.setText(ff.floatConvert(cd.getVelocity9(),1,2));
    	tenVelTxt.setText(ff.floatConvert(cd.getVelocity10(),1,2));
    	shotsInStrTxt.setText(Integer.toString(cd.getShotsInString()));
    	shooterTxt.setText(cd.getShooter());
    	testNumTxt.setText(Integer.toString(cd.getTestNumber()));
    	CalendarTest cal = new CalendarTest(cd.getShotDate()); 		
    	dateTxt.setText(cal.toString());
    	timeTxt.setText(cd.getShotTime());
    	tempTxt.setText(Integer.toString(cd.getTempature()));
    	locationTxt.setText(cd.getLocation());
    	wdTxt.setText(cd.getWindDirection());
    	wsTxt.setText(ff.floatConvert(cd.getWindSpeed(),1,1));
    	elevationTxt.setText(ff.floatConvert(cd.getElevation(),1,2));
    	targetIDTxt.setText(Integer.toString(cd.getTargetIDNumber()));
    	centerBtn.setText("UPDATE");
    	centerBtn.addActionListener(this);
    	rightBtn.setText("CANCEL");
    	rightBtn.addActionListener(this);
    }
    
    private void buildView() {
     	FormatFloat ff = new FormatFloat();
    	oneVelTxt.setText(ff.floatConvert(cd.getVelocity1(),1,2));
    	twoVelTxt.setText(ff.floatConvert(cd.getVelocity2(),1,2));
    	threeVelTxt.setText(ff.floatConvert(cd.getVelocity3(),1,2));
    	fourVelTxt.setText(ff.floatConvert(cd.getVelocity4(),1,2));
    	fiveVelTxt.setText(ff.floatConvert(cd.getVelocity5(),1,2));
    	sixVelTxt.setText(ff.floatConvert(cd.getVelocity6(),1,2));
    	sevenVelTxt.setText(ff.floatConvert(cd.getVelocity7(),1,2));
    	eightVelTxt.setText(ff.floatConvert(cd.getVelocity8(),1,2));
    	nineVelTxt.setText(ff.floatConvert(cd.getVelocity9(),1,2));
    	tenVelTxt.setText(ff.floatConvert(cd.getVelocity10(),1,2));
    	totalVelTxt.setText(ff.floatConvert(cd.getTotalVelocity(),1,2));
    	oneDevTxt.setText(ff.floatConvert(cd.getDevVelocity1(),1,2));
    	twoDevTxt.setText(ff.floatConvert(cd.getDevVelocity2(),1,2));
    	threeDevTxt.setText(ff.floatConvert(cd.getDevVelocity3(),1,2));
    	fourDevTxt.setText(ff.floatConvert(cd.getDevVelocity4(),1,2));
    	fiveDevTxt.setText(ff.floatConvert(cd.getDevVelocity5(),1,2));
    	sixDevTxt.setText(ff.floatConvert(cd.getDevVelocity6(),1,2));
    	sevenDevTxt.setText(ff.floatConvert(cd.getDevVelocity7(),1,2));
    	eightDevTxt.setText(ff.floatConvert(cd.getDevVelocity8(),1,2));
    	nineDevTxt.setText(ff.floatConvert(cd.getDevVelocity9(),1,2));
    	tenDevTxt.setText(ff.floatConvert(cd.getDevVelocity10(),1,2));
    	totalDevTxt.setText(ff.floatConvert(cd.getTotalDevVelocity(),1,2));
    	shotsInStrTxt.setText(String.valueOf(cd.getShotsInString()));
    	shooterTxt.setText(cd.getShooter());
    	testNumTxt.setText(String.valueOf(cd.getTestNumber()));
    	CalendarTest cal = new CalendarTest(cd.getShotDate()); 		
    	dateTxt.setText(cal.toString());
        ad = cd.getReloadList();
        fd = cd.getFirearm();
        pd = cd.getPowder();
        bd = cd.getBullet();
        
        if(ad == null && cd.getReloadLotNumber().equalsIgnoreCase("No Lot Number")) {
            cd.calculateValues((float)0.0);
            cartridgeTxt.setText(fd.getCaliber());    		
            pWeightTxt.setText("");
            locTxt.setText("");
            crimpTxt.setText("");
            otherTxt.setText("");  
            bulletTxt.setText("");
            castTxt.setText("");
            weightTxt.setText("");
            bcTxt.setText("");
            powderTxt.setText("");
        } else if(ad == null && bd != null){
            cd.calculateValues(bd.getWeightFloat());
            cartridgeTxt.setText(fd.getCaliber());    		
            pWeightTxt.setText("");
            locTxt.setText("");
            crimpTxt.setText("");
            otherTxt.setText("");
            bulletTxt.setText(bd.getDescription());
            castTxt.setText("");
            weightTxt.setText(bd.getWeight());
            bcTxt.setText("");
            powderTxt.setText("");
    	} else {
            cd.calculateValues(bd.getWeightFloat());
            cartridgeTxt.setText(ad.getCaliber());    		
            pWeightTxt.setText(ff.floatConvert(ad.getPowderWeight(),1,2));
            locTxt.setText(ff.floatConvert(ad.getOverAllLength(),1,2));
            crimpTxt.setText(ad.getCrimp());
            otherTxt.setText(ad.getNotes());
            bulletTxt.setText(bd.getDescription());
            castTxt.setText(bd.getCastAlloy());
            weightTxt.setText(bd.getWeight());
            bcTxt.setText(ff.floatConvert(bd.getBC(),1,2));
            powderTxt.setText(pd.getPowderName());    
    	}
    	timeTxt.setText(cd.getShotTime());
    	tempTxt.setText(Integer.toString(cd.getTempature()));
    	locationTxt.setText(cd.getLocation());
    	wdTxt.setText(cd.getWindDirection());
    	wsTxt.setText(ff.floatConvert(cd.getWindSpeed(),1,1));
    	elevationTxt.setText(ff.floatConvert(cd.getElevation(),1,2));
    	targetIDTxt.setText(String.valueOf(cd.getTargetIDNumber()));
    	dtcTxt.setText(String.valueOf(cd.getDistanceToChrony()));
    	hvTxt.setText(ff.floatConvert(cd.getHighVel(),1,2));
    	lvTxt.setText(ff.floatConvert(cd.getLowVel(),1,2));
    	avTxt.setText(ff.floatConvert(cd.getAvgVel(),1,2));
    	esTxt.setText(ff.floatConvert(cd.getExtermeSpread(),1,2));
    	adTxt.setText(ff.floatConvert(cd.getAvgDev(),1,2));
    	sdTxt.setText(ff.floatConvert(cd.getStdDev(),1,2));
    	engeryTxt.setText(ff.floatConvert(cd.getEngery(),1,2));
    	firearmTxt.setText(fd.getModelName());
    	
        centerBtn.setText("OK");
        centerBtn.addActionListener(this);  
    }
    private void buildDelete() {
        ad = cd.getReloadList();
        fd = cd.getFirearm();
        pd = cd.getPowder();
        bd = cd.getBullet();
                       	
        FormatFloat ff = new FormatFloat();
        cd.calculateValues(bd.getWeightFloat());
        oneVelTxt.setText(ff.floatConvert(cd.getVelocity1(),1,2));
        twoVelTxt.setText(ff.floatConvert(cd.getVelocity2(),1,2));
        threeVelTxt.setText(ff.floatConvert(cd.getVelocity3(),1,2));
        fourVelTxt.setText(ff.floatConvert(cd.getVelocity4(),1,2));
        fiveVelTxt.setText(ff.floatConvert(cd.getVelocity5(),1,2));
        sixVelTxt.setText(ff.floatConvert(cd.getVelocity6(),1,2));
        sevenVelTxt.setText(ff.floatConvert(cd.getVelocity7(),1,2));
        eightVelTxt.setText(ff.floatConvert(cd.getVelocity8(),1,2));
        nineVelTxt.setText(ff.floatConvert(cd.getVelocity9(),1,2));
        tenVelTxt.setText(ff.floatConvert(cd.getVelocity10(),1,2));
        totalVelTxt.setText(ff.floatConvert(cd.getTotalVelocity(),1,2));
        oneDevTxt.setText(ff.floatConvert(cd.getDevVelocity1(),1,2));
        twoDevTxt.setText(ff.floatConvert(cd.getDevVelocity2(),1,2));
        threeDevTxt.setText(ff.floatConvert(cd.getDevVelocity3(),1,2));
        fourDevTxt.setText(ff.floatConvert(cd.getDevVelocity4(),1,2));
        fiveDevTxt.setText(ff.floatConvert(cd.getDevVelocity5(),1,2));
        sixDevTxt.setText(ff.floatConvert(cd.getDevVelocity6(),1,2));
        sevenDevTxt.setText(ff.floatConvert(cd.getDevVelocity7(),1,2));
        eightDevTxt.setText(ff.floatConvert(cd.getDevVelocity8(),1,2));
        nineDevTxt.setText(ff.floatConvert(cd.getDevVelocity9(),1,2));
        tenDevTxt.setText(ff.floatConvert(cd.getDevVelocity10(),1,2));
        totalDevTxt.setText(ff.floatConvert(cd.getTotalDevVelocity(),1,2));
        shotsInStrTxt.setText(Integer.toString(cd.getShotsInString()));
        shooterTxt.setText(cd.getShooter());
        testNumTxt.setText(Integer.toString(cd.getTestNumber()));
        CalendarTest cal = new CalendarTest(cd.getShotDate());
        dateTxt.setText(cal.toString());
        if(ad != null) {
            cartridgeTxt.setText(ad.getCaliber());    		
            pWeightTxt.setText(ff.floatConvert(ad.getPowderWeight(),1,2));
            locTxt.setText(ff.floatConvert(ad.getOverAllLength(),1,2));
            crimpTxt.setText(ad.getCrimp());
            otherTxt.setText(ad.getNotes());
            bulletTxt.setText(bd.getDescription());
            castTxt.setText(bd.getCastAlloy());
            weightTxt.setText(bd.getWeight());
            bcTxt.setText(ff.floatConvert(bd.getBC(),1,2));
            powderTxt.setText(pd.getPowderName());
        } else {
            cartridgeTxt.setText(fd.getCaliber());    		
            pWeightTxt.setText("");
            locTxt.setText("");
            crimpTxt.setText("");
            otherTxt.setText("");  
            bulletTxt.setText("");
            castTxt.setText("");
            weightTxt.setText("");
            bcTxt.setText("");
            powderTxt.setText("");
        }
        timeTxt.setText(cd.getShotTime());
        tempTxt.setText(Integer.toString(cd.getTempature()));
        locationTxt.setText(cd.getLocation());
        wdTxt.setText(cd.getWindDirection());
        wsTxt.setText(ff.floatConvert(cd.getWindSpeed(),1,1));
        elevationTxt.setText(ff.floatConvert(cd.getElevation(),1,2));
        targetIDTxt.setText(String.valueOf(cd.getTargetIDNumber()));
        dtcTxt.setText(String.valueOf(cd.getDistanceToChrony()));
        hvTxt.setText(ff.floatConvert(cd.getHighVel(),1,2));
        lvTxt.setText(ff.floatConvert(cd.getLowVel(),1,2));
        avTxt.setText(ff.floatConvert(cd.getAvgVel(),1,2));
        esTxt.setText(ff.floatConvert(cd.getExtermeSpread(),1,2));
        adTxt.setText(ff.floatConvert(cd.getAvgDev(),1,2));
        sdTxt.setText(ff.floatConvert(cd.getStdDev(),1,2));
        engeryTxt.setText(ff.floatConvert(cd.getEngery(),1,2));
        firearmTxt.setText(fd.getModelName());

        centerBtn.setText("DELETE");
        centerBtn.addActionListener(this);    	
        rightBtn.setText("CANCEL");
        rightBtn.addActionListener(this);
    }

    private boolean createChronographData() {
    	if(shotsInStrTxt.getText().isBlank())
    		return false;
    	
    	int sis = Integer.parseInt(shotsInStrTxt.getText());
    	cd.setShotsInString(sis);
    	if(sis < 1)
    		return false;
    	else
    		cd.setVelocity1(Float.parseFloat(oneVelTxt.getText()));
    	if(sis < 2)
    		return false;
    	else
    		cd.setVelocity2(Float.parseFloat(twoVelTxt.getText()));
    	if(sis >= 3)
    		cd.setVelocity3(Float.parseFloat(threeVelTxt.getText()));
    	if(sis >= 4)    	
    		cd.setVelocity4(Float.parseFloat(fourVelTxt.getText()));
    	if(sis >= 5)
    		cd.setVelocity5(Float.parseFloat(fiveVelTxt.getText()));
    	if(sis >= 6)
    		cd.setVelocity6(Float.parseFloat(sixVelTxt.getText()));
    	if(sis >= 7)
    		cd.setVelocity7(Float.parseFloat(sevenVelTxt.getText()));
    	if(sis >= 8)
    		cd.setVelocity8(Float.parseFloat(eightVelTxt.getText()));
    	if(sis >= 9)
    		cd.setVelocity9(Float.parseFloat(nineVelTxt.getText()));
    	if(sis >= 10)
    		cd.setVelocity10(Float.parseFloat(tenVelTxt.getText()));
    	
    	cd.setShooter(shooterTxt.getText());
    	if(dateTxt.getText().trim().isBlank() == false) {
    		CalendarTest cal = new CalendarTest(dateTxt.getText());
    		cd.setShotDate(cal.getCal());
    	}
    	cd.setReloadLotNumber((String)ammoListCB.getSelectedItem());
    	cd.setFirearmID((String)gunListCB.getSelectedItem());
    	if(!timeTxt.getText().isBlank())
    		cd.setShotTime(timeTxt.getText());
    	if(!tempTxt.getText().isBlank())    	
    		cd.setTempature(Integer.parseInt(tempTxt.getText()));
    	cd.setLocation(locationTxt.getText());
       	if(!wdTxt.getText().isBlank())    	
       		cd.setWindDirection(wdTxt.getText());
       	if(!wsTxt.getText().isBlank())    	
       		cd.setWindSpeed(Float.parseFloat(wsTxt.getText()));
       	if(!elevationTxt.getText().isBlank())    	
       		cd.setElevation(Float.parseFloat(elevationTxt.getText()));
       	if(!targetIDTxt.getText().isBlank())    	
       		cd.setTargetIDNumber(Integer.parseInt(targetIDTxt.getText()));
       	if(!dtcTxt.getText().isBlank())    	
       		cd.setDistanceToChrony(Integer.parseInt(dtcTxt.getText()));
		return true;
    }
    
    //handles the exit after the buttons are pressed
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

    public void setChronoDataListener(ChronoDataListener chronoDataListener) {
        this.chronoDataListener = chronoDataListener;
    }

    public void setReloadListListener(ReloadListListener reloadListListener) {
        this.reloadListListener = reloadListListener;
    }

    public void setBulletsListener(BulletsListener bulletsListener) {
        this.bulletsListener = bulletsListener;
    }

    public void setPowderListener(PowderListener powderListener) {
        this.powderListener = powderListener;
    }

    public void setFirearmListener(FirearmListener firearmListener) {
        this.firearmListener = firearmListener;
    }
}

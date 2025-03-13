/**
 * 
 */
package ReloadEvent;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import BaseClasses.CalendarTest;
import BaseClasses.FormatFloat;
import BulletsEvent.Bullets;
import BulletsEvent.BulletsEvent;
import BulletsEvent.BulletsListener;
import CasesEvent.Cases;
import CasesEvent.CasesEvent;
import CasesEvent.CasesListener;
import PowderEvent.Powder;
import PowderEvent.PowderEvent;
import PowderEvent.PowderListener;
import PrimerEvent.Primer;
import PrimerEvent.PrimerEvent;
import PrimerEvent.PrimerListener;
import java.io.File;
import javax.swing.JRadioButton;

/**
 * @author trbrenn
 *
 */
public class ReloadListGUI extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
    //Default windows size.
    private final int XSIZE = 400;
    private final int YSIZE = 585;

    private FormatFloat         ff = new FormatFloat();
    private CalendarTest        ct = new CalendarTest();
    private ReloadList          al = new ReloadList();
    private ReloadListListener  reloadListListener;
    private Bullets             bs = new Bullets();
    private BulletsListener     bulletsListener;
    private Cases               cs = new Cases();
    private CasesListener       casesListener;
    private Primer              pr = new Primer();
    private PrimerListener      primerListener;
    private Powder              pw = new Powder();
    private PowderListener      powderListener;
    
    private JLabel TitleLbl = new JLabel("Todds Reloading Label", SwingConstants.CENTER);
    private JLabel LotNumberLbl = new JLabel("Lot Number", SwingConstants.CENTER);
    private JLabel CaliberLbl = new JLabel("Caliber", SwingConstants.CENTER);
    private JLabel BulletLbl = new JLabel("Bullet Weight, Brand and Style", SwingConstants.CENTER);
    private JLabel PowderLbl = new JLabel("Powder Weight and Brand", SwingConstants.CENTER);
    private JLabel PrimerLbl = new JLabel("Primer", SwingConstants.CENTER);
    private JLabel CaseLbl = new JLabel("Case", SwingConstants.CENTER);
    private JLabel LoadedLbl = new JLabel("Times Loaded", SwingConstants.CENTER);
    private JLabel CaseLengthLbl = new JLabel("Case Length", SwingConstants.CENTER);
    private JLabel CaseAOLLbl = new JLabel("Over All Length", SwingConstants.CENTER);
    private JLabel DateLbl = new JLabel("Date", SwingConstants.CENTER);
    private JLabel NotesLbl = new JLabel("Notes", SwingConstants.CENTER);
    private JLabel FirearmIDLbl = new JLabel("Firearm ID", SwingConstants.CENTER);
    private JLabel ChronoIDLbl = new JLabel("Chronoghph ID", SwingConstants.CENTER);
    private JLabel CrimpLbl = new JLabel("Crimp", SwingConstants.CENTER);
    private JLabel EmptyLbl = new JLabel("Empty:", SwingConstants.RIGHT);

    private JTextField LotNumberTxt = new JTextField();
    private JTextField CaliberTxt = new JTextField();
    private JTextField BulletTxt = new JTextField();
    private JTextField PowderTxt = new JTextField();
    private JTextField PowderWeightTxt = new JTextField();
    private JTextField PrimerTxt = new JTextField();
    private JTextField CaseTxt = new JTextField();
    private JTextField LoadedTxt = new JTextField();
    private JTextField CaseLengthTxt = new JTextField();
    private JTextField CaseAOLTxt = new JTextField();
    private JTextField DateTxt = new JTextField();
    private JTextArea  NotesTxt = new JTextArea(3,80);
    private JTextField CountTxt = new JTextField();
    private JTextField FirearmIDTxt = new JTextField();
    private JTextField ChronoIDTxt = new JTextField();
    private JTextField CrimpTxt = new JTextField();
    
    private JComboBox <String> bulletList = new JComboBox<String>();
    private JComboBox <String> caseList = new JComboBox<String>();
    private JComboBox <String> primerList = new JComboBox<String>();
    private JComboBox <String> powderList = new JComboBox<String>();

    private int winType = ReloadListGUI.NOT_SET;
    private JButton centerBtn = new JButton();
    private JButton rightBtn = new JButton();
    private JRadioButton EmptyYesBtn= new JRadioButton("Yes", false);
    private JRadioButton EmptyNoBtn= new JRadioButton("No", false);	

    //default constructor.
    public ReloadListGUI() {
        this.setTitle("AmmoList ");
        this.setResizable(false);
        this.setSize(XSIZE, YSIZE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }	

    //Create a new instant and set the AmmoList data to be displayed.
    public ReloadListGUI(ReloadList ics) {
        this.setTitle("AmmoList");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        al = ics;
        pw.setPowderLotNumber(al.getPowderLotNumber());
        bs.setLotNumber(al.getBulletLotNumber());
        cs.setLotNumber(al.getCaseLotNumber());
        pr.setLotNumber(al.getPrimerLotNumber());
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }
	
    //Create a new instant and set the AmmoList data from xml data.
    public ReloadListGUI(String xml) {
        this.setTitle("AmmoList");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        al = new ReloadList(xml);
        pw.setPowderLotNumber(al.getPowderLotNumber());
        bs.setLotNumber(al.getBulletLotNumber());
        cs.setLotNumber(al.getCaseLotNumber());
        pr.setLotNumber(al.getPrimerLotNumber());
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //Create a new window and set the AmmoList data and window type.
    public ReloadListGUI(ReloadList ics, int wt) {
        this.setTitle("AmmoList");
        this.setSize(XSIZE, YSIZE);
        this.setResizable(false);
        al = ics;
        pw.setPowderLotNumber(al.getPowderLotNumber());
        bs.setLotNumber(al.getBulletLotNumber());
        cs.setLotNumber(al.getCaseLotNumber());
        pr.setLotNumber(al.getPrimerLotNumber());
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();
    }

    //create a new window and set the AmmoList data from a xml string and set the wintype.
    public ReloadListGUI(String xml, int wt) {
        this.setTitle("AmmoList");
        this.setSize(XSIZE, YSIZE);
        al = new ReloadList(xml);
        pw.setPowderLotNumber(al.getPowderLotNumber());
        bs.setLotNumber(al.getBulletLotNumber());
        cs.setLotNumber(al.getCaseLotNumber());
        pr.setLotNumber(al.getPrimerLotNumber());
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.makeIcon();

    }	
	
    //Creates the little icon shown on the JFrame.
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
                // need to build all weird shit to fill the section lists.
                this.loadPowder();
                powderList.addActionListener(this);
                this.loadPrimer();
                primerList.addActionListener(this);
                this.loadCases();
                caseList.addActionListener(this);
                this.loadBullets();
                bulletList.addActionListener(this);
                buildInsert();
                buildInsertUpdate();
                break;

            case UPDATE: //Build the update window.
                //Not sure how I'm going to do this.
                fillOutDependants();
                if(pr.getLotNumber().isBlank()) {
                    AlertDialog("Primer has not been set for PrimerGUI.buildWindow");
                    return;
                }
                if(pw.getPowderLotNumber().isBlank()) {
                    AlertDialog("Powder has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                if(bs.getLotNumber().isBlank()) {
                    AlertDialog("Bullets has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                if(cs.getLotNumber().isBlank()) {
                    AlertDialog("1 Cases has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                buildUpdate();
                buildInsertUpdate();
                break;
            case DELETE: //Build the Delete window.
                fillOutDependants();
                if(pr.getLotNumber().isBlank()) {
                    AlertDialog("Primer has not been set for PrimerGUI.buildWindow");
                    return;
                }
                if(pw.getPowderLotNumber().isBlank()) {
                    AlertDialog("Powder has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                if(bs.getLotNumber().isBlank()) {
                    AlertDialog("Bullets has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                if(cs.getLotNumber().isBlank()) {
                    AlertDialog("2 Cases has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                buildDelete();
                buildDeleteView();
                break;
            case VIEW: //Build the view window.
                fillOutDependants();
                if(pr.getLotNumber().isBlank()) {
                    AlertDialog("Primer has not been set for PrimerGUI.buildWindow");
                    return;
                }
                if(pw.getPowderLotNumber().isBlank()) {
                    AlertDialog("Powder has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                if(cs.getLotNumber().isBlank()) {
                    AlertDialog("3 Cases has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                if(bs == null || bs.getLotNumber().isBlank()) {
                    AlertDialog("Bullets has not been set for AmmoListGUI.buildWindow");
                    return;
                }
                //System.out.println("ReloadList = "+al);
                //System.out.println("Primer = "+pr);
                //System.out.println("Powder = "+pw);
                //System.out.println("Case = "+cs);
                //System.out.println("Bullet = "+bs);
                buildView();
                buildDeleteView();
                break;
            default:
                AlertDialog("The window type has not been set for AmmoListGUI.buildWindow");
                return;	    		
        }
    }

    private void buildInsertUpdate() {
        this.setSize(600, 600);

        TitleLbl = new JLabel("Ammunition Load Data", SwingConstants.CENTER);
        LotNumberLbl = new JLabel("Lot Number", SwingConstants.RIGHT);
        CaliberLbl = new JLabel("Caliber", SwingConstants.RIGHT);
        BulletLbl = new JLabel("Bullet Lot Number", SwingConstants.RIGHT);
        PowderLbl = new JLabel("Powder Lot Number", SwingConstants.RIGHT);
        PrimerLbl = new JLabel("Primer Lot Number", SwingConstants.RIGHT);
        CaseLbl = new JLabel("Case Lot Number", SwingConstants.RIGHT);
        LoadedLbl = new JLabel("Times Loaded", SwingConstants.RIGHT);
        CaseLengthLbl = new JLabel("Case Length", SwingConstants.RIGHT);
        CaseAOLLbl = new JLabel("Over All Length", SwingConstants.RIGHT);
        DateLbl = new JLabel("Date", SwingConstants.RIGHT);
        NotesLbl = new JLabel("Notes", SwingConstants.RIGHT);
        FirearmIDLbl = new JLabel("Firearm ID", SwingConstants.RIGHT);
        ChronoIDLbl = new JLabel("Chronoghph ID", SwingConstants.RIGHT);
        CrimpLbl = new JLabel("Crimp", SwingConstants.RIGHT);

        //Labels and text fields.
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        //c.ipady = 1;
        
        c.anchor = GridBagConstraints.FIRST_LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 1;
        add(TitleLbl , c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(LotNumberLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        add(LotNumberTxt, c);
       
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(CaliberLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        add(CaliberTxt, c);

        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(BulletLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 2;
        add(bulletList, c);

        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(PowderLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 2;
        add(powderList, c);

        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(PowderLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        add(powderList, c);
        c.anchor = GridBagConstraints.LINE_END	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 6;
        c.gridwidth = 1;
        add(PowderWeightTxt , c);  
        c.anchor = GridBagConstraints.LINE_END	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 4;
        c.gridy = 6;
        c.gridwidth = 1;
        add(new JLabel("Grains", SwingConstants.LEFT) , c);
        
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(CaseLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 2;
        add(caseList, c);

        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(PrimerLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 2;
        add(primerList, c);
       
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(LoadedLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 9;
        c.gridwidth = 2;
        add(LoadedTxt, c);
       
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(CaseLengthLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 10;
        c.gridwidth = 2;
        add(CaseLengthTxt, c);
        c.anchor = GridBagConstraints.LINE_END	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 4;
        c.gridy = 10;
        c.gridwidth = 1;
        add(new JLabel("Inches", SwingConstants.LEFT) , c);

        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(CaseAOLLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 11;
        c.gridwidth = 2;
        add(CaseAOLTxt, c);
        c.anchor = GridBagConstraints.LINE_END	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 4;
        c.gridy = 11;
        c.gridwidth = 1;
        add(new JLabel("Inches", SwingConstants.LEFT) , c);

        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(DateLbl , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 12;
        c.gridwidth = 2;
        add(DateTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 13;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(new JLabel("Firearm ID", SwingConstants.RIGHT) , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 13;
        c.gridwidth = 2;
        add(FirearmIDTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 14;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(new JLabel("Chronograph ID", SwingConstants.RIGHT) , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 14;
        c.gridwidth = 2;
        add(ChronoIDTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(new JLabel("Crimp", SwingConstants.RIGHT) , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 15;
        c.gridwidth = 2;
        add(CrimpTxt, c);
        
        c.anchor = GridBagConstraints.LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 16;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(new JLabel("Count:", SwingConstants.RIGHT) , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 16;
        c.gridwidth = 2;
        add(CountTxt, c);
        
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 17;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(NotesLbl , c);   
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 17;
        c.gridwidth = 3;
        c.gridheight = 5;
        NotesTxt.setLineWrap(true);
        NotesTxt.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(NotesTxt);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(200, 50));        
        add(areaScrollPane , c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 24;
        c.gridwidth = 2;
        add(EmptyLbl, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 24;
        c.gridwidth = 1;
        add(EmptyYesBtn, c);   
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 24;
        c.gridwidth = 1;
        add(EmptyNoBtn, c);   

        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 29; 
        c.gridwidth = 5;
        c.gridheight = 1;
        add(new JLabel("     ", SwingConstants.LEFT), c);        
        
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 29; 
        c.gridwidth = 1;
        c.gridheight = 1;
        add(centerBtn, c);

        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 29;
        c.gridwidth = 1;
        c.gridheight = 1;
	add(rightBtn, c);   
    }
	
    private void buildDeleteView() {
        TitleLbl = new JLabel("Todds Reloading Label", SwingConstants.CENTER);
        LotNumberLbl = new JLabel("Lot Number", SwingConstants.CENTER);
        CaliberLbl = new JLabel("Caliber", SwingConstants.CENTER);
        BulletLbl = new JLabel("Bullet Weight, Brand and Style", SwingConstants.CENTER);
        PowderLbl = new JLabel("Powder Weight and Brand", SwingConstants.CENTER);
        PrimerLbl = new JLabel("Primer", SwingConstants.CENTER);
        CaseLbl = new JLabel("Case", SwingConstants.CENTER);
        LoadedLbl = new JLabel("Times Loaded", SwingConstants.CENTER);
        CaseLengthLbl = new JLabel("Case Length", SwingConstants.CENTER);
        CaseAOLLbl = new JLabel("Over All Length", SwingConstants.CENTER);
        DateLbl = new JLabel("Date", SwingConstants.CENTER);
        NotesLbl = new JLabel("Notes", SwingConstants.CENTER);

        //Labels and text fields.
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        //c.ipady = 1;
        
        c.anchor = GridBagConstraints.FIRST_LINE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 2;
        add(TitleLbl , c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        c.gridheight = 1;
        LotNumberTxt.setHorizontalAlignment(JTextField.CENTER);
        add(LotNumberTxt, c);
        c.insets = new Insets(0,15,5,15);
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        c.ipady = 1;
        add(LotNumberLbl, c);
       
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 4;
	    CaliberTxt.setHorizontalAlignment(JTextField.CENTER);
	    add(CaliberTxt , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 4;
        add(CaliberLbl, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 4;
        BulletTxt.setHorizontalAlignment(JTextField.CENTER);
        add(BulletTxt  , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 4;
       	add(BulletLbl , c); 
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 4;
        PowderTxt.setHorizontalAlignment(JTextField.CENTER);
        add(PowderTxt , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 4;
        add(PowderLbl , c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 2;
        PrimerTxt.setHorizontalAlignment(JTextField.CENTER);
        add(PrimerTxt , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 2;
        add(PrimerLbl , c);       
        
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 11;
        c.gridwidth = 2;
        CaseTxt.setHorizontalAlignment(JTextField.CENTER);
        add(CaseTxt , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 12;
        c.gridwidth = 2;
        add(CaseLbl , c);   
  
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 13;
        c.gridwidth = 2;
        LoadedTxt.setHorizontalAlignment(JTextField.CENTER);
        add(LoadedTxt , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 14;
        c.gridwidth = 2;
        add(LoadedLbl, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 13;
        c.gridwidth = 2;
        CaseLengthTxt.setHorizontalAlignment(JTextField.CENTER);
        add(CaseLengthTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 14;
        c.gridwidth = 2;
        add(CaseLengthLbl, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 2;
        CaseAOLTxt.setHorizontalAlignment(JTextField.CENTER);
        add(CaseAOLTxt, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 16;
        c.gridwidth = 2;
        add(CaseAOLLbl, c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 15;
        c.gridwidth = 2;
        DateTxt.setHorizontalAlignment(JTextField.CENTER);
        add(DateTxt , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,5,15);
        c.weightx = 0.6;
        c.gridx = 2;
        c.gridy = 16;
        c.gridwidth = 2;
        add(DateLbl , c);   

        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5,15,0,15);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 17;
        c.gridwidth = 4;
        c.gridheight = 3;
        NotesTxt.setLineWrap(true);
        NotesTxt.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(NotesTxt);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(200, 50));        
        add(areaScrollPane , c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,15,10,15);
        c.weightx = 0.6;
        c.gridx = 0;
        c.gridy = 20;
        c.gridwidth = 4;
        c.gridheight = 6;
        add(NotesLbl , c);   

        //Page Buttons
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.7;
        c.gridx = 0;
        c.gridy = 27;
        c.gridwidth = 1;
        add(new JLabel("             "), c);   
        c.gridx = 2;
        c.gridy = 27;
        c.gridwidth = 1;
        add(new JLabel("             "), c);   
             
        c.insets = new Insets(5,2,5,15);
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 27; 
        c.gridwidth = 1;
        add(centerBtn, c);

        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 27;
        c.gridwidth = 1;
        add(rightBtn, c);   
    }

    private void buildInsert() {
        int i = 0;
        CalendarTest cal = new CalendarTest();
        Calendar cl = cal.getCurrentDate();
        String lot = cal.generateLotNumber(cl);
        int stepID = 2;
        //Check that lotnumber is unique. 
        ReloadListEvent ev = new ReloadListEvent(this, ReloadListEvent.GET_ALL_LOTNUMBERS);

        if(reloadListListener != null){
            reloadListListener.ReloadListEventOccurred(ev);
        }
        
        List<String> lotNum = ev.getAllReloadsList();
        boolean keepLooking = true;

        while(keepLooking){
            for(; i < lotNum.size();i++){
                //System.out.println("LotNum["+i+"] of ["+lotNum.size()+"] = "+lotNum.get(i));
                if (lotNum.get(i).equals(lot)){
                    keepLooking = true;
                    break;
                }
            }     
            if(i >= (lotNum.size())){
                //System.out.println("No Match");
                keepLooking = false;
                i = 0;
            }else{
                //System.out.println("Matched LotNum["+i+"} = "+lotNum.get(--i));
                i = 0;
                lot = lot.substring(0,(lot.length()-3));
                lot = lot+"00"+stepID++;
                //System.out.println("lot = "+lot);
            }

        }
        al.setLotNumber(lot);
        
        LotNumberTxt.setText(lot);
        LotNumberTxt.setEditable(true);		
        CaliberTxt.setText("");
        CaliberTxt.setEditable(true);
        loadBullets();
        loadCases();
        loadPrimer();
        loadPowder();		
        LoadedTxt.setText("");
        LoadedTxt.setEditable(true);
        CaseLengthTxt.setText("");
        CaseLengthTxt.setEditable(true);
        CaseAOLTxt.setText("");
        CaseAOLTxt.setEditable(true);
        DateTxt.setText(cal.formatedString(cl));
        DateTxt.setEditable(true);
        ChronoIDTxt.setText("");
        ChronoIDTxt.setEditable(true);
        FirearmIDTxt.setText("");
        FirearmIDTxt.setEditable(true);
        CrimpTxt.setText("Factory");
        CrimpTxt.setEditable(true);
        NotesTxt.setText("");
        NotesTxt.setEditable(true);
        EmptyYesBtn.setSelected(false);
        EmptyNoBtn.setSelected(true);            
        EmptyYesBtn.addActionListener(this);
        EmptyNoBtn.addActionListener(this);

        centerBtn.setText("Insert");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
    }
	
	//builds the display items for the Update window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildUpdate() {
        int needed;
        LotNumberTxt.setText(al.getLotNumber());
        LotNumberTxt.setEditable(false);
        CaliberTxt.setText(al.getCaliber());
        CaliberTxt.setEditable(true);
        //Populat the bullets combobox and set it to the needed lot.
        loadBullets();
        needed = getLotNumberItem(bulletList, al.getBulletLotNumber());
        if(needed < 0)
            AlertDialog("Error can't find the Bullet needed");
        else
            bulletList.setSelectedIndex(needed);
        
        loadCases();
        needed = getLotNumberItem(caseList, al.getCaseLotNumber());
        if(needed < 0)
            AlertDialog("Error can't find the Case needed");
        else
            caseList.setSelectedIndex(needed);
        
        loadPrimer();
        needed = getLotNumberItem(primerList, al.getPrimerLotNumber());
        if(needed < 0)
            AlertDialog("Error can't find the Primer needed");
        else
            primerList.setSelectedIndex(needed);
        
        loadPowder();		
        needed = getLotNumberItem(powderList, al.getPowderLotNumber());
        if(needed < 0)
            AlertDialog("Error can't find the Powder needed");
        else
            powderList.setSelectedIndex(needed);
        
        LoadedTxt.setText(Integer.toString(al.getTimesLoaded()));
        LoadedTxt.setEditable(true);
        CaseLengthTxt.setText(ff.floatConvert(al.getCaseLength(), 2, 3));
        CaseLengthTxt.setEditable(true);
        CaseAOLTxt.setText(ff.floatConvert(al.getOverAllLength(), 2, 3));
        CaseAOLTxt.setEditable(true);
        DateTxt.setText(ct.formatedString(al.getLoadDate()));
        DateTxt.setEditable(true);
        ChronoIDTxt.setText(al.getChronoGraphDataID());
        ChronoIDTxt.setEditable(true);
        FirearmIDTxt.setText(al.getFireArmID());
        FirearmIDTxt.setEditable(true);
        CountTxt.setText(Integer.toString(al.getCount()));
        CountTxt.setEditable(true);
        CrimpTxt.setText(al.getCrimp());
        CrimpTxt.setEditable(true);
        NotesTxt.setText(al.getNotes());
        NotesTxt.setEditable(true);
        centerBtn.setText("Update");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
        PowderWeightTxt.setText(ff.floatConvert(al.getPowderWeight(), 2, 3));
        if(al.IsEmpty()){
            EmptyYesBtn.setSelected(true);
            EmptyNoBtn.setSelected(false);
        } else {
            EmptyYesBtn.setSelected(false);
            EmptyNoBtn.setSelected(true);            
        }
        EmptyYesBtn.addActionListener(this);
        EmptyNoBtn.addActionListener(this);
    }
	
    //Builds the display items for the Delete window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildDelete() {
        LotNumberTxt.setText(al.getLotNumber());
        LotNumberTxt.setEditable(false);
        CaliberTxt.setText(al.getCaliber());
        CaliberTxt.setEditable(false);
        BulletTxt.setText(bs.getBulletMaker()+" "+bs.getWeight()+" Grains, "+bs.getDescription());
        BulletTxt.setEditable(false);
        PowderTxt.setText(ff.floatConvert(al.getPowderWeight(), 4, 1)+" Grains, "+pw.getPowderMaker()+" "+pw.getPowderName());
        PowderTxt.setEditable(false);
        PrimerTxt.setText(pr.getPrimerMaker()+" "+pr.getPrimerSize());
        PrimerTxt.setEditable(false);
        CaseTxt.setText(cs.getCaseMaker());
        CaseTxt.setEditable(false);
        LoadedTxt.setText(Integer.toString(al.getTimesLoaded()));
        LoadedTxt.setEditable(false);
        CaseLengthTxt.setText(ff.floatConvert(al.getCaseLength(), 2, 3));
        CaseLengthTxt.setEditable(false);
        CaseAOLTxt.setText(ff.floatConvert(al.getOverAllLength(), 2, 3));
        CaseAOLTxt.setEditable(false);
        DateTxt.setText(ct.formatedString(al.getLoadDate()));
        DateTxt.setEditable(false);
        ChronoIDTxt.setText(al.getChronoGraphDataID());
        ChronoIDTxt.setEditable(true);
        FirearmIDTxt.setText(al.getFireArmID());
        FirearmIDTxt.setEditable(true);
        CrimpTxt.setText(al.getCrimp());
        CrimpTxt.setEditable(true);
        NotesTxt.setText(al.getNotes());
        NotesTxt.setEditable(false);
        centerBtn.setText("Delete");
        centerBtn.addActionListener(this);
        rightBtn.setText("Cancel");
        rightBtn.addActionListener(this);
        PowderWeightTxt.setText(ff.floatConvert(al.getPowderWeight(), 2, 3));
    }
	
    //Builds the display items for the View window. Everything is set by the case data that is loaded into the local Cases class.
    private void buildView() {
        LotNumberTxt.setText(al.getLotNumber());
        LotNumberTxt.setEditable(false);
        CaliberTxt.setText(al.getCaliber());
        CaliberTxt.setEditable(false);
        BulletTxt.setText(bs.getBulletMaker()+" "+bs.getWeight()+" Grains, "+bs.getDescription());
        BulletTxt.setEditable(false);
        PowderTxt.setText(ff.floatConvert(al.getPowderWeight(), 4, 1)+" Grains, "+pw.getPowderMaker()+" "+pw.getPowderName());
        PowderTxt.setEditable(false);
        PrimerTxt.setText(pr.getPrimerMaker()+" "+pr.getPrimerSize());
        PrimerTxt.setEditable(false);
        CaseTxt.setText(cs.getCaseMaker());
        CaseTxt.setEditable(false);
        LoadedTxt.setText(Integer.toString(al.getTimesLoaded()));
        LoadedTxt.setEditable(false);
        CaseLengthTxt.setText(ff.floatConvert(al.getCaseLength(), 2, 3));
        CaseLengthTxt.setEditable(false);
        CaseAOLTxt.setText(ff.floatConvert(al.getOverAllLength(), 2, 3));
        CaseAOLTxt.setEditable(false);
        DateTxt.setText(ct.formatedString(al.getLoadDate()));
        DateTxt.setEditable(false);
        NotesTxt.setText(al.getNotes());
        NotesTxt.setEditable(false);
        centerBtn.setText("OK");
        centerBtn.addActionListener(this);
        rightBtn.setText("Save");
        rightBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent actEvt) {
    	if(actEvt.getSource() == centerBtn) {
            switch(winType) {
                case INSERT: //run the insert process.boolean
                    if(this.confirmDialog("Insert Ammo Lot Number "+al.getLotNumber()) == 1) {
                        if(createAmmo()) {
                            ReloadListEvent ev = new ReloadListEvent(this, al, ReloadListEvent.INSERT_LOTNUMBER);
                            if(reloadListListener != null)
                                reloadListListener.ReloadListEventOccurred(ev);
                            if(this.confirmDialog("Save the reloading label for "+al.getLotNumber()) == 1) {
                                RelaodingLabelPDF rlPDF = new RelaodingLabelPDF(al);
                                fillOutDependants();
                                rlPDF.setBullet(bs);
                                rlPDF.setCase(cs);
                                rlPDF.setPowder(pw);
                                rlPDF.setPrimer(pr);
                                rlPDF.saveReloadningLabel("");
                            }
                            exit();
                        } else {
                            AlertDialog("Error: Something is wrong with the reload. \n"+al);
                        }						
                    }
                    break;
                case UPDATE: //run the update process.
                    if(this.confirmDialog("Update Ammo Lot Number "+al.getLotNumber()) == 1) {
                        if(createAmmo()) {
                            ReloadListEvent ev = new ReloadListEvent(this, al, ReloadListEvent.UPDATE_LOTNUMBER);
                            if(reloadListListener != null)
                                reloadListListener.ReloadListEventOccurred(ev); 
                            if(this.confirmDialog("Save the reloading label for "+al.getLotNumber()) == 1) {
                                RelaodingLabelPDF rlPDF = new RelaodingLabelPDF(al);
                                rlPDF.setBullet(bs);
                                rlPDF.setCase(cs);
                                rlPDF.setPowder(pw);
                                rlPDF.setPrimer(pr);
                                rlPDF.saveReloadningLabel("");
                            }
                            exit();
                        } else {
                            AlertDialog("Error: Something is wrong with the reload. \n"+al);
                        }						
                    }
                    break;
                case DELETE: //run the Delete process.
                    if(this.confirmDialog("Delete Ammo Lot Number "+al.getLotNumber()) == 1) {
                        ReloadListEvent ev = new ReloadListEvent(this, al, ReloadListEvent.DELETE_LOTNUMBER);
                        if(reloadListListener != null)
                            reloadListListener.ReloadListEventOccurred(ev);                            
                    }
                    exit();
                    break;
                case VIEW: //run the view process.
                    exit();
                    break;
                default:
                    AlertDialog("The window type has not been set for AmmoListGUI.");
                    exit();
                    break;	    		
            }
    	} else if(actEvt.getSource() == rightBtn) {
            if(winType == VIEW){
                if(this.confirmDialog("Save the reloading label for "+al.getLotNumber()) == 1) {
                    RelaodingLabelPDF rlPDF = new RelaodingLabelPDF(al);
                    rlPDF.setBullet(bs);
                    rlPDF.setCase(cs);
                    rlPDF.setPowder(pw);
                    rlPDF.setPrimer(pr);
                    rlPDF.saveReloadningLabel("");
                }
            }
            exit();   
        } else if(actEvt.getSource() == EmptyYesBtn) {
            EmptyYesBtn.setSelected(true);
            EmptyNoBtn.setSelected(false);    			
    	} else if(actEvt.getSource() == EmptyNoBtn) { 
            EmptyYesBtn.setSelected(false);
            EmptyNoBtn.setSelected(true);  
    	}
    }
    
    public void setCloseType(int cl) {
        this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
        return this.getDefaultCloseOperation();
    }

    public void setAmmoList(ReloadList a) {
        al = a;
        pw.setPowderLotNumber(al.getPowderLotNumber());
        bs.setLotNumber(al.getBulletLotNumber());
        cs.setLotNumber(al.getCaseLotNumber());
        pr.setLotNumber(al.getPrimerLotNumber());
    }

    public ReloadList getAmmoList() {
        return al;
    }

    public void setBullets(Bullets b) {
        bs = b;
    }

    public Bullets getBullets() {
        return bs;
    }

    public void Cases(Cases c) {
        cs = c;
    }

    public Cases getCases() {
        return cs;
    }

    public void setPowder(Powder p) {
        pw = p;
    }

    public Powder getPowder() {
        return pw;
    }
	
    public void setPrimer(Primer p) {
        pr = p;
    }

    public Primer getPrimer() {
        return pr;
    }

    private boolean createAmmo() {
        al.setLotNumber(LotNumberTxt.getText());
        // get date and convert it to a correct date for the database.
        String dateLoaded = DateTxt.getText();
        CalendarTest clt = new CalendarTest();
        clt.convertDate(dateLoaded);
        dateLoaded = clt.toString();
        al.setLoadDate(dateLoaded);
        al.setCaliber(CaliberTxt.getText());
        if(BulletTxt.getText().isBlank()){
            String bln = bulletList.getSelectedItem().toString();
            int spot = bln.indexOf(",");
            bln = bln.substring(0,spot);
            al.setBulletLotNumber(bln);
        } else {
            al.setBulletLotNumber(BulletTxt.getText());
        }
        if(PowderTxt.getText().isBlank()){
            String pln = powderList.getSelectedItem().toString();
            int spot = pln.indexOf(",");
            pln = pln.substring(0,spot);
            al.setPowderLotNumber(pln);
        } else {
            al.setPowderLotNumber(PowderTxt.getText());
        }
        if(CaseTxt.getText().isBlank()){
            String cln = caseList.getSelectedItem().toString();
            int spot = cln.indexOf(",");
            cln = cln.substring(0,spot);
            al.setCaseLotNumber(cln);
        } else {
            al.setCaseLotNumber(CaseTxt.getText());
        }
        if(PrimerTxt.getText().isBlank()){
            String prln = primerList.getSelectedItem().toString();
            int spot = prln.indexOf(",");
            prln = prln.substring(0,spot);
            al.setPrimerLotNumber(prln);
        } else {
            al.setPrimerLotNumber(PrimerTxt.getText());
        }
        
        if(PowderWeightTxt.getText().isBlank())
            return false;
        al.setPowderWeight(PowderWeightTxt.getText());
        al.setTimesLoaded(LoadedTxt.getText());
        al.setCaseLength(CaseLengthTxt.getText());
        al.setOverAllLength(CaseAOLTxt.getText());
        al.setCount(CountTxt.getText());
        al.setNotes(NotesTxt.getText());
        al.setFireArmID(FirearmIDTxt.getText());
        al.setChronoGraphDataID(ChronoIDTxt.getText());
        al.setCrimp(CrimpTxt.getText());
        if(EmptyYesBtn.isSelected())
            al.setIsEmpty(false);
        else
            al.setIsEmpty(true);
            
        al.setManufacturer("Todd Brenneman");
    
        return al.isValid();
    }

    private void exit() {
        switch (this.getCloseType()) {
            case WindowConstants.EXIT_ON_CLOSE:
                System.exit(0);
            case WindowConstants.DISPOSE_ON_CLOSE:
                this.setVisible(false);
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
    
    private void fillOutDependants() {
        PowderEvent pwev = new PowderEvent(this, pw, PowderEvent.GET_LOTNUMBER);
        if(powderListener != null)
            powderListener.PowderEventOccurred(pwev);
        pw = pwev.getPowder();
        
        PrimerEvent prev = new PrimerEvent(this, pr, PrimerEvent.GET_LOTNUMBER);
        if(primerListener != null)
            primerListener.PrimerEventOccurred(prev);
        pr = prev.getPrimer();

        CasesEvent csev = new CasesEvent(this, cs, CasesEvent.GET_LOTNUMBER);
        if(casesListener != null)
            casesListener.CasesEventOccurred(csev);
        cs = csev.getCases();

        BulletsEvent bsev = new BulletsEvent(this, bs, BulletsEvent.GET_LOTNUMBER, true);
        if(bulletsListener != null)
            bulletsListener.BulletsEventOccurred(bsev);
            
        bs = bsev.getBullet();
    }
    
    private void loadPowder() {
    	List<String> ac;
    	powderList = new JComboBox<String>();
        PowderEvent ev1;
        
        if(winType == INSERT){
            ev1 = new PowderEvent(this, PowderEvent.GET_ALL_LOTNUMBERS, true);
        }else{
            ev1 = new PowderEvent(this, PowderEvent.GET_ALL_LOTNUMBERS, false);
        }
        if(powderListener != null)
            powderListener.PowderEventOccurred(ev1);

        ac = ev1.getPowderList();
        Iterator<String> it = ac.iterator(); 
        while(it.hasNext()) {
            powderList.addItem(it.next());
        }
    }
	    
    private void loadPrimer() {
    	List<String> ac;
    	primerList = new JComboBox<String>();
        PrimerEvent ev;
        
        if(winType == INSERT)
            ev = new PrimerEvent(this, PrimerEvent.GET_ALL_LOTNUMBERS, true);
        else
            ev = new PrimerEvent(this, PrimerEvent.GET_ALL_LOTNUMBERS, false);
            
        if(primerListener != null)
            primerListener.PrimerEventOccurred(ev);
        
        ac = ev.getPrimerList();
        Iterator<String> it = ac.iterator(); 
        while(it.hasNext()) {
            primerList.addItem(it.next());
        }
    }

    private void loadCases() {
    	List<String> ac;
    	caseList = new JComboBox<String>();
        CasesEvent ev; 

        if(winType == INSERT)
            ev = new CasesEvent(this, CasesEvent.GET_ALL_CASE_LOTNUMBERS, true);
        else
            ev = new CasesEvent(this, CasesEvent.GET_ALL_CASE_LOTNUMBERS, false);
        
        if(casesListener != null)
            casesListener.CasesEventOccurred(ev);

        ac = ev.getCasesList();
        Iterator<String> it = ac.iterator(); 
        while(it.hasNext()) {
            caseList.addItem(it.next());
        }
    }

    private void loadBullets() {
    	List<String> ac;
    	bulletList = new JComboBox<String>();
        BulletsEvent ev;
        
        if(winType == INSERT)
            ev = new BulletsEvent(this, BulletsEvent.GET_ALL_LOTNUMBERS, true);
        else
            ev = new BulletsEvent(this, BulletsEvent.GET_ALL_LOTNUMBERS, false);
 
        if(bulletsListener != null)
            bulletsListener.BulletsEventOccurred(ev);
        
        ac = ev.getAllBulletsList();
        Iterator<String> it = ac.iterator(); 
        while(it.hasNext()) {
            bulletList.addItem(it.next());
        }
    }
    
    public int getLotNumberItem(JComboBox jcb, String ln){
        int items = jcb.getItemCount();
        int found = -1;
        
        for(int i=0; i < items; i++){
            String it = jcb.getItemAt(i).toString();
            String lot = it.substring(0, it.indexOf(","));
            //System.out.println("Lot = "+lot+", LN = "+ln);
            if(ln.equalsIgnoreCase(lot)){
                found = i;
                break;
            }
        }
        return found;
    }

    public void setReloadListListener(ReloadListListener reloadListListener) {
        this.reloadListListener = reloadListListener;
    }

    public void setBulletsListener(BulletsListener bulletsListener) {
        this.bulletsListener = bulletsListener;
    }

    public void setCasesListener(CasesListener casesListener) {
        this.casesListener = casesListener;
    }

    public void setPrimerListener(PrimerListener primerListener) {
        this.primerListener = primerListener;
    }

    public void setPowderListener(PowderListener powderListener) {
        this.powderListener = powderListener;
    }

 }

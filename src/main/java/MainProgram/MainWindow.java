/****************************************************************************************************************
 * MainWindow.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the main GUI program. It loads all the tables, assembles them into the tabbed panes and displays them *
 * to the user. 																								*
 * 																												*
 ***************************************************************************************************************/

package MainProgram;

import BulletsEvent.BulletsQuery;
import CasesEvent.CasesQuery;
import BaseClasses.DataBaseConn;
import BaseClasses.DataBaseConnData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import BaseClasses.SettingsXML;
import BulletMoldEvent.BulletMold;
import BulletMoldEvent.BulletMoldEvent;
import BulletMoldEvent.BulletMoldGUI;
import BulletMoldEvent.BulletMoldListener;
import BulletMoldEvent.BulletMoldQuery;
import BulletMoldEvent.BulletMoldTableMain;
import BulletsEvent.Bullets;
import BulletsEvent.BulletsEvent;
import BulletsEvent.BulletsGUI;
import BulletsEvent.BulletsListener;
import BulletsEvent.BulletsTableMain;
import CasesEvent.Cases;
import CasesEvent.CasesEvent;
import CasesEvent.CasesGUI;
import CasesEvent.CasesListener;
import CasesEvent.CasesTableMain;
import ChronoDataEvent.ChronoDataEvent;
import ChronoDataEvent.ChronoDataListener;
import ChronoDataEvent.ChronographData;
import ChronoDataEvent.ChronographDataQuery;
import ChronoDataEvent.ChronographDataTableMain;
import ChronoDataEvent.ChronographGUI;
import FirearmsEvent.Firearm;
import FirearmsEvent.FirearmEvent;
import FirearmsEvent.FirearmListener;
import FirearmsEvent.FirearmQuery;
import FirearmsEvent.FirearmsGUI;
import FirearmsEvent.FirearmsTableMain;
import ManufacturedAmmoEvent.ManufacturedAmmo;
import ManufacturedAmmoEvent.ManufacturedAmmoEvent;
import ManufacturedAmmoEvent.ManufacturedAmmoGUI;
import ManufacturedAmmoEvent.ManufacturedAmmoListener;
import ManufacturedAmmoEvent.ManufacturedAmmoQuery;
import ManufacturedAmmoEvent.ManufacturedAmmoTableMain;
import PowderEvent.Powder;
import PowderEvent.PowderEvent;
import PowderEvent.PowderGUI;
import PowderEvent.PowderListener;
import PowderEvent.PowderQuery;
import PowderEvent.PowderTableMain;
import PrimerEvent.Primer;
import PrimerEvent.PrimerEvent;
import PrimerEvent.PrimerGUI;
import PrimerEvent.PrimerListener;
import PrimerEvent.PrimerQuery;
import PrimerEvent.PrimerTableMain;
import ReloadEvent.ReloadListQuery;
import ReloadEvent.ReloadListTableMain;
import ReloadEvent.ReloadListGUI;
import ReloadEvent.ReloadList;
import ReloadEvent.ReloadListEvent;
import ReloadEvent.ReloadListListener;
import ReloadingDiesEvent.ReloadingDies;
import ReloadingDiesEvent.ReloadingDiesEvent;
import ReloadingDiesEvent.ReloadingDiesGUI;
import ReloadingDiesEvent.ReloadingDiesListener;
import ReloadingDiesEvent.ReloadingDiesQuery;
import ReloadingDiesEvent.ReloadingDiesTableMain;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainWindow  extends JFrame implements ActionListener {
    public static final int NOT_SET = -1;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int VIEW = 3;
	
    //Default windows size.
    private final int XSIZE = 1500;
    private final int YSIZE = 585;
	
    //Variables for the menu on the main GUI. 
    private JMenuBar menuBar;
    private JMenu fileMenu, helpMenu, viewMenu, searchMenu, filterMenu, filterMenuItem1, filterMenuItem2, filterMenuItem3, filterMenuItem4;
    private JMenuItem menuItem, settingMenuItem, exitMenuItem, aberrMenuItem, insertMenuItem, searchMenuItem1, clearFilterMenuItem;
    private JMenuItem searchMenuItem2;
    private JMenuItem[] filterMenuArray;
    private JRadioButtonMenuItem viewMenuItemRB,updateMenuItemRB,deleteMenuItemRB, isEmptyMenuItemRB;
    private boolean[] isEmpytArr= {false,false,false,false,false,false,false,false,false};
    
    //Database object for querries.
    private DataBaseConnData dbcd;
    private DataBaseConn dbc;
    private BulletsQuery bsq;
    private CasesQuery csq;
    private PrimerQuery prq;
    private PowderQuery pwq;
    private ManufacturedAmmoQuery maq;
    private BulletMoldQuery bmq;
    private FirearmQuery fmq;
    private ReloadingDiesQuery rdq;
    private ReloadListQuery rlq;
    private ChronographDataQuery cdq;
    
    //The tables for the base class items.
    private BulletsTableMain bulletPane;
    private ManufacturedAmmoTableMain ammoPane;
    private ReloadListTableMain reloadsPane;
    private CasesTableMain casePane = new CasesTableMain();
    private PowderTableMain powderPane = new PowderTableMain();
    private PrimerTableMain primerPane = new PrimerTableMain();
    private FirearmsTableMain gunPane = new FirearmsTableMain();
    private ChronographDataTableMain chronoPane = new ChronographDataTableMain();
    private ReloadingDiesTableMain reloadPane;
    private BulletMoldTableMain bulletMoldPane;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private SettingsXML sxml = new SettingsXML();
    private String filepath;

    public MainWindow() {
    	this.setTitle("AmmoDatabase ");
	this.setResizable(false);
	this.setSize(XSIZE, YSIZE);
	this.makeIcon();

        try {
            //Read the user settings for the program.
            sxml.readData();
            //Create the object querries.
            dbcd = new DataBaseConnData();
            dbc = new DataBaseConn(dbcd);
            bsq = new BulletsQuery(dbc);
            csq = new CasesQuery(dbc);
            prq = new PrimerQuery(dbc);
            pwq = new PowderQuery(dbc);
            maq = new ManufacturedAmmoQuery(dbc);
            bmq = new BulletMoldQuery(dbc);
            fmq = new FirearmQuery(dbc);
            rdq = new ReloadingDiesQuery(dbc);
            rlq = new ReloadListQuery(dbc);
            cdq = new ChronographDataQuery(dbc);
            this.setIgnoreEmpty();                        
            
            bulletPane = new BulletsTableMain(bsq);
            casePane = new CasesTableMain(csq);
            primerPane = new PrimerTableMain(prq);
            powderPane = new PowderTableMain(pwq);
            ammoPane = new ManufacturedAmmoTableMain(maq);
            bulletMoldPane = new BulletMoldTableMain(bmq);
            gunPane = new FirearmsTableMain(fmq);
            reloadPane = new ReloadingDiesTableMain(rdq);
            reloadsPane = new ReloadListTableMain(rlq, bsq, csq, prq, pwq);

            
            filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);

            //Creates the icon for the AmmoList
            String ammoIconPath = filepath + "ammo.jpg";
            File myObj = new File(ammoIconPath);
            BufferedImage ammoIMG = ImageIO.read(myObj);
            ImageIcon ammoIcon = new ImageIcon(ammoIMG);

            //Creates the icon for the BulletTable
	    String bulletIconPath = filepath + "Bullets.jpg";
            myObj = new File(bulletIconPath);
            BufferedImage bulletIMG = ImageIO.read(myObj);
	    ImageIcon bulletIcon = new ImageIcon(bulletIMG);
	        
            //Creates the icon for the CasesTable
	    String casesIconPath = filepath + "cases.jpg";
            myObj = new File(casesIconPath);
            BufferedImage casesIMG = ImageIO.read(myObj);
	    ImageIcon casesIcon = new ImageIcon(casesIMG);
	        
            //Creates the icon for the PrimerTable
	    String primerIconPath = filepath + "primer.jpg";
            myObj = new File(primerIconPath);
            BufferedImage primerIMG = ImageIO.read(myObj);
            ImageIcon primerIcon = new ImageIcon(primerIMG);
	           
            //Creates the icon for the PowderTable
	    String powderIconPath = filepath + "powder.jpg";
            myObj = new File(powderIconPath);
            BufferedImage powderIMG = ImageIO.read(myObj);
            ImageIcon powderIcon = new ImageIcon(powderIMG);
	
            //Creates the icon for the ChronographTable
	    String chronoIconPath = filepath + "chrono.jpg";
            myObj = new File(chronoIconPath);
            BufferedImage chronoIMG = ImageIO.read(myObj);
            ImageIcon chronoIcon = new ImageIcon(chronoIMG);
	        
            //Creates the icon for the FirearmsTable
	    String firearmIconPath = filepath + "firearm.jpg";
            myObj = new File(firearmIconPath);
            BufferedImage firearmIMG = ImageIO.read(myObj);
            ImageIcon firearmIcon = new ImageIcon(firearmIMG);
	 
            //Creates the icon for the ReloadingDiesTable
	    String rdIconPath = filepath + "ReloadingDies.jpg";
            myObj = new File(rdIconPath);
            BufferedImage rdIMG = ImageIO.read(myObj);
            ImageIcon rdIcon = new ImageIcon(rdIMG);
	 
            //Creates the icon for the BulletMoldsTable
	    String bmIconPath = filepath + "BulletMold.jpg";
            myObj = new File(bmIconPath);
            BufferedImage bmIMG = ImageIO.read(myObj);
            ImageIcon bmIcon = new ImageIcon(bmIMG);
	        
            tabbedPane.addTab("Bullets", bulletIcon, bulletPane.getPanel(), "Bullets Panel");
            tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	
            tabbedPane.addTab("Cases", casesIcon, casePane.getPanel(), "Cases Panel");
            tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
	
            tabbedPane.addTab("Primer", primerIcon, primerPane.getPanel(), "Primer Panel");
            tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
	
            tabbedPane.addTab("Powder", powderIcon, powderPane.getPanel(), "Powder Panel");
            tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
	
            tabbedPane.addTab("Ammunation", ammoIcon, ammoPane.getPanel(), "Ammunition Panel");
            tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
	
            tabbedPane.addTab("Bullet Molds", bmIcon, bulletMoldPane.getPanel(), "Bullet Molds Panel");
            tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);

            tabbedPane.addTab("Reloading Dies", rdIcon, reloadPane.getPanel(), "Reloading Dies Panel");
            tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);
	
            tabbedPane.addTab("Firearm", firearmIcon, gunPane.getPanel(), "Firearms Panel");
            tabbedPane.setMnemonicAt(7, KeyEvent.VK_8);
	
            tabbedPane.addTab("Reloads", ammoIcon, reloadsPane.getPanel(), "Reloads Panel");
            tabbedPane.setMnemonicAt(8, KeyEvent.VK_9);
	
            tabbedPane.addTab("Chronograph", chronoIcon, chronoPane.getPanel(), "Chronograph Panel");
            tabbedPane.setMnemonicAt(9, KeyEvent.VK_A);
	
            this.add(tabbedPane);
            setAllWinType(VIEW);

            //Create the menu bar.
            menuBar = new JMenuBar();

            //Build the first menu.
            fileMenu = new JMenu("File");
            fileMenu.setMnemonic(KeyEvent.VK_F);
            fileMenu.getAccessibleContext().setAccessibleDescription("The File Menu");
            menuBar.add(fileMenu);

            //a group of JMenuItems
            menuItem = new JMenuItem("Statics", KeyEvent.VK_S);
            menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
            menuItem.getAccessibleContext().setAccessibleDescription("Produces a dialog with statics about the database");
            menuItem.addActionListener(this);
            fileMenu.add(menuItem);

            settingMenuItem = new JMenuItem("Settings");
            settingMenuItem.setMnemonic(KeyEvent.VK_S);
            settingMenuItem.addActionListener(this);
            fileMenu.add(settingMenuItem);

            fileMenu.addSeparator();

            exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.setMnemonic(KeyEvent.VK_E);
            exitMenuItem.addActionListener(this);
            fileMenu.add(exitMenuItem);

            //Build second menu in the menu bar.
            viewMenu = new JMenu("View");
            viewMenu.setMnemonic(KeyEvent.VK_N);
            viewMenu.getAccessibleContext().setAccessibleDescription("Set the window function.");
            menuBar.add(viewMenu);
            
            buildSearchMenu();
            buildFilterMenu();

            insertMenuItem = new JMenuItem("Insert");
            insertMenuItem.setMnemonic(KeyEvent.VK_I);
            insertMenuItem.addActionListener(this);
            menuBar.add(insertMenuItem);
            
            ButtonGroup group = new ButtonGroup();
            viewMenuItemRB = new JRadioButtonMenuItem("View");
            viewMenuItemRB.setSelected(true);
            viewMenuItemRB.setMnemonic(KeyEvent.VK_V);
            viewMenuItemRB.addActionListener(this);
            group.add(viewMenuItemRB);
            viewMenu.add(viewMenuItemRB);

            updateMenuItemRB = new JRadioButtonMenuItem("Update");
            updateMenuItemRB.setMnemonic(KeyEvent.VK_U);
            updateMenuItemRB.addActionListener(this);
            group.add(updateMenuItemRB);
            viewMenu.add(updateMenuItemRB);

            deleteMenuItemRB = new JRadioButtonMenuItem("Delete");
            deleteMenuItemRB.setMnemonic(KeyEvent.VK_D);
            deleteMenuItemRB.addActionListener(this);
            group.add(deleteMenuItemRB);
            viewMenu.add(deleteMenuItemRB);

            this.setJMenuBar(menuBar);
		            
            helpMenu = new JMenu("Help");
            helpMenu.setMnemonic(KeyEvent.VK_H);
            helpMenu.getAccessibleContext().setAccessibleDescription("Help menu.");

            aberrMenuItem = new JMenuItem("Aberr", KeyEvent.VK_A);
            aberrMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
            aberrMenuItem.getAccessibleContext().setAccessibleDescription("Produces a dialog with statics about the database");
            aberrMenuItem.addActionListener(this);
            helpMenu.add(aberrMenuItem);
		
            menuBar.add(helpMenu);

        } catch (Exception e) {
            this.AlertDialog("Error in main window: "+e);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
	
    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            filepath = filepath + "ammo.jpg";
            File myObj = new File(filepath);
	    BufferedImage image = ImageIO.read(myObj);
	    this.setIconImage(image);
	} catch (IOException e) {
	    AlertDialog("Error creating icon: "+e);
	}
    }   
	
    //set the type of windows to be shown on all dialog boxes.
    private void setAllWinType(int wt) {
        reloadsPane.setWinType(wt);
	chronoPane.setWinType(wt);
	ammoPane.setWinType(wt);
	bulletPane.setWinType(wt);
	casePane.setWinType(wt);
	powderPane.setWinType(wt);
	primerPane.setWinType(wt);
	gunPane.setWinType(wt);
	reloadPane.setWinType(wt);
	bulletMoldPane.setWinType(wt);
        gunPane.setWinType(wt);
    }
    
      private void setIgnoreEmpty(){
        Boolean ie;
        if(sxml.getIgnoreEmpty().equalsIgnoreCase("false"))
            ie = false;
        else
            ie = true;

        bsq.setIgnoreEmpty(ie);
        csq.setIgnoreempty1(ie);
        prq.setIgnoreEmpty1(ie);
        pwq.setIgnoreempty1(ie);
        maq.setNotEmpty(ie);
        rlq.setIgnoreEmpty(ie);       
    }

    //Alerts the user if error happen.
    private void AlertDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(this, t, "ALERT!", JOptionPane.ERROR_MESSAGE);
    }
   	
    @Override
    //Watch the menu items and take the correct action.
    public void actionPerformed(ActionEvent actEvt) {
	//The aberr menu item is select show the aberr dialog box. 
        if(actEvt.getSource() == aberrMenuItem) {
            AboutGUI AGUI = new AboutGUI();
            AGUI.setCloseType(DISPOSE_ON_CLOSE);
            AGUI.setVisible(true);
	}
        if(actEvt.getSource() == settingMenuItem) {
            SettingsGUI SGUI = new SettingsGUI();
            SGUI.setCloseType(DISPOSE_ON_CLOSE);
            SGUI.buildWindow();
            SGUI.setVisible(true);
	}
        if(actEvt.getSource() == menuItem) {
            DataBaseConnData dbcd1 = new DataBaseConnData();
            DataBaseConn dbc1 = new DataBaseConn(dbcd1);
            StatisticsGUI STGUI = new StatisticsGUI(dbc1);
            STGUI.setCloseType(DISPOSE_ON_CLOSE);
	}
        if(actEvt.getSource() == exitMenuItem) {
            System.exit(0);
	}
	//The view item is set in the menu.
	if(actEvt.getSource() == viewMenuItemRB) {
            viewMenu.setText("View");
	    setAllWinType(VIEW);
	}
	//The update item was selected.
	if(actEvt.getSource() == updateMenuItemRB) {
            viewMenu.setText("Update");
	    setAllWinType(UPDATE);
        }
	//The update item was selected.
	if(actEvt.getSource() == deleteMenuItemRB) {
            viewMenu.setText("Delete");
	    setAllWinType(DELETE);
        }
        
	//The insert item was selected.
	if(actEvt.getSource() == insertMenuItem) {
            //viewMenu.setText("Insert");
	    //setAllWinType(INSERT);
            switch(tabbedPane.getSelectedIndex()){
                case 0:
                    BulletsGUI bgui = new BulletsGUI(new Bullets());
                    bgui.buildWindow(INSERT);
                    bgui.setBulletsListener(new BulletsListener(){ 
                        @Override
                        public void BulletsEventOccurred(BulletsEvent e) {
                            if(e.getEventType() == BulletsEvent.INSERT_LOTNUMBER) {                               
                                try{
                                    bsq.connect();
                                    bsq.insertLotNumber(e.getBullet());
                                    bsq.close();
                                }catch(Exception q) {
                                    AlertDialog("BulletsTableMain: Database error in BulletsTableMain:insertBullet()): "+q);
                                }
                                //AlertDialog("Insert Bullet into database\n"+e.getBullet().toString());
                                bulletPane.getPanel().addBullet(e.getBullet());
                                bulletPane.getPanel().updateTable();
                            }
                        }
                    }); 
                    bgui.setVisible(true);
                    break;
                case 1:
                    CasesGUI cgui = new CasesGUI();
                    cgui.setCases(new Cases());
                    cgui.buildWindow(INSERT);
                    cgui.setCasesListener(new CasesListener(){
                        @Override
                        public void CasesEventOccurred(CasesEvent e) {
                            if(e.getEventType() == CasesEvent.INSERT_LOTNUMBER) {
                                try{
                                    csq.connect();
                                    csq.insertLotNumber(e.getCases());
                                    csq.close();
                                } catch(Exception q) {
                                    AlertDialog("Database error in CasesTableMain:insertBullet()): "+q);
                                }
                                casePane.getPanel().addCase(e.getCases());
                                casePane.getPanel().updateTable();
                            }
                        }
                    });
                    cgui.setVisible(true);
                    break;
                case 2:
                    PrimerGUI prgui = new PrimerGUI();
                    prgui.setPrimer(new Primer());
                    prgui.buildWindow(INSERT);
                    prgui.setPrimerListener(new PrimerListener(){
                        @Override
                        public void PrimerEventOccurred(PrimerEvent e) {
                            if(e.getEventType() == PrimerEvent.INSERT_LOTNUMBER) {
                                try{
                                    prq.connect();
                                    prq.insertLotNumber(e.getPrimer());
                                    prq.close();
                                } catch (Exception q) {
                                    AlertDialog("PrimerTableMain: Database error in PrimerTableMain:insertprimer(): "+q);
                                }
                                primerPane.getPanel().addPrimer(e.getPrimer());
                                primerPane.getPanel().updateTable();
                            }
                        }
                    });
                    prgui.setVisible(true);
                    break;

                case 3:
                    PowderGUI pwgui = new PowderGUI();
                    pwgui.setPowder(new Powder());
                    pwgui.buildWindow(INSERT);
                    pwgui.setPowderListener(new PowderListener() {
                        @Override
                        public void PowderEventOccurred(PowderEvent e) {
                            if (e.getEventType() == PowderEvent.INSERT_LOTNUMBER) {
                                try{
                                    pwq.connect();
                                    pwq.insertLotNumber(e.getPowder());
                                    pwq.close();
                                }catch(Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                powderPane.getPanel().addPowder(e.getPowder());
                                powderPane.getPanel().updateTable();
                            }
                        }
                    });
                    pwgui.setVisible(true);
                    break;
                case 4:                
                    ManufacturedAmmoGUI mgui = new ManufacturedAmmoGUI();
                    mgui.setManufacturedAmmo(new ManufacturedAmmo());
                    mgui.buildWindow(INSERT);
                    mgui.setManufacturedAmmoListener(new ManufacturedAmmoListener(){ 
                        @Override
                        public void ManufacturedAmmoEventOccurred(ManufacturedAmmoEvent e) {
                            if (e.getEventType() == ManufacturedAmmoEvent.INSERT_LOTNUMBER) {
                                    try{
                                        maq.connect();
                                        maq.insertLotNumber(e.getManufacturedAmmo());
                                        maq.close();
                                    }catch(Exception q) {
                                        AlertDialog("ManufacturedAmmoTableMain: Database error in ManufacturedAmmoTableMain:insertBullet()): "+q);
                                    }
                                    //AlertDialog("Insert Bullet into database\n"+e.getBullet().toString());
                                    ammoPane.getPanel().addAmmo(e.getManufacturedAmmo());
                                    ammoPane.getPanel().updateTable();
                            }

                        }
                    });
                    mgui.setVisible(true); 
                    break;
                case 5:
                    BulletMoldGUI bmgui = new BulletMoldGUI();
                    bmgui.setBm(new BulletMold());
                    bmgui.buildWindow(INSERT);
                    bmgui.setBulletMoldListener(new BulletMoldListener() {
                        @Override
                        public void BulletMoldEventOccurred(BulletMoldEvent e) {
                            if(e.getEventType() == BulletMoldEvent.INSERT_LOTNUMBER) {
                                try{
                                bmq.connect();
                                bmq.insertMoldID(e.getBulletMold());
                                bmq.close();
                                } catch (Exception q) {
                                    AlertDialog("Database error in getAll(): "+q);
                                }
                                //AlertDialog("Insert Bullet into database\n"+e.getBulletMold().toString());
                                bulletMoldPane.getPanel().addBulletMold(e.getBulletMold());
                                bulletMoldPane.getPanel().updateTable();
                            }

                        }
                    });
                    bmgui.setVisible(true);
                    break;
                case 6:
                    ReloadingDiesGUI rlgui = new ReloadingDiesGUI();
                    rlgui.setReloadingDie(new ReloadingDies());
                    rlgui.buildWindow(INSERT);
                    rlgui.setReloadingDiesListener(new ReloadingDiesListener(){ 
                        @Override
                        public void ReloadingDiesEventOccurred(ReloadingDiesEvent e) {
                            if(e.getEventType() == ReloadingDiesEvent.INSERT_LOTNUMBER) {
                                try{
                                    rdq.connect();
                                    rdq.insertID(e.getReloadingDie());
                                    rdq.close();
                                }catch(Exception q) {
                                    AlertDialog("ReloadingDiesTableMain: Database error in ReloadingDiesTableMain:insertReloadingDie()): "+q);
                                }
                                //AlertDialog("Insert ReloadingDie into database\n"+e.getReloadingDie().toString());
                                reloadPane.getPanel().addDie(e.getReloadingDie());
                                reloadPane.getPanel().updateTable();
                            }

                        }
                    });
                    rlgui.setVisible(true);
                    break;
                case 7:
                    FirearmsGUI fgui = new FirearmsGUI();
                    fgui.setFirearm(new Firearm());
                    fgui.buildWindow(INSERT);
                    fgui.setFirearmListener(new FirearmListener(){ 
                        @Override
                        public void FirearmEventOccurred(FirearmEvent e) {
                            if (e.getEventType() == FirearmEvent.INSERT_LOTNUMBER) {
                                try{
                                    fmq.connect();
                                    fmq.insertFirearm(e.getFirearm());
                                    fmq.close();
                                }catch(Exception q) {
                                    AlertDialog("FirearmTableMain: Database error in FirearmTableMain:insertBullet()): "+q);
                                }
                                //AlertDialog("Insert Bullet into database\n"+e.getFirearm().toString());
                                gunPane.getPanel().addFirearm(e.getFirearm());
                                gunPane.getPanel().updateTable();
                            }
                        }
                    });
                    fgui.setVisible(true);
                    break;
                case 8:
                    ReloadListGUI rgui = new ReloadListGUI();
                    rgui.setAmmoList(new ReloadList());
                    rgui.setReloadListListener(new ReloadListListener(){
                        @Override
                        public void ReloadListEventOccurred(ReloadListEvent e) {
                            switch(e.getEventType()) {
                                case ReloadListEvent.INSERT_LOTNUMBER:
                                    try{
                                        rlq.connect();
                                        rlq.insertLotNumber(e.getReloadList());
                                        rlq.close();
                                    }catch(Exception q) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:insertLotNumber()): "+q);
                                    }
                                    reloadsPane.getPanel().addReloadList(e.getReloadList());
                                    reloadsPane.getPanel().updateTable();
                                case ReloadListEvent.GET_ALL_LOTNUMBERS:
                                    List<String> lrl = new ArrayList<String>();
                                    try{
                                        rlq.connect();
                                        lrl = rlq.getAllLotNumbers();
                                        rlq.close();
                                    }catch(Exception q) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllLotNumbers()): "+q);
                                    }
                                    e.setAllReloadsList(lrl);
                                    break;
                            }
                        }
                    });
                    rgui.setBulletsListener(new BulletsListener(){
                        @Override
                        public void BulletsEventOccurred(BulletsEvent be){
                            List<String> bsl = new ArrayList<String>();
                            Bullets bs = new Bullets();
                            switch(be.getEventType()){
                                case BulletsEvent.GET_ALL_LOTNUMBERS:
                                    try{
                                        bsq.connect();
                                        bsl = bsq.getAllBulletLotNums();
                                        bsq.close();
                                    } catch (Exception bq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllBulletLotNums()): "+bq);
                                    }
                                    be.setAllBulletsList(bsl);
                                    break;
                                case BulletsEvent.GET_LOTNUMBER:
                                    try{
                                        bsq.connect();
                                        bs = bsq.getLotNumber(be.getBullet().getLotNumber());
                                        bsq.close();
                                    } catch (Exception bq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+bq);
                                    }
                                    be.setBullet(bs);
                                    break;
                            }             

                        }                   
                    });
                    rgui.setCasesListener(new CasesListener(){
                        @Override
                        public void CasesEventOccurred(CasesEvent ce){
                            Cases cs = new Cases();
                            List<String> csl = new ArrayList<String>();
                            switch(ce.getEventType()){
                                case CasesEvent.GET_ALL_CASE_LOTNUMBERS:
                                    try{
                                        csq.connect();
                                        csl = csq.getAllCaseLotNums();
                                        csq.close();
                                    } catch (Exception cq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllBulletLotNums()): "+cq);
                                    }
                                    ce.setCasesList(csl);
                                    break;
                                case CasesEvent.GET_LOTNUMBER:
                                    try{
                                        csq.connect();
                                        cs = csq.getLotNumber(ce.getCases().getLotNumber());
                                        csq.close();
                                    } catch (Exception cq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+cq);
                                    }
                                    ce.setCases(cs);
                                    break;
                            }             
                        }
                    });
                    rgui.setPrimerListener(new PrimerListener(){
                        @Override
                        public void PrimerEventOccurred(PrimerEvent pre){
                            Primer pr = new Primer();
                            List<String> lpr = new ArrayList<String>();
                            switch(pre.getEventType()){
                                case PrimerEvent.GET_ALL_LOTNUMBERS:
                                    try{
                                        prq.connect();
                                        lpr = prq.getAllPrimerLotNums();
                                        prq.close();                                   
                                    }catch (Exception pq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllPrimerLotNums()): "+pq);
                                    }
                                    pre.setPrimerList(lpr);
                                    break;
                                case PrimerEvent.GET_LOTNUMBER:
                                    try{
                                        prq.connect();
                                        pr = prq.getLotNumber(pre.getPrimer().getLotNumber());
                                        prq.close();
                                    }catch (Exception pq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+pq);
                                    }
                                    pre.setPrimer(pr);
                                    break;
                            }             
                        }
                    });
                    rgui.setPowderListener(new PowderListener(){
                        @Override
                        public void PowderEventOccurred(PowderEvent pwe){
                            Powder pw = new Powder();
                            List<String> lpw = new ArrayList<String>();
                            switch(pwe.getEventType()){
                                case PowderEvent.GET_ALL_LOTNUMBERS:
                                    try{
                                        pwq.connect();
                                        lpw = pwq.getAllPowderLotNums();
                                        pwq.close();
                                    }catch (Exception pq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllPowderLotNums()): "+pq);
                                    }
                                    pwe.setPowderList(lpw);
                                    break;
                                case PowderEvent.GET_LOTNUMBER:
                                    try{
                                        pwq.connect();
                                        pw = pwq.getLotNumber(pwe.getPowder().getPowderLotNumber());
                                        pwq.close();  
                                    }catch (Exception pq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+pq);
                                    }
                                    pwe.setPowder(pw);
                                    break;
                            }             
                        }
                    });
                    rgui.buildWindow(INSERT);
                    rgui.setVisible(true);                    
                    break;
                case 9:
                    ChronographGUI cdgui = new ChronographGUI();
                    cdgui.setCd(new ChronographData());
                    cdgui.setWinType(INSERT);

                    cdgui.setChronoDataListener(new ChronoDataListener(){
                        @Override
                        public void ChronoDataEventOccurred(ChronoDataEvent cde) {
                            if (cde.getEventType() == ChronoDataEvent.INSERT_TEST_NUMBER){
                                try{
                                    cdq.connect();
                                    cdq.insertTestNumber(cde.getChronoData());
                                    cdq.close();
                                }catch(Exception q) {
                                    AlertDialog("ChronographDataTableMain: Database error in ChronographDataTableMain:insertLotNumber()): "+q);
                                }
                                chronoPane.getPanel().addChrono(cde.getChronoData());
                                chronoPane.getPanel().updateTable();
                            }
                        }                    
                    });
                    cdgui.setBulletsListener(new BulletsListener(){
                        @Override
                        public void BulletsEventOccurred(BulletsEvent be){
                            List<String> bsl = new ArrayList<String>();
                            Bullets bs = new Bullets();
                            switch(be.getEventType()){
                                case BulletsEvent.GET_ALL_LOTNUMBERS:
                                    try{
                                        bsq.connect();
                                        bsl = bsq.getAllBulletLotNums();
                                        bsq.close();
                                    } catch (Exception bq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllBulletLotNums()): "+bq);
                                    }
                                    be.setAllBulletsList(bsl);
                                    break;
                                case BulletsEvent.GET_LOTNUMBER:
                                    try{
                                        bsq.connect();
                                        bs = bsq.getLotNumber(be.getBullet().getLotNumber());
                                        bsq.close();
                                    } catch (Exception bq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+bq);
                                    }
                                    be.setBullet(bs);
                                    break;
                            }             

                        }                   
                    });
                    cdgui.setPowderListener(new PowderListener(){
                        @Override
                        public void PowderEventOccurred(PowderEvent pwe){
                            Powder pw = new Powder();
                            List<String> lpw = new ArrayList<String>();
                            switch(pwe.getEventType()){
                                case PowderEvent.GET_ALL_LOTNUMBERS:
                                    try{
                                        pwq.connect();
                                        lpw = pwq.getAllPowderLotNums();
                                        pwq.close();
                                    }catch (Exception pq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllPowderLotNums()): "+pq);
                                    }
                                    pwe.setPowderList(lpw);
                                    break;
                                case PowderEvent.GET_LOTNUMBER:
                                    try{
                                        pwq.connect();
                                        pw = pwq.getLotNumber(pwe.getPowder().getPowderLotNumber());
                                        pwq.close();  
                                    }catch (Exception pq) {
                                        AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getLotNumber()): "+pq);
                                    }
                                    pwe.setPowder(pw);
                                    break;
                            }             
                        }
                    });
                    cdgui.setFirearmListener(new FirearmListener(){
                        @Override
                        public void FirearmEventOccurred(FirearmEvent fde) {
                            Firearm fd = new Firearm();
                            List<String> lfd = new ArrayList<String>();
                            switch(fde.getEventType()){
                                case FirearmEvent.GET_ALL_LOTNUMBERS:
                                    try{
                                        fmq.connect();
                                        lfd = fmq.getAllSerialNumbers();
                                        fmq.close();
                                    } catch(Exception q) {
                                        AlertDialog("Database error in getAll(): "+q);
                                    }
                                    fde.setFirearmList(lfd);
                                    break;
                                case FirearmEvent.GET_LOTNUMBER:
                                    try{
                                        fmq.connect();
                                        fd = fmq.getFirearmData(fde.getFirearm().getSerialNumber());
                                        fmq.close();
                                    } catch(Exception q) {
                                        AlertDialog("Database error in getAll(): "+q);
                                    }
                                    fde.setFirearm(fd);
                                    break;
                            }
                        }
                    });
                    cdgui.setReloadListListener(new ReloadListListener() {
                        @Override
                        public void ReloadListEventOccurred(ReloadListEvent rle) {
                            ReloadList rl = new ReloadList();
                            List<String> lrl = new ArrayList<String>();
                            switch(rle.getEventType()){
                                case ReloadListEvent.GET_ALL_LOTNUMBERS:
                                    try{
                                        rlq.connect();
                                        lrl = rlq.getAllLotNumbers();
                                        rlq.close();
                                    } catch(Exception q) {
                                        AlertDialog("Database error in getAll(): "+q);
                                    }
                                    rle.setAllReloadsList(lrl);
                                    break;
                                case ReloadListEvent.GET_LOTNUMBER:
                                    try{
                                        rlq.connect();
                                        rl = rlq.getLotNumber(rle.getReloadList().getLotNumber());
                                        rlq.close();
                                    }catch(Exception q) {
                                        AlertDialog("Database error in getAll(): "+q);
                                    }
                                    rle.setReloadList(rl);
                                    break;
                            }
                        }
                    });
                    cdgui.buildWindow();
                    cdgui.setVisible(true);
                    break;                    
            }           
        }       
    }
    
    private void buildSearchMenu(){
        searchMenu = new JMenu("Search");
        searchMenu.setMnemonic(KeyEvent.VK_S);
        searchMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                //AlertDialog("menuSelected");
                String answer;
                JDialog.setDefaultLookAndFeelDecorated(true);
                if(tabbedPane.getSelectedIndex() < 5 || tabbedPane.getSelectedIndex() == 8){
                    //add Lot Number and Include Empty
                    searchMenuItem1 = new JMenuItem("Lot Number"); 
                    searchMenuItem1.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String ans = JOptionPane.showInputDialog("Enter the Lot Number.");
                            if(ans == null){
                                AlertDialog("Wait What?");
                                return;
                            }
                            //AlertDialog("Tabbed Panel = "+tabbedPane.getSelectedIndex());
                            try{
                                switch(tabbedPane.getSelectedIndex()){
                                    case 0: //bullets tab is being shown.
                                        try{
                                            bsq.connect();
                                            boolean temp = bsq.isIgnoreEmpty();
                                            bsq.setIgnoreEmpty(isEmpytArr[tabbedPane.getSelectedIndex()]);
                                            Bullets bs = bsq.getLotNumber(ans);
                                            bsq.close();
                                            bsq.setIgnoreEmpty(temp);
                                            BulletsGUI bgui = new BulletsGUI();
                                            bgui.setBullets(bs);
                                            bgui.buildWindow(bulletPane.getWinType());
                                            bgui.setVisible(true);  
                                        } catch(Exception q) {
                                            AlertDialog("Database error in getAll(): "+q);
                                        }
                                        break;
                                    case 1: //Cases
                                        try {
                                            csq.connect();
                                            boolean temp = csq.isIgnoreempty1();
                                            csq.setIgnoreempty1(isEmpytArr[tabbedPane.getSelectedIndex()]);
                                            Cases cs = csq.getLotNumber(ans);
                                            csq.close();
                                            csq.setIgnoreempty1(temp);
                                            CasesGUI cgui = new CasesGUI();
                                            cgui.setCases(cs);
                                            cgui.buildWindow(casePane.getWinType());
                                            cgui.setVisible(true);
                                         }catch(Exception q) {
                                            AlertDialog("Database error in CasessTableMain:insertCases()): "+q);
                                        }
                                        break;
                                    case 2:
                                        try{
                                            prq.connect();
                                            boolean temp = prq.isIgnoreEmpty1();
                                            prq.setIgnoreEmpty1(isEmpytArr[tabbedPane.getSelectedIndex()]);
                                            Primer prs = prq.getLotNumber(ans);
                                            prq.close();
                                            prq.setIgnoreEmpty1(temp);
                                            PrimerGUI pgui = new PrimerGUI();
                                            pgui.setPrimer(prs);
                                            pgui.buildWindow(primerPane.getWinType());
                                            pgui.setVisible(true);
                                        } catch(Exception q) {
                                            AlertDialog("PrimeTableMain: Database error in getAll(): "+q);
                                        }
                                        break;
                                    case 3:
                                        try{
                                            pwq.connect();
                                            boolean temp = pwq.isIgnoreempty1();
                                            pwq.setIgnoreempty1(isEmpytArr[tabbedPane.getSelectedIndex()]);                                               
                                            Powder pw = pwq.getLotNumber(ans);
                                            pwq.close();
                                            pwq.setIgnoreempty1(temp);
                                            PowderGUI pgui = new PowderGUI();
                                            pgui.setPowder(pw);
                                            pgui.buildWindow(powderPane.getWinType());
                                            pgui.setVisible(true);
                                        } catch(Exception q) {
                                            AlertDialog("Database error in getAll(): "+q);
                                        }
                                       break;
                                    case 4:
                                        try{
                                            maq.connect();
                                            boolean temp = maq.isNotEmpty();
                                            maq.setNotEmpty(isEmpytArr[tabbedPane.getSelectedIndex()]);   
                                            ManufacturedAmmo ma = maq.getLotNumber(ans);
                                            maq.close();
                                            maq.setNotEmpty(temp);
                                            ManufacturedAmmoGUI bgui = new ManufacturedAmmoGUI();
                                            bgui.setManufacturedAmmo(ma);
                                            bgui.buildWindow(ammoPane.getWinType());
                                            bgui.setVisible(true);                           
                                        } catch(Exception q) {
                                            AlertDialog("Database error in getAll(): "+q);
                                        }
                                        break;
                                    case 8:
                                        try{
                                            AlertDialog("Searching for reload.");
                                            rlq.connect();
                                            boolean temp = rlq.isIgnoreEmpty();
                                            rlq.setIgnoreEmpty(isEmpytArr[tabbedPane.getSelectedIndex()]);
                                            ReloadList rl = rlq.getLotNumber(ans);
                                            rlq.close();
                                            rlq.setIgnoreEmpty(temp);
                                            ReloadListGUI rgui = new ReloadListGUI();
                                            rgui.setAmmoList(rl);
                                            rgui.setBulletsListener(new BulletsListener(){
                                                @Override
                                                public void BulletsEventOccurred(BulletsEvent be){
                                                    List<String> bsl = new ArrayList<String>();
                                                    Bullets bs = new Bullets();
                                                    switch(be.getEventType()){
                                                        case BulletsEvent.GET_ALL_LOTNUMBERS:
                                                            try{
                                                                boolean temp = bsq.isIgnoreEmpty();
                                                                bsq.setIgnoreEmpty(be.isIgnoreEmpty());
                                                                bsq.connect();
                                                                bsl = bsq.getAllBulletLotNums();
                                                                bsq.close();
                                                                bsq.setIgnoreEmpty(temp);
                                                            } catch (Exception bq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllBulletLotNums()): "+bq);
                                                            }
                                                            be.setAllBulletsList(bsl);
                                                            break;
                                                        case BulletsEvent.GET_LOTNUMBER:
                                                            try{
                                                                bsq.connect();
                                                                bs = bsq.getLotNumber(be.getBullet().getLotNumber());
                                                                bsq.close();

                                                            } catch (Exception bq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getBulletLotNumber()): "+bq);
                                                            }
                                                            be.setBullet(bs);
                                                            break;
                                                    }             

                                                }                   
                                            });
                                            rgui.setCasesListener(new CasesListener(){
                                                @Override
                                                public void CasesEventOccurred(CasesEvent ce){
                                                    Cases cs = new Cases();
                                                    List<String> csl = new ArrayList<String>();
                                                    switch(ce.getEventType()){
                                                        case CasesEvent.GET_ALL_CASE_LOTNUMBERS:
                                                            try{
                                                                boolean temp = csq.isIgnoreempty1();
                                                                bsq.setIgnoreEmpty(ce.isIgnoreEmpty());
                                                                csq.connect();
                                                                csl = csq.getAllCaseLotNums();
                                                                csq.close();
                                                                bsq.setIgnoreEmpty(temp);
                                                            } catch (Exception cq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllCasesLotNums()): "+cq);
                                                            }
                                                            ce.setCasesList(csl);
                                                            break;
                                                        case CasesEvent.GET_LOTNUMBER:
                                                            try{
                                                                csq.connect();
                                                                cs = csq.getLotNumber(ce.getCases().getLotNumber());
                                                                csq.close();
                                                            } catch (Exception cq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getCaseLotNumber()): "+cq);
                                                            }
                                                            ce.setCases(cs);
                                                            break;
                                                    }             
                                                }
                                            });
                                            rgui.setPrimerListener(new PrimerListener(){
                                                @Override
                                                public void PrimerEventOccurred(PrimerEvent pre){
                                                    Primer pr = new Primer();
                                                    List<String> lpr = new ArrayList<String>();
                                                    switch(pre.getEventType()){
                                                        case PrimerEvent.GET_ALL_LOTNUMBERS:
                                                            try{
                                                                boolean temp = prq.isIgnoreEmpty1();
                                                                prq.setIgnoreEmpty1(pre.isIgnoreEmpty());
                                                                prq.connect();
                                                                lpr = prq.getAllPrimerLotNums();
                                                                prq.close();
                                                                prq.setIgnoreEmpty1(temp);
                                                            }catch (Exception pq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllPrimerLotNums()): "+pq);
                                                            }
                                                            pre.setPrimerList(lpr);
                                                            break;
                                                        case PrimerEvent.GET_LOTNUMBER:
                                                            try{
                                                                prq.connect();
                                                                pr = prq.getLotNumber(pre.getPrimer().getLotNumber());
                                                                prq.close();
                                                            }catch (Exception pq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getPrimerLotNumber()): "+pq);
                                                            }
                                                            pre.setPrimer(pr);
                                                            break;
                                                    }             
                                                }
                                            });
                                            rgui.setPowderListener(new PowderListener(){
                                                @Override
                                                public void PowderEventOccurred(PowderEvent pwe){
                                                    Powder pw = new Powder();
                                                    List<String> lpw = new ArrayList<String>();
                                                    switch(pwe.getEventType()){
                                                        case PowderEvent.GET_ALL_LOTNUMBERS:
                                                            try{
                                                                boolean temp = pwq.isIgnoreempty1();
                                                                pwq.setIgnoreempty1(pwe.isIgnoreEmpty());
                                                                pwq.connect();
                                                                lpw = pwq.getAllPowderLotNums();
                                                                pwq.close();
                                                                pwq.setIgnoreempty1(temp);
                                                            }catch (Exception pq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getAllPowderLotNums()): "+pq);
                                                            }
                                                            pwe.setPowderList(lpw);
                                                            break;
                                                        case PowderEvent.GET_LOTNUMBER:
                                                            try{
                                                                pwq.connect();
                                                                pw = pwq.getLotNumber(pwe.getPowder().getPowderLotNumber());
                                                                pwq.close();  
                                                            }catch (Exception pq) {
                                                                AlertDialog("ReloadListTableMain: Database error in ReloadListTableMain:getPowderLotNumber()): "+pq);
                                                            }
                                                            pwe.setPowder(pw);
                                                            break;
                                                    }             
                                                }
                                            });
                                            rgui.buildWindow(reloadPane.getWinType());
                                            rgui.setVisible(true);
                                        } catch(Exception q) {
                                            AlertDialog("Database error Searching for reload: "+q);
                                        }
                                        break;
                                 }
                            } catch(Exception q) {
                                AlertDialog("Database error in getAll(): "+q);
                            }
                        }
                    });
                    searchMenu.add(searchMenuItem1);
                    isEmptyMenuItemRB = new JRadioButtonMenuItem("Show Empty");
                    isEmptyMenuItemRB.setSelected(isEmpytArr[tabbedPane.getSelectedIndex()]);
                    isEmptyMenuItemRB.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(isEmptyMenuItemRB.isSelected())
                                isEmpytArr[tabbedPane.getSelectedIndex()] = true;
                            else
                                isEmpytArr[tabbedPane.getSelectedIndex()] = false;
                        } 
                    });
                    searchMenu.add(isEmptyMenuItemRB);
                } else if(tabbedPane.getSelectedIndex() == 5){
                    //add Mold Number and ID Number
                    searchMenuItem1 = new JMenuItem("Mold Number"); 
                    searchMenuItem1.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String ans = JOptionPane.showInputDialog("Enter the Mold Number.");
                            if(ans == null)
                                return;
                            try{
                                bmq.connect();
                                BulletMold bm = bmq.getMoldNumber(ans);
                                bmq.close();
                                BulletMoldGUI bmgui = new BulletMoldGUI();
                                bmgui.setBm(bm);
                                bmgui.buildWindow(BulletMoldGUI.VIEW);
                                bmgui.setVisible(true);
                            } catch (Exception q) {
                                AlertDialog("Database error in getAll(): "+q);
                            }
                        }                           
                    });
                    searchMenu.add(searchMenuItem1);
                    searchMenuItem2 = new JMenuItem("ID Number");
                    searchMenuItem2.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String ans = JOptionPane.showInputDialog("Enter the Mold Number.");
                            if(ans == null)
                                return;
                            try{
                                bmq.connect();
                                BulletMold bm = bmq.getMoldID(Integer.parseInt(ans));
                                bmq.close();
                                BulletMoldGUI bmgui = new BulletMoldGUI();
                                bmgui.setBm(bm);
                                bmgui.buildWindow(BulletMoldGUI.VIEW);
                                bmgui.setVisible(true);
                            } catch (Exception q) {
                                AlertDialog("Database error in getAll(): "+q);
                            }
                        }                           
                    });

                    searchMenu.add(searchMenuItem2);
                } else if(tabbedPane.getSelectedIndex() == 6){
                    //Show Dialog asking for ID Number
                    answer = JOptionPane.showInputDialog("Enter the Relaoding Die Identification Number.");
                    if(answer == null)
                        return;
                    try{
                        int selectedLotNumber = Integer.parseInt(answer);
                        ReloadingDies rd = new ReloadingDies();
                        rdq.connect();
                        rd = rdq.getID(selectedLotNumber);
                        rdq.close();
                        ReloadingDiesGUI rgui = new ReloadingDiesGUI();
                        rgui.setReloadingDie(rd);
                        rgui.buildWindow(ReloadingDiesGUI.VIEW);
                        rgui.setVisible(true);
                    } catch(Exception q) {
                        AlertDialog("Database error in getAll(): Search Reloading Dies"+q);
                    }
                } else if(tabbedPane.getSelectedIndex() == 7){
                    //Show Dialog asking for Serial Number
                    answer = JOptionPane.showInputDialog("Enter the Firearm Serial Number.");
                    if(answer == null)
                        return;
                    Firearm fs = new Firearm();
                    try{
                        fmq.connect();
                        fs = fmq.getFirearmData(answer);
                        fmq.close();
                        FirearmsGUI fgui = new FirearmsGUI();
                        fgui.setFirearm(fs);
                        fgui.buildWindow(FirearmsGUI.VIEW);
                        fgui.setVisible(true);
                    } catch(Exception q) {
                        AlertDialog("Database error in getAll() Search Firearm: "+q);
                    }
                 } else if(tabbedPane.getSelectedIndex() == 9){
                    //Show Dialog asking for Test Number
                    answer = JOptionPane.showInputDialog("Enter the Chronogragh Test Number.");
                    if(answer == null)
                        return;
                    ChronographData cd = new ChronographData();
                    try{
                        int selectedTestNumber = Integer.parseInt(answer);
                        cdq.connect();
                        cd = cdq.getTestNumber(selectedTestNumber);
                        cdq.close();
                        ChronographGUI cgui = new ChronographGUI();
                        cgui.setCd(cd);
                        cgui.setWinType(ChronographGUI.VIEW);
                        cgui.buildWindow();
                        cgui.setVisible(true);
                    } catch(Exception q) {
                        AlertDialog("Database error in getAll() Search Chronograph: "+q);
                    }
                }
            }

            public void menuDeselected(MenuEvent e) {
                //AlertDialog("menuDeselected");
                try {
                    searchMenu.remove(searchMenuItem1);
                } catch(Exception q) {}
                try {
                    searchMenu.remove(searchMenuItem2);
                } catch(Exception q) {}
                try {
                    searchMenu.remove(isEmptyMenuItemRB);
                } catch(Exception q) {}
            }

            public void menuCanceled(MenuEvent e) {
                //AlertDialog("menuCanceled");
            }
        });
        menuBar.add(searchMenu);
    }
    
    private void buildFilterMenu(){
        filterMenu = new JMenu("Filter");
        filterMenu.setMnemonic(KeyEvent.VK_T);
        filterMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
    //**********Bullet Filter Menu.************************************************
                switch(tabbedPane.getSelectedIndex()){
                    case 0: //Maker	Caliber	Weight	Description
                        filterMenuItem1 = new JMenu("Maker");
                        try{
                            bsq.connect();
                            List<String> bulletMakers = bsq.getAllBulletMakers();
                            bsq.close();
                            int count = bulletMakers.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(bulletMakers.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        try{
                                            String maker = (e.getActionCommand());
                                            bsq.connect();
                                            List<Bullets> bList = bsq.getList("BulletMaker",maker);
                                            bsq.close();
                                            bulletPane.getPanel().filterTable(bList);
                                            bulletPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Bullet Filter BulletMaker: Well that didn't work!"+s);
                                        }
                                    }   
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }                                
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Caliber");
                        try{
                            bsq.connect();
                            List<String> bulletCaliber = bsq.getAllCaliber();
                            bsq.close();
                            int count = bulletCaliber.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(bulletCaliber.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        try{
                                            String caliber = (e.getActionCommand());
                                            bsq.connect();
                                            List<Bullets> bList = bsq.getList("Caliber",caliber);
                                            bsq.close();
                                            bulletPane.getPanel().filterTable(bList);
                                            bulletPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Bullet Filter Caliber: Well that didn't work!"+s);
                                        }
                                    }   
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }                                
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Weight");
                        try{
                            bsq.connect();
                            List<String> bulletWeight = bsq.getAllWeight();
                            bsq.close();
                            int count = bulletWeight.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(bulletWeight.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        try{
                                            String weight = (e.getActionCommand());
                                            bsq.connect();
                                            List<Bullets> bList = bsq.getList("Weight",weight);
                                            bsq.close();
                                            bulletPane.getPanel().filterTable(bList);
                                            bulletPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Bullet Filter Weight: Well that didn't work!"+s);
                                        }
                                    }   
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }                                
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        filterMenuItem4 = new JMenu("Description");
                        try{
                            bsq.connect();
                            List<String> bulletDescription = bsq.getAllDescription();
                            bsq.close();
                            int count = bulletDescription.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(bulletDescription.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        try{
                                            String Description = (e.getActionCommand());
                                            bsq.connect();
                                            List<Bullets> bList = bsq.getList("Description",Description);
                                            bsq.close();
                                            bulletPane.getPanel().filterTable(bList);
                                            bulletPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Bullet Filter Description: Well that didn't work!"+s);
                                        }
                                    }   
                                });
                                filterMenuItem4.add(filterMenuArray[i++]);
                            }                                
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem4);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Cases Filter Menu*****************************************************************************                    
                    case 1: //Maker	Caliber	Type
                        filterMenuItem1 = new JMenu("Maker");
                        try{
                            csq.connect();
                            List<String> cList = csq.getMaker();
                            csq.close();
                            int count = cList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(cList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            csq.connect();
                                            List<Cases> cList = csq.getList("CaseMaker",maker);
                                            csq.close();
                                            casePane.getPanel().filterTable(cList);
                                            casePane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Cases Filter CaseMaker: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Caliber");
                        try{
                            csq.connect();
                            List<String> cList = csq.getCaliber();
                            csq.close();
                            int count = cList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(cList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String caliber = (e.getActionCommand());
                                            csq.connect();
                                            List<Cases> cList = csq.getList("Caliber",caliber);
                                            csq.close();
                                            casePane.getPanel().filterTable(cList);
                                            casePane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Cases Filter Caliber: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Type");
                        try{
                            csq.connect();
                            List<String> cList = csq.getType();
                            csq.close();
                            int count = cList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(cList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String type = (e.getActionCommand());
                                            csq.connect();
                                            List<Cases> cList = csq.getList("Type",type);
                                            csq.close();
                                            casePane.getPanel().filterTable(cList);
                                            casePane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Cases Filter Type: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Primer Filter Menu*****************************************************************************
                    case 2: //Maker Size Model
                        filterMenuItem1 = new JMenu("Maker");
                        try{
                            prq.connect();
                            List<String> prList = prq.getMaker();
                            prq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            AlertDialog("Maker = "+maker);
                                            prq.connect();
                                            List<Primer> prList = prq.getList("PrimerMaker",maker);
                                            prq.close();
                                            primerPane.getPanel().filterTable(prList);
                                            primerPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Primer Filter PrimerMaker: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Size");
                        try{
                            prq.connect();
                            List<String> prList = prq.getSize();
                            prq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            prq.connect();
                                            List<Primer> prList = prq.getList("Size",maker);
                                            prq.close();
                                            primerPane.getPanel().filterTable(prList);
                                            primerPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Primer Filter Size: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Model");
                        try{
                            prq.connect();
                            List<String> prList = prq.getModel();
                            prq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            prq.connect();
                                            List<Primer> prList = prq.getList("ModelNumber",maker);
                                            prq.close();
                                            primerPane.getPanel().filterTable(prList);
                                            primerPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Primer Filter ModelNumber: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Powder Filter Menu******************************************************************************
                    case 3: //Maker	Name Purchase Date
                        filterMenuItem1 = new JMenu("Maker");
                        try{
                            pwq.connect();
                            List<String> prList = pwq.getMaker();
                            pwq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            pwq.connect();
                                            List<Powder> prList = pwq.getList("PowderMaker",maker);
                                            pwq.close();
                                            powderPane.getPanel().filterTable(prList);
                                            powderPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Powder Filter PowderMaker: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Name");
                        try{
                            pwq.connect();
                            List<String> prList = pwq.getName();
                            pwq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            pwq.connect();
                                            List<Powder> prList = pwq.getList("PowderName",maker);
                                            pwq.close();
                                            powderPane.getPanel().filterTable(prList);
                                            powderPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Powder Filter PowderName: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Purchase Date");
                        try{
                            pwq.connect();
                            List<String> prList = pwq.getDate();
                            pwq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            pwq.connect();
                                            List<Powder> prList = pwq.getList("PurchaseDate",maker);
                                            pwq.close();
                                            powderPane.getPanel().filterTable(prList);
                                            powderPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Powder Filter PurchaseDate: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Ammunition Filter Menu****************************************************************************
                    case 4: //Manufacturer Caliber Purchase Date Bullet
                        filterMenuItem1 = new JMenu("Manufacturer");
                        try{
                            maq.connect();
                            List<String> prList = maq.getMaker();
                            maq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            maq.connect();
                                            List<ManufacturedAmmo> prList = maq.getList("Manufacturer",maker);
                                            maq.close();
                                            ammoPane.getPanel().filterTable(prList);
                                            ammoPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Ammunition Filter Manufacturer: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Caliber");
                        try{
                            maq.connect();
                            List<String> prList = maq.getCaliber();
                            maq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            maq.connect();
                                            List<ManufacturedAmmo> prList = maq.getList("Caliber",maker);
                                            maq.close();
                                            ammoPane.getPanel().filterTable(prList);
                                            ammoPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Ammunition Filter Caliber: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Purcahse Date");
                        try{
                            maq.connect();
                            List<String> prList = maq.getDate();
                            maq.close();
                            int count = prList.size(); 
                            //AlertDialog(prList);
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            maq.connect();
                                            List<ManufacturedAmmo> prList = maq.getListLike("DatePurchased",maker);
                                            maq.close();
                                            ammoPane.getPanel().filterTable(prList);
                                            ammoPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Ammunition Filter DatePurchased: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        filterMenuItem4 = new JMenu("Bullet");
                        try{
                            maq.connect();
                            List<String> prList = maq.getBullet();
                            maq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            maq.connect();
                                            List<ManufacturedAmmo> prList = maq.getList("Bullet",maker);
                                            maq.close();
                                            ammoPane.getPanel().filterTable(prList);
                                            ammoPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Ammunition Filter Bullet: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem4.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem4);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Bullet Mold Filter Menu****************************************************************************
                    case 5: //Manufacturer Description Diameter
                        filterMenuItem1 = new JMenu("Manufacturer");
                        try{
                            bmq.connect();
                            List<String> prList = bmq.getMaker();
                            bmq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            bmq.connect();
                                            List<BulletMold> prList = bmq.getList("Maker",maker);
                                            bmq.close();
                                            bulletMoldPane.getPanel().filterTable(prList);
                                            bulletMoldPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Bullet Mold Filter Maker: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Description");
                        try{
                            bmq.connect();
                            List<String> prList = bmq.getDescription();
                            bmq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            bmq.connect();
                                            List<BulletMold> prList = bmq.getList("Description",maker);
                                            bmq.close();
                                            bulletMoldPane.getPanel().filterTable(prList);
                                            bulletMoldPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Bullet Mold Filter Description: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Diameter");
                        try{
                            bmq.connect();
                            List<String> prList = bmq.getDiameter();
                            bmq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            bmq.connect();
                                            List<BulletMold> prList = bmq.getList("Diameter",maker);
                                            bmq.close();
                                            bulletMoldPane.getPanel().filterTable(prList);
                                            bulletMoldPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Bullet Mold Filter Diameter: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Reloading Dies Filter Menu**************************************************************************
                    case 6: //Manufacturer	Caliber
                        filterMenuItem1 = new JMenu("Manufacturer");
                        try{
                            rdq.connect();
                            List<String> prList = rdq.getMaker();
                            rdq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            rdq.connect();
                                            List<ReloadingDies> prList = rdq.getList("Maker",maker);
                                            rdq.close();
                                            reloadPane.getPanel().filterTable(prList);
                                            reloadPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Reloading Dies Filter Maker: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Caliber");
                        try{
                            rdq.connect();
                            List<String> prList = rdq.getCaliber();
                            rdq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            rdq.connect();
                                            List<ReloadingDies> prList = rdq.getList("Caliber",maker);
                                            rdq.close();
                                            reloadPane.getPanel().filterTable(prList);
                                            reloadPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Reloading Dies Filter Caliber: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }                        
                        filterMenu.add(filterMenuItem2);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Firearm Filter Menu***********************************************************************************
                    case 7: //Manufacturer	Model	Type	Caliber
                        filterMenuItem1 = new JMenu("Manufacturer");
                        try{
                            fmq.connect();
                            List<String> prList = fmq.getMaker();
                            fmq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            fmq.connect();
                                            List<Firearm> prList = fmq.getList("Manufacturer",maker);
                                            fmq.close();
                                            gunPane.getPanel().filterTable(prList);
                                            gunPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Firearm Filter Manufacturer: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Model");
                        try{
                            fmq.connect();
                            List<String> prList = fmq.getModel();
                            fmq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            fmq.connect();
                                            List<Firearm> prList = fmq.getList("ModelName",maker);
                                            fmq.close();
                                            gunPane.getPanel().filterTable(prList);
                                            gunPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Firearm Filter Model: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Type");
                        try{
                            fmq.connect();
                            List<String> prList = fmq.getType();
                            fmq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            fmq.connect();
                                            List<Firearm> prList = fmq.getList("Type",maker);
                                            fmq.close();
                                            gunPane.getPanel().filterTable(prList);
                                            gunPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Firearm Filter Type: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        filterMenuItem4 = new JMenu("Caliber");
                        try{
                            fmq.connect();
                            List<String> prList = fmq.getBullet();
                            fmq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            fmq.connect();
                                            List<Firearm> prList = fmq.getList("Caliber",maker);
                                            fmq.close();
                                            gunPane.getPanel().filterTable(prList);
                                            gunPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Firearm Filter Caliber: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem4.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem4);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    //**************Reload List Filter Menu********************************************************************************
                    case 8:	//Load Date	Caliber

                        filterMenuItem1 = new JMenu("Caliber");
                        try{
                            rlq.connect();
                            List<String> prList = rlq.getCaliber();
                            rlq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            rlq.connect();
                                            List<ReloadList> prList = rlq.getList("Caliber",maker);
                                            rlq.close();
                                            reloadsPane.getPanel().filterTable(prList);
                                            reloadsPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Reload List Filter: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Load Date");
                        try{
                            rlq.connect();
                            List<String> prList = rlq.getDate();
                            rlq.close();
                            int count = prList.size(); 
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            rlq.connect();
                                            List<ReloadList> prList = rlq.getListLike("Date",maker);
                                            rlq.close();
                                            reloadsPane.getPanel().filterTable(prList);
                                            reloadsPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Reload List Filter: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
    /**************Chronograph Data Filter Menu****************************************************************************/
                    case 9:	//Shot Date Caliber Firearm Standard Deviation
                        filterMenuItem1 = new JMenu("Shot Date");
                        try{
                            cdq.connect();
                            List<String> prList = cdq.getDateList();
                            cdq.close();
                            int count = prList.size();
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            cdq.connect();
                                            List<ChronographData> prList = cdq.getListLike("Date",maker);
                                            cdq.close();
                                            chronoPane.getPanel().filterTable(prList);
                                            chronoPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Reload List Filter: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem1.add(filterMenuArray[i++]);
                            }    
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem1);
                        filterMenuItem2 = new JMenu("Caliber");
                        try{
                            cdq.connect();
                            List<String> prList = cdq.getCaliberList();
                            cdq.close();
                            int count = prList.size();
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                filterMenuArray[i] = new JMenuItem(prList.get(i));
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            cdq.connect();
                                            List<ChronographData> prList = cdq.getListLike("Caliber",maker);
                                            cdq.close();
                                            chronoPane.getPanel().filterTable(prList);
                                            chronoPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Reload List Filter: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem2.add(filterMenuArray[i++]);
                            }    
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem2);
                        filterMenuItem3 = new JMenu("Firearm");
                        try{
                            cdq.connect();
                            List<String> prList = cdq.getFirearmList();
                            List<String> gunList = new ArrayList<String>();
                            cdq.close();
                            int count = prList.size();
                            filterMenuArray = new JMenuItem[count];
                            for(int i = 0; i < count;){
                                String gun = prList.get(i);
                                gun = gun.substring(gun.indexOf(',')+1);
                                gunList.add(gun);
                                filterMenuArray[i] = new JMenuItem(gun);
                                filterMenuArray[i].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e){ 
                                        try{
                                            String maker = (e.getActionCommand());
                                            int index = gunList.indexOf(maker);
                                            String fs = prList.get(index);
                                            fs = fs.substring(0,fs.indexOf(','));
                                            cdq.connect();
                                            List<ChronographData> cdList = cdq.getListLike("FirearmID",fs);
                                            cdq.close();
                                            chronoPane.getPanel().filterTable(cdList);
                                            chronoPane.getPanel().updateTable();
                                        }catch(Exception s){
                                            AlertDialog("Reload List Filter: Well that didn't work!"+s);
                                        }
                                    }
                                });
                                filterMenuItem3.add(filterMenuArray[i++]);
                            }    
                        } catch (Exception x){
                            AlertDialog("Error: "+x);
                        }
                        filterMenu.add(filterMenuItem3);
                        clearFilterMenuItem = new JMenuItem("Clear Filter");
                        clearFilterMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                clearFilter();
                            }    
                        });
                        filterMenu.add(clearFilterMenuItem);
                        break;
                }
            }

            public void menuDeselected(MenuEvent e) {
                //AlertDialog("menuDeselected");
                try {
                    filterMenu.remove(filterMenuItem1);
                } catch(Exception q) {}
                 try {
                    filterMenu.remove(filterMenuItem2);
                } catch(Exception q) {}
                try {
                    filterMenu.remove(filterMenuItem3);
                } catch(Exception q) {}
                try {
                    filterMenu.remove(filterMenuItem4);
                } catch(Exception q) {}
                try {
                    filterMenu.remove(clearFilterMenuItem);
                } catch(Exception q) {}
            }

            public void menuCanceled(MenuEvent e) {
                //AlertDialog("menuCanceled");
            }
        });
        menuBar.add(filterMenu);
    }
    
    private void clearFilter(){
        switch(tabbedPane.getSelectedIndex()){
            case 0:
                try{
                    bsq.connect();
                    List<Bullets> hbList = bsq.getAll();
                    bsq.close();
                    bulletPane.getPanel().filterTable(hbList);
                    bulletPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[0] Well that didn't work!"+s);
                }
                break;
            case 1:
                try{
                    csq.connect();
                    List<Cases> hcList = csq.getAll();
                    csq.close();
                    casePane.getPanel().filterTable(hcList);
                    casePane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[1] Well that didn't work!"+s);
                }
                break;
            case 2:
                try{
                    prq.connect();
                    List<Primer> hcList = prq.getAll();
                    prq.close();
                    primerPane.getPanel().filterTable(hcList);
                    primerPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[2] Well that didn't work!"+s);
                }
                break;
            case 3:
                try{
                    pwq.connect();
                    List<Powder> hcList = pwq.getAll();
                    pwq.close();
                    powderPane.getPanel().filterTable(hcList);
                    powderPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[3] Well that didn't work!"+s);
                }
                break;
            case 4:
                try{
                    maq.connect();
                    List<ManufacturedAmmo> hcList = maq.getAll();
                    maq.close();
                    ammoPane.getPanel().filterTable(hcList);
                    ammoPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[4] Well that didn't work!"+s);
                }
                break;
            case 5:
                try{
                    bmq.connect();
                    List<BulletMold> hcList = bmq.getAll();
                    bmq.close();
                    bulletMoldPane.getPanel().filterTable(hcList);
                    bulletMoldPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[5] Well that didn't work!"+s);
                }
                break;
            case 6:
                try{
                    rdq.connect();
                    List<ReloadingDies> hcList = rdq.getAll();
                    rdq.close();
                    reloadPane.getPanel().filterTable(hcList);
                    reloadPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[6] Well that didn't work!"+s);
                }
                break;
            case 7:
                try{
                    fmq.connect();
                    List<Firearm> hcList = fmq.getAll();
                    fmq.close();
                    gunPane.getPanel().filterTable(hcList);
                    gunPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[7] Well that didn't work!"+s);
                }
                break;
            case 8:
                try{
                    rlq.connect();
                    List<ReloadList> hcList = rlq.getAll();
                    rlq.close();
                    reloadsPane.getPanel().filterTable(hcList);
                    reloadsPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[8] Well that didn't work!"+s);
                }
                break;
            case 9:
                try{
                    cdq.connect();
                    List<ChronographData> hcList = cdq.getAll();
                    cdq.close();
                    chronoPane.getPanel().filterTable(hcList);
                    chronoPane.getPanel().updateTable();
                }catch(Exception s){
                    AlertDialog("clearFilter()[9] Well that didn't work!"+s);
                }
                break;
        }
    }
}



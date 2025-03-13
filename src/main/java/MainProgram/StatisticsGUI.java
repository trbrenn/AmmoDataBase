/****************************************************************************************************************
 * StatisticsGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 1-8-25																								*
 *  																											*
 * This is the StatisticsGUI program. It collects and displays the number of records in the database.										*
 * 																												*
 ***************************************************************************************************************/
package MainProgram;

import BaseClasses.DataBaseConn;
import BaseClasses.DataBaseConnData;
import java.awt.BorderLayout;
import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StatisticsGUI extends JDialog implements ActionListener {
    private DataBaseConn DBConn;
    
    private int adtc; 
    private int ade;
    private int adne;
    private int cstc; 
    private int cse;
    private int csne;
    private int prtc; 
    private int pre;
    private int prne;
    private int pwtc; 
    private int pwe;
    private int pwne;
    private int rdtc;
    private int bmtc;
    private int bbtc;
    private int gltc;
    private int cdtc;
    private int altc;
    private int ale;
    private int alne;
    private JTextArea text;
    private JButton okBtn;
    private JScrollPane scrollPane;
    
    private final int XSIZE = 450;
    private final int YSIZE = 320;    

    public StatisticsGUI(DataBaseConn dbc){
        this.setTitle("Database Statistics");
        this.DBConn = dbc;
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setSize(XSIZE, YSIZE);
        this.makeIcon();
        text = new JTextArea();
        this.add(okBtn = new JButton("OK"), BorderLayout.SOUTH);
        this.okBtn.addActionListener(this);
        this.scrollPane = new JScrollPane(text);
        this.add(scrollPane, BorderLayout.CENTER);
        
        try{
            DBConn.connect();
            
            //Get data from the AmmoDataBase.bullets.
            String QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Bullets";
            ResultSet rs = DBConn.runQuery(QueryString);
            rs.next();
            adtc = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Bullets WHERE Empty1 = 1 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            ade = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Bullets WHERE Empty1 = 0 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            adne = rs.getInt(1);
           
            //Get data from the AmmoDataBase.cases.
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Cases";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            cstc = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Cases WHERE isEmpty = 1 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            cse = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Cases WHERE isEmpty = 0 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            csne = rs.getInt(1);
           
            //Get data from the AmmoDataBase.primer.
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Primer";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            prtc = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Primer WHERE Empty1 = 1 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            pre = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Primer WHERE Empty1 = 0 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            prne = rs.getInt(1);
           
            //Get data from the AmmoDataBase.powder.
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Powder";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            pwtc = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Powder WHERE Empty1 = 1 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            pwe = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.Powder WHERE Empty1 = 0 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            pwne = rs.getInt(1);
                           
            //Get data from the AmmoDataBase.ManufacturedAmmo.
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.ManufacturedAmmo";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            pwtc = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.ManufacturedAmmo WHERE Empty1 = 1 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            pwe = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.ManufacturedAmmo WHERE Empty1 = 0 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            pwne = rs.getInt(1);
                           
            //Get data from the AmmoDataBase.AmmoList.
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.AmmoList";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            altc = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.AmmoList WHERE isEmpty = 1 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            ale = rs.getInt(1);
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.AmmoList WHERE isEmpty = 0 ";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            alne = rs.getInt(1);
                           
            //Get data from the AmmoDataBase.BulletMold.
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.BulletMold";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            bmtc = rs.getInt(1);
            
            //Get data from the AmmoDataBase.ReloadingDies.
            QueryString = "SELECT COUNT(*) FROM AmmoDataBase.ReloadingDies";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            rdtc = rs.getInt(1);
            
            //Get data from the Bound Book
            QueryString = "SELECT COUNT(*) FROM BoundBook.BoundBookPage";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            bbtc = rs.getInt(1);
            
            
            //Get data from the GunList.GunsInList
            QueryString = "SELECT COUNT(*) FROM GunList.GunsInList";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            gltc = rs.getInt(1);            
            
            //Get data from the ammodataBase.chronographdata.
            QueryString = "SELECT COUNT(*) FROM ammodataBase.chronographdata";
            rs = DBConn.runQuery(QueryString);
            rs.next();
            cdtc = rs.getInt(1);            
            
            rs.close();
            DBConn.close();
        } catch (Exception e) {
            AlertDialog("Database Error: StatisticsGUI "+e);
        }
        
        this.text.setText(this.toString());
        this.text.select(0,0);
        this.setVisible(true);
    }
    
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

    public void setCloseType(int cl) {
	this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
	return this.getDefaultCloseOperation();
    }
	
    //Dump the data to a string.
    @Override
    public String toString(){
        String output = new String("AmmoDataBase.Bullets:\n");
        output = output + "             Total number of records in AmmoDataBase.Bullets:"+adtc+"\n";
        output = output + "                     Total number of records marked as empty:"+ade+"\n";
        output = output + "                 Total number of records marked as not empty:"+adne +"\n\n"; 
        output = output + "AmmoDataBase.Cases:\n";
        output = output + "                         Total number of records in database:"+cstc+"\n";
        output = output + "                     Total number of records marked as empty:"+cse+"\n";
        output = output + "                 Total number of records marked as not empty:"+csne+"\n\n";
        output = output + "AmmoDataBase.Primer:\n";
        output = output + "                         Total number of records in database:"+prtc+"\n";
        output = output + "                     Total number of records marked as empty:"+pre+"\n";
        output = output + "                 Total number of records marked as not empty:"+prne+"\n\n"; 
        output = output + "AmmoDataBase.Powder:\n";
        output = output + "                         Total number of records in database:"+prtc+"\n";
        output = output + "                     Total number of records marked as empty:"+pre+"\n";
        output = output + "                 Total number of records marked as not empty:"+prne+"\n\n"; 
        output = output + "AmmoDataBase.ManufacturedAmmo:\n";
        output = output + "                         Total number of records in database:"+pwtc+"\n";
        output = output + "                     Total number of records marked as empty:"+pwe+"\n";
        output = output + "                 Total number of records marked as not empty:"+pwne+"\n\n"; 
        output = output + "AmmoDataBase.ammolist:\n";
        output = output + "                         Total number of records in database:"+altc+"\n";
        output = output + "                     Total number of records marked as empty:"+ale+"\n";
        output = output + "                 Total number of records marked as not empty:"+alne+"\n\n"; 
        output = output + "AmmoDataBase.BulletMolds:\n";
        output = output + "                         Total number of records in database:"+bmtc+"\n\n"; 
        output = output + "AmmoDataBase.reloadingdies:\n";
        output = output + "                         Total number of records in database:"+rdtc+"\n\n"; 
        output = output + "BoundBook.BoundBookPage:\n";
        output = output + "                         Total number of records in database:"+bbtc+"\n\n"; 
        output = output + "GunList.GunsInList:\n";
        output = output + "                         Total number of records in database:"+gltc+"\n\n"; 
        output = output + "AmmoDataBase.ChronographData:\n";
        output = output + "                         Total number of records in database:"+cdtc+"\n\n"; 

        return output;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }

    //Alerts the user if error happen.
    private void AlertDialog(String t) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(this, t, "ALERT!", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String ARGV[]){
        DataBaseConnData dbcd = new DataBaseConnData();
        DataBaseConn dbc = new DataBaseConn(dbcd);
        StatisticsGUI sgui = new StatisticsGUI(dbc);   
        System.out.println(sgui);    
    }
}

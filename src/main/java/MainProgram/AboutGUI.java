/****************************************************************************************************************
 * AboutGUI.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 12-30-24																								*
 *  																											*
 * This is the About GUI program. It display the information about the program to the user. 																								*
 * 																												*
 ***************************************************************************************************************/
package MainProgram;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class AboutGUI extends JDialog implements ActionListener {
	
    private static final long serialVersionUID = 1L;
    private final int XSIZE = 350;
    private final int YSIZE = 220;
    private JButton okButton = new JButton("OK");
    private JLabel titleLbl = new JLabel("Todd's Ammunition and Firearm Database",SwingConstants.CENTER);
	
    public AboutGUI() {
	this.setTitle("About");
	this.setResizable(false);
	this.setSize(XSIZE, YSIZE);
	this.makeIcon();
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	//Labels and text fields.
	this.setLayout(new GridBagLayout());
	okButton.addActionListener(this);
		
	GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        //c.ipady = 1;
        
        c.anchor = GridBagConstraints.PAGE_START 	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 2;
        titleLbl.setFont(new Font("Calibri", Font.BOLD, 16));
        add(titleLbl, c);

        c.anchor = GridBagConstraints.LINE_START	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("       ") , c);        
        c.anchor = GridBagConstraints.CENTER	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("Author: Todd Brenneman") , c);
        c.anchor = GridBagConstraints.LINE_END	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("       ") , c);        
        
        c.anchor = GridBagConstraints.LINE_START	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("       ") , c);        
        c.anchor = GridBagConstraints.CENTER	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;
        add(new JLabel("Version: 1.0") , c);
        c.anchor = GridBagConstraints.LINE_START	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("       ") , c);        
        
        c.anchor = GridBagConstraints.LINE_START	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("       ") , c);        
        c.anchor = GridBagConstraints.CENTER	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 3;
        c.gridheight = 1;
        add(new JLabel("Date Created: 8 May 2024") , c);
        c.anchor = GridBagConstraints.LINE_START	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("       ") , c);        
        
        c.anchor = GridBagConstraints.LINE_START	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;  
        add(new JLabel("       ") , c);        

        c.anchor = GridBagConstraints.CENTER	;
        c.insets = new Insets(0,2,5,5);
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(okButton, c);        
    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
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

    //used to control what happens when the window is closed. 
    public void setCloseType(int cl) {
	this.setDefaultCloseOperation(cl);
    }

    //used to return what happens when the window is closed. 
    public int getCloseType() {
	return this.getDefaultCloseOperation();
    }

    private void makeIcon() {
	try {
            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(0, index);
            String ammoIconPath = filepath + "About.jpg";
            File myObj = new File(ammoIconPath);
            BufferedImage image = ImageIO.read(myObj);
	    this.setIconImage(image);
	} catch (IOException e) {
	    System.err.println("Error creating icon: "+e);
	}
    }   
	
    public static void main(String[] args) {
        AboutGUI AGUI = new AboutGUI();

        AGUI.setCloseType(DISPOSE_ON_CLOSE);
        AGUI.setVisible(true);
    }
}

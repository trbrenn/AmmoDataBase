/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainProgram;

import javax.swing.SwingUtilities;

/**
 *
 * @author trbrenn
 */
public class AmmoDataBase {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                MainWindow adm = new MainWindow();
                adm.setVisible(true);
            }
        });
        
    }    
}

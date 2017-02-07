/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objmatrixt;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javagames.util.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author carol_8wybosj
 */
public class ObjMatrixT {

    /**
     * @param args the command line arguments
     */
    public static VectorGraphic app;
    private FrameRate frameRate;
   
   public static void main(String[] args) throws IOException {
       app = new VectorGraphic();
        if(app!=null){   
      app.addWindowListener( new WindowAdapter() {
        
          public void windowClosing( WindowEvent e ) {
            app.onWindowClosing();
         }
      });
      
      SwingUtilities.invokeLater( new Runnable() {
         public void run() {
             app.createAndShowGUI();      
         }
         
      });
      
        } 
   }
    
}

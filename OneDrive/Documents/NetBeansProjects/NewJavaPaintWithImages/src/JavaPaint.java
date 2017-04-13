/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapaint;


import Images.MyImageNew;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author carol_8wybosj
 */
public class JavaPaint {

 
    /**
     *MAIN
     */
    public static Create app;
   private FrameRate frameRate;
   
   public static void main(String[] args) throws IOException {
       app = new Create();
      
         UIManager.put("swing.boldMetal", Boolean.FALSE); 
            
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cannoncommand;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javagames.timeandspace.CannonControl;
import javax.swing.SwingUtilities;
import javagames.util.*;
import javagame.*;

/**
 *
 * @author carol_8wybosj
 */
public class CannonCommand {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
		final CannonControl app = new CannonControl();
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				app.onWindowClosing();
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				app.createAndShowGUI();
			}
		});
	}
    
}

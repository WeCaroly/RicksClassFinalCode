package objmatrixt;


import java.awt.Color;
import java.awt.Graphics;
import static javafx.scene.paint.Color.color;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carol_8wybosj
 */
public class VectorObject implements Drawable{
	public Vector2f[] objects;
        public Matrix3x3f[] world;
        
        int location;
        int scale;
        Color color;    

    VectorObject(int i) {
        
        switch(i){
            case 1: //tri
                objects = new Vector2f[] { new Vector2f(10, 0), new Vector2f(0, 10),
				new Vector2f(-10, 0), new Vector2f(0, -10), };
                break;
            case 2: // hex
                objects = new Vector2f[] { new Vector2f(10, 0), new Vector2f(0, 10),
				new Vector2f(-10, 0), new Vector2f(0, -10), };
                break;
            case 3: //square
                objects = new Vector2f[] { new Vector2f(10, 0), new Vector2f(0, 10),
				new Vector2f(-10, 0), new Vector2f(0, -10), };
                break;
        }
        //add shape 
        updateWorld();
    }
        
    @Override
    public void updateWorld() {
      //add shape to world
        
      // world = new Vector2f[objects.length];
        //update world matrix
        
    }

    @Override
    public void render(Graphics g) {
        //Vector2f S = world[world.length - 1];
        //Vector2f P = null;
        ////adds to screen
        //for (int i = 0; i < world.length; ++i) {
          //  P = world[i];
           // g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            //S = P;
	//}        
        
        
        
         // draw the sun...
        Matrix3x3f sunMat = Matrix3x3f.identity();
        sunMat = sunMat.mul(Matrix3x3f.translate(1280 / 2, 720 / 2));
        Vector2f sun = sunMat.mul(new Vector2f());

        
        
    }
    
    
}

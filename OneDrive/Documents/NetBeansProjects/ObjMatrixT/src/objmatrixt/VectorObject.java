package objmatrixt;


import java.awt.Color;
import java.awt.Graphics;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;


/**
 *
 * @author carol_8wybosj
 */
public class VectorObject implements Drawable{
	public Vector2f[] lines;
        public Matrix3x3f world;
        Vector2f centerLocation;
        Float scale;
        Color color;    
        float rotation;
        //Added
        int widthScreen, heightScreen;
        int widthScreenNeg, heightScreenNeg;

    VectorObject(int i, Color color) {
        world = Matrix3x3f.identity();
        centerLocation = new Vector2f();
        setColor(color);
        switch(i){
            case 1: //tri
                lines = new Vector2f[] { new Vector2f(-10, 0), new Vector2f(0, -10),
				new Vector2f(10, 0) };
                break;
            case 2: // hex
                lines = new Vector2f[] { new Vector2f(0, 0), new Vector2f(0, 10),
				new Vector2f(8, 16), new Vector2f(16, 10), 
                                new Vector2f(16, 0), new Vector2f(8, -6) };
                
                               
                break;
            case 3: //square
                lines = new Vector2f[] { new Vector2f(-10, 10), new Vector2f(10, 10),
				new Vector2f(10, -10), new Vector2f(-10, -10) };
                break;
        }
     }
        
    @Override
    public void updateWorld() {
              
        
       world = Matrix3x3f.scale(scale,scale);
                  
        world = world.mul(Matrix3x3f.rotate(rotation));
        
        world = world.mul(Matrix3x3f.translate(centerLocation));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        
        for(int x = 0; x < lines.length-1; x++){
            g.drawLine((int)world.mul(lines[x]).x,(int)world.mul(lines[x]).y, 
        			(int)world.mul(lines[x+1]).x, (int)world.mul(lines[x+1]).y);
        }
        g.drawLine((int)world.mul(lines[lines.length-1]).x, (int)world.mul(lines[lines.length-1]).y, 
            (int)world.mul(lines[0]).x, (int)world.mul(lines[0]).y);
    }
    
    public void setScreen(int h, int w){
        heightScreen = h;
        widthScreen = w;
        heightScreenNeg =  -h;
        widthScreenNeg = -w;
    }
    
    public void setWord(Matrix3x3f world){
        this.world = world;
    }
    
    public void setVectorLocation(float locationX, float locationY){
        centerLocation.x = locationX;
        centerLocation.y = locationY;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public void setRotation(float rotation){
           this.rotation = rotation;
    }
    
    public void setScale(float scale){
        this.scale = scale;
    }
}

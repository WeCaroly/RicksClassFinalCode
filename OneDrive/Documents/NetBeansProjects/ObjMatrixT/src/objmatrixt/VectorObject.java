package objmatrixt;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import static javafx.scene.paint.Color.color;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;


/**
 *
 * @author carol_8wybosj
 */
public class VectorObject implements Drawable{
	public Vector2f[] lines;
        public Matrix3x3f world;
        Point point = new Point(0,0);
        Point location;
        Float scale;
        Color color;    
        float rotation = 0.3f;
        //Added
        int widthScreen, heightScreen;
        int widthScreenNeg, heightScreenNeg;

    VectorObject(int i, Color color) {
        world = Matrix3x3f.identity();
        setColor(color);
        switch(i){
            case 1: //tri
                lines = new Vector2f[] { new Vector2f(10, 0), new Vector2f(0, 10),
				new Vector2f(0, 0) };
                break;
            case 2: // hex
                lines = new Vector2f[] { new Vector2f(0, 0), new Vector2f(0, 10),
				new Vector2f(8, 16), new Vector2f(16, 10), 
                                new Vector2f(16, 0), new Vector2f(8, -6) };
                break;
            case 3: //square
                lines = new Vector2f[] { new Vector2f(10, 0), new Vector2f(0, 10),
				new Vector2f(-10, 0), new Vector2f(0, -10) };
                break;
        }
     }
        
    @Override
    public void updateWorld() {
        //multiplys the world matrix by the rotation value set
        world = world.mul(Matrix3x3f.rotate(rotation));
        //sclaes the world by the sale set 
        world = Matrix3x3f.scale(scale,scale);
        //translated vector to matrix obj
        Matrix3x3f temp = Matrix3x3f.translate(new Vector2f(location.x,location.y));
        world = world.mul(temp);
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
        heightScreenNeg =  h - (h+h);
        widthScreenNeg = w - (w+w);
    }
    
    public void setWord(Matrix3x3f world){
        this.world = world;
    }
    
    public void setLocation(Point location){
        this.location = location;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public void setRotation(float rotation){
           this.rotation = rotation;
           System.out.print("Rotation In:"+rotation+"=\n");
    }
    public void setScale(float scale){
        this.scale = scale;
    }
}

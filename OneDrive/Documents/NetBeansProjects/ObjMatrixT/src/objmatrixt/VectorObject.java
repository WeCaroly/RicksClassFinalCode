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
	public Vector2f[] objects;
        public Matrix3x3f[] world;
        Point point = new Point(0,0);
        int location;
        int scale;
        Color color;    
        int rotation;
        //Added
        int widthScreen, heightScreen;
        int widthScreenNeg, heightScreenNeg;
        
        

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
     }
        
    @Override
    public void updateWorld() {
    
      //Two transgormations 
        // move and multiply
      
    }

    @Override
    public void render(Graphics g) {
       
        Vector2f S;
        //S = world[world.length - 1];
        Vector2f P = null;
        //adds to screen
        for (int i = 0; i < world.length; ++i) {
         //  P = world[i];
         //   g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            S = P;
	}        
        
       
        g.drawLine(point.x + 10, point.y, point.x, point.y);
        g.drawLine(point.x, point.y + 10, point.x, point.y);
        g.drawLine(point.x, point.y, point.x - 10, point.y);
        g.drawLine(point.x, point.y, point.x, point.y - 10);

        
        
         /* draw the sun...
        Matrix3x3f sunMat = Matrix3x3f.identity();
        sunMat = sunMat.mul(Matrix3x3f.translate(1280 / 2, 720 / 2));
        Vector2f sun = sunMat.mul(new Vector2f());
        */
    }
    
    public void move(float x, float y){
        if(objects != null){
            if((x < widthScreen && x > widthScreenNeg)
                    &&(y < widthScreen && y > widthScreenNeg)){
                //TODO: CHECK CORRECTNESS
                for(int i = 0; i < objects.length;++i){
                    objects[i].translate(x, y);
                }
            } 
        }
    }
    public void updatePoint(Point point){
        this.point = point;
    }
    public void setScreen(int h, int w){
        heightScreen = h;
        widthScreen = w;
        heightScreenNeg =  h - (h+h);
        widthScreenNeg = w - (w+w);
    }
    
    public void setWord(Matrix3x3f[] world){
        this.world = world;
    }
    
    public void setLocation(int location){
        this.location = location;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public void setRotation(int rotation){
           this.rotation = rotation;
    }
    public void setScale(int scale){
        this.scale = scale;
    }
}

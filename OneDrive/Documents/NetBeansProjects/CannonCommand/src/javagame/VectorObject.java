package javagame;


import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;


/**
 *
 * @author carol_8wybosj
 */
public class VectorObject{
    private static final int SCREEN_W = 1280;
    private static final int SCREEN_H = 720;
	public Vector2f[] lines;
        public Matrix3x3f world;
        public Vector2f centerLocation;
        Float scale = (float)2;
        Color color;    
        float rotation;
        
        public float ty,tx;
        int width;
        int height;

    public VectorObject(int i, Color color) {
        world = Matrix3x3f.identity();
        centerLocation = new Vector2f();
        setColor(color);
        tx=0;
        ty=0;
        switch(i){
            case 1: //tri
                lines = new Vector2f[] { new Vector2f(0, 10), new Vector2f(10, -10),
				new Vector2f(-10, -10) };
                break;
            case 2: // hex   
            lines = new Vector2f[]{new Vector2f(-5, 5), new Vector2f(5, 5)
                                ,new Vector2f(7, 0), new Vector2f(5, -5)
                                ,new Vector2f(-5, -5), new Vector2f(-7, 0)                                
                                        };
          
                //drawPolygon();
                               
                break;
            case 3: //square
                lines = new Vector2f[] { new Vector2f(-10, 10), new Vector2f(10, 10),
				new Vector2f(10, -10), new Vector2f(-10, -10) };
             
                break;
        
        case 4: 
        lines = new Vector2f[] { new Vector2f(-13, 0), new Vector2f(-13, -6),
				new Vector2f(-6, -7), new Vector2f(-6, -12),
                                new Vector2f(6, -12), new Vector2f(6, -7),
				new Vector2f(13, -6), new Vector2f(13, 0) };
        break;
        }        
     }
        
    private void drawPolygon() {
		Vector2f P;
		Vector2f S = lines[lines.length - 1];
		for (int i = 0; i < lines.length; ++i) {
			P = lines[i];
			lines = new Vector2f[]{new Vector2f(S.x, S.y), new Vector2f(P.x, P.y)};
			S = P;
		}
	}
    
    
    /**
     *  Updates the world and translates the old shape with new positions
     */
    public void updateWorld() {
        Random rand = new Random();
        if(centerLocation.y > SCREEN_H+25){    
                    centerLocation.y = -25;
                   centerLocation.x = rand.nextInt(SCREEN_W);
                   setVectorLocation(centerLocation.x,centerLocation.y);            
        }
    
        
        world = Matrix3x3f.scale(scale,scale);
              
        world = world.mul(Matrix3x3f.rotate(rotation));
        
        world = world.mul(Matrix3x3f.translate(centerLocation));
    }

    /**
     *  Writes the shapes to the screen from the world vectors
     * @param g
     */
    public void render(Graphics g) {
        g.setColor(color);
        
        for(int x = 0; x < lines.length-1; x++){
            g.drawLine((int)world.mul(lines[x]).x,(int)world.mul(lines[x]).y, 
        			(int)world.mul(lines[x+1]).x, (int)world.mul(lines[x+1]).y);
        }
        g.drawLine((int)world.mul(lines[lines.length-1]).x, (int)world.mul(lines[lines.length-1]).y, 
            (int)world.mul(lines[0]).x, (int)world.mul(lines[0]).y);
    }
 
    /**
     * Sets the vector location from the two floats
     * @param locationX
     * @param locationY
     */
    public void setVectorLocation(float locationX, float locationY){
        centerLocation.x = locationX;
        centerLocation.y = locationY;
    }
    
    /**
     * sets the color of the shape 
     * @param color
     */
    public void setColor(Color color){
        this.color = color;
    }
    
    /**
     * changes the rotation of the object
     * @param rotation
     */
    public void setRotation(float rotation){
           this.rotation = rotation;
    }
    
    /**
     * sets the scale of transformation
     * @param scale
     */
    public void setScale(float scale){
        this.scale = scale;
    }
    
    /**
     * checks in the point is in the building
     * @param widthInput coordinates of object
     * @param heightInput coordinates of object
     * 
     * @return boolean true if point is in the city 
    */
    public boolean cointainsThePoint(int widthInput, int heightInput){
        int width2 = width + widthInput;
        int height2 = height + heightInput;
        return((widthInput < width2 && widthInput > width2)&&(height < heightInput));
    } // center? 
 
    
    public void clicked(int i){
        //if clicked meteor and not city
        remove(i);
    }
    
    public void remove(int i){
        //from array of meteors remove
    }
}
/**
 * VactorObject bounded subclass 
 * that adds bounder box capability 
 * 
 * Create a seperate class for bounded box and bounded circle classes
 * 
 * then our vector objectBouneded
 * can have data mentiors made our of bounded boxes
 * 
 * the box and circle themselves are interChangeaboe 
 * must play nicely
 * 
 * have instance of box that can take either circle or box
 * 
 * something must tie it together/ relates direcly
 * 
 * 1 create abstract parent class called bounding shape
 *  everything common between both
 *      -data member and behavoirs,
 *      -intersects method returns boolean, check boundeing shapes 
 *      intersects(box) or overoad to intercets(circle)
 *         > both are realted to bounding shape
 *          intersects(boundingShapes)
 *     -move around 
 *          setPos(vector2f)
 *          inc X(float) incY(float )  incPos(Vector2f)((does both)) setPos(Vector2f)
 * ALL ABOVE ABSTRACT
 *  no data mamber at are bastract so make it an INTERFACE
 * /////////////////////////////
 * Bounding Shape
 * BoundingBox
 *  -Vector2f max
 *  Vector2f min
 * boundingCirc
 *  -float r
 *    Vector2f center
 * 
 * in intesection(BoundShapre){
 *   use instance of for box or shape
 * comparision 
 * 
 * VectorObjcet{
 *  what to but in here to make it bounded and not just an object
 *          -define bounds by :
 *              collection of bounding shapes
 *              
 *           *   might create boundingShape for exterior
 *            *      then one for interior,
 *          more flexible then:
 *          could just use interior and call outside 0
 * 
 *              can set to null if there is no bounds, make sure to check the null when check
 * 
 *    - check if one intersects with another intercets that bounded
 *          - has it;s own intersects(VectorObject){
 *      do both of the vocal and pass have exior? if both not null check outer bunding shapes if they do visa versa
 * }
 *      - could also  but the bounding stuff for sprites will also have bounding shapes interior and extior intercts ect. 
 *          - all will be the same so extract bounding stuff and be it;s own class 
 *          -Bounds manager, so all have instance of boundingmanager
 *          -less to maintain
 *          -
 * 
 * }
 * 
    */
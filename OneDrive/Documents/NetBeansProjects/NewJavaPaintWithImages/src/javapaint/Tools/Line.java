package javapaint.Tools;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import javapaint.Create;
import javapaint.CreateInput;

/**
 *  Line Tool
 * @author carol_8wybosj
 */
public class Line extends Tools{

    /**
     * Clearing 
     */
    @Override
    public void clear() {
        shapes.clear();
        color.clear();
    }

    /**
     * Pointer Pair class to store points of the lines
     */
    public class PointPair
    {
        Point left;
        Point right;
        
        /**
         *
         * @param l
         * @param r
         */
        public PointPair(Point l, Point r)
        {
            left = l;
            right = r;
        }
    }
    
    private ArrayList<PointPair> shapes = new ArrayList<PointPair>();
    private ArrayList<Color> color = new ArrayList<Color>();

    /**
     * Starting point
     */
    private Point str,    

    /**
     *Ending point
     */
    end;    
    private Point point;
    private CreateInput mouse;
    
    /**
     * setter of start
     * @param str
     */
    public void setSTR(Point str){
        this.str = str;
    }

    /**
     * setter of end point
     * @param end
     */
    public void setEND(Point end){
        this.end = end;
    }
     
    /**
     * adds the canvas to the object
     * @param canvas
     */
    public Line(Canvas canvas){
    mouse = new CreateInput(canvas);
  }     
      
    
    /**
     * Save for the lines
     * @param g Graphic passed in for the current color
     */
    public void save(Graphics g){
         color.add(g.getColor());
         shapes.add(new PointPair(str,end));
    }
     
    /**
     * Renders the lines to the screen
     * @param g
     */
    public void AddShapes(Graphics g){
    for (int i = 0; i < shapes.size(); i++){
            // Adding a null into the list is used
            // for breaking up the lines when
            // there are two or more lines
            // that are not connected
           if (!(shapes == null)&&color.get(i)!=null) {
                g.setColor(color.get(i));
                g.drawLine(shapes.get(i).left.x, shapes.get(i).left.y, 
                        shapes.get(i).right.x, shapes.get(i).right.y);
                
            }
        }
    }

}

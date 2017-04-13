package javapaint.Tools;

import java.awt.Canvas;
import java.awt.Color;
import static java.awt.Color.RED;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import javapaint.CreateInput;

/**
 * Free Draw Tool
 * @author carol_8wybosj
 */
public class FreeDraw extends Tools{

    private ArrayList<Point> lines = new ArrayList<Point>();
    private ArrayList<Color> color = new ArrayList<Color>();
    private Color colorNow;
    
    /**
     * Unused 
     * @param canvas
     */
    public FreeDraw(Canvas canvas){}  

    /**
     * sets the array line points when needing a null
     * @param p
     */
    public void setArrayLines( Point p) {
        lines.add(p);
        color.add(Color.RED);
    }
    
    /**
     * Draws the lines to the screen and saves
     * @param g
     * @param mouse
     */
    public void draw(Graphics g, CreateInput mouse) {
            lines.add(mouse.getPosition());
            color.add(g.getColor());
    }   
    
    /**
     * Rendering the lines to the screen
     * @param g
     */
    public void AddLines(Graphics g){
    for (int i = 0; i < lines.size() - 1; i++) {
            Point p1 = lines.get(i);
            Point p2 = lines.get(i + 1);            
      
            // Adding a null into the list is used
            // for breaking up the lines when
            // there are two or more lines
            // that are not connected
            if (!(p1 == null || p2 == null)&&color.get(i)!=null) {
                g.setColor(color.get(i));
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    /**
     * Clear for the free draw
     */
    @Override
    public void clear() {
        lines.clear();
        color.clear();
    }
    
}

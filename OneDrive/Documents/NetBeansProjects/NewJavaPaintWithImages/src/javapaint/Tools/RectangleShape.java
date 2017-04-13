/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapaint.Tools;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javapaint.CreateInput;

/**
 * Rectangle object
 * @author carol_8wybosj
 */
public class RectangleShape extends Tools{
    
    private final ArrayList<Rectangle> shapesBuffer = new ArrayList<>();
    private final ArrayList<Rectangle> shapes = new ArrayList<>();
    private final ArrayList<Color> color = new ArrayList<>();

    public Point str = new Point(0, 0), end = new Point(0, 0);    
    private final Point point = new Point(0, 0);
    private final CreateInput mouse;
    Rectangle rectangle;
    
    /**
     * setter of the end point
     * @param end
     */
    public void setEND(Point end){
        this.end = end;
    }
 
    /**
     * getting the canvas for the object
     * @param canvas
     */
    public RectangleShape(Canvas canvas){
        mouse = new CreateInput(canvas);
    }      
    
    /**
     * setter for the start point
     * @param str
     */
    public void setSTR(Point str){
        this.str = str;
    }
    
    /**
     * Saves the Rectangle to the ArrayList
     * @param g
     */
    public void save(Graphics g){
        int xValue = Math.min(str.x, end.x);
        int yValue = Math.min(str.y, end.y);
        int width = Math.abs(str.x - end.x);
        int height = Math.abs(str.y - end.y);
        rectangle = new Rectangle(xValue, yValue, width, height);
       
        color.add(g.getColor());
        shapes.add(rectangle);        
    }
    
   /**
     * Renders the lines to the screen
     * @param g
     */
    public void AddShapes(Graphics g){
      
        for (int i = 0; i < shapes.size(); i++) {
              Rectangle r = shapes.get(i);
            // Adding a null into the list is used
            // for breaking up the lines when
            // there are two or more lines
            // that are not connected
                if (!(r == null)&&color.get(i)!=null) {
                    g.setColor(color.get(i));   
                    g.drawRect(r.x, r.y, r.width, r.height);
                }
            }
    }

    /**
     *Clears the screen
     */
    @Override
    public void clear() {
        shapes.clear();
        color.clear();
    }
}

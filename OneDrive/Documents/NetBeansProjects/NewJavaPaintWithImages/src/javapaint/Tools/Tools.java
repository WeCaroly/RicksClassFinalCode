/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapaint.Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javapaint.CreateInput;

/**
 * Super class to all of the tool objects
 * @author carol_8wybosj
 */
public abstract class Tools {
    
    private Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};
        
    protected abstract void clear();
    
    public Color getColor(int index){
        if(index<COLORS.length || index>-1)
             return COLORS[index];   
        return COLORS[0];
    }    
      
}


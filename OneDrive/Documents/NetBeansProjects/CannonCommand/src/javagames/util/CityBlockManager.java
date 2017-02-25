/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagames.util;

import static java.awt.Color.BLUE;
import java.util.ArrayList;
import javagame.VectorObject;

/**
 *
 * @author carol_8wybosj
 */
public class CityBlockManager {
    public VectorObject city[];
    
    public void removebuilding(int i){
        city[i] = null;
    }
    
    public void cityMaker(){
        ArrayList<VectorObject> cityBlock = null;
        VectorObject vector;
        for(int x = 0;x<15;x++){
            vector = new VectorObject(4,BLUE);
        cityBlock.add(vector);
        }
    }
    
    public boolean hitBuilding(int vectorObject){
        //remove the vectorObject from the city if it is destroyed
        return true;
    }  
}

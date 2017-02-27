/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagames.util;

import static java.awt.Color.BLUE;
import java.awt.Graphics;
import java.util.ArrayList;
import javagame.VectorObject;

/**
 *
 * @author carol_8wybosj
 */
public class CityBlockManager {
    public VectorObject city[];
    int count = 21;
    
    public CityBlockManager(){
        city = new VectorObject[21];
        cityMaker();
    }
    
    public void removeBuilding(int i){
        city[i] = null;
        count--;
    }
    
    public int countCity(){
        return count;
    }
    
    public void cityMaker(){
        VectorObject vector;
        int location = 30;
        int maxh = 720;
        for(int x = 0; x < city.length; x++)
        {
            vector = new VectorObject(4,BLUE);
            city[x] = vector;
            city[x].setVectorLocation(location, maxh);
            location += 60;
        }
    }
    public int length(){return city.length;}
    
    public void updateWorld(){
        for(int i=0;i<city.length;i++){
            if (city[i] != null)
            {
                city[i].updateWorld();
            }
        }
    }        
    
    public void render(Graphics g){
        for(int i=0;i<city.length;i++){
            if (city[i] != null)
            {
                city[i].render(g);
            }
        }
    }
    
    public boolean hitBuilding(ArrayList<VectorObject> meteoroids){
        
        for (int i = 0; i < meteoroids.size(); i++)
        {
            if (meteoroids.get(i).centerLocation.y >= 700)
            {
                for (int j = 0; j < city.length; j++)
                {
                    if (city[j] != null)
                    {
                        if (meteoroids.get(i).centerLocation.x <= city[j].centerLocation.x + 30
                                && meteoroids.get(i).centerLocation.x >= city[j].centerLocation.x - 30)
                        {
                            removeBuilding(j);
                        }
                    }
                }
            }
        }
        //remove the vectorObject from the city if it is destroyed
        return true;
    }    
    
}

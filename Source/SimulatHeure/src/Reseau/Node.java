/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;
import java.util.*;
/**
 *
 * @author rem54
 */
public class Node {
    
    public Node(int argX, int argY){
        positionX = argX;
        positionY = argY;
        isStation = false;
        numberOfRoutes = 0;
        numberOFPassengers = 0;
        listLines = new ArrayList<Line>();
    }
    
    public void addLine(Line l){
        listLines.add(l);
    }
    
    public int getPositionX(){
        return positionX;
    }
    
    public int getPositionY(){
        return positionY;
    }
    
    public void setPositionX(int x_arg){
        positionX = x_arg;
    }
    
    public void setPositionY(int y_arg){
        positionY = y_arg;
    }
    
    private int positionX;
    private int positionY;
    public Boolean isStation;
    
    
    //Station
    
     public void setName(String newName){
         if(isStation){
        name = newName;
         }
    }
    
    //not used yet
     
     public void setStation(String arg_nom){
         isStation = true;
         name = arg_nom;
     }
     
     public void deleteStation(){
         isStation = false;
         name = null;
         numberOFPassengers = 0;
         numberOfRoutes = 0;
     }
     
    public void setNumberOfPassengers(int number){
         if(isStation){
            numberOFPassengers = number;
         }
    }

    public void setNumberOfCircuit(int n){
  
        if (n == 1){
            numberOfRoutes++;
        }
        if (n == -1){
            numberOfRoutes--;
        }
         
    }
    
    public int getNumberOfPassengers(){
         if(isStation){
        return numberOFPassengers;
         }
         return 0;
    }
    
    public String getName(){
         if(isStation){
        return name;
         }
         return null;
    }
    

    public int getNumberOfRoutes(){

        return numberOfRoutes;

    }
    
    public List<Line> listLines;
    private String name;
    private int numberOfRoutes;
    private int numberOFPassengers;
}

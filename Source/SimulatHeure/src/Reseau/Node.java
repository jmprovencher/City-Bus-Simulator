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
        listLines = new ArrayList<Line>();
        listRoutes = new ArrayList<Route>();
        listPassenger = new ArrayList<Passenger>();
        listBus = new ArrayList<Bus>();
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

         numberOfRoutes = 0;
     }


    public void setRoute(int n, Route r){
  
        if (n == 1){
            numberOfRoutes++;
            listRoutes.add(r);
        }
        if (n == -1){
            numberOfRoutes--;
            listRoutes.remove(r);
        }
         
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
    
    public void addPassenger(Passenger p){
        listPassenger.add(p);
    }
    
    public void addBus(Bus b){
        listBus.add(b);
    }
    
      public void removeBus(Bus b){
        listBus.remove(b);
    }
    
    public void removePassenger(Passenger p){
        listPassenger.remove(p);
    }
    
    public List<Route> listRoutes;
    public List<Line> listLines;
    public List<Passenger> listPassenger;
    public List<Bus> listBus;
    private String name;
    private int numberOfRoutes;

}

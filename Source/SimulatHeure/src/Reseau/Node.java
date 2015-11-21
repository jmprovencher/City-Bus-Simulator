/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;
import java.util.*;
/**
 *
 * @author Rémi
 */
public class Node extends  LocationData implements java.io.Serializable{
    
    public Node(int argX, int argY){
        positionX = argX;
        positionY = argY;
        isStation = false;
        listLines = new ArrayList<Line>();
        listRoutes = new ArrayList<Route>();
        listPassenger = new ArrayList<Passenger>();
        listBus = new ArrayList<Bus>();
    }
    
    public void addLine(Line l){
        listLines.add(l);
    }
    

    @Override
    public double getPositionX(){
        return positionX;
    }
    
    @Override
    public double getPositionY(){
        return positionY;
    }
    
    @Override
    public void setPositionX(double x){
        positionX = x;
    }
    
    @Override
    public void setPositionY(double y){
        positionY = y;
    }
    
    private double positionX;
    private double positionY;
    public Boolean isStation;
    
    // *****
    //-----------------Station------------------//
    // *****
     public void setName(String newName){
         if(isStation){
        name = newName;
         }
    }
    
     public void setStation(String arg_nom){
         isStation = true;
         name = arg_nom;
     }
     
     public Boolean deleteStation(){
         if (listRoutes.isEmpty()){
            isStation = false;
            name = null;
            return true;
         }
         return false;
     }


    public void setRoute(int n, Route r){
  
        if (n == 1){
            if(!listRoutes.contains(r)){
                listRoutes.add(r);
            }
            
        }
        if (n == -1){
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

        return listRoutes.size();

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

}

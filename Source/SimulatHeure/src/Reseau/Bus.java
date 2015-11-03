/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;
import java.util.*;

/**
 *
 * @author Sam
 */
public class Bus implements java.io.Serializable{
    public Bus(int numeroArg, Route circuitActuelArg, int arg_capacite){
     
        number = numeroArg;
        route = circuitActuelArg;
        positionX = route.getNodeFromIndex(0).getPositionX();
        positionY = route.getNodeFromIndex(0).getPositionY();
        capacity = arg_capacite;
        listPassenger = new ArrayList<Passenger>();
        lastNodeIndex = 0;
        speed = route.getLineFromIndex(0).speed;
        timeNextNode = (getNextNodeDistance()/speed);
        actualNode = circuitActuelArg.getNodeFromIndex(0);
        actualNode.addBus(this);
    }
    
    
    public void updateSpeed(){
        Line a = route.getLineFromIndex(lastNodeIndex);
        if (a != null){
            speed = a.speed;
        }
        else{
            speed = 0;
        }
    }
    
    public void setNode(Node n){
        actualNode = n;
        setPositionX(n.getPositionX());
        setPositionY(n.getPositionY());
        lastNodeIndex++;
        updateTimeNextNode();
        updateSpeed();
        if (n.isStation){
            n.addBus(this);
        
            
        }
        for (Passenger p: listPassenger){
            p.nodePast();
        }
    }
    
    public void exitNode(){
        if (actualNode.isStation){
            actualNode.removeBus(this);
            actualNode = null;
        }
    }

    
    public double reqSpeed(){
        return speed;
    }

    public Route getRoute(){
        return route;
    }
   
    public int getCapacity()
    {
        return capacity;
    }
    
    public void setPositionX(double x)
    {
        positionX = x;
    }
    
    public void setPositionY(double y)
    {
        positionY = y;
    }
    
    // retourne le nb de passager qui ne sont pas entrer
    public void addPassenger(Passenger p){
        listPassenger.add(p);
    }
        
    public void removePassenger(Passenger p){
        listPassenger.remove(p);
    }
    
    public void updateTimeNextNode(){
        
        timeNextNode = (getNextNodeDistance()/speed);
    }
    

     public int getNumber(){
    
        return number;
    }
     
     public double getPositionX(){
    
        return positionX;
    }
          
     public double getPositionY(){
    
        return positionY;
    }
    
     public int getLastNodeIndex(){
         return lastNodeIndex;
     }
     
     
     public double getTimeNextNode(){
         return timeNextNode;
     }
     

     
     public void reset(){
         lastNodeIndex = 0;
         speed = route.getLineFromIndex(0).speed;
         timeNextNode = (getNextNodeDistance()/speed);
     }
     
     public double getNextNodeDistance(){
         double x1, x2, y1, y2;
         if (lastNodeIndex+1 == route.getNumberOfNodes()){
             return 0;
         }
         x1 = positionX;
         x2 = route.getNodeFromIndex(lastNodeIndex+1).getPositionX();
         y1 = positionY;
         y2 = route.getNodeFromIndex(lastNodeIndex+1).getPositionY();
         
         double distance = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
         return distance;
     }
    
    
    private double speed;
    private int number;
    private double positionX;
    private double positionY;
    private int capacity;
    public Node actualNode;
    private Route route;
    private int lastNodeIndex;
    private double timeNextNode;
    public List<Passenger> listPassenger;
    

}

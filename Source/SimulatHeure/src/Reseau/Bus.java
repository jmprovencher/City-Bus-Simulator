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
    public Bus(int numeroArg, Route startRoute, int maxCapacity, Route.Source sourceInit){
     
        number = numeroArg;
        route = startRoute;
        actualNode = sourceInit.originNode;
        positionX = actualNode.getPositionX();
        positionY = actualNode.getPositionY();
        source =sourceInit;
        capacity = maxCapacity;
        listPassenger = new ArrayList<Passenger>();
        if (route.canLoop && actualNode == route.getNodeFromIndex(0)){
            lastNodeIndex = 0;
        }
        else{
            lastNodeIndex = startRoute.route.lastIndexOf(actualNode);
        }
        speed = route.getLineFromIndex(lastNodeIndex).speed;
        timeNextNode = (getNextNodeDistance()/speed);

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
        System.out.println("sup");
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
         actualNode = source.originNode;

        if (route.canLoop){
            lastNodeIndex = 0;
        }
        else{
            lastNodeIndex = route.route.lastIndexOf(actualNode);
        }
         speed = route.getLineFromIndex(lastNodeIndex).speed;
         timeNextNode = (getNextNodeDistance()/speed);
         
         positionX = route.getNodeFromIndex(lastNodeIndex).getPositionX();
         positionY = route.getNodeFromIndex(lastNodeIndex).getPositionY();
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
    
    private Route.Source source;
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

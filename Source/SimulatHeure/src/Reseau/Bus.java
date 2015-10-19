/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;


/**
 *
 * @author Sam
 */
public class Bus {
    public Bus(int numeroArg, Route circuitActuelArg, int arg_capacite){
     
        number = numeroArg;
        route = circuitActuelArg;
        nodesPast = 1;
        positionX = route.getNodeFromIndex(0).getPositionX();
        positionY = route.getNodeFromIndex(0).getPositionY();
        capacity = arg_capacite;
        numberOfPassenger = 0;
        lastNodeIndex = 0;
        speed = route.getLineFromIndex(0).speed;
        timeNextNode = (getNextNodeDistance()/speed);
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
    
    public double reqSpeed(){
        return speed;
    }
    public void addNodesPast(){
        nodesPast++;
    }
    
    public int getNodesPast(){
        return nodesPast;
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
    public int addPassenger(int arg_nombre_passager){
        
        if (numberOfPassenger == capacity){
            return arg_nombre_passager;
        }
        
        if ((numberOfPassenger + arg_nombre_passager) < capacity){
            numberOfPassenger += arg_nombre_passager;
            return 0;
        }
        else{
            int restant = arg_nombre_passager - (capacity - numberOfPassenger);
            numberOfPassenger = capacity;
            return restant;
        }
    }
    
    public void getPassengerNumber(int arg_nombre_passager){
        
        numberOfPassenger -= arg_nombre_passager;
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
     
     //not used yet
     public int getNumberOfPassenger(){
         return numberOfPassenger;
     }
     
     public double getTimeNextNode(){
         return timeNextNode;
     }
     
     public void setLastNodeIndex(){
         lastNodeIndex++;
     }
     
     public void reset(){
         lastNodeIndex = 0;
         nodesPast = 1;
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
    
     //using right now....
    private double speed;
    private int number;
    private double positionX;
    private double positionY;
    private int capacity;
    private Route route;
    private int lastNodeIndex;
    private int nodesPast;
    private double timeNextNode;
    
    //not used yet
    private int numberOfPassenger;
}

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
    public Bus(int numeroArg, Route circuitActuelArg, int arg_capacite, Route.Source s){
     
        number = numeroArg;
        route = circuitActuelArg;
        actualNode = s.originNode;
        positionX = actualNode.getPositionX();
        positionY = actualNode.getPositionY();
        source =s;
        capacity = arg_capacite;
        listPassenger = new ArrayList<Passenger>();
        if (route.canLoop && actualNode == route.getNodeFromIndex(0)){
            lastNodeIndex = 0;
        }
        else{
            lastNodeIndex = circuitActuelArg.route.lastIndexOf(actualNode);
        }
        speed = route.getLineFromIndex(lastNodeIndex).speed;
        timeNextNode = (getNextNodeDistance()/speed);
        nodePastCount = 1;
        actualNode.addBus(this);
        positionInTime = new ArrayList<PositionBus>();

    }
    
    
    public void initPositionInTime(double tickTime){
        int datCount = 1;
                    double angle;
            double newX;
            double newY;
                        double originX = actualNode.getPositionX();
            double originY = actualNode.getPositionY();
        positionInTime.add(new PositionBus(positionX, positionY, 0));
        for (int i = 0; i<route.route.size() - 1; i++){
            Line l = route.getLineFromIndex(i);
            double relativeSpeed = l.speed * tickTime;
            Node previousNode = getRoute().getNodeFromIndex(i);
            Node nextNode = getRoute().getNodeFromIndex(i+1);

            int targetX = nextNode.getPositionX();
            int targetY= nextNode.getPositionY();
            double distance = Math.sqrt(Math.pow((targetX - originX),2)+ Math.pow((targetY - originY), 2));
            int numberOfTick = (int)( distance / relativeSpeed);
            if (distance % relativeSpeed != 0){
                numberOfTick++;
            }

            for (int y = 0; y  < numberOfTick; y++){
                angle = Math.atan((double)(targetY-originY)/(double)(targetX-originX));

                if (targetX == originX){
                    if (targetY > originY){
                        angle = -Math.PI/2;
                    }
                    else{
                        angle = (Math.PI/2);
                    }
                }

                if (targetX - originX <= 0){
                    newX = originX- (Math.cos(angle)*relativeSpeed);
                    newY = originY- (Math.sin(angle)*relativeSpeed);
                }
                else{
                    newX = (originX+ (Math.cos(angle)*relativeSpeed));
                    newY = (originY+ (Math.sin(angle)*relativeSpeed));
                }
                if (numberOfTick == y){
                    newX = targetX;
                    newY = targetY;
                }
                originX = newX;
                originY = newY;
                positionInTime.add(new PositionBus(newX, newY, datCount));
                datCount++;
            }
        }
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
        nodePastCount++;
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
         nodePastCount = 1;
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
    public int nodePastCount;
    private double timeNextNode;
    public List<Passenger> listPassenger;
    public List<PositionBus> positionInTime;
    
    public class PositionBus implements java.io.Serializable{
        
        public PositionBus(double argX,double argY,int argCount){
            positionX = argX;
            positionY = argY;
            count = argCount;
            //System.out.println("X: " +(int)argX+ " Y: "+ (int)argY+" Count: "+count);
        }
    
        public double positionX;
        public double positionY;
        public int count;
}
}

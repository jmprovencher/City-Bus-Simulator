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
public class Passenger implements java.io.Serializable{
    public Passenger(Directions d, double time){
        
        assignedDirections = d;
        startPoint = d.getStartPoint();
        endPoint = d.getEndPoint();
        actualNode = startPoint;
        startTime = time;
        startPoint.addPassenger(this);
        actualBus = null;
        nodesPast = 1;
        nextStop = assignedDirections.getNextStop(nodesPast);
        

    }
    
    public Node getNode(){
        return actualNode;
    }
    
    public Node getStartPoint(){
        return startPoint;
    }
    
    public Node getEndPoint(){
        return endPoint;
        
    }
    
    public Directions getDirection(){
        return assignedDirections;
    }
    
    public int nodePast(){
        nodesPast++;
        return nodesPast;
    }
    
    public void setBus(Bus b){
        actualBus = b;
        b.addPassenger(this);
        actualNode.removePassenger(this);
        actualNode = null;
    }
    
    public void setOutOfBus(){
        actualBus.removePassenger(this);
        actualBus = null;
        
        actualNode = assignedDirections.getNode(nodesPast);

        actualNode.addPassenger(this);
        nextStop = assignedDirections.getNextStop(nodesPast);
 
    }
    
    public Bus getBus(){
        return actualBus;
    }
    
    public Route getNextRoute(){
        
        return assignedDirections.getRoute(nodesPast);
    }
    
            
    private int nodesPast;
    public Node actualNode;
    private final Directions assignedDirections;
    private final Node startPoint;
    public Node nextStop;
    public Bus actualBus;
    private final Node endPoint;
    public final double startTime;
    public double stopTime;
}

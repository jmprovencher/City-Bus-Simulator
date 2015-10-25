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
public class Passenger {
    public Passenger(Directions d){
        
        assignedDirections = d;
        Noeud_origine = d.getStartPoint();
        Noeud_destination = d.getEndPoint();

        actualNode = Noeud_origine;
        Noeud_origine.addPassenger(this);
        actualBus = null;
        nodesPast = 1;
        nextStop = assignedDirections.getNextStop(nodesPast);

    }
    
    public Node req_actuelle(){
        return actualNode;
    }
    
    public Node req_origine(){
        return Noeud_origine;
    }
    
    public Node req_destination(){
        return Noeud_destination;
        
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
    
    public Route getNextRoute(){
        
        return assignedDirections.getRoute(nodesPast);
    }
    
            
    private int nodesPast;
    public Node actualNode;
    private Directions assignedDirections;
    private Node Noeud_origine;
    public Node nextStop;
    public Bus actualBus;
    private Node Noeud_destination;
}

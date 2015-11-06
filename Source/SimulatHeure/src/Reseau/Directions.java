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
public class Directions implements java.io.Serializable{
    
    public Directions(){
        directions = new ArrayList<SubRoute>();
        startPoint = null;
        endPoint = null;
        frequency = 1;
        timeFirstStart = 0;
        timeNextStart = timeFirstStart;
        numberOfStops = 0;
        listPassengers = new ArrayList<Passenger>();
        listPassengersDone = new ArrayList<Passenger>();
    }
    
    public void reset(){
        listPassengers.clear();
        listPassengersDone.clear();
        timeNextStart = timeFirstStart;
    }
    
    public void setTimeFirstStart(int t){
        timeFirstStart = t;
    }
    
    public void setTimeNextStart(double t){
        timeNextStart += t;
    }
    
    public int getTimeNextStart(){
        return timeNextStart;
    }
    
    public void setFrequency(int f){
        frequency = f;
    }
    
     public int getFrequency(){
        return frequency;
    }
    
    public Node getStartPoint(){
        return startPoint;
    }
    
    public Node getEndPoint(){
        return endPoint;
    }
    public void addPassenger(double time){
        Passenger newPassenger = new Passenger(this);
        newPassenger.startTinme = time;
        listPassengers.add(newPassenger);
    }
    
    public void removePassenger(Passenger p, double time){
        p.stopTime = time;
        p.actualNode.listPassenger.remove(p);
        listPassengersDone.add(p);
        
    }
    
    public void addSubRoute(Route r, int start, int end){
        System.out.println(directions.size());
        if (directions.isEmpty() && r.getNodeFromIndex(start).isStation){
            directions.add(new SubRoute(r, start, end));
            startPoint = r.getNodeFromIndex(start);
            endPoint = r.getNodeFromIndex(end);
        }
        
        else if (directions.size() >= 1){
            numberOfStops--; // car le start d'un subroute == end de l'autre avant, faut pas le compter 2x.
            Node lastNode = directions.get(directions.size()-1).getLastNode();
            System.out.println(lastNode.getName());
            if (lastNode == r.getNodeFromIndex(start)){
                directions.add(new SubRoute(r, start, end));
                endPoint = r.getNodeFromIndex(end);
            }
        }
    }
    
    public Node getNode(int i){
        int count = 0;
        for (SubRoute s: directions){
            for (Node n: s.subRoute){
                count++;
                if (count == i){
                    return n;
                }
            }
            count--;
        }
        return null;
    }
    
  
    public Route getRoute(int i){
       int count = -1;
        for (SubRoute s: directions){
            for (Node n: s.subRoute){
                count++;
                if (count == i){
                    return s.gerRoute();
                }
            }
            count--;
        }
        //System.out.println("Got none anymore I'm done as fuck");
        return null;
    }
    
    public Node getNextStop(int i){
      int count = -1;
        for (SubRoute s: directions){
            for (Node n: s.subRoute){
                count++;
                if (count == i){
                    return s.getLastNode();
                }
            }
            count--;
        }
        return null;
    }
 
    public int getNumberOfStops(){
        return numberOfStops;
    }
    
    private Node startPoint;
    private Node endPoint;
    private int timeFirstStart;
    private int frequency;
    private int timeNextStart;
    private int numberOfStops;
    public List<SubRoute> directions;
    public List<Passenger> listPassengers;
    public List<Passenger> listPassengersDone;
    
    
    public class SubRoute implements java.io.Serializable{
        
        public SubRoute(Route r,int start ,int end){
            route = r;
            subRoute = new ArrayList<Node>();
            for (int i = start; i<= end; i++){
                subRoute.add(r.getNodeFromIndex(i));
                numberOfStops++;
            }
            if (start > end && r.isLoop){
                for (int i = start; i < r.getNumberOfNodes();i++){
                    subRoute.add(r.getNodeFromIndex(i));
                }
                subRoute.add(r.getNodeFromIndex(0));
            }
           
        }
        
        public Node getNode(int i){
            return subRoute.get(i);
        }
        
        public Route gerRoute(){
            return route;
        }
        
        public Node getLastNode(){

                return subRoute.get(subRoute.size()-1);

        }
        
        public int size(){
            return subRoute.size();
        }
        
        private List<Node> subRoute;
        private Route route;
    }
}

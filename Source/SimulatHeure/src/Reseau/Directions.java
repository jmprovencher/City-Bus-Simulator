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
        minFreq = 1;
        maxFreq = 1;
        typeFreq = 1;
        timeFirstStart = 0;
        timeNextStart = timeFirstStart;
        numberOfStops = 0;
        listPassengers = new ArrayList<Passenger>();
        listPassengersDone = new ArrayList<Passenger>();
    }
    
    public void update(){
        numberOfStops = 0;
        for (SubRoute s: directions){
            s.update();
            numberOfStops+= s.size();
        }
        
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
    
    public void setFrequency(int min, int max, int type){
        minFreq = min;
        maxFreq = max;
        typeFreq = type;
    }
    

    public Node getStartPoint(){
        return startPoint;
    }
    
    public Node getEndPoint(){
        return endPoint;
    }
    public void addPassenger(double time){
        listPassengers.add(new Passenger(this, time));
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
                    return s.getRoute();
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
    public int minFreq;
    public int maxFreq;
    public int typeFreq;
    private int timeNextStart;
    private int numberOfStops;
    public List<SubRoute> directions;
    public List<Passenger> listPassengers;
    public List<Passenger> listPassengersDone;
    
    
    public class SubRoute implements java.io.Serializable{
        
        public SubRoute(Route r,int start ,int end){
            route = r;
            r.directionsUsingMe++;
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
        
        public void update(){
            
            Node origin = getStartPoint();
            Node destination = getEndPoint();
            
            int startIndex = route.route.indexOf(origin);
            int endIndex = route.route.indexOf(destination);
            
            subRoute.clear();
            while (startIndex!= endIndex){
                subRoute.add(route.route.get(startIndex));
                startIndex++;
            }
            subRoute.add(route.route.get(endIndex));
            
        }
        
        public Node getNode(int i){
            return subRoute.get(i);
        }
        
        public Route getRoute(){
            return route;
        }
        
        public Node getLastNode(){

                return subRoute.get(subRoute.size()-1);

        }
        
        public int size(){
            return subRoute.size();
        }
        
        public List<Node> subRoute;
        private Route route;
    }
}

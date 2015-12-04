/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import java.util.*;
import Reseau.*;

/**
 *
 * @author rem54
 */
public class Simulation implements java.io.Serializable{
    public Simulation(){

        listRoutes= new ArrayList<Route>();
        listLines = new ArrayList<Line>();
        listNodes = new ArrayList<Node>();
        listDirections = new ArrayList<Directions>();
        count = 0;
    }

    public void simulateTick(){
 
        for (Route r: listRoutes){
            Bus busDone = null;
            for (Bus b: r.listBus){
               
                if (!moveBus(b)){
                    busDone = b;
                }
            }
            
            if (busDone != null){
                r.deleteBus(busDone);
            }
            for (Route.Source s: r.listSources){
                
                double typicalTime = s.typeFrequency;
                double minimumTime = s.minFrequency;
                double maximumTime = s.maxFrequency;
                if ((int)(s.timeNextStart*(1000/(freq))) == count && !r.loopDone){
                    if (r.busAvalaible()){
                        Bus newBus = r.addBus(s);
                        passengerIn(newBus);
                    }
                    s.timeNextStart += (int) triangular(minimumTime, maximumTime, typicalTime);
                }
            }
        }
        
        for (Directions d: listDirections){
            if ((int)(d.getTimeNextStart()*(1000/(freq))) == count){
                d.addPassenger((double)count*(double)freq/(double)1000);
                d.setTimeNextStart(triangular(d.minFreq, d.maxFreq, d.typeFreq));
            }
        }
        
        count++;
    }
    
    public void stopSimulation(){
        count = 0;

        double min = Double.MAX_VALUE;
        double max = 0;
        double average = 0;
        double time;
        
        // GESTION PRÉLIMINAIRE DE STATS
        for (Directions d: listDirections){
            if (d.listPassengersDone.isEmpty()){
                System.out.println("Aucun passager a terminé!");
            }
            else{
                for (Passenger p: d.listPassengersDone){

                    time = p.stopTime - p.startTime;
                    if (time < min){
                        min = time;
                    }
                    if (time > max){
                        max = time;
                    }
                    average += time;
                }
                average = (double)average /(double) d.listPassengersDone.size();
                System.out.println("Trajet" + d.getStartPoint() + " - " + d.getEndPoint() + "Min: "+min+" Max: "+max+ " Average: "+average);
            }
        }
        
        
        
        for (Route r: listRoutes){
            r.reset();
        }
        for (Directions d: listDirections){
            d.reset();
        }
        
        for (Node n: listNodes){
            n.listPassenger.clear();
        }

    }
    
    public void passengerIn(Bus aBus){
        List<Passenger> passengerGettingIn = new ArrayList<Passenger>();
        if (!aBus.actualNode.listPassenger.isEmpty()){
            for (Passenger p: aBus.actualNode.listPassenger){
                if (p.getNextRoute() == aBus.getRoute() && aBus.getCapacity()>(aBus.listPassenger.size()+passengerGettingIn.size())){
                    passengerGettingIn.add(p);
                }
            }
            for (Passenger p: passengerGettingIn){
                p.setBus(aBus);
            }
        }
    }
    
    public void passengerOut(Bus aBus){
        List<Passenger> passengerGettingOut = new ArrayList<Passenger>();
        for (Passenger p: aBus.listPassenger){
            if (p.nextStop == aBus.actualNode || p.getEndPoint() ==aBus.actualNode){
                passengerGettingOut.add(p);
            }
        }
        for (Passenger p: passengerGettingOut){
            p.setOutOfBus();
            if (p.getEndPoint() == p.actualNode){
                p.getDirection().removePassenger(p, (double)count*(double)freq/(double)1000);
            }
        }
    }
    
    public Boolean moveBus(Bus b){
        
        // Bus has reached its destination woohoo
        if (b.getLastNodeIndex()+1 == b.getRoute().getNumberOfNodes() ){
                if (b.getRoute().isLoop){
                    b.getRoute().loopDone = true;
                    b.reset();
                }
            else {
                 return false;
            }
        }
       

        double originX = b.getPositionX();
        double originY = b.getPositionY();
        
        if(b.actualNode != null){
            b.exitNode();
        }
        // speeds are in meters/min, each tick is freq/1000 of a minute
        // the following is the movement per tick
        double relativeSpeed = (b.getSpeed())*freq/1000;
        
        Node nextNode = b.getRoute().getNodeFromIndex(b.getLastNodeIndex()+1);
        double targetX = nextNode.getPositionX();
        double targetY= nextNode.getPositionY();
        
        double angle;
        
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
            b.setPositionX(b.getPositionX()- (Math.cos(angle)*relativeSpeed));
            b.setPositionY(b.getPositionY()- (Math.sin(angle)*relativeSpeed));
        }
        else{
            b.setPositionX((b.getPositionX()+ (Math.cos(angle)*relativeSpeed)));
            b.setPositionY((b.getPositionY()+ (Math.sin(angle)*relativeSpeed)));
        }

        b.updateTimeNextNode();
        
        
        
        double tickTime = freq/1000;
        // si le temps restant avant le prochain noeud correspond à moins d'un tick de simulation....
        if (b.getTimeNextNode() <= tickTime){
            b.setNode(nextNode);
            passengerIn(b);
            passengerOut(b);
        }
        return true;
    }
    
    
    public Boolean addStation(List<Node> listNodesStations)
    {
        Boolean newStation = false;
        for (Node n: listNodesStations){
            if (n.setStation("Station "+listNodes.indexOf(n))){
                newStation = true;
            }
        }
        return newStation;

    }
    public Boolean deleteStation(List<Node> listNodes){
        Boolean deletedStation = false;
        for (Node n: listNodes){
            if(n.deleteStation()){
                deletedStation = true;
            }
            
        }
        return deletedStation;
    }
    

    public void deleteNode(List<Node> listNodesToDelete){
        if (!listNodesToDelete.isEmpty())
        {
            
            for (Node node: listNodesToDelete){
                if (node.getNumberOfRoutes() == 0){
                    List<Line> linesToDelete = new ArrayList<Line>();
                    for (Line l: listLines){
                        if (l.origin == node || l.destination == node){
                            linesToDelete.add(l);
                        }
                    }

                        deleteLine(linesToDelete);

                   listNodes.remove(node);
                }
            }
        }
    }

    public Boolean deleteLine(List<Line> listLinesToDelete){
        for (Line l: listLinesToDelete){
            if (l.getNumberOfRoutes() == 0){
                listLines.remove(l);
                l.delete();
            }
            else{
                return false;
            }
        }
        return true;
    }
    
    public Boolean deleteDirections(Directions d){
        for (Directions.SubRoute s: d.directions){
            s.getRoute().directionsUsingMe--;
        }
        listDirections.remove(d);
        return true;
    }
    
    public Node getNodeFromPosition(int positionX, int positionY, int nodeSize, int stationSize){
        int size;
          for (Node n: listNodes){
              if (n.isStation){
                  size = stationSize;
              }
              else{
                  size = nodeSize;
              }
               if (positionX < n.getPositionX() +size/2 && positionX > n.getPositionX() -size/2){
                   if (positionY < n.getPositionY() +size/2 && positionY > n.getPositionY() -size/2){
                       return n;
                   }
               }
          }
          return null;
    }
    
    public Node getNodeFromIndex(int index){
        return listNodes.get(index);
    }
    
    public int getNodeQuantity(){
        return listNodes.size();
    }
    
    public Boolean routeNumberAvailable(int i){
        for (Route r: listRoutes){
            if (r.getNumber() == i){
                return false;
            }
        }
        return true;
    }
    
    public Route addNewRoute(int number, int max){
        
        newRoute.setNumber(number);
        newRoute.setMaxBus(max);
        
        
        for (int i = 0; i< newRoute.route.size(); i++){
            newRoute.route.get(i).setRoute(1, newRoute);
            if (i < newRoute.route.size()-1){
                getLine(newRoute.route.get(i), newRoute.route.get(i+1)).setRoute(1, newRoute);
            }
        }
        listRoutes.add(newRoute);
        return listRoutes.get(listRoutes.size()-1);
    }
    
    public void addDirection(){
        listDirections.add(newDirections);
    }
    
    public Boolean addNodeToNewRoute(Node n){
        return newRoute.addNode(n);
    }
    
    public Boolean deleteRoute(Route route){
        if (route != null && route.directionsUsingMe == 0)
            {
            for (int i = 0; i<route.getNumberOfNodes(); i++){
                route.getNodeFromIndex(i).setRoute(-1, route);
                if (i < route.getNumberOfNodes()-1){
                    route.getLineFromIndex(i).setRoute(-1, route);
                }
            }

            
                listRoutes.remove(route);
                return true;
            }
        else{
            return false;
        }
    }
    
    public Node addNode(int x, int y){
        Node n = new Node(x, y);
        listNodes.add(n);
        return n;
    }
    
    public Line addLine(Node node1, Node node2){
        if (node1 != node2){
            if (!listNodes.contains(node1)){
                listNodes.add(node1);
            }
            if (!listNodes.contains(node2)){
                listNodes.add(node2);
            }
            if (getLine(node1, node2) != null){
                return null;
            }
            
            Line newLine = new Line(node1, node2);
            listLines.add(newLine);
            return newLine;   
            
        }
        else{
            return null;
        }
    }
    
    public Node splitLine(Line line, int x, int y){
        
        line.delete();
        listLines.remove(line);

        Line oppositeLine = getLine(line.destination, line.origin);
        
        Node n = new Node(x,y);
        n.listRoutes.addAll(line.associatedRoutes);
        listNodes.add(n);
        Line lineOne = new Line(line.origin, n);
        Line lineTwo = new Line(n, line.destination);
        lineOne.associatedRoutes = line.associatedRoutes;
        lineTwo.associatedRoutes = line.associatedRoutes;
        listLines.add(lineOne);
        listLines.add(lineTwo);
        
        if (oppositeLine != null){
            oppositeLine.delete();
            listLines.remove(oppositeLine);
            listLines.add(new Line(oppositeLine.origin, n));
            listLines.add(new Line(n, oppositeLine.destination));
            updateRoutesAfterSplitLine(oppositeLine, n);
            oppositeLine = null;
        }
        updateRoutesAfterSplitLine(line, n);
        for (Directions d: listDirections){
            d.update();
        }
        
        line = null;     
        return n;
    }
    
    public void updateRoutesAfterSplitLine(Line l, Node n){
        int i = 0;
        for (Route r: l.associatedRoutes){

           List<Integer> indexToAdd = new ArrayList<>();
            for (Node node: r.route){
                
                if (node == l.destination){
                    indexToAdd.add(i);
                    i++; 
                }
                i++;
            }
            for (int newIndex: indexToAdd){
                r.route.add(newIndex, n);
            }
        }
    }
    
    public Line getLineFromPosition(int x, int y){
        for (Line a: listLines){
            if (a.line.intersects(x-10, y-10, 20, 20)){   
                return a;
            }
        }
        return null;
    }
    
    public Line getLine(Node origine, Node destination){
        for (Line l : listLines){
            if (l.origin == origine && l.destination == destination){
                return l;
            }
        }
        return null;
    }
    
    public Node getNodeFromName(String name){
        for (Node n: listNodes){
            if (n.isStation && n.getName().equalsIgnoreCase(name)){
                return n;
            }
        }
        return null;
    }

    public int getRouteQuantity()
    {
        return listRoutes.size();
    }
    
    public Route getRouteFromIndex(int index){
        return listRoutes.get(index);
    }
    
    public Route getRouteFromNumber(int number){
        for (Route r: listRoutes){
            if (r.getNumber() == number)
                return r;
        }
        return null;
    }
    
    
    public void updateLines(Node n){
        for (Line l: listLines){
            l.update();
        }
    }
    
    public void setLinesSpeed(){
        for (Line l: listLines){
            l.setSpeed(triangular(l.minSpeed, l.maxSpeed, l.typeSpeed));
        }
    }
    
    public Bus getBusFromPosition(int positionX, int positionY, int size){
        
        
        for (Route r: listRoutes){
            for (Bus b : r.listBus){
                if (positionX < b.getPositionX() +size/2 && positionX > b.getPositionX() -size/2){
                    if (positionY < b.getPositionY() +size/2 && positionY > b.getPositionY() -size/2){
                        return b;
                    }
                }    
            }
        }
        return null;
    }
    
   double triangular(double a,double b,double c) {
        double U = Math.random();
        double F = (c - a) / (b - a);
        if (U <= F)
           return a + Math.sqrt(U * (b - a) * (c - a));
        else
           return b - Math.sqrt((1 - U) * (b - a) * (b - c));
    }
    public Directions newDirections;
    public Route newRoute;
    public List<Route> listRoutes;
    public List<Line> listLines;
    public List<Node> listNodes;
    public List<Directions> listDirections;
    public int count;
    public double freq;
    
    

}

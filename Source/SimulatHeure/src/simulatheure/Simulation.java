/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import java.util.*;
import java.math.*;
import Reseau.*;

/**
 *
 * @author rem54
 */
public class Simulation {
    public Simulation(){

        listRoutes= new ArrayList<Route>();
        newRoute = new ArrayList<Node>();
        listLines = new ArrayList<Line>();
        listNodes = new ArrayList<Node>();
        count = 0;
    }

    public void simulateTick(){
        
        for (Route c: listRoutes){
            Bus busDone = null;
            for (Bus b: c.listBus){
               
                if (!moveBus(b)){
                    busDone = b;
                }
            }
            if (busDone != null){
                c.deleteBus(busDone);
            }
            
            if ((int)(c.getTimeNextStart()*(1000/(freq))) == count && c.busAvalaible()){
                Bus newBus = c.addBus();
                double typicalTime = c.getFrequency();
                c.setTimeNextStart(triangular(typicalTime-2, typicalTime+2, typicalTime));
            }
        }
        count++;
    }
    
    public void stopSimulation(){
        count = 0;
        for (Route c: listRoutes){
            c.reset();
        }
    }
    
    public Boolean moveBus(Bus b){
              
        if (b.getRoute().getNumberOfNodes() == b.getNodesPast()){
            if (b.getRoute().isLoop){
                b.getRoute().loopDone = true;
                b.reset();
            }
            else{
                return false;
            } 
        }
        double originX = b.getPositionX();
        double originY = b.getPositionY();
        
        double relativeSpeed = (b.reqSpeed())*freq/1000;
        int targetX = b.getRoute().getNodeFromIndex(b.getLastNodeIndex()+1).getPositionX();
        int targetY= b.getRoute().getNodeFromIndex(b.getLastNodeIndex()+1).getPositionY();
        
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

            b.setPositionX(targetX);
            b.setPositionY(targetY);
            
            b.setLastNodeIndex();
           
            b.addNodesPast();
            b.updateTimeNextNode();
            b.updateSpeed();
        }
        return true;
    }
    
    
    public Node addStation(Node n)
    {
        n.setStation("Station");
        return n;
    }
    

    public Boolean deleteNode(Node node){
        if (node != null)
        {
            if (node.isStation){
                node.deleteStation();
            }
            else{
                if (node.getNumberOfRoutes() == 0){
                    List<Line> linesToDelete = new ArrayList<Line>();
                    for (Line l: listLines){
                        if (l.origine == node || l.destination == node){
                            linesToDelete.add(l);
                        }
                    }
                    for (Line l : linesToDelete){
                        deleteLine(l);
;                    }
                    listNodes.remove(node);
                    return true; // supprimée avec succès
                }

            
            
            }
        }
        return false ;// n'l pas pu être supprimée
    }

    public Boolean deleteLine(Line l){
        if (l.getNumberOfRoutes() == 0){
            l.delete();
            listLines.remove(l);
            return true;
        }
        return false;
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
    
    public Route addRoute(List<Node> routeList, int number, int frequency, int firstStart, int maxBus)
    {
        listRoutes.add(new Route(number, frequency , maxBus, firstStart , routeList));
        
        for (int i = 0; i< routeList.size(); i++){
            routeList.get(i).setNumberOfRoutes(1);
            if (i < routeList.size()-1){
                getLine(routeList.get(i), routeList.get(i+1)).setNumberOfRoutes(1);
            }
        }

        return listRoutes.get(listRoutes.size()-1);
    }
    
    public Boolean addNodeToNewRoute(Node n){
        if (newRoute.size()>0){
            for (Line a: newRoute.get(newRoute.size()-1).listLines){
                if (n != newRoute.get(newRoute.size()-1)){
                    if (n == a.destination){
                     return true;
                     
                    }
                }
            }
        }
        return false;
    }
    
    public void deleteRoute(Route route){
        if (route != null)
            {
            for (int i = 0; i<route.getNumberOfNodes(); i++){
                route.getNodeFromIndex(i).setNumberOfRoutes(-1);
                if (i < route.getNumberOfNodes()-1){
                    route.getLineFromIndex(i).setNumberOfRoutes(-1);
                }
            }


                listRoutes.remove(route);
            }
    }
    
    public Node addNode(int x, int y){
        Node n = new Node(x, y);
        listNodes.add(n);
        return n;
    }
    
    public Line addLine(Node node1, Node node2){
        
        if (!listNodes.contains(node1)){
            listNodes.add(node1);
        }
        if (!listNodes.contains(node2)){
            listNodes.add(node2);
        }

        Line newLine = new Line(node1, node2);
        listLines.add(newLine);
        return newLine;   
    }
    
    public Node splitLine(Line line, int x, int y){
        
        line.delete();
        listLines.remove(line);
        
        Line oppositeLine = getLine(line.destination, line.origine);
        
        Node n = new Node(x,y);
        
        listNodes.add(n);
        listLines.add(new Line(line.origine, n));
        listLines.add(new Line(n, line.destination));
        
        if (oppositeLine != null){
            System.out.println("has opposite");
            oppositeLine.delete();
            listLines.remove(oppositeLine);
            listLines.add(new Line(oppositeLine.origine, n));
            listLines.add(new Line(n, oppositeLine.destination));
            oppositeLine = null;
        }
        
        line = null;     
        return n;
    }
    
    public Line isLine(int x, int y){
        for (Line a: listLines){
            if (a.line.intersects(x-10, y-10, 20, 20)){   
                return a;
            }
        }
        return null;
    }
    
    public Line getLine(Node origine, Node destination){
        for (Line l : listLines){
            if (l.origine == origine && l.destination == destination){
                return l;
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
    
    public void updateLines(){
        for (Line l: listLines){
            l.update();
        }
    }
    
    public void setLinesSpeed(){
        for (Line l: listLines){
            l.setSpeed(triangular(l.minSpeed, l.maxSpeed, l.typeSpeed));
        }
    }
    
   double triangular(double a,double b,double c) {
        double U = Math.random();
        double F = (c - a) / (b - a);
        if (U <= F)
           return a + Math.sqrt(U * (b - a) * (c - a));
        else
           return b - Math.sqrt((1 - U) * (b - a) * (b - c));
    }

    public List<Node> newRoute;
    private List<Route> listRoutes;
    public List<Line> listLines;
    public List<Node> listNodes;
    public int count;
    public double freq;

}

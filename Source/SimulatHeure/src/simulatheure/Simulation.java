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

    public void simulate(){
        
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
            
            if ((int)(c.getTimeNextStart()*(1000/(freq))) == count){
                Bus newBus = c.addBus();
 
                c.setTimeNextStart();
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
        
        if (b.getTimeNextNode() <= freq/1000){

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
                int size = node.listLines.size();
                for (int i = 0; i < size; i++){
                    Line a = node.listLines.get(0);
                    a.delete();
                    
                    if(node != a.origine && a.origine.listLines.isEmpty()){
                        listNodes.remove(a.origine);
                    }
                    if(node != a.destination && a.destination.listLines.isEmpty()){
                        listNodes.remove(a.destination);
                    }
                    listLines.remove(a);
                }
                listNodes.remove(node);
            }
            return true; // supprimée avec succès
            }
        }
        return false ;// n'a pas pu être supprimée
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
    
    public Route addRoute(List<Node> routeList, int number, int frequency, int firstStart)
    {
        listRoutes.add(new Route(number, frequency , firstStart ,routeList));

        for (Node n: routeList){
            n.setNumberOfCircuit(1);
        }

        return listRoutes.get(listRoutes.size()-1);
    }
    
    public void deleteRoute(Route route){

        for (int i = 0; i<route.getNumberOfNodes(); i++){
            route.getNodeFromIndex(i).setNumberOfCircuit(-1);
        }

        if (route != null)
        {
            listLines.remove(route);
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

        Line newArete = new Line(node1, node2);
        listLines.add(newArete);
        return newArete;   
    }
    
    public Node splitLine(Line line, int x, int y){
        
        line.origine.listLines.remove(line);
        line.destination.listLines.remove(line);
        listLines.remove(line);
        
        Node n = new Node(x,y);
        listNodes.add(n);
        listLines.add(new Line(line.origine, n));
        listLines.add(new Line(line.destination, n));
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
    
    public void setSpeed(){
        for (Line a: listLines){
            a.speed = triangular(a.minSpeed, a.maxSpeed, a.typeSpeed);
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

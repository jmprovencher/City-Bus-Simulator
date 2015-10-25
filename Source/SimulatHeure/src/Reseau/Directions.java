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
public class Directions {
    
    public Directions(){
        directions = new ArrayList<SubRoute>();
        startPoint = null;
        endPoint = null;
    }
    
    public Node getStartPoint(){
        return startPoint;
    }
    
    public Node getEndPoint(){
        return endPoint;
    }

    public void addSubRoute(Route r, int start, int end){
        if (directions.isEmpty() && r.getNodeFromIndex(start).isStation){
            directions.add(new SubRoute(r, start, end));
            startPoint = r.getNodeFromIndex(start);
        }
        else if (directions.size() > 0){
            Node lastNode = directions.get(directions.size()-1).getLastNode();
            if (lastNode == r.getNodeFromIndex(start)){
                directions.add(new SubRoute(r, start, end));
                endPoint = r.getNodeFromIndex(end);
            }
        }
    }
    
    private Node startPoint;
    private Node endPoint;
    private List<SubRoute> directions;
    
    
    public class SubRoute{
        
        public SubRoute(Route r,int start ,int end){
            subRoute = new ArrayList<Node>();
            for (int i = start; i<= end; i++){
                subRoute.add(r.getNodeFromIndex(i));
            }
        }
        
        public Node getNode(int i){
            return subRoute.get(i);
        }
        
        public Node getLastNode(){
            return subRoute.get(subRoute.size()-1);
        }
        
        public int size(){
            return subRoute.size();
        }
        
        private List<Node> subRoute;
    }
}

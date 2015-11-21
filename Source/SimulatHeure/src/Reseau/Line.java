/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;
import java.awt.geom.Line2D;
import java.util.*;
/**
 *
 * @author Sam
 */
public class Line implements java.io.Serializable{
    public Line(Node startPoint, Node endPoint){
        maxSpeed = 30*1000/60;
        minSpeed = 30*1000/60;
        typeSpeed = 30*1000/60;
        origin = startPoint;
        destination = endPoint;
        origin.addLine(this);
        double x1, x2, y1, y2;
        x1 = origin.getPositionX();
        y1 = origin.getPositionY();
        x2 = destination.getPositionX();
        y2 = destination.getPositionY();        
        line = new Line2D.Double(x1,y1,x2,y2);
        associatedRoutes =new ArrayList<Route>();
        offsetLine();
    }
    
    public void offsetLine()
    {
        
        double originX = origin.getPositionX();
        double originY = origin.getPositionY();

        double targetX = destination.getPositionX();
        double targetY= destination.getPositionY();

        double angle = Math.atan((double)(targetY-originY)/(double)(targetX-originX));
         if (targetX == originX){
            if (targetY > originY){
                angle = -Math.PI/2;
            }
            else{
                angle = (Math.PI/2);
            }
        }
         
         if (targetY < originY && targetX < originX){
             angle-= Math.PI/2;
         }
         else if (targetY > originY && targetX < originX){
             angle-= Math.PI/2;
         }
         else if (targetY < originY && targetX > originX){
             angle+= Math.PI/2;
         }
             
         else{
             angle+= Math.PI/2;
         }
         double N = 7;
         double moveX = N* Math.cos(angle);
         double moveY = N* Math.sin(angle);
         
         line.setLine(origin.getPositionX()+moveX, origin.getPositionY()+moveY, destination.getPositionX()+moveX,destination.getPositionY()+moveY );
        
    }   
    
    public Node getOrigin(){
        return origin;
    }
    
    public Node getDestination(){
        return destination;
    }
    

    public void update(){
        line.setLine(origin.getPositionX(), origin.getPositionY(), destination.getPositionX(), destination.getPositionY());
        offsetLine();
    }
    
    public void delete(){

            origin.listLines.remove(this);
            destination.listLines.remove(this);
         
 
    }
    
    public int getNumberOfRoutes(){

        return associatedRoutes.size();

    }
    public void setRoute(int n, Route r){
  
        if (n == 1){
            associatedRoutes.add(r);
        }
        if (n == -1){
            associatedRoutes.remove(r);
            
        }
         
    }
    
    public void setSpeed(double s){
        speed =s;
    }
    
    public double maxSpeed;
    public double minSpeed;
    public double typeSpeed;
    public double speed;
    
    public List<Route> associatedRoutes;
    public Line2D line;
    public Node origin;
    public Node destination;
}

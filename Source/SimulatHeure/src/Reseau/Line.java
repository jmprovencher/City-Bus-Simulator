/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;
import java.awt.geom.Line2D;
/**
 *
 * @author Sam
 */
public class Line implements java.io.Serializable{
    public Line(Node arg_origine, Node arg_destination){
        maxSpeed = 30*1000/60;
        minSpeed = 30*1000/60;
        typeSpeed = 30*1000/60;
        origine = arg_origine;
        destination = arg_destination;
        origine.addLine(this);
        int x1, x2, y1, y2;
        x1 = origine.getPositionX();
        y1 = origine.getPositionY();
        x2 = destination.getPositionX();
        y2 = destination.getPositionY();        
        line = new Line2D.Double(x1,y1,x2,y2);
        numberOfRoutes =0;
    }
    
    public Node getOrigine(){
        return origine;
    }
    
    public Node getDestination(){
        return destination;
    }
    

    public void update(){
        line.setLine(origine.getPositionX(), origine.getPositionY(), destination.getPositionX(), destination.getPositionY());
    }
    
    public void delete(){
        if (origine.listLines.contains(this)){
            origine.listLines.remove(this);
        }
         if (destination.listLines.contains(this)){
            destination.listLines.remove(this);
         }
 
    }
    public int getNumberOfRoutes(){

        return numberOfRoutes;

    }
    public void setRoute(int n){
  
        if (n == 1){
            numberOfRoutes++;
        }
        if (n == -1){
            numberOfRoutes--;
        }
         
    }
    
    public void setSpeed(double s){
        speed =s;
    }
    
    public double maxSpeed;
    public double minSpeed;
    public double typeSpeed;
    public double speed;
    
    private int numberOfRoutes;
    public Line2D line;
    public Node origine;
    public Node destination;
}

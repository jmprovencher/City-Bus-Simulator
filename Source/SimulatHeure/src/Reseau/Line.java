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
public class Line {
    public Line(Node arg_origine, Node arg_destination){
        maxSpeed = 60;
        minSpeed = 5;
        typeSpeed = 35;
        origine = arg_origine;
        destination = arg_destination;
        origine.addLine(this);
        destination.addLine(this);
        int x1, x2, y1, y2;
        x1 = origine.getPositionX();
        y1 = origine.getPositionY();
        x2 = destination.getPositionX();
        y2 = destination.getPositionY();        
        line = new Line2D.Double(x1,y1,x2,y2);
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
        if (origine.listRoutes.contains(this)){
            origine.listRoutes.remove(this);
        }
         if (destination.listRoutes.contains(this)){
            destination.listRoutes.remove(this);
         }
 
    }
    
    public double maxSpeed;
    public double minSpeed;
    public double typeSpeed;
    public double speed;
    
    public Line2D line;
    public Node origine;
    public Node destination;
}

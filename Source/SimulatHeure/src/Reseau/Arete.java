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
public class Arete {
    public Arete(Noeud arg_origine, Noeud arg_destination){
        maxSpeed = 60;
        minSpeed = 5;
        typeSpeed = 35;
        origine = arg_origine;
        destination = arg_destination;
        origine.addArete(this);
        destination.addArete(this);
        int x1, x2, y1, y2;
        x1 = origine.req_positionX();
        y1 = origine.req_positionY();
        x2 = destination.req_positionX();
        y2 = destination.req_positionY();        
        line = new Line2D.Double(x1,y1,x2,y2);
    }
    
    public Noeud req_origine(){
        return origine;
    }
    
    public Noeud req_destination(){
        return destination;
    }
    

    public void update(){
        line.setLine(origine.req_positionX(), origine.req_positionY(), destination.req_positionX(), destination.req_positionY());
    }
    
    public void delete(){
        if (origine.listAretes.contains(this)){
            origine.listAretes.remove(this);
        }
         if (destination.listAretes.contains(this)){
            destination.listAretes.remove(this);
         }
 
    }
    
    public double maxSpeed;
    public double minSpeed;
    public double typeSpeed;
    public double speed;
    
    public Line2D line;
    public Noeud origine;
    public Noeud destination;
}

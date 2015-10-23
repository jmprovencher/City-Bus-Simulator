/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;

/**
 *
 * @author Rémi
 */
public class Directions {
    
    public Directions(Node newStartPoint, Node newEndPoint){
        startPoint = newStartPoint;
        endPoint = newEndPoint;
    }
    
    public Node getStartPoint(){
        return startPoint;
    }
    
    public Node getEndPoint(){
        return endPoint;
    }

    private Node startPoint;
    private Node endPoint;
    
    
}

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
public abstract class LocationData {
    
    
    public abstract double getPositionX();
    public abstract double getPositionY();
    
    public abstract void setPositionX(double x);
    public abstract void setPositionY(double y);
    
    private double positionX;
    private double positionY;


}

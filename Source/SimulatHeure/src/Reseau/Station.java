/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;

/**
 *
 * @author Sam
 */
public class Station {
    public Station(String arg_nom, int arg_positionX, int arg_positionY){
        nom = arg_nom;
        positionX = arg_positionX;
        positionY = arg_positionY;
    }
    
    public void mod_nom(String arg_nom){
        nom = arg_nom;
    }
    
    public void mod_nb_passager(int arg_nb_passager){
        nb_passager = arg_nb_passager;
    }
    
    public int req_nb_passager(){
        return nb_passager;
    }
    
    public int req_positionX(){
        return positionX;
    }
    
    public int req_positionY(){
        return positionY;
    }
    
    public String req_nom(){
        return nom;
    }
    
    
    private int nb_passager;
    private int positionX;
    private int positionY;
    private String nom;
}

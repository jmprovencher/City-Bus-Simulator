/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;
import java.util.*;
/**
 *
 * @author rem54
 */
public class Noeud {
    
    public Noeud(int argX, int argY){
        positionX = argX;
        positionY = argY;
        isStation = false;
        nombre_circuits = 0;
        nombre_passagers = 0;
        listAretes = new ArrayList<Arete>();
    }
    
    public void addArete(Arete a){
        listAretes.add(a);
    }
    
    public int req_positionX(){
        return positionX;
    }
    
    public int req_positionY(){
        return positionY;
    }
    
    public void mod_positionX(int x_arg){
        positionX = x_arg;
    }
    
    public void mod_positionY(int y_arg){
        positionY = y_arg;
    }
    
    private int positionX;
    private int positionY;
    public Boolean isStation;
    
    
    //Station
    
     public void mod_nom(String arg_nom){
         if(isStation){
        nom = arg_nom;
         }
    }
    
    //not used yet
     
     public void setStation(String arg_nom){
         isStation = true;
         nom = arg_nom;
     }
     
     public void deleteStation(){
         isStation = false;
         nom = null;
         nombre_passagers = 0;
         nombre_circuits = 0;
     }
     
    public void mod_nb_passager(int arg_nb_passager){
         if(isStation){
            nombre_passagers = arg_nb_passager;
         }
    }

    public void mod_nombre_circuits(int n){
  
        if (n == 1){
            nombre_circuits++;
        }
        if (n == -1){
            nombre_circuits--;
        }
         
    }
    
    public int req_nombreb_passager(){
         if(isStation){
        return nombre_passagers;
         }
         return 0;
    }
    
    public String req_nom(){
         if(isStation){
        return nom;
         }
         return null;
    }
    

    public int req_nombre_circuits(){

        return nombre_circuits;

    }
    
    public List<Arete> listAretes;
    private String nom;
    private int nombre_circuits;
    private int nombre_passagers;
}

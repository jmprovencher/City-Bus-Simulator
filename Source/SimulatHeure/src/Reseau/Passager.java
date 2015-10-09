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
public class Passager {
    public Passager(Noeud arg_origine, Noeud arg_destination){
        Noeud_origine = arg_origine;
        Noeud_destination = arg_destination;
        Noeud_actuelle = Noeud_origine;
    }
    public Noeud req_actuelle(){
        return Noeud_actuelle;
    }
    
    void mod_station_actuelle(Noeud arg_actuelle){
        Noeud_actuelle = arg_actuelle;
    }
    
    public Noeud req_origine(){
        return Noeud_origine;
    }
    
    public Noeud req_destination(){
        return Noeud_destination;
    }
    
    private Noeud Noeud_origine;
    private Noeud Noeud_actuelle;
    private Noeud Noeud_destination;
}

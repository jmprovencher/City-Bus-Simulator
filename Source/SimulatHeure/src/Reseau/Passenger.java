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
    public Passager(Node arg_origine, Node arg_destination){
        Noeud_origine = arg_origine;
        Noeud_destination = arg_destination;
        Noeud_actuelle = Noeud_origine;
    }
    public Node req_actuelle(){
        return Noeud_actuelle;
    }
    
    void mod_station_actuelle(Node arg_actuelle){
        Noeud_actuelle = arg_actuelle;
    }
    
    public Node req_origine(){
        return Noeud_origine;
    }
    
    public Node req_destination(){
        return Noeud_destination;
    }
    
    private Node Noeud_origine;
    private Node Noeud_actuelle;
    private Node Noeud_destination;
}

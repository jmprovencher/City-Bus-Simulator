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
public class Arete {
    public Arete(Station arg_origine, Station arg_destination){
        t = 0;
        origine = arg_origine;
        destination = arg_destination;
    }
    
    public Station req_origine(){
        return origine;
    }
    
    public Station req_destination(){
        return destination;
    }
    
    public int req_t(){
        return t;
    }
    public void mod_t(int arg_t){
        t= arg_t;
    }
    private int t;
    private Station origine;
    private Station destination;
}

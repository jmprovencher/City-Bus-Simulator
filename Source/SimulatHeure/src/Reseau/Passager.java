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
    public Passager(Station arg_origine, Station arg_destination){
        origine = arg_origine;
        destination = arg_destination;
    }
    
    public Station req_origine(){
        return origine;
    }
    
    public Station req_destination(){
        return destination;
    }
    
    private Station origine;
    private Station destination;
}

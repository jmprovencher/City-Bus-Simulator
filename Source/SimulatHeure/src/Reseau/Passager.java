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
        station_origine = arg_origine;
        station_destination = arg_destination;
        station_actuelle = station_origine;
    }
    public Station req_actuelle(){
        return station_actuelle;
    }
    
    void mod_station_actuelle(Station arg_actuelle){
        station_actuelle = arg_actuelle;
    }
    
    public Station req_origine(){
        return station_origine;
    }
    
    public Station req_destination(){
        return station_destination;
    }
    
    private Station station_origine;
    private Station station_actuelle;
    private Station station_destination;
}

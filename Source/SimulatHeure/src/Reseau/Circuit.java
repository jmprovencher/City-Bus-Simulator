/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package Reseau;

import java.util.*;
/**
 *
 * @author Sam
 */
public class Circuit {
    public Circuit(int arg_numero, int arg_frequence, int arg_t_depart, List<Station> p){
        numero = arg_numero;
        frequence = arg_frequence;
        t_premier_depart = arg_t_depart;
        t_prochain_depart = arg_t_depart;
        parcours = new ArrayList<Station>(p);
        liste_bus = new ArrayList<Bus>();
    }
    
    public void reset(){
        liste_bus.clear();
        t_prochain_depart = t_premier_depart;
    }
    
    public Bus ajouter_bus(){
        Bus newBus = new Bus(0, this, 0);
        liste_bus.add(newBus);
        return newBus;
        
    }
    
    public void supprimer_bus(Bus b){
        liste_bus.remove(b);
    }
    
    
    public void mod_numero(int arg_num){
        numero = arg_num;
    }
    
    public int mod_t_parcours(){
        t_parcours = 0;
       return t_parcours;
    }
    public int req_t_parcours(){
        return t_parcours;
    }
    
    public int req_numero(){
        return numero;
    }
    
    public int req_frequence(){
        return frequence;
    }
    
    public int req_t_prochain_depart(){
        return t_prochain_depart;
    }
    
    public void mod_t_prochain_depart(){
        t_prochain_depart += frequence;
    }
    
    public Station req_station_index(int index){
        return parcours.get(index);
    }
    
    public int req_nombre_stations(){
        return parcours.size();
    }
    
    private List<Station> parcours;
    public List<Bus> liste_bus;
    private int numero;
    private int frequence;
    private int t_premier_depart;
    private int t_prochain_depart;
    private int t_parcours;
}

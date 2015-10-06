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
        t_depart = arg_t_depart;
        parcours = new ArrayList<Station>(p);
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
    
    public int req_t_depart(){
        return t_depart;
    }
    
    public Station req_station_index(int index){
        return parcours.get(index);
    }
    
    public int req_nombre_stations(){
        return parcours.size();
    }
    
    private List<Station> parcours;
    private int numero;
    private int frequence;
    private int t_depart;
    private int t_parcours;
}

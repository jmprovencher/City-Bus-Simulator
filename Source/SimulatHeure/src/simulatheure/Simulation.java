/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import java.util.*;
import Reseau.*;
/**
 *
 * @author rem54
 */
public class Simulation {
    public Simulation(){
        liste_stations = new ArrayList<Station>();
        liste_circuits= new ArrayList<Circuit>();
    }
    public Station ajouter_station(int arg_x, int arg_y)
    {
        liste_stations.add(new Station("Station "+liste_stations.size(), arg_x,arg_y));
        return liste_stations.get(liste_stations.size()-1);
    }
    
    public Boolean supprimer_station(Station s){
        if (s != null && s.req_nombre_circuits() ==0)
        {
            liste_stations.remove(s);
            return true; // supprimée avec succès
        }
        return false ;// n'a pas pu être supprimée
    }

    public Station req_station_pos(int arg_x, int arg_y, int taille){
          for (Station s: liste_stations){
               if (arg_x < s.req_positionX() +taille/2 && arg_x > s.req_positionX() -taille/2){
                   if (arg_y < s.req_positionY() +taille/2 && arg_y > s.req_positionY() -taille/2){
                       return s;
                   }
               }
          }
          return null;
    }
    
    public Station req_station_index(int index){
        return liste_stations.get(index);
    }
    
    public int req_nombre_stations(){
        return liste_stations.size();
    }
    
    public Circuit ajouter_circuit(List<Station> p, int arg_num, int arg_freq, int arg_t_depart)
    {
        liste_circuits.add(new Circuit(arg_num,arg_freq,arg_t_depart,p));
        for (Station s: p){
            s.mod_nombre_circuits(1);
        }
        return liste_circuits.get(liste_circuits.size()-1);
    }
    
    public void supprimer_circuit(Circuit c){
        for (int i = 0; i<c.req_nombre_stations(); i++){
            c.req_station_index(i).mod_nombre_circuits(-1);
        }
        if (c != null)
        {
            liste_circuits.remove(c);
        }
    }
    
    public int req_nombre_circuits()
    {
        return liste_circuits.size();
    }
    
    public Circuit req_circuit_index(int index){
        return liste_circuits.get(index);
    }
    

    
    private List<Station> liste_stations;
    private List<Circuit> liste_circuits;
}

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
        temps = 0;
    }
    
    public void Simuler(){
        
     
        
        for (Circuit c: liste_circuits){
            Bus bus_terminee = null;
            for (Bus b: c.liste_bus){
               
                if (!deplacer_bus(b)){
                    bus_terminee = b;
                }
            }
            if (bus_terminee != null){
                c.supprimer_bus(bus_terminee);
            }
            
            if (c.req_t_prochain_depart() == temps){
                c.ajouter_bus();
                c.mod_t_prochain_depart(temps);
            }
        }
        temps++;
    }
    
    public void Arreter_simulation(){
        temps = 0;
        for (Circuit c: liste_circuits){
            c.reset();
        }
    }
    
    public Boolean deplacer_bus(Bus b){
        int origin_x = b.req_circuitActuel().req_station_index(b.req_index_derniere_station()).req_positionX();
        int origin_y = b.req_circuitActuel().req_station_index(b.req_index_derniere_station()).req_positionY();
        
        if (b.req_circuitActuel().req_nombre_stations() == b.req_nombre_station_parcourue()){
           //DELETE LA BUS YO

            return false;
        }
        
        int target_x = b.req_circuitActuel().req_station_index(b.req_index_derniere_station()+1).req_positionX();
        int target_y= b.req_circuitActuel().req_station_index(b.req_index_derniere_station()+1).req_positionY();

        b.mod_positionX(b.req_positionX()+(target_x - origin_x));
        b.mod_positionY(b.req_positionY()+(target_y - origin_y));
        
        if (b.req_positionX() == target_x && b.req_positionY() == target_y){
            
            b.mod_index_derniere_station();
            b.incrementer_nombre_station_parcourue();
        }
        return true;
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
    private int temps;
}

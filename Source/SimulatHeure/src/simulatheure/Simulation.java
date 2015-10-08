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
        parcours = new ArrayList<Station>();
        count = 0;

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
            
            if ((int)(c.req_t_prochain_depart()*(1000/(freq))) == count){
                Bus newBus = c.ajouter_bus();
                newBus.setSpeed(60);
                c.mod_t_prochain_depart();
            }
            
        }
        
        count++;
    }
    
    public void Arreter_simulation(){
        count = 0;
        for (Circuit c: liste_circuits){
            c.reset();
        }
    }
    
    public Boolean deplacer_bus(Bus b){
              
        if (b.req_circuitActuel().req_nombre_stations() == b.req_nombre_station_parcourue()){
          return false;
        }
        double origin_x = b.req_positionX();
        double origin_y = b.req_positionY();
        
        double relativeSpeed = (b.reqSpeed())*freq/1000;
        int target_x = b.req_circuitActuel().req_station_index(b.req_index_derniere_station()+1).req_positionX();
        int target_y= b.req_circuitActuel().req_station_index(b.req_index_derniere_station()+1).req_positionY();
        
        double angle;
        
        angle = Math.atan((double)(target_y-origin_y)/(double)(target_x-origin_x));
        
        if (target_x == origin_x){
            if (target_y > origin_y){
                angle = Math.PI/2;
            }
            else{
                angle = -(Math.PI/2);
            }
        }

        if (target_x - origin_x <= 0){
            b.mod_positionX(b.req_positionX()- (Math.cos(angle)*relativeSpeed));
            b.mod_positionY(b.req_positionY()- (Math.sin(angle)*relativeSpeed));
        }
        else{
            b.mod_positionX((b.req_positionX()+ (Math.cos(angle)*relativeSpeed)));
            b.mod_positionY((b.req_positionY()+ (Math.sin(angle)*relativeSpeed)));
        }

        b.update_t_next_station();
        
        if (b.req_t_next_station() <= freq/1000){

            b.mod_positionX(target_x);
            b.mod_positionY(target_y);
            // fonction qui fait tout ça....
            b.mod_index_derniere_station();
            b.incrementer_nombre_station_parcourue();
            b.update_t_next_station();
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
    

    public List<Station> parcours;
    private List<Station> liste_stations;
    private List<Circuit> liste_circuits;
    public int count;
    public double freq;

}

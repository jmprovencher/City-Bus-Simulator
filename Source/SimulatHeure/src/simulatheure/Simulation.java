/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import java.util.*;
import java.math.*;
import Reseau.*;

/**
 *
 * @author rem54
 */
public class Simulation {
    public Simulation(){

        liste_circuits= new ArrayList<Circuit>();
        parcours = new ArrayList<Noeud>();
        liste_aretes = new ArrayList<Arete>();
        liste_noeuds = new ArrayList<Noeud>();
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
              
        if (b.req_circuitActuel().req_nombre_noeuds() == b.req_nombre_noeud_parcourue()){
            if (b.req_circuitActuel().isLoop){
                b.req_circuitActuel().loopDone = true;
                b.reset();
            }
            else{
                return false;
            } 
        }
        double origin_x = b.req_positionX();
        double origin_y = b.req_positionY();
        
        double relativeSpeed = (b.reqSpeed())*freq/1000;
        int target_x = b.req_circuitActuel().req_noeud_index(b.req_index_derniere_noeud()+1).req_positionX();
        int target_y= b.req_circuitActuel().req_noeud_index(b.req_index_derniere_noeud()+1).req_positionY();
        
        double angle;
        
        angle = Math.atan((double)(target_y-origin_y)/(double)(target_x-origin_x));
        
        if (target_x == origin_x){
            if (target_y > origin_y){
                angle = -Math.PI/2;
            }
            else{
                angle = (Math.PI/2);
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

        b.update_t_next_noeud();
        
        if (b.req_t_next_noeud() <= freq/1000){

            b.mod_positionX(target_x);
            b.mod_positionY(target_y);
            
            b.mod_index_dernier_noeud();
           
            b.incrementer_nombre_noeud_parcourue();
            b.update_t_next_noeud();
            b.updateSpeed();
        }
        return true;
    }
    
    
    public Noeud ajouter_station(Noeud n)
    {
        n.setStation("Station");
        return n;
    }
    

    public Boolean supprimer_noeud(Noeud n){
        if (n != null)
        {
            if (n.isStation){
                n.deleteStation();
            }
            else{
                
                if (n.req_nombre_circuits() == 0){
                int size = n.listAretes.size();
                for (int i = 0; i < size; i++){
                    Arete a = n.listAretes.get(0);
                    a.delete();
                    
                    if(n != a.origine && a.origine.listAretes.isEmpty()){
                        liste_noeuds.remove(a.origine);
                    }
                    if(n != a.destination && a.destination.listAretes.isEmpty()){
                        liste_noeuds.remove(a.destination);
                    }
                    liste_aretes.remove(a);
                }
                liste_noeuds.remove(n);
                
            }
            return true; // supprimée avec succès
            }
        }
        return false ;// n'a pas pu être supprimée

    }


    public Noeud req_noeud_pos(int arg_x, int arg_y, int arg_taille, int argTailleStation){
        int taille;
          for (Noeud n: liste_noeuds){
              if (n.isStation){
                  taille = argTailleStation;
              }
              else{
                  taille = arg_taille;
              }
               if (arg_x < n.req_positionX() +taille/2 && arg_x > n.req_positionX() -taille/2){
                   if (arg_y < n.req_positionY() +taille/2 && arg_y > n.req_positionY() -taille/2){
                       return n;
                   }
               }
          }
          return null;
    }
    
    public Noeud req_noeud_index(int index){
        return liste_noeuds.get(index);
    }
    
    public int req_nombre_noeud(){
        return liste_noeuds.size();
    }
    
    public Circuit ajouter_circuit(List<Noeud> p, int arg_num, int arg_freq, int arg_t_depart)
    {
        liste_circuits.add(new Circuit(arg_num,arg_freq,arg_t_depart,p));

        for (Noeud n: p){
            n.mod_nombre_circuits(1);
        }

        return liste_circuits.get(liste_circuits.size()-1);
    }
    
    public void supprimer_circuit(Circuit c){

        for (int i = 0; i<c.req_nombre_noeuds(); i++){
            c.req_noeud_index(i).mod_nombre_circuits(-1);
        }

        if (c != null)
        {
            liste_circuits.remove(c);
        }
    }
    
    public Noeud addNoeud(int x, int y){
        Noeud n = new Noeud(x, y);
        liste_noeuds.add(n);
        return n;
    }
    
    public Arete addLine(Noeud noeud1, Noeud noeud2){
        
        if (!liste_noeuds.contains(noeud1)){
            liste_noeuds.add(noeud1);
        }
        if (!liste_noeuds.contains(noeud2)){
            liste_noeuds.add(noeud2);
        }

        Arete newArete = new Arete(noeud1, noeud2);
        liste_aretes.add(newArete);
        return newArete;   
    }
    
    public Noeud splitLine(Arete a, int x, int y){
        
        a.origine.listAretes.remove(a);
        a.destination.listAretes.remove(a);
        liste_aretes.remove(a);
        
        Noeud n = new Noeud(x,y);
        liste_noeuds.add(n);
        liste_aretes.add(new Arete(a.origine, n));
        liste_aretes.add(new Arete(a.destination, n));
        a = null;     
        return n;
    }
    
    public Arete isLine(int x, int y){
        for (Arete a: liste_aretes){
            if (a.line.intersects(x-10, y-10, 20, 20)){   
                return a;
            }
        }
        return null;
    }
    

    public int req_nombre_circuits()
    {
        return liste_circuits.size();
    }
    
    public Circuit req_circuit_index(int index){
        return liste_circuits.get(index);
    }
    
    public void updateArete(){
        for (Arete a: liste_aretes){
            a.update();
        }
    }
    
    public void setSpeed(){
        for (Arete a: liste_aretes){
            a.speed = triangular(a.minSpeed, a.maxSpeed, a.typeSpeed);
        }
    }
    
   double triangular(double a,double b,double c) {
        double U = Math.random();
        double F = (c - a) / (b - a);
        if (U <= F)
           return a + Math.sqrt(U * (b - a) * (c - a));
        else
           return b - Math.sqrt((1 - U) * (b - a) * (b - c));
    }

    public List<Noeud> parcours;
    private List<Circuit> liste_circuits;
    public List<Arete> liste_aretes;
    public List<Noeud> liste_noeuds;
    public int count;
    public double freq;

}

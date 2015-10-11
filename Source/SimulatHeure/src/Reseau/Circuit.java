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
    public Circuit(int arg_numero, int arg_frequence, int arg_t_depart, List<Noeud> p){
        numero = arg_numero;
        frequence = arg_frequence;
        t_premier_depart = arg_t_depart;
        t_prochain_depart = arg_t_depart;
        parcours = new ArrayList<Noeud>(p);
        liste_bus = new ArrayList<Bus>();
        if (p.get(0) == p.get(p.size()-1)){
            isLoop = true;
        }
        else{
            isLoop = false;
        }
        loopDone = false;
        System.out.println(isLoop);
    }
    
    public void reset(){
        liste_bus.clear();
        loopDone = false;
        t_prochain_depart = t_premier_depart;
    }
    
    public Bus ajouter_bus(){
        if (!loopDone){
            Bus newBus = new Bus(0, this, 0);
            liste_bus.add(newBus);
            return newBus;
        }
        return null;
    }
    
    public void supprimer_bus(Bus b){
        liste_bus.remove(b);
    }
    
    
    public void mod_numero(int arg_num){
        numero = arg_num;
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
    
    public Noeud req_noeud_index(int index){
        return parcours.get(index);
    }
    
    public int req_nombre_noeuds(){
        return parcours.size();
    }
    
    public Arete getArete(int i){
        if (i+1 < parcours.size())
        {
            for (Arete a: parcours.get(i).listAretes){
                if (a.origine == parcours.get(i+1) || a.destination == parcours.get(i+1) ){
                    if (a.origine == parcours.get(i) || a.destination == parcours.get(i) ){
                        return a;
                    }

                }
            }
        }
        return null;
    }
    
    private List<Noeud> parcours;
    public List<Bus> liste_bus;
    private int numero;
    private int frequence;
    private int t_premier_depart;
    private int t_prochain_depart;
    public Boolean isLoop;
    public Boolean loopDone;
}

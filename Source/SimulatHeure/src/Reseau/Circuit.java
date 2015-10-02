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
    public Circuit(int arg_numero, int arg_frequence, int arg_t_depart){
        numero = arg_numero;
        frequence = arg_frequence;
        t_depart = arg_t_depart;
        parcours = new ArrayList<Arete>();
    }
    
    public void ajout_arete(Arete arg_arete){
        parcours.add(arg_arete);
        MAJ_t_parcours();
    }
    
    public int MAJ_t_parcours(){
        t_parcours = 0;
        for (int x = 0; x < parcours.size(); x = x+1){
            t_parcours += parcours.get(x).req_t();
        }
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
    private List<Arete> parcours;
    private int numero;
    private int frequence;
    private int t_depart;
    private int t_parcours;
}

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
public class Bus {
    public Bus(int numeroArg, Circuit circuitActuelArg, int arg_capacite){
     
        numero = numeroArg;
        circuitActuel = circuitActuelArg;
        nombre_noeud_parcourue = 1;
        positionX = circuitActuel.req_noeud_index(0).req_positionX();
        positionY = circuitActuel.req_noeud_index(0).req_positionY();
        capacite = arg_capacite;
        nombre_passager = 0;
        index_dernier_noeud = 0;
        speed = circuitActuel.getArete(0).speed;
        t_next_noeud = (getDistanceNextNoeud()/speed);
    }
    
    public void updateSpeed(){
        Arete a = circuitActuel.getArete(index_dernier_noeud);
        if (a != null){
            speed = a.speed;
        }
        else{
            speed = 0;
        }
    }
    
    public double reqSpeed(){
        return speed;
    }
    public void incrementer_nombre_noeud_parcourue(){
        nombre_noeud_parcourue++;
    }
    
    public int req_nombre_noeud_parcourue(){
        return nombre_noeud_parcourue;
    }
    
    public Circuit req_circuitActuel(){
        return circuitActuel;
    }
   
    public int req_capacite()
    {
        return capacite;
    }
    
    public void mod_positionX(double x)
    {
        positionX = x;
    }
    
    public void mod_positionY(double y)
    {
        positionY = y;
    }
    
    // retourne le nb de passager qui ne sont pas entrer
    public int ajout_nombre_passager(int arg_nombre_passager){
        
        if (nombre_passager == capacite){
            return arg_nombre_passager;
        }
        
        if ((nombre_passager + arg_nombre_passager) < capacite){
            nombre_passager += arg_nombre_passager;
            return 0;
        }
        else{
            int restant = arg_nombre_passager - (capacite - nombre_passager);
            nombre_passager = capacite;
            return restant;
        }
    }
    
    public void ret_nombre_passager(int arg_nombre_passager){
        
        nombre_passager -= arg_nombre_passager;
    }
    
    public void update_t_next_noeud(){
        
        t_next_noeud = (getDistanceNextNoeud()/speed);
    }
    
    /* NOT USED
    public void decrement_t_next_station(){
        t_next_station--;
    }
    */
   
     public int req_numero(){
    
        return numero;
    }
     
     public double req_positionX(){
    
        return positionX;
    }
          
     public double req_positionY(){
    
        return positionY;
    }
    
     public int req_index_derniere_noeud(){
         return index_dernier_noeud;
     }
     
     //not used yet
     public int req_nombre_passager(){
         return nombre_passager;
     }
     
     public double req_t_next_noeud(){
         return t_next_noeud;
     }
     
     public void mod_index_dernier_noeud(){
         index_dernier_noeud++;
     }
     
     public void reset(){
         index_dernier_noeud = 0;
         nombre_noeud_parcourue = 1;
         speed = circuitActuel.getArete(0).speed;
         t_next_noeud = (getDistanceNextNoeud()/speed);
     }
     
     public double getDistanceNextNoeud(){
         double x1, x2, y1, y2;
         if (index_dernier_noeud+1 == circuitActuel.req_nombre_noeuds()){
             return 0;
         }
         x1 = positionX;
         x2 = circuitActuel.req_noeud_index(index_dernier_noeud+1).req_positionX();
         y1 = positionY;
         y2 = circuitActuel.req_noeud_index(index_dernier_noeud+1).req_positionY();
         
         double distance = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
         return distance;
     }
    
     //using right now....
    private double speed;
    private int numero;
    private double positionX;
    private double positionY;
    private int capacite;
    private Circuit circuitActuel;
    private int index_dernier_noeud;
    private int nombre_noeud_parcourue;
    private double t_next_noeud;
    
    //not used yet
    private int nombre_passager;
}

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
        nombre_station_parcourue = 1;
        positionX = circuitActuel.req_station_index(0).req_positionX();
        positionY = circuitActuel.req_station_index(0).req_positionY();
        capacite = arg_capacite;
        nombre_passager = 0;
        index_derniere_station = 0;
        t_next_station = 5; //test
    }
    
    public void incrementer_nombre_station_parcourue(){
        nombre_station_parcourue++;
    }
    
    public int req_nombre_station_parcourue(){
        return nombre_station_parcourue;
    }
    
    public Circuit req_circuitActuel(){
        return circuitActuel;
    }
   
    public int req_capacite()
    {
        return capacite;
    }
    
    public void mod_positionX(int x)
    {
        positionX = x;
    }
    
    public void mod_positionY(int y)
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
    
    public void mod_t_next_station(int arg_t){
        
        t_next_station = arg_t;
    }

   
     public int req_numero(){
    
        return numero;
    }
     
     public int req_positionX(){
    
        return positionX;
    }
          
     public int req_positionY(){
    
        return positionY;
    }
    
     public int req_index_derniere_station(){
         return index_derniere_station;
     }
     
     public int req_nombre_passager(){
         return nombre_passager;
     }
     
     public int req_t_next_station(){
         return t_next_station;
     }
     
     public void mod_index_derniere_station(){
         index_derniere_station++;
     }
    
    private int numero;
    private int positionX;
    private int positionY;
    private int capacite;
    private Circuit circuitActuel;
    private int index_derniere_station;
    private int nombre_station_parcourue;
    private int nombre_passager;
    private int t_next_station;
}

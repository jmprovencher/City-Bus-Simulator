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
    public Bus(int numeroArg, Circuit circuitActuelArg){
     
        numero = numeroArg;
        circuitActuel = circuitActuelArg;
        positionX = 0;
        positionY = 0;
    }
    
    public void mod_positionX(int x)
    {
        positionX = x;
    }
    
    public void mod_positionY(int y)
    {
        positionY = y;
    }
    
    void mod_nombre_passager(int arg_nombre_passager){
        
        nombre_passager = arg_nombre_passager;
    }
    
    void mod_t_service(int arg_t_service){
        
        t_service = arg_t_service;
    }
    
    public Circuit req_circuitActuel(){
    
        return circuitActuel;
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
    
     public int req_nombre_passager(){
         return nombre_passager;
     }
     
     public int req_t_service(){
         return t_service;
     }
    
    private int numero;
    private int positionX;
    private int positionY;
    private Circuit circuitActuel;
    private int nombre_passager;
    private int t_service;
}

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
public class Arete {
    public Arete(){
        t = 0;
    }
    
    public int req_t(){
        return t;
    }
    public void mod_t(int arg_t){
        t= arg_t;
    }
    private int t;
}

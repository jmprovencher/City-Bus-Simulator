/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

/**
 *
 * @author rem54
 */
public class Circuit {
    public Circuit(int arg_numero, int arg_frequence){
        numero = arg_numero;
        frequence = arg_frequence;
    }
    
    public int req_numero(){
        return numero;
    }
    
    public int req_frequence(){
        return frequence;
    }

    private int numero;
    private int frequence;
}

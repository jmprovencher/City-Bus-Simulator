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
public class Route {
    public Route(int arg_numero, int arg_frequence, int argMaxBus, int arg_t_depart, List<Node> p){
        number = arg_numero;
        frequency = arg_frequence;
        timeFirstStart = arg_t_depart;
        timeNextStart = arg_t_depart;
        route = new ArrayList<Node>(p);
        listBus = new ArrayList<Bus>();
        maxBus = argMaxBus;
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
        for (Bus b: listBus){
            b.listPassenger.clear();
        }
        listBus.clear();
        
        loopDone = false;
        timeNextStart = timeFirstStart;
    }
    
    public Bus addBus(){
        if (!loopDone){
            Bus newBus = new Bus(0, this, 50);
            listBus.add(newBus);
            return newBus;
        }
        return null;
    }
    
    public void deleteBus(Bus b){
        listBus.remove(b);
    }
    
    
    public void setNumber(int arg_num){
        number = arg_num;
    }
    
    public int getNumber(){
        return number;
    }
    
    public int getFrequency(){
        return frequency;
    }
    
    public int getTimeNextStart(){
        return timeNextStart;
    }
    
    public void setTimeNextStart(double time){
        timeNextStart += time;
    }
    
    public Node getNodeFromIndex(int index){
        return route.get(index);
    }
    
    public int getNumberOfNodes(){
        return route.size();
    }
    
    public Boolean busAvalaible(){
        return listBus.size()<maxBus;
    }
    
    public Line getLineFromIndex(int i){
        if (i+1 < route.size())
        {
            for (Line a: route.get(i).listLines){
                if (a.origine == route.get(i+1) || a.destination == route.get(i+1) ){
                    if (a.origine == route.get(i) || a.destination == route.get(i) ){
                        return a;
                    }

                }
            }
        }
        return null;
    }
   public int getNodeIndexFromName(String name){
       int i = 0;
       for (Node n: route){
          
           if (n.isStation && n.getName().equalsIgnoreCase(name)){
               return i;
           }
           i++;
       }
       return -1;
   }
    
    public List<Node> route;
    public List<Bus> listBus;
    private int number;
    private int frequency;
    private int timeFirstStart;
    private int timeNextStart;
    private int maxBus;
    public Boolean isLoop;
    public Boolean loopDone;
}

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
public class Route implements java.io.Serializable{
    public Route(){
        number = 0;
        route = new ArrayList<Node>();
        listBus = new ArrayList<Bus>();
        listSources = new ArrayList<Source>();
        maxBus = 50;
        isLoop = false;
        loopDone = false;
        directionsUsingMe = 0;
    }
    
    public Boolean addNode(Node n){
        if (route.size() ==0 && n.isStation == false){
            return false;
        }
        else if (route.size() == 0 && n.isStation == true){
            route.add(n);
            return true;
        }
        else if (route.size()>0){
            for (Line a: route.get(route.size()-1).listLines){
                if (n != route.get(route.size()-1)){
                    if (n == a.destination){
                        route.add(n);
                        return true;
                     
                    }
                }
            }
        }
        return false;
       
    }
    
    public Boolean updateIfLoop(){
                 if (route.get(0) == route.get(route.size()-1)){
            canLoop = true;
            return true;
        }
        else{
            canLoop = false;
            return false;
        }
    }
    
    
    public void setMaxBus(int max){
        maxBus = max;
    }
    
    
    public void reset(){
        for (Bus b: listBus){
            b.listPassenger.clear();
        }
        listBus.clear();
        
        loopDone = false;
        for (Source s: listSources){
            s.timeNextStart = s.timeFirstStart;
        }

    }
    
    public Bus addBus(Source s){
        if (!loopDone){
            Bus newBus = new Bus(0, this, 50, s);
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
    
    public int getMaxBus(){
        return maxBus;
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
    
   public Source addSource(Node n, int time, int minFreq, int maxFreq, int typeFreq){
       Source newSource = new Source(n, time, minFreq, maxFreq, typeFreq);
       listSources.add(newSource);
       return newSource;
   }
   
    public List<Node> route;
    public List<Bus> listBus;
    public List<Source> listSources;
    private int number;
    private int maxBus;
    public int directionsUsingMe;
    public Boolean canLoop;
    public Boolean isLoop;
    public Boolean loopDone;
    
    public class Source implements java.io.Serializable{
        public Source(Node n, int time, int minFreq, int maxFreq, int typeFreq){
            originNode = n;
            timeFirstStart = time;
            timeNextStart = timeFirstStart;
            minFrequency = minFreq;
            maxFrequency = maxFreq;
            typeFrequency = typeFreq;
                    
            
        }
        
    public void setTimeNextStart(double time){
        timeNextStart += time;
    }
        public void setTimeFirstStart(int t){
            timeFirstStart = t;
            timeNextStart = t;
        }
        public Node originNode;
        public int timeFirstStart;
        public int timeNextStart;
        public int minFrequency;
        public int maxFrequency;
        public int typeFrequency;
        
    }
}

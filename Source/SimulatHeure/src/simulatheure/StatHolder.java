/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

/**
 *
 * @author Charles-Olivier
 */
import java.util.*;

public class StatHolder implements java.io.Serializable{
    public List<String> tagList = new ArrayList<>();
    public List<Double> minList = new ArrayList<>();
    public List<Double> maxList = new ArrayList<>();
    public List<Integer> numList = new ArrayList<>();
    public List<Double> avgList = new ArrayList<>();
    
    public void add(StatHolder newStats){
        boolean foundIt;
        int j;
        int tempNum;
        for(int i = 0; i < newStats.tagList.size(); i++){
            foundIt = false;
            j = 0;
            while(j < this.tagList.size() && foundIt == false){
                if(this.tagList.get(j).equals(newStats.tagList.get(i))){
                    foundIt = true;
                    tempNum = this.numList.get(j);
                    this.minList.set(j, Math.min(this.minList.get(j), newStats.minList.get(i)));
                    this.maxList.set(j, Math.max(this.minList.get(j), newStats.minList.get(i)));
                    this.numList.set(j, tempNum + newStats.numList.get(i));
                    double newAvg = ((this.avgList.get(j) * tempNum) + (newStats.avgList.get(j) * newStats.numList.get(j)))/(this.numList.get(j));
                    this.avgList.set(j, newAvg);
                }
                j++;
            }
            if(foundIt == false){
                this.tagList.add(newStats.tagList.get(i));
                this.minList.add(newStats.minList.get(i));
                this.maxList.add(newStats.maxList.get(i));
                this.numList.add(newStats.numList.get(i));
                this.avgList.add(newStats.avgList.get(i));
            }
        }
    }
    
    public void clear(){
        tagList.clear();
        minList.clear();
        maxList.clear();
        numList.clear();
        avgList.clear();
    }
}

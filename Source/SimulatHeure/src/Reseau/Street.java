/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;
import java.util.*;
/**
 *
 * @author Rémi
 */
public class Street {
    
    public Street(String argName, List<Line> argLinesList){
        name = argName;
        linesList = argLinesList;
    }
   
    public String getName(){
        return name;
    }
    
    
    private String name;
    private List<Line> linesList;
}

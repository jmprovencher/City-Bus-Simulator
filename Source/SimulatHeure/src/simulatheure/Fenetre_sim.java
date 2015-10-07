/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import Reseau.*;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
/**
 *
 * @author rem54
 */
public class Fenetre_sim extends JPanel {
        
    public Fenetre_sim(){
        
        try
        {
        img_station = ImageIO.read(getClass().getResource("/images/icon.png"));
        img_station_selected = ImageIO.read(getClass().getResource("/images/station-selected.png"));
        img_bus = ImageIO.read(getClass().getResource("/images/bus.png"));
        }
        catch (IOException e)
        {
            
        }
        img_station_size = img_station.getWidth();
        img_bus_size = img_bus.getWidth();
        Sim = new Simulation();
        
        liste_stations_selected = new ArrayList<Station>();
        liste_Aretes_selected = new ArrayList<Arete>();
        liste_Buses_selected = new ArrayList<Bus>();
    }
    
    public void display_sim(Graphics g){
        for (int i = 0; i < Sim.req_nombre_stations(); i++){
            Station station_i = Sim.req_station_index(i);
            if (liste_stations_selected.contains(station_i)){
                g.drawImage(img_station_selected, station_i.req_positionX() - img_station_size/2, station_i.req_positionY()- img_station_size/2, null);
            } else {
                g.drawImage(img_station, station_i.req_positionX() - img_station_size/2, station_i.req_positionY()- img_station_size/2, null);    
            }
        }
       for (int i = 0; i < Sim.req_nombre_circuits(); i++){
           Circuit circuit_i = Sim.req_circuit_index(i);
           int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
           
           x1 = circuit_i.req_station_index(0).req_positionX();
           y1 = circuit_i.req_station_index(0).req_positionY();
           
           for (int y = 1; y < circuit_i.req_nombre_stations(); y++){
                x2 = circuit_i.req_station_index(y).req_positionX();
                y2 = circuit_i.req_station_index(y).req_positionY();
                g.drawLine(x1, y1, x2, y2);
                x1 = x2;
                y1 = y2;
           }
           
           
           for (Bus b : circuit_i.liste_bus){
                g.drawImage(img_bus, b.req_positionX() - img_bus_size/2, b.req_positionY()- img_bus_size/2, null);
           }
       }
       
       int x1 = 0, x2 = 0, y1 = 0, y2 = 0, count = 0;
       for (Station s: Sim.parcours){
           if (count == 0){x1 = s.req_positionX(); y1 = s.req_positionY();count++;continue;}
           x2 = s.req_positionX();
           y2 = s.req_positionY();
           g.drawLine(x1,y1,x2,y2);
           x1 = x2;
           y1 = y2;
           count++;
       }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        display_sim(g);
        
    }
    
    /*
    Item selection management
    */
    
    public void selectStation(Station st){
        liste_stations_selected.add(st);
    }
    
    public void selectArete(Arete arr){
        liste_Aretes_selected.add(arr);
    }
    
    public void selectBus(Bus b){
        liste_Buses_selected.add(b);
    }
    
    public void clearSelection(){
        liste_stations_selected.clear();
        liste_Aretes_selected.clear();
        liste_Buses_selected.clear();
    }
    
    /*
     END Item selection management
    */

     private List<Station> liste_stations_selected;
     private List<Arete> liste_Aretes_selected;
     private List<Bus> liste_Buses_selected;
     public BufferedImage img_station;
     public BufferedImage img_station_selected;
     public BufferedImage img_bus;
     public int img_station_size;
     public int img_bus_size;
     public int x;
     public int y;
     public Simulation Sim;
}

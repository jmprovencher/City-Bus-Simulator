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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
/**
 *
 * @author rem54
 */
public class FenetreSim extends JPanel {
        
    public FenetreSim(){
        defaultCursor = new Cursor(0); // pointing hand
        handCursor = new Cursor(12); // pointing hand
        quadraArrowsCursor = new Cursor(13); // crosshair arrows
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
        
        liste_Noeuds_selected = new ArrayList<Noeud>();
        liste_Aretes_selected = new ArrayList<Arete>();
        liste_Buses_selected = new ArrayList<Bus>();
        
        
        /* ----------------- GRAPHIC CONTROL TIMER ------------------ */
        ActionListener action = new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent event)
            {
                repaint();
            }
        };
        
        displayTimer = new javax.swing.Timer(16, action);
    }
    
    public void display_sim(Graphics g){
        
        g.drawString("Temps: "+Sim.freq*Sim.count/1000, 10, 20);
        

        
        
       for (int i = 0; i < Sim.req_nombre_circuits(); i++){
           Circuit circuit_i = Sim.req_circuit_index(i);
           
 
           for (Bus b : circuit_i.liste_bus){
                g.drawImage(img_bus, (int)b.req_positionX() - img_bus_size/2, (int)b.req_positionY()- img_bus_size/2, null);
           }
       }
       
       int x1 = 0, x2 = 0, y1 = 0, y2 = 0, count = 0;
       for (Noeud s: Sim.parcours){
           if (count == 0){x1 = s.req_positionX(); y1 = s.req_positionY();count++;continue;}
           x2 = s.req_positionX();
           y2 = s.req_positionY();
           g.drawLine(x1,y1,x2,y2);
           x1 = x2;
           y1 = y2;
           count++;
       }
       for (Arete a: Sim.liste_aretes){
           g.drawLine((int)a.line.getX1(), (int)a.line.getY1(), (int)a.line.getX2(),(int)a.line.getY2());
       }
       for (Noeud n: Sim.liste_noeuds){
           if (liste_Noeuds_selected.contains(n)){
                   g.drawOval(n.req_positionX()-25, n.req_positionY()-25, 50, 50);
           }
           if(!n.isStation){
                g.drawRect(n.req_positionX()-10, n.req_positionY()-10, 20, 20);
           }
           else{

               
                    g.drawImage(img_station, n.req_positionX() - img_station_size/2, n.req_positionY()- img_station_size/2, null);    
               
               }
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
    
    public void selectNoeud(Noeud n){
        liste_Noeuds_selected.add(n);
    }
    
    public void selectArete(Arete arr){
        liste_Aretes_selected.add(arr);
    }
    
    public void selectBus(Bus b){
        liste_Buses_selected.add(b);
    }
    
    public void clearSelection(){
        liste_Noeuds_selected.clear();
        liste_Aretes_selected.clear();
        liste_Buses_selected.clear();
    }
    
    public Cursor getHandCursor(){
        return handCursor;
    }
    public Cursor getQuadraArrowsCursor(){
        return quadraArrowsCursor;
    }
    public Cursor getDefaultCursor(){
        return defaultCursor;
    }
    
    /*
     END Item selection management
    */
     private Cursor defaultCursor;
     private Cursor handCursor;
     private Cursor quadraArrowsCursor;
     public javax.swing.Timer displayTimer;
     private List<Noeud> liste_Noeuds_selected;
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

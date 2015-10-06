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
        }
        catch (IOException e)
        {
            
        }
        img_station_size = img_station.getWidth();
        Sim = new Simulation();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < Sim.req_nombre_stations(); i++){
            Station station_i = Sim.req_station_index(i);
            g.drawImage(img_station, station_i.req_positionX() - img_station_size/2, station_i.req_positionY()- img_station_size/2, null);
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
       }
    }
    
     public BufferedImage img_station;
     public int img_station_size;
     public int x;
     public int y;
     public Simulation Sim;
}

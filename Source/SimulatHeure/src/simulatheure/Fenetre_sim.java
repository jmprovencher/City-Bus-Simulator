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
        for (Station s: Sim.liste_stations){
            g.drawImage(img_station, s.req_positionX() - img_station_size/2, s.req_positionY()- img_station_size/2, null);
        }
    }
    
     public BufferedImage img_station;
     int img_station_size;
     public int x;
     public int y;
     public Simulation Sim;
}

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
public class myJPanel extends JPanel {
    
    public myJPanel(){
        
        try
        {
        img_station = ImageIO.read(new File("images/icon.png"));
        }
        catch (IOException e)
        {
            
        }
        liste_stations = new ArrayList<Station>();
        s_count = 0;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (Station s : liste_stations){
            g.drawImage(img_station, s.req_positionX(), s.req_positionY(), null);
        }
    }
    
     private static BufferedImage img_station;
     public int x;
     public int y;
     public int s_count;
     public List<Station> liste_stations;
}

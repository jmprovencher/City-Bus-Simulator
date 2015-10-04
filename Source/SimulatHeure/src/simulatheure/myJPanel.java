/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img_station, x, y, null);
    }
    
     private static BufferedImage img_station;
     public int x;
     public int y;
}

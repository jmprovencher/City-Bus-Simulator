/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import Reseau.*;
import java.awt.Color;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.awt.geom.Line2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
/**
 *
 * @author rem54
 */
public class SimDisplay extends JPanel {
        

   
    public SimDisplay(){
        
        
        System.out.println(topFrame);
        scale = 1;
        centerPositionX = 0;
        centerPositionY = 0;
        
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
        
        createLineTemp = null;

        liste_Noeuds_selected = new ArrayList<Node>();
        liste_Aretes_selected = new ArrayList<Line>();
        liste_Buses_selected = new ArrayList<Bus>();
        
        
        /* ----------------- GRAPHIC CONTROL TIMER ------------------ */
        ActionListener action = new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent event)
            {
                repaint();
                topFrame.displayTime();
                
            }
        };
        
        displayTimer = new javax.swing.Timer(16, action);
    }
    
    public void displaySim(Graphics g){
       
        Graphics2D g2 = (Graphics2D) g;
        double w = this.getWidth(); // real width of canvas
        double h = this.getHeight();// real height of canvas
        AffineTransform old= g2.getTransform();
        AffineTransform tr= new AffineTransform(old);
        tr.translate((this.getWidth()/2) - ((this.getWidth()/2-centerPositionX)*(scale))  ,
                    (this.getHeight()/2) -((this.getHeight()/2-centerPositionY)*(scale)));
        tr.scale(scale,scale);
        g2.setTransform(tr);

       for (int i = 0; i < Sim.getRouteQuantity(); i++){
           Route circuit_i = Sim.getRouteFromIndex(i);

           for (Bus b : circuit_i.listBus){
                g.drawImage(img_bus, (int)b.getPositionX() - img_bus_size/2, (int)b.getPositionY()- img_bus_size/2, null);
           }
       }
       
       for (Line a: Sim.listLines){
           g.drawLine((int)a.line.getX1(), (int)a.line.getY1(), (int)a.line.getX2(),(int)a.line.getY2());
       }
       for (Node n: Sim.listNodes){
           if (liste_Noeuds_selected.contains(n)){
                   g.drawOval(n.getPositionX()-25, n.getPositionY()-25, 50, 50);
           }
           if(!n.isStation){
                g.drawRect(n.getPositionX()-10, n.getPositionY()-10, 20, 20);
           }
           else{

               
                    g.drawImage(img_station, n.getPositionX() - img_station_size/2, n.getPositionY()- img_station_size/2, null);    
               
               }
       }
       
       g.setColor(Color.red);
       int x1 = 0, x2 = 0, y1 = 0, y2 = 0, count = 0;
       for (Node n: Sim.newRoute){
           if (count == 0){x1 = n.getPositionX(); y1 = n.getPositionY();count++;continue;}
           x2 = n.getPositionX();
           y2 = n.getPositionY();
           g.drawLine(x1,y1,x2,y2);
           x1 = x2;
           y1 = y2;
           count++;
       }
       
       // ligne pendant la création d'une arrete
       BasicStroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
       if (createLineTemp != null){
            g.setColor(Color.gray);
            g2.setStroke(dashed);
            g.drawLine((int)createLineTemp.getX1(), (int)createLineTemp.getY1(), (int)createLineTemp.getX2(), (int)createLineTemp.getY2());
       
       }
       
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        displaySim(g);

    }
    
    public void updateScale(int n){
        
        double scaleFactor = 0;
        if (Math.abs(n) == 1){
            scaleFactor = 1.1;
        }
        if (Math.abs(n) >= 1){
            scaleFactor = 1.1;
        }
        
        if (n <= -1){
            scale = scale *(scaleFactor);
        }
        else if (n >= 1){
            
            scale = scale/(scaleFactor);
        }
        
        repaint();

    }
    
    /*
    Item selection management
    */
    
    public void selectNode(Node n){
        liste_Noeuds_selected.add(n);
    }
    
    public void selectLine(Line arr){
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
    
    public double getSimTime(){
        return Sim.freq*Sim.count;
    }
    
    /*
     END Item selection management
    */

     double centerPositionX;
     double centerPositionY;
     public javax.swing.Timer displayTimer;
     private List<Node> liste_Noeuds_selected;
     private List<Line> liste_Aretes_selected;
     private List<Bus> liste_Buses_selected;
     public Cursor defaultCursor;
     public Cursor quadraArrowsCursor;
     public Cursor handCursor;
     public BufferedImage img_station;
     public BufferedImage img_station_selected;
     public BufferedImage img_bus;
     public int img_station_size;
     public int img_bus_size;
     public int x;
     public int y;
     public Line2D.Double createLineTemp;
     public Simulation Sim;
     public double scale;
     public SimulatHeure topFrame;
}

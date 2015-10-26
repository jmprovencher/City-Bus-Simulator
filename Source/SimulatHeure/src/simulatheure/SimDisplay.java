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
        lightGray = new Color(190,190,190);
        lightLightGray = new Color(220,220,220);
        lightLightLightGray = new Color(240,240,240);

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
        
        displayTimer = new javax.swing.Timer(33, action);
    }
    
   
    
    private final int ARR_SIZE = 10;
    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
                Graphics2D g = (Graphics2D) g1.create();

                double dx = x2 - x1, dy = y2 - y1;
                double angle = Math.atan2(dy, dx);
                int len = (int) Math.sqrt(dx*dx + dy*dy);
                AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
                at.concatenate(AffineTransform.getRotateInstance(angle));
                g.transform(at);

                // Draw horizontal arrow starting in (0, 0)
                g.drawLine(0, 0, len, 0);
                g.fillPolygon(new int[] {len-12, len-ARR_SIZE-12, len-ARR_SIZE-12, len-12},
                              new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
   }
    
    private final int GRID_SIZE = 10000;
    
    public void drawGrid(Graphics g, int scale){
        
        for (int i = -GRID_SIZE/2; i <=GRID_SIZE/2; i = i+scale){
           g.drawLine(-GRID_SIZE/2, i, GRID_SIZE/2 , i);
       }
        
        for (int i = -GRID_SIZE/2; i <=GRID_SIZE/2; i = i+scale){
           g.drawLine( i, -GRID_SIZE/2, i,GRID_SIZE/2 );
       }
        g.setColor(Color.black);
    }
    
    public void setCenterPosition(int x, int y){
            centerPositionX -= x;
            centerPositionY -=  y;
            int limit = GRID_SIZE/2 +750;
            if (centerPositionX >limit){
                centerPositionX = limit;
            }
            if (centerPositionX <-limit){
                centerPositionX = -limit;
            }
            if (centerPositionY <-limit){
                centerPositionY = -limit;
            }
            if (centerPositionY >limit){
                centerPositionY = limit;
            }
            repaint();
    }
    
    public void displaySim(Graphics g){

        
        //coordinates setup
        Graphics2D g2 = (Graphics2D) g;
        double w = this.getWidth(); // real width of canvas
        double h = this.getHeight();// real height of canvas
        AffineTransform old= g2.getTransform();
        AffineTransform tr= new AffineTransform(old);
        tr.translate((this.getWidth()/2) - ((this.getWidth()/2-centerPositionX)*(scale))  ,
                    (this.getHeight()/2) -((this.getHeight()/2-centerPositionY)*(scale)));
        tr.scale(scale,scale);
        g2.setTransform(tr);

        //white background
        g.setColor(Color.white);
        g.fillRect(0-GRID_SIZE/2, 0-GRID_SIZE/2, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.black);
        //grid
        if (scale >= 0.04 && scale <= 0.1){
            g.setColor(lightGray);
            drawGrid(g, 250);
        }
        if (scale > 0.1 && scale <= 0.5){
            
            g.setColor(lightLightGray);
            drawGrid(g, 50);
            g.setColor(lightGray);
            drawGrid(g, 250);
        }
        if (scale >0.5 ){
            g.setColor(lightLightLightGray);
            drawGrid(g, 10);
            g.setColor(lightLightGray);
            drawGrid(g, 50);
            g.setColor(lightGray);
            drawGrid(g, 250);
        }
        
       
        // end grid
        
       for (int i = 0; i < Sim.getRouteQuantity(); i++){
           Route circuit_i = Sim.getRouteFromIndex(i);
           
           for (Bus b : circuit_i.listBus){
                g.drawImage(img_bus, (int)b.getPositionX() - img_bus_size/2, (int)b.getPositionY()- img_bus_size/2, null);
                g.drawString(""+b.listPassenger.size(), (int)b.getPositionX(), (int)b.getPositionY()-40);
           }
       }
       
       for (Line l: Sim.listLines){
           if (liste_Aretes_selected.contains(l)){
               g.setColor(Color.red);
           }
           drawArrow(g, (int)l.line.getX1(), (int)l.line.getY1(), (int)l.line.getX2(),(int)l.line.getY2());
           g.setColor(Color.black);
           
       }
       for (Node n: Sim.listNodes){
           if (n.isStation){
               g.drawString(""+n.listPassenger.size(), n.getPositionX(), n.getPositionY()-25);
           }
           if (liste_Noeuds_selected.contains(n)){
                g.setColor(Color.red);  
                if (n.isStation){
                    g.drawImage(img_station_selected, n.getPositionX() - img_station_size/2, n.getPositionY()- img_station_size/2, null);    
                }
                else{
                    g.drawRect(n.getPositionX()-10, n.getPositionY()-10, 20, 20);
                }
                g.setColor(Color.black);
           }
           else if(!n.isStation){
               
               g.drawRect(n.getPositionX()-10, n.getPositionY()-10, 20, 20);
               
                
           }
           else{
                
                g.drawImage(img_station, n.getPositionX() - img_station_size/2, n.getPositionY()- img_station_size/2, null); 
                
           }
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
            if (scale < 6){
                scale = scale *(scaleFactor);
            }
        }
        else if (n >= 1){
            if (scale > 0.05){
                scale = scale/(scaleFactor);
            }
        }
        
        repaint();

    }
    
    public void resetDisplay(){
     centerPositionX = 0;
     centerPositionY = 0;
     scale = 1;
     repaint();
    }
    
    public int getGridPositionX(int mouseClickPositionX){
        int gridX = (int) ((double)mouseClickPositionX/scale - (-1+(1/scale))*getWidth()/2 - centerPositionX);
        //x = gridX;
        return gridX;
    }
    
     public int getGridPositionY(int mouseClickPositionY){
        int gridY = (int) ((double)mouseClickPositionY/scale - (-1+(1/scale))*getHeight()/2 - centerPositionY);
        //y = gridY;
        return gridY;
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
     public Line2D.Double createLineTemp;
     public Simulation Sim;
     public double scale;
     public SimulatHeure topFrame;
     private Color lightGray;
     private Color lightLightGray;
     private Color lightLightLightGray;
     
     
}

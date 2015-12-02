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
import java.awt.Cursor;
import java.awt.geom.*;
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
            imgStationEmpty = ImageIO.read(getClass().getResource("/images/bustop0.png"));
            imgStationHalf = ImageIO.read(getClass().getResource("/images/bustop1.png"));
            imgStationFull = ImageIO.read(getClass().getResource("/images/bustop2.png"));
            imgStationSelected = ImageIO.read(getClass().getResource("/images/bustopselected.png"));
            imgBus = ImageIO.read(getClass().getResource("/images/bus2.png"));
            imgBusSelected = ImageIO.read(getClass().getResource("/images/bus2Selected.png"));
        }
        catch (IOException e)
        {
            
        }
        stationSize = imgStationEmpty.getWidth();
        imgBusSize = imgBus.getWidth();
        imgBusSelectedSize = imgBusSelected.getWidth();
        nodeSize = 20;
        Sim = new Simulation();
        
        selectionRectangle = new Rectangle2D.Double(0, 0, 0, 0);
        createLineTemp = new Line2D.Double(0, 0, 0, 0);
        lightGray = new Color(30,30,30);
        lightLightGray = new Color(70,70,70);
        lightLightLightGray = new Color(110,110,110);

        listSelectedNode = new ArrayList<>();
        listSelectedLine = new ArrayList<>();
        listSelectedBus = new ArrayList<>();
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
                g.setStroke(new BasicStroke(4));
                g.drawLine(0, 0, len, 0);
                g.fillPolygon(new int[] {len/2+20, len/2-ARR_SIZE+20, len/2-ARR_SIZE+20, len/2+20},
                              new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
   }
    
    private final int GRID_SIZE = 20000;
    
    public void drawGrid(Graphics g, int scale){
        
        for (int i = -GRID_SIZE/2; i <=GRID_SIZE/2; i = i+scale){
           g.drawLine(-GRID_SIZE/2, i, GRID_SIZE/2 , i);
       }
        
        for (int i = -GRID_SIZE/2; i <=GRID_SIZE/2; i = i+scale){
           g.drawLine( i, -GRID_SIZE/2, i,GRID_SIZE/2 );
       }
        g.setColor(Color.white);
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
        g.setColor(Color.gray);
        g.fillRect(0-GRID_SIZE/2, 0-GRID_SIZE/2, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.white);
        //grid
        if (scale >= 0.02 && scale <= 0.2){
            g.setColor(lightGray);
            drawGrid(g, 250);
        }
        if (scale > 0.2 && scale <= 0.6){
            
            g.setColor(lightLightGray);
            drawGrid(g, 50);
            g.setColor(lightGray);
            drawGrid(g, 250);
        }
        if (scale >0.6 ){
            g.setColor(lightLightLightGray);
            drawGrid(g, 10);
            g.setColor(lightLightGray);
            drawGrid(g, 50);
            g.setColor(lightGray);
            drawGrid(g, 250);
        }

        // end grid
       
       for (Line l: Sim.listLines){
           if (listSelectedLine.contains(l)){
               g.setColor(Color.red);
           }
           drawArrow(g, (int)l.line.getX1(), (int)l.line.getY1(), (int)l.line.getX2(),(int)l.line.getY2());
           g.setColor(Color.white);
           
       }
       for (int i = 0; i < Sim.getRouteQuantity(); i++){
            Route circuit_i = Sim.getRouteFromIndex(i);

            for (Bus b : circuit_i.listBus){
                if (listSelectedBus.contains(b)){
                     g.drawImage(imgBusSelected, (int)b.getPositionX() - imgBusSize/2, (int)b.getPositionY()- imgBusSize/2, null);
                }
                else{
                     g.drawImage(imgBus, (int)b.getPositionX() - imgBusSize/2, (int)b.getPositionY()- imgBusSize/2, null);

                }
                g.setColor(Color.WHITE);
                g.drawString(""+b.listPassenger.size(), (int)b.getPositionX(), (int)b.getPositionY()-40);
                g.setColor(Color.WHITE);
                g.drawString(""+b.getRoute().getNumber(), (int)b.getPositionX()-5, (int)b.getPositionY()-5);
            }
       }
       
       for (Node n: Sim.listNodes){
           if (n.isStation){
               g.drawString(""+n.listPassenger.size(), (int)n.getPositionX(), (int)n.getPositionY()-50);
           }
           if (listSelectedNode.contains(n)){
                g.setColor(Color.red);  
                if (n.isStation){
                    g.drawImage(imgStationSelected, (int)n.getPositionX() - stationSize/2, (int)n.getPositionY()- imgStationEmpty.getHeight()/2, null);    
                }
                else{
                    g.fillOval((int)n.getPositionX()-10, (int)n.getPositionY()-10, 20, 20);
                }
                g.setColor(Color.white);
           }
           else if(!n.isStation){
               
               g.fillOval((int)n.getPositionX()-10, (int)n.getPositionY()-10, 20, 20);
               
                
           }
           else{
               int passengerInStation = n.listPassenger.size();
                if (passengerInStation == 0){
                    g.drawImage(imgStationEmpty, (int)n.getPositionX() - stationSize/2, (int)n.getPositionY()- imgStationEmpty.getHeight()/2, null); 
                }
                else if (passengerInStation < 10){
                    g.drawImage(imgStationHalf, (int)n.getPositionX() - stationSize/2, (int)n.getPositionY()- imgStationEmpty.getHeight()/2, null); 
                }
                else{
                    g.drawImage(imgStationFull, (int)n.getPositionX() - stationSize/2, (int)n.getPositionY()- imgStationEmpty.getHeight()/2, null); 
                }
                
           }
        }
       
       // ligne pendant la création d'une arrete
       BasicStroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0);
       if (createLineTemp != null){
            g.setColor(Color.white);

                g2.setStroke(dashed);

            g.drawLine((int)createLineTemp.getX1(), (int)createLineTemp.getY1(), (int)createLineTemp.getX2(), (int)createLineTemp.getY2());
       
       }
       if (selectionRectangle !=  null){  
            g.drawRect((int)selectionRectangle.getX(), (int)selectionRectangle.getY(), (int)selectionRectangle.getWidth(), (int)selectionRectangle.getHeight());
       }
       
       // Scale indicator
       g2.setStroke(new BasicStroke());
       g2.setTransform(old);
       g2.setColor(Color.WHITE);
       int scaleIndicatorLeft =  getGridPositionX((int)w-110);
       int scaleIndicatorRight = getGridPositionX((int)w-10);
       int size = scaleIndicatorRight - scaleIndicatorLeft;
       g2.drawString(size+" m", (int)w - 70,(int)h - 15 );
       g2.drawLine((int)w-110, (int)h-15, (int)w-110, (int)h-5);
       g2.drawLine((int)w-110, (int)h-10, (int)w-10, (int)h-10);
       g2.drawLine((int)w-10, (int)h-15, (int)w-10, (int)h-5);
        
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
            if (scale > 0.03){
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
    
    public void selectRectangle(){
        List<Node> nodesToSelect = new ArrayList<Node>();
        for (Node n: Sim.listNodes){
            if(selectionRectangle.contains(n.getPositionX(), n.getPositionY())){
                nodesToSelect.add(n);
            }
        }
        selectNode(nodesToSelect);
        //Lines
        List<Line> linesToSelect = new ArrayList<Line>();
        for (Line l: Sim.listLines){
            if(selectionRectangle.intersectsLine(l.line)){
                linesToSelect.add(l);
            }
        }
        selectLine(linesToSelect);
    }
    
    
    public void selectNode(List<Node> listNodes){
        for(Node n: listNodes){
            listSelectedNode.add(n);
        }
        repaint();
    }
    
    public void selectLine(List<Line> listLines){
        for(Line l :listLines){
            listSelectedLine.add(l);
            repaint();
        }
    }
    
    public void selectBus(Bus b){
        listSelectedBus.add(b);
        repaint();
    }
    
    public void selectRoute(Route r){
        List<Line> linesToSelect = new ArrayList<Line>();
        for (int y = 0; y< r.getNumberOfNodes()-1; y++){
            selectNode(r.route);
            linesToSelect.add(r.getLineFromIndex(y));
        }
        selectLine(linesToSelect);
  
    }
    
    public void selectDirections(Directions d){
        Node currentNode;
        Node lastNode = null;
        for (Directions.SubRoute s: d.directions){
            int size  = s.size();
            Route r = s.getRoute();
            selectNode(s.subRoute);
            List<Line> linesToSelect = new ArrayList<Line>();
            for (int i = 0; i < size; i++){
                currentNode = s.getNode(i);
                if (i > 0){
                    linesToSelect.add(Sim.getLine(lastNode, currentNode));
                }
                lastNode = currentNode;
                
            }
            selectLine(linesToSelect);
        }
    }
    
    public void clearSelection(){
        listSelectedNode.clear();
        listSelectedLine.clear();
        listSelectedBus.clear();
        createLineTemp.setLine(0, 0, 0, 0);
        selectionRectangle.setRect(0, 0, 0, 0);
        repaint();
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
     private List<Node> listSelectedNode;
     private List<Line> listSelectedLine;
     private List<Bus> listSelectedBus;
     public Cursor defaultCursor;
     public Cursor quadraArrowsCursor;
     public Cursor handCursor;
     public BufferedImage imgStationEmpty;
     public BufferedImage imgStationHalf;
     public BufferedImage imgStationFull;
     public BufferedImage imgStationSelected;
     public BufferedImage imgBus;
     public BufferedImage imgBusSelected;
     public int nodeSize;
     public int stationSize;
     public int imgBusSize;
     public int imgBusSelectedSize;
     public Line2D.Double createLineTemp;
     public Rectangle2D.Double selectionRectangle;
     public Simulation Sim;
     public double scale;
     private Color lightGray;
     private Color lightLightGray;
     private Color lightLightLightGray;
     
     
}

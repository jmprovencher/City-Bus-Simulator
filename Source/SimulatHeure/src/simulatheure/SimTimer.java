/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author rem54
 */
public class SimTimer {
    public SimTimer(Simulation sim_arg, SimDisplay d, SimulatHeure s){
        sim= sim_arg;
        topFrame = s;
        display = d;
        time = 0;
        freq = 0;
        running = false;
        simSpeed = 0;
    }
    

    
    ActionListener action = new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent event)
            {
               if (sim.count < time/((double)freq/1000)){
                    if (simSpeed >= 1 ){

                         for (int i = 0; i< simSpeed; i++){
                             sim.simulateTick();
                             display.repaint();
                             topFrame.displayTime();
                         }
                    }
               }
               else{
                   stop();
               }
            };
        
        };
    
 
    
    public void setSimSpeed(int s){
        simSpeed= s;
    }
    
    public void start(int t, int argFreq, Boolean fullSim){
        time = t;
        freq = argFreq;
        sim.freq = freq;
        sim.setLinesSpeed();
        topFrame.selectOnly(true);
        
       if (!fullSim){
        
        simTimer = new javax.swing.Timer(freq, action);
        simTimer.start();
        running = true;
       }
       else{
           while (sim.count < time/((double)freq/1000)){

            sim.simulateTick();
           }
            topFrame.selectOnly(false);
            sim.stopSimulation();
        }

    }
    
    public void stop(){
        simTimer.stop();
        sim.stopSimulation();
        display.repaint();
        topFrame.displayTime();
        topFrame.selectOnly(false);
        running = false;
    }
    
    public int simSpeed;
    public SimulatHeure topFrame;
    private SimDisplay display;
    private javax.swing.Timer simTimer;
    private Simulation sim;
    private int time;
    private int freq;
    public Boolean running;
    
}

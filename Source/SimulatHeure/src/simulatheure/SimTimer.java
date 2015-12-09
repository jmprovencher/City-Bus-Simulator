/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author rem54
 */
public class SimTimer {
    public SimTimer(Simulation sim_arg, SimDisplay d, SimulatHeure s){
        sim= sim_arg;
        topFrame = s;
        display = d;
        recentStats = new StatHolder();
        time = 0;
        freq = 0;
        running = false;
        simSpeed = 0;
        simTimer = new javax.swing.Timer(freq, action);
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
    
    public void start(int t, int startTime, int argFreq, Boolean fullSim, int numberOfSimulations){
        time = t;
        freq = argFreq;
        sim.freq = freq;
        
        topFrame.selectOnly(true);
        
       if (!fullSim){
           sim.setLinesSpeed();
        while (sim.count < startTime/((double)freq/1000)){

            sim.simulateTick();
        }
        simTimer = new javax.swing.Timer(freq, action);
        simTimer.start();
        running = true;
       }
       else{
          
          for (int i = 0; i< numberOfSimulations; i++){
            sim.setLinesSpeed();
            while (sim.count < time/((double)freq/1000)){

             sim.simulateTick();
            }
            topFrame.selectOnly(false);
            stop();
        }
       }
       

    }
    
    public void stop(){
        simTimer.stop();
        recentStats = sim.stopSimulation();
        System.out.println(recentStats);
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
    public StatHolder recentStats;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;


import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
/**
 *
 * @author Rémi
 */
public class TimeJSpinner extends JSpinner{
    
    public TimeJSpinner(){
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MINUTE, 1439); // number of minutes in a day - 1

        model = new SpinnerDateModel(calendar.getTime(),
                null,
                null,
                Calendar.MINUTE);
       this.setModel(model);
       this.setEditor(new JSpinner.DateEditor(this, "HH:mm"));


    }
    public int getMinutes(){
        return calendar.get(calendar.MINUTE);
        
    }
    
    public int getHours(){
        if (calendar.get(Calendar.AM_PM) == Calendar.PM){
            return  calendar.get(calendar.HOUR)+12;
        }
        return  calendar.get(calendar.HOUR);
        
    }
    
    public void setTime(){
       Date d = (Date)this.getValue();

       calendar.setTime(d);

    }
    
    public void incrementHours(){
        model.setCalendarField(Calendar.HOUR);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+1);
        model.setValue(model.getNextValue());
        
    }
    
    private Calendar calendar;
    private SpinnerDateModel model;
    
}

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

        SpinnerDateModel model = new SpinnerDateModel(calendar.getTime(),
                null,
                null,
                Calendar.MINUTE);
       this.setModel(model);
       this.setEditor(new JSpinner.DateEditor(this, "HH:mm"));
        setTime();


    }
    public int getMinutes(){
        return calendar.get(calendar.MINUTE);
        
    }
    
    public int getHours(){
        return calendar.get(calendar.MINUTE);
        
    }
    
    public void setTime(){
       Date d = (Date)this.getValue();
       calendar.setTime(d);
    }
    private Calendar calendar;
    
}

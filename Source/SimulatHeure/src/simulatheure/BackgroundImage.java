/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import java.awt.Image;
import java.io.*;

/**
 *
 * @author Sam
 */
public class BackgroundImage implements Serializable{
    
    private transient Image bgImage;
    public boolean enabled;
    public boolean gridEnabled;
    private int scaleFactor;
    private boolean requireRescaling;
    
    // constructor
    public BackgroundImage(){
        enabled = false;
        gridEnabled = true;
        scaleFactor = 1;
        requireRescaling = false;
    }
    
    //get/set image
    public Image getImage(){
        if(bgImage == null){return null;}
        return bgImage;
    }
    public void setImage(Image img){
        bgImage = img;
    }
    
    public int getScaleFactor(){
        return scaleFactor;
    }
    public void setScaleFactor(int newScale){
        scaleFactor = newScale;
        requireRescaling = true;
    }
    
    public void setRequireRescaling(boolean newVal){
        requireRescaling = newVal;
    }
    public boolean getRequireRescaling(){
        return requireRescaling;
    }
}

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
    private transient Image originalImage;
    public boolean enabled;
    public boolean gridEnabled;
    private int scaleFactor;
    private boolean requireRescaling;
    private int resizeQuality;
    
    // constructor
    public BackgroundImage(){
        enabled = false;
        gridEnabled = true;
        scaleFactor = 1;
        requireRescaling = false;
        resizeQuality = Image.SCALE_SMOOTH;
    }
    
    //get/set image
    public Image getImage(){
        if(bgImage == null){return null;}
        return bgImage;
    }
    public void setImage(Image img){
        bgImage = img;
    }
    public Image getOriginalImage(){
        if(originalImage == null){return null;}
        return originalImage;
    }
    public void setOriginalImage(Image img){
        originalImage = img;
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
    
    public void setResizeQuality(int newVal){
        if (newVal == 1 || newVal == 2
            || newVal == 4 || newVal == 8
            || newVal == 16){
            resizeQuality = newVal;
        }
    }
    public int getResizeQuality(){
        return resizeQuality;
    }
    
}

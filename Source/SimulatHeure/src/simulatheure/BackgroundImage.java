/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

import java.awt.Image;


/**
 *
 * @author Sam
 */
public class BackgroundImage{
    
    private Image bgImage;
    public boolean enabled;
    public boolean gridEnabled;
    
    // constructor
    public BackgroundImage(){
        enabled = false;
        gridEnabled = true;
    }
    
    //get/set image
    public Image getImage(){
        if(bgImage == null){return null;}
        return bgImage;
    }
    public void setImage(Image img){
        bgImage = img;
    }
    
}

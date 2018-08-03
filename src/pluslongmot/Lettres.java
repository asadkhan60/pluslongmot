/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pluslongmot;

import javax.swing.JButton;

/**
 *
 * @author khan
 */
public class Lettres extends JButton{
    private int position;
    
    Lettres(){
        this.setText(("-"));
        
    }
    
    int getPosition(){
        return this.position;
    }
    
    void setPosition(int pos){
        this.position = pos;
    }
}

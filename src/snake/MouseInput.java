/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{

    @Override
    //Set mouse event listeners
    public void mouseClicked(MouseEvent e) {
        if(STATE.State==STATE.MENU){
            //Set game speed
            if(Menu.slug.contains(e.getX(),e.getY())||Menu.worm.contains(e.getX(),e.getY())||Menu.python.contains(e.getX(),e.getY())){
                if(Menu.slug.contains(e.getX(),e.getY()))
                    Game.setDifficulty("SLUG");
                else if(Menu.worm.contains(e.getX(),e.getY()))
                    Game.setDifficulty("WORM");
                else if(Menu.python.contains(e.getX(),e.getY()))
                    Game.setDifficulty("PYTHON");
                STATE.State=STATE.GAME;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javax.swing.JFrame;


public class Snake {
    public static void main(String[] args) {
        Game panel = new Game();
        JFrame application = new JFrame();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        application.add(panel);
        application.setSize(260,320);
        application.setVisible(true);
    }
    
}

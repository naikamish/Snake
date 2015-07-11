/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Menu {
    public static Rectangle slug = new Rectangle(60,125,125,30);
    public static Rectangle worm = new Rectangle(60,175,125, 30);
    public static Rectangle python = new Rectangle(60,225,125,30);
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font titleFont = new Font("TimesRoman", Font.BOLD, 50);
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        g.drawString("SNAKE", 35, 50);
        
        Font textFont = new Font("TimesRoman", Font.BOLD, 20);
        g.setFont(textFont);
        g2d.draw(slug);
        g.drawString("Slug", 102, 148);
        g2d.draw(worm);
        g.drawString("Worm", 95, 198);
        g2d.draw(python);
        g.drawString("Python", 90, 248);
    }
}

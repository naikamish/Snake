/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import java.awt.Rectangle;

public class Block {
    private final int BLOCK_SIZE=10, MAX_WIDTH, MAX_HEIGHT, MIN_WIDTH, MIN_HEIGHT;
    private int xPos, yPos, dy, dx;
    private final Random random;
    private Color color;
    
    public Block(int maxWidth, int maxHeight, int minWidth, int minHeight){
        MAX_WIDTH=maxWidth;
        MAX_HEIGHT=maxHeight;
        MIN_WIDTH=minWidth;
        MIN_HEIGHT=minHeight;
        
        //Create a random position for the block within the game bounds
        random=new Random();
        xPos=random.nextInt((MAX_WIDTH-MIN_WIDTH)/BLOCK_SIZE)*BLOCK_SIZE+MIN_WIDTH;
        yPos=random.nextInt((MAX_HEIGHT-MIN_HEIGHT)/BLOCK_SIZE)*BLOCK_SIZE+MIN_HEIGHT;
        color= Color.RED;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(xPos, yPos, BLOCK_SIZE, BLOCK_SIZE);
    }
    
    public void setBounds(Rectangle block){
        this.xPos=block.x;
        this.yPos=block.y;
    }
    
    public void changeColor(){
        color=Color.WHITE;
    }
    
    public void move(Point point){
        xPos+=point.x;
        yPos+=point.y;
    }
    
    public void render(Graphics g){
        g.setColor(color);
        g.fillRect(xPos, yPos, BLOCK_SIZE, BLOCK_SIZE);
    }
}

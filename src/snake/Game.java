/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Game extends JPanel{
    private static Timer timer;
    private final Menu menu;
    private static int gameSpeed=100, score;
    private static String gameDifficulty;
    private List<Block> BlockChain = new ArrayList<>();
    private Block firstBlock, currentBlock;
    private final int MAX_GAME_WIDTH=220, MAX_GAME_HEIGHT=220, MIN_GAME_WIDTH=20, MIN_GAME_HEIGHT=20;
    private Point direction;
    private boolean gameOver=false, paused=false;
    
    public Game(){
        setBackground(Color.BLACK);
        menu = new Menu();
        firstBlock=new Block(MAX_GAME_WIDTH,MAX_GAME_HEIGHT,MIN_GAME_WIDTH,MIN_GAME_HEIGHT);
        currentBlock = new Block(MAX_GAME_WIDTH,MAX_GAME_HEIGHT,MIN_GAME_WIDTH,MIN_GAME_HEIGHT);
        firstBlock.changeColor();
        BlockChain.add(firstBlock);
        direction=new Point(0,0);
        
        timer=new Timer(100,
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(STATE.State==STATE.GAME)
                    move();
                    repaint();
                }        
            }
        );
        timer.start();
        
        getInputMap().put(KeyStroke.getKeyStroke("W"), "Move Up");
        getInputMap().put(KeyStroke.getKeyStroke("UP"), "Move Up");
        getActionMap().put("Move Up", new KeyAction("W"));
        
        getInputMap().put(KeyStroke.getKeyStroke("S"), "Move Down");
        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Move Down");
        getActionMap().put("Move Down", new KeyAction("S"));
        
        getInputMap().put(KeyStroke.getKeyStroke("A"), "Move Left");
        getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Move Left");
        getActionMap().put("Move Left", new KeyAction("A"));
        
        getInputMap().put(KeyStroke.getKeyStroke("D"), "Move Right");
        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Move Right");
        getActionMap().put("Move Right", new KeyAction("D"));
        
        getInputMap().put(KeyStroke.getKeyStroke("P"), "Pause");
        getActionMap().put("Pause", new KeyAction("P"));
        
        getInputMap().put(KeyStroke.getKeyStroke("M"), "Menu");
        getActionMap().put("Menu", new KeyAction("M"));
        
        this.addMouseListener(new MouseInput());
    }
    
    //Set timer speed based on difficulty selected in main menu
    public static void setDifficulty(String difficulty){
        gameDifficulty=difficulty;
        switch (difficulty) {
            case "SLUG":
                gameSpeed=100;
                break;
            case "WORM":
                gameSpeed=75;
                break;
            case "PYTHON":
                gameSpeed=50;
                break;
        }
        timer.setDelay(gameSpeed);
    }
    
    //Create a new block, place it in the front of the array list, and move the block forward
    private void move(){
        Block tempBlock = new Block(MAX_GAME_WIDTH,MAX_GAME_HEIGHT,MIN_GAME_WIDTH,MIN_GAME_HEIGHT);
        tempBlock.setBounds(BlockChain.get(0).getBounds());
        tempBlock.changeColor();
        BlockChain.add(0,tempBlock);
        BlockChain.get(0).move(direction);
        checkIntersection();       
        checkLoss();
    }
    
    //Check if the head block of the snake intersected with an apple. If it did, create a new apple
    //If not, remove a block from the end of the chain. This way, the snake moves forward one block,
    //and then its size decreases in the back thus creating the movement effect
    private void checkIntersection(){
        if(BlockChain.get(0).getBounds().intersects(currentBlock.getBounds())){
            currentBlock=new Block(MAX_GAME_WIDTH,MAX_GAME_HEIGHT,MIN_GAME_WIDTH,MIN_GAME_HEIGHT);
            score+=(150-gameSpeed);
        }
        else
            BlockChain.remove(BlockChain.size()-1);
    }
    
    //Check to see if the snake intersected with itself or the bounds of the game board
    public void checkLoss(){
        if(BlockChain.get(0).getBounds().x>=MAX_GAME_WIDTH || BlockChain.get(0).getBounds().x<MIN_GAME_WIDTH || BlockChain.get(0).getBounds().y>=MAX_GAME_HEIGHT || BlockChain.get(0).getBounds().y<MIN_GAME_HEIGHT)
            gameOver=true;
        for(int i=1; i<BlockChain.size();i++){
            if(BlockChain.get(0).getBounds().intersects(BlockChain.get(i).getBounds())){
                gameOver=true;
            }
        }
    }
    
    //Restart the game by resetting all the variables
    public void restart(){
        BlockChain=new ArrayList<>();
        firstBlock=new Block(MAX_GAME_WIDTH,MAX_GAME_HEIGHT,MIN_GAME_WIDTH,MIN_GAME_HEIGHT);
        currentBlock = new Block(MAX_GAME_WIDTH,MAX_GAME_HEIGHT,MIN_GAME_WIDTH,MIN_GAME_HEIGHT);
        firstBlock.changeColor();
        BlockChain.add(firstBlock);
        direction=new Point(0,0);
        score=0;
        gameOver=false;
    }
    
    //Render the game board
    public void render(Graphics g){
        g.drawRect(MIN_GAME_WIDTH, MIN_GAME_HEIGHT, MAX_GAME_WIDTH-MIN_GAME_WIDTH, MAX_GAME_HEIGHT-MIN_GAME_HEIGHT);
        Font textFont = new Font("Arial",Font.BOLD, 12);
        g.setFont(textFont);
        g.drawString("SCORE: "+score, MIN_GAME_WIDTH, MAX_GAME_HEIGHT+20);
        g.drawString("DIFFICULTY: "+gameDifficulty, MIN_GAME_WIDTH+90, MAX_GAME_HEIGHT+20);
        if(gameOver){
            Font gameOverFont = new Font("Arial",Font.BOLD, 30);
            g.setFont(gameOverFont);
            g.drawString("GAME OVER", 30, 130);
        }
    }
    
    //Render all the components of the game
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        
        if(STATE.State==STATE.GAME){
            this.render(g);

            if(!gameOver){
                for(Block block:BlockChain){
                    block.render(g);
                }
                currentBlock.render(g);
            }
        }
        
        else if(STATE.State==STATE.MENU)
            menu.render(g);
    }
    
    //Implement key bindings
    private class KeyAction extends AbstractAction{
        private String keyPressed;
        public KeyAction(String key){
            keyPressed=key;
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(STATE.State==STATE.GAME){
                if(timer.isRunning()){
                    if(keyPressed=="W"){
                        if(!(direction.x==0&&direction.y==10))
                            direction=new Point(0,-10);
                    }

                    else if(keyPressed=="S"){
                        if(!(direction.x==0&&direction.y==-10))
                            direction=new Point(0,10);
                    }

                    else if(keyPressed=="A"){
                        if(!(direction.x==10&&direction.y==0))
                            direction=new Point(-10,0);
                    }

                    else if(keyPressed=="D"){
                        if(!(direction.x==-10&&direction.y==0))
                            direction=new Point(10,0);
                    }
                }
                
                if(keyPressed=="P"){
                    if(timer.isRunning())
                        timer.stop();
                    else
                        timer.restart();
                }
                
                else if(keyPressed=="M"){
                    if(!timer.isRunning())
                        timer.restart();
                    restart();
                    STATE.State=STATE.MENU;
                }
            }
        }
    }
}

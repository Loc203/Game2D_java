package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // pixel size object (default)
    final int scale = 3;

    public int tileSize = originalTileSize * scale;
    public int maxScreeenCol = 16;
    public int maxScreeenRow = 12; // ti le 3:4
    public int screenWidth = tileSize * maxScreeenCol; //768 pixels
    public int screenHeight = tileSize * maxScreeenRow; //576 pixels

    // world setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //--------------
    int FPS = 60;
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound music = new Sound();
    //sound effect
    Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10];


    public GamePanel() {
        // cai dat cho panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //x2 bộ đệm, tang hieu suat hien thi
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    public void setupGame(){
        assetSetter.setObject();
        playMusic(0);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //loop
    @Override
    public void run() {
        // 1s = 1bil nanos
        double drawInterval = (double) 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            // update: cap nhat thong tin, vi tri cua nhan vat
            update();
            //ve lai man hinh voi thong tin vua cap nhat
            repaint();
            //thread loop lien tuc hon 1trieu lan tren s, dung sleep de gioi han lan lap FPS
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime <= 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileManager.draw(g2);
        for (int i = 0; i < obj.length; i++){
            if (obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        player.draw(g2);

        //UI
        ui.draw(g2);
        g2.dispose();
    }
// set sound
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    //sound effect
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}

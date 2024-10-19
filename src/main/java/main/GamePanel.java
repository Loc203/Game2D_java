package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // pixel size object (default)
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreeenCol = 16;
    final int maxScreeenRow = 12; // ti le 3:4
    final int screenWidth = tileSize * maxScreeenCol; //768 pixels
    final int screenHeight = tileSize * maxScreeenRow; //576 pixels

    int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();

    Thread gameThread;

    //set default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel() {
        // cai dat cho panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //x2 bộ đệm, tang hieu suat hien thi
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //loop
    @Override
    public void run() {
        // 1s = 1bil nanos
        double drawInterval = (double) 1000000000 /FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            // update: cap nhat thong tin, vi tri cua nhan vat
            update();
            //ve lai man hinh voi thong tin vua cap nhat
            repaint();
            //thread loop lien tuc hon 1trieu lan tren s, dung sleep de gioi han lan lap FPS
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime <= 0){
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
        if (keyHandler.upPressed) {
            playerY -= playerSpeed;
        } else if (keyHandler.downPressed) {
            playerY += playerSpeed;
        } else if (keyHandler.rightPressed) {
            playerX += playerSpeed;
        } else if (keyHandler.leftPressed) {
            playerX -= playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}

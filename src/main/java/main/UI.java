package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40, arial_70B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messagerCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_70B = new Font("Arial", Font.BOLD, 70);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if (gameFinished) {
            String text;
            int textLength;
            int x, y;

            g2.setFont(arial_40);
            g2.setColor(Color.white);
            text = "You found the treasure...";
            //lay chieu dai van ban
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "You have completed the level in: " + dFormat.format(playTime);
            //lay chieu dai van ban
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);



            g2.setFont(arial_70B);
            g2.setColor(Color.yellow);
            text = "Congratulations.";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, 10, 20, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 55, 60);
            //time
            playTime += (double) 1/60;
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize*11, 65);
            //mesage
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30f));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messagerCounter++;
                if (messagerCounter > 120) {
                    messagerCounter = 0;
                    messageOn = false;
                }

            }

        }
    }
}

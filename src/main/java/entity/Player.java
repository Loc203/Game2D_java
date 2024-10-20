package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "up";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/walkingSprites/boy_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed) {
            direction = "up";
            y -= speed;
        } else if (keyHandler.downPressed) {
            direction = "down";
            y += speed;
        } else if (keyHandler.rightPressed) {
            direction = "right";
            x += speed;
        } else if (keyHandler.leftPressed) {
            direction = "left";
            x -= speed;
        }

        spritesCounter++;
        if (spritesCounter > 10){
            if (spritesNum == 1){
                spritesNum = 2;
            }
            else if (spritesNum == 2){
                spritesNum = 1;
            }
            spritesCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spritesNum == 1) {
                    image = up1;
                }
                if (spritesNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spritesNum == 1) {
                    image = down1;
                }
                if (spritesNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spritesNum == 1) {
                    image = left1;
                }
                if (spritesNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spritesNum == 1) {
                    image = right1;
                }
                if (spritesNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}

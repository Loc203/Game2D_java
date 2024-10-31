package entity;

import main.CollisionChecker;
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

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 28);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
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
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            }

            // check tile collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);
            //if false => cant move
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
        }
        spritesCounter++;
        if (spritesCounter > 10) {
            if (spritesNum == 1) {
                spritesNum = 2;
            } else if (spritesNum == 2) {
                spritesNum = 1;
            }
            spritesCounter = 0;
        }

    }

    public void pickUpObject(int index) {
        if (index != 999) {
            String objectName = gp.obj[index].name;
            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0){
                        gp.playSE(3);
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed+=2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("now, you look like Usain Bolt");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}

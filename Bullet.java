import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

public class Bullet {
    private int positionX;
    private int positionY;
    private Direction direction;
    private boolean active;
    private int movingSpeed = 7;
    BufferedImage bulletImage;
    GamePanel gamePanel;
    Rectangle rectangle;
    public boolean shotByPlayer;
    int mode;

    public Bullet(GamePanel gamePanel, int mode) {
        this.gamePanel = gamePanel;
        this.mode = mode;
        setImage();


        setRectangleValues();
    }

    private void setRectangleValues() {
        rectangle = new Rectangle();
        rectangle.x = 0;
        rectangle.y = 0;
        rectangle.width = gamePanel.getTileSize();
        rectangle.height = gamePanel.getTileSize();
    }

    private void setImage() {
        try {
            bulletImage = ImageIO.read(new FileInputStream("resources/bullet.png"));
        } catch (IOException e) {

        }
    }

    public void setBullet(int locationX, int locationY, Direction direction, boolean active, boolean shotByPlayer) {
        this.positionX = locationX;
        this.positionY = locationY;
        this.direction = direction;
        this.active = active;
        this.shotByPlayer = shotByPlayer;
    }

    public void travelMode() {
        if (mode == 4) {
        } else {

        }
    }

    public void update(){

        if(shotByPlayer) {
            int enemyIndex = gamePanel.getCollision().checkBulletWithEnemyCollision(this, gamePanel.enemies);
            if (enemyIndex != -1) {
                gamePanel.setScore(gamePanel.getScore() + 1);
                gamePanel.enemies.remove(enemyIndex);
                gamePanel.addNewEnemy();
                active = false;
                return;
            }
        }else{
            if (gamePanel.getCollision().checkBulletWithPlayerCollision(this, gamePanel.getPlayer())) {
                gamePanel.setGameOver(true);
                return;
            }
        }

        switch (direction){
            case UP: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionY -= movingSpeed;
                else active = false;
                break;
            }
            case DOWN: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionY += movingSpeed;
                else active = false;
                break;
            }
            case LEFT: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionX -= movingSpeed;
                else active = false;
                break;
            }
            case RIGHT: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionX += movingSpeed;
                else active = false;
                break;
            }
        }
    }

    public void paint(Graphics g){
        g.drawImage(bulletImage, positionX, positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isActive() {
        return active;
    }


    public int getMovingSpeed() {
        return movingSpeed;
    }

}

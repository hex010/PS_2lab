import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Bullet {
    private int positionX;
    private int positionY;
    private Direction direction;
    private boolean active;
    private int movingSpeed;
    BufferedImage bulletImage;
    GamePanel gamePanel;
    Rectangle rectangle;
    private boolean shotByPlayer;
    int mode;

    public Bullet(GamePanel gamePanel, int mode) {
        this.gamePanel = gamePanel;
        this.mode = mode;
        this.movingSpeed = 7;
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
            String resource = "resources/bullet.png";
            bulletImage = ImageIO.read(new FileInputStream(resource));
        } catch (IOException e) {
            System.out.println("Įvyko klaida.");
        }
    }

    public void setBullet(int locationX, int locationY, Direction direction, boolean active, boolean shotByPlayer) {
        this.positionX = locationX;
        this.positionY = locationY;
        this.direction = direction;
        this.active = active;
        this.shotByPlayer = shotByPlayer;
    }

    public void update(){

        if(shotByPlayer) {
            if (shotByPlayerHitTheEnemy()) return;
        }else{
            if (shotByEnemyHitThePlayer()) return;
        }
        
        switch (direction) {
            case UP: {
                moveUp();
                break;
            }
            case DOWN: {
                moveDown();
                break;
            }
            case LEFT: {
                moveLeft();
                break;
            }
            case RIGHT: {
                moveRight();
                break;
            }
        }
    }

    private void moveUp() {
        if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
            positionY -= movingSpeed;
        else active = false;
    }

    private void moveDown() {
        if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
            positionX -= movingSpeed;
        else active = false;
    }

    private void moveLeft() {
        if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
            positionY -= movingSpeed;
        else active = false;
    }

    private void moveRight() {
        if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
            positionX += movingSpeed;
        else active = false;
    }

    private boolean shotByPlayerHitTheEnemy() {
            int enemyIndex = gamePanel.getCollision().checkBulletWithEnemyCollision(this, gamePanel.enemies);
            if (enemyIndex != -1) {
                gamePanel.setScore(gamePanel.getScore() + 1);
                gamePanel.enemies.remove(enemyIndex);
                gamePanel.addNewEnemy();
                active = false;
                return true;
            }
            return false
        }
                
    private boolean shotByEnemyHitThePlayer() {
            if (gamePanel.getCollision().checkBulletWithPlayerCollision(this, gamePanel.getPlayer())) {
                gamePanel.setGameOver(true);
                return true;
            }
            return false;
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

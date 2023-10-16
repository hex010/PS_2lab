import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Map {
    GamePanel gamePanel;
    Tile[][] tiles;

    public Map(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[gamePanel.getMaxScreenRow()][gamePanel.getMaxScreenColumn()];
        readMap();
    }

    private void readMap() {
        try {
            int x = 0;
            int y = 0;
            
            String filePath = "resources/map.txt";
            Scanner input = new Scanner(new File(filePath));

            for(int i = 0; i < gamePanel.getMaxScreenRow(); i++){
                for(int j = 0; j < gamePanel.getMaxScreenColumn(); j++){
                    if(input.hasNextInt()){
                        int tileType = input.nextInt();
                        addTile(x, y, i, j, tileType);
                        x += gamePanel.getTileSize();
                    }
                }
                y += gamePanel.getTileSize();
                x = 0;
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void paint(Graphics g) {
        for (int i = 0; i < gamePanel.getMaxScreenRow(); i++) {
            for (int j = 0; j < gamePanel.getMaxScreenColumn(); j++) {
                g.drawImage(tiles[i][j].getBufferedImage(), tiles[i][j].getX(), tiles[i][j].getY(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }
        }
    }

    private void addTile(int x, int y, int i, int j, int tileType) throws IOException {
        switch (tileType){
            case 0: {
                tiles[i][j] = new Tile(x, y, tileType, null, false); break;
            }
            case 1: {
                String filePath = "resources/wall.bmp";
                BufferedImage tileImage = ImageIO.read(new FileInputStream(filePath));
                tiles[i][j] = new Tile(x, y, tileType, tileImage, true); break;
            }
            default: {
                tiles[i][j] = new Tile(x, y, 0, null, false);
            }
        }
    }

    public void clearMap() {
        for (int i = 0; i < gamePanel.getMaxScreenRow(); i++) {
            for (int j = 0; j < gamePanel.getMaxScreenColumn(); j++) {
                try {
                    tiles[i][j].setType(0);
                } catch (Exception e) {
                    System.out.println("Nepavyko nustatyti type 0 tile " + i + ", " + j);
                }
            }
        }
    }


    public Tile getTileAtCoordinates(int x, int y) {
        int tileSize = 32;
        
        int row = y / tileSize;
        int column = x / tileSize;

        if (row >= 0 && row < gamePanel.getMaxScreenRow() && column >= 0 && column < gamePanel.getMaxScreenColumn()) {
            return tiles[row][column];
        }
        return null;
    }

}

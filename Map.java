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

    public Map(GamePanel g) {
        this.gamePanel = g;
        tiles = new Tile[g.getMaxScreenRow()][g.getMaxScreenColumn()];
        ReadMap();
    }

    private void ReadMap() {
        try {
            int x = 0;
            int y = 0;
            Scanner input = new Scanner(new File("resources/map.txt"));

            for(int i = 0; i < gamePanel.getMaxScreenRow(); i++){
                for(int j = 0; j < gamePanel.getMaxScreenColumn(); j++){
                    if(input.hasNextInt()){
                        int tileType = input.nextInt();

                        switch (tileType){
                            case 0: {
                                tiles[i][j] = new Tile(x, y, tileType, null, false); break;
                            }
                            case 1: {
                                BufferedImage tileImage = ImageIO.read(new FileInputStream("resources/wall.bmp"));
                                tiles[i][j] = new Tile(x, y, tileType, tileImage, true); break;
                            }
                            default: {
                                tiles[i][j] = new Tile(x, y, 0, null, false);
                            }
                        }

                        x += gamePanel.getTileSize();
                    }
                }
                y += gamePanel.getTileSize();
                x = 0;
            }
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




    public void updateMap(int x, int y) {
        // todo
    }

    public void clearMap() {
        for (int i = 0; i < gamePanel.getMaxScreenRow(); i++) {
            for (int j = 0; j < gamePanel.getMaxScreenColumn(); j++) {
                try {
                    tiles[i][j].setType(0);
                } catch (Exception e) {
                }
            }
        }
    }




    public Tile getTileAtCoordinates(int x, int y) {
        int row = y / 32;
        int column = x / 32;

        if (row >= 0 && row < gamePanel.getMaxScreenRow() && column >= 0 && column < gamePanel.getMaxScreenColumn()) {
            return tiles[row][column];
        }
        return null; // nera tokio tile
    }

}

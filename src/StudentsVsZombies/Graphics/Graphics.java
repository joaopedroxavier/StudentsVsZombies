package StudentsVsZombies.Graphics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Graphics {
    abstract public void update(GameObject obj, Game game);
    protected double update_speed = 0.1; // Change to time, getting time as parameter in update
    protected int update_counter = 0;
    public Graphics(Graphics gr){}
    public Graphics(){}

    protected void displayImage(Game game, BufferedImage img, int x, int y) { //prototype
        Graphics2D g = (Graphics2D) game.bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, game.get_size().x, game.get_size().y);
        g.drawImage(img, x, y, game.panel);
        g.dispose();
        game.bufferStrategy.show();
    }


}

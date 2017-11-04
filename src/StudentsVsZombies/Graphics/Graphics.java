package StudentsVsZombies.Graphics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public abstract class Graphics {
    protected double update_speed = 0.05; // Change to time, getting time as parameter in update
    protected int update_counter = 0;
    protected ArrayList<BufferedImage> sprite_seq;
    protected int sprite_i = 0;

    abstract public Graphics copy();

    public void update(GameObject obj, Game game){
        update_counter++;
        if(update_counter == (int)(1/update_speed)){
            sprite_i = (sprite_i + 1)%sprite_seq.size();
            update_counter = 0;
        }
        displayImage(game,sprite_seq.get(sprite_i), obj.x_, obj.y_);
    }

    protected void displayImage(Game game, BufferedImage img, int x, int y) { //prototype
        Graphics2D g = (Graphics2D) game.bufferStrategy.getDrawGraphics();
        g.drawImage(img, x, y, game.panel);
        g.dispose();
    }


}

package StudentsVsZombies.Graphics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

import java.awt.image.BufferedImage;

public class StaticGraphics extends Graphics {
    private BufferedImage sprite;
    public StaticGraphics copy(){ return new StaticGraphics(sprite); }
    public void update(GameObject obj, Game game){
    	displayImage(game,sprite, obj.x_, obj.y_);
    }
    public StaticGraphics(BufferedImage img) { this.sprite = img; }
}
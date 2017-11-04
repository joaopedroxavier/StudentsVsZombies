package StudentsVsZombies.Graphics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StaticGraphics extends Graphics {
    public StaticGraphics copy(){ return new StaticGraphics(sprite_seq); }
    public StaticGraphics(ArrayList<BufferedImage> sprite_seq) { this.sprite_seq = sprite_seq; }
    public StaticGraphics(BufferedImage img) { this.sprite_seq = new ArrayList<>(); sprite_seq.add(img); }
}
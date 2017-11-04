package StudentsVsZombies.Graphics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class StateGraphics extends Graphics {

    public StateGraphics(ArrayList<BufferedImage> sprite_seq) { this.sprite_seq = sprite_seq; }
    
    public StateGraphics(BufferedImage spritesheet, int scale) {
    	for (int i = 0 ; i < 4 ; ++i) {
    		sprite_seq.add((spritesheet.getSubimage(i*16*scale, 0, 16*scale, 16*scale)));
    	}
    }

    public void generateEnergy(){ }

    public StateGraphics copy(){
        return new StateGraphics(this.sprite_seq);
    }


    public void reset(ArrayList<BufferedImage> new_seq){
        sprite_seq = new_seq;
        sprite_i = 0;
    }
}

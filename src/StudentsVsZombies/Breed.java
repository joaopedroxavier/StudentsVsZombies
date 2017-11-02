package StudentsVsZombies;
    //Cell get_inCell(Point cell) { return grid[cell.x][cell.y]; }
import StudentsVsZombies.Graphics.StateGraphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;
import StudentsVsZombies.State.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Breed {
    private int totalHp;
    private int attack;
    private int width = 10, height = 10;
    private Physics py;
    private Input in;
    private State base_state;
    ArrayList <ArrayList<BufferedImage>> sprites;
    /** @param spritesheet_path Path to the image which has the sequence of pictures of an animation. **/
    Breed(int total_hp, int attack, String spritesheet_path, Physics py, Input in, State base_state){
        this.py = py; this.in = in; this.base_state = base_state;
        this.totalHp = total_hp;
        this.attack = attack;
		try {
			sprites = new ArrayList<>();
	        ArrayList<BufferedImage> spr = new ArrayList<>();
	        File file = new File(spritesheet_path);
			BufferedImage img = ImageIO.read(file);
			for (int i = 0 ; i < 4 ; ++i) {
				spr.add((img.getSubimage(i*16, 0, 16, 16)));
			}
	        sprites.add(spr);
		}
		catch (IOException e) {
				e.printStackTrace();
		}
    }

    Spawnable spawn(Point cell, Grid grid){
       Spawnable monster = new Spawnable(grid, grid.get_loc(cell), new StateGraphics(sprites.get(0)), py.copy(), in.copy(), width, height, base_state.copy(), totalHp, this);

       return monster;
    }
}

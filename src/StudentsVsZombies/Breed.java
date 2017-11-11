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
    private int width = 50, height = 50;
    private int cost = 100000;
    private Physics py;
    private Input in;
    private State base_state;
    private ArrayList <ArrayList<BufferedImage>> sprites;


    Breed(int total_hp, int attack, ArrayList<String> spritesheet_paths, int scale, Physics py, Input in, State base_state){
        this.py = py; this.in = in; this.base_state = base_state;
        this.totalHp = total_hp;
        this.attack = attack;
		try {
			sprites = new ArrayList<>();
	        for ( String spritesheet_path : spritesheet_paths) {
		        ArrayList<BufferedImage> spr = new ArrayList<>();
	        	File file = new File(spritesheet_path);
	        	BufferedImage img = ImageIO.read(file);
                for (int i = 0 ; i < img.getWidth()/(16*scale); ++i) {
	        		spr.add((img.getSubimage(i*16*scale, 0, 16*scale, 16*scale)));
	        	}
	        	sprites.add(spr);
	        }
		}
		catch (IOException e) {
				e.printStackTrace();
		}
    }

    public Spawnable spawn(Point cell, Grid grid){
       Spawnable monster = new Spawnable(grid, grid.get_loc(cell), new StateGraphics(sprites.get(0)), py.copy(), in.copy(), width, height, base_state.copy(), totalHp, this);

       return monster;
    }

    public int getAttack() { return attack; }

    public int getCost() { return cost; }

    public ArrayList<ArrayList<BufferedImage>> getSprites(){ return sprites; }
}

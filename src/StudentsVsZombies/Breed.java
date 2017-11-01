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
import java.util.ArrayList;

public class Breed {
    private int totalHp;
    private int attack;
    private int width = 10, height = 10;
    private Physics py;
    private Input in;
    private State base_state;
    ArrayList<ArrayList<BufferedImage>> sprites; //A sequence of sprites for each state
                                           //Directories should be like gtx/zombie/eating/103.png

    Breed(int total_hp, int attack, ArrayList<String> sprite_folders, Physics py, Input in, State base_state){
        this.py = py; this.in = in; this.base_state = base_state;
        this.totalHp = total_hp;
        this.attack = attack;
        sprites = new ArrayList<>();
        for(String s : sprite_folders) {
            ArrayList<BufferedImage> spr = new ArrayList<>();
            for(int i = 0; ;i++){
                File file = new File("gfx/" + s + i + ".png");
                if(!file.exists()) break;
                try{spr.add(ImageIO.read(file));}catch(Exception e){};
            }
            sprites.add(spr);
        }
    }

    Spawnable spawn(Point cell, Grid grid){
       Spawnable monster = new Spawnable(grid, grid.get_loc(cell), new StateGraphics(sprites.get(0)), py.copy(), in.copy(), width, height, base_state.copy(), totalHp);

       return monster;
    }
}

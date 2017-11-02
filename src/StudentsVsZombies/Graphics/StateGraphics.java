package StudentsVsZombies.Graphics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StateGraphics extends Graphics {
    private ArrayList<BufferedImage> sprite_seq;
    private int sprite_i = 0;

    public void update(GameObject obj, Game game){
        update_counter++;
        if(update_counter == (int)(1/update_speed)){
            sprite_i = (sprite_i + 1)%sprite_seq.size();
            update_counter = 0;
        }
        displayImage(game,sprite_seq.get(sprite_i), obj.x_, obj.y_);
    }

    public StateGraphics(ArrayList<BufferedImage> sprite_seq) { this.sprite_seq = sprite_seq; }

    public void generateEnergy(){ }

    public StateGraphics copy(){
        return new StateGraphics(this.sprite_seq);
    }


    void reset(ArrayList<BufferedImage> new_seq){
        sprite_seq = new_seq;
        sprite_i = 0;
    }
}

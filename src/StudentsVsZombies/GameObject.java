package StudentsVsZombies;

import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;

import java.awt.*;

public class GameObject {
    public int x_, y_;
    private int width, height;
    protected Grid grid_;
    public Graphics gr;
    public Physics py;
    public Input in;

    public GameObject(Grid grid, Point pos, Graphics gr, Physics py, Input in, int width, int height){
        grid_ = grid;
        x_ = pos.x; y_ = pos.y;
        this.width = width; this.height = height;
        this.gr = gr; this.py = py; this.in = in;
        grid_.add(this, grid_.get_cell(pos));
    }
    void update(){};
}

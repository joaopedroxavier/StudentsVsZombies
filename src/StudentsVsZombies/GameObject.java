package StudentsVsZombies;

import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;

import java.awt.*;

public class GameObject {
    public int x_, y_;
    private int width, height;
    public Graphics gr;
    public Physics py;
    public Input in;

    public GameObject(Point pos, Graphics gr, Physics py, Input in, int width, int height){
        x_ = pos.x; y_ = pos.y;
        this.width = width; this.height = height;
        this.gr = gr; this.py = py; this.in = in;
    }

    public GameObject(GameObject obj, Point pos){
        gr = obj.gr; py = obj.py; in = obj.in;
        x_ = obj.x_; y_ = obj.y_;
        width = obj.width; height = obj.height;
    }

    public void update(){}


}

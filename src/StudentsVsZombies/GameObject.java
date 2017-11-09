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

    public GameObject(Point pos, Graphics gr, Physics py, Input in, int width, int height) {
        x_ = pos.x;
        y_ = pos.y;
        this.width = width;
        this.height = height;
        this.gr = gr;
        this.py = py;
        this.in = in;
    }

    public GameObject(GameObject obj, Point pnt) {
        gr = obj.gr.copy();
        py = obj.py.copy();
        in = obj.in.copy();
        x_ = pnt.x;
        y_ = pnt.y;
        width = obj.width;
        height = obj.height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

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

    public GameObject(Point pos, Graphics gr, Physics py, Input in, int width, int height){
        x_ = pos.x; y_ = pos.y;
        this.width = width; this.height = height;
        this.gr = gr; this.py = py; this.in = in;
    }

    public GameObject(GameObject obj){
        grid_ = obj.grid_;
        gr = obj.gr; py = obj.py; in = obj.in;
        x_ = obj.x_; y_ = obj.y_;
        width = obj.width; height = obj.height;
        grid_.add(this, grid_.get_cell(new Point(x_, y_)));
    }

    public void update(){}

    public Point getCell () { return grid_.get_cell(new Point(x_, y_)); }

    public Cell getListOfObjects() { return grid_.getListOfObjects(getCell()); }

}

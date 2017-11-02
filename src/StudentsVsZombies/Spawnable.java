package StudentsVsZombies;

import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;
import StudentsVsZombies.State.Standing;
import StudentsVsZombies.State.State;

import java.awt.*;

public class Spawnable extends GameObject {
    public int hp;
    public State state;
    private Breed breed;
    protected Grid grid_;

    public Spawnable(Grid grid, Point pos, Graphics gr, Physics py, Input in, int width, int height, State state, int hp, Breed breed)
    {
        super(pos, gr, py, in , width, height);
        this.hp = hp; this.state = state; this.breed = breed;
        grid_ = grid;
        grid_.add(this, grid_.get_cell(pos));
    }

    public void update(){}

    public Breed getBreed() { return breed; }

    public Point getCell () { return grid_.get_cell(new Point(x_, y_)); }

    public Point getCell (Point p) { return grid_.get_cell(p); }

    public Point getLimits() { return grid_.get_limit(); }

    public Cell getListOfObjects() { return grid_.getListOfObjects(getCell()); }

    public Cell getListOfObjects(Point p) { return grid_.getListOfObjects(p); }

}

package StudentsVsZombies;

import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;
import StudentsVsZombies.State.State;

import java.awt.*;

public class Spawnable extends GameObject {
    public int hp;
    public State state;
    private Breed breed;

    Spawnable(Grid grid, Point pos, Graphics gr, Physics py, Input in, int width, int height, State state, int hp, Breed breed)
    {
        super(grid, pos, gr, py, in , width, height);
        this.hp = hp; this.state = state; this.breed = breed;
    }

    public Breed getBreed() { return breed; }

}

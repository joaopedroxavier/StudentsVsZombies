package StudentsVsZombies;

import StudentsVsZombies.State.State;

public class Spawnable extends GameObject {
    private int hp, attack;
    State state;

    Spawnable(Grid gr) { super(gr); }
}

package StudentsVsZombies.Input;

import StudentsVsZombies.GameObject;

public abstract class Input {
    abstract public void update(GameObject obj, Boolean clicked);
    abstract public Input copy();
}

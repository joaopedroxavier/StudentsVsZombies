package StudentsVsZombies.Input;

import StudentsVsZombies.GameObject;

public abstract class Input {
    abstract void update(GameObject obj, Boolean clicked);
    public Input copy(){return null;}
}

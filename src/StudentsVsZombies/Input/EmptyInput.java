package StudentsVsZombies.Input;

import StudentsVsZombies.GameObject;

public class EmptyInput extends Input{
    public void update(GameObject obj, Boolean clicked){}
    public Input copy() { return new EmptyInput(); }
}

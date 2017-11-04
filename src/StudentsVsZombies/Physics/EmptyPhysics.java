package StudentsVsZombies.Physics;

import StudentsVsZombies.GameObject;

public class EmptyPhysics extends Physics{
    public EmptyPhysics copy() { return new EmptyPhysics(); }
    public void update(GameObject obj){/* Do nothing */}
}

package StudentsVsZombies.Physics;

import StudentsVsZombies.GameObject;

public class WalkerPhysics extends Physics {
    private double speed = 0.8;
    private int update_counter = 0;
    public WalkerPhysics copy() {return new WalkerPhysics();}
    public void update(GameObject obj){ update_counter++; if(update_counter > 1/speed) {update_counter = 0; obj.x_ -= 1;} }
}

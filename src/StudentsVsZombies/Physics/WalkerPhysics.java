package StudentsVsZombies.Physics;

import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;
import StudentsVsZombies.State.Walking;

public class WalkerPhysics extends Physics {
    private double speed = 0.8;
    private int update_counter = 0;
    public WalkerPhysics copy() {return new WalkerPhysics();}
    public void update(GameObject o){
        Spawnable obj = (Spawnable) o;
        if(obj.state instanceof Walking) {
            update_counter++;
            if (update_counter > (int) (1 / speed)){
                update_counter = 0;
                obj.x_ -= 1;
            }
        }
    }
}

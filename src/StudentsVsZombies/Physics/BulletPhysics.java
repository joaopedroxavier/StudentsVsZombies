package StudentsVsZombies.Physics;

import StudentsVsZombies.GameObject;

public class BulletPhysics extends Physics {
    private double speed = 3;
    private int update_counter = 0;

    public Physics copy(){ return new BulletPhysics(); }
    public void update(GameObject obj){
        obj.x_ += speed;
    }
}

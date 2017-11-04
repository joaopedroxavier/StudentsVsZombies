package StudentsVsZombies.State;

import StudentsVsZombies.Game;
import StudentsVsZombies.Spawnable;

public abstract class State {
    abstract public void change(Spawnable monster);
    public void die(Spawnable monster, Game game){
        //space for implement fadeaway effect or animation, maybe with a thread, add a counter to remove just after some frames
        monster.state = new Dying();
        game.removeObject(monster);
    }
    abstract public State copy();
}

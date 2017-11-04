package StudentsVsZombies.State;

import StudentsVsZombies.Graphics.StateGraphics;
import StudentsVsZombies.Spawnable;

public class Eating extends State {
    public void change(Spawnable monster) {
        monster.state = new Walking();
        ((StateGraphics) monster.gr).reset(monster.getBreed().getSprites().get(0));
    }
    public State copy(){return new Eating(); }
}

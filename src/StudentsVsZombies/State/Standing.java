package StudentsVsZombies.State;

import StudentsVsZombies.Graphics.StateGraphics;
import StudentsVsZombies.Spawnable;

public class Standing extends State {
    public void change(Spawnable monster) {
        monster.state = new Shooting();
        ((StateGraphics) monster.gr).reset(monster.getBreed().getSprites().get(1));
    }
    public State copy(){return new Standing();}
}

package StudentsVsZombies.State;

import StudentsVsZombies.Graphics.StateGraphics;
import StudentsVsZombies.Spawnable;

public class Walking extends State {
    public void change(Spawnable monster){
        monster.state = new Eating();
        ((StateGraphics)monster.gr).reset(monster.getBreed().getSprites().get(1));
    }
    public State copy(){return new Walking();}
}

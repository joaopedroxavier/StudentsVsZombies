package StudentsVsZombies.State;

import StudentsVsZombies.Game;
import StudentsVsZombies.Spawnable;

public class Dying extends State {
    public void change(Spawnable monster){}
    public State copy(){return new Dying(); }
    public void die(Spawnable monster, Game game) { /* do nothing */ }
}

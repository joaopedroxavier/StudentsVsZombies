package StudentsVsZombies.Input;

import StudentsVsZombies.Cell;
import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;
import StudentsVsZombies.State.Eating;
import StudentsVsZombies.State.State;
import StudentsVsZombies.State.Walking;

public class WalkerIA extends Input {
    private Game game;

    public WalkerIA copy(){ return new WalkerIA(game); }

    public WalkerIA(Game game) {
        this.game = game;
    }

    public void update(GameObject obj, Boolean clicked) {
        if( ((Spawnable)obj).hp <= 0 ) {
            ((Spawnable)obj).state.die();
        }
        Cell cell = obj.getListOfObjects();
        for(Spawnable other : cell) {
            if(other.getBreed() == game.plant_breed && ((Spawnable)obj).state instanceof Walking) {
                    ((Spawnable)obj).state.change();
            }
        }
    }
}
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
        State currentState = ((Spawnable) obj).state;

        if( ((Spawnable)obj).hp <= 0 ) {
            currentState.die();
        }
        Cell cell = obj.getListOfObjects();
        for(Spawnable other : cell) {
            if(other.getBreed() == game.plant_breed && currentState instanceof Walking) {
                    currentState.change();
            }
        }
    }
}
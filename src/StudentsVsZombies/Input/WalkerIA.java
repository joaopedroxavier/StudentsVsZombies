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

    public void update(GameObject o, Boolean clicked) {
        Spawnable obj = (Spawnable) o;
        State currentState = obj.state;

        if(obj.hp <= 0) { currentState.die(); }

        Cell cell = obj.getListOfObjects();
        for(Spawnable other : cell) {
            if(other.getBreed() == game.green_breed && currentState instanceof Walking) {
                    currentState.change();
            }
        }
    }
}
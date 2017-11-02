package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;

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

    }
}
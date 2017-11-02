package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;

public class PlantIA extends Input {
    private Game game;

    public PlantIA copy(){ return new PlantIA(game); }

    public PlantIA(Game game) {
        this.game = game;
    }

    public void update(GameObject obj, Boolean clicked) {
        if( ((Spawnable)obj).hp <= 0 ) {
            ((Spawnable)obj).state.die();
        }
    }

}

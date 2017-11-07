package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

public class EnergyClick extends Input {
    private Game game;

    public EnergyClick(Game game) { this.game = game; }

    public void update(GameObject obj, Boolean clicked){
        if(!clicked) {
            game.gainEnergy();
        }
    }
    public Input copy() { return new EnergyClick(game); }
}

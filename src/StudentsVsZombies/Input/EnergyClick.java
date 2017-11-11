package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

public class EnergyClick extends Input {
    private Game game;
    private int count = 0;

    public EnergyClick(Game game) { this.game = game; }

    public void update(GameObject obj, Boolean clicked){
        if(clicked) {
            System.out.println("Gained energy!");
            game.gainEnergy();
            game.removeObject(obj);
        }
        count++;
        if(count >= 300) { game.removeObject(obj); }
    }
    public Input copy() { return new EnergyClick(game); }
}

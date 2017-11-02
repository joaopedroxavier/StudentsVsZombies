package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

public class EnergyGeneratorIA extends Input {
    private Game game;
    private int counterInterval = 300;
    private int counter;

    public Input copy() { return new EnergyGeneratorIA(game); }

    public EnergyGeneratorIA(Game game) {
        this.game = game;
        counter = counterInterval - 1;
    }

    public void update(GameObject obj, Boolean clicked) {
        int oldCounter = counter;
        counter = (counter + counterInterval - 1) % counterInterval;
        if(counter > oldCounter) {
           game.generateEnergy();
        }
    }
}

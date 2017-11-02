package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

public class EnergyGeneratorIA extends Input {
    private Game game;
    private int counter = 299;

    public Input copy() { return new EnergyGeneratorIA(game); }

    public EnergyGeneratorIA(Game game) {
        this.game = game;
    }

    public void update(GameObject obj, Boolean clicked) {
        int oldCounter = counter;
        counter = (counter + 299) % 300;
        if(counter > oldCounter) {
           game.generateEnergy();
        }
    }
}

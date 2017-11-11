package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Physics.EmptyPhysics;
import StudentsVsZombies.Physics.EnergyPhysics;
import StudentsVsZombies.Prototype;
import StudentsVsZombies.Spawnable;
import StudentsVsZombies.State.State;

import java.awt.*;

public class EnergyGeneratorIA extends Input {
    private Game game;
    private int counterInterval = 600;
    private int counter;

    public Input copy() { return new EnergyGeneratorIA(game); }

    public EnergyGeneratorIA(Game game) {
        this.game = game;
        counter = counterInterval - 1;
    }

    public void update(GameObject o, Boolean clicked) {
        Spawnable obj = (Spawnable) o;
        State currentState = obj.state;

        int oldCounter = counter;

        if (obj.hp <= 0) { currentState.die(obj, game); }

        counter = (counter + counterInterval - 1) % counterInterval;
        if(counter > oldCounter) {
            GameObject energy = game.energyPrototype.create(new Point(obj.x_, obj.y_));
            energy.py = new EmptyPhysics();
            game.addObject(energy);
        }
    }
}

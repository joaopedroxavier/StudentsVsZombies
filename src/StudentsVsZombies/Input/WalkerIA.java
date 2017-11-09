package StudentsVsZombies.Input;

import java.util.ArrayList;
import java.util.List;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;
import StudentsVsZombies.State.Eating;
import StudentsVsZombies.State.State;
import StudentsVsZombies.State.Walking;

public class WalkerIA extends Input {
    private Game game;
    private int eatCounter = 0;
    private double eatSpeed = 0.05;

    public WalkerIA copy(){ return new WalkerIA(game); }

    public WalkerIA(Game game) {
        this.game = game;
    }

    public void update(GameObject o, Boolean clicked) {
        Spawnable obj = (Spawnable) o;
        State currentState = obj.state;

        if(clicked) { System.out.println("Clicked a zombie!"); }

        if(obj.hp <= 0) { currentState.die(obj, game); }

        List<Spawnable> cell = obj.getListOfObjects();
        boolean foundPlant = false;
        for(Spawnable other : cell) {
            if(other.getBreed() == game.green_breed || other.getBreed() == game.blue_breed || other.getBreed() == game.sunflower_breed) {
                    foundPlant = true;
            }
        }

        if(foundPlant && currentState instanceof Walking) { currentState.change(obj); }
        if(!foundPlant && currentState instanceof Eating) { currentState.change(obj); }

        if(currentState instanceof Eating) {
            eatCounter++;
            if(eatCounter == (int) (1/eatSpeed)) {
                for(Spawnable other : cell) if(other.getBreed() == game.green_breed || other.getBreed() == game.blue_breed || other.getBreed() == game.sunflower_breed) {
                    other.hp -= obj.getBreed().getAttack();
                }
                eatCounter = 0;
            }
        }
    }
}
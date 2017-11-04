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

        if(obj.hp <= 0) { currentState.die(obj, game); System.out.println("Zombie is ded men."); }

        Cell cell = obj.getListOfObjects();
        boolean foundPlant = false;
        for(Spawnable other : cell) {
            if(other.getBreed() == game.green_breed && currentState instanceof Walking) {
                    currentState.change(obj);
                    foundPlant = true;
                    System.out.println("Zombie is eating.");
            }
        }
        if(!foundPlant && currentState instanceof Eating) { currentState.change(obj); }
    }
}
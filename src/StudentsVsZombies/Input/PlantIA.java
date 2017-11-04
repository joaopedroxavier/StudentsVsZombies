package StudentsVsZombies.Input;

import StudentsVsZombies.Cell;
import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;
import StudentsVsZombies.State.Shooting;
import StudentsVsZombies.State.Standing;
import StudentsVsZombies.State.State;
import StudentsVsZombies.State.Walking;

import java.awt.*;

public class PlantIA extends Input {
    private Game game;

    public PlantIA copy(){ return new PlantIA(game); }

    public PlantIA(Game game) {
        this.game = game;
    }

    public void update(GameObject o, Boolean clicked) {
        Spawnable obj = (Spawnable) o;
        State currentState = obj.state;


        if (obj.hp <= 0) { currentState.die(obj, game); }

        Point limits = obj.getLimits();
        Point myCell = obj.getCell();
        int zombiesInRow = 0;

        for(int i=myCell.x; i<limits.x; i++) {
            Point point = new Point(i, myCell.y);
            Cell cell = obj.getListOfObjects(point);
            for(Spawnable other : cell) {
                if(other.getBreed() == game.zombie_breed && currentState instanceof Standing) {
                    zombiesInRow++;
                    currentState.change(obj);
                }
            }
        }

        if(zombiesInRow == 0 && currentState instanceof Shooting) {
            currentState.change(obj);
        }
    }
}

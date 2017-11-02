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

    public void update(GameObject obj, Boolean clicked) {
        State currentState = ((Spawnable) obj).state;

        if( ((Spawnable)obj).hp <= 0 ) {
            currentState.die();
        }

        Point limits = obj.getLimits();
        Point myCell = obj.getCell();
        int zombiesInRow = 0;

        for(int j=myCell.y; j<=limits.y; j++) {
            Point point = new Point(myCell.x, j);
            Cell cell = obj.getListOfObjects(point);
            for(Spawnable other : cell) {
                if(other.getBreed() == game.zombie_breed && currentState instanceof Standing) {
                    zombiesInRow++;
                    currentState.change();
                }
            }
        }

        if(zombiesInRow == 0 && currentState instanceof Shooting) {
            currentState.change();
        }
    }
}

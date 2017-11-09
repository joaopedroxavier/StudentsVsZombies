package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;
import StudentsVsZombies.State.Shooting;
import StudentsVsZombies.State.Standing;
import StudentsVsZombies.State.State;
import StudentsVsZombies.State.Walking;

import java.awt.*;
import java.util.List;

public class PlantIA extends Input {
    private Game game;
    private double shoot_speed = 0.01;
    private int shoot_counter = 0;

    public PlantIA copy(){ return new PlantIA(game); }

    public PlantIA(Game game) {
        this.game = game;
    }

    public void update(GameObject o, Boolean clicked) {
        Spawnable obj = (Spawnable) o;
        State currentState = obj.state;

        if(clicked) { System.out.println("Clicked a plant!"); }

        if (obj.hp <= 0) { currentState.die(obj, game); }

        Point limits = obj.getLimits();
        Point myCell = obj.getCell();
        int zombiesInRow = 0;

        for(int i=myCell.x; i<=limits.y; i++) {
            Point point = new Point(i, myCell.y);
            List<Spawnable> cell = obj.getListOfObjects(point);
            for(Spawnable other : cell) {
                if(other.getBreed() == game.zombie_breed) {
                    zombiesInRow++;
                }
            }
        }

        if(zombiesInRow == 0 && currentState instanceof Shooting) currentState.change(obj);
        else if(zombiesInRow > 0 && currentState instanceof Standing) currentState.change(obj);

        if(currentState instanceof Shooting) {
            shoot_counter++;
            if(shoot_counter == (int)(1/shoot_speed)) {
                game.addObject(game.bulletPrototype.create(new Point(obj.x_, obj.y_)));
                shoot_counter = 0;
            }
        }
    }
}

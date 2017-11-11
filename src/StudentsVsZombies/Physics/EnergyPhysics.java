package StudentsVsZombies.Physics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Grid;

import java.awt.*;

public class EnergyPhysics extends Physics {
    private double speed = 2;
    private int counter;
    private Game game;

    public EnergyPhysics(Game game) {this.game = game; counter=0;}
    public Physics copy(){ return new EnergyPhysics(game);}
    public void update(GameObject obj) {
        Grid grid = game.getGrid();
        Point myCell = grid.get_cell(new Point(obj.x_, obj.y_));
        counter++;
        if(counter==3){
            obj.y_ += speed;
            counter=0;
        }
        if(myCell.y >= 5) {
            game.removeObject(obj);
        }
    }
}

package StudentsVsZombies.Physics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Grid;
import StudentsVsZombies.Spawnable;
import StudentsVsZombies.State.Walking;

import java.awt.*;
import java.util.List;

public class WalkerPhysics extends Physics {
    final private double speed = 0.8;
    private int update_counter = 0;
    private Game game;
    public WalkerPhysics(Game game) {this.game = game;}
    public WalkerPhysics copy() {return new WalkerPhysics(game);}
    public void update(GameObject o){
        Grid grid = game.getGrid();
        Spawnable obj = (Spawnable) o;
        if(obj.state instanceof Walking) {
            update_counter++;
            if (update_counter > 1 / speed){
                update_counter = 0;
                obj.x_ -= 1;
            }
            Point myCell = grid.get_cell(new Point(o.x_, o.y_));
            Point rightCell = grid.get_cell(new Point(o.x_+1, o.y_));
            List<Spawnable> objects = grid.getListOfObjects(myCell);
            if(objects.contains(o)){}
            else{
                grid.add(obj, myCell);
                grid.remove(o, rightCell);
            }
            if(o.x_ == 0) {
                game.removeObject(o);
                game.lose();
            }
        }
    }
}

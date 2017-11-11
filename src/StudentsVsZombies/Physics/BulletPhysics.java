package StudentsVsZombies.Physics;

import StudentsVsZombies.*;

import java.awt.*;
import java.util.List;

public class BulletPhysics extends Physics {
    private Game game;
    private double speed = 2;
    private int update_counter = 0;
    private Breed parentBreed;

    public BulletPhysics(Game game, Breed parentBreed) {this.game = game; this.parentBreed = parentBreed;}

    public Physics copy(){ return new BulletPhysics(game, parentBreed); }

    public void update(GameObject o){
        o.x_ += speed;
        Grid grid = game.getGrid();
        Point myCell = grid.get_cell(new Point(o.x_, o.y_));
        System.out.println(myCell.x + " " + myCell.y);
        if(myCell.x >= 9) {
            game.removeObject(o);
        }
        else {
            List<Spawnable> objects = grid.getListOfObjects(myCell);
            for (Spawnable element : objects) {
                if (element.getBreed() == game.zombie_breed) {
                    game.removeObject(o);
                    element.hp -= parentBreed.getAttack();
                }
            }
        }

    }
}

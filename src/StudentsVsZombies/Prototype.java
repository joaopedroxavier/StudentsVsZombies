package StudentsVsZombies;

import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;

import java.awt.*;

public class Prototype {
    GameObject original;

    Prototype(Graphics gr, Physics py, Input in, int width , int height){
        original = new GameObject(new Point(0, 0), gr.copy(), py.copy(), in.copy(), width, height);
    }

    Prototype clone(Point pnt){
        return new GameObject();
    }
}

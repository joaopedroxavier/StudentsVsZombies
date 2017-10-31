package StudentsVsZombies;

import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;

public class GameObject {
    private int x_, y;
    private int width, height;
    private Grid grid_;
    private Graphics gr;
    private Physics py;
    private Input in;

    GameObject(Grid grid){ grid_ = grid; }
    void update(){};
}

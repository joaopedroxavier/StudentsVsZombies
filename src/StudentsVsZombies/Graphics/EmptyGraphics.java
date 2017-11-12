package StudentsVsZombies.Graphics;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;

public class EmptyGraphics extends Graphics {
    public void update(GameObject obj, Game game){}
    public EmptyGraphics copy() { return new EmptyGraphics(); }
}

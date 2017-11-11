package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Spawnable;

import java.awt.*;
import java.util.List;

public class CardClick extends Input {
    private Game game;
    private boolean holding = false;

    public CardClick(Game game) { this.game = game; }

    public void update(GameObject obj, Boolean clicked){}
    public Input copy() { return new CardClick(game); }
}

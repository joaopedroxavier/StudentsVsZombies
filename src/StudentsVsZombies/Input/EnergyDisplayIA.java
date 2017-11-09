package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Graphics.StaticGraphics;

public class EnergyDisplayIA extends Input {
    private Game game;
    private int magnitude;
    private int energyAmount = 0;

    public EnergyDisplayIA(Game game, int m) { this.game = game; magnitude = m; }

    public EnergyDisplayIA copy() { return new EnergyDisplayIA(game, magnitude); }

    public void update(GameObject o, Boolean clicked) {
        energyAmount = game.energyAmount;
        int digit = energyAmountDigit(energyAmount);
        o.gr = new StaticGraphics(game.numbers[digit]);
    }

    private int energyAmountDigit(int amount) {
        int power = 1;
        for(int i=1; i<magnitude; i++) power = power * 10;
        int digit = amount / power;
        return digit % 10;
    }

}

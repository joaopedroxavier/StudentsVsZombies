package StudentsVsZombies.Input;

import StudentsVsZombies.Game;
import StudentsVsZombies.GameObject;
import StudentsVsZombies.Prototype;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class ProgressIA extends Input {
    private Game game;
    private int[] waveDensity;
    private int[] waveUpdateInterval;
    private int[] waveInstants;
    private int[] waveTime;
    private int N = 30;
    private int timeCounter = 0;
    private int updateTimeCounter = 0;
    private int currentWave = 0;
    private float difficultyIncrease = 2;

    public ProgressIA(Game game) {
        this.game = game;
        waveDensity = new int[N];
        waveUpdateInterval = new int[N];
        waveTime = new int[N];

        waveUpdateInterval[0] = 600;
        waveTime[0] = 1200;
        waveDensity[0] = 0;
        waveInstants = new int[waveUpdateInterval[0]];

        for(int i = 1; i < N; i++) {
            waveTime[i] = 1200;
            waveUpdateInterval[i] = 600;
            waveDensity[i] = (int) (difficultyIncrease*i);
        }
    }

    public void update(GameObject obj, Boolean clicked){
        timeCounter++;
        updateTimeCounter++;

        if(currentWave < N) {

            for (int i = 0; i < waveUpdateInterval[currentWave]; i++) if(updateTimeCounter == i){
                int cnt = 0;
                while (cnt < waveInstants[i]) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
                    game.addObject(game.zombie_breed.spawn(new Point(randomNum, 8), game.getGrid()));
                    cnt++;
                }
            }

            if (updateTimeCounter >= waveUpdateInterval[currentWave]) {
                System.out.println("New interval");
                updateTimeCounter = 0;
                waveInstants = new int[waveUpdateInterval[currentWave]];
                for (int i = 0; i < waveDensity[currentWave]; i++) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, waveUpdateInterval[currentWave]);
                    System.out.println("Instant: " + randomNum);
                    waveInstants[randomNum]++;
                }
            }

            if (timeCounter >= waveTime[currentWave]) {
                timeCounter = 0;
                currentWave++;
                System.out.println(currentWave);
            }

        } else {
            game.win();
        }
    }

    public Input copy() { return new ProgressIA(game); }
}

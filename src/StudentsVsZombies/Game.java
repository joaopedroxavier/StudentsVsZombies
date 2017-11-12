package StudentsVsZombies;

import StudentsVsZombies.Graphics.EmptyGraphics;
import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Input.*;
import StudentsVsZombies.Physics.*;
import StudentsVsZombies.State.Standing;
import StudentsVsZombies.State.Walking;
import StudentsVsZombies.Graphics.StateGraphics;
import StudentsVsZombies.Graphics.StaticGraphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashSet;
import java.util.TreeSet;

public class Game implements Runnable {
    public ArrayList<GameObject> objects;
    private ArrayList<GameObject> dying;
    private ArrayList<GameObject> borning;
    private Grid grid;
    private Point click;
    public int energyAmount = 500;
    public Breed zombie_breed;
    public Breed green_breed;
    public Breed blue_breed;
    public Breed sunflower_breed;
    public Breed sun_breed;
    public Prototype energyPrototype;
    public Prototype green_bulletPrototype;
    public Prototype blue_bulletPrototype;
    private Card sunCard;
    private Card blueCard;
    private Card greenCard;
    private Card holding;
    private GameObject progressIntelligence;

    public BufferedImage[] numbers;
    private BufferedImage greenPlantSprite = null;
    private BufferedImage bluePlantSprite = null;
    private BufferedImage sunflowerPlantSprite = null;

    private GameObject gameOverBG = null;
    private GameObject winnerBG = null;


    private int scale = 4;
    private int WIDTH = 144, HEIGHT = 112;
    private int energyCounter = 0;
    private boolean running = true;
    private boolean winner;
    private long desiredFPS = 60;
    private long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
    private JFrame frame;
    public JPanel panel;
    private Canvas canvas;
    public BufferStrategy bufferStrategy;

    Game(){
        frame = new JFrame("Basic Game");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH*scale, HEIGHT*scale));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH*scale, HEIGHT*scale);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        MouseControl mouse = new MouseControl();
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();
        construct_world();
    }

    private class MouseControl extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            click = new Point(e.getPoint());
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
            click = new Point(e.getPoint());
            if(holding != null) {
                holding.x_ = click.x - holding.getWidth() / 2;
                holding.y_ = click.y - holding.getHeight() / 2;
            }
        }

        public void mousePressed(MouseEvent e) {
            click = new Point(e.getPoint());
            for (GameObject obj : objects) {
                boolean clickInsideObject = (click != null && obj.x_ <= click.getX() && obj.x_ + obj.getWidth() >= click.getX() && obj.y_ <= click.getY() && obj.y_ + obj.getHeight() >= click.getY());
                if((obj instanceof Card) && clickInsideObject) {
                    Card myCard = (Card) obj;
                    if(myCard.getCost() <= energyAmount) {
                        holding = myCard.copy();
                        holding.gr = obj.gr.copy();
                        holding.gr.setAlpha((float) 0.5);
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            click = new Point(e.getPoint());
            boolean clickInsideObject = (click != null && grid.x_ <= click.getX() && grid.x_ + grid.getWidth() >= click.getX() && grid.y_ <= click.getY() && grid.y_ + grid.getHeight() >= click.getY());
            if(holding != null && clickInsideObject) {

                Point cellClick = new Point(grid.get_cell(click).y, grid.get_cell(click).x);

                Breed breed = holding.getBreed();
                List<Spawnable> cell = grid.getListOfObjects(grid.get_cell(click));
                boolean foundPlant = false;
                for(Spawnable other : cell) {
                    if(other.getBreed() == green_breed || other.getBreed() == blue_breed || other.getBreed() == sunflower_breed) {
                        foundPlant = true;
                    }
                }
                if(!foundPlant && breed != null) {
                    energyAmount -= holding.getCost();
                    addObject(breed.spawn(cellClick, grid));
                }
            }
            holding = null;
        }
    }

    public void run(){
        long beginLoopTime;
        long endLoopTime;
        long deltaLoop;

        while(running){
            beginLoopTime = System.nanoTime();

            update();

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            if(deltaLoop < desiredDeltaLoop) {
                try{
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
                }catch(InterruptedException e){ /*Do nothing*/ }
            }
        }

        gameOver();
    }

    private void construct_world(){
        grid = new Grid(0, 128-16, 16*scale, 6, 10);
        objects = new ArrayList<>();
        dying = new ArrayList<>();
        borning = new ArrayList<>();
        numbers = new BufferedImage[10];

        try {
            File file = new File("gfx/sheets/numbersx" + scale + ".png");
            BufferedImage img = ImageIO.read(file);
            for (int i = 0 ; i < 10 ; ++i) {
                numbers[i] = img.getSubimage(3*scale*i, 0, 3*scale, 5*scale);
            }
            File bgFile = new File("gfx/sheets/backgroundx"+scale+".png");
            BufferedImage bgImg = ImageIO.read(bgFile);
            StaticGraphics background = new StaticGraphics(bgImg);
            GameObject bg = new GameObject(new Point(0,0), background, new EmptyPhysics(), new EmptyInput(), 112*scale,144*scale);
            objects.add(bg);

            File gameOverFile = new File("gfx/src/LoseScreen.png");
            BufferedImage gameOverImg = ImageIO.read(gameOverFile);
            StaticGraphics gbg = new StaticGraphics(gameOverImg);
            gameOverBG = new GameObject(new Point(0,0), gbg, new EmptyPhysics(), new EmptyInput(), 112*scale,144*scale);

            File winScreenFile = new File("gfx/src/WinScreen.png");
            BufferedImage winScreenImg = ImageIO.read(winScreenFile);
            StaticGraphics wbg = new StaticGraphics(winScreenImg);
            winnerBG = new GameObject(new Point(0,0), wbg, new EmptyPhysics(), new EmptyInput(), 112*scale,144*scale);

            File greenPlantFile = new File("gfx/sheets/plant-greenx"+scale+".png");
            BufferedImage greenImg = ImageIO.read(greenPlantFile);
            greenPlantSprite = ((greenImg.getSubimage(0, 0, 16*scale, 16*scale)));

            File bluePlantFile = new File("gfx/sheets/plant-bluex"+scale+".png");
            BufferedImage blueImg = ImageIO.read(bluePlantFile);
            bluePlantSprite = ((blueImg.getSubimage(0, 0, 16*scale, 16*scale)));

            File sunflowerPlantFile = new File("gfx/sheets/sunflowerx"+scale+".png");
            BufferedImage sunImg = ImageIO.read(sunflowerPlantFile);
            sunflowerPlantSprite = ((sunImg.getSubimage(0, 0, 16*scale, 16*scale)));

        } catch (IOException e) { e.printStackTrace(); }

        // build list of animation sheets relative to the entities
        ArrayList<String> greens = new ArrayList<>();
        ArrayList<String> blues = new ArrayList<>();
        ArrayList<String> sun = new ArrayList<>();
        ArrayList<String> sunflower = new ArrayList<>();
        ArrayList<String> zombies = new ArrayList<>();
        greens.add("gfx/sheets/plant-greenx" + scale +".png");
        greens.add("gfx/sheets/plant-green-shootingx" + scale +".png");
        blues.add("gfx/sheets/plant-bluex" + scale +".png");
        blues.add("gfx/sheets/plant-blue-shootingx" + scale +".png");
        sun.add("gfx/sheets/sunx"+scale+".png");
        sunflower.add("gfx/sheets/sunflowerx"+scale+".png");
        zombies.add("gfx/sheets/zombiex"+scale+".png");
        zombies.add("gfx/sheets/zombie-eatingx"+scale+".png");

        green_breed = new Breed(100, 100, greens, scale, new PlantPhysics(), new PlantIA(this), new Standing());
        blue_breed = new Breed(100, 300, blues, scale, new PlantPhysics(), new PlantIA(this), new Standing());
        zombie_breed = new Breed(400,10, zombies, scale, new WalkerPhysics(this), new WalkerIA(this), new Walking());
        sunflower_breed = new Breed(100, 10, sunflower, scale, new PlantPhysics(), new EnergyGeneratorIA(this), new Standing());

        green_bulletPrototype = new Prototype("gfx/sheets/green-bulletx"+scale+".png", scale, new BulletPhysics(this, green_breed), new EmptyInput(), 50, 50); // colocar imagem da bullet x
        blue_bulletPrototype = new Prototype("gfx/sheets/green-bulletx"+scale+".png", scale, new BulletPhysics(this, blue_breed), new EmptyInput(), 50, 50); // colocar imagem da bullet x
        energyPrototype = new Prototype("gfx/sheets/sunx"+scale+".png", scale, new EnergyPhysics(this), new EnergyClick(this), 50, 50); // Colocar animacao da energia

        GameObject display1 = new GameObject(new Point(41,72), new StaticGraphics(numbers[0]), new EmptyPhysics(), new EnergyDisplayIA(this, 1), numbers[0].getWidth(), numbers[0].getHeight());
        GameObject display2 = new GameObject(new Point(41 - (numbers[0].getWidth() + 3),72), new StaticGraphics(numbers[0]), new EmptyPhysics(), new EnergyDisplayIA(this, 2), numbers[0].getWidth(), numbers[0].getHeight());
        GameObject display3 = new GameObject(new Point(41 - 2*(numbers[0].getWidth() + 3),72), new StaticGraphics(numbers[0]), new EmptyPhysics(), new EnergyDisplayIA(this, 3), numbers[0].getWidth(), numbers[0].getHeight());

        progressIntelligence = new GameObject(new Point(0, 0), new EmptyGraphics(), new EmptyPhysics(), new ProgressIA(this), 0, 0);

        try {
            sunCard = new Card(sunflower_breed, new GameObject(new Point(63, 0), new StaticGraphics(sunflowerPlantSprite), new EmptyPhysics(), new CardClick(this), sunflowerPlantSprite.getWidth(), sunflowerPlantSprite.getHeight()),50);
            greenCard = new Card(green_breed, new GameObject(new Point(63 + (sunflowerPlantSprite.getWidth() - 1),0), new StaticGraphics(greenPlantSprite), new EmptyPhysics(), new CardClick(this), greenPlantSprite.getWidth(), greenPlantSprite.getHeight()), 100);
            blueCard = new Card(blue_breed, new GameObject(new Point(63 + (sunflowerPlantSprite.getWidth() + 1) * 2, 0), new StaticGraphics(bluePlantSprite), new EmptyPhysics(), new CardClick(this), bluePlantSprite.getWidth(), bluePlantSprite.getHeight()), 200);
        } catch (Exception e) { e.printStackTrace(); }

        //GameObject zero = new GameObject(new Point(0,0), new StaticGraphics(numbers[0]), new PlantPhysics(), new Idle() , 5*scale, 3*scale);
        //objects.add(zero);
        //objects.add(bulletPrototype.create(new Point(200, 200))); // sun and bullet creation example
        objects.add(display1);
        objects.add(display2);
        objects.add(display3);

        objects.add(greenCard);
        objects.add(blueCard);
        objects.add(sunCard);

        //objects.add(energyPrototype.create(new Point(50, 100))); //Apenas para teste do EnergyPhysics

    }

    public void gainEnergy() {
        energyAmount += 25;
    }

    private void update() {
        energyCounter++;
        if(energyCounter >= 600) {
            int randomNum = ThreadLocalRandom.current().nextInt(50, 500);
            objects.add(energyPrototype.create(new Point(randomNum, 50)));
            energyCounter=0;
        }

        bufferStrategy.getDrawGraphics().clearRect(0, 0, WIDTH, HEIGHT);
        for (GameObject obj : dying) {
            objects.remove(obj);
            grid.remove(obj, grid.get_cell(new Point(obj.x_, obj.y_)));
        }
        for (GameObject obj : borning) objects.add(obj);
        dying.clear();
        borning.clear();
        for (GameObject obj : objects) {
            boolean clickInsideObject = (click != null && obj.x_ <= click.getX() && obj.x_ + obj.getWidth() >= click.getX() && obj.y_ <= click.getY() && obj.y_ + obj.getHeight() >= click.getY());
            obj.py.update(obj);
            obj.gr.update(obj, this);
            obj.in.update(obj, clickInsideObject);
        }

        progressIntelligence.in.update(progressIntelligence, false);
        if(holding != null) holding.gr.update(holding, this);

        click = null;
        bufferStrategy.show();
    }

    public void removeObject(GameObject obj){
        dying.add(obj);
    }
    public void addObject(GameObject obj){ borning.add(obj); }

    public Grid getGrid() { return grid; }
    public Point getClick() { return click; }

    public void lose() { running = false; winner = false;}
    public void win() { running = false; winner = true;}

    public void gameOver() {
        if(winner) objects.add(winnerBG);
        else objects.add(gameOverBG);

        update();

        objects.clear();
        dying.clear();
        borning.clear();

        try {
            Thread.sleep(1000);
        } catch (Exception e) { e.printStackTrace(); }

        running = false;
    }

    public static void main(String [] args){
        Game game = new Game();
        new Thread(game).run();
    }
}

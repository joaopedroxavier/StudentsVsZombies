package StudentsVsZombies;

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
import java.util.HashSet;
import java.util.TreeSet;

public class Game implements Runnable {
    public ArrayList<GameObject> objects;
    private ArrayList<GameObject> dying;
    private ArrayList<GameObject> borning;
    private Grid grid;
    private GameObject holding;
    private Point click;
    public int energyAmount = 0;
    public Breed zombie_breed;
    public Breed green_breed;
    public Breed blue_breed;
    public Breed sunflower_breed;
    public Breed sun_breed;
    public Prototype energyPrototype;
    public Prototype green_bulletPrototype;
    public Prototype blue_bulletPrototype;
    public GameObject sunCard;
    public GameObject blueCard;
    public GameObject greenCard;

    public BufferedImage[] numbers;


    private int scale = 4;
    private int WIDTH = 144, HEIGHT = 112;
    private boolean running = true;
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

        canvas.addMouseListener(new MouseControl());

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
            System.out.println(click.x + " " + click.y);
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            click = new Point(e.getPoint());
            System.out.println(click.x + " " + click.y);
            for (GameObject obj : objects) {
                boolean clickInsideObject = (click != null && obj.x_ <= click.getX() && obj.x_ + obj.getWidth() >= click.getX() && obj.y_ <= click.getY() && obj.y_ + obj.getHeight() >= click.getY());
                if((obj == greenCard || obj == blueCard || obj == sunCard) && clickInsideObject) {
                    holding = obj;
                    System.out.println("Holding!!");
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            click = new Point(e.getPoint());
            System.out.println(click.x + " " + click.y);
            boolean clickInsideObject = (click != null && grid.x_ <= click.getX() && grid.x_ + grid.getWidth() >= click.getX() && grid.y_ <= click.getY() && grid.y_ + grid.getHeight() >= click.getY());
            if(holding != null) {

                System.out.println("Releasing!!");
                System.out.println(grid.get_cell(click).x + " " + grid.get_cell(click).y);
                Point cellClick = new Point(grid.get_cell(click).y, grid.get_cell(click).x);

                Breed breed = (holding == greenCard) ? green_breed : ((holding == blueCard) ? blue_breed : sunflower_breed);
                List<Spawnable> cell = grid.getListOfObjects(grid.get_cell(click));
                boolean foundPlant = false;
                for(Spawnable other : cell) {
                    if(other.getBreed() == green_breed || other.getBreed() == blue_breed || other.getBreed() == sunflower_breed) {
                        foundPlant = true;
                    }
                }
                if(!foundPlant && breed != null) { addObject(breed.spawn(cellClick, grid)); }
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
        } catch (IOException e) { e.printStackTrace(); }
        File file = new File("gfx/sheets/backgroundx"+scale+".png");
        try {
            BufferedImage img = ImageIO.read(file);
            StaticGraphics background = new StaticGraphics(img);
            GameObject bg = new GameObject(new Point(0,0), background, new EmptyPhysics(), new EmptyInput(), 112*scale,144*scale);
            objects.add(bg);
        } catch (IOException e) { System.out.println("Background file not found."); }

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
        zombie_breed = new Breed(700,10, zombies, scale, new WalkerPhysics(this), new WalkerIA(this), new Walking());
        sunflower_breed = new Breed(100, 10, sunflower, scale, new PlantPhysics(), new EnergyGeneratorIA(this), new Standing());

        green_bulletPrototype = new Prototype("gfx/sheets/green-bulletx"+scale+".png", scale, new BulletPhysics(this, green_breed), new EmptyInput(), 50, 50); // colocar imagem da bullet x
        blue_bulletPrototype = new Prototype("gfx/sheets/green-bulletx"+scale+".png", scale, new BulletPhysics(this, blue_breed), new EmptyInput(), 50, 50); // colocar imagem da bullet x
        energyPrototype = new Prototype("gfx/sheets/sunx"+scale+".png", scale, new EnergyPhysics(), new EnergyClick(this), 50, 50); // Colocar animacao da energia

        GameObject display1 = new GameObject(new Point(41,72), new StaticGraphics(numbers[0]), new EmptyPhysics(), new EnergyDisplayIA(this, 1), numbers[0].getWidth(), numbers[0].getHeight());
        GameObject display2 = new GameObject(new Point(41 - (numbers[0].getWidth() + 3),72), new StaticGraphics(numbers[0]), new EmptyPhysics(), new EnergyDisplayIA(this, 2), numbers[0].getWidth(), numbers[0].getHeight());
        GameObject display3 = new GameObject(new Point(41 - 2*(numbers[0].getWidth() + 3),72), new StaticGraphics(numbers[0]), new EmptyPhysics(), new EnergyDisplayIA(this, 3), numbers[0].getWidth(), numbers[0].getHeight());

        sunCard = new GameObject(new Point(90,40), new StaticGraphics(numbers[0]), new EmptyPhysics(), new CardClick(this), numbers[0].getWidth(), numbers[0].getHeight());
        greenCard = new GameObject(new Point(150,40), new StaticGraphics(numbers[0]), new EmptyPhysics(), new CardClick(this), numbers[0].getWidth(), numbers[0].getHeight());
        blueCard = new GameObject(new Point(210,40), new StaticGraphics(numbers[0]), new EmptyPhysics(), new CardClick(this), numbers[0].getWidth(), numbers[0].getHeight());

        //GameObject zero = new GameObject(new Point(0,0), new StaticGraphics(numbers[0]), new PlantPhysics(), new Idle() , 5*scale, 3*scale);
        //objects.add(zero);
        for (int i = 0 ; i < 5 ; ++i) {
            objects.add(green_breed.spawn(new Point(i, 2), grid));
            objects.add(blue_breed.spawn(new Point(i, 1), grid));
            objects.add(sunflower_breed.spawn(new Point(i, 0), grid));
        }
        objects.add(zombie_breed.spawn(new Point (1,8), grid));
        //objects.add(bulletPrototype.create(new Point(200, 200))); // sun and bullet creation example
        objects.add(display1);
        objects.add(display2);
        objects.add(display3);

        objects.add(greenCard);
        objects.add(blueCard);
        objects.add(sunCard);
    }

    public void gainEnergy() {
        energyAmount += 5;
    }

    private void update() {
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
        click = null;
        bufferStrategy.show();
    }

    public void removeObject(GameObject obj){
        dying.add(obj);
    }
    public void addObject(GameObject obj){ borning.add(obj); }

    public Grid getGrid() { return grid; }
    public Point getClick() { return click; }

    public static void main(String [] args){
        Game game = new Game();
        new Thread(game).run();
    }
}

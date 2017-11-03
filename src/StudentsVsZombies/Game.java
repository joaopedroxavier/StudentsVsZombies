package StudentsVsZombies;

import StudentsVsZombies.Input.*;
import StudentsVsZombies.Physics.BulletPhysics;
import StudentsVsZombies.Physics.EnergyPhysics;
import StudentsVsZombies.Physics.PlantPhysics;
import StudentsVsZombies.Physics.WalkerPhysics;
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

public class Game implements Runnable {
    private ArrayList<GameObject> objects;
    private Breed zombie, plant;
    private Grid grid;
    private GameObject holding;
    private Point click;
    public Breed zombie_breed;
    public Breed green_breed;
    public Breed blue_breed;
    public Breed sunflower_breed;
    public Breed sun_breed;
    private Prototype energyPrototype;
    private Prototype bulletPrototype;
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
        public void mouseClicked(MouseEvent e){
            click = e.getPoint();
            System.out.println(click.x + " " + click.y);
        }
        public void mouseMoved(MouseEvent e){}
        public void mouseDragged(MouseEvent e){}
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
        grid = new Grid(0, 128, 16*scale, 9, 5);
        objects = new ArrayList<>();
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
	        GameObject bg = new GameObject(new Point(0,0), background, new PlantPhysics(), new Idle(), 112*scale,144*scale);
	        objects.add(bg);
	        BufferedImage bullet = ImageIO.read(new File("gfx/sheets/green-bulletx"+scale+".png"));
	        BufferedImage sunsheet = ImageIO.read(new File("gfx/sheets/sunx"+scale+".png"));
            bulletPrototype = new Prototype(new StaticGraphics(bullet), new BulletPhysics(), new Idle(), 10, 10); // colocar imagem da bullet x
            // TODO change StaticGraphics to StateGraphics(sunsheet,scale);
            energyPrototype = new Prototype(new StaticGraphics(sunsheet.getSubimage(0, 0, 16*scale, 16*scale)), new EnergyPhysics(), new EnergyGeneratorIA(this), 10, 10); // Colocar animacao da energia
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
        green_breed = new Breed(100, 10, greens, scale, new PlantPhysics(), new PlantIA(this), new Standing());
        blue_breed = new Breed(100, 10, blues, scale, new PlantPhysics(), new PlantIA(this), new Standing());
        zombie_breed = new Breed(100,10, zombies, scale, new PlantPhysics(), new PlantIA(this), new Standing());

        sunflower_breed = new Breed(100, 10, sunflower, scale, new PlantPhysics(), new PlantIA(this), new Standing());
        sun_breed = new Breed(100,0, sun ,scale, new PlantPhysics(), new PlantIA(this), new Standing());
        //GameObject zero = new GameObject(new Point(0,0), new StaticGraphics(numbers[0]), new PlantPhysics(), new Idle() , 5*scale, 3*scale);
        //objects.add(zero);
        for (int i = 0 ; i < 5 ; ++i) {
            objects.add(green_breed.spawn(new Point(i, 2), grid));
            objects.add(blue_breed.spawn(new Point(i, 1), grid));
            objects.add(sunflower_breed.spawn(new Point(i, 0), grid));
            objects.add(zombie_breed.spawn(new Point (i,7), grid));
        }
        objects.add(sun_breed.spawn(new Point (0, 0), grid));

    }

    public void generateEnergy() {
        System.out.println("Gerou. Top.");
    }

    private void update(){
        bufferStrategy.getDrawGraphics().clearRect(0, 0, WIDTH, HEIGHT);
        for(GameObject obj : objects){ obj.py.update(obj); obj.gr.update(obj, this); obj.in.update(obj, true); }
        bufferStrategy.show();
    }

    public Point get_size(){return new Point(WIDTH, HEIGHT);}

    public static void main(String [] args){
         Game game = new Game();
         new Thread(game).run();
    }
}

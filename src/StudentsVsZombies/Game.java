package StudentsVsZombies;

import StudentsVsZombies.Input.*;
import StudentsVsZombies.Physics.BulletPhysics;
import StudentsVsZombies.Physics.EnergyPhysics;
import StudentsVsZombies.Physics.PlantPhysics;
import StudentsVsZombies.Physics.WalkerPhysics;
import StudentsVsZombies.State.Standing;
import StudentsVsZombies.State.Walking;
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
    private Prototype energyPrototype;
    private Prototype bulletPrototype;


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
        grid = new Grid(-30, 85, 16*scale, 9, 5);
        objects = new ArrayList<>();
        File file = new File("gfx/sheets/backgroundx4.png");
		try {
			BufferedImage img = ImageIO.read(file);
	        StaticGraphics background = new StaticGraphics(img);
	        GameObject bg = new GameObject(new Point(0,0), background, new PlantPhysics(), new Idle(), 112*scale,144*scale);
	        objects.add(bg);
            bulletPrototype = new Prototype(new StaticGraphics(img), new BulletPhysics(), new Idle(), 10, 10); // colocar imagem da bullet x
            energyPrototype = new Prototype(new StaticGraphics(img), new EnergyPhysics(), new EnergyGeneratorIA(this), 10, 10); // Colocar animacao da energia
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        green_breed = new Breed(100, 10, "gfx/sheets/plant-greenx4.png", scale, new PlantPhysics(), new PlantIA(this), new Standing());
        blue_breed = new Breed(100, 10, "gfx/sheets/plant-icex4.png", scale, new PlantPhysics(), new PlantIA(this), new Standing());

        sunflower_breed = new Breed(100, 10,"gfx/sheets/sunflowerx4.png", scale, new PlantPhysics(), new PlantIA(this), new Standing());

        for (int i = 0 ; i < 5 ; ++i) {
            objects.add(green_breed.spawn(new Point(2, i), grid));
            objects.add(blue_breed.spawn(new Point(1, i), grid));
            objects.add(sunflower_breed.spawn(new Point(0, i), grid));
        }
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

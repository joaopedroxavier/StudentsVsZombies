package StudentsVsZombies;

import StudentsVsZombies.Input.PlantIA;
import StudentsVsZombies.Input.WalkerIA;
import StudentsVsZombies.Physics.PlantPhysics;
import StudentsVsZombies.Physics.WalkerPhysics;
import StudentsVsZombies.State.Standing;
import StudentsVsZombies.State.Walking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game implements Runnable {
    private ArrayList<GameObject> objects;
    private Breed zombie, plant;
    private Grid grid;
    private GameObject holding;
    private Point click;
    private Breed zombie_breed;
    private Breed plant_breed;


    private int WIDTH = 800, HEIGHT = 600;
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
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
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
        grid = new Grid(0, 0, 80, 5, 9);
        objects = new ArrayList<GameObject>();
        ArrayList<String> z_folders = new ArrayList<>(), p_folders = new ArrayList<>();
        z_folders.add("zombie/walking/");
        z_folders.add("zombie/eating/");
        z_folders.add("zombie/dying/");
        p_folders.add("plant/standing/");
        p_folders.add("plant/shooting/");
        p_folders.add("plant/dying/");
        zombie_breed = new Breed(100, 10, z_folders, new WalkerPhysics(), new WalkerIA(), new Walking());
        plant_breed = new Breed(100, 10, p_folders, new PlantPhysics(), new PlantIA(), new Standing());

        objects.add(zombie_breed.spawn(new Point(grid.get_limit().x, 1), grid));
        objects.add(zombie_breed.spawn(new Point(grid.get_limit().x, 2), grid));
        objects.add(zombie_breed.spawn(new Point(grid.get_limit().x, 3), grid));
        objects.add(zombie_breed.spawn(new Point(grid.get_limit().x, 4), grid));
        objects.add(zombie_breed.spawn(new Point(grid.get_limit().x, 5), grid));

    }

    private void update(){
        bufferStrategy.getDrawGraphics().clearRect(0, 0, WIDTH, HEIGHT);
        for(GameObject obj : objects){ obj.py.update(obj); obj.gr.update(obj, this); }
        bufferStrategy.show();
    }

    public Point get_size(){return new Point(WIDTH, HEIGHT);}

    public static void main(String [] args){
         Game game = new Game();
         new Thread(game).run();
    }
}

package StudentsVsZombies;

import StudentsVsZombies.Graphics.Graphics;
import StudentsVsZombies.Graphics.StaticGraphics;
import StudentsVsZombies.Input.Input;
import StudentsVsZombies.Physics.Physics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Prototype {
    GameObject original;

    public Prototype(String spritesheet_path, int scale, Physics py, Input in, int width , int height){
        try {
		        ArrayList<BufferedImage> spr = new ArrayList<>();
	        	File file = new File(spritesheet_path);
	        	BufferedImage img = ImageIO.read(file);
	        	for (int i = 0 ; i < img.getWidth()/(16*scale); ++i) {
	        		spr.add((img.getSubimage(i*16*scale, 0, 16*scale, 16*scale)));
	        	}
                original = new GameObject(new Point(0, 0), new StaticGraphics(spr), py.copy(), in.copy(), width, height);
		}
		catch (IOException e) {
			System.out.println("Prototype animation file not found at " + spritesheet_path);
		}
    }

    public GameObject create(Point pos){
        return new GameObject(original, pos);
    }
}

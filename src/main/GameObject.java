package main;
// Class: GameObject
// Written by: Woodland Wanderer Dev Team
// Date: 1/20/2022
// Description: This class consists of the implementation of a game object/entity.
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*; import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

public class GameObject {

	// instance variables
	protected String name;
	protected Hitbox hitbox;
	protected double x,y,width,height;
	protected String currentAnimation;
	private Hashtable<String, ArrayList<VolatileImage>> animations;
	private Hashtable<String, Integer> times;
	private int SCALE;
	protected double frame;
	protected double xVelocity;
	protected double yVelocity;
	protected boolean flipped;

	// packed constructor 1
	public GameObject(double x, double y, double width, double height, String imagePath, String[] images, int[] lengths, int[] times, int scale,  String name) throws IOException {
		this.name = name;
		this.x = x;
		this.y = y;
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.animations = new Hashtable<>();
		this.times = new Hashtable<>();
		this.frame = 0;
		this.SCALE = scale;

		for(int i = 0; i < images.length; i++) {
			ArrayList<VolatileImage> imagesToStore = new ArrayList<>();
			for(int j = 1; j <= lengths[i]; j++) {
				
				String newImagePath = "main/" + imagePath + images[i] + (j) + ".png";
				ClassLoader cldr = this.getClass().getClassLoader();
				URL imageURL = cldr.getResource(newImagePath);	
				ImageIcon image = new ImageIcon(newImagePath);	
				image = new ImageIcon(imageURL);
				image.getImage();
				Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / SCALE, image.getIconHeight() / SCALE, Image.SCALE_SMOOTH);
				imagesToStore.add(loadFromFile(imageURL));
			}

			animations.put(images[i], imagesToStore);
			this.times.put(images[i], times[i]);
		}

		this.currentAnimation = images[0];
		this.width = width;
		this.height = height;
		this.hitbox = new Hitbox(new double[]{0, 0, width, height});
		flipped = false;
	}

	// packed constructor 2
	public GameObject(double x, double y, double width, double height, String name) {
		this.name = name;
		this.hitbox = new Hitbox(new double[]{0, 0, width, height});
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.animations = null;
	}

	// packed constructor 3
	public GameObject(double x, double y, String name) {
		this(x, y, 25, 25, name);
	}

	// method: createVolatileImage
	// parameters: the width, the height and whether or not there is transparency in an image, all ints
	// return type: it returns a blank volatile image so the images can be added to it
	// description: it create a new blank volatile image
	
	
	private VolatileImage createVolatileImage(int width, int height, int transparency) { 
		
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
	GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration(); 
	VolatileImage image = null;
	image = gc.createCompatibleVolatileImage(width, height, transparency);
	int valid = image.validate(gc);
	if (valid == VolatileImage.IMAGE_INCOMPATIBLE) { 
		image = this.createVolatileImage(width, height, transparency); return image; 
	}
	return image; }
	
	
	
	// method: loadFromFile
	// parameters: The url of an image imageURL
	// return type: it returns a volatile image (an image that renders faster)
	// description: it gets a volatile image from a file path
	
	public VolatileImage loadFromFile(URL imageURL) throws IOException {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		
		BufferedImage bimage = ImageIO.read(imageURL);
		
		VolatileImage vimage = createVolatileImage(bimage.getWidth()/SCALE, bimage.getHeight()/SCALE, Transparency.TRANSLUCENT);
		
		Graphics2D g = null;

		try { 
			g = vimage.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.setColor(Color.black); 
			g.clearRect(0, 0, vimage.getWidth(), vimage.getHeight());
			g.drawImage(bimage, 0, 0, bimage.getWidth()/SCALE, bimage.getHeight()/SCALE, 0, 0, bimage.getWidth(),bimage.getHeight(), null);
		}
		finally { 
			g.dispose(); 
		}
	
		return vimage;
	}
	
	
	
	
	
	
	// method: collide
	// parameters: otherObject
	// return type: array of doubles
	// description: detects collision
	public double[] collide(GameObject otherObject) {
		return hitbox.calcCollision(this, otherObject);
	}

	// method: updateVelocity
	// parameters: xVelocity and yVelocity
	// return type: void
	// description: updates velocity
	public void updateVelocity(double x, double y) {
		xVelocity += x;
		yVelocity += y;
	}

	// getter
	public Hitbox getHitBox() {
		return hitbox;
	}

	// method: move
	// parameters: xMove and yMove
	// return type: void
	// description: moves the object
	public void move(double x, double y) {
		this.x += x;
		this.y += y;
	}

	// method: setPos
	// parameters: xLocation and yLocation
	// return type: void
	// description: sets position of the object
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// method: draw
	// parameters: cameraX, cameraY, Component c, Graphics g
	// return type: void
	// description: updates the image drawing
	public void draw(double cameraX, double cameraY, Component c, Graphics g) {
		int relativeX = (int)x - (int)cameraX;
		int relativeY =(int)y - (int)cameraY;
		
		if(this.animations != null) {
			if(flipped) {
				frame +=.05 * times.get("flipped" + currentAnimation);
				frame %= animations.get("flipped" + currentAnimation).size();
				g.drawImage(animations.get("flipped" + currentAnimation).get((int)frame), (int)relativeX, (int)relativeY, c);
			} else {
				frame +=.05 * times.get(currentAnimation);
				frame %= animations.get(currentAnimation).size();
				g.drawImage(animations.get(currentAnimation).get((int)frame), (int)relativeX, (int)relativeY, c);
			}
		} else {
			g.setColor(new Color(53, 26, 22));
			g.fillRect((int)relativeX, (int)relativeY, (int)width, (int)height);
		}
	}
}
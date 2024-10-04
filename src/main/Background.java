package main;
import java.awt.AlphaComposite;
import java.awt.Color;
// Class: Background
// Written by: Woodland Wanderer Dev Team
// Date: 1/20/2022
// Description: This is an abstract class that provides partial implementation for a Background. You can't
// 				create an instance of this class.
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Background {

	// instance variables
	protected VolatileImage image;
	protected double scale;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	// packed constructor
	public Background(int x, String pathOfImage) throws IOException {
		ClassLoader cldr = this.getClass().getClassLoader();
		String imagePath = pathOfImage;
		URL imageURL = cldr.getResource(imagePath);

		image = loadFromFile(imageURL);

		this.x = x;
		this.y=0;
	}

	// default constructor (unused)
	public Background() throws IOException {
		this(0,"images/background/raceTrack.png");
	}

	// method: draw
	// parameters: Component c and Graphics g
	// return type: void
	// description: Draws the background
	public void draw(Component c, Graphics g) {
		g.drawImage(image, (int)x, (int)y, c);
	}

	
	// getter
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHieght() {
		return height;
	}

	//setter
	public void setX(double x) {
		this.x=(int)x;
	}
	public void setY(double y) {
		this.y=(int)y;
	}


	// method: createVolatileImage
	// parameters: the width, the height and whether or not there is transparency in an image, all ints
	// return type: it returns a blank volatile image so the images can be added to it
	// description: it create a new blank volatile image
	
	private VolatileImage createVolatileImage(int width, int height, int transparency) { GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration(); VolatileImage image = null;

	this.width=width;
	this.height=height;
	image = gc.createCompatibleVolatileImage(width, height, transparency);

	int valid = image.validate(gc);

	if (valid == VolatileImage.IMAGE_INCOMPATIBLE) { image = this.createVolatileImage(width, height, transparency); return image; }

	return image; }
	
	
	// method: loadFromFile
	// parameters: The url of an image imageURL
	// return type: it returns a volatile image (an image that renders faster)
	// description: it gets a volatile image from a file path
	
	public VolatileImage loadFromFile(URL imageURL) throws IOException {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		
		BufferedImage bimage = ImageIO.read(imageURL);
		
		VolatileImage vimage = createVolatileImage(bimage.getWidth(), bimage.getHeight(), Transparency.TRANSLUCENT);
		
		Graphics2D g = null;

		try { 
			g = vimage.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.setColor(Color.black); 
			g.clearRect(0, 0, vimage.getWidth(), vimage.getHeight());
			g.drawImage(bimage, 0, 0, bimage.getWidth(), bimage.getHeight(), 0, 0, bimage.getWidth(),bimage.getHeight(), null);
		}
		finally { 
			g.dispose(); 
		}
	
		return vimage;
	}
}
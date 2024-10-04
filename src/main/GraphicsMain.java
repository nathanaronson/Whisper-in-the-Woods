package main;
// Class: GraphicsMain
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class contains the main method for this project. You shouldn't modify this class.
//              This class must be xselected when you run your project.
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javazoom.jl.player.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class GraphicsMain extends JFrame{

	private static MyAudioPlayer thePlayer;

	public static void main(String[] args) throws IOException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thePlayer = new MyAudioPlayer(".//sounds//Woodland Wander.mp3", true);
					thePlayer.start();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();


		GraphicsMain window = new GraphicsMain();
		window.setSize(1280, 720);
		JPanel p = new JPanel();
		p.setBackground(Color.black);
		JButton start = new JButton("Start Game");
		
		

		ClassLoader cldr = GraphicsMain.class.getClassLoader();
		String imagePath = "main/images/background/start_screen_example.png";
		URL imageURL = cldr.getResource(imagePath);
		Image tempImage = ImageIO.read(imageURL);
		tempImage=tempImage.getScaledInstance(tempImage.getWidth(null)/2, tempImage.getHeight(null)/2, Image.SCALE_SMOOTH);
		JLabel image = new JLabel(new ImageIcon(tempImage));
		image.add(start);
		
		JButton help = new JButton("LVL 1 - Super Jump: right, left, right, up, LVL 2 - Dash: right, left, right, down LVL 3 - Lightning: up, up, down"); 
		image.add(help);
		
		p.add(image);
		
		

		start.setBounds(650,350,200,100);  
		start.setFont(new Font("Arial", Font.PLAIN, 30));
		help.setBounds(0,480,940,60);  
		help.setFont(new Font("Arial", Font.PLAIN, 12));
		help.setBackground(Color.BLUE);
		help.setOpaque(true);
		start.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				try {
					GraphicsPanel temp = new GraphicsPanel();
					temp.setBackground(Color.black);
					p.add(temp);  
					temp.start();
					window.setSize(1280, 720);
					p.remove(image);
					p.revalidate();
					p.repaint();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}  
		});  




		window.setTitle("The Woodland Wanderer");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(p);
		window.getContentPane().setBackground(Color.black);


		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}

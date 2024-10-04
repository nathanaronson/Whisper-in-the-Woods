package main;
import java.awt.Color;
// Class: Scene
// Written by: Woodland Wanderer Dev Team
// Date: 1/20/2022
// Description: This class consists of the implementation of scene.
import java.awt.Component;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Scene {

	// instance variables
	private Background background1;
	private Background background2;
	private ArrayList<GameObject> blocks;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private PlayerCharacter player;
	private double cameraX;
	private double cameraY;
	private boolean previous;
	private int startingX;
	private int startingY;
	private int endX1;
	private int endY1;
	private int endX2;
	private int endY2;

	// packed constructor
	public Scene(int startingX, int startingY, int endX1, int endY1, int endX2, int endY2, ArrayList<GameObject> blocks, ArrayList<Enemy> enemies, String backgroundPath) throws IOException {
		
		this.background1 = new Background(0, backgroundPath);
		this.background2 = new Background(0, backgroundPath);
		this.blocks = blocks;
		this.enemies = enemies;
		this.previous = true;
		this.player = new PlayerCharacter(startingX, startingY, "player");
		this.projectiles = new ArrayList <Projectile>();
		cameraX = player.getGameObject().x - 600;
		cameraY = 0;
		this.endX1 = endX1;
		this.endX2 = endX2;
		this.endY1 = endY1;
		this.endY2 = endY2;
		this.startingX = startingX;
		this.startingY = startingY;
	}

	// getter
	public PlayerCharacter getPlayer() {
		return this.player;
	}

	// getter
	public Background getBackground1() {
		return background1;
	}
	
	// getter
	public Background getBackground2() {
		return background2;
	}

	// method: addEntity
	// parameters: xPosition, yPosition, name
	// return type: void
	// description: adds an entity
	public void addEntity(int x, int y, String name) {
		blocks.add(new GameObject(x, y, name));
	}

	// method: Component c and Graphics g
	// parameters: void
	// return type: none
	// description: draws scene relative to player location
	public void draw(Component c, Graphics g) {
		background1.draw(c, g);
		background2.draw(c, g);
		for(GameObject e: blocks) {
			e.draw(cameraX, cameraY, c, g);
		}
		for(Enemy e: enemies) {
			e.getGameObject().draw(cameraX, cameraY, c, g);
		}
		for(Projectile e: projectiles) {
			e.getGameObject().draw(cameraX, cameraY, c, g);
		}
		player.getGameObject().draw(cameraX, cameraY, c, g);
	}

	// method: updatePositions
	// parameters: listOfLastPresses, game, and sceneNumber
	// return type: void
	// description: updates the positions of everything (also includes special abilities based off of movement)
	public void updatePositions(ArrayList<String>listOflastPresses, Game game, int sceneNumber) throws IOException {
		for(Enemy e: enemies)
			e.updatePosition();

		int pr = 0;
		while(pr < projectiles.size()) {
			if(projectiles.get(pr).updatePosition()) {
				pr += 1;
			} else {
				projectiles.remove(pr);
			}
		}

		player.touchingGround=false;
		if(listOflastPresses.size() > 3 && sceneNumber >= 0) {
			if(listOflastPresses.get(listOflastPresses.size() - 1) == "down" && listOflastPresses.get(listOflastPresses.size() - 2) == "up" &&  listOflastPresses.get(listOflastPresses.size() - 3) == "right"  &&  listOflastPresses.get(listOflastPresses.size() - 4) == "left") {
				player.getGameObject().yVelocity =- 6;
				listOflastPresses.clear();
			}
		}
		if(listOflastPresses.size()>3 && sceneNumber>=0) {
			if(listOflastPresses.get(listOflastPresses.size()-1)=="down" && listOflastPresses.get(listOflastPresses.size()-2)=="up" &&  listOflastPresses.get(listOflastPresses.size()-3)=="left"  &&  listOflastPresses.get(listOflastPresses.size()-4)=="right") {
				player.getGameObject().yVelocity=-6;
				listOflastPresses.clear();
			}
		}
		if(listOflastPresses.size()>3 && sceneNumber>=1) {
			if(listOflastPresses.get(listOflastPresses.size()-1)=="down" && listOflastPresses.get(listOflastPresses.size()-2)=="right" &&  listOflastPresses.get(listOflastPresses.size()-3)=="left"  &&  listOflastPresses.get(listOflastPresses.size()-4)=="right") {
				player.getGameObject().xVelocity+=10;
				player.canJump=true;
				player.gravity=false;
				listOflastPresses.clear();
			}
		}
		if(listOflastPresses.size()>3  && sceneNumber>=1) {
			if(listOflastPresses.get(listOflastPresses.size()-1)=="down" && listOflastPresses.get(listOflastPresses.size()-2)=="left" &&  listOflastPresses.get(listOflastPresses.size()-3)=="right"  &&  listOflastPresses.get(listOflastPresses.size()-4)=="left") {
				player.getGameObject().xVelocity-=10;
				player.canJump=true;
				player.gravity=false;
				listOflastPresses.clear();
			}
		}
//		if(listOflastPresses.size()>3 && sceneNumber>=2) {
//			if(listOflastPresses.get(listOflastPresses.size()-1)=="down" && listOflastPresses.get(listOflastPresses.size()-2)=="right" &&  listOflastPresses.get(listOflastPresses.size()-3)=="up"  &&  listOflastPresses.get(listOflastPresses.size()-4)=="down") {
//				projectiles.add(new Projectile(player.getGameObject().x, player.getGameObject().y, player.getGameObject().x+600 ,player.getGameObject().y,10,"projectile"));
//				listOflastPresses.clear();
//			}
//		}
//		if(listOflastPresses.size()>3  && sceneNumber>=2) {
//			if(listOflastPresses.get(listOflastPresses.size()-1)=="down" && listOflastPresses.get(listOflastPresses.size()-2)=="left" &&  listOflastPresses.get(listOflastPresses.size()-3)=="up"  &&  listOflastPresses.get(listOflastPresses.size()-4)=="down") {
//				projectiles.add(new Projectile(player.getGameObject().x, player.getGameObject().y, player.getGameObject().x-600 ,player.getGameObject().y,10,"projectile"));
//				listOflastPresses.clear();
//			}
//		}
		if(listOflastPresses.size()>2  && sceneNumber>=2) {
			if(listOflastPresses.get(listOflastPresses.size()-1)=="down" && listOflastPresses.get(listOflastPresses.size()-2)=="up" &&  listOflastPresses.get(listOflastPresses.size()-3)=="up") {
				projectiles.add(new Projectile(player.getGameObject().x-30, player.getGameObject().y-1000, player.getGameObject().x-30 ,player.getGameObject().y+200,10,"projectile"));
				listOflastPresses.clear();
				listOflastPresses.clear();
			}
		}


		player.getGameObject().x += player.getGameObject().xVelocity;
		for(GameObject e: blocks){
			if(e.name!="player") {
				if(player.getGameObject().collide(e)[0]!=0)
					player.gravity=true;
				player.getGameObject().x-=player.getGameObject().collide(e)[0]+.01*Math.signum(player.getGameObject().collide(e)[0]);
			}
		}

		if(player.gravity) {
			player.getGameObject().y += player.getGameObject().yVelocity;
			for(GameObject e: blocks){
				if(e.name!="player") {
					if(player.getGameObject().collide(e)[1]>0){
						player.touchingGround=true;
					}else if(player.getGameObject().collide(e)[1]<0){
						player.getGameObject().yVelocity=0;
					}
					//System.out.println(e.name + player.getGameObject().collide(e)[1]+1*Math.signum(player.getGameObject().collide(e)[1]));
					player.getGameObject().y-=player.getGameObject().collide(e)[1]+.01*Math.signum(player.getGameObject().collide(e)[1]);
				}
			}
		}

		for(Enemy e: enemies) {
			if(player.getGameObject().collide(e.getGameObject())[1]<0) {
				this.player=new PlayerCharacter(startingX,startingY,"player");
				game.player=this.player;
			}
		}

		for(Projectile p: projectiles) {
			int er=0;
			while(er<enemies.size()) {
				if(enemies.get(er).getGameObject().collide(p.getGameObject())[1]<0) {
					enemies.remove(er);
					break;
				}else {
					er+=1;
				}
			}

		}



		if(player.getGameObject().y<500) {
			if(player.getGameObject().yVelocity<20 && !player.touchingGround && player.gravity) {
				player.getGameObject().yVelocity+=.1;
			}
		}else {
			player.getGameObject().y=300;
			player.getGameObject().yVelocity=0;
			player.touchingGround=true;
		}
		if(player.getGameObject().x>=1300) {
			cameraX=1300-600;
		}else if(player.getGameObject().x<-800) {
			cameraX=-800-600;
		}else {
			cameraX=player.getGameObject().x-600;
		}


		if(player.getGameObject().y>125) {
			cameraY=-175;
		}else {
			cameraY=player.getGameObject().y-300;
		}
		if(player.touchingGround) {
			player.canJump=true;
		}
		if(previous && !player.touchingGround && player.getGameObject().yVelocity>-1) {
			player.getGameObject().yVelocity=-1;
		}
		previous=player.touchingGround;

		if(player.getGameObject().x>endX1 && player.getGameObject().x<endX2 && player.getGameObject().y>endY1 && player.getGameObject().y<endY2) {
			game.nextScene();
		}

		if(player.getGameObject().xVelocity == 0 && (player.getGameObject().yVelocity == 0||player.touchingGround)) {
			player.getGameObject().currentAnimation="still";
		}else if (player.getGameObject().yVelocity == 0||player.touchingGround) {
			player.getGameObject().currentAnimation="Walk";
		}else {
			player.getGameObject().currentAnimation="Jump";
		}
		background1.setX(-1-cameraX/2);
		background2.setX(0-background1.getWidth()-cameraX/2);
		
		background1.setY(-630-cameraY/2);
		background2.setY(-630-cameraY/2);
	}
}
package main;
// Class: Enemy
// Written by: Woodland Wanderer Dev Team
// Date: 1/20/2022
// Description: This class consists of the implementation of an enemy.

import java.io.IOException;

public class Enemy {

	// instance variables
	protected GameObject object;
	protected ImageResource imageResource;
	protected double[][]movementCoords = new double[2][2];
	protected boolean direction;
	protected double speed;

	// packed constructor
	public Enemy(double xStart, double yStart, double xEnd, double yEnd, double speed, String name) throws IOException {
		this.object = new GameObject(xStart, yStart, 40, 40, "images/", new String[]{"tile0"}, new int[]{19}, new int[]{3}, 3, name);
		movementCoords = new double[][] {{xStart, yStart}, {xEnd, yEnd}};
		direction=true;
		this.speed=speed;
	}

	// method: updatePosition
	// parameters: none
	// return type: void
	// description: updates position of the enemy
	public void updatePosition() {
		int i = (direction ? 1 : 0);
		double magnitude = Math.sqrt((movementCoords[i][0] - object.x) * (movementCoords[i][0] - object.x) + (movementCoords[i][1] - object.y) * (movementCoords[i][1] - object.y));
		if(magnitude > 2) {
			object.x += speed * (movementCoords[i][0] - object.x) / (magnitude);
			object.y += speed * (movementCoords[i][1] - object.y) / magnitude;
		} else {
			direction = !direction;
		}
	}

	// getter
	public GameObject getGameObject() {
		return object;
	}
}
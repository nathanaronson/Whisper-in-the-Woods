package main;
// Class: Projectile
// Written by: Woodland Wanderer Dev Team
// Date: 1/20/2022
// Description: This class consists of the implementation for projectiles.

import java.io.IOException;

public class Projectile {

	// instance variables
	protected GameObject object;
	protected ImageResource imageResource;
	protected double[] movementCoords = new double[2];
	protected double speed;

	// packed constructor
	public Projectile(double xStart, double yStart, double xEnd, double yEnd, double speed, String name) throws IOException {
		this.object = new GameObject(xStart, yStart, 80, 80, "images/", new String[]{"lightning-"}, new int[]{11}, new int[]{2}, 1, name);
		movementCoords = new double[]{xEnd, yEnd};
		this.speed = speed;
	}

	// method: updatePosition
	// parameters: none
	// return type: boolean
	// description: checks if you are able to update the position
	public boolean updatePosition() {
		double magnitude = Math.sqrt((movementCoords[0] - object.x) * (movementCoords[0] - object.x) + (movementCoords[1] - object.y) * (movementCoords[1] - object.y));
		if(magnitude > 2) {
			object.x += speed * (movementCoords[0] - object.x) / (magnitude);
			object.y += speed * (movementCoords[1] - object.y) / magnitude;
			return true;
		} else {
			return false;
		}
	}

	// getter
	public GameObject getGameObject() {
		return object;
	}
}
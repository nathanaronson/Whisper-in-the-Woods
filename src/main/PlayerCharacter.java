package main;

import java.io.IOException;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

// Class: PlayerCharacter
// Written by: Woodland Wanderer Dev Team
// Date: 1/20/2022
// Description: This class contains the implementation for a player.
public class PlayerCharacter {

	// instance variables
	protected GameObject object;
	protected int jumpCounter;
	protected boolean state;
	protected boolean touchingGround,canJump;
	protected boolean gravity;
	MidiChannel channel;
	Synthesizer synth;

	// packed constructor
	public PlayerCharacter(double x, double y, String name) throws IOException{
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		channel = synth.getChannels()[0];
		gravity = true;
		touchingGround = true;
		canJump = true;
		this.object = new GameObject(x, y - 100, 52, 70, "images/", new String[]{"Jump","Walk", "still","flippedJump","flippedWalk", "flippedstill"}, new int[]{5, 4, 1, 5, 4, 1}, new int[]{1, 1, 1, 1, 1, 1}, 12, name);
		jumpCounter = -1;
	}

	// method: move
	// parameters: movement
	// return type: void
	// description: checks for and updates movement of player
	public void move(String movement) {
		
		if(movement == "left") {
			channel.noteOn(70, 90);
			gravity = true;
			object.flipped = true;
			if(object.xVelocity > -3)
				object.xVelocity = -3;
		}else {
			channel.noteOff(70);
		}
		
		if(movement == "right") {
			channel.noteOn(60, 90);
			gravity = true;
			object.flipped = false;
			if(object.xVelocity < 3)
				object.xVelocity = 3;
		}else {
			channel.noteOff(60);
		}
		
		if(movement == "up") {
			channel.noteOn(80, 90);
			gravity = true;
			if(canJump) {
				if(object.yVelocity < -5);
				object.yVelocity = -5;
				canJump = false;
			}
		}else {
			channel.noteOff(75);
		}
		
		if(movement == "down") {
			channel.noteOn(50, 90);

		}
		else {
			channel.noteOff(50);
		}
	}

	// method: friction
	// parameters: movement
	// return type: none
	// description: calculates and updates friction
	public void friction(String movement) {
		if(movement == "left") {
			object.xVelocity = 0;
			channel.noteOff(70);
		}
		if(movement == "right") {
			object.xVelocity = 0;
			channel.noteOff(60);
		}
		if(movement == "up") {
			channel.noteOff(80);

		}
		if(movement == "down") {
			channel.noteOff(50);

		}
	}

	// getter
	public GameObject getGameObject() {
		return object;
	}
}
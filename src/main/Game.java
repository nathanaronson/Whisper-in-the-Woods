package main;
// Class: Game
// Written by: Woodland Wanderer Dev Team
// Date: 1/20/2022
// Description: This class consists of the implementation of a game.
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game {

	// instance variables
	protected PlayerCharacter player;
	private Scene scene1,scene2,scene3,currentScene;
	private ArrayList<Scene> scenes;
	private int currentSceneNumber; 

	// default constructor 
	public Game() throws IOException {
		currentSceneNumber = 0;
		scenes = new ArrayList<>();
		
		ArrayList<GameObject> scene1Blocks = new ArrayList<>();
		scene1Blocks.add(new GameObject(-2000, 400, 4500, 200, "block"));
		scene1Blocks.add(new GameObject(50, 200, 80, 40, "block"));
		scene1Blocks.add(new GameObject(100, 125, 80, 40,  "block"));
		scene1Blocks.add(new GameObject(200, 100, 80, 40, "block"));
		scene1Blocks.add(new GameObject(500, 75, 80, 40, "block"));
		scene1Blocks.add(new GameObject(550, 25, 80, 40, "block"));
		scene1Blocks.add(new GameObject(850, 50, 80, 40, "block"));
		scene1Blocks.add(new GameObject(950, -25, 150, 40, "block"));
		scene1Blocks.add(new GameObject(1150, -275, 100, 40, "block"));
		scene1Blocks.add(new GameObject(1750, -275, 100, 40, "block"));
		scene1Blocks.add(new GameObject(1810, -275, 40, 1500, "block"));
		scene1Blocks.add(new GameObject(-1600, -275, 400, 1500, "block"));
		scene1Blocks.add(new GameObject(2025, -1070, 400, 1500, "block"));

		ArrayList<Enemy> scene1Enemies = new ArrayList<>();
		scene1Enemies.add(new Enemy(0, 0, 200, 200, 1, "enemy"));
		scene1 = new Scene(0, 300, 1850, 220, 2005, 280, scene1Blocks, scene1Enemies, "main/images/background/background.png");

		ArrayList<GameObject> scene2Blocks = new ArrayList<>();
		scene2Blocks.add(new GameObject(-2000, 400, 4000, 200, "block"));
		scene2Blocks.add(new GameObject(2025, -1000, 4000, 5500, "block"));
		scene2Blocks.add(new GameObject(-1400, -275, 40, 4500, "block"));
		scene2Blocks.add(new GameObject(240, -200, 80, 40, "block"));
		scene2Blocks.add(new GameObject(1800,-200, 1500, 40,"block"));
		
		
		for(int x = 0; x < 8; x++)
			scene2Blocks.add(new GameObject(-1000 + x * 150, 200 - (50 * x), 80, 40, "block"));
		for(int x = 0; x < 6; x++)
			scene2Blocks.add(new GameObject(200 + x * 200, -200, 80, 40, "block"));
		
		ArrayList<GameObject> scene3Blocks = new ArrayList<>();
		scene3Blocks.add(new GameObject(-2000, 400, 4500, 200, "block"));
		scene3Blocks.add(new GameObject(2025, -1070, 400, 1500, "block"));
		scene3Blocks.add(new GameObject(-1400, -275, 40, 4500, "block"));
		scene3Blocks.add(new GameObject(50, 250, 80, 40, "block"));
		scene3Blocks.add(new GameObject(150, 150, 80, 40, "block"));
		scene3Blocks.add(new GameObject(320, 0, 80, 40, "block"));
		scene3Blocks.add(new GameObject(500, 0, 80, 40, "block"));
		scene3Blocks.add(new GameObject(600, 25, 80, 40, "block"));
		scene3Blocks.add(new GameObject(950, 75, 80, 40, "block"));
		scene3Blocks.add(new GameObject(1650, 75, 80, 40, "block"));
		scene3Blocks.add(new GameObject(1825, 0, 200, 40,"block"));


		ArrayList<Enemy> scene2Enemies = new ArrayList<>();
		ArrayList<Enemy> scene3Enemies = new ArrayList<>();
		scene3Enemies.add(new Enemy(0, 0, 200, 200, 1, "enemy"));
		scene3Enemies.add(new Enemy(300, 0, 500, 200, 1, "enemy"));
		scene3Enemies.add(new Enemy(1000, 0, 1600, 0, 1, "enemy"));
		

		scene2 = new Scene(0, 300, 1885, -280, 2025, -200, scene2Blocks, scene2Enemies, "main/images/background/background.png");
		scene3 = new Scene(-200, 300, 1850, -3000, 2005, -3000, scene3Blocks, scene3Enemies, "main/images/background/background.png");

		scenes.add(scene1);
		scenes.add(scene2);
		scenes.add(scene3);

		currentScene = scenes.get(0);
		player = currentScene.getPlayer();
	}

	// method: nextScene
	// parameters: none
	// return type: void
	// description: changes to the next scene
	public void nextScene() {
		currentSceneNumber += 1;
		if(currentSceneNumber<3) {
			this.currentScene = scenes.get(currentSceneNumber);
		}else {
			currentSceneNumber=0;
			this.currentScene = scenes.get(currentSceneNumber);
		}
		player = currentScene.getPlayer();
	}

	// method: update
	// parameters: list of last presses
	// return type: void
	// description: updates the scene
	public void update(ArrayList<String>listOflastPresses) throws IOException {
		currentScene.updatePositions(listOflastPresses, this, currentSceneNumber);
	}

	// method: playSound
	// parameters: fileName
	// return type: void
	// description: plays the audio
	public static void playSound(String fileName) {
		try {
			File url = new File(fileName);
			Clip clip = AudioSystem.getClip();

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.start();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// getter
	public Scene getScene() {
		return this.currentScene;
	}

	// getter
	public PlayerCharacter getPlayer() {
		return this.player;
	}
}
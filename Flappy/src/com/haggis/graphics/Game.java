package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Game {

	static Bird bird;
	static ArrayList<Pipe> pipes = new ArrayList<>();
	static ArrayList<Pipe> toRemove = new ArrayList<>();
	
	int frameCount = 0;
	static int score = 0;
	static int bestScore = 0;
	
	int pipeRate = 120;
	
	public void init() {
		pipes.clear();
		toRemove.clear();
       
		bird = new Bird();
		pipes.add(new Pipe());
	}

	public static void reset() {
		pipes.clear();      
		toRemove.clear();
		bird = new Bird();
		pipes.add(new Pipe());
		if(bestScore < score) bestScore = score;
		score = 0;
	}

	public void update() {
		frameCount++;

		for (Pipe p : pipes) {
			p.update();

			//p.collides(bird);
			

			if (p.x < -p.width) {
				toRemove.add(p);
			}
		}

		// Rmeove dead pipes
		for (Pipe p : toRemove) {
			pipes.remove(p);
		}
		toRemove.clear();

		if (frameCount % pipeRate == 0) {
			pipes.add(new Pipe());
	
		}

		bird.update(pipes);
		score++;
	}
	
	

	public void render(Graphics2D g) {

		bird.show(g);
		for (Pipe p : pipes) {
			p.show(g);
		}

		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 10, 25);
		g.drawString("Best Score " + bestScore, 10, 50);
	}

	// Constructor
	public Game() {
		init();
	}
	
	public void spacePressed() {
		Bird.displayNEATinfo = !Bird.displayNEATinfo;
	}

	//Commented oout for neuroevolution
//	public void spacePressed() {
//		bird.up();
//	}

}

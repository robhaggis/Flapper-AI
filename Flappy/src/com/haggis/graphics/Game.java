package com.haggis.graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Game {

	static Bird bird;
	static ArrayList<Pipe> pipes = new ArrayList<>();
	static ArrayList<Pipe> toRemove = new ArrayList<>();
	int pipeRate = 150;
	int frameCount = 0;

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
	}

	public void update() {
		frameCount++;

		

		for (Pipe p : pipes) {
			p.update();
			
			if(p.collides(bird)) {
			}
			
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
		
		bird.update();
	}

	public void render(Graphics2D g) {

		bird.show(g);
		for (Pipe p : pipes) {
			p.show(g);
		}

	}

	// Constructor
	public Game() {
		init();
	}

	public void spacePressed() {
		bird.up();
	}

}

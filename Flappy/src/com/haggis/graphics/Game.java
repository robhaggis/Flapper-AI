package com.haggis.graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Game {

	Bird bird;
	ArrayList<Pipe> pipes = new ArrayList<>();
	ArrayList<Pipe> toRemove = new ArrayList<>();
	int pipeRate = 150;
	int frameCount = 0;

	public void init() {
		bird = new Bird();
		pipes.add(new Pipe());
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

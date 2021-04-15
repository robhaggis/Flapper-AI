package com.haggis.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.haggis.utils.Vector;

public class Game {
		
	final int TOTAL = 100;

	Bird b;
	int wid = 400;
	int rez = 20;
	int score = 0;
	int bestScore;
	boolean jumping = false;
	static Vector gravity = new Vector(0f, 0.5f);
	ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	ArrayList<Bird> birds = new ArrayList<Bird>();
	int frameCount;

	void init() {
		pipes.clear();
		birds.clear();
		
		for(int i=0;i<TOTAL;i++) {
			birds.add(new Bird());
		}	
		pipes.add(new Pipe());
		if(score> bestScore) bestScore = score;
		score = 0;
		frameCount = 0;
	}

	void update() {
		
		//Spawn new pipes
		if (frameCount % 75 == 0) {
			pipes.add(new Pipe());
		}
		
		//Update pipes and collision check
		for (int i = pipes.size() - 1; i >= 0; i--) {
			pipes.get(i).update();

			for(int j = birds.size()-1; j>=0;j--) {
				if (pipes.get(i).hits(birds.get(j))) {
					birds.remove(j);
				}
			}
			
			
			if (pipes.get(i).x < -pipes.get(i).w) {
				pipes.remove(i);
			}
			score++;
		}
		
		//Update birds
		for(Bird b: birds) {
			b.update();
			b.think(pipes);
		}
		
		
		if(birds.size()==0) {
			init();
			return;
		}
		frameCount++;
	}
	


	
	//Commented out for NEAT
//	void flap() {
//		b.applyForce(new Vector(0,-10));
//	}
	
	
	
	void render(Graphics2D g) {
		
		for (Pipe p : pipes) {
			p.render(g);
		}
		g.setColor(Color.black);
		g.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
		g.drawString("Score: " + score, Window.WIDTH / 2, 50);
		g.drawString("Best: " + bestScore, Window.WIDTH / 2, 75);
		
		for(Bird b: birds) {
			b.render(g);
		}
	}
	
	public Game() {
		init();
	}
}
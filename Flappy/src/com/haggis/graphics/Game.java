package com.haggis.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Game {

	static ArrayList<Bird> population;
	static ArrayList<Bird> saved;
	ArrayList<Pipe> pipes;
	static float TOTAL;
	static int Gens;
	float counter, bestScore;
	Pipe p;

	void init() {
		TOTAL = 500;
		pipes = new ArrayList<Pipe>();
		population = new ArrayList<Bird>();
		saved = new ArrayList<Bird>();
		for (int i = 0; i < TOTAL; i++) {
			population.add(new Bird());
		}
	}

	void update() {

		// Spawn new pipes
		if (counter % 200 == 0) {
			pipes.add(new Pipe());
		}

		// Update pipes and collision check
		for (int i = pipes.size() - 1; i >= 0; i--) {
			Pipe p = pipes.get(i);
			p.update();
			if (p.offscreen()) {
				pipes.remove(i);
			}

			for (int j = population.size() - 1; j >= 0; j--) {
				Bird b = population.get(j);
				if (p.hit(b) || b.dead()) {
					saved.add(b);
					population.remove(j);
				}
			}
		}

		if (population.size() == 0) {
			
			if(counter > bestScore)bestScore = counter;
			counter = 0;
			GA.nextGeneration();
			pipes.clear();
			pipes.add(new Pipe());
		}

		for (Bird b : population) {
			b.update();
			b.think(pipes);
		}

//		  for (int i = pipes.size()-1; i >= 0; i--)
//		  {
//		    Pipe p = pipes.get(i);;
//		  }

		counter++;
	}

	// Commented out for NEAT
//	void flap() {
//		b.applyForce(new Vector(0,-10));
//	}

	void render(Graphics2D g) {

		for (Pipe p : pipes) {
			p.render(g);
		}

		// Draw NEAT info
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Window.WIDTH - 150, 0, Window.WIDTH, 150);
		g.setColor(Color.white);
		g.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
		int textX = Window.WIDTH - 140;
		g.drawString("Dist: " + "" + counter, textX, 10);
		g.drawString("Best: " + "" + bestScore, textX, 25);
		g.drawString("Generation: " + Gens, textX, 40);
		g.drawString("BirdCount:" + population.size(), textX, 55);
		g.drawString("DeadBirds:" + saved.size(), textX, 70);

		for (Bird b : population) {
			b.render(g);
		}
	}

	public Game() {
		init();
	}
}
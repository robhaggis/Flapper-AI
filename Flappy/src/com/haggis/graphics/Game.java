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
	static int Gens = 1;
	float counter, bestScore;
	Pipe p;

	int cycles = 1;

	void init() {
		TOTAL = 300;
		pipes = new ArrayList<Pipe>();
		population = new ArrayList<Bird>();
		saved = new ArrayList<Bird>();
		for (int i = 0; i < TOTAL; i++) {
			population.add(new Bird());
		}
	}
	
	void adjustSpeed() {
		if(cycles == 100) cycles = 1;
		else if(cycles == 1) cycles = 10;
		else if (cycles ==10) cycles=100;
	}

	void update() {

		for (int n = 0; n < cycles; n++) {
			// Spawn new pipes
			if (counter % 150 == 0) {
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

				if (counter > bestScore) {
					bestScore = counter;
				}
				counter = 0;
				GA.nextGeneration();
				pipes.clear();
				pipes.add(new Pipe());
				Gens++;
			}

			for (Bird b : population) {
				b.update();
				b.think(pipes);
			}
			counter++;

		}

	}

	void render(Graphics2D g) {

		for (Pipe p : pipes) {
			p.render(g);
		}

		// Draw NEAT info
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Window.WIDTH - 150, 0, Window.WIDTH, 180);
		g.setColor(Color.white);
		g.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
		int textX = Window.WIDTH - 140;
		g.drawString("Dist: " + "" + counter, textX, 50);
		g.drawString("Best: " + "" + bestScore, textX, 70);
		g.drawString("Generation: " + Gens, textX, 90);
		g.drawString("BirdCount:" + population.size(), textX, 110);
		g.drawString("DeadBirds:" + saved.size(), textX, 130);
		g.drawString("Speed: "+cycles+"X", textX, 150);
		g.drawString("(Click to change speed)", textX, 170);
		for (Bird b : population) {
			b.render(g);
		}

	}

	public Game() {
		init();
	}
}
package com.haggis.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.haggis.utils.Vector;

public class Game {

	Bird b;
	int wid = 400;
	int rez = 20;
	int score = 0;
	int bestScore;
	boolean jumping = false;
	static Vector gravity = new Vector(0f, 0.5f);
	ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	int frameCount = 0;

	void init() {
		pipes.clear();
		b = new Bird();
		pipes.add(new Pipe());
		if(score> bestScore) bestScore = score;
		score = 0;
		frameCount = 0;
	}

	void update() {

		if (frameCount % 75 == 0) {
			pipes.add(new Pipe());
		}

		b.update();
		boolean safe = true;
		
		for (int i = pipes.size() - 1; i >= 0; i--) {
			Pipe p = pipes.get(i);
			p.update();

			if (p.hits(b)) {
				init();
				return;
			}
			
			if (p.x < -p.w) {
				pipes.remove(i);
			}

			score++;
		}
		frameCount++;
	}
	


	
	void flap() {
		b.applyForce(new Vector(0,-10));
	}
	
	void render(Graphics2D g) {
		b.show(g);
		for (Pipe p : pipes) {
			p.render(g);
		}
		g.setColor(new Color(0, 255, 0));
		Font font = new Font("Sans-Serif", Font.PLAIN, 16);
		g.setFont(font);
		g.drawString("Score: " + score, Window.WIDTH / 2, 50);
		g.drawString("Score: " + bestScore, Window.WIDTH / 2, 75);
	}
	
	public Game() {
		init();
	}
}
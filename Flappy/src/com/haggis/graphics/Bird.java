package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Bird {
	int x, y;
	static int size;
	int lift;
	Color col;
	final int GRAVITY = 1;

	int velocity = 0;

	boolean dead = false;

	NN brain;
	static double[] output = new double[1];
	Pipe closest = null;

	static boolean displayNEATinfo = true;

	public Bird() {
		x = 64;
		y = Window.HEIGHT / 2;
		size = 32;
		lift = -3;
		col = Color.green;

		brain = new NN(4, 4, 1);
	}

	void think(ArrayList<Pipe> pipes) {

		closest = pipes.get(0);
		double closestDist = 100000000;

		for (int i = 1; i < pipes.size(); i++) {
			double d = pipes.get(i).x - this.x;
			if (d < closestDist && d > 0) {
				closest = pipes.get(i);
				closestDist = d;
			}
		}

		double[] inputs = new double[4];
//		inputs[0] = this.y / Window.HEIGHT;
//		inputs[1] = closest.top/Window.HEIGHT;
//		inputs[2] = closest.bottom/Window.HEIGHT;
//		inputs[3] = closest.x/Window.WIDTH;

		inputs[0] = this.y;
		inputs[1] = closest.top;
		inputs[2] = closest.bottom;
		inputs[3] = closest.x;

		output = brain.calculate(inputs);
		if (output[0] > 0.5) {
			this.up();
		}
	}

	void update(ArrayList<Pipe> pipes) {
		think(pipes);

		if (!dead) {
			velocity += GRAVITY;
			y += velocity;

			if (y >= Window.HEIGHT - size) {
				y = Window.HEIGHT - size;
				velocity = 0;
			}

			if (y <= 0) {
				y = 0;
				velocity = 0;
			}
		} else {
			Game.reset();
		}

	}

	void gameOver() {
		dead = true;
	}

	void up() {
		velocity += lift;
		velocity *= 0.9;
	}

	void show(Graphics2D g) {
		
		//Draw NEAT info   
		if (displayNEATinfo) {
			g.setColor(new Color(255, 0, 0));

			// Height
			g.drawString("Y:" + this.y, this.x, this.y + 48);

			// Connections to pipe
			if (closest != null) {
				g.drawLine(x+(size/2), y+(size/2), closest.x, closest.top);
				g.drawLine(x+(size/2), y+(size/2), closest.x, closest.bottom);
			}

			g.drawString("Thinking..." + Bird.output[0],this.x, this.y - 8);
		}

		
		//Draw Bird
		g.setColor(col);
		g.fillOval(x, y, size, size);
		
	}
}

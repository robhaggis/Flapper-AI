package com.haggis.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.haggis.utils.Rand;
import com.haggis.utils.Vector;

class Bird {
	Vector pos, vel, acc;
	float r = 16;
	Color c;

	NeuralNetwork brain = new NeuralNetwork(4, 4, 2);
	float[] inputs = new float[4];
	float[] outputs = new float[2];
	Pipe closest = null;
	int score;
	int fitness;

	Bird() {
		pos = new Vector(50, Window.HEIGHT / 2);
		vel = new Vector(0, 0);
		acc = new Vector();

		int r = Rand.randomRange(50, 200);
		int g = Rand.randomRange(50, 200);
		int b = Rand.randomRange(50, 200);
		c = new Color(r, g, b, 100);
	}

	void applyForce(Vector force) {
		acc.add(force);
	}

	void think(ArrayList<Pipe> pipes) {
		
		//Calculate closest pipe
		closest = pipes.get(0);
		double closestD = Double.POSITIVE_INFINITY;
		for(Pipe p : pipes) {
			double d = p.x - pos.x;
			if(d < closestD && d >0) {
				closest = p;
				closestD = d;
			}
		}
			
		//Update inputs
		inputs[0] = pos.y;
		inputs[1] = closest.top;
		inputs[1] = closest.bottom;
		inputs[1] = closest.x;

		//Calculate output
		outputs = brain.feedForward(inputs);
		if(outputs[1] > outputs[0]) {
			this.applyForce(new Vector(0,-10));
		}
	}

	void update() {
		score++;
		
		applyForce(Game.gravity);
		pos.add(vel);
		vel.add(acc);
		vel.limit(4);
		acc.multiply(0);

		if (pos.y + r * 2 >= Window.HEIGHT) {
			pos.y = Window.HEIGHT - r * 2 - 1;
			vel.multiply(0);
		}

		if (pos.y <= 0) {
			pos.y = 1;
			vel.multiply(0);
		}
	}

	void render(Graphics2D g) {
		//Draw strokw outline
		g.setColor(new Color(0,0,0,100));
		int sw = 2;
		g.fillOval((int) pos.x-sw, (int) pos.y-sw, (int) (r * 2)+sw, (int) (r * 2)+sw);
		
		//Draw Body
		g.setColor(c);
		g.fillOval((int) pos.x, (int) pos.y, (int) r * 2, (int) r * 2);


	}
}
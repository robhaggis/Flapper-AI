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

	NN brain = new NN(4, 4, 1);
	double[] inputs = new double[4];
	double[] outputs = new double[1];
	Pipe closest = null;

	Bird() {
		pos = new Vector(50, Window.HEIGHT / 2);
		vel = new Vector(0, 0);
		acc = new Vector();

		int r = Rand.randomRange(50, 200);
		int g = Rand.randomRange(50, 200);
		int b = Rand.randomRange(50, 200);
		c = new Color(r, g, b, 255);
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
		outputs = brain.calculate(inputs);
		if(outputs[0] > 0.5) {
			this.applyForce(new Vector(0,-10));
		}
	}

	void update() {
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

	void show(Graphics2D g) {
		g.setColor(c);
		g.fillOval((int) pos.x, (int) pos.y, (int) r * 2, (int) r * 2);

		g.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
		if (outputs[0] > 0) {
			String o = Double.toString(outputs[0]);
			g.drawString(o.substring(0, 5), pos.x, pos.y + 2 * r + 10);
		}

	}
}
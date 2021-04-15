package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import com.haggis.utils.Vector;

class Bird {
	Vector pos,vel,acc;
	float r = 16;

	Bird() {
		pos = new Vector(50, Window.HEIGHT / 2);
		vel = new Vector(0, 0);
		acc = new Vector();
	}

	void applyForce(Vector force) {
		acc.add(force);
	}
	
	void update() {		
		applyForce(Game.gravity);
		pos.add(vel);
		vel.add(acc);
		vel.limit(4);
		acc.multiply(0);
		if (pos.y +r*2>= Window.HEIGHT) {
			pos.y = Window.HEIGHT-r*2-1;
			vel.multiply(0);
		}
	}

	void show(Graphics2D g) {
		g.setColor(Color.white);
		g.fillOval((int)pos.x,(int) pos.y, (int)r * 2, (int)r * 2);
	}
}
package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bird {
	int x,y;
	static int size;
	int lift;
	Color col;
	final int GRAVITY = 1;

	int velocity = 0;

	boolean dead = false;
	
	NN brain;
	
	public Bird() {
		x = 64;
		y = Window.HEIGHT/2;
		size = 32;
		lift = -3;	
		col = Color.green;
	}

	void update() {

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
		}else
		{
			Game.reset();
		}

	}

	void gameOver() {
		dead = true;
	}

	void up() {
		velocity += lift;
		velocity*=0.9;
	}

	void show(Graphics2D g) {
		g.setColor(col);
		g.fillOval(x, y, size, size);
	}
}

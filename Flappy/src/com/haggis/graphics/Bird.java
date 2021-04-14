package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bird {
	int x = 64;
	int y = Window.HEIGHT / 2;
	int size = 32;
	int lift = -3;
	Color col = Color.white;

	int gravity = 1;

	int velocity = 0;
	int maxSpeed = 3;

	boolean dead = false;

	void update() {

		if (!dead) {
			velocity += gravity;
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

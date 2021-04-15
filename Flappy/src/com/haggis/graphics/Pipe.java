package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Pipe {

	Random rng = new Random(System.currentTimeMillis());

	int top;
	int bottom;
	int speed;
	int x;
	int width;


	Color col = Color.WHITE;

	public Pipe() {
		speed = 2;
		x = Window.WIDTH;
		width = 30;
		
		top = (int) 100+(rng.nextInt((Window.HEIGHT / 2))-(Bird.size*2))-100;
		bottom = (int) (top + (Bird.size*2))+100;
	}

	void update() {
		x -= speed;
	}

	boolean collides(Bird b) {

		if (b.y < top || b.y + Bird.size > Window.HEIGHT - bottom) {
			if (b.x + Bird.size > x && b.x < x + width) {
				b.gameOver();
				return true;
			}
		}
		return false;

	}

	void show(Graphics2D g) {
		g.setColor(col);
		g.fillRect(x, 0, width, top);
		g.fillRect(x, bottom, width, Window.HEIGHT);
	}
}

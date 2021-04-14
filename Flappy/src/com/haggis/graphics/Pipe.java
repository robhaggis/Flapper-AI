package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Pipe {

	Random rng = new Random(System.currentTimeMillis());

	int top = rng.nextInt(Window.HEIGHT / 2);
	int bottom = rng.nextInt(Window.HEIGHT / 2);
	int speed = 2;
	int x = Window.WIDTH;
	int width = 20;
	boolean highlight = false;

	Color col = Color.WHITE;
	Color highlightCol = Color.red;

	void update() {
		x -= speed;
	}

	boolean collides(Bird b) {

		if (b.y < top || b.y > Window.HEIGHT - bottom) {
			if (b.x > x && b.x < x + width) {
				this.highlight = true;
				return true;
			}
		}
		highlight = false;
		return false;
	}

	void show(Graphics2D g) {
		if(highlight)g.setColor(highlightCol);
		else g.setColor(col);
		g.fillRect(x, 0, width, top);
		g.fillRect(x, Window.HEIGHT - bottom, width, bottom);
	}
}

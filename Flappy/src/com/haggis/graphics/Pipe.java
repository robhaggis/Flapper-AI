package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import com.haggis.utils.Rand;

public class Pipe {
	int x;
	int top;
	int bottom;
	int w = 40;

	Pipe() {
		x = Window.WIDTH + w;
		top = Rand.randomRange(100, Window.HEIGHT / 2);
		bottom = Rand.randomRange(100, Window.HEIGHT / 2);
	}

	boolean hits(Bird b) {
		if ((b.pos.x+(b.r*2) > x) && (b.pos.x < (x + w))) {
			if ((b.pos.y < (top + b.r)) || (b.pos.y > (Window.HEIGHT - bottom - b.r))) {
				return true;
			}
		}
		return false;
	}

	void update() {
		x -= 3;
	}

	void render(Graphics2D g) {

		g.setColor(Color.GREEN);

		g.fillRect(x, 0, w, top);
		g.fillRect(x,Window.HEIGHT - bottom, w, Window.HEIGHT);
	}
}

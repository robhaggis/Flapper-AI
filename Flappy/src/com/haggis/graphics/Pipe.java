package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Pipe {
	
	Random rng = new Random(System.currentTimeMillis());
	
	int top = rng.nextInt(Window.HEIGHT/2);
	int bottom = rng.nextInt(Window.HEIGHT/2);
	int speed = 2;
	int x = Window.WIDTH;
	int width = 20;
	
	Color col = Color.WHITE;
	
	void update() {
		x -= speed;
	}
	
	void show(Graphics2D g) {
		g.setColor(col);
		g.fillRect(x, 0, width, top);
		g.fillRect(x,  Window.HEIGHT - bottom, width, bottom);
	}
}

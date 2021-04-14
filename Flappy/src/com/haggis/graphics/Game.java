package com.haggis.graphics;

import java.awt.Graphics2D;

public class Game {

	Bird bird;
	
	public void init() {
		bird = new Bird();
	}

	public void update() {
	//Update logic here
		bird.update();
	}

	public void render(Graphics2D g) {

	bird.show(g);

	}

	// Constructor
	public Game() {
		init();
	}
	
	public void spacePressed() {
		bird.up();
	}

}

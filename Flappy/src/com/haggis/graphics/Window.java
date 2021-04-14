package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends JFrame implements Runnable {

	// Params
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	private static final String TITLE = "TITLE";
	private static final double TARGET_UPS = 60;

	public static float fpsCounter = 0f;
	public static float upsCounter = 0f;

	// Operational
	private boolean isRunning = false;
	private Game game;
	private static final long serialVersionUID = 1L;

	// Input
	public KeyInput keyListener;
	public MouseInput mouseListener;

	// Constructor
	public Window() {

		// Window setup
		this.setSize(WIDTH, HEIGHT);
		this.setTitle(TITLE);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.requestFocus();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		// Init scene
		game = new Game();
		isRunning = true;

		// Input Handling
		keyListener = new KeyInput();
		this.addKeyListener(keyListener);

		mouseListener = new MouseInput(game);
		this.addMouseListener(mouseListener);

	}

	// Called TARGET_UPS times per second. Used to update game stuff
	public void update() {
		game.update();
		checkKeyPresses();
	}

	// Render is called as often as possible. Used to update graphics
	public void render() {

		BufferStrategy bs = getBufferStrategy();

		// Run on first call to render, as no buffer strategy created
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		// Clear screen between frames
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Update Graphics Buffer
		game.render(g);

		// Free up Memory
		g.dispose();

		// Blit to next buffer
		bs.show();
	}

	// Check for keybord inputs
	public void checkKeyPresses() {
		// KeyListener Stuff here - example to detect up key pressed
		if (keyListener.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}

	}

	// Game loop Timing
	public void run() {

		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / TARGET_UPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		while (isRunning) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fpsCounter = frames;
				upsCounter = updates;
				this.setTitle(TITLE + " | FPS:" + frames + " | UPS:" + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

}

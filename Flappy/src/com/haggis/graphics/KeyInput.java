package com.haggis.graphics;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

	private boolean[] keyPressed = new boolean[128];
	
	
	@Override
	public void keyTyped(KeyEvent e) {		
		keyPressed[e.getKeyCode()]=true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()]=true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()]=false;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keyPressed[keyCode];
	}
	
}


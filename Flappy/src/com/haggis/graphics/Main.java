package com.haggis.graphics;

public class Main {
	public static Thread mainThread;

	public static void main(String[] args) {

		Window window = new Window();

		mainThread = new Thread(window);
		mainThread.start();
	}

	@SuppressWarnings("static-access")
	public static void delay(int ms) {
		try {
			mainThread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void log(String s) {
		System.out.println(s);
	}

}

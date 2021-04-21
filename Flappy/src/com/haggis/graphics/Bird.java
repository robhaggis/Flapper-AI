package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import haggis.Vector;

class Bird{
  Vector pos, vel, acc;
  Vector gravity, jump;
  float r = 16;
  float d = r * 2;
  boolean alive = true;
  float[] inputs = new float[5];
  float score = 0;
  float fitness = 0;
  NeuralNetwork brain;
  
  Color c;

  public Bird(){
    pos = new Vector(64, Window.HEIGHT/2);
    vel = new Vector();
    gravity = new Vector(0, .8f);
    jump = new Vector(0, 12);
    score = 0;
    fitness = 0;
    brain = new NeuralNetwork(5, 64, 2);
    
  }

  public Bird(NeuralNetwork b){
    pos = new Vector(64, Window.HEIGHT/2);
    vel = new Vector();
    gravity = new Vector(0, .8f);
    jump = new Vector(0, -12);
    score = 0;
    fitness = 0;
    brain = b.copy();


  }

  void mutate(){
    brain.mutate(.01f);
  }

  void think(ArrayList<Pipe> pipe){
    Pipe closest = null;
    float recordD = Float.POSITIVE_INFINITY;
    for (int i = 0; i < pipe.size(); i++){
      Pipe p = pipe.get(i);
      float d = (p.x+p.w) - pos.x;
      if (d < recordD && d > 0){
        closest = pipe.get(i);
        recordD = d;
      }
    }


    inputs[0] = pos.y / Window.HEIGHT;
    inputs[1] = vel.y / 10;
    inputs[2] = closest.top / Window.HEIGHT;  
    inputs[3] = closest.bottom / Window.WIDTH;
    inputs[4] = closest.x / Window.WIDTH;
    float[] guess = brain.feedForward(inputs);
    if (guess[0] > guess[1]){
      jump();
    }
  }

  void render(Graphics2D g){
	g.setColor(new Color(1,1,1,100));
	g.fillOval((int)pos.x-1, (int)pos.y-1,(int) d+1, (int)d+1);
    g.setColor(new Color(255,0,0,100));
    g.fillOval((int)pos.x, (int)pos.y,(int) d, (int)d);
  }

  void update(){
    score++;
    vel.add(gravity);
    pos.add(vel);
  }

  void jump(){
    vel.add(jump);
  }

  boolean dead(){
    return (pos.y+r > Window.HEIGHT || pos.y < r);
  }
}
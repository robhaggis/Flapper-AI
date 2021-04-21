package com.haggis.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import haggis.Rand;

public class Pipe{

  float top;
  float bottom;
  float x;
  float w;
  float speed;
  float spacing;

  public Pipe(){
    spacing = 200;
    top = Rand.randomRange(Window.HEIGHT/ 6, (float) (.75 * Window.HEIGHT));
    bottom = Window.HEIGHT - (top + spacing);
    w = 80;
    x = Window.WIDTH;
    speed = 3;
  }

  public void render(Graphics2D g){
    g.setColor(Color.GREEN);
    g.fillRect((int)x,0, (int)w,(int) top);
    g.fillRect((int)x, (int) (Window.HEIGHT-bottom),(int) w,(int) bottom);
  }

  public void update(){
    x -= speed;
  }

  public boolean offscreen(){
    return x < -w;
  }

  public boolean hit(Bird b){
    if (b.pos.y < top+b.r || b.pos.y+b.r > Window.HEIGHT-bottom){
      if (b.pos.x+b.r > x && b.pos.x < (x + w)+b.r)
        return true;
    }
    return false;
  }
}
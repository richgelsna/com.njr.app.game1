package com.njr.game.engine.object;

import java.awt.Color;
import java.awt.Graphics;

import com.njr.game.GameProperties;

public class BasicEnemy extends GameObject{
	
	private int widthSize = 16;
	private int heightSize = 16;

	public BasicEnemy(int x, int y, ID id) {
		super(x, y, id);
		velocityX = 5;
		velocityY = 5;
	}

	@Override
	public void tick() {
		x += velocityX;
		y += velocityY;
		
		// TODO: Figure out how to fix
		if(y<=0 || (y+32)>=GameProperties.HEIGHT) {
			velocityY *= -1;
		}
		if(x<=0 || (x+32)>=GameProperties.WIDTH) {
			velocityX *= -1;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, widthSize, heightSize);
		
	}

}

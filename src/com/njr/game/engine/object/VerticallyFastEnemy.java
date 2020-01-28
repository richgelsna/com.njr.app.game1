package com.njr.game.engine.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.njr.game.GameProperties;
import com.njr.game.Handler;

public class VerticallyFastEnemy extends GameObject{
	
	private int DEFAULTWIDTH = 16;
	private int DEFAULTHEIGHT = 16;
	private Color DEFAULTCOLOR = Color.orange;

	public VerticallyFastEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		velocityX = 2;
		velocityY = 9;
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
		
		handler.addObject(new Trail(x, y, ID.Trail, DEFAULTCOLOR,  DEFAULTWIDTH, DEFAULTHEIGHT, 0.045f, this.handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(DEFAULTCOLOR);
		g.fillRect(x, y, DEFAULTWIDTH, DEFAULTHEIGHT);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, DEFAULTWIDTH, DEFAULTHEIGHT);
	}

}

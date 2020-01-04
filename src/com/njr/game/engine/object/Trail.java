package com.njr.game.engine.object;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.njr.game.Handler;

// Truth be told, I do not understand this class well.  It was initially developed during tutorial:
//	https://www.youtube.com/watch?v=3paMFMwVfWU&feature=youtu.be&list=PLWms45O3n--6TvZmtFHaCWRZwEqnz2MHa&t=0s

public class Trail extends GameObject {

	private float alpha = 1;
	
	// life between 0.01 & 0.1
	private float life;
		
	private Color color;
	private int width;
	private int height;
	
	
	
	
	// TODO: Just pass in getBounds() of enemy with width/height?
	public Trail(int x, int y, ID id,  Color color, int width, int height, float life, Handler handler) {
		super(x, y, id, handler);
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
	}

	@Override
	public void tick() {
		if(alpha > life) {
			alpha -= (life -  .0001f); 
		} else {
			handler.removeObject(this);
		}
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g2d.setColor(color);
		g2d.fillRect(x, y, width, height);
		
		// TODO: Get better understanding of setComposite/makeTransparent...
		g2d.setComposite(makeTransparent(1));
		
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		
		return AlphaComposite.getInstance(type, alpha);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.njr.game.engine.object;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.njr.game.Handler;

public abstract class GameObject {
	protected int x;
	protected int velocityX;
	protected int y;
	protected int velocityY;
	protected ID id;
	protected Handler handler;
	
	public GameObject(int x, int y, ID id, Handler handler) {
		this.x=x;
		this.y=y;
		this.id=id;
		this.handler = handler;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getVelocityX() {
		return velocityX;
	}
	
	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y=y;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	
}

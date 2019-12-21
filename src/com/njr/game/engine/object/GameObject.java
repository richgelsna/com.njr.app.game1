package com.njr.game.engine.object;

import java.awt.Graphics;

public abstract class GameObject {
	protected int x;
	protected int velocityX;
	protected int y;
	protected int velocityY;
	protected ID id;
	
	public GameObject(int x, int y, ID id) {
		this.x=x;
		this.y=y;
		this.id=id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
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

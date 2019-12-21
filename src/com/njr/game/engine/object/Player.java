package com.njr.game.engine.object;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {

	public Player(int x, int y, ID id) {
		super(x, y, id);
	}

	@Override
	public void tick() {
		x += velocityX;
		y += velocityY;
	}

	@Override
	public void render(Graphics g) {
		if(id == ID.Player) g.setColor(Color.cyan);
		if(id == ID.Player2) g.setColor(Color.blue);
		g.fillRect(x, y, 32, 32);
	}

}

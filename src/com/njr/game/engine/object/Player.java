package com.njr.game.engine.object;

import java.awt.Color;
import java.awt.Graphics;

import com.njr.game.GameProperties;
import com.njr.game.util.IntegerUtil;
import com.njr.game.util.SimpleLogger;

public class Player extends GameObject {

	SimpleLogger logger = new SimpleLogger(GameProperties.APP_LOGLEVEL, this.getClass());
	
	public Player(int x, int y, ID id) {
		super(x, y, id);
	}

	@Override
	public void tick() {
		// TODO: Fix
		int yBottom = y+75;
		int xRight = x+64;
		x = IntegerUtil.clamp(x+velocityX, 0, GameProperties.WIDTH-54);
		y = IntegerUtil.clamp(y+velocityY, 4, GameProperties.HEIGHT-75);
	}

	@Override
	public void render(Graphics g) {
		if(id == ID.Player) g.setColor(Color.cyan);
		g.fillRect(x, y, 32, 32);
	}

}

package com.njr.game;

import java.awt.Color;
import java.awt.Graphics;

import com.njr.game.util.IntegerUtil;
import com.njr.game.util.SimpleLogger;

public class HUD {
	SimpleLogger logger = new SimpleLogger(GameProperties.APP_LOGLEVEL, this.getClass());
	
	public static int HEALTH = 100;
	private static int MAXHEALTH=100;
	private static int MINHEALTH=0;
	
	public void tick() {
		HEALTH = IntegerUtil.clamp(HEALTH, MINHEALTH, MAXHEALTH);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		
		g.setColor(new Color(40, 158, 71));
		g.fillRect(15, 15, HEALTH*2, 32);
		
		g.setColor(Color.black);
		g.drawRect(15, 15, 200, 32);
	}
}

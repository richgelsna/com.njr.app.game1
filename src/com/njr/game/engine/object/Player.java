package com.njr.game.engine.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import com.njr.game.GameProperties;
import com.njr.game.HUD;
import com.njr.game.Handler;
import com.njr.game.util.IntegerUtil;
import com.njr.game.util.SimpleLogger;

public class Player extends GameObject {

	SimpleLogger logger = new SimpleLogger(GameProperties.APP_LOGLEVEL, this.getClass());
	
	// rectangular player
	private final int DEFAULTPLAYERWIDTH = 32;
	private final int DEFAULTPLAYERHEIGHT = 32;
	private Color DEFAULTPLAYERCOLOR = Color.cyan;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
	}

	@Override
	public void tick() {
		x += velocityX;
		y += velocityY;
		
		// TODO: Fix
		int yBottom = y+75;
		int xRight = x+64;
		x = IntegerUtil.clamp(x+velocityX, 0, GameProperties.WIDTH-54);
		y = IntegerUtil.clamp(y+velocityY, 4, GameProperties.HEIGHT-75);
		
		handler.addObject(new Trail(x, y, ID.Trail, DEFAULTPLAYERCOLOR,  DEFAULTPLAYERWIDTH, DEFAULTPLAYERHEIGHT, 0.045f, this.handler));
		
		collision();
	}

	private void collision() {
		for(GameObject gameObject : handler.getObjects()) {
			if(gameObject.getId() == ID.BasicEnemy && this.getBounds().intersects(gameObject.getBounds())) {
				HUD.HEALTH -= 2;
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(id == ID.Player) g.setColor(DEFAULTPLAYERCOLOR);
		g.fillRect(x, y, DEFAULTPLAYERWIDTH, DEFAULTPLAYERHEIGHT);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, DEFAULTPLAYERWIDTH, DEFAULTPLAYERHEIGHT);
	}
}

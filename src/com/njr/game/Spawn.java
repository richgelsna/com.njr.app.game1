package com.njr.game;

import java.util.Random;

import com.njr.game.engine.object.BasicEnemy;
import com.njr.game.engine.object.VerticallyFastEnemy;
import com.njr.game.engine.object.ID;

public class Spawn {
	// TODO: Left off at 5:05 of vid 6
	private final int scoreToLevelUp = 160;
	
	private Random r = new Random();
	private Handler handler;
	private HUD hud;
	
	private int scoreKeep = 0;
	
	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick() {
		scoreKeep++;
		
		if( scoreKeep == scoreToLevelUp ) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel()+1);
			// TODO: Would be better if using GameProperties instead??
			if( hud.getLevel() % 2 == 1) {
				handler.addObject(new BasicEnemy(r.nextInt(GameMain.WIDTH), r.nextInt(GameMain.HEIGHT), ID.BasicEnemy, handler));
			} else {
				handler.addObject(new VerticallyFastEnemy(r.nextInt(GameMain.WIDTH), r.nextInt(GameMain.HEIGHT), ID.BasicEnemy, handler));
			}
			
		}
	}
}

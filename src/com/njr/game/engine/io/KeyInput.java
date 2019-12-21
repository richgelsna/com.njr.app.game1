package com.njr.game.engine.io;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.njr.game.Handler;
import com.njr.game.engine.object.GameObject;
import com.njr.game.engine.object.ID;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	// TODO: Figure out logger.  Log on "debug" mode ?
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		System.out.println(key + "key pressed");
		for(GameObject object : handler.getObjects()) {
			if(object.getId() == ID.Player) {				
				if(key == KeyEvent.VK_W) object.setVelocityY(-5);
				if(key == KeyEvent.VK_A) object.setVelocityX(-5);
				if(key == KeyEvent.VK_D) object.setVelocityX(5);
				if(key == KeyEvent.VK_S) object.setVelocityY(5);
			}
			
			if(object.getId() == ID.Player2) {
				if(key == KeyEvent.VK_UP) object.setVelocityY(-5);
				if(key == KeyEvent.VK_LEFT) object.setVelocityX(-5);
				if(key == KeyEvent.VK_RIGHT) object.setVelocityX(5);
				if(key == KeyEvent.VK_DOWN) object.setVelocityY(5);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		System.out.println(key + " key released");
		for(GameObject object : handler.getObjects()) {
			if(object.getId() == ID.Player) {				
				if(key == KeyEvent.VK_W) object.setVelocityY(0);
				if(key == KeyEvent.VK_A) object.setVelocityX(0);
				if(key == KeyEvent.VK_D) object.setVelocityX(0);
				if(key == KeyEvent.VK_S) object.setVelocityY(0);
			}
			
			if(object.getId() == ID.Player2) {
				if(key == KeyEvent.VK_UP) object.setVelocityY(0);
				if(key == KeyEvent.VK_LEFT) object.setVelocityX(0);
				if(key == KeyEvent.VK_RIGHT) object.setVelocityX(0);
				if(key == KeyEvent.VK_DOWN) object.setVelocityY(0);
			}
		}
	}
}
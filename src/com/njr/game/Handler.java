package com.njr.game;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import com.njr.game.engine.object.GameObject;

public class Handler {
	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	public void tick() {
		LinkedList<GameObject> shallowCopyList = new LinkedList<>(objects);

		for(GameObject object : shallowCopyList) {
			object.tick();
		}
	}
	
	public void render(Graphics g) {
		LinkedList<GameObject> shallowCopyList = new LinkedList<>(objects);
		for(GameObject object : shallowCopyList) {
			object.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
	
	public void removeObject(GameObject object) {
		objects.remove(object);
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}
}

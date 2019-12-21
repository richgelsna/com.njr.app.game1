package com.njr.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.njr.game.engine.io.KeyInput;
import com.njr.game.engine.io.Window;
import com.njr.game.engine.object.ID;
import com.njr.game.engine.object.Player;

public class GameMain extends Canvas implements Runnable {
	private static final long serialVersionUID = -2729237790344954097L;

	//TODO: private?
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 12 * 9;

	private Thread thread;
	private boolean running = false;

	
	private Handler handler;
	
	public GameMain() {
		handler = new Handler();
		
		//TODO: Figure out this.addKeyListener on my own time.
		this.addKeyListener(new KeyInput(handler));
		new Window(WIDTH, HEIGHT, "game1", this);
		
		handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player));
		handler.addObject(new Player(WIDTH/2+64, HEIGHT/2-32, ID.Player2));
	}

	public static void main(String[] args){
		new GameMain();
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >=1)
			{
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				//TODO: Logger
//				System.out.println("FPS: "+ frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose(); 
		bs.show();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {

		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

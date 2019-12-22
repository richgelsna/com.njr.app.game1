package com.njr.game;

import static com.njr.game.GameProperties.APP_LOGLEVEL;
import static com.njr.game.GameProperties.REPORT_FRAMERATE_TIMING;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;

import com.njr.game.engine.io.KeyInput;
import com.njr.game.engine.io.Window;
import com.njr.game.engine.object.BasicEnemy;
import com.njr.game.engine.object.ID;
import com.njr.game.engine.object.Player;
import com.njr.game.util.SimpleLogger;

public class GameMain extends Canvas implements Runnable {
	private static final long serialVersionUID = -2729237790344954097L;	
	private Thread thread;
	private boolean running = false;

	public static final int WIDTH = GameProperties.WIDTH;
	public static final int HEIGHT = GameProperties.HEIGHT;
	
	private Handler handler;
	private HUD hud;
	
	public GameMain() {
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		new Window(WIDTH, HEIGHT, "game1", this);
		
		hud = new HUD();
		
		handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player));
		handler.addObject(new BasicEnemy(WIDTH/2-32, HEIGHT/2-32, ID.BasicEnemy));
		
	}

	public static void main(String[] args){
		new GameMain();
	}

	public void run() {
		SimpleLogger logger = new SimpleLogger(APP_LOGLEVEL, this.getClass());
		long framerateReportMS = TimeUnit.SECONDS.toMillis(REPORT_FRAMERATE_TIMING);
		logger.info("Reporting framerate every " + REPORT_FRAMERATE_TIMING  + " seconds.");
		
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
			
			if(System.currentTimeMillis() - timer > framerateReportMS)
			{
				timer += framerateReportMS;
				int framesActual = frames/REPORT_FRAMERATE_TIMING;
				if(framesActual<60) logger.warn("FPS: " + framesActual);
				else logger.info("FPS: " + framesActual);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
		hud.tick();
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
		hud.render(g);
		
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

package com.njr.game;

import java.awt.Color;
import java.awt.Graphics;

import com.njr.game.util.IntegerUtil;
import com.njr.game.util.SimpleLogger;

public class HUD {
	SimpleLogger logger = new SimpleLogger.LoggerBuilder(GameProperties.APP_LOGLEVEL).reportingClass(this.getClass()).silentPeriod(6).maxMessagesPerPeriod(2).build();
	
	private static int MAXHEALTH=100;
	private static int MINHEALTH=0;
	public static int HEALTH = MAXHEALTH;
	
	private static Color FULLHEALTHCOLOR = new Color(0, 128, 128);
	private static Color TWOTHIRDHEALTHCOLOR = new Color(0, 188, 35);
	private static Color ONETHIRDHEALTHCOLOR = new Color(252, 241, 83);
	private static Color NOHEALTHCOLOR = new Color(220, 0, 0);
	
	private int score = 0;
	private int level = 1;
	public void tick() {
		HEALTH = IntegerUtil.clamp(HEALTH, MINHEALTH, MAXHEALTH);
		score++;
	}
	
	public void render(Graphics g) {
		// background of HP bar (Seen while missing health)
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		
		// Remaining health
		double healthPercent =  ((double)HEALTH/(double)MAXHEALTH);
		logger.debug("Player health %: " + healthPercent);
		try {
			if(healthPercent > 0.667) {
				double gradientPerc = (healthPercent - 0.667)*3;
				g.setColor(calculateColorTransition(FULLHEALTHCOLOR, TWOTHIRDHEALTHCOLOR, gradientPerc));
			}
			else if(healthPercent > 0.334) {
				// Calculate rough gradient between both colors
			 	double gradientPerc = (healthPercent - 0.334)*3;
				g.setColor(calculateColorTransition(TWOTHIRDHEALTHCOLOR, ONETHIRDHEALTHCOLOR, gradientPerc));
			}
			else {
				double gradientPerc = healthPercent*3;
				g.setColor(calculateColorTransition(ONETHIRDHEALTHCOLOR, NOHEALTHCOLOR, gradientPerc));
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		g.fillRect(15, 15, HEALTH*2, 32);
		
		// Outline of HP bar
		g.setColor(Color.black);
		g.drawRect(15, 15, 200, 32);
		
		// score & level
		g.setColor(Color.white);
		g.drawString("Score: " + score, 14, 64);
		g.drawString("Level: " + level, 14, 80);
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getLevel() { 
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	private Color calculateColorTransition(Color fullColor, Color emptyColor, double percent) throws Exception {
		if(percent>1 || percent<0) {
			throw new Exception("percent must be a percentile between 0 and 1.  It is " + percent + ".");
		}
		
		// fullColor is target, emptyColor is base.
		int transitionR = IntegerUtil.calculateGradient(percent, emptyColor.getRed(), fullColor.getRed());
		int transitionG = IntegerUtil.calculateGradient(percent, emptyColor.getGreen(), fullColor.getGreen());
		int transitionB = IntegerUtil.calculateGradient(percent, emptyColor.getBlue(), fullColor.getBlue());
		
		logger.debugVerbose("RGB Transition:" + "\n\tR:" + transitionR + "\n\tG:" + transitionG + "\n\tB:" + transitionB);
		return new Color(transitionR, transitionG, transitionB);
	}
	
}

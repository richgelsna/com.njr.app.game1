package com.njr.game;

import com.njr.game.util.SimpleLogger;

public class GameProperties {
	// TODO: Find solution for storing config properties.
	public static final SimpleLogger.LoggerLevel APP_LOGLEVEL = SimpleLogger.LoggerLevel.DEBUG;
	public static final SimpleLogger.LoggerLevel ENGINE_LOGLEVEL = SimpleLogger.LoggerLevel.ERROR;
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int REPORT_FRAMERATE_TIMING =  3;
}

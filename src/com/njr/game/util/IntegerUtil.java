package com.njr.game.util;

import com.njr.game.GameProperties;

public class IntegerUtil {
	private static SimpleLogger logger = new SimpleLogger(GameProperties.APP_LOGLEVEL, IntegerUtil.class);
	public static int clamp(int var, int min, int max) {
		if(var >= max) 
			return max;
		if(var <= min)
			return min;
		else
			return var;
	}
}

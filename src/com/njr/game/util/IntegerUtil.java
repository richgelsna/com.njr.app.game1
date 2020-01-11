package com.njr.game.util;

import com.njr.game.GameProperties;

public class IntegerUtil {
	private static SimpleLogger logger = new SimpleLogger.LoggerBuilder(GameProperties.APP_LOGLEVEL).reportingClass(IntegerUtil.class).silentPeriod(3).maxMessagesPerPeriod(2).build();
	
	// Returns var if between min and max, otherwise returns whatever is closer... min or max.
	//  Think of it as a lower/upper boundary that a number is allowed to rest between.
	public static int clamp(int var, int min, int max) {
		if(var >= max) 
			return max;
		if(var <= min)
			return min;
		else
			return var;
	}
	
	// Finds the difference between two numbers, regardless of which one is larger.
	public static int findDifference(int numberA, int numberB) {
		if(numberA > numberB) {
			return numberA-numberB;
		} else {
			return numberB-numberA;
		}
	}
	
	// Add or subtract additive to/from baseNumber so that it becomes closer to targetNumber.
	// Useful for when you find the difference between two numbers and want to apply a percentage to the difference.
	//  Throws RuntimeExxception if it exceeds targetNumber.
	//		TODO: Should it just cap it at otherNumber rather than throw errors? 
	//		  Another question to answer is: Is it this function's job to make sure the sum doesn't pass the target?
	//		  Keeping it private until that question is answered (or needed publicly LMFAO).
	private static int sumTowards(int additive, int baseNumber, int targetNumber) {
		if( (baseNumber - targetNumber) == 0) {
			logger.warn("baseNumber and otherNumber are the same number.");
			return baseNumber;
		}
		
		int sum = 0;
		if(baseNumber>targetNumber) {
			sum = baseNumber - additive;
			if(sum<targetNumber) {
				logger.warn(baseNumber + " - " + additive + " is less than " + targetNumber + ".");
			}
			
			sum = clamp(sum, targetNumber, baseNumber);
		}
		
		if(baseNumber<targetNumber) {
			sum = baseNumber +  additive;
			if(sum>targetNumber) {
				logger.warn(baseNumber + " + " + additive + " is greater than " + targetNumber + ".");
			}
			
			sum = clamp(sum, baseNumber, targetNumber);
		}
		
		return sum;
	}
	
	/**
	 * Calculates the difference between baseNumber and targetNumber, applies given percentage, and adds baseNumber towards targetNumber.
	 * 
	 * @param percent - Dictates how far between the returned gradient will be between baseNumber and targetNumber.
	 * @param baseNumber - Number to be returned as gradient approaches 0. 
	 * @param targetNumber - Number to be returned as gradient approaches 1.
	 * @return A gradient number between baseNumber and targetNumber
	 */
	// 
	public static int calculateGradient(double percent, int baseNumber, int targetNumber) {
		if(percent<0 || percent>1) {
			throw new RuntimeException("gradient must be a percentile between 0 and 1.");
		}
		
		int difference = findDifference(baseNumber, targetNumber);
		int productI = (int) Math.round(difference * percent);
		
		return sumTowards(productI, baseNumber, targetNumber);
	}
}

package com.njr.game.util;

import java.util.concurrent.TimeUnit;

public class SimpleLogger {
	// Instantiate a logger STATICALLY in each class if you want to limit how many messages per class a logger gets.
	// otherwise, it will be per object.
	private static int MAXCLASSWORDSIZE = 35;
	
	private LoggerLevel loggerLevel;
	private String reportingClass;
	private long silentPeriod = 0;
	private int maxMessagesPerPeriod = 100;
	private long periodStart = 0;
	private int reportedMessagesSincePeriodStart = 0;
	
	public SimpleLogger(LoggerLevel loggerLevel) {
		this.loggerLevel = loggerLevel;
	}
	
	public SimpleLogger(LoggerLevel loggerLevel, Class reportingClass) {
		this.loggerLevel = loggerLevel;
		this.reportingClass = chopClassName(reportingClass.getCanonicalName());
	}
	
	// silentPeriodSeconds: A period of time that will limit the number of messages in a given timespan to deafen spam.
	//  Set maxMessages to allow how many messages should be allowed.
	//	Set to 0 if you want infinite messages (or don't instantiate at all).
	public SimpleLogger(LoggerLevel loggerLevel, Class reportingClass, int silentPeriodSeconds) {
		this.loggerLevel = loggerLevel;
		this.reportingClass = chopClassName(reportingClass.getCanonicalName());
		this.silentPeriod = TimeUnit.SECONDS.toMillis(silentPeriodSeconds);
	}
	
	public void setMaxMessagesPerPeriod(int messages) {
		maxMessagesPerPeriod = messages;
	}
	
	public int getMaxMessagesPerPeriod() {
		return maxMessagesPerPeriod;
	}
	
	private void setSilentPeriod(int silentPeriodSeconds) {
		this.silentPeriod = TimeUnit.SECONDS.toMillis(silentPeriodSeconds);
	}
	
	public void error(String output) {
		LoggerLevel implicitLoggerLevel = LoggerLevel.ERROR;
		outputPhrase(implicitLoggerLevel, output);
	}
	
	
	public void critical(String output) {
		LoggerLevel implicitLoggerLevel = LoggerLevel.CRITICAL;
		outputPhrase(implicitLoggerLevel, output);
	}
	
	public void warn(String output) {
		LoggerLevel implicitLoggerLevel = LoggerLevel.WARN;
		outputPhrase(implicitLoggerLevel, output);
	}
	
	public void info(String output) {
		LoggerLevel implicitLoggerLevel = LoggerLevel.INFO;
		outputPhrase(implicitLoggerLevel, output);
	}
	
	public void debug(String output) {
		LoggerLevel implicitLoggerLevel = LoggerLevel.DEBUG;
		outputPhrase(implicitLoggerLevel, output);
	}
	
	public void debugVerbose(String output) {
		LoggerLevel implicitLoggerLevel = LoggerLevel.DEBUG_VERBOSE;
		outputPhrase(implicitLoggerLevel, output);
	}
	
	private String chopClassName(String word) {
		if(word != null && word.length()>MAXCLASSWORDSIZE) {
				String[] stringArray = word.split("\\.");
				String className = stringArray[stringArray.length-1];
				int remainingWordSpace = MAXCLASSWORDSIZE - className.length() - 3;
				
				int index = 0;
				String preWord = "";
				while(remainingWordSpace > 0) {
					String subWord = stringArray[index];
					if(subWord.length()>remainingWordSpace) {
						preWord = preWord + subWord.substring(0, remainingWordSpace) + "...";
						remainingWordSpace = 0;
					} else if(subWord.length() == remainingWordSpace) {
						preWord = preWord + subWord + "...";
						remainingWordSpace = 0;
					} else {
						preWord = preWord + subWord + ".";
						remainingWordSpace = remainingWordSpace - (subWord.length() +1);
					}
					index++;
				}
				word = preWord + className;
		}
		
		return word;
	}
	
	private void outputPhrase(LoggerLevel loggerLevel, String phrase) {
		long currentTime = System.currentTimeMillis();
		
		boolean periodElapsed = (currentTime - periodStart) > silentPeriod;
		if(periodElapsed) {
			periodStart = currentTime;
			reportedMessagesSincePeriodStart = 0;
		}
		
		// In case of LeggerLevel.Error, don't restrict messages.
		boolean restrictMessages = (maxMessagesPerPeriod<reportedMessagesSincePeriodStart) && loggerLevel!=LoggerLevel.ERROR;
		if(this.loggerLevel.getLoggerLevelPriority() >= loggerLevel.getLoggerLevelPriority() && !restrictMessages) {
			reportedMessagesSincePeriodStart++;
			if(reportingClass != null) {
				System.out.println(loggerLevel.name() + ": " + reportingClass + ": " + phrase);
			} else {
				System.out.println(loggerLevel.name()+ ": "  + phrase);
			}
		}
	}

	public enum LoggerLevel {
		NONE(-1000),
		ERROR(000),
		CRITICAL(1000),
		WARN(2000),
		INFO(3000),
		DEBUG(4000),
		DEBUG_VERBOSE(5000);
		
		private int loggerLevelPriority;
		private LoggerLevel(int debugLevel) {
			this.loggerLevelPriority = debugLevel;
		}
		
		protected int getLoggerLevelPriority() { 
			return this.loggerLevelPriority;
		}
	}
	
	public static class LoggerBuilder {
		LoggerLevel loggerLevel;
		private Class reportingClass;
		private int silentPeriod = 0;
		private int maxMessagesPerPeriod = 100;
		
		public LoggerBuilder() { }
		public LoggerBuilder(LoggerLevel loggerLevel) {
			this.loggerLevel = loggerLevel;
		}
		
		public LoggerBuilder loggerLevel(LoggerLevel loggerLevel) {
			this.loggerLevel = loggerLevel;
			return this;
		}
		
		public LoggerBuilder reportingClass(Class reportingClass) {
			this.reportingClass = reportingClass;
			return this;
		}
		
		public LoggerBuilder silentPeriod(int silentPeriod) {
			this.silentPeriod = silentPeriod;
			return this;
		}
		
		public LoggerBuilder maxMessagesPerPeriod(int maxMessagesPerPeriod) {
			this.maxMessagesPerPeriod = maxMessagesPerPeriod;
			return this;
		}
		
		public SimpleLogger build(){
			SimpleLogger logger;
			if(loggerLevel == null) {
				throw new RuntimeException("SimpleLogger requires LoggerLevel");
			}
			
			if(loggerLevel != null && reportingClass != null) {
				logger = new SimpleLogger(loggerLevel, reportingClass);
			} else {
				logger = new SimpleLogger(loggerLevel);
			}
			
			if(silentPeriod>=0) {
				logger.setSilentPeriod(silentPeriod);
			} else {
				throw new RuntimeException("SimpleLogger requires silent period 0 or larger.");
			}
			if(maxMessagesPerPeriod>1) {
				logger.setMaxMessagesPerPeriod(maxMessagesPerPeriod);
			} else {
				throw new RuntimeException("SimpleLogger requires max messages 1 or larger.");
			}
			
			return logger;
		}
	}
}

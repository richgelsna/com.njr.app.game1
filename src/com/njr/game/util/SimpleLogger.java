package com.njr.game.util;

public class SimpleLogger {
	private static int MAXCLASSWORDSIZE = 35;
	
	private LoggerLevel loggerLevel;
	private String reportingClass;
	
	public SimpleLogger(LoggerLevel loggerLevel) {
		this.loggerLevel = loggerLevel;
	}
	
	public SimpleLogger(LoggerLevel loggerLevel, Class reportingClass) {
		this.loggerLevel = loggerLevel;
		this.reportingClass = chopClassName(reportingClass.getCanonicalName());
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
		if(this.loggerLevel.getLoggerLevelPriority() >= loggerLevel.getLoggerLevelPriority()) {
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
}

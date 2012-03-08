/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.utils;

/**
 * Basic Logger that supports a few log levels.
 * 
 * @author Florian Westreicher aka meredrica
 * 
 */
public class Logger {

	/** Mask flag for errors. */
	public static final int ERROR = 1;

	/** Mask flag for warnings. */
	public static final int WARNING = 2;

	/** Mask flag for Debug information. */
	public static final int DEBUG = 4;

	/** Mask flag for informal messages. */
	public static final int INFO = 8;

	/** Mask flag for all messages */
	public static final int ALL = Logger.ERROR | Logger.WARNING | Logger.DEBUG | Logger.INFO;

	/** Mask flag for fatal messages */
	public static final int FATAL = 0;

	private static int outputMask = Logger.ALL;

	/**
	 * Returns a logger for the given Class.
	 * 
	 * @param user
	 *          The Class that will use this logger.
	 * @return The Logger.
	 */
	public static Logger getLogger(final Class user) {
		return new Logger(user);
	}

	/**
	 * Sets the mask that is used by all loggers. You can blend out different
	 * levels here.
	 * 
	 * @param mask
	 *          The mask to use.
	 */
	public static void setMask(final int mask) {
		Logger.outputMask = mask;
	}

	private final Class user;

	private int localMask = -1;

	public void setLocalMask(final int mask) {
		localMask = mask;
	}

	private Logger(final Class user) {
		super();
		this.user = user;
	}

	/**
	 * Logs an informal message. Should be used for everything that is above debug
	 * level.
	 * 
	 * @param message
	 *          The message.
	 */
	public void info(final String message) {
		log(message, Logger.INFO);
	}

	/**
	 * Logs a debug message. Use for everything that can help with debugging the
	 * App.
	 * 
	 * @param message
	 *          The message.
	 */
	public void debug(final String message) {
		log(message, Logger.DEBUG);
	}

	/**
	 * Logs a warning. Use when something could be potentially dangerous.
	 * 
	 * @param message
	 *          The message.
	 */
	public void warn(final String message) {
		log(message, Logger.WARNING);
	}

	/**
	 * Logs an error. Use when something went wrong that could end in crashing the
	 * app.
	 * 
	 * @param message
	 *          The message.
	 */
	public void error(final String message) {
		log(message, Logger.ERROR);
	}

	/**
	 * Logs a fatal message. Messages passed to this method will always be logged.
	 * Use if a situation occurs that can never be recovered.
	 * 
	 * @param message
	 *          The message.
	 */
	public void fatal(final String message) {
		log(message, Logger.FATAL);
	}

	public int getMask() {
		return localMask == -1 ? Logger.outputMask : localMask;
	}

	/**
	 * Logs the given message to STDIO.
	 * 
	 * @param message
	 *          The message.
	 * @param level
	 *          The level this message was logged with.
	 */
	private void log(final String message, final int level) {
		StringBuffer sb = new StringBuffer();

		/*
		 * Assumption: DEBUG is used most, then info, then warning, then error, then
		 * fatal.
		 */
		if (level == Logger.DEBUG && (getMask() & Logger.DEBUG) == Logger.DEBUG) {
			sb.append("[DEBUG]");
		} else if (level == Logger.INFO && (getMask() & Logger.INFO) == Logger.INFO) {
			sb.append("[INFO]");
		} else if (level == Logger.WARNING && (getMask() & Logger.WARNING) == Logger.WARNING) {
			sb.append("[WARN]");
		} else if (level == Logger.ERROR && (getMask() & Logger.ERROR) == Logger.ERROR) {
			sb.append("[ERROR]");
		} else if (level == Logger.FATAL) {
			// fatal is so important we always log it
			sb.append("[FATAL]");
		}
		if (sb.length() != 0) {
			sb.append(" [");
			sb.append(user.toString());
			sb.append("] ");
			sb.append(message);
			System.out.println(sb + "\n");
		}
	}

}

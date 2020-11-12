package com.haulmont.testtask.shared;

import org.apache.log4j.Logger;

import java.util.Arrays;

public class LoggerHelper {

    private static LoggerHelper loggerHelper;

    private LoggerHelper() {
    }

    private static LoggerHelper instance() {
        if (loggerHelper == null) {
            loggerHelper = new LoggerHelper();
        }
        return loggerHelper;
    }

    public static void info(String message, Logger... loggers) {
        for (Logger logger : loggers) {
            if (logger != null) {
                logger.info(message);
            }
        }
    }

    public static void error(String message, Logger... loggers) {
        for (Logger logger : loggers) {
            if (logger != null) {
                logger.error(message);
            }
        }
    }

    public static void error(String message, Exception e, Logger... loggers) {
        for (Logger logger : loggers) {
            if (logger != null) {
                logger.error(message);
                logger.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static void error(Exception e, Logger... loggers) {
        for (Logger logger : loggers) {
            if (logger != null) {
                logger.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}

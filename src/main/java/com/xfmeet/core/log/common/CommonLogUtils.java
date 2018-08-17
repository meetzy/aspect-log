package com.xfmeet.core.log.common;

import org.slf4j.Logger;

/**
 * @author meet
 */
public class CommonLogUtils {
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_DEBUG = "DEBUG";
    private static final String LEVEL_WARN = "WARN";
    private static final String LEVEL_ERROR = "ERROR";
    private static final String LEVEL_TRACE = "TRACE";

    public static void log(Logger logger, String level, StringBuilder stringBuilder) {
        switch (level) {
            case LEVEL_DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(stringBuilder.toString());
                }
                break;
            case LEVEL_INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(stringBuilder.toString());
                }
                break;
            case LEVEL_TRACE:
                if (logger.isTraceEnabled()) {
                    logger.trace(stringBuilder.toString());
                }
                break;
            case LEVEL_WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(stringBuilder.toString());
                }
                break;
            case LEVEL_ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(stringBuilder.toString());
                }
                break;
            default:
                throw new RuntimeException("Invalid log level!");
        }
    }
}
package com.livk.commons.test;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 在test种使用
 * </p>
 *
 * @author livk
 */
@UtilityClass
public class TestLogUtils {
    private static final Map<String, Logger> LOGGER_MAP = new ConcurrentHashMap<>();

    private static final String NAME = TestLogUtils.class.getName();

    /**
     * Info.
     *
     * @param format    the format
     * @param arguments the arguments
     */
    public void info(String format, Object... arguments) {
        Logger logger = currentLogger();
        if (logger.isInfoEnabled()) {
            logger.info(format, arguments);
        }
    }

    /**
     * Trace.
     *
     * @param format    the format
     * @param arguments the arguments
     */
    public void trace(String format, Object... arguments) {
        Logger logger = currentLogger();
        if (logger.isTraceEnabled()) {
            logger.trace(format, arguments);
        }
    }

    /**
     * Warn.
     *
     * @param format    the format
     * @param arguments the arguments
     */
    public void warn(String format, Object... arguments) {
        Logger logger = currentLogger();
        if (logger.isWarnEnabled()) {
            logger.warn(format, arguments);
        }
    }

    /**
     * Debug.
     *
     * @param format    the format
     * @param arguments the arguments
     */
    public void debug(String format, Object... arguments) {
        Logger logger = currentLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(format, arguments);
        }
    }

    /**
     * Error.
     *
     * @param format    the format
     * @param arguments the arguments
     */
    public void error(String format, Object... arguments) {
        Logger logger = currentLogger();
        if (logger.isErrorEnabled()) {
            logger.error(format, arguments);
        }
    }

    private boolean notEqualsClass(StackTraceElement stackTraceElement) {
        return !stackTraceElement.getClassName().equals(NAME);
    }

    private Logger currentLogger() {
        StackTraceElement stackTraceElement = Arrays.stream(Thread.currentThread().getStackTrace())
                .skip(1)
                .filter(TestLogUtils::notEqualsClass)
                .findFirst()
                .orElseThrow();
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        return LOGGER_MAP.computeIfAbsent(className + "#" + methodName, LoggerFactory::getLogger);
    }
}
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppLogger.java

package de.escidoc.core.common.util.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public final class AppLogger
{

    public AppLogger(String className)
    {
        log = Logger.getLogger(className);
    }

    public void debug(Object msg)
    {
        log.debug(msg);
    }

    public void error(Object msg)
    {
        log.error(msg);
    }

    public void error(Object msg, Throwable throwable)
    {
        log.error(msg, throwable);
    }

    public void info(Object msg)
    {
        log.info(msg);
    }

    public void info(Object msg, Throwable throwable)
    {
        log.info(msg, throwable);
    }

    public void trace(Object message)
    {
        log.trace(message);
    }

    public void warn(Object msg)
    {
        log.warn(msg);
    }

    public void warn(Object msg, Throwable throwable)
    {
        log.warn(msg, throwable);
    }

    public boolean isDebugEnabled()
    {
        return log.isDebugEnabled();
    }

    public boolean isInfoEnabled()
    {
        return log.isInfoEnabled();
    }

    public boolean isWarnEnabled()
    {
        return log.isEnabledFor(Priority.WARN);
    }

    private Logger log;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MissingParameterException.java

package de.escidoc.core.common.exceptions.application.missing;

import de.escidoc.core.common.exceptions.application.ApplicationException;

public class MissingParameterException extends ApplicationException
{

    public MissingParameterException()
    {
        super(451, "Parameter is missing.");
    }

    public MissingParameterException(String message, Throwable cause)
    {
        super(message, cause, 451, "Parameter is missing.");
    }

    public MissingParameterException(String message)
    {
        super(message, 451, "Parameter is missing.");
    }

    public MissingParameterException(Throwable cause)
    {
        super(cause, 451, "Parameter is missing.");
    }

    public MissingParameterException(int httpStatusCode, String httpStatusMsg)
    {
        super(httpStatusCode, httpStatusMsg);
    }

    public MissingParameterException(String message, Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super(message, cause, httpStatusCode, httpStatusMsg);
    }

    public MissingParameterException(String message, int httpStatusCode, String httpStatusMsg)
    {
        super(message, httpStatusCode, httpStatusMsg);
    }

    public MissingParameterException(Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super(cause, httpStatusCode, httpStatusMsg);
    }

    private static final long serialVersionUID = 0x2a0b4590669dafL;
    public static final int HTTP_STATUS_CODE = 451;
    public static final String HTTP_STATUS_MESSAGE = "Parameter is missing.";
}

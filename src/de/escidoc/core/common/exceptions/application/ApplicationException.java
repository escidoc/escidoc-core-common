// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApplicationException.java

package de.escidoc.core.common.exceptions.application;

import de.escidoc.core.common.exceptions.EscidocException;

public abstract class ApplicationException extends EscidocException
{

    public ApplicationException()
    {
        super(400, "eSciDoc Application Error");
    }

    public ApplicationException(String message, Throwable cause)
    {
        super(message, cause, 400, "eSciDoc Application Error");
    }

    public ApplicationException(String message)
    {
        super(message, 400, "eSciDoc Application Error");
    }

    public ApplicationException(Throwable cause)
    {
        super(cause, 400, "eSciDoc Application Error");
    }

    public ApplicationException(int httpStatusCode, String httpStatusMsg)
    {
        super(httpStatusCode, httpStatusMsg);
    }

    public ApplicationException(String message, Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super(message, cause, httpStatusCode, httpStatusMsg);
    }

    public ApplicationException(String message, int httpStatusCode, String httpStatusMsg)
    {
        super(message, httpStatusCode, httpStatusMsg);
    }

    public ApplicationException(Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super(cause, httpStatusCode, httpStatusMsg);
    }

    private static final long serialVersionUID = 0x55d8f88e4276f0c0L;
    public static final int HTTP_STATUS_CODE = 400;
    public static final String HTTP_STATUS_MESSAGE = "eSciDoc Application Error";
}

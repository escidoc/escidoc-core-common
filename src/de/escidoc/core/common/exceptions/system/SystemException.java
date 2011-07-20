// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SystemException.java

package de.escidoc.core.common.exceptions.system;

import de.escidoc.core.common.exceptions.EscidocException;

public class SystemException extends EscidocException
{

    public SystemException()
    {
        super(500, "Internal eSciDoc System Error");
    }

    public SystemException(String message, Throwable cause)
    {
        super(message, cause, 500, "Internal eSciDoc System Error");
    }

    public SystemException(String message)
    {
        super(message, 500, "Internal eSciDoc System Error");
    }

    public SystemException(Throwable cause)
    {
        super(cause, 500, "Internal eSciDoc System Error");
    }

    public SystemException(int httpStatusCode, String httpStatusMsg)
    {
        super(httpStatusCode, httpStatusMsg);
    }

    public SystemException(String message, Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super(message, cause, httpStatusCode, httpStatusMsg);
    }

    public SystemException(String message, int httpStatusCode, String httpStatusMsg)
    {
        super(message, httpStatusCode, httpStatusMsg);
    }

    public SystemException(Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super(cause, httpStatusCode, httpStatusMsg);
    }

    private static final long serialVersionUID = 1L;
    public static final int HTTP_STATUS_CODE = 500;
    public static final String HTTP_STATUS_MESSAGE = "Internal eSciDoc System Error";
}

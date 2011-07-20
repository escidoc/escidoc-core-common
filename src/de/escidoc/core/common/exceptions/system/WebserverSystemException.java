// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebserverSystemException.java

package de.escidoc.core.common.exceptions.system;


// Referenced classes of package de.escidoc.core.common.exceptions.system:
//            SystemException

public class WebserverSystemException extends SystemException
{

    public WebserverSystemException()
    {
        super(500, "Internal Webserver Error");
    }

    public WebserverSystemException(Throwable error)
    {
        super(error, 500, "Internal Webserver Error");
    }

    public WebserverSystemException(String message)
    {
        super(message, 500, "Internal Webserver Error");
    }

    public WebserverSystemException(String message, Throwable error)
    {
        super(message, error, 500, "Internal Webserver Error");
    }

    private static final long serialVersionUID = 0x2936a3752231d53aL;
    public static final int HTTP_STATUS_CODE = 500;
    public static final String HTTP_STATUS_MESSAGE = "Internal Webserver Error";
}

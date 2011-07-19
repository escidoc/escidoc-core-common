// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MissingMethodParameterException.java

package de.escidoc.core.common.exceptions.application.missing;


// Referenced classes of package de.escidoc.core.common.exceptions.application.missing:
//            MissingParameterException

public class MissingMethodParameterException extends MissingParameterException
{

    public MissingMethodParameterException()
    {
        super(451, "Method parameter is missing.");
    }

    public MissingMethodParameterException(Throwable error)
    {
        super(error, 451, "Method parameter is missing.");
    }

    public MissingMethodParameterException(String message)
    {
        super(message, 451, "Method parameter is missing.");
    }

    public MissingMethodParameterException(String message, Throwable error)
    {
        super(message, error, 451, "Method parameter is missing.");
    }

    private static final long serialVersionUID = 0x94b742bb68141415L;
    public static final int HTTP_STATUS_CODE = 451;
    public static final String HTTP_STATUS_MESSAGE = "Method parameter is missing.";
}

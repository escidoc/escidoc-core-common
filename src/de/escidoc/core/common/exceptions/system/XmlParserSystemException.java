// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XmlParserSystemException.java

package de.escidoc.core.common.exceptions.system;


// Referenced classes of package de.escidoc.core.common.exceptions.system:
//            SystemException

public class XmlParserSystemException extends SystemException
{

    public XmlParserSystemException()
    {
        super(500, "Internal XML-Parser Error");
    }

    public XmlParserSystemException(Throwable error)
    {
        super(error, 500, "Internal XML-Parser Error");
    }

    public XmlParserSystemException(String message)
    {
        super(message, 500, "Internal XML-Parser Error");
    }

    public XmlParserSystemException(String message, Throwable error)
    {
        super(message, error, 500, "Internal XML-Parser Error");
    }

    private static final long serialVersionUID = 0xfec610a0f3ee69fcL;
    public static final int HTTP_STATUS_CODE = 500;
    public static final String HTTP_STATUS_MESSAGE = "Internal XML-Parser Error";
}

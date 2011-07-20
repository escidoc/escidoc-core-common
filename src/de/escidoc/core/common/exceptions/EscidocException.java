// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EscidocException.java

package de.escidoc.core.common.exceptions;


public abstract class EscidocException extends Exception
{

    public int getHttpStatusCode()
    {
        return httpStatusCode;
    }

    public String getHttpStatusMsg()
    {
        return httpStatusMsg;
    }

    public String toString()
    {
        return toXmlString();
    }

    public String toXmlString()
    {
        return null;
    }

    public String getHttpStatusLine()
    {
        return (new StringBuilder()).append("").append(httpStatusCode).append(" ").append(httpStatusMsg).toString();
    }

    public EscidocException()
    {
        httpStatusCode = 500;
        httpStatusMsg = "Internal eSciDoc Error";
    }

    public EscidocException(String message, Throwable cause)
    {
        super(message, cause);
        httpStatusCode = 500;
        httpStatusMsg = "Internal eSciDoc Error";
    }

    public EscidocException(String message)
    {
        super(message);
        httpStatusCode = 500;
        httpStatusMsg = "Internal eSciDoc Error";
    }

    public EscidocException(Throwable cause)
    {
        super("", cause);
        httpStatusCode = 500;
        httpStatusMsg = "Internal eSciDoc Error";
    }

    public EscidocException(int httpStatusCode, String httpStatusMsg)
    {
        this.httpStatusCode = 500;
        this.httpStatusMsg = "Internal eSciDoc Error";
        this.httpStatusCode = httpStatusCode;
        this.httpStatusMsg = httpStatusMsg;
    }

    public EscidocException(String message, Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super(message, cause);
        this.httpStatusCode = 500;
        this.httpStatusMsg = "Internal eSciDoc Error";
        this.httpStatusCode = httpStatusCode;
        this.httpStatusMsg = httpStatusMsg;
    }

    public EscidocException(String message, int httpStatusCode, String httpStatusMsg)
    {
        super(message);
        this.httpStatusCode = 500;
        this.httpStatusMsg = "Internal eSciDoc Error";
        this.httpStatusCode = httpStatusCode;
        this.httpStatusMsg = httpStatusMsg;
    }

    public EscidocException(Throwable cause, int httpStatusCode, String httpStatusMsg)
    {
        super("", cause);
        this.httpStatusCode = 500;
        this.httpStatusMsg = "Internal eSciDoc Error";
        this.httpStatusCode = httpStatusCode;
        this.httpStatusMsg = httpStatusMsg;
    }

    private static boolean hasHttpErrorCode(EscidocException e)
    {
        return e.getHttpStatusCode() >= 400;
    }

    public static String getStackTraceXml(Throwable e)
    {
        StringBuffer result = new StringBuffer("  <stack-trace><p><![CDATA[\n");
        StackTraceElement elements[] = e.getStackTrace();
        for(int i = 0; i < elements.length; i++)
        {
            result.append("    ");
            result.append(elements[i]);
            result.append("\n");
        }

        result.append("]]></p></stack-trace>\n");
        return result.toString();
    }

    private static final long serialVersionUID = 0xbcb66a705dd89ef0L;
    public static final int ESCIDOC_HTTP_SC_INVALID = 450;
    public static final int ESCIDOC_HTTP_SC_MISSING = 451;
    public static final int ESCIDOC_HTTP_SC_NOT_FOUND = 404;
    public static final int ESCIDOC_HTTP_SC_SECURITY = 302;
    public static final int ESCIDOC_HTTP_SC_VIOLATED = 409;
    public static final int ESCIDOC_HTTP_SC_BAD_REQUEST = 400;
    public static final int ESCIDOC_HTTP_SC_INTERNAL_SERVER_ERROR = 500;
    private int httpStatusCode;
    private String httpStatusMsg;
}

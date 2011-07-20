// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractElement.java

package de.escidoc.core.common.util.xml.stax.events;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

public abstract class AbstractElement
{

    protected AbstractElement(XMLStreamReader parser, String path)
    {
        this.parser = parser;
        this.path = path;
    }

    public int getLineNumber()
    {
        if(parser != null)
            return parser.getLocation().getLineNumber();
        else
            return -1;
    }

    public int getColumnNumber()
    {
        if(parser != null)
            return parser.getLocation().getColumnNumber();
        else
            return -1;
    }

    public String getLocationString()
    {
        return (new StringBuilder()).append("line ").append(getLineNumber()).append(", column ").append(getColumnNumber()).toString();
    }

    public String getPath()
    {
        return path;
    }

    private XMLStreamReader parser;
    private String path;
}

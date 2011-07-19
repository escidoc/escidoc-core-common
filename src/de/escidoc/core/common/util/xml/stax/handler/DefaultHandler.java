// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultHandler.java

package de.escidoc.core.common.util.xml.stax.handler;

import de.escidoc.core.common.util.xml.stax.events.*;

public abstract class DefaultHandler
{

    public DefaultHandler()
    {
        ready = false;
    }

    public StartElement startElement(StartElement element)
        throws Exception
    {
        return element;
    }

    public EndElement endElement(EndElement element)
        throws Exception
    {
        return element;
    }

    public String characters(String data, StartElement element)
        throws Exception
    {
        return data;
    }

    protected boolean isNotReady()
    {
        return !ready;
    }

    protected void setReady()
    {
        ready = true;
    }

    /**
     * @deprecated Method getAttributeValue is deprecated
     */

    protected String getAttributeValue(StartElement element, String namespace, String attributeName)
    {
        String typeValue = null;
        int indexOfType = element.indexOfAttribute(namespace, attributeName);
        if(indexOfType != -1)
        {
            Attribute type = element.getAttribute(indexOfType);
            typeValue = type.getValue();
        }
        return typeValue;
    }

    public static final char DISCARDED = 4;
    public static final char OPTIONAL = 2;
    public static final char MANDATORY = 1;
    public static final char FORBIDDEN = 0;
    public static final String MSG_MANDATORY_ATTRIBUTE_NOT_FOUND = "Mandatory attribute not found.";
    private boolean ready;
}

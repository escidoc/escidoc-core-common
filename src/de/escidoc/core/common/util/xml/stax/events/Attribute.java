// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Attribute.java

package de.escidoc.core.common.util.xml.stax.events;

import de.escidoc.core.common.exceptions.system.XmlParserSystemException;
import javax.xml.stream.XMLStreamReader;

public class Attribute
{

    public Attribute()
    {
        localName = null;
        namespace = null;
        prefix = null;
        value = null;
    }

    public Attribute(String localName, String namespace, String prefix, String value)
    {
        this.localName = localName;
        this.namespace = namespace;
        this.prefix = prefix;
        this.value = value;
    }

    public Attribute(XMLStreamReader parser, int index)
        throws XmlParserSystemException
    {
        localName = parser.getAttributeLocalName(index);
        namespace = parser.getAttributeNamespace(index);
        prefix = parser.getAttributePrefix(index);
        value = parser.getAttributeValue(index);
    }

    public String getLocalName()
    {
        return localName;
    }

    public void setLocalName(String localName)
    {
        this.localName = localName;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    private String localName;
    private String namespace;
    private String prefix;
    private String value;
}

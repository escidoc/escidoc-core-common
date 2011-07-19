// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EndElement.java

package de.escidoc.core.common.util.xml.stax.events;

import javax.xml.stream.XMLStreamReader;

// Referenced classes of package de.escidoc.core.common.util.xml.stax.events:
//            AbstractElement

public class EndElement extends AbstractElement
{

    public EndElement()
    {
        super(null, null);
        localName = null;
        namespace = null;
        prefix = null;
    }

    public EndElement(String localName, String namespace, String prefix)
    {
        super(null, null);
        this.localName = localName;
        this.namespace = namespace;
        this.prefix = prefix;
    }

    public EndElement(XMLStreamReader parser, String path)
    {
        super(parser, path);
        localName = parser.getLocalName();
        namespace = parser.getNamespaceURI();
        prefix = parser.getPrefix();
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

    private String localName;
    private String namespace;
    private String prefix;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StartElement.java

package de.escidoc.core.common.util.xml.stax.events;

import de.escidoc.core.common.exceptions.system.XmlParserSystemException;
import java.util.List;
import java.util.Vector;
import javax.naming.directory.NoSuchAttributeException;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;

// Referenced classes of package de.escidoc.core.common.util.xml.stax.events:
//            AbstractElement, Attribute

public class StartElement extends AbstractElement
{

    public StartElement()
    {
        super(null, null);
        position = 0;
        localName = null;
        namespace = null;
        prefix = null;
        nsContext = null;
        hasCharacters = false;
        hasChild = false;
        attributes = new Vector();
    }

    public StartElement(String localName, String namespace, String prefix, NamespaceContext nsContext)
    {
        super(null, null);
        position = 0;
        this.localName = localName;
        this.namespace = namespace;
        this.prefix = prefix;
        this.nsContext = nsContext;
        hasCharacters = false;
        hasChild = false;
        attributes = new Vector();
    }

    public StartElement(XMLStreamReader parser, String path)
        throws XmlParserSystemException
    {
        super(parser, path);
        position = 0;
        localName = parser.getLocalName();
        namespace = parser.getNamespaceURI();
        prefix = parser.getPrefix();
        nsContext = parser.getNamespaceContext();
        hasCharacters = false;
        hasChild = false;
        attributes = new Vector();
        int attCount = parser.getAttributeCount();
        for(int i = 0; i < attCount; i++)
        {
            Attribute attribute = new Attribute(parser, i);
            attributes.add(attribute);
        }

    }

    public Attribute getAttribute(int index)
        throws IndexOutOfBoundsException
    {
        return (Attribute)attributes.get(index);
    }

    public List getAttributes()
    {
        return attributes;
    }

    public NamespaceContext getNamespaceContext()
    {
        return nsContext;
    }

    public Attribute getAttribute(String namespaceUri, String localName)
        throws NoSuchAttributeException
    {
        Attribute result = null;
        int index = indexOfAttribute(namespaceUri, localName);
        if(index >= 0)
            result = getAttribute(index);
        else
            throw new NoSuchAttributeException((new StringBuilder()).append("No attribute {").append(namespaceUri).append("}").append(localName).append(".").toString());
        return result;
    }

    public boolean hasAttribute(String namespaceUri, String localName)
    {
        try
        {
            getAttribute(namespaceUri, localName);
        }
        catch(NoSuchAttributeException e)
        {
            return false;
        }
        return true;
    }

    public String getAttributeValue(String namespaceUri, String localName)
        throws NoSuchAttributeException
    {
        return getAttribute(namespaceUri, localName).getValue();
    }

    public int indexOfAttribute(String namespace, String localName)
    {
        int size = attributes.size();
        for(int i = 0; i < size; i++)
        {
            Attribute att = (Attribute)attributes.get(i);
            if(!att.getLocalName().equals(localName))
                continue;
            if(namespace == null || namespace.length() == 0)
            {
                String ns = att.getNamespace();
                if(ns == null || ns.length() == 0)
                    return i;
                continue;
            }
            if(att.getNamespace() != null && att.getNamespace().equals(namespace))
                return i;
        }

        return -1;
    }

    public void setAttribute(int index, Attribute att)
    {
        attributes.set(index, att);
    }

    public void removeAttribute(int index)
    {
        attributes.remove(index);
    }

    public void addAttribute(Attribute attribute)
    {
        attributes.add(attribute);
    }

    public int getAttributeCount()
    {
        return attributes.size();
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

    public boolean isEmpty()
    {
        return !hasCharacters && !hasChild;
    }

    public boolean hasCharacters()
    {
        return hasCharacters;
    }

    public void setHasCharacters(boolean hasCharacters)
    {
        this.hasCharacters = hasCharacters;
    }

    public boolean hasChild()
    {
        return hasChild;
    }

    public void setHasChild(boolean hasChild)
    {
        this.hasChild = hasChild;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    protected String localName;
    protected String namespace;
    protected String prefix;
    protected NamespaceContext nsContext;
    protected List attributes;
    protected boolean hasCharacters;
    protected boolean hasChild;
    private int position;
}

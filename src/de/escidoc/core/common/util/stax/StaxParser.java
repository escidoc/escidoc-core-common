// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StaxParser.java

package de.escidoc.core.common.util.stax;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.Constants;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.xml.stax.events.Attribute;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;
import de.escidoc.core.common.util.xml.stax.interfaces.DefaultHandlerStackInterface;

public class StaxParser
    implements DefaultHandlerStackInterface
{

    /**
     * The constructor.
     */
    public StaxParser() {
    }

    /**
     * The constructor.
     *
     * @param rootElementName The expected name of the root element. If the parsed document does not contain a root
     *                        element of the same name, an exception is thrown.
     */
    public StaxParser(final String rootElementName) {
        this.checkRootElementName = true;
        this.expectedName = rootElementName;
    }

    public String getCurPath()
    {
        return curPath.toString();
    }

    public String getXmlBase()
    {
        return xmlBase;
    }

    public void setHandlerChain(List hc)
    {
        handlerChain = hc;
    }

    public void clearHandlerChain()
    {
        handlerChain.clear();
    }

    public void parse(byte in[])
        throws SystemException
    {
        try
        {
            parseStream(new ByteArrayInputStream(in));
        }
        catch(Exception e)
        {
            throw new SystemException(e);
        }
    }

    public void parse(InputStream in)
        throws SystemException
    {
        if(handlerChain == null || handlerChain.size() == 0)
            throw new SystemException("Parser has no handlers. Try StaxParser sp.addHandler(new DefaultHandler());");
        try
        {
            parseStream(in);
        }
        catch(Exception e)
        {
            throw new SystemException(e);
        }
    }

    public void parse(String xml)
        throws SystemException
    {
        ByteArrayInputStream in = null;
        try
        {
            in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        }
        catch(UnsupportedEncodingException e)
        {
            throw new SystemException(e);
        }
        parse(((InputStream) (in)));
    }

    protected void parseStream(InputStream in)
        throws SystemException, Exception
    {
        final XMLStreamReader parser = factory.createXMLStreamReader(in, Constants.XML_CHARACTER_ENCODING);
        while (parser.hasNext()) {
            final int event = parser.next();
            switch (event) {

                case XMLStreamConstants.START_DOCUMENT:
                    init();
                    break;

                case XMLStreamConstants.END_DOCUMENT:
                    // close the XMLStreamReader or TODO reset the input stream
                    parser.close();
                    // reset for next run
                    this.started = false;
                    startElements.pop();
                    this.xmlBase = null;
                    break;

                case XMLStreamConstants.START_ELEMENT:
                    // bug?
                    if (!this.started) {
                        init();
                    }

                    startElements.peek().setHasChild(true);
                    final StartElement startElement =
                        new StartElement(parser.getLocalName(), parser.getNamespaceURI(), parser.getPrefix(), parser
                            .getNamespaceContext());
                    final int xmlBaseIndex =
                        startElement.indexOfAttribute("http://www.w3.org/XML/1998/namespace", "base");
                    if (xmlBaseIndex > -1) {
                        this.xmlBase = startElement.getAttribute(xmlBaseIndex).getValue();
                    }
                    // add attributes
                    final int attCount = parser.getAttributeCount();
                    for (int i = 0; i < attCount; i++) {
                        final Attribute attribute =
                            new Attribute(parser.getAttributeLocalName(i), parser.getAttributeNamespace(i), parser
                                .getAttributePrefix(i), parser.getAttributeValue(i));
                        startElement.addAttribute(attribute);
                    }

                    startElements.push(startElement);
                    curPath.append('/');
                    curPath.append(startElement.getLocalName());
                    handle(startElement);
                    break;

                case XMLStreamConstants.CHARACTERS:
                    final String data = parser.getText();
                    if (data.length() != 0) {
                        startElements.peek().setHasCharacters(true);
                        handle(data);
                    }
                    break;

                case XMLStreamConstants.CDATA:
                    final String cdata = "<![CDATA[" + parser.getText() + "]]>";
                    // FIXME cdata length is always != 0
                    if (cdata.length() != 0) {
                        startElements.peek().setHasCharacters(true);
                        handle(cdata);
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    if (startElements.peek().isEmpty()) {
                        handle("");
                    }
                    final EndElement endElement =
                        new EndElement(parser.getLocalName(), parser.getNamespaceURI(), parser.getPrefix());
                    handle(endElement);
                    startElements.pop();
                    curPath.setLength(curPath.lastIndexOf("/"));
                    break;

                default:
                    break;
            }
        }
    }

    protected void parseEvents(InputStream in)
        throws SystemException, Exception
    {
        final XMLEventReader parser = factory.createXMLEventReader(in);

        while (parser.hasNext()) {
            final XMLEvent event = parser.nextEvent();

            switch (event.getEventType()) {
                case XMLStreamConstants.START_DOCUMENT:
                    init();
                    break;

                case XMLStreamConstants.END_DOCUMENT:
                    // close the XMLStreamReader or TODO reset the input stream
                    parser.close();

                    // set ready
                    this.started = false;

                    // there have to be the root element
                    startElements.pop();
                    break;

                case XMLStreamConstants.START_ELEMENT:
                    // bug?
                    if (!this.started) {
                        init();
                    }
                    startElements.peek().setHasChild(true);
                    final javax.xml.stream.events.StartElement se = event.asStartElement();
                    final StartElement startElement =
                        new StartElement(se.getName().getLocalPart(), se.getName().getNamespaceURI(), se
                            .getName().getPrefix(), se.getNamespaceContext());
                    // add attributes
                    final Iterator<javax.xml.stream.events.Attribute> attIt = se.getAttributes();
                    while (attIt.hasNext()) {
                        final javax.xml.stream.events.Attribute a = attIt.next();
                        final QName name = a.getName();
                        final Attribute attribute =
                            new Attribute(name.getLocalPart(), name.getNamespaceURI(), name.getPrefix(), a.getValue());
                        startElement.addAttribute(attribute);
                    }
                    startElements.push(startElement);
                    curPath.append('/');
                    curPath.append(startElement.getLocalName());
                    handle(startElement);
                    break;

                case XMLStreamConstants.CHARACTERS:
                    final String data = event.asCharacters().getData();
                    if (data.length() != 0) {
                        startElements.peek().setHasCharacters(true);
                        handle(data);
                    }
                    break;

                case XMLStreamConstants.CDATA:
                    final String cdata = "<![CDATA[" + event.asCharacters().getData() + "]]>";
                    // FIXME this length is always != 0
                    if (cdata.length() != 0) {
                        startElements.peek().setHasCharacters(true);
                        handle(cdata);
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    final javax.xml.stream.events.EndElement ee = event.asEndElement();
                    final EndElement endElement =
                        new EndElement(ee.getName().getLocalPart(), ee.getName().getNamespaceURI(), ee
                            .getName().getPrefix());
                    startElements.pop();
                    curPath.setLength(curPath.lastIndexOf("/"));
                    handle(endElement);
                    break;
                default:
                    break;
            }
        }
    }

    protected void init()
    {
        startElements.clear();
        startElements.push(new StartElement("root", null, null, null));
        curPath.setLength(0);
        started = true;
    }

    public void addHandler(DefaultHandler dh)
    {
        handlerChain.add(dh);
    }

    public void addHandler(Collection c)
    {
        handlerChain.addAll(c);
    }

    public void addHandler(int index, DefaultHandler dh)
    {
        handlerChain.add(index, dh);
    }

    public List getHandlerChain()
    {
        return handlerChain;
    }

    protected void handle(StartElement startElement)
        throws SystemException
    {
        StartElement element = startElement;
        if(checkRootElementName && !rootChecked)
        {
            String localName = element.getLocalName();
            if(!expectedName.equals(localName))
                throw new SystemException((new StringBuilder()).append("Unexpected root element, expected ").append(expectedName).append("but was ").append(localName).append(".").toString());
            rootChecked = true;
        }
        int chainSize = handlerChain.size();
        for(int i = 0; i < chainSize; i++)
        {
            DefaultHandler handler = (DefaultHandler)handlerChain.get(i);
            if(handler == null)
                continue;
            try
            {
                element = handler.startElement(element);
            }
            catch(Exception ex)
            {
                throw new SystemException(ex);
            }
        }

    }

    protected void handle(EndElement endElement)
        throws SystemException
    {
        EndElement element = endElement;
        int chainSize = handlerChain.size();
        for(int i = 0; i < chainSize; i++)
        {
            DefaultHandler handler = (DefaultHandler)handlerChain.get(i);
            if(handler == null)
                continue;
            try
            {
                element = handler.endElement(element);
                continue;
            }
            catch(Exception ex)
            {
                LOG.error(ex);
                throw new SystemException(ex);
            }
        }

    }

    protected void handle(String characters)
        throws SystemException
    {
        String chars = characters;
        int chainSize = handlerChain.size();
        for(int i = 0; i < chainSize; i++)
        {
            DefaultHandler handler = (DefaultHandler)handlerChain.get(i);
            if(handler == null)
                continue;
            StartElement e = (StartElement)startElements.peek();
            try
            {
                chars = handler.characters(chars, e);
            }
            catch(Exception ex)
            {
                throw new SystemException(ex);
            }
        }

    }

    private static final AppLogger LOG = new AppLogger(StaxParser.class.getName());
    private boolean started;

    private List<DefaultHandler> handlerChain = new ArrayList<DefaultHandler>();

    private final Stack<StartElement> startElements = new Stack<StartElement>();

    private final StringBuffer curPath = new StringBuffer();

    private final XMLInputFactory factory = XMLInputFactory.newInstance();

    private String expectedName;

    private boolean checkRootElementName;

    private boolean rootChecked;

    private String xmlBase;


}

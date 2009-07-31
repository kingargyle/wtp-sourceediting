/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wtp.xerces.test;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.osgi.util.tracker.ServiceTracker;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

//TODO bug 263976 - copied from org.eclipse.core.internal.content.XMLRootHandler
/**
 * A content describer for detecting the name of the top-level element of the
 * DTD system identifier in an XML file. This supports two parameters:
 * <code>DTD_TO_FIND</code> and <code>ELEMENT_TO_FIND</code>. This is done
 * using the <code>IExecutableExtension</code> mechanism. If the
 * <code>":-"</code> method is used, then the value is treated as the
 * <code>ELEMENT_TO_FIND</code>.
 * 
 * @since 3.0
 */
public final class XMLRootHandler extends DefaultHandler implements LexicalHandler {
	/**
	 * An exception indicating that the parsing should stop. This is usually
	 * triggered when the top-level element has been found.
	 * 
	 * @since 3.0
	 */
	private class StopParsingException extends SAXException {
		/**
		 * All serializable objects should have a stable serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructs an instance of <code>StopParsingException</code> with a
		 * <code>null</code> detail message.
		 */
		public StopParsingException() {
			super((String) null);
		}
	}



	private static final int OSGI_SERVICE = 11;
	private static final int DIRECT_INSTANTIATION = 12;
	private static final int SERVICE_WITH_CONTEXT_CLASSLOADER = 13;
	// set to one of the above
	private int FACTORY_CREATION_METHOD = 11;

	private static final int DIRECT_CREATION = 101;
	private static final int CREATE_VIA_CONTEXT_CLASSLOADER = 102;
	private int PARSER_CREATION_METHOD = 102;


	/**
	 * Should we check the root element?
	 */
	private boolean checkRoot;
	/**
	 * The system identifier for the DTD that was found while parsing the XML.
	 * This member variable is <code>null</code> unless the file has been
	 * parsed successful to the point of finding the DTD's system identifier.
	 */
	private String dtdFound = null;
	/**
	 * This is the name of the top-level element found in the XML file. This
	 * member variable is <code>null</code> unless the file has been parsed
	 * successful to the point of finding the top-level element.
	 */
	private String elementFound = null;

	/**
	 * This is the namespace of the top-level element found in the XML file.
	 * This member variable is <code>null</code> unless the file has been
	 * parsed successful to the point of finding the top-level element.
	 */
	private String namespaceFound = null;

	private String versionFound = null;


	public XMLRootHandler(boolean checkRoot) {
		this.checkRoot = checkRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#comment(char[], int, int)
	 */
	public final void comment(final char[] ch, final int start, final int length) {
		// Not interested.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#endCDATA()
	 */
	public final void endCDATA() {
		// Not interested.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#endDTD()
	 */
	public final void endDTD() {
		// Not interested.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#endEntity(java.lang.String)
	 */
	public final void endEntity(final String name) {
		// Not interested.
	}

	public String getDTD() {
		return dtdFound;
	}

	public String getRootName() {
		return elementFound;
	}

	/**
	 * @since org.eclipse.core.contenttype 3.3
	 */
	public String getRootNamespace() {
		return namespaceFound;
	}

	public String getVersion() {
		return this.versionFound;
	}


	public boolean parseContents(InputSource contents) throws IOException, ParserConfigurationException, SAXException {
		boolean result = false;
		// Parse the file into we have what we need (or an error occurs).
		SAXParser parser = null;
		SAXParserFactory factory = null;

		switch (FACTORY_CREATION_METHOD) {
			case OSGI_SERVICE :
				factory = WTPTestXercesPlugin.instance().getFactoryWithOSGiService();
				break;
			case DIRECT_INSTANTIATION :
				factory = WTPTestXercesPlugin.instance().getFactoryWithDirectInstantiation();
				break;
			case SERVICE_WITH_CONTEXT_CLASSLOADER :
				factory = WTPTestXercesPlugin.instance().getFactoryWithThreadContextClassloader();
				break;
			default :
				throw new IllegalArgumentException("Program Error: invalid value provided for Factory Creation Method");
		}


		if (factory == null) {
			result = false;
		}
		else {

			System.out.println("SAXParserFactory class: " + factory.getClass());
			System.out.println("SAXParserFactory classloader: " + factory.getClass().getClassLoader());

			try {
				parser = factory.newSAXParser();
			}
			catch (ClassCastException e) {
				// catch and print class cast for test
				System.out.println("test Failed");
				System.out.println(e);
			}

			if (parser != null) {
				try {
					parser.parse(contents, this);
					// if we parse anything, just say true for this test
					result = true;
				}
				catch (StopParsingException e) {
					// Abort the parsing normally. Fall through...
				}
			}
		}


		return result;
	}

	/*
	 * Resolve external entity definitions to an empty string. This is to
	 * speed up processing of files with external DTDs. Not resolving the
	 * contents of the DTD is ok, as only the System ID of the DTD declaration
	 * is used.
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#resolveEntity(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
		return new InputSource(new StringReader("")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#startCDATA()
	 */
	public final void startCDATA() {
		// Not interested.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#startDTD(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public final void startDTD(final String name, final String publicId, final String systemId) throws SAXException {
		dtdFound = systemId;
		// If we don't care about the top-level element, we can stop here.
		if (!checkRoot)
			throw new StopParsingException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public final void startElement(final String uri, final String elementName, final String qualifiedName, final Attributes attributes) throws SAXException {
		elementFound = elementName;
		namespaceFound = uri;
		versionFound = attributes.getValue("version");
		throw new StopParsingException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#startEntity(java.lang.String)
	 */
	public final void startEntity(final String name) {
		// Not interested.
	}
}

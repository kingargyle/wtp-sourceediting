/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wtp.xerces.test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.xml.sax.SAXException;

public class TestXMLContentType implements IApplication { // extends TestCase

	private static final int OSGI_SERVICE = 11;
	private static final int DIRECT_INSTANTIATION = 12;
	private static final int SERVICE_WITH_CONTEXT_CLASSLOADER = 13;


	public TestXMLContentType() {
		super();
	}

	public Object start(IApplicationContext context) throws Exception {
		Object result = null;
		boolean testOk = false;

		testOk = testGetParser(DIRECT_INSTANTIATION);
		if (testOk) {
			System.out.println("\nTest OK");
			result = IApplication.EXIT_OK;
		}
		else {
			System.out.println("\nTest Failed");
			result = new Boolean(false);
		}
		/*
		 * Note: SERVICE_WITH_CONTEXT_CLASSLOADER and OSGI_SERVICE are order
		 * sensitive.
		 * 
		 * Both tests fail if OSGI_SERVICE method used first (registers a
		 * "base class loader" version of factory for all time.
		 * 
		 * Both tests pass if SERVICE_WITH_CONTEXT_CLASSLOADER method used
		 * first (registers a xerces bundle version of factory).
		 * 
		 * The DIRECT_INSTANTIATION method seems to always work. 
		 */
		testOk = testGetParser(SERVICE_WITH_CONTEXT_CLASSLOADER);
		if (testOk) {
			System.out.println("\nTest OK");
			result = IApplication.EXIT_OK;
		}
		else {
			System.out.println("\nTest Failed");
			result = new Boolean(false);
		}
		testOk = testGetParser(OSGI_SERVICE);
		if (testOk) {
			System.out.println("\nTest OK");
			result = IApplication.EXIT_OK;
		}
		else {
			System.out.println("\nTest Failed");
			result = new Boolean(false);
		}
		testOk = testGetParser(DIRECT_INSTANTIATION);
		if (testOk) {
			System.out.println("\nTest OK");
			result = IApplication.EXIT_OK;
		}
		else {
			System.out.println("\nTest Failed");
			result = new Boolean(false);
		}
		return result;
	}


	public void stop() {
		// no need to do any thing
	}

	public boolean testGetParser(int factoryMethod) throws ParserConfigurationException, SAXException {
		boolean result = false;

		SAXParser parser = null;
		SAXParserFactory factory = null;

		switch (factoryMethod) {
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
				// if we get a parser, just say true for this test
				result = true;
			}
			catch (final ClassCastException e) {
				// catch and print class cast for test
				System.out.println(e);
				result = false;
			}

		}


		return result;
	}
}

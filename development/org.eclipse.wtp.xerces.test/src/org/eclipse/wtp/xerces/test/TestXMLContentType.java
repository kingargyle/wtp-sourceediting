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

import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.xml.sax.SAXException;

public class TestXMLContentType implements IApplication { // extends TestCase

	private static final int OSGI_SERVICE = 11;
	private static final int DIRECT_INSTANTIATION = 12;
	private static final int SERVICE_WITH_CONTEXT_CLASSLOADER = 13;
	private static final int OSGI_SERVICE_BASE_CLASSLOADER = 14;
	private static final String TCCLParam = "-useTCCL";
	private boolean USE_TCCL_DEFAULT = false;
	private Boolean applicationResult;


	public TestXMLContentType() {
		super();
	}

	public Object start(IApplicationContext context) throws Exception {
		applicationResult = null;
		boolean use_tccl = checkArgs(context);
		applicationResult = doTests(use_tccl);
		return applicationResult;
	}

	private boolean checkArgs(IApplicationContext context) {
		boolean result = USE_TCCL_DEFAULT;
		Map argmap = context.getArguments();
		String[] args = (String[]) argmap.get("application.args");
		for (int i = 0; i < args.length; i++) {
			if (TCCLParam.equals(args[i])) {
				result = true;
				break;
			}
		}
		return result;
	}

	public Boolean doTests(boolean useTcclTest) throws ParserConfigurationException, SAXException {
		boolean testOk = false;
		String testName = null;

		testName = "DIRECT_INSTANTIATION";
		System.out.println("\n     Do test: " + testName + "\n");
		testOk = testGetParser(DIRECT_INSTANTIATION, true);
		handleTestResult(testOk, testName);
		testOk = testGetParser(DIRECT_INSTANTIATION, false);
		handleTestResult(testOk, testName);

		/*
		 * Note: SERVICE_WITH_CONTEXT_CLASSLOADER and OSGI_SERVICE are order
		 * sensitive. Only one is ran per invocation.
		 * 
		 * Do "failing" method by default. Use the -useTCCL to show works ok
		 * with TCCL.
		 * 
		 * The DIRECT_INSTANTIATION method seems to always work.
		 */
		if (useTcclTest) {
			testName = "OSGI_SERVICE_WITH_THREAD_CONTEXT_CLASSLOADER";
			System.out.println("\n       Do test: " + testName + "\n");
			testOk = testGetParser(SERVICE_WITH_CONTEXT_CLASSLOADER, true);
			handleTestResult(testOk, testName);
			testOk = testGetParser(SERVICE_WITH_CONTEXT_CLASSLOADER, false);
			handleTestResult(testOk, testName);
		}
		else {
			if (new Boolean(false)) {
				testName = "OSGI_SERVICE_WITHOUT_THREAD_CONTEXT_CLASSLOADER";
				System.out.println("\n     Do test: " + testName + "\n");
				testOk = testGetParser(OSGI_SERVICE, true);
				handleTestResult(testOk, testName);
				testOk = testGetParser(OSGI_SERVICE, false);
				handleTestResult(testOk, testName);
			}
			else {
				testName = "OSGI_SERVICE_BASE_CLASSLOADER";
				System.out.println("\n     Do test: " + testName + "\n");
				testOk = testGetParser(OSGI_SERVICE_BASE_CLASSLOADER, true);
				handleTestResult(testOk, testName);
				testOk = testGetParser(OSGI_SERVICE_BASE_CLASSLOADER, false);
				handleTestResult(testOk, testName);
			}
		}

		return testOk;
	}

	private void handleTestResult(boolean testOk, String testName) {
		/*
		 * always set application result if its null, but do not change to
		 * true, if it is already false.
		 */
		if (applicationResult == null) {
			applicationResult = new Boolean(testOk);
		}
		else if (!testOk) {
			applicationResult = new Boolean(false);
		}

		if (testOk) {
			System.out.println("\n     Test OK: " + testName + "\n");
		}
		else {
			System.out.println("\n     Test Failed: " + testName + "\n");
		}
	}

	public void stop() {
		// no need to do any thing
	}

	public boolean testGetParser(int factoryMethod, boolean sax) throws ParserConfigurationException, SAXException {
		boolean result = false;

		Object parser = null;
		Object factory = null;

		switch (factoryMethod) {
			case OSGI_SERVICE :
			case OSGI_SERVICE_BASE_CLASSLOADER :
				factory = WTPTestXercesPlugin.instance().getFactoryWithOSGiService(sax);
				break;
			case DIRECT_INSTANTIATION :
				factory = WTPTestXercesPlugin.instance().getFactoryWithDirectInstantiation(sax);
				break;
			case SERVICE_WITH_CONTEXT_CLASSLOADER :
				factory = WTPTestXercesPlugin.instance().getFactoryWithThreadContextClassloader(sax);
				break;
			default :
				throw new IllegalArgumentException("Program Error: invalid value provided for Factory Creation Method");
		}


		if (factory == null) {
			result = false;
		}
		else {

			System.out.println("ParserFactory class: " + factory.getClass());
			System.out.println("ParserFactory classloader: " + factory.getClass().getClassLoader());

			try {
				if (OSGI_SERVICE_BASE_CLASSLOADER == factoryMethod) {
					ClassLoader saveClassLoader = Thread.currentThread().getContextClassLoader();
					try {
						Thread.currentThread().setContextClassLoader(factory.getClass().getClassLoader());
						parser = sax ? ((SAXParserFactory) factory).newSAXParser() : ((DocumentBuilderFactory) factory).newDocumentBuilder();
					}
					finally {
						Thread.currentThread().setContextClassLoader(saveClassLoader);
					}
				}
				else {
					parser = sax ? ((SAXParserFactory) factory).newSAXParser() : ((DocumentBuilderFactory) factory).newDocumentBuilder();
				}
				// if we get a parser, just say true for this test
				System.out.println("Parser class: " + parser.getClass());
				System.out.println("Parser classloader: " + parser.getClass().getClassLoader());
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

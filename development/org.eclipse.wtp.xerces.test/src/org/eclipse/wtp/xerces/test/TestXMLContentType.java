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
		testOk = testGetParser(DIRECT_INSTANTIATION);
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
			testOk = testGetParser(SERVICE_WITH_CONTEXT_CLASSLOADER);
			handleTestResult(testOk, testName);
		}
		else {
			System.out.println("\n  Doing failing order tests. \n");
			testName = "OSGI_SERVICE_WITHOUT_THREAD_CONTEXT_CLASSLOADER";
			System.out.println("\n     Do test: " + testName + "\n");
			testOk = testGetParser(OSGI_SERVICE);
			handleTestResult(testOk, testName);


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

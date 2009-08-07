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

/**
 * Simple class to test various methods of getting DOM and SAX Factories and Parsers. 
 * Note: not a good "how to" example. 
 * For background, see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=285505.
 * @author davidw
 *
 */

public class TestXercesClassloading implements IApplication {

	/* Three ways to get Factories */
	private static final int OSGI_SERVICE_WITHOUT_CONTEXT_CLASSLOADER = 11;
	private static final int DIRECT_INSTANTIATION = 12;
	private static final int OSGI_SERVICE_WITH_CONTEXT_CLASSLOADER = 13;

	/*
	 * Two ways to get Parsers from the factories
	 * 
	 * PARSER_WITH_BUNDLE_CLASSLOADER: This is the normal way, of just using
	 * "newParser()" in your code.
	 * 
	 * PARSER_WITH_FACTORY_CLASSLOADER: This was added as a test case to
	 * explore if we'd break people who (incorrectly) got the Factory while
	 * NOT using the TCCL but then got the parser while using the TCCL. I
	 * don't think this would work well, in practice, but techically it could
	 * be done ... so, just wanted to be sure.
	 */
	private static final int PARSER_WITH_FACTORY_CLASSLOADER = 21;
	private static final int PARSER_WITH_BUNDLE_CLASSLOADER = 22;

	private static final String TCCLParam = "-useTCCL";
	private boolean USE_TCCL_DEFAULT = false;
	private Boolean applicationResult;


	public TestXercesClassloading() {
		super();
	}

	public Object start(IApplicationContext applicationContext) throws Exception {

		boolean use_tccl = checkArgs(applicationContext);
		doTests(use_tccl);
		if (applicationResult.booleanValue()) {
			System.out.println("Overall test ok.");
			return IApplication.EXIT_OK;
		}
		else {
			System.out.println("One or more tests failed.");
			return Boolean.FALSE;
		}

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

	public void doTests(boolean useTcclTest) throws ParserConfigurationException, SAXException {
		
		String testName = null;

		testName = "DIRECT_INSTANTIATION";
		System.out.println("\n     Do test: " + testName);
		testGetParser(DIRECT_INSTANTIATION, true, testName);
		testGetParser(DIRECT_INSTANTIATION, false, testName);
		

		/*
		 * Note: SERVICE_WITH_CONTEXT_CLASSLOADER and OSGI_SERVICE are order
		 * sensitive. Only one is ran per invocation.
		 * 
		 * Do "failing" method by default. Use the -useTCCL to show works ok
		 * with TCCL.
		 * 
		 * The DIRECT_INSTANTIATION method seems to always work.
		 */
		// if (!useTcclTest) {
		testName = "OSGI_SERVICE_WITHOUT_THREAD_CONTEXT_CLASSLOADER";
		System.out.println("\n     Do test: " + testName);
		testGetParser(OSGI_SERVICE_WITHOUT_CONTEXT_CLASSLOADER, true, testName);
		testGetParser(OSGI_SERVICE_WITHOUT_CONTEXT_CLASSLOADER, false, testName);

// }
		testName = "OSGI_SERVICE_WITH_THREAD_CONTEXT_CLASSLOADER";
		System.out.println("\n       Do test: " + testName);
		testGetParser(OSGI_SERVICE_WITH_CONTEXT_CLASSLOADER, true, testName);
		testGetParser(OSGI_SERVICE_WITH_CONTEXT_CLASSLOADER, false, testName);

// else {
// if (new Boolean(true)) {

// }
// else {
		testName = "OSGI_SERVICE_WITH_BASE_CLASSLOADER";
		System.out.println("\n     Do test: " + testName);
		testGetParser(PARSER_WITH_FACTORY_CLASSLOADER, true, testName);
		testGetParser(PARSER_WITH_FACTORY_CLASSLOADER, false, testName);
// }
// }

	}

	private void testGetParser(int directInstantiation, boolean b, String testName) throws ParserConfigurationException, SAXException {

		testGetParser(directInstantiation, b);
		
	}

	private void testGetParser(int factoryMethod, boolean parserType) throws ParserConfigurationException, SAXException {
		boolean result = false;
		System.out.println("\n     with Variation: " + "PARSER_WITH_BUNDLE_CLASSLOADER" + "\n");
		testGetParser(factoryMethod, PARSER_WITH_BUNDLE_CLASSLOADER, parserType);
		System.out.println("\n     with Variation: " + "PARSER_WITH_FACTORY_CLASSLOADER" + "\n");
		testGetParser(factoryMethod, PARSER_WITH_FACTORY_CLASSLOADER, parserType);
	}

	private void handleTestResult(boolean testResult) {
		/*
		 * always set application result if its null, but do not change to
		 * true, if it is already false.
		 */
		if (applicationResult == null) {
			applicationResult = new Boolean(testResult);
		}
		else if (!testResult) {
			applicationResult = new Boolean(false);
		}

		if (testResult) {
			System.out.println("\n     Test OK \n");
		}
		else {
			System.out.println("\n     Test Failed \n");
		}
	}

	public void stop() {
		// no need to do any thing
	}

	private void testGetParser(int factoryMethod, int parserMethod, boolean sax) throws ParserConfigurationException, SAXException {
		boolean result = false;

		Object parser = null;
		Object factory = null;

		switch (factoryMethod) {
			case OSGI_SERVICE_WITHOUT_CONTEXT_CLASSLOADER :
			case PARSER_WITH_FACTORY_CLASSLOADER :
				factory = WTPTestXercesPlugin.instance().getFactoryWithOSGiService(sax);
				break;
			case DIRECT_INSTANTIATION :
				factory = WTPTestXercesPlugin.instance().getFactoryWithDirectInstantiation(sax);
				break;
			case OSGI_SERVICE_WITH_CONTEXT_CLASSLOADER :
				factory = WTPTestXercesPlugin.instance().getFactoryWithThreadContextClassloader(sax);
				break;
			default :
				throw new IllegalArgumentException("Program Error: invalid value provided for Factory Creation Method");
		}


		if (factory == null) {
			throw new RuntimeException("Program or Test setup Error: No factory available!");
		}

		System.out.println("ParserFactory class: " + factory.getClass());
		System.out.println("ParserFactory classloader: " + factory.getClass().getClassLoader());

		getParser(parserMethod, sax, factory);

	}

	private void getParser(int parserMethod, boolean sax, Object factory) throws ParserConfigurationException, SAXException {
		boolean result = false;
		Object parser = null;
		try {
			switch (parserMethod) {
				case PARSER_WITH_FACTORY_CLASSLOADER :
					ClassLoader saveClassLoader = Thread.currentThread().getContextClassLoader();
					try {
						Thread.currentThread().setContextClassLoader(factory.getClass().getClassLoader());
						parser = getParser(sax, factory);
					}
					finally {
						Thread.currentThread().setContextClassLoader(saveClassLoader);
					}
					break;

				case PARSER_WITH_BUNDLE_CLASSLOADER :
					parser = getParser(sax, factory);
					break;
				default :
					throw new IllegalArgumentException("Program Error: invalid value provided for Parser Creation Method");

			}
			/*
			 * if we get a parser without Exception, say "pass" for this test
			 */
			System.out.println("Parser class: " + parser.getClass());
			System.out.println("Parser classloader: " + parser.getClass().getClassLoader());
			handleTestResult(true);

		}
		catch (final ClassCastException e) {
			// catch and print class cast for test
			System.out.println(e);
			handleTestResult(false);
		}

	}

	private Object getParser(boolean sax, Object factory) throws ParserConfigurationException, SAXException {
		Object parser;
		if (sax) {
			parser = ((SAXParserFactory) factory).newSAXParser();
		}
		else {
			parser = ((DocumentBuilderFactory) factory).newDocumentBuilder();
		}
		return parser;
	}
}

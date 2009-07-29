package org.eclipse.wst.sse.tokenizerJitTest;

/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * This class was based on a JUnit test, HTMLHeadTokenizerTester, and then
 * simplified to run as a standalone Java app, to test for JIT errors.
 * 
 * @author davidw
 * 
 */
public class TokenizerTester {
	private long startTime;
	private static String sillyString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + "<geronimo-plugin xmlns=\"http://geronimo.apache.org/xml/ns/plugins-1.3\" xmlns:ns2=\"http://geronimo.apache.org/xml/ns/attributes-1.2\">\r\n" + "    <name>Geronimo Plugins, Client :: Client</name>\r\n" + "    <category>Geronimo Core</category>\r\n" + "    <description>Client plugin</description>\r\n" + "    <url>http://geronimo.apache.org/</url>\r\n" + "    <author>The Apache Geronimo development community</author>\r\n" + "    <license osi-approved=\"true\">The Apache Software License, Version 2.0</license>\r\n" + "    <plugin-artifact>\r\n" + "        <module-id>\r\n" + "            <groupId>org.apache.geronimo.configs</groupId>\r\n" + "            <artifactId>client</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>car</type>\r\n" + "        </module-id>\r\n" + "        <geronimo-version>2.1</geronimo-version>\r\n"
				+ "        <jvm-version>1.5</jvm-version>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>client-system</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>car</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>jee-specs</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>car</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-core</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n"
				+ "            <artifactId>geronimo-interceptor</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-naming</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-j2ee</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-management</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n"
				+ "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-security</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-crypto</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.modules</groupId>\r\n" + "            <artifactId>geronimo-client</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.modules</groupId>\r\n"
				+ "            <artifactId>geronimo-openejb</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.xbean</groupId>\r\n" + "            <artifactId>xbean-reflect</artifactId>\r\n" + "            <version>3.3</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.xbean</groupId>\r\n" + "            <artifactId>xbean-naming</artifactId>\r\n" + "            <version>3.3</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <source-repository>~/.m2/repository/</source-repository>\r\n" + "        <source-repository>http://repo1.maven.org/maven2/</source-repository>\r\n" + "        <source-repository>http://people.apache.org/repo/m2-snapshot-repository/</source-repository>\r\n"
				+ "        <source-repository>http://people.apache.org/repo/m2-incubating-repository/</source-repository>\r\n" + "        <config-xml-content load=\"false\"/>\r\n" + "        <artifact-alias server=\"client\" key=\"org.apache.geronimo.configs/j2ee-server//car\">org.apache.geronimo.configs/client/2.1/car</artifact-alias>\r\n" + "        <artifact-alias server=\"client\" key=\"org.apache.geronimo.configs/j2ee-server/2.1/car\">org.apache.geronimo.configs/client/2.1/car</artifact-alias>\r\n" + "    </plugin-artifact>\r\n" + "</geronimo-plugin>\r\n" + "";


	public static void main(String[] args) {
		final TokenizerTester tester = new TokenizerTester();
		try {
			tester.testRepeatedly();
		}
		catch (final InterruptedException e) {
			System.out.println("     program ended via interuption");
		}
		catch (final Exception e) {
			System.out.println("     unexpected program error: " + e.getMessage());
		}
	}

	private final boolean DEBUG = false;
	private final static int MAX_SCANS = 50000;
	private final static int MAX_EXCEPTIONS = 5;
	private final static int PRINT_INTERVAL = 10000;

	private int nScans;

	private int previousPrint;
	private int nExceptions;

	private boolean checkMaxScans(boolean printTime) {
		if (printTime && (previousPrint != nScans) && ((nScans % PRINT_INTERVAL) == 0)) {
			final long elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println();
			System.out.println("               " + "number of scans: " + nScans);
			System.out.println("               " + "  elapsed time (msecs): " + elapsedTime);
			/*
			 * occasionally, due to ignoring Errors, this method is called
			 * twice where nScans has not incremented. No need to print
			 * info twice if no change.
			 */
			previousPrint = nScans;
		}
		return (nScans >= MAX_SCANS) || (nExceptions >= MAX_EXCEPTIONS);
	}

	private void doTestScan() {
		Reader stringReader = new StringReader(sillyString);
		HTMLHeadTokenizer tokenizer = null;
		try {
			tokenizer = new HTMLHeadTokenizer(stringReader);
			parseHeader(tokenizer);
			nScans++;
		}
		catch (final IOException e) {
			// ignore, since for directories in general, there can be
			// lots of errors based on "access denied", etc.
		}
		catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("   number of scans: " + nScans);
			nExceptions++;
		}
	}

	/**
	 * just scan
	 */
	private void parseHeader(HTMLHeadTokenizer tokenizer) throws IOException {

		do {
			HeadParserToken token = tokenizer.getNextToken();
			if (this.DEBUG) {
				System.out.println(token);
			}
		}
		while (tokenizer.hasMoreTokens());

		return;

	}

	public void testRepeatedly() throws Exception {
		System.out.println();
		System.out.println("     Running Tokenizer scan repeatedly on string reader ");

		System.out.println("          " + "for a maximum number of times: " + MAX_SCANS);
		String version = System.getProperty("java.fullversion");
		if (version == null) {
			version = System.getProperty("java.vendor") + "  " + System.getProperty("java.version");
			System.out.println("          " + "Using Java version: " + version);
		}
		else {
			System.out.println("          " + "Using Java version: ");
			System.out.println(version);
		}

		startTime = System.currentTimeMillis();

		for (int i = 0; i < MAX_SCANS; i++) {
			doTestScan();
			if (checkMaxScans(true)) {
				break;
			}
		}
		if (checkMaxScans(false)) {
			System.out.println("     Test Complete. Maximum exceptions (" + MAX_EXCEPTIONS + ") or scans (" + MAX_SCANS + ") reached");
		}
		else {
			System.out.println("     Test Complete. Number of scans: " + nScans);
		}
	}
}

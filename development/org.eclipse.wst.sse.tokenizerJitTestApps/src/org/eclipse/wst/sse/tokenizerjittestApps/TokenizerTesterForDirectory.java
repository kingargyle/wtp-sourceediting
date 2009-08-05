package org.eclipse.wst.sse.tokenizerjittestApps;

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
import java.util.Date;
import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * This class was based on a JUnit test, HTMLHeadTokenizerTester, and then
 * simplified to run as a standalone Java app, to test for JIT errors.
 * 
 * @author davidw
 * 
 */
public abstract class TokenizerTesterForDirectory implements IApplication {

	protected Boolean DEBUG = false;
	private static String sillyString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + "<geronimo-plugin xmlns=\"http://geronimo.apache.org/xml/ns/plugins-1.3\" xmlns:ns2=\"http://geronimo.apache.org/xml/ns/attributes-1.2\">\r\n" + "    <name>Geronimo Plugins, Client :: Client</name>\r\n" + "    <category>Geronimo Core</category>\r\n" + "    <description>Client plugin</description>\r\n" + "    <url>http://geronimo.apache.org/</url>\r\n" + "    <author>The Apache Geronimo development community</author>\r\n" + "    <license osi-approved=\"true\">The Apache Software License, Version 2.0</license>\r\n" + "    <plugin-artifact>\r\n" + "        <module-id>\r\n" + "            <groupId>org.apache.geronimo.configs</groupId>\r\n" + "            <artifactId>client</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>car</type>\r\n" + "        </module-id>\r\n" + "        <geronimo-version>2.1</geronimo-version>\r\n"
				+ "        <jvm-version>1.5</jvm-version>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>client-system</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>car</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>jee-specs</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>car</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-core</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n"
				+ "            <artifactId>geronimo-interceptor</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-naming</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-j2ee</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-management</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n"
				+ "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-security</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.framework</groupId>\r\n" + "            <artifactId>geronimo-crypto</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.modules</groupId>\r\n" + "            <artifactId>geronimo-client</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.geronimo.modules</groupId>\r\n"
				+ "            <artifactId>geronimo-openejb</artifactId>\r\n" + "            <version>2.1</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.xbean</groupId>\r\n" + "            <artifactId>xbean-reflect</artifactId>\r\n" + "            <version>3.3</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <dependency>\r\n" + "            <groupId>org.apache.xbean</groupId>\r\n" + "            <artifactId>xbean-naming</artifactId>\r\n" + "            <version>3.3</version>\r\n" + "            <type>jar</type>\r\n" + "        </dependency>\r\n" + "        <source-repository>~/.m2/repository/</source-repository>\r\n" + "        <source-repository>http://repo1.maven.org/maven2/</source-repository>\r\n" + "        <source-repository>http://people.apache.org/repo/m2-snapshot-repository/</source-repository>\r\n"
				+ "        <source-repository>http://people.apache.org/repo/m2-incubating-repository/</source-repository>\r\n" + "        <config-xml-content load=\"false\"/>\r\n" + "        <artifact-alias server=\"client\" key=\"org.apache.geronimo.configs/j2ee-server//car\">org.apache.geronimo.configs/client/2.1/car</artifact-alias>\r\n" + "        <artifact-alias server=\"client\" key=\"org.apache.geronimo.configs/j2ee-server/2.1/car\">org.apache.geronimo.configs/client/2.1/car</artifact-alias>\r\n" + "    </plugin-artifact>\r\n" + "</geronimo-plugin>\r\n" + "";

	private static double round(double val, final int places) {
		final long factor = (long) Math.pow(10, places);

		// Shift the decimal the correct number of places
		// to the right.
		val = val * factor;

		// Round to the nearest integer.
		final long tmp = Math.round(val);

		// Shift the decimal the correct number of places
		// back to the left.
		return (double) tmp / factor;
	}

	private int MAX_SCANS = 50000;
	
	private int TIME_PRINT_INTERVAL = 10000;
	private long startTime;
	private int nScans;
	private int totalErrors;
	private int nScansTotal;
	private int totalErrorsTotal;

	private int previousPrintedScans;

	private boolean checkMaxScans(final boolean printTime) {
		if (printTime && previousPrintedScans != nScans && nScans % TIME_PRINT_INTERVAL == 0) {
			final String elapsedTime = computeElapsedTime();
			System.out.println();
			System.out.println("               " + "Progress: number of scans: " + nScans);
			System.out.println("               " + "  elapsed time: " + elapsedTime);
			System.out.flush();
			/*
			 * No need to print info twice if no change.
			 */
			previousPrintedScans = nScans;
		}
		return nScans >= MAX_SCANS;
	}

	private String computeElapsedTime() {
		String result = null;
		final long elapsedTime = System.currentTimeMillis() - startTime;
		if (elapsedTime < 1000) {
			result = elapsedTime + " msecs";
		}
		else if (elapsedTime < 1000 * 60) {
			result = elapsedTime / 1000.0 + " secs";
		}
		else {
			result = round((elapsedTime / 1000.0 / 60.0), 2) + " mins";
		}
		return result;
	}

	private void doFinallyClose(final Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			}
			catch (final IOException e1) {
				// ignore
			}
		}
	}

	abstract protected void doTest(Reader reader) throws Exception;

	private void doTestDir() {

		while (true) {

			doTestScan();

			if (checkMaxScans(true)) {
				break;
			}
		}
	}

	private void doTestScan() {

		Reader reader = null;
		try {
			reader = new StringReader(sillyString);
			doTest(reader);
			/* Do not increment counter, if exception thrown during scan */
			nScans++;
		}
		catch (final IOException e) {
			/*
			 * should be none for string reader
			 */
		}
		catch (final Exception e) {
			handleTestException(reader, e);
		}
		finally {
			doFinallyClose(reader);
		}
	}

	private String[] getArguments(final IApplicationContext context) {
		String[] args = null;
		final Map argMap = context.getArguments();
		final String[] applicationArgs = (String[]) argMap.get("application.args");
		if (applicationArgs != null && applicationArgs.length > 0) {
			args = applicationArgs;
		}
		else {
			args = new String[]{};
		}
		return args;
	}

	abstract protected String getTokenizerName();

	private void handleException(final Exception e) {
		System.out.println("     unexpected program error: " + e.getMessage());
	}

	private void handleInterrupt() {
		System.out.println("     program ended via interuption");
	}

	private void handleTestException(final Reader reader, final Exception e) {
		System.out.println("Exception during scan: " + e.getMessage());
		System.out.println("   number of scans: " + nScans);
		System.out.println("   total (cumlative) errors: " + ++totalErrors);
		doFinallyClose(reader);
	}

	private String[] initialAppActions(final IApplicationContext context, final String tokenizerName) {
		final String[] args = getArguments(context);
		startTime = System.currentTimeMillis();
		return args;
	}

	private void printHeader(final String[] dirNames, final String tokenizerName) {
		System.out.println();
		System.out.println("     Running " + tokenizerName + " scan repeatedly on same string");
		for (int i = 0; i < dirNames.length; i++) {
			String dirName = dirNames[i];
			System.out.println("               " + dirName);
		}

		System.out.println("          " + "for a maximum number of scans: " + MAX_SCANS);
		System.out.println();
		String version = System.getProperty("java.fullversion");
		if (version == null) {
			version = System.getProperty("java.vendor") + "  " + System.getProperty("java.version");
			System.out.println("          " + "Using Java version: " + version);
		}
		else {
			System.out.println("          " + "Using Java version: ");
			System.out.println(version);
		}
		System.out.println();
		System.out.println("Starting at " + new Date());
		System.out.println();
	}

	public Object start(final IApplicationContext context) throws Exception {
		final String tokenizerName = getTokenizerName();
		final String dirNames[] = initialAppActions(context, tokenizerName);
		printHeader(dirNames, tokenizerName);

		nScans = 0;
		totalErrors = 0;
		testRepeatedScans();
		printDirTail();
		nScansTotal += nScans;
		totalErrorsTotal += totalErrors;

		printTail();


		return IApplication.EXIT_OK;
	}

	public void stop() {
		// nothing to clean up
	}

	private void testRepeatedScans() throws Exception {


		doTestDir();


	}

	private void printTail() {
		System.out.println();
		System.out.println("     Total Test Complete. Number of total scans : " + nScansTotal);
		System.out.println("     Total Errors: " + totalErrorsTotal);
	}

	private void printDirTail() {
		System.out.println();
		if (checkMaxScans(false)) {
			System.out.println("     Test Complete. Reached maximum scans: " + nScans);
		}
		else {
			System.out.println("     Test Complete. Number of scans: " + nScans);
		}
		System.out.println("     Total Errors: " + totalErrors);
	}

}

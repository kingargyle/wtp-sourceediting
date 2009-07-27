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


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

/**
 * This class was based on a JUnit test, HTMLHeadTokenizerTester, and then
 * simplified to run as a standalone Java app, to test for JIT errors.
 * 
 * @author davidw
 * 
 */
public class HTMLHeadTokenizerTesterForDirectory {
	private static long startTime;

	public static void main(String[] args) {
		final HTMLHeadTokenizerTesterForDirectory tester = new HTMLHeadTokenizerTesterForDirectory();
		String dirName = defaultDirName;
		if (args.length > 0) {
			dirName = args[0];
		}
		try {
			System.out.println();
			System.out.println("     Running HTMLHEADTokenizer scan on all files in directory ");
			System.out.println("          " + dirName);
			System.out.println("          " + "for a maximum number of files: " + MAX_FILES);
			String version = System.getProperty("java.fullversion");
			if (version == null) {
				version = System.getProperty("java.vendor") + "  " + System.getProperty("java.version");
				System.out.println("          " + "Using Java version: " + version);
			} else {
				System.out.println("          " + "Using Java version: ");
				System.out.println(version);	
			}
			

			startTime = System.currentTimeMillis();
			tester.testAllFilesInDirectory(dirName);
		}
		catch (final InterruptedException e) {
			System.out.println("     program ended via interuption");
		}
		catch (final Exception e) {
			System.out.println("     unexpected program error: " + e.getMessage());
		}
	}

	private final boolean DEBUG = false;
	private final static int MAX_FILES = 50000;
	// provide generic, platform independent default directory
	private final static String defaultDirName = "/";
	private final static int TIME_PRINT_INTERVAL = 2000;

	private int nFilesScanned;

	private int previousPrintedScannedFiles;

	private boolean checkMaxFiles(boolean printTime) {
		if (printTime && (previousPrintedScannedFiles != nFilesScanned) && ((nFilesScanned % TIME_PRINT_INTERVAL) == 0)) {
			final long elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println();
			System.out.println("               " + "number of files scanned: " + nFilesScanned);
			System.out.println("               " + "  elapsed time (msecs): " + elapsedTime);
			// occasionally, due to ignoring file IO Errors, this method is
			// called
			// twice where nFilesScanned has not incremented. No need to print
			// info
			// twice if no change.
			previousPrintedScannedFiles = nFilesScanned;
		}
		return (nFilesScanned >= MAX_FILES);
	}

	private void doTestDir(File dir) {

		final String[] children = dir.list();
		for (int i = 0; i < children.length; i++) {
			final File file = new File(dir, children[i]);
			if (file.isDirectory()) {
				doTestDir(file);
			}
			else {
				doTestFile(file);
			}
			if (checkMaxFiles(true)) {
				break;
			}
		}

	}

	private void doTestFile(File file) {

		HTMLHeadTokenizer tokenizer = null;
		Reader fileReader = null;
		try {
			fileReader = new FileReader(file);
			tokenizer = new HTMLHeadTokenizer(fileReader);
			parseHeader(tokenizer);
			fileReader.close();
			nFilesScanned++;
		}
		catch (final IOException e) {
			// ignore, since for directories in general, there can be
			// lots of errors based on "access denied", etc.
		}
		catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("   filename: " + file.getAbsolutePath());
			System.out.println("   number of files checked: " + nFilesScanned);
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

	public void testAllFilesInDirectory(String directoryName) throws Exception {
		final File filedir = new File(directoryName);

		if (filedir.isDirectory()) {
			doTestDir(filedir);
		}
		else {
			throw new Exception("     test program error: the directory is not a directory: " + defaultDirName);
		}
		if (checkMaxFiles(false)) {
			System.out.println("     Test Complete. Maximum files scanned: " + nFilesScanned);
		}
		else {
			System.out.println("     Test Complete. Number of scanned files: " + nFilesScanned);
		}
	}
}

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


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jst.jsp.core.internal.parser.internal.JSPTokenizer;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This class was based on a JUnit test, HTMLHeadTokenizerTester, and then
 * simplified to run as a standalone Java app, to test for JIT errors.
 * 
 * @author davidw
 * 
 */
public abstract class TokenizerTesterForDirectory implements IApplication {

	public static void main(String[] args) {

	}

	protected final boolean DEBUG = false;
	protected final static int MAX_FILES = 250000;
	// provide generic, platform independent default directory
	protected final static String defaultDirName = "/";
	protected final static int TIME_PRINT_INTERVAL = 2000;

	protected static long startTime;
	protected int nFilesScanned;
	protected int previousPrintedScannedFiles;

	protected boolean checkMaxFiles(boolean printTime) {
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
				// ignore .metadata directories, for more accurate simiulation
				// of validating or indexing a workspace,
				// if a workspace is the starting directory.
				if (!file.getName().endsWith(".metadata")) {
					doTestDir(file);
				}
			}
			else {
				doTestFile(file);
			}
			if (checkMaxFiles(true)) {
				break;
			}
		}

	}

	abstract protected void doTestFile(File file);

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

	public Object start(IApplicationContext context, String tokenizerName) throws Exception {
		String dirName = initialAppActions(context, tokenizerName);
		try {
			testAllFilesInDirectory(dirName);
		}
		catch (final InterruptedException e) {
			handleInterrupt();
		}
		catch (final Exception e) {
			handleException(e);
		}
		return IApplication.EXIT_OK;
	}

	public void stop() {
		// nothing to clean up
	}

	protected void doFinallyClose(Reader fileReader) {
		if (fileReader != null) {
			try {
				fileReader.close();
			}
			catch (IOException e1) {
				// ignore
			}
		}
	}

	protected String getDirectoryToScan(IApplicationContext context) {
		String dirName = null;
		Map args = context.getArguments();
		String[] applicationArgs = (String[]) args.get("application.args");
		if (applicationArgs != null && applicationArgs.length > 0) {
			dirName = applicationArgs[0];
		}
		else {
			dirName = defaultDirName;
		}
		return dirName;
	}

	protected void printHeader(String dirName, String tokenizerName) {
		System.out.println();
		System.out.println("     Running " + tokenizerName + " scan on all files in directory ");
		System.out.println("          " + dirName);
		System.out.println("          " + "for a maximum number of files: " + MAX_FILES);
		String version = System.getProperty("java.fullversion");
		if (version == null) {
			version = System.getProperty("java.vendor") + "  " + System.getProperty("java.version");
			System.out.println("          " + "Using Java version: " + version);
		}
		else {
			System.out.println("          " + "Using Java version: ");
			System.out.println(version);
		}
	}

	protected void handleTestException(File file, Reader fileReader, final Exception e) {
		System.out.println("Error: " + e.getMessage());
		System.out.println("   filename: " + file.getAbsolutePath());
		System.out.println("   number of files checked: " + nFilesScanned);
		doFinallyClose(fileReader);
	}

	protected void handleException(final Exception e) {
		System.out.println("     unexpected program error: " + e.getMessage());
	}

	protected void handleInterrupt() {
		System.out.println("     program ended via interuption");
	}

	protected String initialAppActions(IApplicationContext context, String tokenizerName) {
		String dirName = getDirectoryToScan(context);
		printHeader(dirName, tokenizerName);
		startTime = System.currentTimeMillis();
		return dirName;
	}

}

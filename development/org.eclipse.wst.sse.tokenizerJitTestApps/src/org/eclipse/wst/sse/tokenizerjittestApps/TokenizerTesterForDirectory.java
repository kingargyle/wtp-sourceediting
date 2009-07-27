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

	protected boolean DEBUG = false;
	private boolean DEBUG_FILES = false;
	private int MAX_FILES = 50000;
	// provide generic, platform independent default directory
	private final static String defaultDirName = "/";

	private int TIME_PRINT_INTERVAL = 10000;
	private long startTime;
	private int nFilesScanned;
	private int totalErrors;
	private int nFilesScannedTotal;
	private int totalErrorsTotal;

	private int previousPrintedScannedFiles;

	private boolean checkMaxFiles(final boolean printTime) {
		if (printTime && previousPrintedScannedFiles != nFilesScanned && nFilesScanned % TIME_PRINT_INTERVAL == 0) {
			final String elapsedTime = computeElapsedTime();
			System.out.println();
			System.out.println("               " + "Progress: number of files scanned: " + nFilesScanned);
			System.out.println("               " + "  elapsed time: " + elapsedTime);
			System.out.flush();
			/*
			 * occasionally, due to ignoring file IO Errors, this method is
			 * called twice where nFilesScanned has not incremented. No need
			 * to print info twice if no change.
			 */
			previousPrintedScannedFiles = nFilesScanned;
		}
		return nFilesScanned >= MAX_FILES;
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

	private void doFinallyClose(final Reader fileReader) {
		if (fileReader != null) {
			try {
				fileReader.close();
			}
			catch (final IOException e1) {
				// ignore
			}
		}
	}

	abstract protected void doTest(Reader reader) throws Exception;

	private void doTestDir(final File dir) {

		final String[] children = dir.list();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				final File file = new File(dir, children[i]);
				if (file.isDirectory()) {
					/*
					 * ignore .metadata directories, for more accurate
					 * simulation of validating or indexing a workspace.
					 */
					if (!file.getName().endsWith(".metadata")) {
						doTestDir(file);
					}
				}
				else {
					if (DEBUG_FILES) {
						System.out.println("file: " + file.getAbsolutePath());
					}
					doTestFile(file);
				}
				if (checkMaxFiles(true)) {
					break;
				}
			}
		}

	}

	private void doTestFile(final File file) {

		Reader fileReader = null;
		try {
			fileReader = new FileReader(file);
			doTest(fileReader);
			/* Do not increment counter, if exception thrown during scan */
			nFilesScanned++;
		}
		catch (final IOException e) {
			/*
			 * ignore IOException, since for directories in general, there can
			 * be lots of errors based on "access denied", etc.
			 */
		}
		catch (final Exception e) {
			handleTestException(file, fileReader, e);
		}
		finally {
			doFinallyClose(fileReader);
		}
	}

	private String[] getDirectoryToScan(final IApplicationContext context) {
		String[] dirNames = null;
		final Map args = context.getArguments();
		final String[] applicationArgs = (String[]) args.get("application.args");
		if (applicationArgs != null && applicationArgs.length > 0) {
			dirNames = applicationArgs;
		}
		else {
			dirNames = new String[]{defaultDirName};
		}
		return dirNames;
	}

	abstract protected String getTokenizerName();

	private void handleException(final Exception e) {
		System.out.println("     unexpected program error: " + e.getMessage());
	}

	private void handleInterrupt() {
		System.out.println("     program ended via interuption");
	}

	private void handleTestException(final File file, final Reader fileReader, final Exception e) {
		System.out.println("Exception during scan: " + e.getMessage());
		System.out.println("   filename: " + file.getAbsolutePath());
		System.out.println("   number of files checked: " + nFilesScanned);
		System.out.println("   total (cumlative) errors: " + ++totalErrors);
		doFinallyClose(fileReader);
	}

	private String[] initialAppActions(final IApplicationContext context, final String tokenizerName) {
		final String[] dirNames = getDirectoryToScan(context);
		for (int i = 0; i < dirNames.length; i++) {
			String dirName = dirNames[i];
		}
		startTime = System.currentTimeMillis();
		return dirNames;
	}

	private void printHeader(final String[] dirNames, final String tokenizerName) {
		System.out.println();
		System.out.println("     Running " + tokenizerName + " scan on all files in the follow directories ");
		for (int i = 0; i < dirNames.length; i++) {
			String dirName = dirNames[i];
			System.out.println("               " + dirName);
		}

		System.out.println("          " + "for a maximum number of files, in each directory: " + MAX_FILES);
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
		try {
			for (int i = 0; i < dirNames.length; i++) {
				String dirName = dirNames[i];
				printDirHeader(dirName);
				nFilesScanned = 0;
				totalErrors = 0;
				testAllFilesInDirectory(dirName);
				printDirTail();
	            nFilesScannedTotal += nFilesScanned;
	            totalErrorsTotal += totalErrors;
			}
			printTail();
		}
		catch (final InterruptedException e) {
			handleInterrupt();
		}
		catch (final Exception e) {
			handleException(e);
		}
		return IApplication.EXIT_OK;
	}

	private void printDirHeader(String dirName) {
		System.out.println("     Current Directory: " + dirName);
		
	}

	public void stop() {
		// nothing to clean up
	}

	private void testAllFilesInDirectory(final String directoryName) throws Exception {
		final File filedir = new File(directoryName);


		if (filedir.isDirectory()) {
			doTestDir(filedir);

		}
		else {
			throw new Exception("     Test program error: the supplied directory is not a directory: " + directoryName);
		}

	}

	private void printTail() {
		System.out.println();
		System.out.println("     Total Test Complete. Number of scanned files: " + nFilesScannedTotal);
		System.out.println("     Total Errors: " + totalErrorsTotal);
	}
	private void printDirTail() {
		System.out.println();
		if (checkMaxFiles(false)) {
			System.out.println("     Test Complete. Maximum files scanned: " + nFilesScanned);
		}
		else {
			System.out.println("     Test Complete. Number of scanned files: " + nFilesScanned);
		}
		System.out.println("     Total Errors: " + totalErrors);
	}

}

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
	private final static int MAX_FILES = 250000;
	// provide generic, platform independent default directory
	private final static String defaultDirName = "/";
	private final static int TIME_PRINT_INTERVAL = 2000;

	private int nFilesScanned;
	private String fCharset;
	private String fContentTypeValue;

	private int previousPrintedScannedFiles;

	private boolean canHandleAsUnicodeStream(String tokenType) {
		boolean canHandleAsUnicode = false;
		if (tokenType == EncodingParserConstants.UTF83ByteBOM) {
			canHandleAsUnicode = true;
			this.fCharset = "UTF-8"; //$NON-NLS-1$
		}
		else if ((tokenType == EncodingParserConstants.UTF16BE) || (tokenType == EncodingParserConstants.UTF16LE)) {
			canHandleAsUnicode = true;
			this.fCharset = "UTF-16"; //$NON-NLS-1$
		}
		return canHandleAsUnicode;
	}

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

	private boolean isLegalString(String tokenType) {
		boolean result = false;
		if (tokenType == null) {
			result = false;
		}
		else {
			result = tokenType.equals(EncodingParserConstants.StringValue) || tokenType.equals(EncodingParserConstants.UnDelimitedStringValue) || tokenType.equals(EncodingParserConstants.InvalidTerminatedStringValue) || tokenType.equals(EncodingParserConstants.InvalidTermintatedUnDelimitedStringValue);
		}
		return result;
	}

	private void parseContentTypeValue(String contentType) {
		final Pattern pattern = Pattern.compile(";\\s*charset\\s*=\\s*");
		final String[] parts = pattern.split(contentType);
		if (parts.length > 0) {
			// if only one item, it can still be charset instead of
			// contentType
			if (parts.length == 1) {
				if (parts[0].length() > 6) {
					final String checkForCharset = parts[0].substring(0, 7);
					if (checkForCharset.equalsIgnoreCase("charset")) {
						int eqpos = parts[0].indexOf('=');
						eqpos = eqpos + 1;
						if (eqpos < parts[0].length()) {
							this.fCharset = parts[0].substring(eqpos);
							this.fCharset = this.fCharset.trim();
						}
					}
				}
			}
			else {
				// fContentType = parts[0];
			}

		}
		if (parts.length > 1) {
			this.fCharset = parts[1];
		}
	}

	/**
	 * Give's priority to encoding value, if found else, looks for contentType
	 * value;
	 */
	private HeadParserToken parseHeader(HTMLHeadTokenizer tokenizer) throws IOException {
		this.fCharset = null;
		/*
		 * if (tokenType == XMLHeadTokenizerConstants.XMLDelEncoding) { if
		 * (tokenizer.hasMoreTokens()) { ITextHeadRegion valueToken =
		 * tokenizer.getNextToken(); String valueTokenType =
		 * valueToken.getType(); if (isLegal(valueTokenType)) { resultValue =
		 * valueToken.getText(); if (DEBUG) { System.out.println("XML Head
		 * Tokenizer Found Encoding: " + resultValue); } } } }
		 */
		HeadParserToken token = null;
		HeadParserToken finalToken = null;
		do {
			token = tokenizer.getNextToken();
			if (this.DEBUG) {
				System.out.println(token);
			}
			final String tokenType = token.getType();
			if (canHandleAsUnicodeStream(tokenType)) {

			}
			if (tokenType == HTMLHeadTokenizerConstants.MetaTagContentType) {
				if (tokenizer.hasMoreTokens()) {
					final HeadParserToken valueToken = tokenizer.getNextToken();
					if (this.DEBUG) {
						System.out.println(valueToken);
					}
					final String valueTokenType = valueToken.getType();
					if (isLegalString(valueTokenType)) {
						this.fContentTypeValue = valueToken.getText();

					}
				}
			}

		}
		while (tokenizer.hasMoreTokens());
		if (this.fContentTypeValue != null) {
			parseContentTypeValue(this.fContentTypeValue);
		}
		finalToken = token;
		return finalToken;

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

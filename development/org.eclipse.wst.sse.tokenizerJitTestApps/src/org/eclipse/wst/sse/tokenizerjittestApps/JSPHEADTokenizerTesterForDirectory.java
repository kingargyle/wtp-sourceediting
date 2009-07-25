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
import java.util.regex.Pattern;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jst.jsp.core.internal.contenttype.JSPHeadTokenizer;
import org.eclipse.jst.jsp.core.internal.parser.internal.JSPTokenizer;
import org.eclipse.wst.html.core.internal.contenttype.HTMLHeadTokenizer;
import org.eclipse.wst.html.core.internal.contenttype.HeadParserToken;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This class was based on a JUnit test, HTMLHeadTokenizerTester, and then
 * simplified to run as a standalone Eclipse app, to test for JIT errors.
 * 
 * @author davidw
 * 
 */
public class JSPHEADTokenizerTesterForDirectory extends TokenizerTesterForDirectory {

	static final String tokenizerName = "JSP HEAD Tokenizer";

	protected void doTestFile(File file) {

		Reader fileReader = null;
		try {
			fileReader = new FileReader(file);
			JSPHeadTokenizer tokenizer = new JSPHeadTokenizer(fileReader);
			scanThroughFile(tokenizer);
		}
		catch (final IOException e) {
			/*
			 * ignore IOException, since for directories in general, there can
			 * be lots of errors based on "access denied", etc.
			 */
			doFinallyClose(fileReader);
		}
		catch (final Exception e) {
			handleTestException(file, fileReader, e);
		}
		finally {
			doFinallyClose(fileReader);
		}
	}

	private void scanThroughFile(JSPHeadTokenizer tokenizer) throws IOException {
		org.eclipse.jst.jsp.core.internal.contenttype.HeadParserToken token = null;
		do {
			try {
				token = tokenizer.getNextToken();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (this.DEBUG) {
				System.out.println(token);
			}
		}
		while (tokenizer.hasMoreTokens());
		nFilesScanned++;
	}

	public Object start(IApplicationContext context) throws Exception {
		return start(context, tokenizerName);
	}
}

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


import java.io.Reader;

import org.eclipse.jst.jsp.core.internal.contenttype.JSPHeadTokenizer;

/**
 * This class was based on a JUnit test, HTMLHeadTokenizerTester, and then
 * simplified to run as a standalone Eclipse app, to test for JIT errors.
 * 
 * @author davidw
 * 
 */
public class JSPHEADTokenizerTesterForDirectory extends TokenizerTesterForDirectory {

	static final String tokenizerName = "JSP HEAD Tokenizer";

	@Override
	protected void doTest(final Reader reader) throws Exception {

		final JSPHeadTokenizer tokenizer = new JSPHeadTokenizer(reader);
		scanThroughFile(tokenizer);
	}

	@Override
	protected String getTokenizerName() {
		return tokenizerName;
	}

	private void scanThroughFile(final JSPHeadTokenizer tokenizer) throws Exception {
		org.eclipse.jst.jsp.core.internal.contenttype.HeadParserToken token = null;
		do {
			token = tokenizer.getNextToken();
			if (DEBUG) {
				System.out.println(token);
			}
		}
		while (tokenizer.hasMoreTokens());
	}

}

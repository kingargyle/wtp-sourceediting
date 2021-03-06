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

import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.parser.XMLTokenizer;

/**
 * This class was based on a JUnit test, HTMLHeadTokenizerTester, and then
 * simplified to run as a standalone Eclipse app, to test for JIT errors.
 * 
 * @author davidw
 * 
 */
public class XMLTokenizerTesterForDirectory extends TokenizerTesterForDirectory {

	static final String tokenizerName = "XMLTokenizer";

	@Override
	protected void doTest(final Reader reader) throws IOException {

		final XMLTokenizer tokenizer = new XMLTokenizer(reader);
		scanThroughFile(tokenizer);
	}

	@Override
	protected String getTokenizerName() {
		return tokenizerName;
	}

	private void scanThroughFile(final XMLTokenizer tokenizer) throws IOException {
		ITextRegion token = null;
		do {
			token = tokenizer.getNextToken();
			if (DEBUG) {
				System.out.println(token);
			}
		}
		while (!tokenizer.isEOF());

	}
}

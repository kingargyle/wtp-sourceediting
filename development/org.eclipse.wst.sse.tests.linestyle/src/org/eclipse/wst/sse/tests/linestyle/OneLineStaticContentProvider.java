/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/

package org.eclipse.wst.sse.tests.linestyle;

import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.custom.TextChangeListener;
import org.eclipse.swt.custom.TextChangedEvent;

public class OneLineStaticContentProvider implements StyledTextContent {

	private String contentText = "";
	private TextChangeListener fTextChangedListener;

	public void addTextChangeListener(TextChangeListener listener) {
		// always only one for this simple test
		if (fTextChangedListener != null && fTextChangedListener != listener) {
			throw new Error("whoops, I was wrong, not always just one listener in this test?");
		}
		fTextChangedListener = listener;

	}

	public int getCharCount() {
		return contentText.length();
	}

	public String getLine(int lineIndex) {
		if (lineIndex == 0) {
			return contentText;
		}
		else
			return "";
	}

	public int getLineAtOffset(int offset) {
		// we always only have 1 line
		if (offset == contentText.length()) {
			// as per spec, return "number of lines" if
			// request is exactly equal to length
			return getLineCount();
		}
		// else return the line index, as per spec, starts at zero
		return 0;
	}

	public int getLineCount() {
		// we always only have 1 line
		return 1;
	}

	public String getLineDelimiter() {
		return System.getProperty("line.separator");
	}

	public int getOffsetAtLine(int lineIndex) {
		// we always only have one line, so
		// always return zero. (Spec isn't real clear
		// on what to do if out of bounds?)
		return 0;
	}

	public String getTextRange(int start, int length) {
		// assuming never called in test, but if it as
		// would be just substring.
		return contentText.substring(start, length - start);
	}

	public void removeTextChangeListener(TextChangeListener listener) {
		fTextChangedListener = null;
	}

	public void replaceTextRange(int start, int replaceLength, String text) {
		// not supported
	}

	public void setText(String text) {
		contentText = text;
		TextChangedEvent event = new TextChangedEvent(this);
		fTextChangedListener.textSet(event);
	}

}

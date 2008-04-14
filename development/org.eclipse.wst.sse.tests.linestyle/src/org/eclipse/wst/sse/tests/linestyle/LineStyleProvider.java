/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
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

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class LineStyleProvider implements LineStyleListener {


	private static int fNumberOfRanges = 1;
	private HashMap savedRangeArrays = new HashMap();
	private Display display;

	private boolean turn;

	private LineStyleProvider() {
		super();
	}

	public LineStyleProvider(Display display) {
		this();
		this.display = display;
	}

	public LineStyleProvider(Display display, int rangesToUse) {
		this(display);
		setNumberOfRanges(rangesToUse);
	}

	public void lineGetStyle(LineStyleEvent event) {
		int offset = event.lineOffset;
		String lineText = event.lineText;
		int length = lineText.length();
		int localNumberOfRanges = getNumberOfRanges();
		event.styles = getManyStyleRanges(offset, length, localNumberOfRanges);
		if (MinimalEditor.DEBUG_PRINT) {
			System.out.println();
			System.out.print("number of style ranges: " + event.styles.length);
		}
	}


	private StyleRange[] getManyStyleRanges(int offset, int length, int numberOfRanges) {
		StyleRange[] styleRanges = null;
		StyleRange[] cachedRanges = null;

		// note: we use a "simple rule" for caching, since this is a fixed
		// text test
		cachedRanges = (StyleRange[]) savedRangeArrays.get(new Integer(numberOfRanges));
		if (cachedRanges != null) {
			styleRanges = cachedRanges;
		}
		else {
			styleRanges = computeRanges(offset, length, numberOfRanges);
			savedRangeArrays.put(new Integer(numberOfRanges), styleRanges);
		}
		return styleRanges;
	}

	private StyleRange[] computeRanges(int offset, int length, int numberOfRanges) {
		if (numberOfRanges <= 0)
			throw new IllegalArgumentException("number of ranges must be greater than zero");

		ArrayList arrayList = new ArrayList();

		// I want force bold black here, for the "one range" case,
		// to help "see" it.
		if (numberOfRanges == 1) {
			arrayList.add(new StyleRange(checkOffset(offset, offset+length), safeWidth(length - offset, offset, length), display.getSystemColor(SWT.COLOR_BLACK), null, SWT.BOLD));

		}
		// otherwise break up line into roughly equal width style ranges and
		// assign each a semi-random color
		else {
			int overallEndOffset = offset + length - 1;

			// initial width
			int styleWidth = length / numberOfRanges;
			// initial range offset
			int rangeOffset = offset;
			// initial range count
			int rangeCount = 0;

			do {

				arrayList.add(new StyleRange(rangeOffset, styleWidth, getAlternatingColor(), null));

				rangeCount++;

				// to have relatively uniform style widths for test,
				// recompute each time, to correct for rounding error
				int remainingSpace = overallEndOffset - (rangeOffset + styleWidth);
				int remaingRanges = numberOfRanges - rangeCount;
				// next start offset
				rangeOffset = rangeOffset + styleWidth;
				// guard against out of range, and will drop out of
				// do-loop when checked.
				if (rangeOffset < overallEndOffset) {
					rangeOffset = checkOffset(rangeOffset, overallEndOffset);

					// gaurd against division by zero (and will drop out
					// of do-loop when checked.
					if (remaingRanges > 0) {
						styleWidth = remainingSpace / remaingRanges;
						styleWidth = safeWidth(styleWidth, rangeOffset, overallEndOffset);
					}
				}

			}
			while (rangeCount < numberOfRanges && rangeOffset < overallEndOffset);

		}


		int resultLength = arrayList.size();
		StyleRange[] styleRanges = new StyleRange[resultLength];
		System.arraycopy(arrayList.toArray(), 0, styleRanges, 0, resultLength);
		return styleRanges;
	}

	private Color getAlternatingColor() {
		Color result = null;
		if (turn) {
			result = display.getSystemColor(SWT.COLOR_RED);

		}
		else {
			result = display.getSystemColor(SWT.COLOR_BLUE);

		}
		turn = !turn;
		return result;
	}

	/**
	 * Quick and dirty check to be sure style location are never out of range.
	 * 
	 * we really only expect to be "near the end" of the line, since a given
	 * line length and "fixed width" style range may not divide evenly.
	 * 
	 * @param i
	 * @param length
	 * @return
	 */
	private int checkOffset(int offset, int endOffset) {

		// zero-length lines are possible if there is just one line
		if (offset > endOffset) {
			throw new Error("hmm, not checking or calculating for start offset correctly");
		}
		return offset;
	}

	/**
	 * Quick and dirty check to be sure style location are never out of range.
	 * 
	 * we really only expect to be "near the end" of the line, since a given
	 * line length and "fixed width" style range may not divide evenly.
	 * 
	 * @param styleWidth
	 * @param i
	 * @param length
	 * @return
	 */
	private int safeWidth(int styleWidth, int offset, int endOffset) {

		int result = 0;
		int remaining = (endOffset - offset);
		if (remaining < 0) {
			throw new IllegalArgumentException("not enough remaining space");
		}

		result = Math.min(styleWidth, remaining);

		return result;
	}

	static int getNumberOfRanges() {
		return fNumberOfRanges;
	}

	static void setNumberOfRanges(int numberOfRanges) {
		fNumberOfRanges = numberOfRanges;
	}



}

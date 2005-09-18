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
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class LineStyleProvider implements LineStyleListener {


	static int fNumberOfRanges = 1;
	private HashMap savedRangeArrays = new HashMap();
	private Display display;

	Random random;

	private LineStyleProvider() {
		super();
	}

	public LineStyleProvider(Display display) {
		super();
		this.display = display;
		long seed = System.currentTimeMillis();
		this.random = new Random(seed);
		System.out.println("Random Seed: " + seed);
	}

	public void lineGetStyle(LineStyleEvent event) {
		int offset = event.lineOffset;
		String lineText = event.lineText;
		int length = lineText.length();
		int localNumberOfRanges = fNumberOfRanges;
		event.styles = getManyStyleRanges(offset, length, localNumberOfRanges);
		System.out.println();
		System.out.print("number of style ranges: " + event.styles.length);
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
			// safety check, and simple minded correction if numberOfRanges
			// is set wrong
			if (numberOfRanges <= 0)
				numberOfRanges = 1;
			
			// to have uniform style widths for test, the 
			// requested number of Ranges may not match exactly
			// with the actualy number of Ranges
			int styleWidth = (length - offset) / numberOfRanges;
			
			ArrayList arrayList = new ArrayList();
			for (int i = offset; i < length; i = i + (styleWidth * 2)) {
				int firstRangeOffset = i;
				if (numberOfRanges == 1) {
					// I want bold black here, for the "one" case, 
					// to help "see" it not work
					arrayList.add(new StyleRange(safeOffset(firstRangeOffset, length), safeWidth(styleWidth, firstRangeOffset, length), display.getSystemColor(SWT.COLOR_BLACK), null, SWT.BOLD));

				}
				else {
					arrayList.add(new StyleRange(safeOffset(firstRangeOffset, length), safeWidth(styleWidth, firstRangeOffset, length), getRandomColor(), null));
				}
				int secondRangeOffset = i + styleWidth;
				if (secondRangeOffset < length) {
					arrayList.add(new StyleRange(safeOffset(secondRangeOffset, length), safeWidth(styleWidth, secondRangeOffset, length), getRandomColor(), null));
				}
			}
			int resultLength = arrayList.size();
			styleRanges = new StyleRange[resultLength];
			System.arraycopy(arrayList.toArray(), 0, styleRanges, 0, resultLength);
			savedRangeArrays.put(new Integer(numberOfRanges), styleRanges);
		}
		return styleRanges;
	}



	private Color getRandomColor() {
		// we take advantage of trick that "system colors" are from 1 to 16
		// (but don't use background color)
		Color result = null;
		int randomColorNumber = 0;
		while (randomColorNumber == 0 || display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).equals(result)) {
			randomColorNumber = random.nextInt(16);
			result = display.getSystemColor(randomColorNumber);
		}
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
	private int safeOffset(int offset, int length) {

		if (offset >= length) {
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
	private int safeWidth(int styleWidth, int offset, int length) {

		int result = 0;
		int remaining = (length - offset);
		if (0 == remaining) {
			result = styleWidth;
		}
		else {
			result = Math.min(styleWidth, remaining);
		}
		return result;
	}



}

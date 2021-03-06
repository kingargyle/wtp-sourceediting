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

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.test.performance.PerformanceTestCase;

public class TestManyStyledRanges extends PerformanceTestCase {


	public TestManyStyledRanges() {
		this("TestManyStyleRanges");
	}

	public TestManyStyledRanges(String name) {
		super(name);
	}

	public void testOne() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 1);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void test500() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 500);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}



	public void test1000() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 1000);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void test2000() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 2000);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void test5000() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 5000);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void test10000() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 10000);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void test15000() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 15000);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void test20000() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 20000);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public void test25000() {
		Display display = Display.getDefault();
		final MinimalEditor editor = new MinimalEditor();

		Shell shell = editor.open(display, this, 25000);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public void startMeasuring() {
		super.startMeasuring();
	}

	public void stopMeasuring() {
		super.stopMeasuring();
	}

	public void commitMeasurements() {
		super.commitMeasurements();
	}

	public void assertPerformance() {
		super.assertPerformance();

	}

	public void setUp() {
		try {
			super.setUp();
		}
		catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void tearDown() {
		try {
			commitMeasurements();
			assertPerformance();
			super.tearDown();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
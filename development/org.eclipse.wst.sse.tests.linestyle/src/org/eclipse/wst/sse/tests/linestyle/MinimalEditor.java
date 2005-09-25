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


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * This is a small test or "demo" of StyledText's LineStyleListener method of
 * providing highlighting information. The basic demo basically uses the most
 * minimal "editor" setup, uses a fixed amount of text, which never changes,
 * but then repleated changes the number of styledRanges used each time the
 * Display is refreshed.
 * 
 */

public class MinimalEditor {
	// used to create large input string to display
	private static final String SMALL_TEST_CONTENT = "1234567890";
	// expected to be multiple of 10 (number of chars in SMALL_TEST_CONTENT)
	private static final int NUMBER_OF_CHARACTERS = 40000;
	Shell shell;
	StyledText text;

	LineStyleProvider lineStyleProvider;
	static Thread periodicRefreshThread;

	private static long start;
	private static long end = -1;


	void createShell(Display display) {
		shell = new Shell(display);
		shell.setText("Test of Many LineStyle Ranges");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		shell.setLayout(layout);
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (periodicRefreshThread != null) {
					periodicRefreshThread.interrupt();
				}
			}
		});
	}

	void createStyledText() {
		text = new StyledText(shell, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData spec = new GridData();
		spec.horizontalAlignment = GridData.FILL;
		spec.grabExcessHorizontalSpace = true;
		spec.verticalAlignment = GridData.FILL;
		spec.grabExcessVerticalSpace = true;
		text.setLayoutData(spec);

		text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		LineStyleProvider lineStyleProvider = new LineStyleProvider(text.getDisplay());
		text.addLineStyleListener(lineStyleProvider);
		OneLineStaticContentProvider contentProvider = new OneLineStaticContentProvider();
		text.setContent(contentProvider);
		contentProvider.setText(getOneReallyHugeLineofText(NUMBER_OF_CHARACTERS));

	}

	public static void main(String[] args) {
		final Display display = new Display();
		final MinimalEditor editor = new MinimalEditor();
		Shell shell = editor.open(display);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}

	public Shell open(Display display) {
		createShell(display);



		createStyledText();
		shell.setSize(1000, 150);

		// create test thread after text component created
		PeriodicRefresherForTest periodicRefresherForTest = new PeriodicRefresherForTest(text);
		periodicRefreshThread = new Thread(periodicRefresherForTest, "PeriodicRefresherForTest");
		periodicRefreshThread.start();

		shell.open();

		return shell;
	}

	private String getOneReallyHugeLineofText(int nCharacters) {

		StringBuffer sb = new StringBuffer();
		int limit = nCharacters/SMALL_TEST_CONTENT.length();
		
		for (int i = 0; i < limit; i++) {
			sb.append(SMALL_TEST_CONTENT);
		}
		//EOL does not, rightly, seem to effect results
		//sb.append(System.getProperty("line.separator"));
		String result = sb.toString();
		System.out.println(result.length() + " characters used for test string");
		return result;
	}


}

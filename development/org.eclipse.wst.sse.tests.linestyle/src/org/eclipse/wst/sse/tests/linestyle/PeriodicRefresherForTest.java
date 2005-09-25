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

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

public class PeriodicRefresherForTest implements Runnable {

	static final String END_TIMING = "end";
	private static final String REFRESH_TASK = "refresh";
	static final String BEGIN_TIMING = "start";
	
	// set to something like 5000 to pause and have time to look at displayed ranges.
	private static final int ANIMATION_DELAY = 1000;
	private boolean testInProgress = false;
	private long start;
	private long end;
	private StyledText styledText;
	private int pointInTestLoop;
	private int[] rangeOfRanges = new int[]{1, 500, 1000, 5000, 10000, 15000, 20000};
	/*
	10, 50, 100, 200, 300, 400, 600, 700, 800, 2000, 3000, 4000, 6000, 7000, 8000, 9000,
	*/
	private int pass = 1;
	private boolean endThread;


	private PeriodicRefresherForTest() {
		super();
	}

	public PeriodicRefresherForTest(StyledText styledText) {
		this();
		this.styledText = styledText;
	}

	public void run() {

		while (!endThread && styledText != null && !styledText.isDisposed()) {
			try {
				// we sleep here, so we can see the display,
				// such as look for that black bold one range case
				Thread.currentThread().sleep(ANIMATION_DELAY);

				synchronized (this) {
					if (!testInProgress && !endThread) {
						if (styledText != null && !styledText.isDisposed()) {
							Display display = styledText.getDisplay();
							if (display != null && !display.isDisposed()) {

								// set to false int "end timing" method
								testInProgress = true;

								refreshFromDisplayThread(this, REFRESH_TASK);

								this.wait();

								markTimeFromDisplayThread(this, END_TIMING);

								this.wait();
							}
						}
						else {

							this.wait();

						}
					}
				}
			}

			catch (InterruptedException e1) {
				endThread = true;
			}

		}


	}

	void markTimeFromDisplayThread(final PeriodicRefresherForTest test, final String msgToSendBack) {
		if (styledText != null && !styledText.isDisposed()) {

			Display display = styledText.getDisplay();
			display.asyncExec(new Runnable() {
				public void run() {
					if (styledText != null && !styledText.isDisposed()) {
						Display display = styledText.getDisplay();
						if (display != null && !display.isDisposed()) {
							if (test != null) {
								test.systemTimeAtRun(System.currentTimeMillis(), msgToSendBack);
							}
						}
					}
				}

			});
		}

	}

	void systemTimeAtRun(long systemtime, String message) {
		synchronized (this) {
			if (message.equals(BEGIN_TIMING)) {
				start = systemtime;
				testInProgress = true;
				this.notifyAll();
			}
			else if (message.equals(END_TIMING)) {
				end = systemtime;
				System.out.print(": time: " + (end - start));
				System.out.println();
				testInProgress = false;
				this.notifyAll();
			}
			else {
				testInProgress = true;
				// be sure any "waiting" wakes up and continues
				this.notifyAll();
				
			}
		}

	}

	void refreshFromDisplayThread(final PeriodicRefresherForTest test, final String msg) {
		if (styledText != null && !styledText.isDisposed()) {
			styledText.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (styledText != null && !styledText.isDisposed()) {
						
						// set the line style provider to control number of ranges each redraw
						LineStyleProvider.fNumberOfRanges = lookupDesiredNumberOfRanges();
						
						// set the "begin time" 
						test.systemTimeAtRun(System.currentTimeMillis(), BEGIN_TIMING);

						
						styledText.redraw();
						// here our "callback" is just used to signal its ok
						// to post our next request on display thread
						// (which in our case, would be the Runnable to
						// measure the "end time" when it runs.
						test.systemTimeAtRun(-1, msg);

					}
				}

			});
		}

	}

	private int lookupDesiredNumberOfRanges() {

		if (pointInTestLoop >= rangeOfRanges.length) {
			pointInTestLoop = 0;
			System.out.println(" --- beginning pass " + pass++ + " ---  (provider ranges cached) --- ");
		}

		int result = rangeOfRanges[pointInTestLoop];

		// now, prepare "in advance" for next time
		pointInTestLoop++;


		return result;
	}

}

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

	private boolean testInProgress = false;
	private long start;
	private long end;
	private StyledText styledText;
	private int pointInTestLoop;
	private int[] rangeOfRanges = new int[]{1, 10, 50, 100, 200, 300, 400, 500, 600, 700, 800, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 15000, 20000};
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
				Thread.currentThread().sleep(5000);
			}
			catch (InterruptedException e1) {
				endThread = true;
			}
			synchronized (this) {
				if (!testInProgress && !endThread) {
					if (styledText != null && !styledText.isDisposed()) {
						Display display = styledText.getDisplay();
						if (display != null && !display.isDisposed()) {

							testInProgress = true;

							markTimeFromDisplayThread(this, "start");
							try {
								this.wait();
							}
							catch (InterruptedException e2) {
								endThread = true;
							}
							if (!endThread) {
								refreshFromDisplayThread(this, "refresh");
							}
							try {
								this.wait();
							}
							catch (InterruptedException e1) {
								endThread = true;
							}
							if (!endThread) {
								markTimeFromDisplayThread(this, "end");
							}

							try {
								this.wait();
							}
							catch (InterruptedException e) {
								endThread = true;
							}
						}
					}
					else {
						try {
							this.wait();
						}
						catch (InterruptedException e) {
							endThread = true;
						}
					}
				}
			}
		}


	}

	private void markTimeFromDisplayThread(final PeriodicRefresherForTest test, final String msgToSendBack) {
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
			if (message.equals("start")) {
				start = systemtime;
				testInProgress = true;
				this.notifyAll();
			}
			else if (message.equals("end")) {
				end = systemtime;
				System.out.print(": time: " + (end - start));
				System.out.println();
				testInProgress = false;
				this.notifyAll();
			}
			else {
				// be sure any "waiting" continues
				this.notifyAll();
			}
		}

	}

	private void refreshFromDisplayThread(final PeriodicRefresherForTest test, final String msg) {
		if (styledText != null && !styledText.isDisposed()) {
			styledText.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (styledText != null && !styledText.isDisposed()) {
						LineStyleProvider.fNumberOfRanges = lookupDesiredNumberOfRanges();
						styledText.redraw();
						// here our "callback" is just used to signal its ok 
						// to post net request on display thread
						// (which in our case, would be the Runnable to measure the 
						// "end time" when it runs.
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

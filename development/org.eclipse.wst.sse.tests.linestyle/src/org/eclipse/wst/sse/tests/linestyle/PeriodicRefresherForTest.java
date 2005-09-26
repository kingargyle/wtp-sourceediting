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



	// set to something like 10000 to pause and have time to look at displayed
	// ranges.
	private static final int ANIMATION_DELAY = 1000;
	private boolean testInProgress = false;
	private long start;
	private long end;
	private StyledText styledText;
	private int pointInTestLoop;
	// make static so saved for whole instance of class loaded
	static private int[] rangeOfRanges = new int[]{1, 500, 1000, 5000, 10000, 15000, 20000, 25000};
	/*
	 * 10, 50, 100, 200, 300, 400, 600, 700, 800, 2000, 3000, 4000, 6000,
	 * 7000, 8000, 9000,
	 */
	private int pass = 1;
	private boolean endThread;
	private TestManyStyledRanges fMeasure;
	private int fNRanges;
	private boolean fContinuous = true;
	private int maxRuns = 5;
	private MinimalEditor feditor;



	public PeriodicRefresherForTest(StyledText styledText) {
		super();
		this.styledText = styledText;
	}

	public PeriodicRefresherForTest(StyledText text, TestManyStyledRanges measure) {
		this(text);
		fMeasure = measure;
	}

	public PeriodicRefresherForTest(StyledText text, TestManyStyledRanges measure, int numberOfRanges, MinimalEditor editor) {
		this(text, measure);

		fNRanges = numberOfRanges;
		fContinuous = false;

		feditor = editor;
	}


	public void run() {

		int runs = 0;



		while (!endThread && styledText != null && !styledText.isDisposed() && (runs < maxRuns)) {
			try {
				// we sleep here, so we can see the display,
				// such as look for that black bold one range case
				Thread.sleep(ANIMATION_DELAY);
				if (!fContinuous) {
					runs++;
				}
				synchronized (this) {
					if (!testInProgress && !endThread) {
						if (styledText != null && !styledText.isDisposed()) {
							Display display = styledText.getDisplay();
							if (display != null && !display.isDisposed()) {

								// set to false int "end timing" method
								testInProgress = true;

								refreshFromDisplayThread(this);

								this.wait();

								markEndTimeFromDisplayThread(this);

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


		// fMeasure.commitMeasurements();
		// fMeasure.assertPerformance();


		if (!fContinuous) {
			closeEditor();
		}

	}

	void markEndTimeFromDisplayThread(final PeriodicRefresherForTest test) {
		if (styledText != null && !styledText.isDisposed()) {

			Display display = styledText.getDisplay();
			display.asyncExec(new Runnable() {
				public void run() {
					if (styledText != null && !styledText.isDisposed()) {
						Display display = styledText.getDisplay();
						if (display != null && !display.isDisposed()) {
							if (test != null) {
								if (fMeasure != null) {
									fMeasure.stopMeasuring();
								}
								test.systemTimeAtRun(System.currentTimeMillis(), END_TIMING);
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
				if (MinimalEditor.DEBUG_PRINT) {
					System.out.print(": time: " + (end - start));
					System.out.println();
				}
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

	void refreshFromDisplayThread(final PeriodicRefresherForTest test) {
		if (styledText != null && !styledText.isDisposed()) {
			styledText.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (styledText != null && !styledText.isDisposed()) {

						if (fContinuous) {
							// set the line style provider to control number
							// of
							// ranges each redraw
							LineStyleProvider.setNumberOfRanges(lookupDesiredNumberOfRanges());
						}
						else {
							LineStyleProvider.setNumberOfRanges((fNRanges));
						}

						// set the "begin time"
						if (fMeasure != null) {
							fMeasure.startMeasuring();
						}
						test.systemTimeAtRun(System.currentTimeMillis(), BEGIN_TIMING);


						styledText.redraw();
						// here our "callback" is just used to signal its ok
						// to post our next request on display thread
						// (which in our case, would be the Runnable to
						// measure the "end time" when it finally runs on
						// display thread)
						test.systemTimeAtRun(-1, REFRESH_TASK);

					}
				}

			});
		}

	}

	void closeEditor() {
		if (styledText != null && !styledText.isDisposed()) {
			styledText.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (styledText != null && !styledText.isDisposed()) {
						if (feditor != null) {
							feditor.close();
						}

					}
				}

			});
		}

	}

	private int lookupDesiredNumberOfRanges() {

		if (pointInTestLoop >= rangeOfRanges.length) {
			pointInTestLoop = 0;
			pass++;
			if (MinimalEditor.DEBUG_PRINT) {
				System.out.println(" --- beginning pass " + pass + " ---  (provider ranges cached) --- ");
			}
		}


		int result = rangeOfRanges[pointInTestLoop];

		// now, prepare "in advance" for next time
		pointInTestLoop++;



		return result;
	}

}

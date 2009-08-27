/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.projection.AbstractStructuredFoldingStrategy;
import org.eclipse.wst.sse.ui.internal.provisional.preferences.CommonEditorPreferenceNames;

public class OpenEditorWithFoldingTest extends BasicEditorTest {
	public OpenEditorWithFoldingTest() {

		super();
		ZIP_FILE_NAME = "folding-tests.zip";
		PROJECT_NAME = "folding-tests";
		FILE_NAME = "GolfCountryClub.xml";
	}

	protected void tearDown() throws Exception {
		// deliberately turn off folding preference again (just in cases)
		IPreferenceStore store = SSEUIPlugin.getDefault().getPreferenceStore();
		store.setValue(AbstractStructuredFoldingStrategy.FOLDING_ENABLED, false);
		super.tearDown();
	}

	/**
	 * @see org.eclipse.wst.xml.ui.tests.performance.BasicEditorTest#setUp()
	 */
	protected void setUpPrefs() {
		IPreferenceStore store = SSEUIPlugin.getDefault().getPreferenceStore();
		// turn off reconciling
		store.setValue(CommonEditorPreferenceNames.EVALUATE_TEMPORARY_PROBLEMS, false);
		// turn on folding
		store.setValue(AbstractStructuredFoldingStrategy.FOLDING_ENABLED, true);

		IPreferenceStore editorsStore = EditorsUI.getPreferenceStore();
		// turn off quick diff
		editorsStore.setValue(AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_ALWAYS_ON, false);
	}

	public void testOpenEditorSmall() {
		
		tagAsSummary("Open XML Editor Small Folding ON", new Dimension[]{Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
		testOpenEditor(FILE_NAME, true);
		
		tagAsSummary("Open XML Editor Small Folding OFF", new Dimension[]{Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
		testOpenEditor(FILE_NAME, false);
	}

	public void testOpenEditorMedium() {
		tagAsSummary("Open XML Editor Medium Folding ON", new Dimension[]{Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
		testOpenEditor("GolfCountryClub1.xml", true);

		tagAsSummary("Open XML Editor Medium Folding OFF", new Dimension[]{Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
		testOpenEditor("GolfCountryClub1.xml", false);
	}

	public void testOpenEditorLarge() {
		tagAsSummary("Open XML Editor Large Folding ON", new Dimension[]{Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
		testOpenEditor("formatted-a-first1000.xml", true);

		tagAsSummary("Open XML Editor Large Folding OFF", new Dimension[]{Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
		testOpenEditor("formatted-a-first1000.xml", false);
	}



	private void testOpenEditor(String filename, boolean foldingEnabled) {

		IPreferenceStore store = SSEUIPlugin.getDefault().getPreferenceStore();

		// turn on folding
		store.setValue(AbstractStructuredFoldingStrategy.FOLDING_ENABLED, foldingEnabled);



		int iterations = 3;

		// warmup runs
		for (int i = 0; i < iterations; i++) {

			openEditor(new Path(F_SLASH + PROJECT_NAME + F_SLASH + filename));
			runEvents();
			EditorTestHelper.closeAllEditors();
			runEvents();
		}

		// do the test
		// model size important here
		for (int i = 0; i < iterations; i++) {
			startMeasuring();
			openEditor(new Path(F_SLASH + PROJECT_NAME + F_SLASH + filename));
			runEvents();
			stopMeasuring();

			EditorTestHelper.closeAllEditors();
			runEvents();
		}
		commitMeasurements();
		assertPerformance();
	}

}

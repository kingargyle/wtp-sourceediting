package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.provisional.preferences.CommonEditorPreferenceNames;

/**
 * @author pavery
 */
public class FormatTestWithReconcilerOnly extends FormatTest {
	public FormatTestWithReconcilerOnly() {

		super();

		ZIP_FILE_NAME = "format-test.zip";
		PROJECT_NAME = "FORMAT-TEST";
		FILE_NAME = "xml/format-test.xml";
	}

	/**
	 * @see org.eclipse.wst.xml.ui.tests.performance.BasicEditorTest#setUp()
	 */
	protected void setUpPrefs() {
		IPreferenceStore store = SSEUIPlugin.getDefault().getPreferenceStore();
		// turn on reconciling
		store.setValue(CommonEditorPreferenceNames.EVALUATE_TEMPORARY_PROBLEMS, true);

		IPreferenceStore editorsStore = EditorsUI.getPreferenceStore();
		// turn off quick diff
		editorsStore.setValue(AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_ALWAYS_ON, false);
	}
}

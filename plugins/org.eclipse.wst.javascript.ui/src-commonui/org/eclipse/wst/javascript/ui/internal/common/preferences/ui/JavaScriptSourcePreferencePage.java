/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.javascript.ui.internal.common.preferences.ui;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.help.WorkbenchHelp;
import org.eclipse.wst.javascript.core.internal.JavaScriptCorePlugin;
import org.eclipse.wst.javascript.ui.internal.common.IHelpContextIds;
import org.eclipse.wst.javascript.ui.internal.common.JSCommonUIMessages;
import org.eclipse.wst.javascript.ui.internal.common.preferences.JSCommonUIPreferenceNames;
import org.eclipse.wst.javascript.ui.internal.editor.JSEditorPlugin;
import org.eclipse.wst.sse.ui.preferences.ui.AbstractPreferencePage;

public class JavaScriptSourcePreferencePage extends AbstractPreferencePage implements ModifyListener, SelectionListener, IWorkbenchPreferencePage {
	// Formatting
	protected Label fLineWidthLabel;
	protected Text fLineWidthText;
	protected Button fSplitMultiAttrs;
	protected Button fIndentUsingTabs;
	protected Button fClearAllBlankLines;
	// Content Assist
	protected Button fAutoPropose;
	protected Label fAutoProposeLabel;
	protected Text fAutoProposeText;
	// grammar constraints
	protected Button fUseInferredGrammar;

	protected Preferences getModelPreferences() {
		return JavaScriptCorePlugin.getDefault().getPluginPreferences();
	}

	protected Control createContents(Composite parent) {
		Composite composite = (Composite) super.createContents(parent);

		createContentsForContentAssistGroup(composite);
		WorkbenchHelp.setHelp(composite, IHelpContextIds.JS_PREFWEBX_SOURCE_HELPID);
		
		setSize(composite);
		loadPreferences();

		return composite;
	}

	protected void createContentsForContentAssistGroup(Composite parent) {
		Group contentAssistGroup = createGroup(parent, 2);
		contentAssistGroup.setText(JSCommonUIMessages.getString("Content_assist_UI_")); //$NON-NLS-1$ = "Content assist"

		fAutoPropose = createCheckBox(contentAssistGroup, JSCommonUIMessages.getString("Automatically_make_suggest_UI_")); //$NON-NLS-1$ = "Automatically make suggestions"
		((GridData) fAutoPropose.getLayoutData()).horizontalSpan = 2;
		fAutoPropose.addSelectionListener(this);

		fAutoProposeLabel = createLabel(contentAssistGroup, JSCommonUIMessages.getString("Prompt_when_these_characte_UI_")); //$NON-NLS-1$ = "Prompt when these characters are inserted:"
		fAutoProposeText = createTextField(contentAssistGroup);
	}

	protected void performDefaults() {
		performDefaultsForContentAssistGroup();

		validateValues();
		enableValues();

		super.performDefaults();
	}

	protected void performDefaultsForContentAssistGroup() {
		// Content Assist
		fAutoPropose.setSelection(getPreferenceStore().getDefaultBoolean(JSCommonUIPreferenceNames.AUTO_PROPOSE));
		fAutoProposeText.setText(getPreferenceStore().getDefaultString(JSCommonUIPreferenceNames.AUTO_PROPOSE_CODE));
	}

	protected void initializeValues() {
		initializeValuesForContentAssistGroup();
	}

	protected void initializeValuesForContentAssistGroup() {
		// Content Assist
		fAutoPropose.setSelection(getPreferenceStore().getBoolean(JSCommonUIPreferenceNames.AUTO_PROPOSE));
		fAutoProposeText.setText(getPreferenceStore().getString(JSCommonUIPreferenceNames.AUTO_PROPOSE_CODE));
	}

	protected void validateValues() {
		boolean isError = false;
		String widthText = null;

		if (fLineWidthText != null) {
			try {
				widthText = fLineWidthText.getText();
				int formattingLineWidth = Integer.parseInt(widthText);
				if ((formattingLineWidth < WIDTH_VALIDATION_LOWER_LIMIT) || (formattingLineWidth > WIDTH_VALIDATION_UPPER_LIMIT))
					throw new NumberFormatException();
			}
			catch (NumberFormatException nfexc) {
				setInvalidInputMessage(widthText);
				setValid(false);
				isError = true;
			}
		}

		if (!isError) {
			setErrorMessage(null);
			setValid(true);
		}
	}

	protected void enableValues() {
		if (fAutoPropose != null) {
			if (fAutoPropose.getSelection()) {
				fAutoProposeLabel.setEnabled(true);
				fAutoProposeText.setEnabled(true);
			}
			else {
				fAutoProposeLabel.setEnabled(false);
				fAutoProposeText.setEnabled(false);
			}
		}
	}

	protected void storeValues() {
		storeValuesForContentAssistGroup();
	}

	protected void storeValuesForContentAssistGroup() {
		// Content Assist
		getPreferenceStore().setValue(JSCommonUIPreferenceNames.AUTO_PROPOSE, fAutoPropose.getSelection());
		getPreferenceStore().setValue(JSCommonUIPreferenceNames.AUTO_PROPOSE_CODE, fAutoProposeText.getText());
	}

	protected IPreferenceStore doGetPreferenceStore() {
		return JSEditorPlugin.getDefault().getPreferenceStore();
	}

	protected void doSavePreferenceStore() {
		JSEditorPlugin.getDefault().savePluginPreferences();
	}

	protected void createContentsForFormattingGroup(Composite parent) {
		// do nothing
	}

	protected void performDefaultsForFormattingGroup() {
		// do nothing
	}

	protected void initializeValuesForFormattingGroup() {
		// do nothing
	}

	protected void storeValuesForFormattingGroup() {
		// do nothing
	}

	// don't create grammar constraints group
	protected void createContentsForGrammarConstraintsGroup(Composite parent) {
		// do nothing
	}

	protected void performDefaultsForGrammarConstraintsGroup() {
		// do nothing
	}

	protected void initializeValuesForGrammarConstraintsGroup() {
		// do nothing
	}

	protected void storeValuesForGrammarConstraintsGroup() {
		// do nothing
	}

	public boolean performOk() {
		boolean result = super.performOk();

		doSavePreferenceStore();

		return result;
	}
}

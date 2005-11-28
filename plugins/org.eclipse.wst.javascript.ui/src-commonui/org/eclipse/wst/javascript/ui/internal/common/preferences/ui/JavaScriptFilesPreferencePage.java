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

import java.util.Vector;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.javascript.core.internal.JavaScriptCorePlugin;
import org.eclipse.wst.javascript.ui.internal.common.IHelpContextIds;
import org.eclipse.wst.javascript.ui.internal.common.JSCommonUIMessages;
import org.eclipse.wst.javascript.ui.internal.editor.JSEditorPlugin;
import org.eclipse.wst.sse.core.internal.encoding.CommonEncodingPreferenceNames;

public class JavaScriptFilesPreferencePage extends PreferencePage implements
		ModifyListener, SelectionListener, IWorkbenchPreferencePage {
	protected Combo fEndOfLineCode = null;

	private Vector fEOLCodes = null;

	protected Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);

		// GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		composite.setLayout(layout);

		// GridData
		GridData data = new GridData(GridData.FILL);
		data.horizontalIndent = 0;
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = createScrolledComposite(parent);
		// WorkbenchHelp.setHelp(composite,
		// IHelpContextIds.XML_PREFWEBX_FILES_HELPID);
		createContentsForCreatingOrSavingGroup(composite);
		createContentsForCreatingGroup(composite);

		setSize(composite);
		loadPreferences();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IHelpContextIds.JS_PREFWEBX_FILES_HELPID);
		return composite;
	}

	protected void createContentsForCreatingGroup(Composite parent) {
		// do nothing
	}

	protected void createContentsForCreatingOrSavingGroup(Composite parent) {
		Group creatingOrSavingGroup = createGroup(parent, 2);
		creatingOrSavingGroup.setText(JSCommonUIMessages.Creating_or_saving_files); //$NON-NLS-1$

		Label label = createLabel(creatingOrSavingGroup, JSCommonUIMessages.End_of_line_code_desc); //$NON-NLS-1$
		((GridData) label.getLayoutData()).horizontalSpan = 2;
		((GridData) label.getLayoutData()).grabExcessHorizontalSpace = true;

		createLabel(creatingOrSavingGroup, JSCommonUIMessages.End_of_line_code); //$NON-NLS-1$
		fEndOfLineCode = createDropDownBox(creatingOrSavingGroup);
		populateLineDelimiters();
	}

	protected Combo createDropDownBox(Composite parent) {
		Combo comboBox = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);

		// GridData
		GridData data = new GridData();
		data.verticalAlignment = GridData.CENTER;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		comboBox.setLayoutData(data);

		return comboBox;
	}

	protected Group createGroup(Composite parent, int numColumns) {
		Group group = new Group(parent, SWT.NULL);

		// GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		group.setLayout(layout);

		// GridData
		GridData data = new GridData(GridData.FILL);
		data.horizontalIndent = 0;
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		group.setLayoutData(data);

		return group;
	}

	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);

		// GridData
		GridData data = new GridData(GridData.FILL);
		data.verticalAlignment = GridData.CENTER;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);

		return label;
	}

	protected Composite createScrolledComposite(Composite parent) {
		// create scrollbars for this parent when needed
		final ScrolledComposite sc1 = new ScrolledComposite(parent,
				SWT.H_SCROLL | SWT.V_SCROLL);
		sc1.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite composite = createComposite(sc1, 1);
		sc1.setContent(composite);

		// not calling setSize for composite will result in a blank composite,
		// so calling it here initially
		// setSize actually needs to be called after all controls are created,
		// so scrolledComposite
		// has correct minSize
		setSize(composite);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
	 */
	protected IPreferenceStore doGetPreferenceStore() {
		return JSEditorPlugin.getDefault().getPreferenceStore();
	}

	protected void doSavePreferenceStore() {
		JavaScriptCorePlugin.getDefault().savePluginPreferences(); // model
	}

	protected void enableValues() {
	}

	/**
	 * Return the currently selected line delimiter preference
	 * 
	 * @return a line delimiter constant from CommonEncodingPreferenceNames
	 */
	private String getCurrentEOLCode() {
		int i = fEndOfLineCode.getSelectionIndex();
		if (i >= 0) {
			return (String) (fEOLCodes.elementAt(i));
		}
		return ""; //$NON-NLS-1$
	}

	protected Preferences getModelPreferences() {
		return JavaScriptCorePlugin.getDefault().getPluginPreferences();
	}

	public void init(IWorkbench workbench) {
	}

	protected void initializeValues() {
		initializeValuesForCreatingOrSavingGroup();
		initializeValuesForCreatingGroup();
	}

	protected void initializeValuesForCreatingGroup() {
		// do nothing
	}

	protected void initializeValuesForCreatingOrSavingGroup() {
		String endOfLineCode = getModelPreferences().getString(
				CommonEncodingPreferenceNames.END_OF_LINE_CODE);

		if (endOfLineCode.length() > 0)
			setCurrentEOLCode(endOfLineCode);
		else
			setCurrentEOLCode(CommonEncodingPreferenceNames.NO_TRANSLATION);
	}

	protected boolean loadPreferences() {
		BusyIndicator.showWhile(getControl().getDisplay(), new Runnable() {
			public void run() {
				initializeValues();
				validateValues();
				enableValues();
			}
		});
		return true;
	}

	public void modifyText(ModifyEvent e) {
		// If we are called too early, i.e. before the controls are created
		// then return
		// to avoid null pointer exceptions
		if (e.widget != null && e.widget.isDisposed())
			return;

		validateValues();
		enableValues();
	}

	protected void performDefaults() {
		performDefaultsForCreatingOrSavingGroup();
		performDefaultsForCreatingGroup();

		super.performDefaults();
	}

	protected void performDefaultsForCreatingGroup() {
		// do nothing
	}

	protected void performDefaultsForCreatingOrSavingGroup() {
		String endOfLineCode = getModelPreferences().getDefaultString(
				CommonEncodingPreferenceNames.END_OF_LINE_CODE);

		if (endOfLineCode.length() > 0)
			setCurrentEOLCode(endOfLineCode);
		else
			setCurrentEOLCode(CommonEncodingPreferenceNames.NO_TRANSLATION);
	}

	public boolean performOk() {
		savePreferences();
		doSavePreferenceStore();

		return true;
	}

	/**
	 * Populates the vector containing the line delimiter to display string
	 * mapping and the combobox displaying line delimiters
	 */
	private void populateLineDelimiters() {
		fEOLCodes = new Vector();
		fEndOfLineCode.add(JSCommonUIMessages.EOL_Unix); //$NON-NLS-1$
		fEOLCodes.add(CommonEncodingPreferenceNames.LF);

		fEndOfLineCode.add(JSCommonUIMessages.EOL_Mac); //$NON-NLS-1$
		fEOLCodes.add(CommonEncodingPreferenceNames.CR);

		fEndOfLineCode.add(JSCommonUIMessages.EOL_Windows); //$NON-NLS-1$
		fEOLCodes.add(CommonEncodingPreferenceNames.CRLF);

		fEndOfLineCode.add(JSCommonUIMessages.EOL_NoTranslation); //$NON-NLS-1$
		fEOLCodes.add(CommonEncodingPreferenceNames.NO_TRANSLATION);
	}

	/**
	 * Select the line delimiter in the eol combobox
	 * 
	 * @param eol
	 *            a line delimiter constant from CommonEncodingPreferenceNames
	 */
	private void setCurrentEOLCode(String eolCode) {
		// Clear the current selection.
		fEndOfLineCode.clearSelection();
		fEndOfLineCode.deselectAll();

		int i = fEOLCodes.indexOf(eolCode);
		if (i >= 0) {
			fEndOfLineCode.select(i);
		}
	}

	protected void setSize(Composite composite) {
		if (composite != null) {
			Point minSize = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			composite.setSize(minSize);
			// set scrollbar composite's min size so page is expandable but
			// has scrollbars when needed
			if (composite.getParent() instanceof ScrolledComposite) {
				ScrolledComposite sc1 = (ScrolledComposite) composite
						.getParent();
				sc1.setMinSize(minSize);
				sc1.setExpandHorizontal(true);
				sc1.setExpandVertical(true);
			}
		}
	}

	protected void storeValues() {
		storeValuesForCreatingOrSavingGroup();
		storeValuesForCreatingGroup();
	}

	protected void storeValuesForCreatingGroup() {
		// do nothing
	}

	protected void storeValuesForCreatingOrSavingGroup() {
		String eolCode = getCurrentEOLCode();
		getModelPreferences().setValue(
				CommonEncodingPreferenceNames.END_OF_LINE_CODE, eolCode);
	}

	protected void validateValues() {
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	public void widgetSelected(SelectionEvent e) {
		// If we are called too early, i.e. before the controls are created
		// then return
		// to avoid null pointer exceptions
		if (e.widget != null && e.widget.isDisposed())
			return;

		validateValues();
		enableValues();
	}

	protected boolean savePreferences() {
		BusyIndicator.showWhile(getControl().getDisplay(), new Runnable() {
			public void run() {
				storeValues();
			}
		});
		return true;
	}
}

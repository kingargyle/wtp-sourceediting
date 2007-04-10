/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM - Initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.provisional.preferences.CommonEditorPreferenceNames;
import org.eclipse.wst.xml.core.internal.validation.eclipse.XMLValidator;

/**
 * @author kboo
 */
public class ValidationTest extends BasicEditorTest {
	
	private XMLValidator validator = XMLValidator.getInstance();
	
    public ValidationTest() {
        
        super();
        ZIP_FILE_NAME = "open-editor-test.zip";
        PROJECT_NAME = "OPEN-EDITOR-TEST";
        FILE_NAME = "xml/gbuna118.xml";
    }
    
    /**
     * @see org.eclipse.wst.xml.ui.tests.performance.BasicEditorTest#setUp()
     */
    protected void setUpPrefs() {
        IPreferenceStore store = SSEUIPlugin.getDefault().getPreferenceStore();
        // turn off reconciling
        store.setValue(CommonEditorPreferenceNames.EVALUATE_TEMPORARY_PROBLEMS, false);
        
        IPreferenceStore editorsStore = EditorsUI.getPreferenceStore();
        // turn off quick diff
        editorsStore.setValue(AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_ALWAYS_ON, false); 
    }
    
    public void testValidation() {
		String testfile = (new Path(F_SLASH + PROJECT_NAME + F_SLASH + FILE_NAME)).toString();
		startMeasuring();    
		validator.validate(testfile);
		stopMeasuring();
        commitMeasurements();
        assertPerformance();   
    }
    
}

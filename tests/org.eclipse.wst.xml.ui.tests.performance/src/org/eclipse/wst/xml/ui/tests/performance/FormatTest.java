package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.preferences.CommonEditorPreferenceNames;

/**
 * @author pavery
 */
public class FormatTest extends BasicEditorTest {
    public FormatTest() {
       
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
        // turn off reconciling
        store.setValue(CommonEditorPreferenceNames.EVALUATE_TEMPORARY_PROBLEMS, false);
        
        IPreferenceStore editorsStore = EditorsUI.getPreferenceStore();
        // turn off quick diff
        editorsStore.setValue(AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_ALWAYS_ON, false); 
    }    
    
    public void testFormat() {
        
        int warmUpRuns = 3;
        // warm up
        for(int i=0; i<warmUpRuns; i++) {
            setUpEditor(FILE_NAME);  
            fEditor.getTextViewer().doOperation(StructuredTextViewer.FORMAT_DOCUMENT);
            runEvents();
            EditorTestHelper.closeAllEditors();
            runEvents();
        }
        int realRuns = 3;
        for(int i=0; i<realRuns; i++) {
            setUpEditor(FILE_NAME);
            EditorTestHelper.calmDown(1000,2000,1000);
            // formatting time important 
            startMeasuring();    
            fEditor.getTextViewer().doOperation(StructuredTextViewer.FORMAT_DOCUMENT);
            runEvents();
            stopMeasuring();
        }
        commitMeasurements();
        assertPerformance();   
    }
}

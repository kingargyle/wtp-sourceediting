package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.preferences.CommonEditorPreferenceNames;

/**
 * @author pavery
 */
public class ScrollTest extends BasicEditorTest {
    
    public ScrollTest() {
        
        super();
        ZIP_FILE_NAME = "scroll-test.zip";
        PROJECT_NAME = "SCROLL-TEST";
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
    
    public void testScrolling() {
        
        setUpEditor(FILE_NAME);
        
        // wait for background threads to finish
        EditorTestHelper.calmDown(1000,5000,1000);
        
        // warmup runs
        IDocument doc = getEditor().getDocument();
        int lines = doc.getNumberOfLines();
        int iterations = 3;
        for(int j=0;j<iterations; j++) {
            doScroll(lines);
        }
        
        // do the test
        // time measurement important
        startMeasuring();
        doScroll(lines);
        stopMeasuring();
        commitMeasurements();
        assertPerformance();
    }
    
    public void doScroll(int lines) {
        setCaret(0); 
        for(int i=0; i<lines; i++) {
            type(SWT.ARROW_DOWN, SWT.NONE);
            runEvents();
        }
    }
}

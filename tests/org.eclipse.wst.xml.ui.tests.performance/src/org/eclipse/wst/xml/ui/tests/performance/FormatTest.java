package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.wst.sse.ui.StructuredTextViewer;

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
    
    public void testFormat() {
        
        setUpEditor(FILE_NAME);
        EditorTestHelper.calmDown(1000,5000,1000);
        
        // formatting time important 
        startMeasuring();    
        fEditor.getTextViewer().doOperation(StructuredTextViewer.FORMAT_DOCUMENT);
        runEvents();
        stopMeasuring();
        commitMeasurements();
        assertPerformance();   
    }
}

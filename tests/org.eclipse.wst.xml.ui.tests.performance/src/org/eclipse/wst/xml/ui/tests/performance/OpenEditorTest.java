package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.core.runtime.Path;

/**
 * @author pavery
 */
public class OpenEditorTest extends BasicEditorTest {
    
    public OpenEditorTest() {
        
        super();
        ZIP_FILE_NAME = "open-editor-test.zip";
        PROJECT_NAME = "OPEN-EDITOR-TEST";
        FILE_NAME = "xml/gbuna118.xml";
    }
    
    public void testOpenEditor() {
        
        int iterations = 5;
        
        // warmup runs
        for(int i=0;i<iterations; i++) {
            
            openEditor(new Path(F_SLASH + PROJECT_NAME + F_SLASH + FILE_NAME));
            runEvents();
            EditorTestHelper.closeAllEditors();
            runEvents();    
        }
        
        // do the test
        // model size important here
        startMeasuring();    
        
        openEditor(new Path(F_SLASH + PROJECT_NAME + F_SLASH + FILE_NAME));
        runEvents();

        stopMeasuring();
        commitMeasurements();
        assertPerformance();   
    }
    
}

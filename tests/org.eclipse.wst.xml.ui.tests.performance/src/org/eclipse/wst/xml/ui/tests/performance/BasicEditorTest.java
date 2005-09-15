package org.eclipse.wst.xml.ui.tests.performance;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.ui.tests.performance.util.FileUtil;
import org.eclipse.wst.xml.ui.tests.performance.util.ProjectUnzipUtility;

/**
 * @author pavery
 */
public class BasicEditorTest extends PerformanceTestCase {
     
    protected StructuredTextEditor fEditor;
    private StyledText fTextWidget;
    private Accessor fAccessor;
    private IDocument fDocument;
    private ProjectUnzipUtility fProjUtil;
    
    // subclasses need to set these
    protected String ZIP_FILE_NAME;
    protected String PROJECT_NAME;
    protected String FILE_NAME;
    
    protected final String F_SLASH = "/";
    
    
    protected void setUp() throws Exception {
        super.setUp();
        unzipProject();
        setUpPrefs();
    }
    
    /**
     * configure preferences exactly how you want them
     * for this test
     */
    protected void setUpPrefs() {
        // subclasses should implement
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
        EditorTestHelper.closeAllEditors();
    }
    
    private void unzipProject() {
        
        fProjUtil = new ProjectUnzipUtility();

        // root of workspace directory
        Location platformLocation = Platform.getInstanceLocation();

        File zipFile = null;
        try {
            zipFile = FileUtil.makeFileFor(ProjectUnzipUtility.PROJECT_ZIPS_FOLDER, ZIP_FILE_NAME, ProjectUnzipUtility.PROJECT_ZIPS_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(zipFile != null) {
            fProjUtil.unzipAndImport(zipFile, platformLocation.getURL().getPath());
            try {
                fProjUtil.initProject(PROJECT_NAME);
            } catch (CoreException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    protected void setUpEditor(String filename) {

		fEditor = openEditor(new Path(F_SLASH + PROJECT_NAME + F_SLASH + filename));
		assertNotNull(fEditor);
		fTextWidget = fEditor.getTextViewer().getTextWidget();
		assertNotNull(fTextWidget);
		fAccessor = new Accessor(fTextWidget, StyledText.class);
		fDocument = getEditor().getDocumentProvider().getDocument(getEditor().getEditorInput());
		assertNotNull(fDocument);
	}
    
    protected StructuredTextEditor openEditor(IPath path) {
        IFile file= ResourcesPlugin.getWorkspace().getRoot().getFile(path);
        assertTrue(file != null && file.exists());
        try {
            return (StructuredTextEditor)EditorTestHelper.openInEditor(file, true);
        } catch (PartInitException e) {
            fail();
            return null;
        }
    }
    
    /**
     * Type a character into the styled text.
     * 
     * @param character the character to type
     */
    protected void type(char character) {
        type(character, 0, 0);
    }
    
    /**
     * Type a character into the styled text.
     * 
     * @param character the character to type
     * @param keyCode the key code
     * @param stateMask the state mask
     */
    protected void type(char character, int keyCode, int stateMask) {
        Event event= new Event();
        event.character= character;
        event.keyCode= keyCode;
        event.stateMask= stateMask;
        fAccessor.invoke("handleKeyDown", new Object[] {event});
    }

    protected void type(int keyCode, int stateMask) {
        Event event= new Event();
        event.keyCode= keyCode;
        event.stateMask= stateMask;
        fAccessor.invoke("handleKeyDown", new Object[] {event});
    }
    
    protected void typeString(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++)
            type(chars[i]);
    }
    
    /**
     * Type a character into the styled text and drive the event loop.
     * 
     * @param character the character to type
     */
    protected void typeAndRun(char character) {
        type(character);
        runEvents();
    }

    protected void runEvents() {
        Display display = PlatformUI.getWorkbench().getDisplay();
        if (display != null) {
            while (display.readAndDispatch()) {
                // empty on purpose
            }
        }
    }
    
    protected void setCaret(int offset) {
        fEditor.getTextViewer().setSelection(new TextSelection(offset, 0));
    }
    protected synchronized void sleep(int time) {
        try {
            wait(time);
        } catch (InterruptedException e) {
            //
        }
    }
    
    protected StructuredTextEditor getEditor() {
        return fEditor;
    }
}

package org.eclipse.wst.javascript.ui.internal.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.javascript.core.internal.JavaScriptCorePlugin;
import org.eclipse.wst.javascript.core.internal.contenttype.ContentTypeIdForJavaScript;
import org.eclipse.wst.javascript.core.internal.preferences.JavaScriptCorePreferenceNames;
import org.eclipse.wst.javascript.ui.internal.editor.JSEditorPlugin;
import org.eclipse.wst.javascript.ui.internal.editor.JavaScriptUIMessages;
import org.eclipse.wst.javascript.ui.internal.editor.Logger;

public class NewJSWizard extends Wizard implements INewWizard {
	static String PAGE_IMAGE = "/icons/full/wizban/newjscriptfile_wiz.gif"; //$NON-NLS-1$
	private WizardNewFileCreationPage fNewFilePage;
	private IStructuredSelection fSelection;
	private IContentType fContentType;
	private List fValidExtensions = null;

	/**
	 * Adds default extension to the filename
	 * 
	 * @param filename
	 * @return
	 */
	String addDefaultExtension(String filename) {
		StringBuffer newFileName = new StringBuffer(filename);

		Preferences preference = JavaScriptCorePlugin.getDefault().getPluginPreferences();
		String ext = preference.getString(JavaScriptCorePreferenceNames.DEFAULT_EXTENSION);

		newFileName.append("."); //$NON-NLS-1$
		newFileName.append(ext);

		return newFileName.toString();
	}

	/**
	 * Get content type associated with this new file wizard
	 * 
	 * @return IContentType
	 */
	IContentType getContentType() {
		if (fContentType == null)
			fContentType = Platform.getContentTypeManager().getContentType(ContentTypeIdForJavaScript.ContentTypeID_JAVASCRIPT);
		return fContentType;
	}

	/**
	 * Get list of valid extensions for JavaScript Content type
	 * 
	 * @return
	 */
	List getValidExtensions() {
		if (fValidExtensions == null) {
			IContentType type = getContentType();
			fValidExtensions = new ArrayList(Arrays.asList(type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC)));
		}
		return fValidExtensions;
	}
	
	/**
	 * Verifies if fileName is valid name for content type. Takes base content
	 * type into consideration.
	 * 
	 * @param fileName
	 * @return true if extension is valid for this content type
	 */
	boolean extensionValidForContentType(String fileName) {
		boolean valid = false;

		IContentType type = getContentType();
		// there is currently an extension
		if (fileName.lastIndexOf('.') != -1) {
			// check what content types are associated with current extension
			IContentType[] types = Platform.getContentTypeManager().findContentTypesFor(fileName);
			int i = 0;
			while (i < types.length && !valid) {
				valid = types[i].isKindOf(type);
				++i;
			}
		}
		else
			valid = true; // no extension so valid
		return valid;
	}

	public void addPages() {
		fNewFilePage = new WizardNewFileCreationPage("JSWizardNewFileCreationPage", new StructuredSelection(IDE.computeSelectedResources(fSelection))) { //$NON-NLS-1$
			protected boolean validatePage() {
				String fileName = getFileName();
				IPath fullPath = getContainerFullPath();
				if ((fullPath != null) && (fullPath.isEmpty() == false) && (fileName != null)) {
					// check that filename does not contain invalid extension
					if (!extensionValidForContentType(fileName)) {
						setErrorMessage(NLS.bind(JavaScriptUIMessages._ERROR_FILENAME_MUST_END_JS, getValidExtensions().toString()));
						return false;
					}
					// no file extension specified so check adding default
					// extension doesn't equal a file that already exists
					if (fileName.lastIndexOf('.') == -1) {
						String newFileName = addDefaultExtension(fileName);
						IPath resourcePath = fullPath.append(newFileName);

						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IStatus result = workspace.validatePath(resourcePath.toString(), IResource.FOLDER);
						if (!result.isOK()) {
							// path invalid
							setErrorMessage(result.getMessage());
							return false;
						}

						if ((workspace.getRoot().getFolder(resourcePath).exists() || workspace.getRoot().getFile(resourcePath).exists())) {
							setErrorMessage(JavaScriptUIMessages.ResourceGroup_nameExists);
							return false;
						}
					}
				}
				setErrorMessage(null);
				return super.validatePage();
			}
		};
		fNewFilePage.setTitle(JavaScriptUIMessages._UI_WIZARD_NEW_HEADING); //$NON-NLS-1$
		fNewFilePage.setDescription(JavaScriptUIMessages._UI_WIZARD_NEW_DESCRIPTION); //$NON-NLS-1$

		addPage(fNewFilePage);
	}

	public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
		fSelection = aSelection;
		setWindowTitle(JavaScriptUIMessages._UI_WIZARD_NEW_TITLE); //$NON-NLS-1$
		if (PAGE_IMAGE != null) {
			ImageDescriptor descriptor = JSEditorPlugin.getDefault().getImageRegistry().getDescriptor(PAGE_IMAGE);
			if (descriptor == null) {
				descriptor = ImageDescriptor.createFromURL(JSEditorPlugin.getDefault().getBundle().getEntry(PAGE_IMAGE));
				JSEditorPlugin.getDefault().getImageRegistry().put(PAGE_IMAGE, descriptor);
			}
			setDefaultPageImageDescriptor(descriptor);
		}
	}

	private void openEditor(final IFile file) {
		if (file != null) {
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, file, true);
					}
					catch (PartInitException e) {
						Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
					}
				}
			});
		}
	}

	public boolean performFinish() {
		boolean performedOK = false;

		// no file extension specified so add default extension
		String fileName = fNewFilePage.getFileName();
		if (fileName.lastIndexOf('.') == -1) {
			String newFileName = addDefaultExtension(fileName);
			fNewFilePage.setFileName(newFileName);
		}

		// create a new empty file
		IFile file = fNewFilePage.createNewFile();

		// if there was problem with creating file, it will be null, so make
		// sure to check
		if (file != null) {
			// open the file in editor
			openEditor(file);

			// everything's fine
			performedOK = true;
		}
		return performedOK;
	}

}
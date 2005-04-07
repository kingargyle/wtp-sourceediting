package org.eclipse.wst.javascript.ui.internal.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.javascript.ui.internal.editor.JSEditorPlugin;
import org.eclipse.wst.javascript.ui.internal.editor.JavaScriptUIMessages;
import org.eclipse.wst.javascript.ui.internal.editor.Logger;

public class NewJSWizard extends Wizard implements INewWizard {
	static String PAGE_IMAGE = "/icons/full/wizban/newjscriptfile_wiz.gif"; //$NON-NLS-1$
	private WizardNewFileCreationPage fNewFilePage;
	private IStructuredSelection fSelection;

	public void addPages() {
		fNewFilePage = new WizardNewFileCreationPage("JSWizardNewFileCreationPage", new StructuredSelection(IDE.computeSelectedResources(fSelection))) { //$NON-NLS-1$
			protected boolean validatePage() {
				IPath handlePath = new Path(getFileName());
				String extension = handlePath.getFileExtension();
				if (extension == null || !extension.equalsIgnoreCase("js")) { //$NON-NLS-1$
					setErrorMessage(JavaScriptUIMessages.getString("_ERROR_FILENAME_MUST_END_JS")); //$NON-NLS-1$
					return false;
				}
				setErrorMessage(null);
				return super.validatePage();
			}
		};
		fNewFilePage.setTitle(JavaScriptUIMessages.getString("_UI_WIZARD_NEW_HEADING")); //$NON-NLS-1$
		fNewFilePage.setDescription(JavaScriptUIMessages.getString("_UI_WIZARD_NEW_DESCRIPTION")); //$NON-NLS-1$

		addPage(fNewFilePage);
	}

	public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
		fSelection = aSelection;
		setWindowTitle(JavaScriptUIMessages.getString("_UI_WIZARD_NEW_TITLE")); //$NON-NLS-1$
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
		IFile file = fNewFilePage.createNewFile();
		openEditor(file);
		return true;
	}

}
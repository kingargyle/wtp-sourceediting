package org.eclipse.wst.javascript.ui.internal.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
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
import org.eclipse.wst.javascript.ui.internal.editor.Logger;

public class NewJSWizard extends Wizard implements INewWizard {
	static String PAGE_IMAGE = "/icons/full/wizban/newjscriptfile_wiz.gif";
	private WizardNewFileCreationPage fNewFilePage;
	private IStructuredSelection fSelection;

	public void addPages() {
		fNewFilePage = new WizardNewFileCreationPage("JSWizardNewFileCreationPage", new StructuredSelection(IDE.computeSelectedResources(fSelection))) {
			protected IFile createFileHandle(IPath filePath) {
				// enforce the creation of a ".js" file
				IPath handlePath = filePath;
				String extension = handlePath.getFileExtension();
				if (extension == null || !extension.equalsIgnoreCase("js")) {
					handlePath = handlePath.addFileExtension("js");
				}
				return super.createFileHandle(handlePath);
			}
		};
		fNewFilePage.setTitle(JSEditorPlugin.getResourceString("%_UI_WIZARD_NEW_HEADING"));
		fNewFilePage.setDescription(JSEditorPlugin.getResourceString("%_UI_WIZARD_NEW_DESCRIPTION"));

		addPage(fNewFilePage);
	}

	public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
		fSelection = aSelection;
		setWindowTitle(JSEditorPlugin.getResourceString("%_UI_WIZARD_NEW_TITLE"));
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
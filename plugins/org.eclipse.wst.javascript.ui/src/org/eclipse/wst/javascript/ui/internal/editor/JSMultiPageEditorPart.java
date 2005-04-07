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
package org.eclipse.wst.javascript.ui.internal.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class JSMultiPageEditorPart extends MultiPageEditorPart implements IPropertyListener {

	class TextInputListener implements ITextInputListener {
		public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
		}
		public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
			if (newInput != null) {
				setInput(fEditor.getEditorInput());
			}
		}
	}

	private JSEditor fEditor = null;
	private JSPreviewPage fPreviewPage = null;
	private int fPreviewPageIndex = 1;
	private int fSourcePageIndex = 0;
	private Image fTitleImage = null;

	/**
	 * Adds the preview page of the multi-page editor.
	 */
	protected void addPreviewPage() {
		fPreviewPage = new JSPreviewPage(getContainer(), fEditor);
		fPreviewPageIndex = addPage(fPreviewPage.getControl());
		setPageText(fPreviewPageIndex, JavaScriptUIMessages.getString("%Preview")); //$NON-NLS-1$
	}

	/**
	 * Adds the source page of the multi-page editor.
	 */
	protected void addSourcePage() {
		try {
			fEditor = new JSEditor();
			fEditor.setEditorPart(this);
			fEditor.addPropertyListener(this);
			fSourcePageIndex = addPage(fEditor, getEditorInput());
			setPageText(fSourcePageIndex, JavaScriptUIMessages.getString("%Source")); //$NON-NLS-1$
		}
		catch (PartInitException exception) {
			// dispose editor
			dispose();

			throw new RuntimeException(exception);
		}
	}

	protected void createPages() {
		addSourcePage();
		addPreviewPage();

		// use the javascript icon with beige box instead of the
		// javascript icon for the navigator view so the letter J is
		// visible
		fTitleImage = JSEditorPluginImageHelper.getInstance().getImageDescriptor(JSEditorPluginImages.IMG_OBJ_JAVASCRIPT_VIEW).createImage();
		setTitleImage(fTitleImage);
	}

	public void dispose() {
		if (fEditor != null)
			fEditor.dispose();

		if (fTitleImage != null)
			fTitleImage.dispose();
	}
	
	public void doSave(IProgressMonitor monitor) {
		fEditor.doSave(monitor);
	}

	public void doSaveAs() {
		fEditor.doSaveAs();
	}

	public Object getAdapter(Class required) {
		// content outline page
		if (IContentOutlinePage.class.equals(required))
			return fEditor.getAdapter(IContentOutlinePage.class);

		// goto marker
		if (IGotoMarker.class.equals(required) && fEditor != null)
			return fEditor.getAdapter(IGotoMarker.class);

		if (IShowInTargetList.class.equals(required))
			return fEditor.getAdapter(required);
		if (IContentOutlinePage.class.equals(required))
			return fEditor.getAdapter(required);
		if (ITextEditor.class.equals(required))
			return fEditor;

		return super.getAdapter(required);
	}

	public String getTitle() {
		if (getEditorInput() != null) {
			return getEditorInput().getName();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}

	public boolean isSaveAsAllowed() {
		return fEditor.isSaveAsAllowed();
	}

	protected void pageChange(int newPageIndex) {
		if (newPageIndex == fPreviewPageIndex)
			fPreviewPage.refresh();

		super.pageChange(newPageIndex);
	}

	public void propertyChanged(Object source, int propId) {
		switch (propId) {
			case IEditorPart.PROP_INPUT :
				{
					if (source == fEditor) {
						if (fEditor.getEditorInput() != getEditorInput()) {
							setInput(fEditor.getEditorInput());
						}
					}
					break;
				}
			case IWorkbenchPart.PROP_TITLE :
				{
					if (source == fEditor) {
						if (fEditor.getEditorInput() != getEditorInput()) {
							setInput(fEditor.getEditorInput());
						}
					}
					break;
				}
			default :
				{
					if (source == fEditor) {
						firePropertyChange(propId);
					}
					break;
				}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
	}
}

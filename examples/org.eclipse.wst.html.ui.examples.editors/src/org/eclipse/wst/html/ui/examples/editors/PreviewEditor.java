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
package org.eclipse.wst.html.ui.examples.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.ILocationProvider;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.html.core.HTML40Namespace;
import org.eclipse.wst.html.ui.StructuredTextEditorHTML;
import org.eclipse.wst.html.ui.examples.editors.internal.Logger;
import org.eclipse.wst.sse.core.IStructuredModel;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.ui.ViewerSelectionManager;
import org.eclipse.wst.sse.ui.extension.IExtendedSimpleEditor;
import org.eclipse.wst.xml.core.document.XMLElement;
import org.eclipse.wst.xml.core.document.XMLModel;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLTableTreeViewer;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * A simple multi-page editor embedding a Text Editor and the SWT Browser
 * widget synchronized on page switch.
 * 
 * The Text editor class used is the SSE HTML source editor.
 * 
 * @author nitin
 */
public class PreviewEditor extends MultiPageEditorPart implements ITextEditor, IExtendedSimpleEditor, IReusableEditor {
	Control fPreviewControl = null;
	ITextEditor fSourcePage = null;
	/**
	 * Controls whether to also insert the XML design page into this editor.
	 * This combination is largely untested, so it's more of a
	 * proof-of-concept here.
	 */
	boolean showXMLDesign = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#close(boolean)
	 */
	public void close(boolean save) {
		getSourcePage().close(save);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createPages()
	 */
	protected void createPages() {
		fSourcePage = createSourcePage();
		try {
			addPage(fSourcePage, getEditorInput());
		}
		catch (PartInitException e) {
			if (getPageCount() > 0) {
				removePage(0);
			}
		}

		if (showXMLDesign) {
			final XMLTableTreeViewer tableTreeViewer = new XMLTableTreeViewer(getContainer());

			if (fSourcePage.getAdapter(ViewerSelectionManager.class) != null) {
				tableTreeViewer.setViewerSelectionManager((ViewerSelectionManager) fSourcePage.getAdapter(ViewerSelectionManager.class));
			}
			class ModelUpdater implements IPropertyListener, DisposeListener {
				IStructuredModel model = null;
				int referenceCount = 0;

				public void propertyChanged(Object source, int propId) {
					if (propId == IEditorPart.PROP_INPUT) {
						if (model != null && referenceCount > 0) {
							model.releaseFromRead();
							referenceCount--;
						}
						if (!tableTreeViewer.getControl().isDisposed()) {
							model = StructuredModelManager.getInstance().getModelManager().getExistingModelForRead(fSourcePage.getDocumentProvider().getDocument(fSourcePage.getEditorInput()));
							if (model != null) {
								referenceCount++;
								tableTreeViewer.setModel(model);
							}
						}
					}
				}

				public void widgetDisposed(DisposeEvent e) {
					if (model != null && referenceCount > 0) {
						referenceCount--;
						model.releaseFromRead();
					}
				}
			}
			ModelUpdater modelController = new ModelUpdater();
			addPropertyListener(modelController);
			getContainer().addDisposeListener(modelController);
			modelController.propertyChanged(this, IEditorPart.PROP_INPUT);

			addPage(tableTreeViewer.getControl());
		}

		fPreviewControl = createPreviewControl();
		addPage(fPreviewControl);

		int page = 0;
		setPageText(page++, "Source"); //$NON-NLS-1$
		if (showXMLDesign) {
			setPageText(page++, "Design"); //$NON-NLS-1$
		}
		setPageText(page++, "Preview"); //$NON-NLS-1$
	}

	/**
	 * Creates the Browser preview control
	 * 
	 * @return
	 */
	private Control createPreviewControl() {
		return new Browser(getContainer(), SWT.READ_ONLY);
	}

	/**
	 * Creates the HTML source page
	 * 
	 * @return
	 */
	private ITextEditor createSourcePage() {
		StructuredTextEditorHTML editor = new StructuredTextEditorHTML();
		editor.setEditorPart(this);
		return editor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#doRevertToSaved()
	 */
	public void doRevertToSaved() {
		getSourcePage().doRevertToSaved();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		getSourcePage().doSave(monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#doSaveAs()
	 */
	public void doSaveAs() {
		getSourcePage().doSaveAs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#getAction(java.lang.String)
	 */
	public IAction getAction(String actionId) {
		return getSourcePage().getAction(actionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		return getSourcePage().getAdapter(adapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.sse.editor.extension.IExtendedSimpleEditor#getCaretPosition()
	 */
	public int getCaretPosition() {
		if (getSourcePage() instanceof IExtendedSimpleEditor) {
			return ((IExtendedSimpleEditor) getSourcePage()).getCaretPosition();
		}
		ITextSelection selection = (ITextSelection) getSourcePage().getSelectionProvider().getSelection();
		if (selection != null) {
			return selection.getOffset();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.sse.editor.extension.IExtendedSimpleEditor#getDocument()
	 */
	public IDocument getDocument() {
		return getSourcePage().getDocumentProvider().getDocument(getEditorInput());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#getDocumentProvider()
	 */
	public IDocumentProvider getDocumentProvider() {
		return getSourcePage().getDocumentProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.sse.editor.extension.IExtendedSimpleEditor#getEditorPart()
	 */
	public IEditorPart getEditorPart() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#getHighlightRange()
	 */
	public IRegion getHighlightRange() {
		return getSourcePage().getHighlightRange();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#getSelectionProvider()
	 */
	public ISelectionProvider getSelectionProvider() {
		return getSourcePage().getSelectionProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.sse.editor.extension.IExtendedSimpleEditor#getSelectionRange()
	 */
	public Point getSelectionRange() {
		if (getSourcePage() instanceof IExtendedSimpleEditor) {
			return ((IExtendedSimpleEditor) getSourcePage()).getSelectionRange();
		}
		ITextSelection selection = (ITextSelection) getSourcePage().getSelectionProvider().getSelection();
		if (selection != null) {
			return new Point(selection.getOffset(), selection.getOffset() + selection.getLength());
		}
		return new Point(0, 0);
	}

	protected ITextEditor getSourcePage() {
		return fSourcePage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#getTitle()
	 */
	public String getTitle() {
		String title = super.getTitle();
		if (title == null && getEditorInput() != null) {
			title = getEditorInput().getName();
		}
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorPart#init(org.eclipse.ui.IEditorSite,
	 *      org.eclipse.ui.IEditorInput)
	 */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
	}

	protected void interruptPreview() {
		((Browser) fPreviewControl).stop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#isEditable()
	 */
	public boolean isEditable() {
		return getSourcePage().isEditable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return getSourcePage().isSaveAsAllowed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#pageChange(int)
	 */
	protected void pageChange(int newPageIndex) {
		getEditorSite().getActionBars().getStatusLineManager().setErrorMessage(null);//$NON-NLS-1$
		interruptPreview();

		super.pageChange(newPageIndex);
		if (getControl(newPageIndex) == fSourcePage) {
			getEditorSite().getActionBarContributor().setActiveEditor(fSourcePage);
		}
		else {
			getEditorSite().getActionBarContributor().setActiveEditor(this);
			if (getControl(newPageIndex) == fPreviewControl) {
				updatePreviewContent();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#removeActionActivationCode(java.lang.String)
	 */
	public void removeActionActivationCode(String actionId) {
		getSourcePage().removeActionActivationCode(actionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#resetHighlightRange()
	 */
	public void resetHighlightRange() {
		getSourcePage().resetHighlightRange();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#selectAndReveal(int, int)
	 */
	public void selectAndReveal(int offset, int length) {
		getSourcePage().selectAndReveal(offset, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#setAction(java.lang.String,
	 *      org.eclipse.jface.action.IAction)
	 */
	public void setAction(String actionID, IAction action) {
		getSourcePage().setAction(actionID, action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#setActionActivationCode(java.lang.String,
	 *      char, int, int)
	 */
	public void setActionActivationCode(String actionId, char activationCharacter, int activationKeyCode, int activationStateMask) {
		setActionActivationCode(actionId, activationCharacter, activationKeyCode, activationStateMask);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#setHighlightRange(int, int,
	 *      boolean)
	 */
	public void setHighlightRange(int offset, int length, boolean moveCursor) {
		getSourcePage().setHighlightRange(offset, length, moveCursor);
	}

	/**
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		super.setInitializationData(config, propertyName, data);
		if (data != null) {
			if (data instanceof String && data.toString().length() > 0) {
				showXMLDesign = data.toString().equalsIgnoreCase("true"); //$NON-NLS-1$
			}
			else if (data instanceof Boolean) {
				showXMLDesign = ((Boolean) data).booleanValue();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	public void setInput(IEditorInput input) {
		super.setInput(input);
		if (fSourcePage != null) {
			((IReusableEditor) fSourcePage).setInput(input);
		}
		if (fPreviewControl != null && getControl(getActivePage()) == fPreviewControl) {
			interruptPreview();
			updatePreviewContent();
		}
		setPartName(input.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#showHighlightRangeOnly(boolean)
	 */
	public void showHighlightRangeOnly(boolean showHighlightRangeOnly) {
		getSourcePage().showHighlightRangeOnly(showHighlightRangeOnly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ITextEditor#showsHighlightRangeOnly()
	 */
	public boolean showsHighlightRangeOnly() {
		return getSourcePage().showsHighlightRangeOnly();
	}

	/**
	 * Update the contents of the Preview page
	 */
	protected void updatePreviewContent() {
		if (getEditorInput() == null || getSourcePage() == null || getSourcePage().getDocumentProvider() == null)
			return;

		IDocument editDocument = getSourcePage().getDocumentProvider().getDocument(getSourcePage().getEditorInput());
		IDocument htmlSource = new org.eclipse.jface.text.Document(editDocument.get());

		IStructuredModel editModel = null;
		int insertOffset = 0;
		List removalRegions = new ArrayList(2);
		try {
			editModel = StructuredModelManager.getInstance().getModelManager().getExistingModelForRead(editDocument);
			if (editModel != null && editModel instanceof XMLModel) {
				Document document = ((XMLModel) editModel).getDocument();
				// remove meta tags specifying encoding as required by Browser API
				NodeList metaElements = document.getElementsByTagName(HTML40Namespace.ElementName.META);
				for (int i = 0; i < metaElements.getLength(); i++) {
					XMLElement meta = (XMLElement) metaElements.item(i);
					if (insertOffset == 0)
						insertOffset = meta.getStartOffset();
					insertOffset = Math.max(0, Math.min(insertOffset, meta.getStartOffset()));
					if (meta.getAttribute(HTML40Namespace.ATTR_NAME_HTTP_EQUIV).equals("Content-Type") && meta.getAttribute(HTML40Namespace.ATTR_NAME_CONTENT).indexOf("charset") > 0) { //$NON-NLS-2$ //$NON-NLS-1$
						if (meta.getStartStructuredDocumentRegion() != null)
							removalRegions.add(meta.getStartStructuredDocumentRegion());
						if (meta.getEndStructuredDocumentRegion() != null)
							removalRegions.add(meta.getEndStructuredDocumentRegion());
					}
				}
				// remove existing base elements with hrefs so we can add one for the local location
				NodeList baseElements = document.getElementsByTagName(HTML40Namespace.ElementName.BASE);
				for (int i = 0; i < baseElements.getLength(); i++) {
					XMLElement base = (XMLElement) baseElements.item(i);
					if (insertOffset == 0)
						insertOffset = base.getStartOffset();
					insertOffset = Math.max(0, Math.min(insertOffset, base.getStartOffset()));
					if (base.getStartStructuredDocumentRegion() != null)
						removalRegions.add(base.getStartStructuredDocumentRegion());
					if (base.getEndStructuredDocumentRegion() != null)
						removalRegions.add(base.getEndStructuredDocumentRegion());
				}
			}

			for (int i = removalRegions.size() - 1; i >= 0; i--) {
				IStructuredDocumentRegion region = (IStructuredDocumentRegion) removalRegions.get(i);
				try {
					htmlSource.replace(region.getStartOffset(), region.getEndOffset() - region.getStartOffset(), ""); //$NON-NLS-1$
				}
				catch (BadLocationException e1) {
					Logger.logException(e1);
				}
			}

			if (insertOffset == 0) {
				Document document = ((XMLModel) editModel).getDocument();
				NodeList headElements = document.getElementsByTagName(HTML40Namespace.ElementName.HEAD);
				if (headElements.getLength() > 0) {
					XMLElement head = (XMLElement) headElements.item(0);
					if (head.getStartStructuredDocumentRegion() != null) {
						insertOffset = head.getStartStructuredDocumentRegion().getEndOffset();
					}
					else {
						insertOffset = head.getEndOffset();
					}
				}

			}
		}
		catch (Exception e) {
			Logger.logException(e);
		}
		finally {
			if (editModel != null)
				editModel.releaseFromRead();
		}

		String location = null;
		if (getEditorInput().getAdapter(IFile.class) != null) {
			location = "file:" + ((IFile) getEditorInput().getAdapter(IFile.class)).getLocation(); //$NON-NLS-1$
		}
		else if (getEditorInput() instanceof ILocationProvider) {
			location = "file:" + ((ILocationProvider) getEditorInput()).getPath(getEditorInput()); //$NON-NLS-1$
		}
		else {
			location = "file:" + ResourcesPlugin.getWorkspace().getRoot().getLocation(); //$NON-NLS-1$
		}

		try {
			htmlSource.replace(insertOffset, 0, "<base href=\"" + location + "\" />"); //$NON-NLS-2$ //$NON-NLS-1$
		}
		catch (BadLocationException e1) {
			Logger.logException(e1);
		}

		boolean rendered = ((Browser) fPreviewControl).setText(htmlSource.get());
		if (!rendered) {
			getEditorSite().getActionBars().getStatusLineManager().setErrorMessage("Failure rendering");//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.sse.editor.extension.IExtendedSimpleEditor#validateEdit(org.eclipse.swt.widgets.Shell)
	 */
	public IStatus validateEdit(Shell context) {
		if (getSourcePage() instanceof IExtendedSimpleEditor) {
			return ((IExtendedSimpleEditor) getSourcePage()).validateEdit(context);
		}
		return new Status(IStatus.OK, "org.eclipse.wst.html.ui.tests", IStatus.OK, "", null); //$NON-NLS-2$ //$NON-NLS-1$
	}
}
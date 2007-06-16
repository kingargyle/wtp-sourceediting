/*******************************************************************************
 * Copyright (c) 2004 , 2007 IBM Corporation and others.
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
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.ILocationProvider;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.html.core.internal.provisional.HTML40Namespace;
import org.eclipse.wst.html.core.internal.provisional.contenttype.ContentTypeIdForHTML;
import org.eclipse.wst.html.ui.examples.editors.internal.Logger;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLTableTreeViewer;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * A simple multi-page editor embedding a Text Editor and the SWT Browser
 * widget synchronized on page switch. Other issues like validate-edit,
 * synchronization with resource changes, and action contribution are left for
 * larger examples and implementations.
 * 
 * The Text editor class used is the SSE source editor.
 * 
 * @author nitin
 */
public class PreviewEditor extends MultiPageEditorPart implements IReusableEditor {
	Control fPreviewControl = null;
	int fPreviewPageIndex = -1;
	ITextEditor fSourcePage = null;
	int fSourcePageIndex = -1;

	/**
	 * Controls whether to also insert the XML design page into this editor.
	 * This combination is largely untested, so it's more of a
	 * proof-of-concept here.
	 */
	boolean fShowXMLDesign = false;
	XMLTableTreeViewer fDesignPage = null;
	int fDesignPageIndex = -1;

	String fPreferredContentTypeID = ContentTypeIdForHTML.ContentTypeID_HTML;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createPages()
	 */
	protected void createPages() {
		fSourcePage = createSourcePage();
		try {
			fSourcePageIndex = addPage(fSourcePage, getEditorInput());
		}
		catch (PartInitException e) {
			if (getPageCount() > 0) {
				removePage(0);
			}
		}

		if (fShowXMLDesign) {
			/**
			 * Note, little effort has been spent to keep selection
			 * synchronized between the various pages.
			 */
			fDesignPage = new XMLTableTreeViewer(getContainer());

			class ModelUpdater implements IPropertyListener, DisposeListener {
				public void propertyChanged(Object source, int propId) {
					if (propId == IEditorPart.PROP_INPUT) {
						if (!fDesignPage.getControl().isDisposed()) {
							fDesignPage.setDocument(fSourcePage.getDocumentProvider().getDocument(getEditorInput()));
						}
					}
				}

				public void widgetDisposed(DisposeEvent e) {
					if (!fDesignPage.getControl().isDisposed()) {
						fDesignPage.setDocument(null);
					}
				}
			}
			ModelUpdater modelController = new ModelUpdater();
			addPropertyListener(modelController);
			getContainer().addDisposeListener(modelController);
			modelController.propertyChanged(this, IEditorPart.PROP_INPUT);
			fDesignPageIndex = addPage(fDesignPage.getControl());
			/*
			 * The source page is already sending notifications to the
			 * workbench. Take advantage of that and that we're not both
			 * visible at the same time.
			 */
			fDesignPage.addPostSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					fSourcePage.getSelectionProvider().setSelection(event.getSelection());
				}
			});
		}

		fPreviewControl = createPreviewControl();
		fPreviewPageIndex = addPage(fPreviewControl);

		setPageText(fSourcePageIndex, "Source"); //$NON-NLS-1$
		if (fShowXMLDesign) {
			setPageText(fDesignPageIndex, "Design"); //$NON-NLS-1$
		}
		setPageText(fPreviewPageIndex, "Preview"); //$NON-NLS-1$

		getEditorSite().getActionBarContributor().setActiveEditor(fSourcePage);
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
		StructuredTextEditor editor = new StructuredTextEditor();
		editor.setEditorPart(this);
		return editor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createSite(org.eclipse.ui.IEditorPart)
	 */
	protected IEditorSite createSite(IEditorPart editor) {
		IEditorSite site = null;
		if (editor == fSourcePage) {
			site = new MultiPageEditorSite(this, editor) {
				public String getId() {
					return fPreferredContentTypeID + ".source"; //$NON-NLS-1$;
				}
			};
		}
		else {
			site = super.createSite(editor);
		}
		return site;
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
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class key) {
		Object adapter = super.getAdapter(key);
		if (adapter == null)
			adapter = getSourcePage().getAdapter(key);
		return adapter;
	}

	private ITextEditor getSourcePage() {
		return fSourcePage;
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
	}

	private void interruptPreview() {
		((Browser) fPreviewControl).stop();
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
		getEditorSite().getActionBars().getStatusLineManager().setErrorMessage(null);
		interruptPreview();

		super.pageChange(newPageIndex);
		if (newPageIndex == fSourcePageIndex) {
			getEditorSite().getActionBarContributor().setActiveEditor(fSourcePage);
			if (fShowXMLDesign) {
				fSourcePage.getSelectionProvider().setSelection(fDesignPage.getSelection());
			}
		}
		else {
			getEditorSite().getActionBarContributor().setActiveEditor(this);
			if (newPageIndex == fPreviewPageIndex) {
				getEditorSite().setSelectionProvider(null);
				updatePreviewContent();
			}
			else if (newPageIndex == fDesignPageIndex) {
				fDesignPage.setSelection(fSourcePage.getSelectionProvider().getSelection());
			}
		}
		updateSelectionProvider();
	}

	private void updateSelectionProvider() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		super.setInitializationData(config, propertyName, data);
		if (data != null) {
			if (data instanceof String && data.toString().length() > 0) {
				fShowXMLDesign = data.toString().equalsIgnoreCase("true"); //$NON-NLS-1$
			}
			else if (data instanceof Boolean) {
				fShowXMLDesign = ((Boolean) data).booleanValue();
			}
			else if (data instanceof Map) {
				fShowXMLDesign = Boolean.valueOf((String) ((Map) data).get("showGrid")).booleanValue(); //$NON-NLS-1$
				fPreferredContentTypeID = ((Map) data).get("contentType").toString(); //$NON-NLS-1$
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

	/**
	 * Update the contents of the Preview page
	 */
	private void updatePreviewContent() {
		if (getEditorInput() == null || getSourcePage() == null || getSourcePage().getDocumentProvider() == null)
			return;

		BusyIndicator.showWhile(getSite().getShell().getDisplay(), new Runnable() {
			public void run() {
				IDocument editDocument = getSourcePage().getDocumentProvider().getDocument(getSourcePage().getEditorInput());
				IDocument sourceDocument = new org.eclipse.jface.text.Document(editDocument.get());

				IStructuredModel editModel = null;
				int insertOffset = 0;
				List removalRegions = new ArrayList(2);
				try {
					editModel = StructuredModelManager.getModelManager().getExistingModelForRead(editDocument);
					if (editModel != null && editModel instanceof IDOMModel) {
						Document document = ((IDOMModel) editModel).getDocument();
						/*
						 * remove meta tags specifying encoding as required by
						 * Browser API
						 */
						NodeList metaElements = document.getElementsByTagName(HTML40Namespace.ElementName.META);
						for (int i = 0; i < metaElements.getLength(); i++) {
							IDOMElement meta = (IDOMElement) metaElements.item(i);
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
						/*
						 * remove existing base elements with hrefs so we can
						 * add one for the local location
						 */
						NodeList baseElements = document.getElementsByTagName(HTML40Namespace.ElementName.BASE);
						for (int i = 0; i < baseElements.getLength(); i++) {
							IDOMElement base = (IDOMElement) baseElements.item(i);
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
							sourceDocument.replace(region.getStartOffset(), region.getEndOffset() - region.getStartOffset(), ""); //$NON-NLS-1$
						}
						catch (BadLocationException e1) {
							Logger.logException(e1);
						}
					}

					if (insertOffset == 0) {
						Document document = ((IDOMModel) editModel).getDocument();
						NodeList headElements = document.getElementsByTagName(HTML40Namespace.ElementName.HEAD);
						if (headElements.getLength() > 0) {
							IDOMElement head = (IDOMElement) headElements.item(0);
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
					sourceDocument.replace(insertOffset, 0, "<base href=\"" + location + "\" />"); //$NON-NLS-2$ //$NON-NLS-1$
				}
				catch (BadLocationException e1) {
					Logger.logException(e1);
				}

				boolean rendered = ((Browser) fPreviewControl).setText(sourceDocument.get());
				if (!rendered) {
					getEditorSite().getActionBars().getStatusLineManager().setErrorMessage("Failure rendering");//$NON-NLS-1$
				}
			}
		});

	}
}
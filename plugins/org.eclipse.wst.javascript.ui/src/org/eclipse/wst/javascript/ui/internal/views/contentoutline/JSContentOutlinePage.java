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
package org.eclipse.wst.javascript.ui.internal.views.contentoutline;

import java.text.Collator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.wst.javascript.common.ui.ContentElement;
import org.eclipse.wst.javascript.common.ui.ContentElementComparerImpl;
import org.eclipse.wst.javascript.common.ui.JSContentElementImpl;
import org.eclipse.wst.javascript.ui.internal.editor.JSEditorPlugin;
import org.eclipse.wst.javascript.ui.internal.editor.JSEditorPluginImageHelper;
import org.eclipse.wst.javascript.ui.internal.editor.JSEditorPluginImages;
import org.eclipse.wst.javascript.ui.internal.editor.JavaScriptUIMessages;
import org.eclipse.wst.javascript.ui.internal.editor.SimpleTreeViewer;
import org.eclipse.wst.javascript.ui.internal.editor.SimpleViewerSelectionManagerImpl;
import org.eclipse.wst.sse.core.exceptions.SourceEditingRuntimeException;
import org.eclipse.wst.sse.ui.edit.util.SharedEditorPluginImageHelper;
import org.eclipse.wst.sse.ui.internal.ViewerSelectionManager;
import org.eclipse.wst.sse.ui.internal.contentoutline.PropertyChangeUpdateAction;
import org.eclipse.wst.sse.ui.internal.contentoutline.PropertyChangeUpdateActionContributionItem;

public class JSContentOutlinePage extends ContentOutlinePage implements IDocumentListener {
	protected class SortAction extends PropertyChangeUpdateAction {
		private TreeViewer treeViewer;

		protected class CategorySorter extends ViewerSorter {
			public int category(Object element) {
				if (element instanceof ContentElement) {
					return ((ContentElement) element).getType();
				}
				return 0;
			}

			public CategorySorter() {
				super();
			}

			public CategorySorter(Collator collator) {
				super(collator);
			}
		}

		public SortAction(TreeViewer viewer, IPreferenceStore store, String preferenceKey) {
			super(JavaScriptUIMessages.getString("JSContentOutlinePage.4"), store, preferenceKey, false); //$NON-NLS-1$
			setImageDescriptor(JSEditorPluginImageHelper.getInstance().getImageDescriptor(JSEditorPluginImages.IMG_OBJ_SORT));
			setToolTipText(getText());
			treeViewer = viewer;
			if (isChecked()) {
				treeViewer.setSorter(new CategorySorter(Collator.getInstance()));
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.texteditor.IUpdate#update()
		 */
		public void update() {
			super.update();
			treeViewer.getControl().setVisible(false);
			Object[] expandedElements = treeViewer.getExpandedElements();
			if (isChecked()) {
				treeViewer.setSorter(new CategorySorter(Collator.getInstance()));
			}
			else {
				treeViewer.setSorter(null);
			}
			treeViewer.setInput(treeViewer.getInput());
			treeViewer.setExpandedElements(expandedElements);
			treeViewer.getControl().setVisible(true);
		}
	}

	/**
	 * Structured source files tend to have large/long tree structures. Add a
	 * collapse action to help with navigation.
	 */
	protected class CollapseTreeAction extends Action {
		private TreeViewer fTreeViewer = null;

		public CollapseTreeAction(TreeViewer viewer) {
			super(JavaScriptUIMessages.getString("JSContentOutlinePage.0"), AS_PUSH_BUTTON); //$NON-NLS-1$
			setImageDescriptor(COLLAPSE_E);
			setDisabledImageDescriptor(COLLAPSE_D);
			setToolTipText(getText());
			fTreeViewer = viewer;
		}

		public void run() {
			super.run();
			fTreeViewer.collapseAll();
		}
	}

	protected class DeleteAction extends Action {
		public DeleteAction() {
			super(JavaScriptUIMessages.getString("JSContentOutlinePage.5")); //$NON-NLS-1$
			setImageDescriptor(DELETE_E);
			setDisabledImageDescriptor(DELETE_D);
			setToolTipText(getText());
		}

		public void run() {
			ISelection selection = getTreeViewer().getSelection();
			if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
				Object firstElement = ((IStructuredSelection) selection).getFirstElement();
				if (firstElement instanceof ContentElement) {
					ContentElement contentElement = (ContentElement) firstElement;
					try {
						fDocument.replace(contentElement.getOffset(), contentElement.getLength(), ""); //$NON-NLS-1$
						fTreeViewer.refresh();
					}
					catch (BadLocationException exception) {
						throw new SourceEditingRuntimeException(exception);
					}
				}
			}
		}
	}

	protected class ShowHierarchyAction extends PropertyChangeUpdateAction {
		public ShowHierarchyAction(String text, IPreferenceStore store, String preferenceKey, boolean defaultValue) {
			super(text, store, preferenceKey, defaultValue);
			setToolTipText(getText());
			setImageDescriptor(JSEditorPluginImageHelper.getInstance().getImageDescriptor(JSEditorPluginImages.IMG_OBJ_HIERARCHY));
		}

		public void update() {
			super.update();
			JSContentElementImpl.setSupportChildren(isChecked());
			Object[] expanded = getTreeViewer().getExpandedElements();
			getTreeViewer().refresh();
			getTreeViewer().setExpandedElements(expanded);
		}
	}

	protected class ShowVariablesAction extends PropertyChangeUpdateAction {
		public ShowVariablesAction(String text, IPreferenceStore store, String preferenceKey, boolean defaultValue) {
			super(text, store, preferenceKey, defaultValue);
			setToolTipText(getText());
			setImageDescriptor(JSEditorPluginImageHelper.getInstance().getImageDescriptor(JSEditorPluginImages.IMG_OBJ_DEFAULT));
		}

		public void update() {
			super.update();
			JSContentElementImpl.setSupportVariables(isChecked());
			Object[] expanded = getTreeViewer().getExpandedElements();
			getTreeViewer().refresh();
			getTreeViewer().setExpandedElements(expanded);
		}
	}

	protected class ToggleLinkAction extends PropertyChangeUpdateAction {
		public ToggleLinkAction(IPreferenceStore store, String preference) {
			super(JavaScriptUIMessages.getString("JSContentOutlinePage.1"), store, preference, true); //$NON-NLS-1$
			setToolTipText(getText());
			setDisabledImageDescriptor(SYNCED_D);
			setImageDescriptor(SYNCED_E);
			update();
		}

		public void update() {
			((SimpleTreeViewer) getTreeViewer()).setLinkWithEditor(isChecked());
		}
	}

	private static final String OUTLINE_LINK_PREF = "outline-link-editor"; //$NON-NLS-1$
	private static final String OUTLINE_SHOW_HIERARCHY_PREF = "outline-show-hierarchy"; //$NON-NLS-1$
	private static final String OUTLINE_SHOW_VARIABLES_PREF = "outline-show-variables"; //$NON-NLS-1$
	private static final String OUTLINE_SORT_PREF = "outline-sort"; //$NON-NLS-1$

	protected ImageDescriptor COLLAPSE_D = SharedEditorPluginImageHelper.getImageDescriptor(SharedEditorPluginImageHelper.IMG_DLCL_COLLAPSEALL);
	protected ImageDescriptor COLLAPSE_E = SharedEditorPluginImageHelper.getImageDescriptor(SharedEditorPluginImageHelper.IMG_ELCL_COLLAPSEALL);
	protected ImageDescriptor DELETE_D = SharedEditorPluginImageHelper.getImageDescriptor(SharedEditorPluginImageHelper.IMG_DLCL_DELETE);
	protected ImageDescriptor DELETE_E = SharedEditorPluginImageHelper.getImageDescriptor(SharedEditorPluginImageHelper.IMG_ELCL_DELETE);
	protected ImageDescriptor SYNCED_D = SharedEditorPluginImageHelper.getImageDescriptor(SharedEditorPluginImageHelper.IMG_DLCL_SYNCED);
	protected ImageDescriptor SYNCED_E = SharedEditorPluginImageHelper.getImageDescriptor(SharedEditorPluginImageHelper.IMG_ELCL_SYNCED);

	protected DeleteAction fDeleteAction = new DeleteAction();
	protected IDocument fDocument = null;
	private PropertyChangeUpdateActionContributionItem fShowHierarchyItem;
	private PropertyChangeUpdateActionContributionItem fShowVariablesItem;
	private PropertyChangeUpdateActionContributionItem fSortItem;

	protected ISourceViewer fSourceViewer = null;
	private PropertyChangeUpdateActionContributionItem fToggleLinkItem;
	protected TreeViewer fTreeViewer = null;
	protected SimpleViewerSelectionManagerImpl fViewerSelectionManager = null;
	private boolean fContextMenuRegistered = false;
	private MenuManager fContextMenu = null;

	public JSContentOutlinePage(IDocument document, ISourceViewer sourceViewer) {
		setDocument(document);
		setSourceViewer(sourceViewer);
	}

	protected void contextMenuAboutToShow(IMenuManager menuManager) {
		menuManager.add(fDeleteAction);
		IContributionItem[] items = menuManager.getItems();
		if (items.length > 0 && items[items.length - 1].getId() != null) {
			menuManager.insertAfter(items[items.length - 1].getId(), new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		}
		else {
			menuManager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		}
	}

	public void createControl(Composite parent) {
		createTreeViewer(parent);

		fTreeViewer.setContentProvider(new JSTreeContentProvider());
		fTreeViewer.setLabelProvider(new JSLabelProvider());
		fTreeViewer.addSelectionChangedListener(getViewerSelectionManager());
		fTreeViewer.addDoubleClickListener(getViewerSelectionManager());
		fTreeViewer.setInput(fDocument);

		if (fDocument != null)
			fDocument.addDocumentListener(this);

		fContextMenu = new MenuManager("#popup"); //$NON-NLS-1$
		fContextMenu.setRemoveAllWhenShown(true);
		fContextMenu.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager menuManager) {
				contextMenuAboutToShow(menuManager);
			}
		});
		Menu menu = fContextMenu.createContextMenu(fTreeViewer.getTree());
		fTreeViewer.getTree().setMenu(menu);
		registerContextMenu();
	}

	protected void createTreeViewer(Composite parent) {
		fTreeViewer = new SimpleTreeViewer(new Tree(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL));
		fTreeViewer.addSelectionChangedListener(this);
		fTreeViewer.setComparer(new ContentElementComparerImpl());
		if (fViewerSelectionManager != null)
			fViewerSelectionManager.setTreeViewer(fTreeViewer);
	}

	public void dispose() {
		super.dispose();

		// remove this from DocumentListener
		if (fDocument != null)
			fDocument.removeDocumentListener(this);

		// disconnect from the ViewerSelectionManager
		if (fViewerSelectionManager != null) {
			fViewerSelectionManager.disconnectCaretListeners();

			if (fTreeViewer != null) {
				fTreeViewer.removeDoubleClickListener(fViewerSelectionManager);
				fTreeViewer.removeSelectionChangedListener(fViewerSelectionManager);
			}
		}

		// disocnnect preference change listening contribution items
		if (fShowHierarchyItem != null)
			fShowHierarchyItem.disconnect();
		if (fSortItem != null)
			fSortItem.disconnect();
		if (fShowVariablesItem != null)
			fShowVariablesItem.disconnect();
		if (fToggleLinkItem != null)
			fToggleLinkItem.disconnect();
	}

	/**
	 * The manipulation described by the document event will be performed.
	 * 
	 * @param event
	 *            the document event describing the document change
	 */
	public void documentAboutToBeChanged(DocumentEvent event) {
		//
	}

	/**
	 * The manipulation described by the document event has been performed.
	 * 
	 * @param event
	 *            the document event describing the document change
	 */
	public void documentChanged(DocumentEvent event) {
		//
	}

	public Control getControl() {
		if (fTreeViewer == null)
			return null;
		return fTreeViewer.getControl();
	}

	public ISelection getSelection() {
		if (fTreeViewer == null)
			return StructuredSelection.EMPTY;
		return fTreeViewer.getSelection();
	}

	protected ISourceViewer getSourceViewer() {
		return fSourceViewer;
	}

	protected TreeViewer getTreeViewer() {
		return fTreeViewer;
	}

	protected ViewerSelectionManager getViewerSelectionManager() {
		if (fViewerSelectionManager == null) {
			fViewerSelectionManager = new SimpleViewerSelectionManagerImpl(getSourceViewer(), fTreeViewer);
		}
		return fViewerSelectionManager;
	}

	void registerContextMenu() {
		if (!fContextMenuRegistered && getTreeViewer() != null && getTreeViewer().getControl() != null) {
			IWorkbenchPage page = getSite().getWorkbenchWindow().getActivePage();
			if (page != null) {
				IEditorPart ownerEditor = page.getActiveEditor();
				if (ownerEditor != null) {
					fContextMenuRegistered = true;
					getSite().registerContextMenu(ownerEditor.getEditorSite().getId() + ".OutlineContext", fContextMenu, this);	//$NON-NLS-1$
				}
			}
		}
	}

	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);

		SortAction sortAction = new SortAction(getTreeViewer(), JSEditorPlugin.getDefault().getPreferenceStore(), OUTLINE_SORT_PREF);
		fSortItem = new PropertyChangeUpdateActionContributionItem(sortAction);

		PropertyChangeUpdateAction action = new ShowHierarchyAction(JavaScriptUIMessages.getString("JSContentOutlinePage.2"), JSEditorPlugin.getInstance().getPreferenceStore(), OUTLINE_SHOW_HIERARCHY_PREF, true); //$NON-NLS-1$
		fShowHierarchyItem = new PropertyChangeUpdateActionContributionItem(action);

		action = new ShowVariablesAction(JavaScriptUIMessages.getString("JSContentOutlinePage.3"), JSEditorPlugin.getInstance().getPreferenceStore(), OUTLINE_SHOW_VARIABLES_PREF, true); //$NON-NLS-1$
		fShowVariablesItem = new PropertyChangeUpdateActionContributionItem(action);

		IAction collapseAction = new CollapseTreeAction(getTreeViewer());
		fToggleLinkItem = new PropertyChangeUpdateActionContributionItem(new ToggleLinkAction(JSEditorPlugin.getInstance().getPreferenceStore(), OUTLINE_LINK_PREF));

		actionBars.getToolBarManager().add(fSortItem);
		actionBars.getToolBarManager().add(collapseAction);

		actionBars.getMenuManager().add(fToggleLinkItem);
		actionBars.getMenuManager().add(fShowHierarchyItem);
		actionBars.getMenuManager().add(fShowVariablesItem);

	}

	public void setDocument(IDocument document) {
		if (document != fDocument) {
			fDocument = document;

			if (fTreeViewer != null) {
				fTreeViewer.setInput(fDocument);
				fTreeViewer.refresh();
			}
		}
	}

	public void setFocus() {
		fTreeViewer.getControl().setFocus();
	}

	public void setSelection(ISelection selection) {
		if (fTreeViewer != null)
			fTreeViewer.setSelection(selection, true);
	}

	protected void setSourceViewer(ISourceViewer sourceViewer) {
		fSourceViewer = sourceViewer;
	}
}

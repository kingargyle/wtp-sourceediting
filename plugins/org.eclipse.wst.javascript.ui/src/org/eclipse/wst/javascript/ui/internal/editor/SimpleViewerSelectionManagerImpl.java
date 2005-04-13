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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wst.javascript.ui.internal.common.ContentElement;
import org.eclipse.wst.sse.core.IStructuredModel;
import org.eclipse.wst.sse.ui.internal.CaretMediator;
import org.eclipse.wst.sse.ui.internal.ViewerSelectionManager;
import org.eclipse.wst.sse.ui.internal.view.events.CaretEvent;
import org.eclipse.wst.sse.ui.internal.view.events.INodeSelectionListener;
import org.eclipse.wst.sse.ui.internal.view.events.ITextSelectionListener;

public class SimpleViewerSelectionManagerImpl implements ViewerSelectionManager {

	ISourceViewer fSourceViewer = null;
	TreeViewer fTreeViewer = null;
	private CaretMediator caretMeditator = null;

	public SimpleViewerSelectionManagerImpl(ISourceViewer sourceViewer, TreeViewer treeViewer) {
		fSourceViewer = sourceViewer;
		fTreeViewer = treeViewer;

		// listen to text selections
		StyledText textWidget = sourceViewer.getTextWidget();
		textWidget.addSelectionListener(this);

		// listen to caret movements	
		caretMeditator = new CaretMediator(textWidget);
		caretMeditator.addCaretListener(this);

		// init TreeViewerSelection and SourceViewerRangeIndication
		int caretPosition = fSourceViewer.getTextWidget().getCaretOffset();
		IStructuredSelection structuredSelection = setTreeViewerSelection(caretPosition);
		setSourceViewerRangeIndication(structuredSelection, false);
	}

	/**
	 * @see IDoubleClickListener#doubleClick(DoubleClickEvent)
	 */
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();

		ContentElement contentElement = (ContentElement) selection.getFirstElement();
		fSourceViewer.setSelectedRange(contentElement.getOffset(), contentElement.getLength());
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();

		if (event instanceof ViewerSelectionManagerSelectionChangedEvent) {
			setSourceViewerRangeIndication(selection, false);

			// selected programatically, don't set selected range

		}
		else {
			setSourceViewerRangeIndication(selection, true);

			// not selected programatically, set selected range also
			setSourceViewerSelectedRange(selection);
		}
	}

	protected void setSourceViewerRangeIndication(IStructuredSelection selection, boolean moveCursor) {
		if (selection.size() == 1) {
			ContentElement contentElement = (ContentElement) selection.getFirstElement();
			fSourceViewer.setRangeIndication(contentElement.getOffset(), contentElement.getLength(), moveCursor);
		}
		else
			fSourceViewer.removeRangeIndication();
	}

	protected void setSourceViewerSelectedRange(IStructuredSelection selection) {
		if (selection.size() == 1) {
			ContentElement contentElement = (ContentElement) selection.getFirstElement();
			fSourceViewer.setSelectedRange(contentElement.getNameOffset(), contentElement.getName().length());
		}
	}

	/**
	 * Sent when selection occurs in the control.
	 * <p>
	 * For example, on some platforms selection occurs in
	 * a List when the user selects an item or items.
	 * </p>
	 *
	 * @param event an event containing information about the selection
	 */
	public void widgetSelected(SelectionEvent event) {
	}

	/**
	 * Sent when default selection occurs in the control.
	 * <p>
	 * For example, on some platforms default selection occurs
	 * in a List when the user double-clicks an item or types
	 * return in a Text.
	 * </p>
	 *
	 * @param event an event containing information about the default selection
	 */
	public void widgetDefaultSelected(SelectionEvent event) {
	}

	public void caretMoved(CaretEvent event) {
		// refresh only if fTreeViewer is available 
		if (fTreeViewer != null) {
			fTreeViewer.refresh();
		}
		int caretPosition = event.getPosition();
		setTreeViewerSelection(caretPosition);
	}

	protected ContentElement getContentElementAt(ContentElement contentElement, int caretPosition) {
		ContentElement result = null;

		Vector children = contentElement.getChildren();
		if (children != null) {
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				ContentElement eachContentElement = (ContentElement) iter.next();
				result = getContentElementAt(eachContentElement, caretPosition);

				if (result != null)
					return result;
			}
		}

		int offset = contentElement.getOffset();
		int length = contentElement.getLength();
		if ((caretPosition >= offset) && (caretPosition <= offset + length))
			result = contentElement;

		return result;
	}

	protected IStructuredSelection setTreeViewerSelection(int caretPosition) {
		StructuredSelection structuredSelection = null;

		if (fTreeViewer != null) {
			Object selection = null;

			Object[] treeItems = fTreeViewer.getTree().getItems();
			for (int i = 0; i < treeItems.length; i++) {
				ContentElement eachContentElement = (ContentElement) ((TreeItem) treeItems[i]).getData();

				selection = getContentElementAt(eachContentElement, caretPosition);
				if (selection != null)
					break;
			}

			if (selection == null)
				structuredSelection = new StructuredSelection();
			else
				structuredSelection = new StructuredSelection(selection);

			fTreeViewer.setSelection(structuredSelection, true);
		}

		return structuredSelection;
	}

	public void disconnectCaretListeners() {
		fTreeViewer = null;
		fSourceViewer.removeRangeIndication();
		caretMeditator.release();

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#addNodeDoubleClickListener(org.eclipse.jface.viewers.IDoubleClickListener)
	 */
	public void addNodeDoubleClickListener(IDoubleClickListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#addNodeSelectionListener(com.ibm.sse.editor.view.events.INodeSelectionListener)
	 */
	public void addNodeSelectionListener(INodeSelectionListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#addTextSelectionListener(com.ibm.sse.editor.view.events.ITextSelectionListener)
	 */
	public void addTextSelectionListener(ITextSelectionListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#getCaretPosition()
	 */
	public int getCaretPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#getSelectedNodes()
	 */
	public List getSelectedNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#release()
	 */
	public void release() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#removeNodeDoubleClickListener(org.eclipse.jface.viewers.IDoubleClickListener)
	 */
	public void removeNodeDoubleClickListener(IDoubleClickListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#removeNodeSelectionListener(com.ibm.sse.editor.view.events.INodeSelectionListener)
	 */
	public void removeNodeSelectionListener(INodeSelectionListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#removeTextSelectionListener(com.ibm.sse.editor.view.events.ITextSelectionListener)
	 */
	public void removeTextSelectionListener(ITextSelectionListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#setModel(com.ibm.sse.model.core.IStructuredModel)
	 */
	public void setModel(IStructuredModel newModel) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.ibm.sse.editor.ViewerSelectionManager#setTextViewer(org.eclipse.jface.text.ITextViewer)
	 */
	public void setTextViewer(ITextViewer newTextViewer) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return Returns the treeViewer.
	 */
	public TreeViewer getTreeViewer() {
		return fTreeViewer;
	}

	/**
	 * @param treeViewer The treeViewer to set.
	 */
	public void setTreeViewer(TreeViewer treeViewer) {
		fTreeViewer = treeViewer;
	}
}

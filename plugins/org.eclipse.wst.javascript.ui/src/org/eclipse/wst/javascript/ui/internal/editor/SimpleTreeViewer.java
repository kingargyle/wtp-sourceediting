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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

public class SimpleTreeViewer extends TreeViewer {

	protected int fCaretPosition;
	private boolean fIsLinkWithEditor = false;

	public SimpleTreeViewer(Composite parent) {
		super(parent);
	}

	public SimpleTreeViewer(Tree tree) {
		super(tree);
	}

	protected void handleSelect(SelectionEvent event) {
		// handle case where an earlier selection listener disposed the control.
		Control control = getControl();
		if (control != null && !control.isDisposed()) {
			super.updateSelection(getSelection());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.StructuredViewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	public void setSelection(ISelection selection, boolean reveal) {
		if (isLinkWithEditor())
			super.setSelection(selection, reveal);
	}

	protected void updateSelection(ISelection selection) {
		SelectionChangedEvent event = new ViewerSelectionManagerSelectionChangedEvent(this, selection);
		fireSelectionChanged(event);
	}

	/**
	 * @return Returns the isLinkWithEditor.
	 */
	public boolean isLinkWithEditor() {
		return fIsLinkWithEditor;
	}

	/**
	 * @param isLinkWithEditor
	 *            The isLinkWithEditor to set.
	 */
	public void setLinkWithEditor(boolean isLinkWithEditor) {
		fIsLinkWithEditor = isLinkWithEditor;
	}
}

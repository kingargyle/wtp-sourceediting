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
package org.eclipse.wst.javascript.ui.views.contentoutline;

import java.util.Vector;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.javascript.common.ui.ContentElement;
import org.eclipse.wst.javascript.ui.ContentElementProvider;


public class JSTreeContentProvider implements ITreeContentProvider {

	private ContentElementProvider fContentElementProvider = new JSContentElementProvider();

	/**
	 * @see IContentProvider#dispose()
	 */
	public void dispose() {
	}

	/**
	 * @see ITreeContentProvider#getChildren(Object parentElement)
	 */
	public Object[] getChildren(Object parentElement) {
		Vector children = ((ContentElement) parentElement).getChildren();
		if (children == null)
			return null;
		else
			return children.toArray();
	}

	/**
	 * @see IStructuredContentProvider#getElements(Object inputElement)
	 */
	public Object[] getElements(Object inputElement) {
		return fContentElementProvider.getContentElements(inputElement).toArray();
	}

	/**
	 * @see ITreeContentProvider#getParent(Object element)
	 */
	public Object getParent(Object element) {
		return ((ContentElement) element).getParent();
	}

	/**
	 * @see ITreeContentProvider#hasChildren(Object element)
	 */
	public boolean hasChildren(Object element) {
		return ((ContentElement) element).hasChildren(element);
	}

	/**
	 * @see IContentProvider#inputChanged(Viewer viewer, Object oldInput, Object newInput)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
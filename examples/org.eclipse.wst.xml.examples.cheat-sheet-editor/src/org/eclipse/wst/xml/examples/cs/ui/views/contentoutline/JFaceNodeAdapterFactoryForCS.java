/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jens Lukowski/Innoopract - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.wst.xml.examples.cs.ui.views.contentoutline;



import org.eclipse.wst.common.contentmodel.modelquery.ModelQuery;
import org.eclipse.wst.sse.core.AdapterFactory;
import org.eclipse.wst.sse.core.INodeAdapter;
import org.eclipse.wst.sse.core.INodeNotifier;
import org.eclipse.wst.sse.core.modelquery.ModelQueryAdapter;
import org.eclipse.wst.sse.ui.views.contentoutline.IJFaceNodeAdapter;
import org.eclipse.wst.xml.ui.views.contentoutline.JFaceNodeAdapterFactory;

/**
 * An adapter factory to create JFaceNodeAdapters.  Use this
 * adapter factory with a JFaceAdapterContentProvider to display
 * DOM nodes in a tree.
 */
public class JFaceNodeAdapterFactoryForCS extends JFaceNodeAdapterFactory {
	protected INodeAdapter singletonAdapter;

	public JFaceNodeAdapterFactoryForCS() {
		this(IJFaceNodeAdapter.class, true);
	}

	public JFaceNodeAdapterFactoryForCS(Object adapterKey, boolean registerAdapters) {
		super(adapterKey, registerAdapters);
	}

	/**
	 * Create a new JFace adapter for the DOM node passed in
	 */
	protected INodeAdapter createAdapter(INodeNotifier node) {
		if (singletonAdapter == null) {
			// create the JFaceNodeAdapter
			singletonAdapter = new JFaceNodeAdapterForCS(this);
			initAdapter(singletonAdapter, node);
		}
		return singletonAdapter;
	}

	protected void initAdapter(INodeAdapter adapter, INodeNotifier node) {
		// register for CMDocumentManager events
		if (((JFaceNodeAdapterForCS) adapter).getCMDocumentManagerListener() != null) {
			ModelQueryAdapter mqadapter = (ModelQueryAdapter) node.getAdapterFor(ModelQueryAdapter.class);
			if (mqadapter != null) {
				ModelQuery mquery = mqadapter.getModelQuery();
				if (mquery != null && mquery.getCMDocumentManager() != null) {
					cmDocumentManager = mquery.getCMDocumentManager();
					cmDocumentManager.addListener(((JFaceNodeAdapterForCS) adapter).getCMDocumentManagerListener());
				}
			}
		}
	}

	public void release() {
		// deregister from CMDocumentManager events
		if (cmDocumentManager != null && singletonAdapter != null && ((JFaceNodeAdapterForCS) singletonAdapter).getCMDocumentManagerListener() != null) {
			cmDocumentManager.removeListener(((JFaceNodeAdapterForCS) singletonAdapter).getCMDocumentManagerListener());
		}
	}


	public AdapterFactory copy() {
		return new JFaceNodeAdapterFactoryForCS(this.adapterKey, this.shouldRegisterAdapter);
	}
}

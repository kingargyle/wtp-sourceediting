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
package org.eclipse.wst.xml.examples.cs.ui.registry;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.IDocumentTypeHandler;
import org.eclipse.wst.sse.core.internal.model.FactoryRegistry;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapterFactory;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.ui.internal.contentoutline.IJFaceNodeAdapter;
import org.eclipse.wst.sse.ui.internal.util.Assert;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.CMDocumentManager;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.ModelQuery;
import org.eclipse.wst.xml.core.internal.modelquery.ModelQueryUtil;
import org.eclipse.wst.xml.examples.cs.core.modelhandler.ModelHandlerForCS;
import org.eclipse.wst.xml.examples.cs.ui.views.contentoutline.JFaceNodeAdapterFactoryForCS;
import org.eclipse.wst.xml.ui.internal.DOMObserver;
import org.eclipse.wst.xml.ui.internal.preferences.XMLUIPreferenceNames;
import org.eclipse.wst.xml.ui.internal.registry.AdapterFactoryProviderForXML;

/**
 * 
 */
public class AdapterFactoryProviderForCS extends AdapterFactoryProviderForXML {

	private final String EDITOR_PLUGIN_ID = "org.eclipse.wst.xml.ui"; // $NON-NLS-1$

	/**
	 * 
	 */
	public AdapterFactoryProviderForCS() {
		super();
	}

	protected void addContentBasedFactories(IStructuredModel structuredModel) {
		FactoryRegistry factoryRegistry = structuredModel.getFactoryRegistry();
		Assert.isNotNull(factoryRegistry, "Program Error: client caller must ensure model has factory registry"); //$NON-NLS-1$
		INodeAdapterFactory factory = null;
		// == this list came from the previous "XML only" list

		factory = factoryRegistry.getFactoryFor(IJFaceNodeAdapter.class);
		if (factory == null) {
			factory = new JFaceNodeAdapterFactoryForCS();
			factoryRegistry.addFactory(factory);
		}

		// cs... added for inferred grammar support
		//
		if (structuredModel != null) {
			ModelQuery modelQuery = ModelQueryUtil.getModelQuery(structuredModel);
			if (modelQuery != null) {
				CMDocumentManager documentManager = modelQuery.getCMDocumentManager();
				if (documentManager != null) {
					IPreferenceStore store = ((AbstractUIPlugin) Platform.getPlugin(EDITOR_PLUGIN_ID)).getPreferenceStore();
					boolean useInferredGrammar = (store != null) ? store.getBoolean(XMLUIPreferenceNames.USE_INFERRED_GRAMMAR) : true;

					documentManager.setPropertyEnabled(CMDocumentManager.PROPERTY_ASYNC_LOAD, true);
					documentManager.setPropertyEnabled(CMDocumentManager.PROPERTY_AUTO_LOAD, false);
					documentManager.setPropertyEnabled(CMDocumentManager.PROPERTY_USE_CACHED_RESOLVED_URI, true);
					DOMObserver domObserver = new DOMObserver(structuredModel);
					domObserver.setGrammarInferenceEnabled(useInferredGrammar);
					domObserver.init();
				}
			}
		}
	}

	/*
	 * @see AdapterFactoryProvider#isFor(ContentTypeDescription)
	 */
	public boolean isFor(IDocumentTypeHandler contentTypeDescription) {
		return (contentTypeDescription instanceof ModelHandlerForCS);
	}

}

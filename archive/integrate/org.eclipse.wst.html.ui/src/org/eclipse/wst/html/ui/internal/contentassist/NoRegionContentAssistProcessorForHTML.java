/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.html.ui.internal.contentassist;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.wst.css.core.text.ICSSPartitions;
import org.eclipse.wst.css.ui.internal.contentassist.CSSContentAssistProcessor;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.ui.StructuredTextViewerConfigurationHTML.externalTypeExtension;
import org.eclipse.wst.html.ui.internal.projection.StructuredTextFoldingProviderHTML;
import org.eclipse.wst.sse.ui.internal.ExtendedConfigurationBuilder;
import org.eclipse.wst.xml.ui.internal.contentassist.NoRegionContentAssistProcessor;

/**
 * 
 * @author pavery
 */
public class NoRegionContentAssistProcessorForHTML extends NoRegionContentAssistProcessor {
	protected void initPartitionToProcessorMap() {
		
		super.initPartitionToProcessorMap();
		IContentAssistProcessor htmlProcessor = new HTMLContentAssistProcessor();
		addPartitionProcessor(IHTMLPartitions.HTML_DEFAULT, htmlProcessor);
		addPartitionProcessor(IHTMLPartitions.HTML_COMMENT, htmlProcessor);

		
		IContentAssistProcessor jsContentAssistProcessor =  (IContentAssistProcessor)ExtendedConfigurationBuilder.getInstance().getConfiguration(externalTypeExtension.CONTENT_ASSIST, IHTMLPartitions.SCRIPT);
		addPartitionProcessor(IHTMLPartitions.SCRIPT, jsContentAssistProcessor);

		IContentAssistProcessor cssContentAssistProcessor = new CSSContentAssistProcessor();
		addPartitionProcessor(ICSSPartitions.STYLE, cssContentAssistProcessor);
	}
}

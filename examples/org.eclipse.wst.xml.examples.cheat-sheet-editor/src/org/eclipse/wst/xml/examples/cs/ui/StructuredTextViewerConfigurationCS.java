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
package org.eclipse.wst.xml.examples.cs.ui;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.wst.sse.core.text.rules.StructuredTextPartitioner;
import org.eclipse.wst.xml.core.text.rules.StructuredTextPartitionerForXML;
import org.eclipse.wst.xml.examples.cs.ui.contentassist.CSContentAssistProcessor;
import org.eclipse.wst.xml.ui.StructuredTextViewerConfigurationXML;
import org.eclipse.wst.xml.ui.contentassist.NoRegionContentAssistProcessor;


public class StructuredTextViewerConfigurationCS extends StructuredTextViewerConfigurationXML {

	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		IContentAssistant ca = super.getContentAssistant(sourceViewer);
		if (ca != null && ca instanceof ContentAssistant) {
			ContentAssistant contentAssistant = (ContentAssistant) ca;
			IContentAssistProcessor xmlContentAssistProcessor = new CSContentAssistProcessor(getEditorPart());
			IContentAssistProcessor noRegionProcessor = new NoRegionContentAssistProcessor();
			addContentAssistProcessor(contentAssistant, xmlContentAssistProcessor, StructuredTextPartitioner.ST_DEFAULT_PARTITION);
			addContentAssistProcessor(contentAssistant, xmlContentAssistProcessor, StructuredTextPartitionerForXML.ST_DEFAULT_XML);
			addContentAssistProcessor(contentAssistant, noRegionProcessor, StructuredTextPartitioner.ST_UNKNOWN_PARTITION);
		}
		return ca;
	}

}

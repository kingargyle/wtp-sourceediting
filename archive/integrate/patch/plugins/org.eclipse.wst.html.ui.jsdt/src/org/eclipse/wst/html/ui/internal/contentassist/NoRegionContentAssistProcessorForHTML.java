package org.eclipse.wst.html.ui.internal.contentassist;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.wst.css.core.text.ICSSPartitions;
import org.eclipse.wst.css.ui.internal.contentassist.CSSContentAssistProcessor;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
//import org.eclipse.wst.javascript.ui.internal.common.contentassist.JavaScriptContentAssistProcessor;
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

		//IContentAssistProcessor jsContentAssistProcessor = new JavaScriptContentAssistProcessor();
		//addPartitionProcessor(IHTMLPartitions.SCRIPT, jsContentAssistProcessor);

		IContentAssistProcessor cssContentAssistProcessor = new CSSContentAssistProcessor();
		addPartitionProcessor(ICSSPartitions.STYLE, cssContentAssistProcessor);
	}
}
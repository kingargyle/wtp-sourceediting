package org.eclipse.wst.html.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.css.core.text.ICSSPartitions;
import org.eclipse.wst.css.ui.internal.contentassist.CSSContentAssistProcessor;
import org.eclipse.wst.css.ui.internal.style.LineStyleProviderForEmbeddedCSS;
import org.eclipse.wst.html.core.internal.HTMLCorePlugin;
import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.html.core.internal.preferences.HTMLCorePreferenceNames;
import org.eclipse.wst.html.core.internal.text.StructuredTextPartitionerForHTML;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.ui.internal.autoedit.AutoEditStrategyForTabs;
import org.eclipse.wst.html.ui.internal.autoedit.StructuredAutoEditStrategyHTML;
import org.eclipse.wst.html.ui.internal.contentassist.HTMLContentAssistProcessor;
import org.eclipse.wst.html.ui.internal.contentassist.NoRegionContentAssistProcessorForHTML;
import org.eclipse.wst.html.ui.internal.hyperlink.XMLHyperlinkDetector;
import org.eclipse.wst.html.ui.internal.style.LineStyleProviderForHTML;
import org.eclipse.wst.html.ui.internal.taginfo.HTMLInformationProvider;
import org.eclipse.wst.html.ui.internal.taginfo.HTMLTagInfoHoverProcessor;
//import org.eclipse.wst.javascript.ui.internal.common.contentassist.JavaScriptContentAssistProcessor;
//import org.eclipse.wst.javascript.ui.internal.common.style.LineStyleProviderForJavaScript;
//import org.eclipse.wst.javascript.ui.internal.common.taginfo.JavaScriptInformationProvider;
//import org.eclipse.wst.javascript.ui.internal.common.taginfo.JavaScriptTagInfoHoverProcessor;
import org.eclipse.wst.sse.core.text.IStructuredPartitions;
import org.eclipse.wst.sse.ui.StructuredTextViewerConfiguration;
import org.eclipse.wst.sse.ui.internal.ExtendedConfigurationBuilder;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.format.StructuredFormattingStrategy;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;
import org.eclipse.wst.sse.ui.internal.taginfo.TextHoverManager;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;
import org.eclipse.wst.xml.core.internal.text.rules.StructuredTextPartitionerForXML;
import org.eclipse.wst.xml.core.text.IXMLPartitions;
import org.eclipse.wst.xml.ui.StructuredTextViewerConfigurationXML;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;
import org.w3c.dom.Node;

/**
 * Configuration for a source viewer which shows HTML content.
 * <p>
 * Clients can subclass and override just those methods which must be specific
 * to their needs.
 * </p>
 * 
 * @see org.eclipse.wst.sse.ui.StructuredTextViewerConfiguration
 * @since 1.0
 */
public class StructuredTextViewerConfigurationHTML extends StructuredTextViewerConfiguration {
	/*
	 * One instance per configuration because not sourceviewer-specific and
	 * it's a String array
	 */
	private String[] fConfiguredContentTypes;
	/*
	 * One instance per configuration
	 */
	private LineStyleProvider fLineStyleProviderForEmbeddedCSS;
	/*
	 * One instance per configuration
	 */
	private LineStyleProvider fLineStyleProviderForHTML;
	/*
	 * One instance per configuration
	 */
	private LineStyleProvider fLineStyleProviderForJavascript;
	/*
	 * One instance per configuration
	 */
	private StructuredTextViewerConfiguration fXMLSourceViewerConfiguration;
	private ILabelProvider fStatusLineLabelProvider;
	/* Extension point identifications for externalalized content providers [Bradley Childs - childsb@us.ibm.com]*/
	private static final class externalTypeExtension{
		public static final String HOVER_ID="texthover";
		public static final String INFORMATIONPROVIDER_ID="informationpresenter";
		public static final String AUTOEDIT_ID="autoeditstrategy";
		public static final String CONTENT_FORMATER="contentformater";
		
	}
	/**
	 * Create new instance of StructuredTextViewerConfigurationHTML
	 */
	public StructuredTextViewerConfigurationHTML() {
		// Must have empty constructor to createExecutableExtension
		super();
	}

	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		List allStrategies = new ArrayList(0);

		IAutoEditStrategy[] superStrategies = super.getAutoEditStrategies(sourceViewer, contentType);
		for (int i = 0; i < superStrategies.length; i++) {
			allStrategies.add(superStrategies[i]);
		}

		if (contentType == IHTMLPartitions.HTML_DEFAULT || contentType == IHTMLPartitions.HTML_DECLARATION) {
			allStrategies.add(new StructuredAutoEditStrategyHTML());
		}else{
			/* Load external autoEditProviders from extension point [Bradley Childs - childsb@us.ibm.com]*/
			
			Object externalAutoEditProvider = ExtendedConfigurationBuilder.getInstance().getConfiguration(externalTypeExtension.AUTOEDIT_ID, contentType);
			if(externalAutoEditProvider!=null)
				allStrategies.add(externalAutoEditProvider);
			
		}

		// be sure this is added last in list, so it has a change to modify
		// previous results.
		// add auto edit strategy that handles when tab key is pressed
		allStrategies.add(new AutoEditStrategyForTabs());

		return (IAutoEditStrategy[]) allStrategies.toArray(new IAutoEditStrategy[allStrategies.size()]);
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		if (fConfiguredContentTypes == null) {
			String[] xmlTypes = StructuredTextPartitionerForXML.getConfiguredContentTypes();
			String[] htmlTypes = StructuredTextPartitionerForHTML.getConfiguredContentTypes();
			fConfiguredContentTypes = new String[2 + xmlTypes.length + htmlTypes.length];

			fConfiguredContentTypes[0] = IStructuredPartitions.DEFAULT_PARTITION;
			fConfiguredContentTypes[1] = IStructuredPartitions.UNKNOWN_PARTITION;

			int index = 0;
			System.arraycopy(xmlTypes, 0, fConfiguredContentTypes, index += 2, xmlTypes.length);
			System.arraycopy(htmlTypes, 0, fConfiguredContentTypes, index += xmlTypes.length, htmlTypes.length);
		}

		return fConfiguredContentTypes;
	}

	protected IContentAssistProcessor[] getContentAssistProcessors(ISourceViewer sourceViewer, String partitionType) {
		IContentAssistProcessor[] processors = null;

		if ((partitionType == IHTMLPartitions.HTML_DEFAULT) || (partitionType == IHTMLPartitions.HTML_COMMENT)) {
			processors = new IContentAssistProcessor[]{new HTMLContentAssistProcessor()};
		}
		/* content assistance now provided by extension point [Bradley Childs - childsb@us.ibm.com]*/	
//		else if (partitionType == IHTMLPartitions.SCRIPT) {
//			processors = new IContentAssistProcessor[]{new JavaScriptContentAssistProcessor()};
//		}
		else if (partitionType == ICSSPartitions.STYLE) {
			processors = new IContentAssistProcessor[]{new CSSContentAssistProcessor()};
		}
		else if (partitionType == IStructuredPartitions.UNKNOWN_PARTITION) {
			processors = new IContentAssistProcessor[]{new NoRegionContentAssistProcessorForHTML()};
		}

		return processors;
	}

	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		final MultiPassContentFormatter formatter = new MultiPassContentFormatter(getConfiguredDocumentPartitioning(sourceViewer), IHTMLPartitions.HTML_DEFAULT);

		formatter.setMasterStrategy(new StructuredFormattingStrategy(new HTMLFormatProcessorImpl()));
		/* Check for any externally supported auto edit strategies from EP. [Bradley Childs - childsb@us.ibm.com]*/
		String[] contentTypes = getConfiguredContentTypes(sourceViewer);
		for(int i = 0;i<contentTypes.length;i++){
			IFormattingStrategy cf = (IFormattingStrategy)ExtendedConfigurationBuilder.getInstance().getConfiguration(externalTypeExtension.CONTENT_FORMATER, contentTypes[i]);
			if(cf!=null) formatter.setSlaveStrategy(cf,contentTypes[i]);
		}

		return formatter;
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (contentType == IHTMLPartitions.HTML_DEFAULT) {
			// use xml's doubleclick strategy
			return getXMLSourceViewerConfiguration().getDoubleClickStrategy(sourceViewer, IXMLPartitions.XML_DEFAULT);
		}
		else
			return super.getDoubleClickStrategy(sourceViewer, contentType);
	}

	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (sourceViewer == null || !fPreferenceStore.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_HYPERLINKS_ENABLED))
			return null;

		List allDetectors = new ArrayList(0);
		allDetectors.add(new XMLHyperlinkDetector());

		IHyperlinkDetector[] superDetectors = super.getHyperlinkDetectors(sourceViewer);
		for (int m = 0; m < superDetectors.length; m++) {
			IHyperlinkDetector detector = superDetectors[m];
			if (!allDetectors.contains(detector)) {
				allDetectors.add(detector);
			}
		}
		return (IHyperlinkDetector[]) allDetectors.toArray(new IHyperlinkDetector[0]);
	}

	public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
		Vector vector = new Vector();

		// prefix[0] is either '\t' or ' ' x tabWidth, depending on preference
		Preferences preferences = HTMLCorePlugin.getDefault().getPluginPreferences();
		int indentationWidth = preferences.getInt(HTMLCorePreferenceNames.INDENTATION_SIZE);
		String indentCharPref = preferences.getString(HTMLCorePreferenceNames.INDENTATION_CHAR);
		boolean useSpaces = HTMLCorePreferenceNames.SPACE.equals(indentCharPref);

		for (int i = 0; i <= indentationWidth; i++) {
			StringBuffer prefix = new StringBuffer();
			boolean appendTab = false;

			if (useSpaces) {
				for (int j = 0; j + i < indentationWidth; j++)
					prefix.append(' ');

				if (i != 0)
					appendTab = true;
			}
			else {
				for (int j = 0; j < i; j++)
					prefix.append(' ');

				if (i != indentationWidth)
					appendTab = true;
			}

			if (appendTab) {
				prefix.append('\t');
				vector.add(prefix.toString());
				// remove the tab so that indentation - tab is also an indent
				// prefix
				prefix.deleteCharAt(prefix.length() - 1);
			}
			vector.add(prefix.toString());
		}

		vector.add(""); //$NON-NLS-1$

		return (String[]) vector.toArray(new String[vector.size()]);
	}

	protected IInformationProvider getInformationProvider(ISourceViewer sourceViewer, String partitionType) {
		IInformationProvider provider = null;
		if (partitionType == IHTMLPartitions.HTML_DEFAULT) {
			// HTML
			provider = new HTMLInformationProvider();
		}else{
			/* IInformationProvider now provided by extension point [Bradley Childs - childsb@us.ibm.com]*/
			Object externalInfoProvider = ExtendedConfigurationBuilder.getInstance().getConfiguration(externalTypeExtension.INFORMATIONPROVIDER_ID, partitionType);
			if(externalInfoProvider!=null)
				provider = (IInformationProvider)externalInfoProvider;
			
		}
//		else if (partitionType == IHTMLPartitions.SCRIPT) {
//			// HTML JavaScript
//			provider = new JavaScriptInformationProvider();
//		}
		return provider;
	}

	public LineStyleProvider[] getLineStyleProviders(ISourceViewer sourceViewer, String partitionType) {
		LineStyleProvider[] providers = null;

		if (partitionType == IHTMLPartitions.HTML_DEFAULT || partitionType == IHTMLPartitions.HTML_COMMENT || partitionType == IHTMLPartitions.HTML_DECLARATION) {
			providers = new LineStyleProvider[]{getLineStyleProviderForHTML()};
		}
		/* Line Styel Provider now provided by extension point defined in SSE [Bradley Childs - childsb@us.ibm.com]*/
//		else if (partitionType == IHTMLPartitions.SCRIPT) {
//			providers = new LineStyleProvider[]{getLineStyleProviderForJavascript()};
//		}
		else if (partitionType == ICSSPartitions.STYLE) {
			providers = new LineStyleProvider[]{getLineStyleProviderForEmbeddedCSS()};
		}

		return providers;
	}

	private LineStyleProvider getLineStyleProviderForEmbeddedCSS() {
		if (fLineStyleProviderForEmbeddedCSS == null) {
			fLineStyleProviderForEmbeddedCSS = new LineStyleProviderForEmbeddedCSS();
		}
		return fLineStyleProviderForEmbeddedCSS;
	}

	private LineStyleProvider getLineStyleProviderForHTML() {
		if (fLineStyleProviderForHTML == null) {
			fLineStyleProviderForHTML = new LineStyleProviderForHTML();
		}
		return fLineStyleProviderForHTML;
	}
	/* No longer needed.  Provided by extension defined in SSE. [Bradley Childs - childsb@us.ibm.com]*/
//	private LineStyleProvider getLineStyleProviderForJavascript() {
//		if (fLineStyleProviderForJavascript == null) {
//			fLineStyleProviderForJavascript = new LineStyleProviderForJavaScript();
//		}
//		return fLineStyleProviderForJavascript;
//	}

	public ILabelProvider getStatusLineLabelProvider(ISourceViewer sourceViewer) {
		if (fStatusLineLabelProvider == null) {
			fStatusLineLabelProvider = new JFaceNodeLabelProvider() {
				public String getText(Object element) {

					if (element == null)
						return null;

					StringBuffer s = new StringBuffer();
					Node node = (Node) element;
					if (node.getNodeType() != Node.DOCUMENT_NODE) {
						while (node != null && node.getNodeType() != Node.DOCUMENT_NODE) {
							s.insert(0, super.getText(node));
							node = node.getParentNode();
							if (node != null)
								s.insert(0, IPath.SEPARATOR);
						}
					}
					return s.toString();
				}

			};
		}
		return fStatusLineLabelProvider;
	}

	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
		ITextHover textHover = null;
		
		// look for appropriate text hover processor to return based on
		// content type and state mask
		TextHoverManager manager = SSEUIPlugin.getDefault().getTextHoverManager();
		TextHoverManager.TextHoverDescriptor[] hoverDescs = manager.getTextHovers();
		int i = 0;
		while (i < hoverDescs.length && textHover == null) {
			if (hoverDescs[i].isEnabled() && EditorUtility.computeStateMask(hoverDescs[i].getModifierString()) == stateMask) {
				String hoverType = hoverDescs[i].getId();
				if (TextHoverManager.COMBINATION_HOVER.equalsIgnoreCase(hoverType)) {
					// check if script or html is needed
//					if (contentType == IHTMLPartitions.SCRIPT) {
//						textHover = manager.createBestMatchHover(new JavaScriptTagInfoHoverProcessor());
//					}
//					else 
					if (contentType == IHTMLPartitions.HTML_DEFAULT) {
						textHover = manager.createBestMatchHover(new HTMLTagInfoHoverProcessor());
					}else{
						/* Check extension for TextHover providers [Bradley Childs - childsb@us.ibm.com]*/
						Object externalHover = ExtendedConfigurationBuilder.getInstance().getConfiguration(externalTypeExtension.HOVER_ID, contentType);
						if(externalHover!=null){
							textHover = manager.createBestMatchHover((ITextHover)externalHover);
						}
					}
				}
				else if (TextHoverManager.DOCUMENTATION_HOVER.equalsIgnoreCase(hoverType))
					// check if script or html is needed
//					if (contentType == IHTMLPartitions.SCRIPT) {
//						textHover = new JavaScriptTagInfoHoverProcessor();
//					}
					if (contentType == IHTMLPartitions.HTML_DEFAULT) {
						textHover = new HTMLTagInfoHoverProcessor();
					}else{
						/* Check extension for TextHover providers [Bradley Childs - childsb@us.ibm.com]*/
						Object externalHover = ExtendedConfigurationBuilder.getInstance().getConfiguration(externalTypeExtension.HOVER_ID, contentType);
						if(externalHover!=null){
							textHover = (ITextHover)externalHover;
						}
					}
			}
			i++;
		}

		// no appropriate text hovers found, try super
		if (textHover == null) {
			textHover = super.getTextHover(sourceViewer, contentType, stateMask);
		}
		return textHover;
	}

	private StructuredTextViewerConfiguration getXMLSourceViewerConfiguration() {
		if (fXMLSourceViewerConfiguration == null) {
			fXMLSourceViewerConfiguration = new StructuredTextViewerConfigurationXML();
		}
		return fXMLSourceViewerConfiguration;
	}
}
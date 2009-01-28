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
package org.eclipse.jst.jsp.ui.tests.performance;



import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jst.jsp.core.text.IJSPPartitions;
import org.eclipse.jst.jsp.ui.internal.style.LineStyleProviderForJSP;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.css.core.text.ICSSPartitions;
import org.eclipse.wst.css.ui.internal.style.LineStyleProviderForEmbeddedCSS;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.ui.internal.style.LineStyleProviderForHTML;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.ui.internal.provisional.style.Highlighter;
import org.eclipse.wst.sse.ui.internal.provisional.style.IHighlighter;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;

/**
 * This class is a performance test for the sed Highlighter class
 */
public class TestHighlighterPerformance extends PerformanceTestCase {



	public TestHighlighterPerformance(String name) {
		super(name);
	}

	// public void testAllFiles() throws IOException {
	// // this just does the whole file line by line
	// doHighlighterTest("bigStyle.jsp"); //$NON-NLS-1$
	// doHighlighterTest("testfiles/company300k.jsp"); //$NON-NLS-1$
	// doHighlighterTest("testfiles/company300k.html"); //$NON-NLS-1$
	// doHighlighterTest("testfiles/company300k.xml"); //$NON-NLS-1$
	// }

	public void testHighlightBigStyle() throws IOException {
		startMeasuring();
		doHighlighterTest("bigStyle.jsp"); //$NON-NLS-1$
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}

	public void testHighlight300kJSP() throws IOException {
		startMeasuring();
		doHighlighterTest("testfiles/company300k.jsp"); //$NON-NLS-1$
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}

	// pa_TODO needs to be moved to HTML peroformance plugin
	// public void testHighlight300kHTML() throws IOException {
	// startMeasuring();
	// doHighlighterTest("testfiles/company300k.html"); //$NON-NLS-1$
	// stopMeasuring();
	// commitMeasurements();
	// assertPerformance();
	// }

	// pa_TODO needs to be moved to XML peroformance plugin
	// public void testHighlight300kXML() throws IOException {
	// startMeasuring();
	// doHighlighterTest("testfiles/company300k.xml"); //$NON-NLS-1$
	// stopMeasuring();
	// commitMeasurements();
	// assertPerformance();
	// }

	protected void doHighlighterTest(String filename) throws IOException {
		doHighligtherTest(filename, 0);
	}

	protected void doHighligtherTest(String filename, int start) throws IOException {
		// create model
		IModelManager modelManager = StructuredModelManager.getModelManager();
		InputStream inStream = getClass().getResourceAsStream(filename);

		// TODO_future: seems silly to create a document here, just to get
		// length,
		// we should refactor that out.
		IStructuredDocument sDoc = modelManager.createStructuredDocumentFor(filename, inStream, null);
		int end = sDoc.getText().length();
		doHighlighterTest(filename, start, end);
	}

	protected void doHighlighterTest(String filename, int start, int end) throws IOException {

		doNewHighlighterTest(filename, start, end);

	}

	/**
	 * exactly the same as doOldHighlighterTest except uses the old
	 * *.structured.style.Highlighter class
	 * 
	 * @param filename
	 * @param start
	 * @param end
	 * @throws IOException
	 */
	protected void doNewHighlighterTest(String filename, int start, int end) throws IOException {
		Runtime rt = Runtime.getRuntime();

		// create model
		IModelManager modelManager = StructuredModelManager.getModelManager();
		InputStream inStream = getClass().getResourceAsStream(filename);
		IStructuredModel sModel = null;
		try {
			sModel = modelManager.getModelForEdit(filename, inStream, null);

			// IStructuredDocumentRegion startNode =
			// sModel.getStructuredDocument().getNodeAtCharacterOffset(start);
			// IStructuredDocumentRegion endNode =
			// sModel.getStructuredDocument().getNodeAtCharacterOffset(end);

			// ==> // ITypedRegion[] partitions =
			// sModel.getStructuredDocument().getDocumentPartitioner().computePartitioning(start,
			// end - start);
			Highlighter fHighlighter = (Highlighter) getAppropriateHighlighter(filename);
			fHighlighter.setDocument(sModel.getStructuredDocument());

			// lineGetStyle, line by line
			try {
				IStructuredDocument structuredDocument = sModel.getStructuredDocument();

				int startLine = structuredDocument.getLineOfOffset(start);
				int i = startLine;
				int lineStart = start;
				int lineLength = end - start;

				long totalTime = 0;
				long totalMemory = 0;
				int totalRanges = 0;


				IRegion region = structuredDocument.getLineInformation(i++);
				StyleRange[] ranges = null;

				// long totalStartTime = System.currentTimeMillis();
				while (i <= structuredDocument.getLineOfOffset(end)) {
					lineStart = region.getOffset();
					lineLength = region.getLength();

					long startMem = rt.totalMemory() - rt.freeMemory();
					long startTime = System.currentTimeMillis();
					ranges = fHighlighter.lineGetStyle(lineStart, lineLength);
					long endTime = System.currentTimeMillis();
					long endMem = rt.totalMemory() - rt.freeMemory();
					long diffTime = endTime - startTime;
					long diffMem = endMem - startMem;
					totalTime += diffTime;
					totalMemory += diffMem;
					totalRanges += ranges.length;

					// System.out.println("++");
					// System.out.println("line number > " + i);
					// System.out.println("from > " + lineStart + " length of
					// > "
					// + lineLength);
					// System.out.println("time > " + diffTime);
					// System.out.println("style ranges > " + ranges.length);
					// System.out.println("");
					region = structuredDocument.getLineInformation(i++);
				}
				// long totalEndTime = System.currentTimeMillis();

				System.out.println(""); //$NON-NLS-1$
				System.out.println("---------------------------------------"); //$NON-NLS-1$
				System.out.println(""); //$NON-NLS-1$
				System.out.println("V51 Highlighter > " + filename); //$NON-NLS-1$
				System.out.println("total lines >			" + (i - startLine)); //$NON-NLS-1$
				System.out.println("total time >			" + totalTime); //$NON-NLS-1$
				System.out.println("total memory >			" + totalMemory); //$NON-NLS-1$
				// System.out.println("accurate total time > " + (totalEndTime
				// -
				// totalStartTime));
				System.out.println("total style ranges  >	" + totalRanges); //$NON-NLS-1$
				System.out.println("ave time/line >			" + ((double) totalTime / (double) (i - startLine))); //$NON-NLS-1$
			}
			catch (BadLocationException ble) {
				System.out.println("no region at that line number..."); //$NON-NLS-1$
			}
		}
		finally {
			if (sModel != null) {
				sModel.releaseFromEdit();
			}

		}
	}

	/**
	 * @param filename
	 * @return
	 */
	private IHighlighter getAppropriateHighlighter(String filename) {
		IHighlighter result = null;
		// if (filename.endsWith(".html")) { //$NON-NLS-1$
		// StructuredTextViewerConfiguration configuration = new
		// StructuredTextViewerConfigurationHTML();
		// result = configuration.getHighlighter(null);
		// } else
		if (filename.endsWith(".jsp")) { //$NON-NLS-1$
			result = getHighlighterJSP();
		}
		// else if (filename.endsWith(".xml")) { //$NON-NLS-1$
		// StructuredTextViewerConfiguration configuration = new
		// StructuredTextViewerConfigurationXML();
		// result = configuration.getHighlighter(null);
		// }
		return result;
	}

	/**
	 * This file should be kept "in snych" with what's in viewer configuration
	 * It was found the "java" part caused loading of plugins which then would
	 * throw exception automatically if workbench UI hadn't been started.
	 */
	private IHighlighter getHighlighterJSP() {
		IHighlighter highlighter = new Highlighter();

		if (highlighter != null) {
			// HTML
			LineStyleProvider htmlLineStyleProvider = new LineStyleProviderForHTML();
			highlighter.addProvider(IHTMLPartitions.HTML_DEFAULT, htmlLineStyleProvider);
			highlighter.addProvider(IHTMLPartitions.HTML_COMMENT, htmlLineStyleProvider);
			highlighter.addProvider(IHTMLPartitions.HTML_DECLARATION, htmlLineStyleProvider);

			// CSS
			LineStyleProvider cssLineStyleProvider = new LineStyleProviderForEmbeddedCSS();
			highlighter.addProvider(ICSSPartitions.STYLE, cssLineStyleProvider);

			// JSP
			LineStyleProvider jspLineStyleProvider = new LineStyleProviderForJSP();
			highlighter.addProvider(IJSPPartitions.JSP_DEFAULT, jspLineStyleProvider);
			highlighter.addProvider(IJSPPartitions.JSP_COMMENT, jspLineStyleProvider);
			highlighter.addProvider(IJSPPartitions.JSP_DIRECTIVE, jspLineStyleProvider);
			highlighter.addProvider(IJSPPartitions.JSP_CONTENT_DELIMITER, jspLineStyleProvider);

		}

		return highlighter;
	}
}

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
package org.eclipse.wst.javascript.common.ui.internal.taginfo;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.wst.javascript.common.ui.internal.Logger;
import org.eclipse.wst.sse.core.text.IStructuredDocument;
import org.eclipse.wst.sse.core.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.text.ITextRegion;
import org.eclipse.wst.sse.ui.StructuredTextViewer;

/**
 * Provides hover help documentation for javascript code
 * @see ITextHoverProcessor
 */
public class JavaScriptTagInfoHoverProcessor implements ITextHover {
	static protected final char chEndBracket = ']';
	static protected final char chEndBrace = ')';
	static protected final char chBeginBracket = '[';
	static protected final char chBeginBrace = '(';
	static protected final char chPeriod = '.';


	protected JavaScriptTagInfoProvider tagInfoProvider = null;

	/**
	 * Constructor for JSHoverHelpProcessor
	 */
	public JavaScriptTagInfoHoverProcessor() {
	}

	/**
	 * Return the flatnode at the current offset
	 */
	protected IStructuredDocumentRegion getStructuredDocumentRegion(StructuredTextViewer viewer, int offset) {
		IStructuredDocumentRegion flatNode = null;

		if ((viewer != null) && (viewer.getDocument() != null))
			flatNode = ((IStructuredDocument) viewer.getDocument()).getRegionAtCharacterOffset(offset);

		return flatNode;
	}

	/**
	 * Returns the region to hover the text over based on the offset.
	 * @param textViewer
	 * @param offset
	 * 
	 * @return IRegion region to hover over if offset is within tag name, attribute
	 * name, or attribute value and if offset is
	 * not over invalid whitespace.  otherwise, returns <code>null</code>
	 * 
	 * @see ITextHover#getHoverRegion(ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		if (textViewer instanceof StructuredTextViewer) {
			StructuredTextViewer viewer = (StructuredTextViewer) textViewer;
			IStructuredDocumentRegion flatNode = getStructuredDocumentRegion(viewer, offset);
			ITextRegion region = null;

			if (flatNode != null) {
				region = flatNode.getRegionAtCharacterOffset(offset);
			}

			// if no structuredDocumentRegion or in region's trailing whitespace, no hover region
			if ((region == null) || (offset > flatNode.getTextEndOffset(region)))
				return null;
		}

		try {
			// check if we are at whitespace before or after line
			IRegion line = textViewer.getDocument().getLineInformationOfOffset(offset);
			if ((offset > (line.getOffset())) && (offset < (line.getOffset() + line.getLength()))) {
				org.eclipse.jface.text.Region codeRegion = getSegmentRegion(textViewer, offset);
				if (codeRegion == null)
					codeRegion = new org.eclipse.jface.text.Region(offset, 0);
				return codeRegion;
			}
		}
		catch (BadLocationException e) {
			Logger.logException(e);
		}
		return null;
	}


	/**
	 * Returns the documentation associated with the hoverRegion passed in
	 * @param ITextViewer viewer
	 * @param IRegion hoverRegion
	 * 
	 * @return String documentation for hoverRegion or null if there is none
	 * @see ITextHover#getHoverInfo(ITextViewer, IRegion)
	 */
	public String getHoverInfo(ITextViewer viewer, IRegion hoverRegion) {
		if ((hoverRegion == null) || (viewer == null))
			return null;

		int documentOffset = hoverRegion.getOffset();
		String displayText = null;

		displayText = computeHoverHelp(viewer, documentOffset);

		return displayText;
	}

	/**
	 * Retrieves documentation to display in the hover help popup.
	 * 
	 * @return String any documentation information to display
	 * <code>null</code> if there is nothing to display.
	 * 
	 */
	public String computeHoverHelp(ITextViewer textViewer, int documentPosition) {

		String result = null;
		IStructuredDocumentRegion scriptNode = null;
		ITextRegion scriptRegion = null;
		org.eclipse.jface.text.Region segmentRegion = null;
		String segment = null;

		if ((textViewer == null) || (textViewer.getDocument() == null))
			return null;

		// get only the region/block that contains the keyword identified by documentPosition
		if (textViewer instanceof StructuredTextViewer) {
			scriptNode = getStructuredDocumentRegion((StructuredTextViewer) textViewer, documentPosition);
			if (scriptNode != null) {
				scriptRegion = scriptNode.getRegionAtCharacterOffset(documentPosition);
			}
		}
		if (scriptRegion == null)
			segmentRegion = getSegmentRegion(textViewer, documentPosition);
		else
			segmentRegion = getSegmentRegion(textViewer, documentPosition, scriptNode.getStartOffset(scriptRegion), scriptNode.getEndOffset(scriptRegion));

		segment = getSegmentString(textViewer, segmentRegion);

		result = getJSTagInfo(segment);
		return result;
	}

	/**
	 * @param ITextViewer textViewer - assumes this is not null
	 * 
	 * @return String the substring in textViewer's doc associated with codeRegion
	 */
	protected String getSegmentString(ITextViewer textViewer, org.eclipse.jface.text.Region codeRegion) {
		String fullPath = null;

		if (codeRegion == null)
			return null;
		try {
			// get the substring from the document associated with the region
			fullPath = textViewer.getDocument().get(codeRegion.getOffset(), codeRegion.getLength());
		}
		catch (org.eclipse.jface.text.BadLocationException ble) {
			return null;
		}

		return fullPath;
	}

	/**
	 * Returns a region containing the segment of code containing docPos.
	 * for example: document.lay*er where * is the docPos, would return a 
	 * region representing layer
	 */
	public org.eclipse.jface.text.Region getSegmentRegion(ITextViewer viewer, int docPos) {
		if (viewer == null)
			return null;

		org.eclipse.jface.text.IDocument doc = viewer.getDocument();
		return getSegmentRegion(viewer, docPos, 0, doc.getLength());
	}

	/**
	 * Returns a region containing the segment of code containing docPos
	 * for example: document.lay*er where * is the docPos, would return a 
	 * region representing layer
	 * @param int blockBegin - the min doc position use in computing
	 * @param int blockEnd - the max doc position to use in computing
	 */
	public org.eclipse.jface.text.Region getSegmentRegion(ITextViewer viewer, int docPos, int blockBegin, int blockEnd) {
		org.eclipse.jface.text.IDocument doc = viewer.getDocument();

		int begin = -1;
		int end = -1;

		// check if we are within our parameters
		if ((docPos < blockBegin) || (docPos > blockEnd) || (blockBegin > blockEnd))
			return null;

		int iPos = docPos;
		char ch;
		try {
			// future_TODO: add a limit so that we don't scan backwards a huge amount of time
			while (iPos >= blockBegin) {
				if (iPos < (docPos - 100)) {
					// don't scan backwards a lot.  If it's long, just give up.
					return null;
				}
				ch = doc.getChar(iPos);
				if (!Character.isLetterOrDigit(ch))
					break;
				--iPos;
			}
			begin = iPos;
			iPos = docPos;

			// future_TODO: add a limit so that we don't scan forwards a huge amount of time
			while (iPos <= blockEnd) {
				if (iPos > (docPos + 100)) {
					// don't scan forwards a lot.  If it's long, just give up.
					return null;
				}
				ch = doc.getChar(iPos);
				if (!Character.isLetterOrDigit(ch))
					break;
				++iPos;
			}
			end = iPos;

		}
		catch (org.eclipse.jface.text.BadLocationException ble) {
			return null;
		}

		if (begin == docPos && end == docPos)
			return new org.eclipse.jface.text.Region(docPos, 0);
		else if (begin == docPos)
			return new org.eclipse.jface.text.Region(begin, end - begin);
		else
			return new org.eclipse.jface.text.Region(begin + 1, end - begin - 1);
	}

	/**
	 * Gets the default JSTagInfo provider adapter
	 */
	protected JavaScriptTagInfoProvider getTagInfoProvider() {
		if (tagInfoProvider == null)
			tagInfoProvider = new JavaScriptTagInfoProvider();

		return tagInfoProvider;
	}

	/**
	 * Return the tag info associated with fullPath
	 */
	protected String getJSTagInfo(String fullPath) {
		String result = null;

		result = getTagInfoProvider().getTagInfo(fullPath);

		return result;
	}

	////	// THE BELOW CODE MAY BE USED TO GET THE FULL PATH, RATHER THAN JUST THE IMMEDIATE SEGMENT OF CODE	
	////	/**
	////	 * parses the file and determines begining position of string that 
	////	 * we have to help complete from documentPosition and before
	////	 * 
	////	 * @param doc IDocument assumes doc != null
	////	 * @param blockBegin int the min doc position we can search
	////	 * @param docPos int the current doc position to start from
	////	 * 
	////	 * @return int position of starting point of string
	////	 */	
	////	protected int getExistingStringPre(IDocument doc, int blockBegin, int docPos) {
	////		int iPos = docPos;
	////		int iPos0 = docPos;
	////		Stack stk = new Stack(); 
	////		
	////		try {
	////			char firstch = doc.getChar(iPos);
	////			// todo: add a limit so that we don't scan backwards a huge amount of time
	////			while (iPos>=blockBegin) {
	////				if (iPos<(docPos-100)) {
	////					// don't scan backwards a lot.  If it's long, just give up.
	////					return -1;
	////				}
	////				char ch = doc.getChar(iPos);
	////				if (Character.isLetterOrDigit(ch) || (ch==chPeriod)) {
	////					//if ((ch=='.') && (iPosLastDot!=-1)) { iPosLastDot = iPos; }
	////					iPos--;
	////				} else if (ch==chEndBracket) {
	////					stk.push(new Character(chEndBracket));
	////					iPos--;
	////				} else if (ch==chBeginBracket) {
	////					if (stk.isEmpty()) {
	////						if (iPos==iPos0) {
	////							// todo: adjust the test above to allow for not just the first char if the first char is a space type character
	////							// todo: In this case we don't know if they want to provide a number of a quoted string or a variable whose value is the index.  We need to decide on the best action.
	////							// for now we'll assume they want to work locally.  We'll offer a quote character or a variable.  If we ever want to offer a number, we'll have to let it parse backwards further
	////							//    because "document["  can be completed with 'links' or a formula, but not a number, but we can't know that a number is not acceptable without scanning back before the [ to recognize what comes before.
	////							break;
	////						} else {
	////							// in this case, unless we started with " or ', we want to be local, but let's not try
	////							//  to support internal quoted string completion for arrays.  In other words, we are local.
	////							break;                      
	////						}
	////					} else {
	////						Character xx = (Character)stk.pop();
	////						if (xx.charValue()!=chEndBracket) return -1; // wrong thing popped
	////						iPos--;
	////					}
	////					//if (poppedout) break;
	////					//iPos--;
	////					//poppedout = true;
	////				} else if (ch==chBeginBrace) {
	////					if (stk.isEmpty()) break;  // we will return whatever is in parenthesis
	////					Character xx = (Character)stk.pop();
	////					if (xx.charValue()!=chEndBrace) return -1; // wrong thing popped
	////					iPos--;
	////				} else if (ch==chEndBrace) {
	////					stk.push( new Character(chEndBrace) );
	////					iPos--;
	////				} else if ((ch==',')||(ch==' ')) {
	////					if (stk.isEmpty()) break;
	////					iPos--;
	////				} else if ((ch=='\'') || (ch=='\"')) {
	////					if (stk.isEmpty()) return -1;  // we don't complete empty strings
	////					// I guess we're just trying to parse past a parameter to an array ref or method
	////					char ch0 = ch;
	////					while (true) {
	////						if (iPos<(docPos-100)) return -1; // long, something probably wrong
	////						if (iPos<=blockBegin) return -1; // something probably wrong
	////						ch = doc.getChar(--iPos);
	////						if (ch==ch0) break;
	////					}
	////					// todo: we can test for \" and \' escaped characters
	////					iPos--;
	////				} else {
	////					break;
	////				}
	////			} // endwhile
	////			iPos++;
	////		} catch (org.eclipse.jface.text.BadLocationException ble) {
	////			return -1;
	////		}
	////		return iPos;
	////	}
	////	/**
	////	 * parses the file and determines ending position of string that 
	////	 * we have to help complete from documentPosition and after
	////	 * 
	////	 * @param doc IDocument assumes doc != null
	////	 * @param blockBegin int the max doc position we can search
	////	 * @param docPos int the current doc position to start from
	////	 * 
	////	 * @return int position of ending point of string
	////	 */		
	////	protected int getExistingStringPost(IDocument doc, int blockEnd, int docPos) {
	////		int iPos = docPos;
	////		int iPos0 = docPos;
	////		Stack stk = new Stack(); 
	////		
	////		try {
	////			char firstch = doc.getChar(iPos);
	////			// todo: add a limit so that we don't scan forward a huge amount of time
	////			while (iPos<=blockEnd) {
	////				if (iPos>(docPos+100)) {
	////					// don't scan foward a lot.  If it's long, just give up.
	////					return -1;
	////				}
	////				char ch = doc.getChar(iPos);
	////				if (Character.isLetterOrDigit(ch) || (ch==chPeriod)) {
	////					//if ((ch=='.') && (iPosLastDot!=-1)) { iPosLastDot = iPos; }
	////					iPos++;
	////				} else if (ch==chBeginBracket) {
	////					stk.push(new Character(chBeginBracket));
	////					iPos++;
	////				} else if (ch==chEndBracket) {
	////					if (stk.isEmpty()) {
	////						if (iPos==iPos0) {
	////							// todo: adjust the test above to allow for not just the first char if the first char is a space type character
	////							// todo: In this case we don't know if they want to provide a number of a quoted string or a variable whose value is the index.  We need to decide on the best action.
	////							// for now we'll assume they want to work locally.  We'll offer a quote character or a variable.  If we ever want to offer a number, we'll have to let it parse backwards further
	////							//    because "document["  can be completed with 'links' or a formula, but not a number, but we can't know that a number is not acceptable without scanning back before the [ to recognize what comes before.
	////							break;
	////						} else {
	////							// in this case, unless we started with " or ', we want to be local, but let's not try
	////							//  to support internal quoted string completion for arrays.  In other words, we are local.
	////							break;                      
	////						}
	////					} else {
	////						Character xx = (Character)stk.pop();
	////						if (xx.charValue()!=chBeginBracket) return -1; // wrong thing popped
	////						iPos++;
	////					}
	////					//if (poppedout) break;
	////					//iPos--;
	////					//poppedout = true;
	////				} else if (ch==chEndBrace) {
	////					if (stk.isEmpty()) break;  // we will return whatever is in parenthesis
	////					Character xx = (Character)stk.pop();
	////					if (xx.charValue()!=chBeginBrace) return -1; // wrong thing popped
	////					iPos++;
	////				} else if (ch==chBeginBrace) {
	////					stk.push( new Character(chBeginBrace) );
	////					iPos++;
	////				} else if ((ch==',')||(ch==' ')) {
	////					if (stk.isEmpty()) break;
	////					iPos++;
	////				} else if ((ch=='\'') || (ch=='\"')) {
	////					if (stk.isEmpty()) return -1;  // we don't complete empty strings
	////					// I guess we're just trying to parse past a parameter to an array ref or method
	////					char ch0 = ch;
	////					while (true) {
	////						if (iPos>(docPos+100)) return -1; // long, something probably wrong
	////						if (iPos>=blockEnd) return -1; // something probably wrong
	////						ch = doc.getChar(++iPos);
	////						if (ch==ch0) break;
	////					}
	////					// todo: we can test for \" and \' escaped characters
	////					iPos++;
	////				} else {
	////					break;
	////				}
	////			} // endwhile
	////			iPos--;
	////		} catch (org.eclipse.jface.text.BadLocationException ble) {
	////			return -1;
	////		}
	////		return iPos;
	////	}	
	////	/**
	////	 * parses the file and grabs the full string that we have to help complete.
	////	 * 
	////	 * @param viewer ITextViewer current viewer - assumes viewer != null
	////	 * @param documentPosition int
	////	 * @param blockBegin int where block text begins
	////	 * @param blockEnd int where block text ends
	////	 * 
	////	 * @return String
	////	 */
	////	public String getExistingString(ITextViewer viewer, int documentPosition, int blockBegin, int blockEnd) {
	////		org.eclipse.jface.text.IDocument doc = viewer.getDocument();
	////		String result = null;
	////		int stringBegin, stringEnd;
	////
	////		// check if we are within our parameters
	////		if ((documentPosition < blockBegin)
	////			|| (documentPosition > blockEnd)
	////			|| (blockBegin > blockEnd))
	////			return null;
	////
	////		try {
	////			// get the beginning position of the javascript
	////			if (documentPosition > blockBegin)
	////				stringBegin = getExistingStringPre(doc, blockBegin, documentPosition - 1);
	////			else
	////				stringBegin = documentPosition;
	////
	////			// get the ending position of the javascript
	////			if (documentPosition < blockEnd)
	////				stringEnd = getExistingStringPost(doc, blockEnd, documentPosition + 1);
	////			else
	////				stringEnd = documentPosition;
	////	
	////			
	////			if ((stringBegin == -1) || (stringEnd == -1))
	////				return null;	// something wrong happenned while getting positions
	////			if (stringEnd > stringBegin)
	////				result = doc.get(stringBegin, stringEnd - stringBegin );
	////			else
	////				result = "";  //$NON-NLS-1$
	////		} catch (org.eclipse.jface.text.BadLocationException ble) {
	////			return null;
	////		}
	////		
	////		if(com.ibm.sed.util.Debug.jsDebugContextAssist) {
	////			System.out.println( "the string to complete is: <<<"+result+">>>" );//$NON-NLS-2$//$NON-NLS-1$
	////			System.out.println( "the string begin is: <"+stringBegin+">"+"the string end is: <"+stringEnd+">" );//$NON-NLS-2$//$NON-NLS-1$ //$NON-NLS-3$ //$NON-NLS-4$
	////		}
	////		return result;
	////	}
	////	/**
	////	 * parses the file and grabs the full string that we have to help complete.
	////	 * only grabs full string from documentPosition and before
	////	 * 
	////	 * @param viewer ITextViewer current viewer
	////	 * @param documentPosition int
	////	 * 
	////	 * @return String
	////	 */
	////	public String getExistingString(ITextViewer viewer, int documentPosition) {
	////		return getExistingString(viewer, documentPosition, 0, documentPosition);
	////	}
}

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
package org.eclipse.wst.xml.examples.cs.ui.openon;



import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.wst.sse.core.util.StringUtils;
import org.eclipse.wst.xml.core.document.XMLAttr;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.ModelQuery;
import org.eclipse.wst.xml.ui.openon.DefaultOpenOnXML;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * This action class retrieves the link/file selected by the cursor and
 * attempts to open the link/file in the default editor or web browser
 */
public class DefaultOpenOnCS extends DefaultOpenOnXML {
	private final String SCHEMA_LOCATION = "schemaLocation"; //$NON-NLS-1$
	private final String NO_NAMESPACE_SCHEMA_LOCATION = "noNamespaceSchemaLocation"; //$NON-NLS-1$
	private final String XMLNS = "xmlns"; //$NON-NLS-1$
	private final String XSI_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema-instance"; //$NON-NLS-1$
	
	/**
	 * Checks to see if the given attribute is openOn-able.  Attribute is openOn-able
	 * if it is a namespace declaration attribute or if the attribute value is of type URI.
	 * 
	 * @param attr cannot be null
	 * @param query ModelQuery associated with the attribute (can be null) 
	 * @return true if this attribute is "openOn-able" false otherwise
	 */
	protected boolean isLinkableAttr(Attr attr, ModelQuery query) {
		if ("href".equals(attr.getNodeName())) {
			return true;
		}
		return super.isLinkableAttr(attr, query);
	}

	/*
	 *  (non-Javadoc)
	 */
	protected IRegion doGetOpenOnRegion(int offset) {
		// find the element for this node
		Node currNode = getCurrentNode(offset);
		if (currNode != null) {
			// handle doc type node
//			if (currNode.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
//				XMLNode docNode = (XMLNode) currNode;
//				return new Region(docNode.getStartOffset(), docNode.getEndOffset());
//			}
//
			Attr linkableAtt = getLinkableAttrFromNode(currNode, offset);
			// found attribute to open on
			if (linkableAtt != null) {
				XMLAttr att = (XMLAttr) linkableAtt;
				// do not include quotes in attribute value region
				int regOffset = att.getValueRegionStartOffset();
				int regLength = att.getValueRegion().getTextLength();
				String attValue = att.getValueRegionText();
				if (StringUtils.isQuoted(attValue)) {
					regOffset = ++regOffset;
					regLength = regLength - 2;
				}
				return new Region(regOffset, regLength);
			}
		}
		return null;
	}

	/*
	 *  (non-Javadoc)
	 */
	protected void doOpenOn(IRegion region) {
		int offset = region.getOffset();
		Node currNode = getCurrentNode(offset);

		if (currNode != null) {
			Attr linkableAtt = getLinkableAttrFromNode(currNode, offset);
			// found attribute to open on
			if (linkableAtt != null) {
				XMLAttr att = (XMLAttr) linkableAtt;
				// do not include quotes in attribute value region
				int regOffset = att.getValueRegionStartOffset();
				int regLength = att.getValueRegion().getTextLength();
				String attValue = att.getValueRegionText();
				if (StringUtils.isQuoted(attValue)) {
					attValue = attValue.substring(1, attValue.length() - 1);
				}
//				openFileInEditor(attValue);
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(attValue));
				openFileInEditor(file);
			}
		}
	}
}

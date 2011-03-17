/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.core.document.level3;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.xml.core.internal.provisional.contenttype.ContentTypeIdForXML;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Compare the behavior of our DOM on level 3 API calls versus that of the JRE
 */
public class CoreSuite extends TestCase {

	public CoreSuite() {
		this("WTP XML DOM Level 3 Core Tests");
	}

	public CoreSuite(String name) {
		super(name);
	}

	private void compareTextContent(String s) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException {
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document1 = documentBuilder.parse(new InputSource(new StringReader(s)));

		IDOMModel xmlModel = (IDOMModel) StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(ContentTypeIdForXML.ContentTypeID_XML);
		xmlModel.getStructuredDocument().set(s);
		Document document2 = xmlModel.getDocument();

		if (document1.getDocumentElement() != null && document2.getDocumentElement() != null) {
			compareTextContent(document1.getDocumentElement(), document2.getDocumentElement());

			NamedNodeMap attrs1 = document1.getDocumentElement().getAttributes();
			NamedNodeMap attrs2 = document2.getDocumentElement().getAttributes();
			assertEquals("different number of attributes for " + document1.getDocumentElement().getNodeName(), attrs1.getLength(), attrs2.getLength());
			int length = attrs1.getLength();
			for (int i = 0; i < length; i++) {
				String attrName = attrs1.item(i).getNodeName();
				compareTextContent(attrs1.getNamedItem(attrName), attrs2.getNamedItem(attrName));
			}
		}
		else {
			compareTextContent(document1, document2);
		}
	}

	private void compareTextContent(Node node1, Node node2) {
		assertEquals("different node types", node1.getNodeType(), node2.getNodeType());
		if (node1.getNodeType() == Node.TEXT_NODE || node1.getNodeType() == Node.CDATA_SECTION_NODE) {
			assertEquals("different 'whole text' content", ((Text) node1).getWholeText(), ((Text) node2).getWholeText());
		}

		assertEquals("different number of children for \"" + node1.getNodeName() + "\"", node1.getChildNodes().getLength(), node2.getChildNodes().getLength());
		Node child1 = node1.getFirstChild();
		Node child2 = node2.getFirstChild();
		while (child1 != null) {
			compareTextContent(child1, child2);
			child1 = child1.getNextSibling();
			child2 = child2.getNextSibling();
		}

		assertEquals("different text content for " + node1.getNodeName(), node1.getTextContent(), node2.getTextContent());
	}

	// simple root Element
	public void testNode_getTextContent001() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root/>");
	}

	// root Element with child Text
	public void testNode_getTextContent002() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root>a</root>");
	}

	// root Element with child Comment
	public void testNode_getTextContent003() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root><!-- commented --></root>");
	}

	// root Element with child CDATA
	public void testNode_getTextContent004() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root><![CDATA[ protected ]]></root>");
	}

	// root Element with child Texts and EntityReference
	public void testNode_getTextContent005() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root> &#34; </root>");
	}

	// root Element with attribute
	public void testNode_getTextContent006() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root a = \"b\"/>");
	}

	// root Element with child Text and attribute
	public void testNode_getTextContent007() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root a = \"b\">  </root>");
	}

	// root Element with child processing instruction and trailing single whitespace Text
	public void testNode_getTextContent008() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root><?preprocessor content?> </root>");
	}

	// root Element with child processing instruction
	public void testNode_getTextContent009() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root><?preprocessor content?></root>");
	}

	// root Element with child processing instruction and attribute
	public void testNode_getTextContent010() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root att1 = \"&#65;\"><?preprocessor content?></root>");
	}

	// root Element with child whitespace Text
	public void testNode_getTextContent011() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root>   \n  </root>");
	}

	// root Element with child processing instruction and leading single whitespace Text
	public void testNode_getTextContent012() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root> <?preprocessor content?></root>");
	}
	
	// root Element with child processing instruction with leading and trailing single whitespace Text
	public void testNode_getTextContent013() throws Throwable {
		compareTextContent("<?xml version=\"1.0\"?><root> <?preprocessor content?> </root>");
	}

	public void testNode_getNamespaceURI_Element() throws Throwable {
		String content = "<?xml version=\"1.0\"?>\n<a><b xmlns=\"http://example\"/></a>";
		String designatedNamespace = "http://example";

		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document1 = documentBuilder.parse(new InputSource(new StringReader(content)));

		document1.getDocumentElement().insertBefore(document1.createElementNS(designatedNamespace, "c"), document1.getDocumentElement().getFirstChild());

		String namespaceURI = document1.getDocumentElement().getFirstChild().getNamespaceURI();
		assertEquals("namespace not preserved on creation", designatedNamespace, namespaceURI);
		namespaceURI = document1.getDocumentElement().removeChild(document1.getDocumentElement().getFirstChild()).getNamespaceURI();
		assertEquals("namespace not preserved on removal", designatedNamespace, namespaceURI);

		IDOMModel xmlModel = (IDOMModel) StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(ContentTypeIdForXML.ContentTypeID_XML);
		xmlModel.getStructuredDocument().set(content);
		Document document2 = xmlModel.getDocument();

		document2.getDocumentElement().insertBefore(document2.createElementNS(designatedNamespace, "c"), document2.getDocumentElement().getFirstChild());

		namespaceURI = document2.getDocumentElement().getFirstChild().getNamespaceURI();
		assertEquals("namespace not preserved after creation", designatedNamespace, namespaceURI);
		namespaceURI = document2.getDocumentElement().removeChild(document2.getDocumentElement().getFirstChild()).getNamespaceURI();
		assertEquals("namespace not preserved after removal", designatedNamespace, namespaceURI);
	}

	public void testNode_getNamespaceURI_AttrNS() throws Throwable {
		String content = "<?xml version=\"1.0\"?>\n<a><b xmlns=\"http://example\"/></a>";
		String designatedNamespace = "http://example";

		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document1 = documentBuilder.parse(new InputSource(new StringReader(content)));

		document1.getDocumentElement().getFirstChild().getAttributes().setNamedItemNS(document1.createAttributeNS(designatedNamespace, "c"));

		String namespaceURI = document1.getDocumentElement().getFirstChild().getAttributes().getNamedItemNS(designatedNamespace, "c").getNamespaceURI();
		assertEquals("namespace not preserved on creation", designatedNamespace, namespaceURI);
		namespaceURI = document1.getDocumentElement().removeChild(document1.getDocumentElement().getFirstChild()).getAttributes().getNamedItemNS(designatedNamespace, "c").getNamespaceURI();
		assertEquals("namespace not preserved after removal", designatedNamespace, namespaceURI);

		IDOMModel xmlModel = (IDOMModel) StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(ContentTypeIdForXML.ContentTypeID_XML);
		xmlModel.getStructuredDocument().set(content);
		Document document2 = xmlModel.getDocument();

		document2.getDocumentElement().getFirstChild().getAttributes().setNamedItemNS(document2.createAttributeNS(designatedNamespace, "c"));

		namespaceURI = document2.getDocumentElement().getFirstChild().getAttributes().getNamedItemNS(designatedNamespace, "c").getNamespaceURI();
		assertEquals("namespace not preserved on creation", designatedNamespace, namespaceURI);
		namespaceURI = document2.getDocumentElement().removeChild(document2.getDocumentElement().getFirstChild()).getAttributes().getNamedItemNS(designatedNamespace, "c").getNamespaceURI();
		assertEquals("namespace not preserved after removal", designatedNamespace, namespaceURI);
	}

	public void testNode_getNamespaceURI_Attr() throws Throwable {
		String content = "<?xml version=\"1.0\"?>\n<a><b xmlns=\"http://example\"/></a>";
		String designatedNamespace = "http://example";

		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document1 = documentBuilder.parse(new InputSource(new StringReader(content)));

		document1.getDocumentElement().getFirstChild().getAttributes().setNamedItemNS(document1.createAttributeNS(designatedNamespace, "c"));

		String namespaceURI = document1.getDocumentElement().getFirstChild().getAttributes().getNamedItem("c").getNamespaceURI();
		assertEquals("namespace not preserved on creation", designatedNamespace, namespaceURI);
		namespaceURI = document1.getDocumentElement().removeChild(document1.getDocumentElement().getFirstChild()).getAttributes().getNamedItem("c").getNamespaceURI();
		assertEquals("namespace not preserved after removal", designatedNamespace, namespaceURI);

		IDOMModel xmlModel = (IDOMModel) StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(ContentTypeIdForXML.ContentTypeID_XML);
		xmlModel.getStructuredDocument().set(content);
		Document document2 = xmlModel.getDocument();

		document2.getDocumentElement().getFirstChild().getAttributes().setNamedItemNS(document2.createAttributeNS(designatedNamespace, "c"));

		namespaceURI = document2.getDocumentElement().getFirstChild().getAttributes().getNamedItem("c").getNamespaceURI();
		assertEquals("namespace not preserved on creation", designatedNamespace, namespaceURI);
		namespaceURI = document2.getDocumentElement().removeChild(document2.getDocumentElement().getFirstChild()).getAttributes().getNamedItem("c").getNamespaceURI();
		assertEquals("namespace not preserved after removal", designatedNamespace, namespaceURI);
	}

}

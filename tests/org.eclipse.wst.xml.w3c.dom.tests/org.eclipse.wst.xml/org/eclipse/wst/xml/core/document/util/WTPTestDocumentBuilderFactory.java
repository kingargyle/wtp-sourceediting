/*******************************************************************************
 * Copyright (c) 2001, 2006 David Carver - STAR  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     David Carver - STAR - initial API and implementation based on JTIdy
 *                           DocumentBuilderFactory since WTP does not include
 *                           a builder factory implementation.   Original copyright
 *                           notice is still included.
 *                           bug 214439
 *******************************************************************************/
/*
 * Copyright (c) 2002-2004 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.eclipse.wst.xml.core.document.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.encoding.EncodingRule;
import org.eclipse.wst.sse.core.internal.provisional.IModelLoader;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.encoding.XMLDocumentLoader;
import org.eclipse.wst.xml.core.internal.modelhandler.ModelHandlerForXML;
import org.eclipse.wst.xml.core.internal.modelhandler.XMLModelLoader;
import org.eclipse.wst.xml.core.internal.provisional.contenttype.ContentTypeIdForXML;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.domts.DOMTestDocumentBuilderFactory;
import org.w3c.domts.DOMTestIncompatibleException;
import org.w3c.domts.DOMTestLoadException;
import org.w3c.domts.DocumentBuilderSetting;
import org.eclipse.wst.sse.core.internal.util.Utilities;

/**
 *   This class implements the generic parser builder
 *   for the WTP DOM (http://www.eclipse.org/webtools) which reads XML
 *   and HTML and currently supports DOM Level 1, parts of Level 2, and Level 3
 */
public class WTPTestDocumentBuilderFactory
    extends DOMTestDocumentBuilderFactory {

  private final DOMImplementation domImpl;

  /**
   * Creates a implementation of DOMTestDocumentBuilderFactory
   * using WTP's StructuredDocument loader and DOMModelImpl implementation.
   * @param settings array of settings, may be null.
   */
  public WTPTestDocumentBuilderFactory(
      DocumentBuilderSetting[] settings) throws DOMTestIncompatibleException {
    super(settings);

    try {

      domImpl = new DOMModelImpl();

    }
    catch (Exception ex) {
      throw new DOMTestIncompatibleException(ex, null);
    }

  }

  public DOMTestDocumentBuilderFactory newInstance(DocumentBuilderSetting[]
      newSettings) throws DOMTestIncompatibleException {
    if (newSettings == null) {
      return this;
    }
    DocumentBuilderSetting[] mergedSettings = mergeSettings(newSettings);
    return new WTPTestDocumentBuilderFactory(mergedSettings);
  }

  public Document load(java.net.URL url) throws DOMTestLoadException {
	IStructuredDocument document = null;
	Document doc = null;
	IDOMModel model = null;
	  
	
	try {

		model = (IDOMModel) StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(ContentTypeIdForXML.ContentTypeID_XML);

	    FileInputStream file = new FileInputStream(url.getPath());
	    InputStream inputStream = Utilities.getMarkSupportedStream(file);
		ModelHandlerForXML xmlModelHandler = new ModelHandlerForXML();
//		IDocumentLoader loader = xmlModelHandler.getDocumentLoader();
//		document = (IStructuredDocument)loader.createNewStructuredDocument("/home/dcarver/test.xml", inputStream, EncodingRule.FORCE_DEFAULT);
		IModelLoader xmlModelLoader = xmlModelHandler.getModelLoader();
		xmlModelLoader.load(inputStream, model, null);

	}
	catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	IDOMModel docModel = (IDOMModel)model;
	doc = (Document)docModel.getDocument();
	
	return doc;
  }

  public DOMImplementation getDOMImplementation() {
    return domImpl;
  }

  public boolean hasFeature(String feature, String version) {
    return domImpl.hasFeature(feature, version);
  }

  public String getContentType() {
    return "text/xml";
  }

  public boolean isCoalescing() {
    return false;
  }

  public boolean isExpandEntityReferences() {
    return false;
  }

  public boolean isIgnoringElementContentWhitespace() {
    return false;
  }

  public boolean isNamespaceAware() {
    return false;
  }

  public boolean isValidating() {
    return false;
  }
}

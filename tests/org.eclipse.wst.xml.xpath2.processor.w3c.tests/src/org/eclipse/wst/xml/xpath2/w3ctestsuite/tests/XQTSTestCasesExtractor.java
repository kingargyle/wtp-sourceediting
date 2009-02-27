/*******************************************************************************
 * Copyright (c) 2009 Jin Mingjan and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jin Mingjan - bug 262765 -  initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.xpath2.w3ctestsuite.tests;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.wst.xml.xpath2.processor.test.XPath20TestPlugin;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XQTSTestCasesExtractor extends DefaultHandler {

	private static final String reportFilePath = "/testcases-xpath2-report.xml";

	private static final String E_TESTCASE = "test-case";
	private static final String E_XQFILE = "xq-file";
	private static final String E_CONTEXTFILES = "context-files";
	private static final String E_INPUTCONTEXT = "input-context";
	private static final String E_EXPECTEDRESULT = "expected-result";
	
	private ArrayList<String> xqFilesList = new ArrayList<String>(8300);
	private HashMap<String, ArrayList<String>> contextFilesMap = new HashMap<String, ArrayList<String>>(8300);
	private HashMap<String, String> resultFilesMap = new HashMap<String, String>(8300);
	
	private String xqFile = "";
	private ArrayList<String> contextFilesList = new ArrayList<String>(3);
	private String resultFile = "";
	
	private String tmpCharacters = "";

	public XQTSTestCasesExtractor() throws IOException, ParserConfigurationException, SAXException {
		
		InputStream rfis =  XPath20TestPlugin.getDefault().getBundle().getEntry(reportFilePath).openStream();
		SAXParserFactory spfactory = SAXParserFactory.newInstance();
		SAXParser parser = spfactory.newSAXParser();
		parser.parse(rfis, this);
	}

	public void startDocument() throws SAXException {
//		System.out.println("Starting...");
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		this.tmpCharacters = "";
	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (qName.equals(E_XQFILE)) {
			this.xqFile = tmpCharacters;						
		}else if (qName.equals(E_INPUTCONTEXT)) {
			this.contextFilesList.add(tmpCharacters);
		}else if (qName.equals(E_EXPECTEDRESULT)) {
			this.resultFile = tmpCharacters;			
		}else if (qName.equals(E_TESTCASE)) {
			this.xqFilesList.add(this.xqFile);
			this.contextFilesMap.put(this.xqFile, this.contextFilesList);
			this.resultFilesMap.put(this.xqFile, this.resultFile);
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.tmpCharacters += new String(ch, start, length);//assume all chars-needed element will not be nested						
	}
	
	public void endDocument() throws SAXException {
//		System.out.println("...End");
	}

	public ArrayList<String> getXqFilesList() {
		return xqFilesList;
	}

	public HashMap<String, ArrayList<String>> getContextFilesMap() {
		return contextFilesMap;
	}

	public HashMap<String, String> getResultFilesMap() {
		return resultFilesMap;
	}

}
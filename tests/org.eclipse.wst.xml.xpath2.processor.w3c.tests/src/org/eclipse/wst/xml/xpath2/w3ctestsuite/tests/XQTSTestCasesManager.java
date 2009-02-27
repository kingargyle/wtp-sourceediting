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

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.XSModel;
import org.eclipse.wst.xml.xpath2.processor.DOMLoader;
import org.eclipse.wst.xml.xpath2.processor.DOMLoaderException;
import org.eclipse.wst.xml.xpath2.processor.DefaultDynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DefaultEvaluator;
import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.Evaluator;
import org.eclipse.wst.xml.xpath2.processor.JFlexCupParser;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.StaticChecker;
import org.eclipse.wst.xml.xpath2.processor.StaticError;
import org.eclipse.wst.xml.xpath2.processor.StaticNameResolver;
import org.eclipse.wst.xml.xpath2.processor.XPathParser;
import org.eclipse.wst.xml.xpath2.processor.XPathParserException;
import org.eclipse.wst.xml.xpath2.processor.XercesLoader;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.function.FnFunctionLibrary;
import org.eclipse.wst.xml.xpath2.processor.function.FunctionLibrary;
import org.eclipse.wst.xml.xpath2.processor.function.XDTCtrLibrary;
import org.eclipse.wst.xml.xpath2.processor.function.XSCtrLibrary;
import org.eclipse.wst.xml.xpath2.processor.test.XPath20TestPlugin;
import org.eclipse.wst.xml.xpath2.processor.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.types.ElementType;
import org.eclipse.wst.xml.xpath2.processor.types.QName;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XQTSTestCasesManager {

	private static ArrayList<String> xqFilesList = new ArrayList<String>();
	private static HashMap<String, ArrayList<String>> contextFilesMap = new HashMap<String, ArrayList<String>>();
	private static HashMap<String, String> resultFilesMap = new HashMap<String, String>();
	
	private static String xpath2Expr = "";
//	private static String expectedResult = "";
	private static HashMap<String, String> namespaceMap = new HashMap<String, String>(3);
	private static HashMap<String, String> inputMap = new HashMap<String, String>(3);
	
	private static final int nCount = 10000;//XXX: for debug, if >8225, then nCount will not be reached
	
	private static final String S_INPUT = "input-context";
	private static final String S_INPUT1 = "input-context1";
	private static final String S_INPUT2 = "input-context2";
//	private static final String S_COMMENT1 = "(:";
	private static final String S_COMMENT2 = ":)";
	private static final String S_DN = "declare namespace";
	private static final String S_DV = "declare variable";
	private static final String REGEX_DN = "declare namespace\\s+(\\w[-_\\w]*)\\s*=\\s*['\"]([^;]*)['\"];";
	
	public static List<Object> prepareTestCaseData() {

		List<Object> rt = new ArrayList<Object>();
		
		//
		try {
			XQTSTestCasesExtractor extractor = new XQTSTestCasesExtractor();
			xqFilesList = extractor.getXqFilesList();
			contextFilesMap = extractor.getContextFilesMap();
			resultFilesMap = extractor.getResultFilesMap();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count = 1;
		for (String xqFile : xqFilesList) {
			String expectedResult = getExpectedResult(xqFile); 
			rt.add(new Object[] { xqFile, expectedResult });
			if (count==nCount) {
				break;
			}else {
				count++;
			}
		}	
		
		return rt;
	}


	private static String getExpectedResult(String xqFile){
		String resultFile = resultFilesMap.get(xqFile);
		//
		if (resultFile.length()<10) {   //<9 enough? like XPST0001
			return resultFile;
		}
		String content="";
		//
		InputStream isrf;
		try {
			isrf = XPath20TestPlugin.getDefault().getBundle().getEntry(resultFile).openStream();
			BufferedReader rfreader = new BufferedReader(new InputStreamReader(isrf));
			//XXX:assume char buffer 2048 is long enough;1024 maybe enough
	        //Exception: Axes085, NodeTest003/04/05,... 
			int bufferLength = 2048;
			if ((resultFile.indexOf("Axes085") != -1)
					|| (resultFile.indexOf("NodeTest003") != -1)
					|| (resultFile.indexOf("NodeTest004") != -1)
					|| (resultFile.indexOf("NodeTest005") != -1)) {
				bufferLength = 40000;
			}else if(resultFile.indexOf("ForExpr013") != -1) {
				bufferLength = 433500;
			}else if(resultFile.indexOf("ForExpr016") != -1
					|| (resultFile.indexOf("ReturnExpr011") != -1)
					|| (resultFile.indexOf("sgml-queries-results-q1") != -1)
					|| (resultFile.indexOf("sgml-queries-results-q2") != -1)) {
				bufferLength = 10240;
			}
			char[] cbuf = new char[bufferLength];
			int nByte = rfreader.read(cbuf);
	        assertTrue(resultFile,nByte<bufferLength);//assert nice buffer length
	        
	        content = new String(cbuf).trim();
	        rfreader.close();
	        isrf.close();
			
		} catch (IOException e) {
//			e.printStackTrace();
			content = "No Expected Result Found!";
		}
		return content;
	}


	private static void analyzeContent(String xqFile) {
		// get the xpath2 expr from xq file, first
		ArrayList<String> contextFiles = contextFilesMap.get(xqFile);
		String resultFiles = resultFilesMap.get(xqFile);
		
		try {
			InputStream isxq =  XPath20TestPlugin.getDefault().getBundle().getEntry(xqFile).openStream();
	        BufferedReader xqreader = new BufferedReader(new InputStreamReader(isxq));
	        char[] cbuf = new char[2048];//
	        int nByte = xqreader.read(cbuf);
	        assertTrue(xqFile,nByte<2048);
	        String content = new String(cbuf).trim();
	        //
			if (content.indexOf(S_INPUT) != -1
					&& content.indexOf(S_INPUT1) == -1
					&& content.indexOf(S_INPUT2) == -1) {
				inputMap.put(S_INPUT, contextFiles.get(0));
			} else if (content.indexOf(S_INPUT1) == -1) {
				inputMap.put(S_INPUT1, contextFiles.get(0));
			} else if (content.indexOf(S_INPUT2) != -1) {
				inputMap.put(S_INPUT2, contextFiles.get(1));
				;// assert to get?
			}
	        //	        
	        if (content.indexOf(S_DN)!=-1) {
	        	setupNamespace(content);
	        }
	        //
	        assertTrue(content.lastIndexOf(S_COMMENT2)!=-1);//assert to get
	        xpath2Expr = content.substring(content.lastIndexOf(S_COMMENT2)+2).trim(); 
	        
	        xqreader.close();
	        isxq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void setupNamespace(String content) {
		Pattern p = Pattern.compile(REGEX_DN);
		Matcher m = p.matcher(content);
		while (m.find()) {
			assertTrue(m.groupCount()==2);//
			namespaceMap.put(m.group(1), m.group(2));
		}
		
	}


	private static String getResultForCurrentXPath2(String xpath2)
			throws XPathParserException, StaticError, DynamicError, IOException, DOMLoaderException {
		//
		int nInput= inputMap.size();
        assertTrue(nInput<3);
		String rt=null;
		Bundle testsBundle = XPath20TestPlugin.getDefault().getBundle();

		DOMLoader domloader = new XercesLoader();
		InputStream is = testsBundle.getEntry(
		"/emptydoc.xml").openStream();
		Document emptydom = domloader.load(is);
		//XXX:schema is dummy now 
		ElementPSVI rootPSVI = (ElementPSVI)emptydom.getDocumentElement();
		XSModel schema = rootPSVI.getSchemaInformation();
		
		DynamicContext dc = new DefaultDynamicContext(schema, emptydom);
		
		Set<String> inputVars = inputMap.keySet();
		for (String inputVar : inputVars) {
			String inputFile = inputMap.get(inputVar);
			is = testsBundle.getEntry("/emptydoc.xml").openStream();
			if (inputFile.indexOf("emptydoc") == -1)
				is = testsBundle.getEntry(inputFile).openStream();
			Document dom = domloader.load(is);
			ElementType e = new ElementType(dom.getDocumentElement(), 0);
			dc.set_variable(new QName(inputVar), e);
		}
		//
		dc.add_namespace("xs", "http://www.w3.org/2001/XMLSchema");
		dc.add_namespace("xdt", "http://www.w3.org/2004/10/xpath-datatypes");
//		dc.add_namespace("fn", "http://www.w3.org/2005/xpath-functions");//XXX:PP FunctionLibrary ns bug 
		dc.add_namespace("fn", "http://www.w3.org/2004/10/xpath-functions");//XXX:PP FunctionLibrary ns bug 
		dc.add_namespace("local", "http://www.w3.org/2005/xquery-local-functions");
		// XXX:setup namespaces
		Set<String> keys = namespaceMap.keySet();
		for (String key : keys) {
			dc.add_namespace(key, namespaceMap.get(key));
		}
		//
		dc.add_function_library(new FnFunctionLibrary());
		dc.add_function_library(new XSCtrLibrary());
		dc.add_function_library(new XDTCtrLibrary());

		Evaluator eval = new DefaultEvaluator(dc, emptydom);
		ResultSequence rs = eval.evaluate(compileXPath(dc, xpath2));
		// ElementType result = (ElementType) rs.string();
		// String resultValue = result.node_value().getTextContent();\

		//is.close();//XXX:possible resource leaking?
			
		return getXQTSCompatibleString(rs);
	}
	
	
	private static XPath compileXPath(DynamicContext dc, String xpath)
			throws XPathParserException, StaticError {
		XPathParser xpp = new JFlexCupParser();
		XPath path = xpp.parse(xpath);

		StaticChecker name_check = new StaticNameResolver(dc);
		name_check.check(path);
		return path;
	}


	public static String getProcessorResult(String testedXqFile) {
		analyzeContent(testedXqFile);
		String resultValue;
		try {
			resultValue = getResultForCurrentXPath2(xpath2Expr);			
		} catch (XPathParserException e) {
//			e.printStackTrace();
			//XXX: workaround: XPathParserException->XPST0003?
			//     not 100% reliable now see more at
			//     http://www.w3.org/Bugs/Public/show_bug.cgi?id=6581
			resultValue = "XPST0003";
		} catch (StaticError e) {
//			e.printStackTrace();
			resultValue = "StaticError";
		} catch (DynamicError e) {
//			e.printStackTrace();
			resultValue = "DynamicError";
		} catch (DOMLoaderException e) {
//			e.printStackTrace();
			resultValue = "DOMLoaderException";
		} catch (IOException e) {
//			e.printStackTrace();
			resultValue = "IOException";
		}
		clearContent();
		return resultValue;
	}


	private static void clearContent() {
		xpath2Expr = "";
		namespaceMap = new HashMap<String, String>(3);
		inputMap = new HashMap<String, String>(3);
	}

	/**
	 * This Method try to convert the Psyschopath ResultSequence to the result
	 * used in the w3c xquery test suit, and may be not completed. 
	 * 
	 */
	private static String getXQTSCompatibleString(ResultSequence rs) {
		String result = "";//?

		for (Iterator i = rs.iterator(); i.hasNext();) {
			AnyType elem = (AnyType) i.next();
			String value = elem.string_value();
			//XXX: it is could be use the Numberformat in some number
			//     results test in future. now the Psyschopath use java
			//     double to store decimal literal. 
			
			result = result+ " " +value;
		}
		
		return result.trim();
	}
	
}

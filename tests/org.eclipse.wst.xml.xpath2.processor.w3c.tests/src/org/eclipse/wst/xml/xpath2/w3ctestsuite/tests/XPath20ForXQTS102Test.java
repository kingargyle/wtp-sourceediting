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

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.StaticError;
import org.eclipse.wst.xml.xpath2.processor.XPathParserException;
import org.eclipse.wst.xml.xpath2.processor.test.XPath20TestPlugin;
import org.eclipse.wst.xml.xpath2.processor.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.types.NodeType;
import org.eclipse.wst.xml.xpath2.processor.types.QName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class) 
public class XPath20ForXQTS102Test {
	
	private String testedXqFile;
	private String expectedString;
	
	public XPath20ForXQTS102Test(String xqFile, String es) {
		this.testedXqFile = xqFile;
		this.expectedString = es;		
	}

	@Parameters  
    public static Collection prepareData(){
		Collection rt = null;
        try {
			rt = XQTSTestCasesManager.prepareTestCaseData();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
    }
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
				
	}
	
	@AfterClass
	public static void clearBeforeClass() throws Exception {
		
	}
	
	@Test
	public void testPsychoPathProcessor() throws XPathParserException, StaticError, DynamicError, IOException {
		
		String processorString = XQTSTestCasesManager.getProcessorResult(testedXqFile);
		assertEquals("Error@"+getFullOSPath(testedXqFile)+": ", expectedString, processorString);
	}

	private String getFullOSPath(String xqFile) throws IOException {
		String ps = FileLocator.getBundleFile(
				XPath20TestPlugin.getDefault().getBundle()).getAbsolutePath();
		return Path.fromOSString(ps + xqFile).toOSString();
	}

	
	


}

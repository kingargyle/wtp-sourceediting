/*******************************************************************************
 * Copyright (c) 2001, 2006 David Carver - STAR  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     David Carver - STAR - initial API and implementation based on JTIdyTest
 *                           Original copyright notice is still included.
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


package org.eclipse.wst.xml.core.document.level1;

import junit.framework.TestSuite;

import org.eclipse.wst.xml.core.document.util.WTPTestDocumentBuilderFactory;
import org.w3c.domts.DOMTestDocumentBuilderFactory;
import org.w3c.domts.DOMTestSuite;
import org.w3c.domts.DocumentBuilderSetting;
import org.w3c.domts.JUnitTestSuiteAdapter;
import org.w3c.domts.level1.core.alltests;

public class TestWTP extends TestSuite {

  public static TestSuite suite() throws Exception
  {

    DOMTestDocumentBuilderFactory factory =
        new WTPTestDocumentBuilderFactory(new DocumentBuilderSetting[0]);

    Object test = new alltests(factory);
    
    return new JUnitTestSuiteAdapter((DOMTestSuite) test);
  }

}


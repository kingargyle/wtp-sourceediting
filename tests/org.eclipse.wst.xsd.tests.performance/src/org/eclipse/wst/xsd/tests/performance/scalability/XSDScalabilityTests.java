/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xsd.tests.performance.scalability;

import junit.framework.Test;
import junit.framework.TestSuite;

public class XSDScalabilityTests
{

  public static Test suite()
  {
    TestSuite suite = new TestSuite("Test for org.eclipse.wst.xsd.tests.performance.scalability");
    //$JUnit-BEGIN$
    suite.addTestSuite(Open500KBFileTestCase.class);
    suite.addTestSuite(Open1MBFileTestCase.class);
    suite.addTestSuite(Open10KBFileTestCase.class);
    suite.addTestSuite(Open1KBFileTestCase.class);
    suite.addTestSuite(RepeatValidatex25TestCase.class);
    suite.addTestSuite(Validate10XSDsCrossDependsTestCase.class);
//    suite.addTestSuite(Validate1000XSDsCrossDependsTestCase.class);
    suite.addTestSuite(Validate1MBFileTestCase.class);
    suite.addTestSuite(Validate100XSDsCrossDependsTestCase.class);
    suite.addTestSuite(Validate10KBFileTestCase.class);
    suite.addTestSuite(Validate1KBFileTestCase.class);
    suite.addTestSuite(Validate100KBFileTestCase.class);
    suite.addTestSuite(Validate500KBFileTestCase.class);
    suite.addTestSuite(RepeatValidatex1TestCase.class);
    suite.addTestSuite(RepeatValidatex100TestCase.class);
    suite.addTestSuite(RepeatValidatex10TestCase.class);
    suite.addTestSuite(RepeatValidatex50TestCase.class);
    suite.addTestSuite(RepeatOpenEditorx75TestCase.class);
    suite.addTestSuite(Open100KBFileTestCase.class);
    suite.addTestSuite(Open200KBFileTestCase.class);
    suite.addTestSuite(Validate200KBFileTestCase.class);
    suite.addTestSuite(Validate100XSDsNoDependsTestCase.class);
    suite.addTestSuite(Validate10XSDsNoDependsTestCase.class);
//    suite.addTestSuite(Validate1000XSDsNoDependsTestCase.class);
    suite.addTestSuite(RepeatValidatex75TestCase.class);
    suite.addTestSuite(RepeatOpenEditorx10TestCase.class);
    suite.addTestSuite(RepeatOpenEditorx50TestCase.class);
    suite.addTestSuite(RepeatOpenEditorx100TestCase.class);
    suite.addTestSuite(RepeatOpenEditorx1TestCase.class);
    suite.addTestSuite(RepeatOpenEditorx25TestCase.class);
    //$JUnit-END$
    return suite;
  }

}

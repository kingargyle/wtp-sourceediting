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

package org.eclipse.wst.xsd.tests.performance;

import org.eclipse.wst.xsd.tests.performance.scalability.XSDScalabilityTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{

  public static Test suite()
  {
    TestSuite suite = new TestSuite("Test for org.eclipse.wst.xsd.tests.performance");
    //$JUnit-BEGIN$
// The commented out test cannot be run in the workspace without some extra setup.
// They are however running on the test machine because they are invoked through test.xml
//    suite.addTestSuite(ValidateOAGISXSDTestcase.class);
//    suite.addTestSuite(ReadOAGISXSDTestcase.class);
//    suite.addTestSuite(OpenEditorOAGISXSDTestcase.class);
    suite.addTest(XSDScalabilityTests.suite());
    //$JUnit-END$
    return suite;
  }

}

/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.core.resources.IProject;

public class Validate1000XSDsNoDependsTestCase extends RunXSDValidatorTestCase {

	protected void setUp() throws Exception
	{
	    IProject project = createProject("sp");
	    String bundleId = getBundleId();
	    for(int i = 1; i < 1000; i++)
	    {
	      copyFile(bundleId, "data/1000XSDsNoDepends/sample" + i +".xsd", project);
	    }
	    super.setUp();
	}

	protected String getFilePath()
	{
	    return "data/1000XSDsNoDepends/sample0.xsd";
	}
	
	public void testValidate1000XSDsNoDepends()
	{
	    try
	    {
	      super.execute();
	    }
	    catch (Exception t)
	    {
	      fail(t.getMessage());
	    }
	}

}

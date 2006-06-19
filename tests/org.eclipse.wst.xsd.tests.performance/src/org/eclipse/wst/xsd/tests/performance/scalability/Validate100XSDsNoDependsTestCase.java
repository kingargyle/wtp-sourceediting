/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
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

public class Validate100XSDsNoDependsTestCase extends RunXSDValidatorTestCase {

	protected void setUp() throws Exception
	{
	    IProject project = createProject("sp");
	    String bundleId = getBundleId();
	    for(int i = 1; i < 100; i++)
	    {
	      copyFile(bundleId, "data/100XSDsNoDepends/sample" + i +".xsd", project);
	    }
	    super.setUp();
	}

	protected String getFilePath()
	{
	    return "data/100XSDsNoDepends/sample0.xsd";
	}
	
	public void testValidate100XSDsNoDepends()
	{
	    try
	    {
	      super.execute();
	    }
	    catch (Throwable t)
	    {
	      fail(t.getMessage());
	    }
	}

}

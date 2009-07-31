/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wtp.xerces.test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

//import junit.framework.TestCase;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class TestXMLContentType implements IApplication { // extends TestCase
	// {
	/**
	 * Constructor for TestXMLContentType.
	 * 
	 * @param name
	 */
// public TestXMLContentType(String name) {
// super(name);
// }

	public TestXMLContentType() {
		super();
	}

	public static void main(java.lang.String[] args) {
		try {
			new TestXMLContentType().testModel();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testModel() throws IOException {
		Reader testReader = new StringReader("testString");
		IContentDescription description = Platform.getContentTypeManager().getDescriptionFor(testReader, "test.xml", new QualifiedName[0]);
		testReader.close();
		System.out.println("test content type found: " + description.getContentType().getId());
	}


	public Object start(IApplicationContext context) throws Exception {

		testModel();

		return IApplication.EXIT_OK;
	}

	public void stop() {
		// no need to do any thing

	}
}

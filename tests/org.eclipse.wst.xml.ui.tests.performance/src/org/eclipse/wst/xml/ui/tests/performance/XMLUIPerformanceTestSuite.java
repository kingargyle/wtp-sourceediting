/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author pavery
 */
public class XMLUIPerformanceTestSuite extends TestSuite {
	public XMLUIPerformanceTestSuite() {
		super("XML UI Performance Tests");

		addTest(new TestSuite(BasicPluginTests.class));
		addTest(new TestSuite(OpenEditorTest.class));
		addTest(new TestSuite(FormatTest.class));
		addTest(new TestSuite(FormatTestWithReconcilerOnly.class));
		addTest(new TestSuite(ScrollTest.class));
		addTest(new TestSuite(OpenEditorWithFoldingTest.class));
	}

	public static Test suite() {
		return new XMLUIPerformanceTestSuite();
	}
}

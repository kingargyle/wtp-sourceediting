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
	}
	
	public static Test suite() {
		return new XMLUIPerformanceTestSuite();
	}
}

package org.eclipse.wst.css.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author pavery
 */
public class CSSUIPerformanceTestSuite extends TestSuite {
	
	public CSSUIPerformanceTestSuite() {
		addTest(new TestSuite(BasicPluginTests.class));
	}
	
	public static Test suite() {
		return new CSSUIPerformanceTestSuite();
	}
}

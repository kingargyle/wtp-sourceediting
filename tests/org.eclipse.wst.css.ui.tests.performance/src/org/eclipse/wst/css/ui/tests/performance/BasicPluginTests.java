package org.eclipse.wst.css.ui.tests.performance;

import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.css.ui.internal.CSSUIPlugin;

/**
 * @author pavery
 */
public class BasicPluginTests extends PerformanceTestCase {

	public void testInitializePlugin() {
		// just a dummy first test
		startMeasuring();
		stopMeasuring();
		CSSUIPlugin.getDefault();
		commitMeasurements();
		assertPerformance();
	}
}

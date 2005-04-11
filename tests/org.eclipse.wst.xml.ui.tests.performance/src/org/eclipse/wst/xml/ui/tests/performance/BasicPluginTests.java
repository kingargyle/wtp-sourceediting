package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.xml.ui.internal.XMLUIPlugin;

/**
 * @author pavery
 */
public class BasicPluginTests extends PerformanceTestCase {

	public void testInitializePlugin() {
		// just a dummy first test
		startMeasuring();
		stopMeasuring();
		XMLUIPlugin.getDefault();
		commitMeasurements();
		assertPerformance();
	}
}

package org.eclipse.wst.xml.ui.tests.performance;

import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.xml.ui.XMLEditorPlugin;

/**
 * @author pavery
 */
public class BasicPluginTests extends PerformanceTestCase {
	
	public void testInitializePlugin() {
		// just a dummy first test
		startMeasuring();
		stopMeasuring();
		XMLEditorPlugin.getDefault();
		commitMeasurements();
		assertPerformance();
	}
}

/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.wtp.xerces.test;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Test Bundle
 */

public class WTPTestXercesPlugin implements BundleActivator {

	private BundleContext bundleContext;

	// ********** singleton **********

	private static WTPTestXercesPlugin INSTANCE;

	/**
	 * Return this singleton
	 */
	public static WTPTestXercesPlugin instance() {
		return INSTANCE;
	}


	// ********** public static methods **********


	public WTPTestXercesPlugin() {
		super();
	}


	public Object getFactoryWithDirectInstantiation(boolean sax) {
		return sax ? SAXParserFactory.newInstance() : DocumentBuilderFactory.newInstance();
	}

	public Object getFactoryWithOSGiService(boolean sax) {
		String serviceClazz = sax ? SAXParserFactory.class.getName() : DocumentBuilderFactory.class.getName();
		ServiceReference ref = bundleContext.getServiceReference(serviceClazz);
		try {
			return ref == null ? null : bundleContext.getService(ref);
		} finally {
			if (ref != null)
				bundleContext.ungetService(ref);
		}
	}

	public Object getFactoryWithThreadContextClassloader(boolean sax) {
		/*
		 * set context class loader while factories instantiate classes (for
		 * minimal time) so proper classes, and classloaders, are used in IBM
		 * JRE, with Xerces on the classpath. See bug 283721.
		 * (https://bugs.eclipse.org/bugs/show_bug.cgi?id=283721)
		 */

		final ClassLoader savedClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			return getFactoryWithOSGiService(sax);
		}
		finally {
			Thread.currentThread().setContextClassLoader(savedClassLoader);
		}
	}


	public void start(BundleContext context) throws Exception {
		bundleContext = context;
		INSTANCE = this;
	}


	public void stop(BundleContext context) throws Exception {
		// nothing
	}
}

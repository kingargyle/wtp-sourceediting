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

import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Test Plugin, originally from JPT.
 * 
 */

public class WTPTestXercesPlugin extends Plugin {



	private ServiceTracker parserTracker;

	// ********** singleton **********

	private static WTPTestXercesPlugin INSTANCE;

	/**
	 * Return the singleton JPT plug-in.
	 */
	public static WTPTestXercesPlugin instance() {
		return INSTANCE;
	}


	// ********** public static methods **********


	/**
	 * Log the specified status.
	 */
	public static void log(IStatus status) {
		INSTANCE.getLog().log(status);
	}


	// ********** plug-in implementation **********

	public WTPTestXercesPlugin() {
		super();
	}


	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			if (parserTracker != null) {
				parserTracker.close();
				parserTracker = null;
			}
		}
		finally {
			super.stop(context);
		}
	}

	public SAXParserFactory getFactoryWithOSGiService() {
		SAXParserFactory theFactory = null;
		if (parserTracker == null) {
			parserTracker = new ServiceTracker(getBundle().getBundleContext(), "javax.xml.parsers.SAXParserFactory", null);

			parserTracker.open(true);
		}
		theFactory = (SAXParserFactory) parserTracker.getService();
		return theFactory;
	}


	public SAXParserFactory getFactoryWithDirectInstantiation() {
		SAXParserFactory theFactory = null;

		theFactory = SAXParserFactory.newInstance();

		return theFactory;
	}


	public SAXParserFactory getFactoryWithThreadContextClassloader() {
		SAXParserFactory theFactory = null;
		/*
		 * set context class loader while factories instantiate classes (for
		 * minimal time) so proper classes, and classloaders, are used in IBM
		 * JRE, with Xerces on the classpath. See bug 283721.
		 * (https://bugs.eclipse.org/bugs/show_bug.cgi?id=283721)
		 */

		ClassLoader savedClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			if (parserTracker == null) {
				parserTracker = new ServiceTracker(getBundle().getBundleContext(), "javax.xml.parsers.SAXParserFactory", null);

				parserTracker.open(true);
			}
			theFactory = (SAXParserFactory) parserTracker.getService();
		}
		finally {
			Thread.currentThread().setContextClassLoader(savedClassLoader);
		}


		return theFactory;
	}
}

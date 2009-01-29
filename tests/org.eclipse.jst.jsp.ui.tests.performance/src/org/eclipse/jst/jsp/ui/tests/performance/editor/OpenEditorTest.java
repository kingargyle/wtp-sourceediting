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
package org.eclipse.jst.jsp.ui.tests.performance.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;

public class OpenEditorTest extends PerformanceTestCase {
	private final String PLUGIN_ID = "org.eclipse.jst.jsp.ui.tests.performance";
	
	// relative to the plugin base directory in dev workspace
	// (where build.xml, plugin.xml, etc.. are)
	private final String TEST_FILES_DIRECTORY = "testfiles";
	private final String ID_JSP_EDITOR = "org.eclipse.jst.jsp.core.jspsource.source";
	private final int ITERATIONS = 500;

	public void testReadFiles() throws MalformedURLException, CoreException, FileNotFoundException {

		File dir = new File(getTestFilesDirectory());//new File("D:/eclipsedev/eclipse-M7/eclipse/workspace/org.eclipse.jst.jsp.tests.encoding/testfiles/jsp");
		if (dir.exists() && dir.isDirectory()) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("sp");
			project.create(null);
			project.open(null);
			copy(dir, project);
			joinBackgroundJobs();
			final List files = new ArrayList();
			project.accept(new IResourceProxyVisitor() {
				public boolean visit(IResourceProxy proxy) throws CoreException {
					if (proxy.getName().endsWith(".jsp")) {
						files.add(proxy.requestResource());
					}
					return true;
				}
			}, IContainer.INCLUDE_PHANTOMS);
			tagAsSummary("Open JSP Editor", new Dimension[]{Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
			startMeasuring();
			IFile file = (IFile) files.get(0);
			for (int i = 0; i < ITERATIONS; i++) {
				openEditor(file);
				closeAllEditors();
				//System.out.println("run #:" + i);
			}
			stopMeasuring();
			commitMeasurements();
			// assert heap usage
			assertPerformance();
		}
		else
			fail(dir.toString());
	}

	public String getTestFilesDirectory() {

		Bundle bun = Platform.getBundle(PLUGIN_ID);
		URL pluginURL = null;
		try {
			pluginURL = FileLocator.toFileURL(bun.getEntry("/"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return pluginURL != null ? pluginURL.getPath() + "/" + TEST_FILES_DIRECTORY : "";
	}

	private void copy(File src, IContainer dest) throws CoreException, FileNotFoundException {
		File[] children = src.listFiles();
		for (int i = 0; i < children.length; i++) {
			String name = children[i].getName();
			if (children[i].isDirectory()) {
				IFolder folder = dest.getFolder(new Path(name));
				folder.create(true, true, null);
				copy(children[i], folder);
			}
			else if (name.endsWith(".jsp")) {
				IFile file = dest.getFile(new Path(name));
				file.create(new FileInputStream(children[i]), true, null);
			}
		}
	}

	private void joinBackgroundJobs() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				}
				catch (InterruptedException e) {
					// 
				}
				long start = System.currentTimeMillis();
				Display display = Display.getDefault();
				while (System.currentTimeMillis() - start < 5000) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
	}

	private void esc() {
		Event e = new Event();
		e.type = SWT.KeyDown;
		e.character = SWT.ESC;
		Display.getDefault().post(e);
		readAndDispatch();
	}

	private void readAndDispatch() {
		final Display display = Display.getDefault();
		display.syncExec(new Runnable() {
			public void run() {
				while (display.readAndDispatch()) {
					//
				}
			}
		});
	}

	private void openEditor(IFile file) throws PartInitException {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		IEditorPart editor = page.openEditor(new FileEditorInput(file), ID_JSP_EDITOR, true);
		page.activate(editor);

		esc();
	}

	private void closeAllEditors() throws PartInitException {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		workbenchWindow.getActivePage().closeAllEditors(false);
		
		esc();
	}
}
/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xsd.tests.performance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.Display;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.xsd.util.XSDResourceImpl;

public class ReadOAGISXSDTestcase extends PerformanceTestCase
{

  public void testReadXSD() throws MalformedURLException, CoreException, FileNotFoundException, IOException
  {
    String oagis80Dir = System.getProperty("oagis80Dir");
    Assert.assertNotNull(oagis80Dir);
    if (!oagis80Dir.endsWith("/") && !oagis80Dir.endsWith("\\"))
      oagis80Dir = oagis80Dir + "/";
    File dir = new File(oagis80Dir + "OAGIS8.0");
    if (dir.exists() && dir.isDirectory())
    {
	      IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("sp");
	      project.create(null);
	      project.open(null);
	      copy(dir, project);
	      joinBackgroundJobs();
	      final List xsds = new ArrayList();
	      project.accept
	      (
	        new IResourceProxyVisitor()
	        {
	          public boolean visit(IResourceProxy proxy) throws CoreException
	          {
	            if (proxy.getName().endsWith(".xsd"))
	            {
	              xsds.add(proxy.requestResource());
	            }
	            return true;
	          }
	        },
	        IContainer.INCLUDE_PHANTOMS
	      );
      tagAsSummary("Read XSD", new Dimension[] {Dimension.ELAPSED_PROCESS, Dimension.WORKING_SET});
      //System.out.println("processing: " + xsds.size() + " files...");
      startMeasuring();
	  for (Iterator it = xsds.iterator(); it.hasNext();)
        readXSD((IFile)it.next());
      stopMeasuring();
      commitMeasurements();
      assertPerformance();
    }
    else
      fail(dir.toString());
  }
  
  private void joinBackgroundJobs()
  {
    Display.getDefault().syncExec(new Runnable()
    {
      public void run()
      {
        try
        {
          Platform.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
        }
        catch (InterruptedException e)
        {
        }
        long start = System.currentTimeMillis();
        Display display = Display.getDefault();
        while (System.currentTimeMillis() - start < 5000)
        {
          if (!display.readAndDispatch())
          {
            display.sleep();
          }
        }
      }
    });
  }  
  
  private void copy(File src, IContainer dest) throws CoreException, FileNotFoundException
  {
    File[] children = src.listFiles();
    for (int i = 0; i < children.length; i++)
    {
      String name = children[i].getName();
      if (children[i].isDirectory())
      {
        IFolder folder = dest.getFolder(new Path(name));
        folder.create(true, true, null);
        copy(children[i], folder);
      }
      else if (name.endsWith(".xsd"))
      {
        IFile file = dest.getFile(new Path(name));
        file.create(new FileInputStream(children[i]), true, null);
      }
    }
  }
  
  private void readXSD(IFile file) throws CoreException, IOException
  {
    InputStream is = file.getContents();
	ResourceSet resourceSet = new ResourceSetImpl();
    XSDResourceImpl resource = (XSDResourceImpl)resourceSet.createResource(URI.createURI("*.xsd"));
	resource.load(is,null); 
	//XSDSchema xsdSchema = 
		resource.getSchema();
	//xsdSchema.validate();
	//List l = xsdSchema.getAllDiagnostics();
  }
}

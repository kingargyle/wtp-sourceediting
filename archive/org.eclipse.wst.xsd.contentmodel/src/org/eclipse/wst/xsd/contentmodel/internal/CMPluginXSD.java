/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xsd.contentmodel.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.Plugin;


public class CMPluginXSD extends Plugin 
{
  public CMPluginXSD(IPluginDescriptor descriptor) 
  {
    super(descriptor);
  }     

  public void startup() throws CoreException 
  {
    XSDTypeUtil.initialize();
    //ContentModelManager.getInstance().setInferredGrammarFactory(new InferredGrammarFactoryImpl());
  }
}

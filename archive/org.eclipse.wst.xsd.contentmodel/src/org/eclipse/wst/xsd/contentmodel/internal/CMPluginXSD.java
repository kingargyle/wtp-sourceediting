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
    //CMPlugin.getInstance().setInferredGrammarFactory(new InferredGrammarFactoryImpl());
  }
}

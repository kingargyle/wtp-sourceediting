package org.eclipse.wst.xsd.contentmodel.internal;

import org.eclipse.wst.contentmodel.CMDocument;
import org.eclipse.wst.contentmodel.factory.CMDocumentFactory;
//import com.ibm.etools.contentmodel.util.CMDocumentBuilderRegistry;

/**
 *  This builder handles building .dtd / .xsd grammar files
 */
public class CMDocumentFactoryXSD implements CMDocumentFactory
{
  public static final String XSD_FILE_TYPE = "XSD";

  public CMDocumentFactoryXSD() 
  {  
    // here we call init on the XSD and DTD packages to avoid strange initialization bugs
    //
    org.eclipse.xsd.impl.XSDPackageImpl.init();
  }

 
  public CMDocument createCMDocument(String uri)
  {               
    // work around a bug in our parsers
    // todo... revist this
    //
    String fileProtocol = "file:";
    if (uri.startsWith(fileProtocol))
    {
      uri = uri.substring(fileProtocol.length());
    }

    // work around a VAJava bug
    // todo... revist this
    //
    String valoaderProtocol = "valoader:/";
    if (uri.startsWith(valoaderProtocol))
    {
      uri = uri.substring(valoaderProtocol.length());
    }

	// TODO... separate DTD/XSD into separate factories
	// remove this resourceType variable
	String resourceType = null;
	
	
    CMDocument result = null;
    try
    {                                
        result = XSDImpl.buildCMDocument(uri);     
    }
    catch (Exception e)
    {
    }
    return result;  
  } 
}

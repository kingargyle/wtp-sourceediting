package org.eclipse.wst.xsd.contentmodel.internal;
                                              
import java.io.InputStream;
import java.net.URL;

import org.eclipse.wst.encoding.EncodingHelper;
import org.eclipse.wst.contentmodel.CMDocument;

public class CMDocumentEncodingHelper
{
  protected static void setEncodingInfo(CMDocument cmDocument, String uri)
  {
    if (cmDocument != null)
    {
      uri = addImpliedFileProtocol(uri);
      InputStream inputStream = null;
      try
      {       
        URL url = new URL(uri);
        inputStream = url.openStream();
        String[] encodingInfo = (String[])cmDocument.getProperty("encodingInfo");
        if (encodingInfo != null)
        {
          //if (Display.getCurrent() != null)
          //{
            updateFromEncodingHelper(inputStream, encodingInfo);
          //}
          //else
          // {
          //   encodingInfo[0] = "UTF8";
          //   encodingInfo[1] = "UTF-8";	        	                              
          // }
        }
      }
      catch (Exception e)
      {
      }                        
      finally
      {
        if (inputStream != null)
        {
          try
          { 
            inputStream.close();
          }
          catch (Exception e)
          {
          }   
        }
      }
    }
  }  

  private static void updateFromEncodingHelper(InputStream iStream, String[] encodingInfo)
  {
    EncodingHelper encodingHelper = new EncodingHelper(iStream);
    encodingInfo[0] = encodingHelper.getEncoding() != null ? encodingHelper.getEncoding() : encodingHelper.getDefaultEncoding();
    encodingInfo[1] = encodingHelper.getEncodingTag() != null ? encodingHelper.getEncodingTag() : encodingHelper.getDefaultEncodingTag();
  }


  // This code is taken from com.ibm.etools.xmlutility.uri.URIHelper
  // I didn't want to add this plugin  as a dependency this plugin
  // in order to simplify our xerces dependenies
  protected static final String FILE_PROTOCOL = "file:";
  protected static final String PROTOCOL_PATTERN = ":"; 

  public static String addImpliedFileProtocol(String uri)
  {  
    if (!hasProtocol(uri))
    {
      uri = FILE_PROTOCOL + uri;
    }
    return uri;
  }

  public static boolean hasProtocol(String uri)
  {
    boolean result = false;     
    if (uri != null)
    {
      int index = uri.indexOf(PROTOCOL_PATTERN);
      if (index != -1 && index > 2) // assume protocol with be length 3 so that the'C' in 'C:/' is not interpreted as a protocol
      {
        result = true;
      }
    }
    return result;
  } 
}

package org.eclipse.wst.xsd.contentmodel.internal.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.wst.uriresolver.URIResolverPlugin;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.eclipse.xsd.util.XSDSchemaLocator;

public class XSDSchemaLocatorImpl extends AdapterImpl implements XSDSchemaLocator
{
    /**
     * @see org.eclipse.xsd.util.XSDSchemaLocator#locateSchema(org.eclipse.xsd.XSDSchema,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public XSDSchema locateSchema(XSDSchema xsdSchema, String namespaceURI, String rawSchemaLocationURI, String resolvedSchemaLocationURI)
    {
      XSDSchema result = null;
      String baseLocation = xsdSchema.getSchemaLocation();      
      String resolvedURI = URIResolverPlugin.createResolver().resolve(baseLocation, namespaceURI, rawSchemaLocationURI);
      if (resolvedURI == null) 
      {
        resolvedURI = resolvedSchemaLocationURI;
      }
      try
      {
        ResourceSet resourceSet = xsdSchema.eResource().getResourceSet();
        URI uri = URI.createURI(resolvedURI);
        InputStream inputStream = resourceSet.getURIConverter().createInputStream(uri);
        XSDResourceImpl resolvedResource = (XSDResourceImpl)resourceSet.createResource(URI.createURI("*.xsd"));
        resolvedResource.setURI(uri);
        resolvedResource.load(inputStream, resourceSet.getLoadOptions());
        result = resolvedResource.getSchema();
      }
      catch (IOException exception)
      {
        // It is generally not an error to fail to resolve.
        // If a resource is actually created, 
        // which happens only when we can create an input stream,
        // then it's an error if it's not a good schema
      }
      return result;
    }

    public boolean isAdatperForType(Object type)
    {
      return type == XSDSchemaLocator.class;
    }  
}

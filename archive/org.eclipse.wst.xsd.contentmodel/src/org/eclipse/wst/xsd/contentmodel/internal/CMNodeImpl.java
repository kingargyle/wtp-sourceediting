package org.eclipse.wst.xsd.contentmodel.internal;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.wst.contentmodel.CMNode;

public abstract class CMNodeImpl extends AdapterImpl implements CMNode
{
  protected static final String PROPERTY_DOCUMENTATION = "documentation";
  protected static final String PROPERTY_DOCUMENTATION_SOURCE = "documentationSource";
  protected static final String PROPERTY_DOCUMENTATION_LANGUAGE = "documentationLanguage";
  protected static final String PROPERTY_MOF_NOTIFIER = "key";
  protected static final String PROPERTY_DEFINITION_INFO = "http://com.ibm.etools/cm/properties/definitionInfo";
  protected static final String PROPERTY_DEFINITION = "http://com.ibm.etools/cm/properties/definition";

  public abstract Object getKey();

  public boolean supports(String propertyName)
  {
    return propertyName.equals(PROPERTY_MOF_NOTIFIER);
  }

  public Object getProperty(String propertyName)
  {
    return null;
  }

  public void setProperty(String propertyName, Object object)
  {
  }
}

package org.eclipse.wst.html.core.external.content;
import java.awt.List;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;



import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class ExternalContentProviderExtension {

	private Hashtable fPreferedProvider = new Hashtable();
	
	private ContentElectoral pSourceViewers 		= new ContentElectoral();
	private ContentElectoral pPartitionHelpers 	= new ContentElectoral();
	
	private String fHostContentID;
	
	
	private final String EXTENSION_POINT_NAME 	  = "subcontentprovider"; //$NON-NLS-1$
	private final String EXTENSION_POINT_PROVIDER = "org.eclipse.wst.html.core"; //$NON-NLS-1$
	
	private final String CONTENT_EXT_NAME = "content";
	private final String CONTENT_PARTITION_EXT_NAME = "partition";
	private final String CONTENT_FEATURE_LEVEL_EXT_NAME = "featureLevel";
	
	
	private final String PARTITIONHELPER_EXT_NAME="partitionHelper";
	private final String PARTITIONHELPER_CLASS_EXT_NAME = "class";
	private final String PARTITIONHELPER_MASTERPARTITION_EXT_NAME = "masterPartitions";
	
	private final String SOURCEVIEWERCONFIGURATION_EXT_NAME = "sourceViewerConfiguration";
	private final String SOURCEVIEWERCONFIGURATION_CLASS_EXT_NAME = "class";
	
	private static ExternalContentProviderExtension _instance;
	
	public static ExternalContentProviderExtension getInstance(){
		if(_instance==null){
			_instance = new ExternalContentProviderExtension();
		}
		
		return _instance;
	}
	
	public static ContentElectoral getPartitionersElectoral(){
		return getInstance().pPartitionHelpers;
	}
	
	public static ContentElectoral getSourceViewerElectoral(){
		return getInstance().pSourceViewers;
	}
	
	public static void init(){
		if(_instance==null){
			_instance = new ExternalContentProviderExtension();
		}
	}
	
	private ExternalContentProviderExtension(){
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT_PROVIDER, EXTENSION_POINT_NAME);
		if (point == null) return;
		
		IExtension extensions[] = point.getExtensions();
		if ((extensions != null) && (extensions.length > 0)) {
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement elements[] = extensions[i].getConfigurationElements();
					if ((elements != null) && (elements.length > 0)) {
						Hashtable values = new Hashtable();
						/* Process all element names, hashtable them for later */
						for (int j = 0; j < elements.length; j++) {
							IConfigurationElement config = elements[j];
							values.put(config.getName(), config);
						}
						/* process values loaded from ext points */
						OrderedContentType type = getContentDefinition((IConfigurationElement)values.get(CONTENT_EXT_NAME));
						ContentElectoral partitioners = getPartitioner((IConfigurationElement)values.get(PARTITIONHELPER_EXT_NAME), type);
						Object sourceviewer = getSourceViewerConfig((IConfigurationElement)values.get(SOURCEVIEWERCONFIGURATION_EXT_NAME));
						
						/* Add the new candidates as read frome extension points */
						pSourceViewers.addCanidate(type, sourceviewer);
						pPartitionHelpers.mergeElectoral(partitioners);
						
					}
				}
			}
		
		
	}
	
	private Object getSourceViewerConfig(IConfigurationElement config){
		Object viewer=null;
		try {
			viewer = config.createExecutableExtension(SOURCEVIEWERCONFIGURATION_CLASS_EXT_NAME);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error loading SourceViewerConfiguration from ext point in ExternalContentPrviderResolution:" + e);
		}
		return viewer;
	}
	
	private ContentElectoral getPartitioner(IConfigurationElement config, 
														 OrderedContentType contentType){
		
		/* partitioner elections are for master partition type based on feature level */
		
		ContentElectoral partitioners = new ContentElectoral();
		int featureLevel = contentType.getFeatureLevel();
		
		String rawMPString = config.getAttribute(PARTITIONHELPER_MASTERPARTITION_EXT_NAME);
		
		/* dont support partitioners with undefined MP's */
		if(rawMPString==null) return null;
		
		String masterPartitions[] = rawMPString.split(",");
		Object partitioner = null;
		try {
			partitioner = config.createExecutableExtension(PARTITIONHELPER_CLASS_EXT_NAME);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			System.out.println("Error loading partitioner from ext point in ExternalContentPrviderResolution:" + e);
		}
		
		if(partitioner==null) return null;
		
		for(int i = 0;i<masterPartitions.length;i++){
			OrderedContentType oct = new OrderedContentType(masterPartitions[i],featureLevel);
			partitioners.addCanidate(oct, partitioner);
		}
		return partitioners;
	}

	
	private OrderedContentType getContentDefinition(IConfigurationElement config){
		String content_id = config.getAttribute(CONTENT_PARTITION_EXT_NAME);
		final String resolvedContentType =  content_id.intern();
		
		int featureLevel = 0;
		
		try{
			featureLevel = Integer.parseInt(config.getAttribute(CONTENT_FEATURE_LEVEL_EXT_NAME));
		}catch(Exception e){/* gets default feature level of 0 */}
		return new OrderedContentType(resolvedContentType, featureLevel);
	}

}

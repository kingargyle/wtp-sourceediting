package org.eclipse.wst.html.ui.external.content;

import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.wst.html.core.external.content.ContentElectoral;
import org.eclipse.wst.html.core.external.content.ExternalContentProviderExtension;
import org.eclipse.wst.sse.core.internal.text.rules.StructuredTextPartitioner;
import org.eclipse.wst.sse.ui.StructuredTextViewerConfiguration;

public class ExternalContentResolver {
	
	private ContentElectoral fPartitioners;
	
	private ContentElectoral fSourceViewers;
	
	private static ExternalContentResolver _instance;
	
	private ExternalContentResolver(){
		fPartitioners=ExternalContentProviderExtension.getPartitionersElectoral();
		fSourceViewers=ExternalContentProviderExtension.getSourceViewerElectoral();
	}
	
	public static ExternalContentResolver getInstance(){
		if(_instance==null){
			_instance= new ExternalContentResolver();
		}
		
		return _instance;
	}
	
	public static void init(){
		if(_instance==null){
			_instance= new ExternalContentResolver();
		}
	}
	
	public StructuredTextViewerConfiguration getElectedSourceViewerByType(String type){
		return (StructuredTextViewerConfiguration)fSourceViewers.getWinner(type);
	}
	
	public StructuredTextPartitioner getPartitionerForType(String masterType){
		return (StructuredTextPartitioner)fPartitioners.getWinner(masterType);
	}

	public String[] getSupportedContentTypes(){
		return fSourceViewers.getArrayOfTypes();
	}
	
	public boolean isExternalSourceViewerConfigOf(String type){
		return isInArray(fSourceViewers.getArrayOfTypes(),type);
	}
	
	public boolean isSubPartitionerOfMaster(String type){
		return isInArray(fPartitioners.getArrayOfTypes(),type);
	}
	
	private static boolean isInArray(String StringArray[], String text){
		if(StringArray == null || text == null) return false;
		
		
		for(int i = 0;i<StringArray.length;i++){
			if(StringArray[i].equalsIgnoreCase(text.trim())) return true;
		}
		return false;
	}
}

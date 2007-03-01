package org.eclipse.wst.html.core.external.content;


public class OrderedContentType implements Comparable {
	
	private String content_id;
	private int feature_level;
	private long creationTime;
	
	public OrderedContentType(){
		
		this.creationTime= System.currentTimeMillis();
	}
	
	public OrderedContentType(String content_id, int feature_level){
		this.content_id=content_id;
		this.feature_level=feature_level;
	}
	
	public String getContentID(){ return content_id; }
	public void setContentID(String content_id){ this.content_id=content_id; }
	
	public int getFeatureLevel(){ return feature_level; }
	public void setFeatureLevel(int feature_level){ this.feature_level=feature_level; }
	
	public int compareTo(Object o) {
		if(o instanceof OrderedContentType){
			
			
			OrderedContentType c = (OrderedContentType)o;
			
			if(c.content_id==null || !c.content_id.equals(this.content_id)) return 0;
			
			if(this.feature_level<c.feature_level) return -1;
			if(this.feature_level>c.feature_level) return 1;
			
			/* equal, so determine based on creation time */
			if(this.feature_level==c.feature_level && this.creationTime<c.creationTime) return 1;
			if(this.feature_level==c.feature_level && this.creationTime>c.creationTime) return 1;
			
		}
		// All non similar classes are peers
		return 0;
	}
	
	
}

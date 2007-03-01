package org.eclipse.wst.html.core.internal.text;

import java.util.Locale;


import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;


import org.eclipse.wst.css.core.text.ICSSPartitions;
import org.eclipse.wst.html.core.internal.provisional.HTML40Namespace;
import org.eclipse.wst.html.core.internal.provisional.JavaScriptNameSpace;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.sse.core.internal.parser.ForeignRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredTextPartitioner;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.sse.core.internal.text.rules.IStructuredTypedRegion;
import org.eclipse.wst.sse.core.internal.text.rules.SimpleStructuredTypedRegion;
import org.eclipse.wst.sse.core.internal.util.ScriptLanguageKeys;
import org.eclipse.wst.sse.core.utils.StringUtils;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.eclipse.wst.xml.core.internal.text.rules.StructuredTextPartitionerForXML;

/**
 * Document partitioner for HTML. Client-side scripts of type JavaScript are
 * reported as ST_SCRIPT, all others for <script language="foo" type="foo2">
 * as SCRIPT.language:FOO and SCRIPT.type:FOO2.
 */
public class StructuredTextPartitionerForHTML extends StructuredTextPartitionerForXML implements IStructuredTextPartitioner {
	// Removed IHTMLPartitions.SCRIPT
	private final static String[] configuredContentTypes = new String[]{IHTMLPartitions.HTML_DEFAULT, IHTMLPartitions.HTML_DECLARATION, IHTMLPartitions.HTML_COMMENT, ICSSPartitions.STYLE};

	public static final String JAVASCRIPT = "javascript"; //$NON-NLS-1$
	public static final String JAVASCRIPT_APPLICATION = "application/x-javascript"; //$NON-NLS-1$

	public StructuredTextPartitionerForHTML() {
		super();
		
	}

	public IStructuredTypedRegion createPartition(int offset, int length, String type) {
		if (type == IHTMLPartitions.SCRIPT) {
			String stype = getScriptingPartitionType(offset);
			return super.createPartition(offset, length, stype);
		}
		return super.createPartition(offset, length, type);
	}

	protected void setInternalPartition(int offset, int length, String type) {
		String localType = type;
		if (type == IHTMLPartitions.SCRIPT) {
				localType = getScriptingPartitionType(offset);
		}
		super.setInternalPartition(offset, length, localType);
	}
	
//	private String getScriptingPartitionType(IStructuredDocumentRegion coreNode) {
//		
//		NodeHelper nh = new NodeHelper(coreNode);
//		String language = null;
//		String type = null;
//		if (coreNode.getType() ==  DOMRegionContext.XML_TAG_NAME){
//			if((!nh.isEndTag() || nh.isSelfClosingTag() )&& nh.nameEquals(HTML40Namespace.ElementName.SCRIPT)){
//				type = nh.getAttributeValue("type");
//				language = nh.getAttributeValue("language");
//			}
//		}
//	}
	
	
	
	
	private String getScriptingPartitionType(int offset) {
		String language = null;
		String type = null;
		String result = IHTMLPartitions.SCRIPT;
		
		IStructuredDocumentRegion node = fStructuredDocument.getRegionAtCharacterOffset(offset);
		ITextRegion attrNameRegion = null;

		/* we're on an XML_ATTRIBUTE_VALUE node */
		if(node==null){
			return result;
		}
		
		if(node.getType()!=DOMRegionContext.XML_TAG_ATTRIBUTE_VALUE ){
			/* should check global script language type, but haven't implemented */
			return result;
		}
		
		while (node != null && isValidScriptingRegionType(node.getType())) {
			node = node.getPrevious();
		}

		ITextRegionList regions = node.getRegions();
		if (regions.size() > 4 && regions.get(1).getType() == DOMRegionContext.XML_TAG_NAME) {
			ITextRegion potentialLanguageRegion = regions.get(1);
			String potentialLanguageString = node.getText(potentialLanguageRegion);
			if (potentialLanguageString.equalsIgnoreCase(HTML40Namespace.ElementName.SCRIPT)) {
				for (int i = 0; i < regions.size(); i++) {
					ITextRegion region = regions.get(i);
					String regionType = region.getType();
					if (regionType == DOMRegionContext.XML_TAG_ATTRIBUTE_NAME)
						attrNameRegion = region;
					else if (regionType == DOMRegionContext.XML_TAG_ATTRIBUTE_VALUE) {
						String attrName = node.getText(attrNameRegion);
						if (attrName.equalsIgnoreCase(HTML40Namespace.ATTR_NAME_LANGUAGE))
							language = StringUtils.strip(node.getText(region));
						else if (attrName.equalsIgnoreCase(HTML40Namespace.ATTR_NAME_TYPE)) {
							type = StringUtils.strip(node.getText(region));
							break;
						}
						attrNameRegion = null;
					}
				}
			}
		}
		if (type != null)
			result = lookupScriptType(type);
		else if (language != null)
			result = lookupScriptLanguage(language);
		return result;
	}

	private boolean isValidScriptingRegionType(String type) {
		return type == DOMRegionContext.BLOCK_TEXT || type == DOMRegionContext.XML_CDATA_OPEN || type == DOMRegionContext.XML_CDATA_TEXT || type == DOMRegionContext.XML_CDATA_CLOSE;
	}

	protected void initLegalContentTypes() {
		fSupportedTypes = getConfiguredContentTypes();
	}

	private String lookupScriptType(String type) {
		for (int i = 0; i < ScriptLanguageKeys.JAVASCRIPT_MIME_TYPE_KEYS.length; i++)
			if (ScriptLanguageKeys.JAVASCRIPT_MIME_TYPE_KEYS[i].equalsIgnoreCase(type))
				return IHTMLPartitions.SCRIPT;
		return IHTMLPartitions.SCRIPT + ".type." + type.toUpperCase(Locale.ENGLISH); //$NON-NLS-1$
	}

	private String lookupScriptLanguage(String language) {
		for (int i = 0; i < ScriptLanguageKeys.JAVASCRIPT_LANGUAGE_KEYS.length; i++)
			if (ScriptLanguageKeys.JAVASCRIPT_LANGUAGE_KEYS[i].equalsIgnoreCase(language))
				return IHTMLPartitions.SCRIPT;
		return IHTMLPartitions.SCRIPT + ".language." + language.toUpperCase(Locale.ENGLISH); //$NON-NLS-1$
	}

//	public String getPartitionType(ITextRegion region, int offset) {
//		System.out.println("1001: looking at region of type:"+ region.getType());
//		String result = null;
//		if (region.getType() == DOMRegionContext.XML_COMMENT_TEXT || region.getType() == DOMRegionContext.XML_COMMENT_OPEN)
//			result = IHTMLPartitions.HTML_COMMENT;
//		else if (region.getType() == DOMRegionContext.XML_DOCTYPE_DECLARATION || region.getType() == DOMRegionContext.XML_DECLARATION_OPEN)
//			result = IHTMLPartitions.HTML_DECLARATION;
//		else
//			result = super.getPartitionType(region, offset);
//		return result;
//	}

	public String getPartitionTypeBetween(IStructuredDocumentRegion previousNode, IStructuredDocumentRegion nextNode) {
		
		ITextRegion previousStartTagNameRegion = null;
		ITextRegion nextEndTagNameRegion = null;
		
		ITextRegion[] regions = previousNode.getRegions().toArray();
		for (int i = 0; i < regions.length; i++) {
			if(regions[i].getType() == DOMRegionContext.XML_TAG_NAME) {
				previousStartTagNameRegion = regions[i];
				break;
			}
		}
		regions = nextNode.getRegions().toArray();
		for (int i = 0; i < regions.length; i++) {
			if(regions[i].getType() == DOMRegionContext.XML_TAG_NAME) {
				nextEndTagNameRegion = regions[i];
				break;
			}
		}
		
		if(previousStartTagNameRegion == null || nextEndTagNameRegion == null)
			return IHTMLPartitions.HTML_DEFAULT;
		
		String name1 = previousNode.getText(previousStartTagNameRegion);
		String name2 = nextNode.getText(nextEndTagNameRegion);
		if (name1.equalsIgnoreCase(HTML40Namespace.ElementName.SCRIPT) && name2.equalsIgnoreCase(HTML40Namespace.ElementName.SCRIPT))
			//			return ST_SCRIPT;
			return getScriptingPartitionType(previousNode.getStartOffset(previousStartTagNameRegion));
		else if (name1.equalsIgnoreCase(HTML40Namespace.ElementName.STYLE) && name2.equalsIgnoreCase(HTML40Namespace.ElementName.STYLE))
			return ICSSPartitions.STYLE;
		return super.getPartitionTypeBetween(previousNode, nextNode);
	}


//	protected String getPartitionType(ForeignRegion region, int offset) {
//		String tagname = region.getSurroundingTag();
//		String result = null;
//		System.out.println("HTML Looking at type:" + region.getType() + " surrounding region " + region.getSurroundingTag());
//		// tagname should not be null,
//		// but see https://w3.opensource.ibm.com/bugzilla/show_bug.cgi?id=4911
//		if (tagname == null) {
//			result = getUnknown();
//		}
//		else if (tagname.equalsIgnoreCase(HTML40Namespace.ElementName.SCRIPT))
//			result = IHTMLPartitions.SCRIPT;
//		else if (tagname.equalsIgnoreCase(HTML40Namespace.ElementName.STYLE))
//			result = ICSSPartitions.STYLE;
//		else
//			result = super.getPartitionType(region, offset);
//
//		return result;
//	}

	public String getDefaultPartitionType() {
		return IHTMLPartitions.HTML_DEFAULT;
	}

	public IDocumentPartitioner newInstance() {
		return new StructuredTextPartitionerForHTML();
	}

	/**
	 * @return
	 */
	public static String[] getConfiguredContentTypes() {
		return configuredContentTypes;
	}

	/* Added---------------- BC ------------------------------ */
	public String getPartitionType(ITextRegion region, int offset) {
		String result = null;
		String attrName = null;
//		char charAtOffset=0;
//		
//		
//		
//		try{
//			charAtOffset = fStructuredDocument.getChar(offset);
//			System.out.println(charAtOffset);
//		}catch(Exception e){}
		
		if(region.getType()==DOMRegionContext.XML_TAG_ATTRIBUTE_VALUE )
			attrName = getAttrName(region,offset);
		
		
		if (region.getType() == DOMRegionContext.XML_COMMENT_TEXT || region.getType() == DOMRegionContext.XML_COMMENT_OPEN){
			result = IHTMLPartitions.HTML_COMMENT;
		}else if (region.getType() == DOMRegionContext.XML_DOCTYPE_DECLARATION || region.getType() == DOMRegionContext.XML_DECLARATION_OPEN){
			result = IHTMLPartitions.HTML_DECLARATION;
		}else if( null!=attrName && (isInArray(JavaScriptNameSpace.EVENTS, attrName))  ){
			/* check for script elements in attributes */
			result = IHTMLPartitions.SCRIPT;
		}else{
			result = super.getPartitionType(region, offset);
		}
		
		return result;
	}

	public String getPartitionType(ForeignRegion region, int offset) {
		String result = null;
		String attrName = null;
		char charAtOffset=0;
		String tagname = region.getSurroundingTag();
		if(region.getType()==DOMRegionContext.XML_TAG_ATTRIBUTE_VALUE )
			attrName = getAttrName(region,offset);
		
		if(region.getType()==DOMRegionContext.XML_TAG_ATTRIBUTE_VALUE )
			attrName = getAttrName(region,offset);
		
		if (region.getType() == DOMRegionContext.XML_COMMENT_TEXT || region.getType() == DOMRegionContext.XML_COMMENT_OPEN)
			result = IHTMLPartitions.HTML_COMMENT;
		else if (region.getType() == DOMRegionContext.XML_DOCTYPE_DECLARATION || region.getType() == DOMRegionContext.XML_DECLARATION_OPEN){
			result = IHTMLPartitions.HTML_DECLARATION;
		}else if( null!=attrName && (isInArray(JavaScriptNameSpace.EVENTS, attrName))  ){
			/* check for script elements in attributes */
			result = IHTMLPartitions.SCRIPT;
		}else	if (tagname.equalsIgnoreCase(HTML40Namespace.ElementName.SCRIPT)){
				result = IHTMLPartitions.SCRIPT;
		}else if (tagname.equalsIgnoreCase(HTML40Namespace.ElementName.STYLE)){
				result = ICSSPartitions.STYLE;
		}else
			result = super.getPartitionType(region, offset);
		return result;
	}
	
	private String getAttrName(ITextRegion attrValueRegion, int offset){
		if( attrValueRegion.getType() !=DOMRegionContext.XML_TAG_ATTRIBUTE_VALUE ) return null;
		
		IStructuredDocumentRegion node = fStructuredDocument.getRegionAtCharacterOffset(offset);
		ITextRegionList regionList = node.getRegions();
		int currentIndex = regionList.indexOf(attrValueRegion);
		
		if((currentIndex-2)<0) return null;
		ITextRegion tagAttrNameRegion = regionList.get(currentIndex-2);
		
		String tagAttrName = node.getText().substring(tagAttrNameRegion.getStart() , tagAttrNameRegion.getTextEnd()).trim();
		return tagAttrName;
	}

	public static boolean isInArray(String StringArray[], String text){
		if(StringArray == null || text == null) return false;
		
		
		for(int i = 0;i<StringArray.length;i++){
			if(StringArray[i].equalsIgnoreCase(text.trim())) return true;
		}
		return false;
	}
	/* End Added---------------- BC ------------------------------ */
}
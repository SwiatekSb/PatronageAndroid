package pl.patronage.task1.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class DataXmlParser {

	
	private static final String ns = null;
	/**
	 * This method setting up parser and calls readData. 
	 * @param InputStream of opened file.
	 * @return List of parsed Items 
	 * @throws XmlPullParserException 
	 * @throws IOException
	 */
	public List<Items> parse(InputStream in) throws XmlPullParserException, IOException{
		try{
			XmlPullParser parser = Xml.newPullParser();
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(in, null);
	        parser.nextTag();
	            
	        return readData(parser);
		}finally{
			in.close();
		}	
	}
	/**
	 * This method looks for "items" in XML and calls readItems to parse each item.
	 * @param parser 
	 * @return List of Items
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private List<Items> readData(XmlPullParser parser) throws XmlPullParserException, IOException{
		List<Items> items = new ArrayList<Items>();
		
		int eventType = parser.getEventType();

		while(eventType != XmlPullParser.END_DOCUMENT){
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }

	        String name = parser.getName();
	        if(name.equals(XmlConstans.TAG_ITEMS)){
	        	//add items to List
	        	items.add(readItems(parser));
	        }else{
	        	skip(parser);
	        }	
	        eventType = parser.next();
		}
		return items;
	}
	/**
	 * This method is parsing Items body(List of item elements)
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private Items readItems(XmlPullParser parser) throws XmlPullParserException, IOException {
		String name = null;
		//List<Item> item = new ArrayList<Item>();
		List<XmlObject> xmlObject = new ArrayList<XmlObject>();
		
		parser.require(XmlPullParser.START_TAG, ns,XmlConstans.TAG_ITEMS);
	    name = parser.getName();
	    String sortOrder = parser.getAttributeValue(ns,XmlConstans.ATTRIBUTE_SORT);
	    
	    while(parser.next() != XmlPullParser.END_TAG){
	    	if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	    	name = parser.getName();
			if(name.equals(XmlConstans.TAG_ITEM)){
	    		
	    		String type = parser.getAttributeValue(ns, XmlConstans.ATTRIBUTE_TYPE);
	    		XmlObject it = Item.deserialize(readText(parser), type);
	    		if(it != null){
	    			xmlObject.add(it);
	    		}
	    	}else if(name.equals(XmlConstans.TAG_TEXT)){
	    		//parsing text
	    		XmlObject xmlO = Text.deserialize(readText(parser));
	    		xmlObject.add(xmlO);
	    	}else if(name.equals(XmlConstans.TAG_POINT)){
	    		//parsing point
	    		XmlObject xmlO = readPoint(parser);
	    		xmlObject.add(xmlO);
	    	}else{
	    		skip(parser);
	    	}
	    }
		  return new Items(sortOrder,xmlObject);
	 //   return new Items(sortOrder,item);
	}
	//read x and y
	private XmlObject readPoint(XmlPullParser parser) throws XmlPullParserException, IOException{
		Float x = null, y = null;
		String name = null;
		while(parser.next() != XmlPullParser.END_TAG){
	    	if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	    	name = parser.getName();
	    	 System.out.println(name);
	    		if(name.equals("x")){
	    			x = Float.parseFloat(readText(parser));
	    		}else if(name.equals("y")){
	    			y =  Float.parseFloat(readText(parser));
	    		}else{
	    			skip(parser);
	    		}
		 
		 }
		return Point.deserialize(x, y);
	}
	/**
	 * This method read tag text.
	 * @param parser
	 */
	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException{
	    String result = "";
	
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
		return result;
	}
	/**
	 * This method skip unused tag. 
	 * @param parser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
	
}

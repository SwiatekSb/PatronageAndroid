package pl.patronage.task1.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Items {
	
	private String sortOrder;
	private List<XmlObject> xmlObject;
	
	Items(List<XmlObject> xmlObject){
		//this.xmlObject = xmlObject;
		new Items(null,xmlObject);
	}
	
	Items(String sortOrder, List<XmlObject> item){
		this.sortOrder = sortOrder;
		this.xmlObject = item;
	}

	public float[] getPointList(){
		List<Point> points = getPoint();
		float[] coordinates = new float[(points.size()*2)];
		//List<Float> numbers = new ArrayList<Float>();
		int i = 0;
		for (Point point : points) {
			coordinates[i++] = point.getPointX();
			coordinates[i++] = point.getPointY();
		}

		return coordinates;
	}
	
	public List<String> getTextList(){
		List<String> texts = new ArrayList<String>();
		List<Text> text = getText();
		
		for (Text text2 : text) {
			texts.add((String) text2.serialize());
		}
		
		return texts;
	}
	
	private void sort(){
		List<IntegerItem> integers = getIntegerItems();
		List<StringItem> strings = getStringItems();
		List<XmlObject> it = new ArrayList<XmlObject>();
		
		if(sortOrder.equals(XmlConstans.ATTRIBUTE_VALUE_ASC)){	
			Collections.sort(integers);
		}else if(sortOrder.equals(XmlConstans.ATTRIBUTE_VALUE_DESC)){
			Collections.sort(integers);
			Collections.reverse(integers);
		}else{
			//not recognize the type of sorting
			return;
		}
		
		it.addAll(integers);
		it.addAll(strings);	
		xmlObject = it;
	}
	
	public String serialize(){
		StringBuilder itemsString = new StringBuilder();	
		sort();
		
		itemsString.append("<"+XmlConstans.TAG_ITEMS +">\n");
		for (XmlObject it : xmlObject) {
			itemsString.append("\t"+it.serialize()+"\n");
		}
		itemsString.append("</"+ XmlConstans.TAG_ITEMS+">\n");
		return itemsString.toString();
	}
	
	private List<IntegerItem> getIntegerItems(){
		List<IntegerItem> intItem = new ArrayList<IntegerItem>();
		
		for (XmlObject it : xmlObject) {
			if(it.getClass().equals(IntegerItem.class)){
				intItem.add((IntegerItem) it);
			}
		}
		return intItem;
	}
	
	private List<StringItem> getStringItems(){
		List<StringItem> stringItem = new ArrayList<StringItem>();
		
		for (XmlObject it : xmlObject) {
			if(it.getClass().equals(StringItem.class)){
				stringItem.add((StringItem) it);
			}
		}
		return stringItem;
	}
	
	private List<Point> getPoint(){
		List<Point> point = new ArrayList<Point>();
		
		for(XmlObject xml : xmlObject){
			if(xml.getClass().equals(Point.class)){
				point.add((Point) xml);
			}
		}
		return point;
	}
	
	private List<Text> getText(){
		List<Text> text = new ArrayList<Text>();
	
		for (XmlObject xml : xmlObject) {
			if(xml.getClass().equals(Text.class)){
				text.add((Text) xml);
				
			}
		}
		return text;
	}
	
}

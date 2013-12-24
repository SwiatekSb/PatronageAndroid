package pl.patronage.task1.parser;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Items {
	
	private String sortOrder;
//	private List<Item> itemList ;
	private List<XmlObject> xmlObject;
	
	Items(List<XmlObject> xmlObject){
		this.xmlObject = xmlObject;
	}
	

	Items(String sortOrder, List<XmlObject> item){
		this.sortOrder = sortOrder;
		this.xmlObject = item;
	}
	
	public List<Float> getPointList(){
		List<Point> points = getPoint();
		List<Float> numbers = new ArrayList<Float>();
		
		for (Point point : points) {
			numbers.add(point.getPointX());
			numbers.add(point.getPointY());
		}

		return numbers;
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
			return;
		}
	
		it.addAll(integers);
		it.addAll(strings);	
		xmlObject = it;
	}
	
	public String serialize(){
		StringBuilder itemsString = new StringBuilder();	
		sort();
		
		itemsString.append("<item>\n");
		for (XmlObject it : xmlObject) {
			itemsString.append("\t"+it.serialize()+"\n");
		}
		itemsString.append("</items>\n");
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
}

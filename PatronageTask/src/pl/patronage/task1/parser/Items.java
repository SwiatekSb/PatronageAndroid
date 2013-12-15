package pl.patronage.task1.parser;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Items {
	
	private String sortOrder;
	private List<Item> itemList ;

	Items(String sortOrder, List<Item> item){
		this.sortOrder = sortOrder;
		this.itemList = item;
	}
	
	private void sort(){
		List<IntegerItem> integers = getIntegerItems();
		List<StringItem> strings = getStringItems();
		List<Item> it = new ArrayList<Item>();
		
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
		itemList = it;
	}
	
	public String serialize(){
		StringBuilder itemsString = new StringBuilder();	
		sort();
		
		itemsString.append("<item>\n");
		for (Item it : itemList) {
			itemsString.append("\t"+it.serialize()+"\n");
		}
		itemsString.append("</items>\n");
		return itemsString.toString();
	}
	
	private List<IntegerItem> getIntegerItems(){
		List<IntegerItem> intItem = new ArrayList<IntegerItem>();
		
		for (Item it : itemList) {
			if(it.getClass().equals(IntegerItem.class)){
				intItem.add((IntegerItem) it);
			}
		}
		
		return intItem;
	}
	
	private List<StringItem> getStringItems(){
	List<StringItem> stringItem = new ArrayList<StringItem>();
		
		for (Item it : itemList) {
			if(it.getClass().equals(StringItem.class)){
				stringItem.add((StringItem) it);
			}
		}
		
		return stringItem;
	}
}

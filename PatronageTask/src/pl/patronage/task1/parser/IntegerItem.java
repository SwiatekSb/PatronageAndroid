package pl.patronage.task1.parser;

public class IntegerItem extends Item implements Comparable<IntegerItem>{

	private Integer value;
	
	public IntegerItem(Integer value) {
		this.value = value;	
	}
	
	@Override
	String serialize() {
		return "<item type=" + getType() + ">" + value + "</item>";	
	}
	
	@Override
	String getType() {
		return XmlConstans.ATTRIBUTE_VALUE_INTEGER;
	}
	
	public static Item deserialize(String text){
		Integer val = null;
		try{
			val = Integer.parseInt(text);
		}catch(NumberFormatException e){
			return new StringItem(text);
		}
		
		return new IntegerItem(val);
	}
	
	@Override
	public int compareTo(IntegerItem another) {
		return value.compareTo(another.value);
	}




}

package pl.patronage.task1.parser;

public class StringItem extends Item{

	private String value;
	
	public StringItem(String value) {
		this.value = value;
	}
	
	@Override
	String serialize() {
		return "<item type=" + getType() + ">" + value + "</item>";
	}

	@Override
	String getType() {
		return XmlConstans.ATTRIBUTE_VALUE_STRING;
	}

	public static Item deserialize(String text) {
		return new StringItem(text);
	}

}

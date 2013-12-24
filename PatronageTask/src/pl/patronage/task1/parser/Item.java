package pl.patronage.task1.parser;

abstract class Item extends XmlObject{

	Object value;
	
	abstract String getType();
	
	/**
	 * Delegates deserialize work to specific Item.
	 * @param text 
	 * @param type 
	 * @return Deserialized StringItem or IntegerItem depends on type. Null if type 
	 * is not recognized.
	 */
	static XmlObject deserialize(String text, String type){
		
		if(type.equals(XmlConstans.ATTRIBUTE_VALUE_INTEGER)){	
			return IntegerItem.deserialize(text);
		}else if(type.equals(XmlConstans.ATTRIBUTE_VALUE_STRING)){
			return StringItem.deserialize(text);
		}
		return null;
	}
}

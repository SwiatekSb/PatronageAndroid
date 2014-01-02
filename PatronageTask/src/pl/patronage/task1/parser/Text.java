package pl.patronage.task1.parser;

public class Text extends XmlObject{

	private String text;
	
	public Text(String text){
		this.text = text;
	}

	@Override
	String serialize() {
		return "<"+XmlConstans.TAG_TEXT +">" + text + "</"+XmlConstans.TAG_TEXT +">";
	}

	@Override
	public String toString() {
		return text;
	}

	public static XmlObject deserialize(String text){
		return new Text(text);
	}

}

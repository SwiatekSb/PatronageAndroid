package pl.patronage.task1.parser;

public class Text extends XmlObject{

	private String text;
	
	public Text(String text){
		this.text = text;
	}

	@Override
	String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public static XmlObject deserialize(String text){
		return new Text(text);
	}
}
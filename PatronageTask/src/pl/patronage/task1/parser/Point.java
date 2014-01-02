package pl.patronage.task1.parser;

public class Point extends XmlObject{

	private Float x;
	private Float y;
	
	public Point(Float x, Float y){
		this.x = x;
		this.y = y;
	}

	@Override
	String serialize() {
		return "<" + XmlConstans.TAG_POINT +">\n" + 
					"\t<"+XmlConstans.TAG_X + ">" + x + "</" + XmlConstans.TAG_X + ">\n" +
					"\t<"+XmlConstans.TAG_Y + ">" + y + "</" + XmlConstans.TAG_Y + ">\n" +
				"</"+XmlConstans.TAG_POINT +">";
	}
	
	public static XmlObject deserialize(String x_val, String y_val){
		Float x = null, y = null;
		
		try{
			x = Float.parseFloat(x_val);
			y = Float.parseFloat(y_val);
		}catch(NumberFormatException e){
			//bad format of point value
			e.printStackTrace();
		}
		return new Point(x,y);
	}
	
	public Float getPointX(){
		return x;
	}
	
	public Float getPointY(){
		return y;
	}

}

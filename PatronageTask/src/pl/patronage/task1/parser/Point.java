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
		// TODO Auto-generated method stub
		return null;
	}
	
	public static XmlObject deserialize(Float x, Float y){
		return new Point(x,y);
	}
	
	public Float getPointX(){
		return x;
	}
	
	public Float getPointY(){
		return y;
	}

}

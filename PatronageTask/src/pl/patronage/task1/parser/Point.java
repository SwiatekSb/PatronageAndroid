package pl.patronage.task1.parser;

public class Point extends XmlObject{

	private float x;
	private float y;
	
	public Point(float x, float y){
		this.x = x;
		this.y = y;
	}

	@Override
	String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static XmlObject deserialize(float x, float y){
		return new Point(x,y);
	}
	
	public float getPointX(){
		return x;
	}
	
	public float getPointY(){
		return y;
	}

}

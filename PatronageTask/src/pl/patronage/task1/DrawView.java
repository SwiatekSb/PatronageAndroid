package pl.patronage.task1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View{
	
    private Paint paint = new Paint();
    private float[] points;

    public DrawView(Context context){
    	super(context);
    }
    
    public DrawView(Context context, float[] points) {
        super(context);  
        this.points = points;
    }

    @Override
    public void onDraw(Canvas canvas) {
    	canvas.drawPoints(points, paint);
    	paint.setColor(Color.BLACK);
    }
}

package pl.patronage.task1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View{
    Paint paint = new Paint();
    private float[] mPts;

    public DrawView(Context context, float[] mPts) {
        super(context);  
        this.mPts = mPts;
    }

    @Override
    public void onDraw(Canvas canvas) {
    	
    	canvas.drawPoints(mPts, paint);
    	paint.setColor(Color.BLACK);
    }
}

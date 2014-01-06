package pl.patronage.task1;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TextAdapater extends ArrayAdapter<String> {

	private Activity context;
	private int layoutResourceId;
	private String[] texts;
	
	public TextAdapater(Activity context, int layoutResourceId, String[] texts){
		super(context,layoutResourceId,texts);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.texts = texts;
	}
	
	static class ViewHolder{
		public TextView text;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View row = convertView;
		 ViewHolder viewHolder = null;
		
		if(row == null){
			LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
			
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) row.findViewById(R.id.textView);
            
            viewHolder.text.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					TextView tv = (TextView) v;
					
					AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
					dialogBuilder.setMessage(tv.getText().toString());
		
					AlertDialog alertDialog = dialogBuilder.create();
					alertDialog.show();
				}
			});
                
            row.setTag(viewHolder);
            
		}else{
			
			viewHolder = (ViewHolder) row.getTag();
		}
		
		String string = texts[position];
		viewHolder.text.setText(string);
		
		return row;
	}
}

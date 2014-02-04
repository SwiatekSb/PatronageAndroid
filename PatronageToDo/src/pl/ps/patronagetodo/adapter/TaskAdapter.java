package pl.ps.patronagetodo.adapter;

import java.util.List;

import pl.ps.patronagetodo.R;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task>{
	
	private Activity context;
	private List<Task> tasksList;
	private Handler uiHandler = new Handler();
	
    public TaskAdapter(Activity context, List<Task> tasksList) {
        super(context, pl.ps.patronagetodo.R.layout.task_layout, tasksList);
        this.context = context;
        this.tasksList = tasksList;   
    }
    
    static class ViewHolder {
    	public ImageView imgvStatus;
    	public TextView txtvDecription;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	  ViewHolder viewHolder = null;  
    	  
    	  if ( convertView == null ) {
    		  
              LayoutInflater layoutInflater = context.getLayoutInflater();
              convertView = layoutInflater.inflate(pl.ps.patronagetodo.R.layout.task_layout, null, true);
              
              viewHolder = new ViewHolder();
              
              viewHolder.imgvStatus = (ImageView) convertView.findViewById(pl.ps.patronagetodo.R.id.imgvStatus);
              viewHolder.txtvDecription = (TextView) convertView.findViewById(pl.ps.patronagetodo.R.id.txtvDescription);
              
            

              convertView.setTag(viewHolder);
              
    	  } else {
    		  viewHolder = (ViewHolder) convertView.getTag();
    	  }
    	  Task task = tasksList.get(position);
    	  viewHolder.txtvDecription.setText(task.getDescription() + "");
    	  if (task.getStatus() == 1) {  
        	  viewHolder.imgvStatus.setImageResource(R.drawable.icon_done);
          }else {
        	  viewHolder.imgvStatus.setImageResource(R.drawable.icon_not_done);
          }
    	  
    	  return convertView;
    }
    
    public void refill(List<Task> tasks) {
    	tasksList.clear();
    	tasksList.addAll(tasks);
    	uiHandler.post(new Runnable() {
			
			@Override
			public void run() {
				notifyDataSetChanged();
			}
		});
    	
    }

}

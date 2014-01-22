package pl.ps.patronagetodo;

import java.util.ArrayList;
import java.util.List;

import pl.ps.patronagetodo.adapter.Task;
import pl.ps.patronagetodo.adapter.TaskAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TaskAdapter taskAdapter;
	private ListView lvTask;
	private List<Task> tasksList;
	
	private ImageView imgvAddTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setupUi();
		setupListeners();
	}
	
	public void setupUi() {
		
		lvTask = (ListView) findViewById(R.id.lvTasks);
		imgvAddTask = (ImageView) findViewById(R.id.imgvAddButton);
		
		registerForContextMenu(lvTask);
		
		tasksList = new ArrayList<Task>();
		tasksList.add(new Task());
		taskAdapter = new TaskAdapter(this, tasksList);
		lvTask.setAdapter(taskAdapter);
	}
	
	public void setupListeners() {
		imgvAddTask.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createAlertDialog();
			}
		});
		
		lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODOif not done - check database
				TextView temp = (TextView) arg0.findViewById(R.id.txtvDescription);
				
				
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
				TextView tvTitle = new TextView(MainActivity.this);
				tvTitle.setText(getString(R.string.new_task));
				tvTitle.setGravity(Gravity.CENTER);
				tvTitle.setTextSize(20);
//				dialogBuilder.setTitle(getString(R.string.new_task));
				dialogBuilder.setCustomTitle(tvTitle);
				
				dialogBuilder.setMessage(temp.getText() + "");
				
				dialogBuilder.setNegativeButton(getString(R.string.task_done), new DialogInterface.OnClickListener() {	
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//TODO Update task - completed
					
					
					}
				});
				dialogBuilder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
			
				showAlertDialog(dialogBuilder);
			}
		});
	}
	
	
	private void createAlertDialog() {
		
		LayoutInflater inflate = LayoutInflater.from(MainActivity.this);
		final View view = inflate.inflate(R.layout.dialog_layout, null);
		final EditText editText = (EditText) view.findViewById(R.id.etEnterText);
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
		
		TextView tvTitle = new TextView(MainActivity.this);
		tvTitle.setText(getString(R.string.new_task));
		tvTitle.setGravity(Gravity.CENTER);
		tvTitle.setTextSize(20);
//		dialogBuilder.setTitle(getString(R.string.new_task));
		dialogBuilder.setCustomTitle(tvTitle);
		dialogBuilder.setView(view);
		
		dialogBuilder.setNegativeButton(getString(R.string.add_new_task), new DialogInterface.OnClickListener() {	
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO Add new Task			
			}
		});
		dialogBuilder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	
		showAlertDialog(dialogBuilder);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lvTasks) {
	  
			menu.add("Zakoñcz zadanie");
			menu.add("Usuñ zadanie");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * Method create and show AlertDialog
	 * @param dialogBuilder
	 */
	private void showAlertDialog(AlertDialog.Builder dialogBuilder){	
		AlertDialog alertDialog = dialogBuilder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
	}
	/**
	 * Method show Message on Top of screen
	 * @param toast
	 */
	private void showToast(Toast toast) {
		toast.setGravity(Gravity.TOP, 0, 60);
		toast.show();
	}

}

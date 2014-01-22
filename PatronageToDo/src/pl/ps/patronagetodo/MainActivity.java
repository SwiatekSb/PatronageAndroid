package pl.ps.patronagetodo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.ps.patronagetodo.adapter.Task;
import pl.ps.patronagetodo.adapter.TaskAdapter;
import pl.ps.patronagetodo.manage.DataManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TaskAdapter taskAdapter;
	private ListView lvTask;
	public List<Task> tasksList;
	
	private ImageView imgvAddTask;
	private DataManager dateManager;
	
	private SimpleDateFormat dateFormat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		setup();
		setupListView();
		setupListeners();
	}
	
	/**
	 * Method initzialized all variables
	 */
	public void setup() {
		
		dateManager = DataManager.getInstance(this);
		lvTask = (ListView) findViewById(R.id.lvTasks);
		imgvAddTask = (ImageView) findViewById(R.id.imgvAddButton);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tasksList = new ArrayList<Task>();
			
	}
	
	private void setupListView() {
		
		registerForContextMenu(lvTask);	
	
		dateManager.getAllTaskFromDb(new DataManager.OnDataLoadedListener() {
			
			@Override
			public void onDataLoaded(List<Task> task) {
				tasksList = task;
				taskAdapter = new TaskAdapter(MainActivity.this, tasksList);
				lvTask.setAdapter(taskAdapter);
			}
		});
	}
	

	/**
	 * Method init all Listeners
	 */
	public void setupListeners() {
		
		imgvAddTask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createDialog();
			}
		});
		
		lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long position) {
				TextView temp = (TextView) arg0.findViewById(R.id.txtvDescription);
				
				final Task tasks = tasksList.get(arg2);
		
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
				TextView tvTitle = new TextView(MainActivity.this);
				tvTitle.setText(getString(R.string.end));
				tvTitle.setGravity(Gravity.CENTER);
				tvTitle.setTextSize(20);

				dialogBuilder.setCustomTitle(tvTitle);
				dialogBuilder.setMessage(tasks.getDescription() + "");
				dialogBuilder.setNegativeButton(getString(R.string.task_done), new DialogInterface.OnClickListener() {	
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Date date = new Date();
 
                        Task task = new Task(tasks.getId(),tasks.getDescription(), dateFormat.format(date), 1);
                        dateManager.updateTaskInDb(task);
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
	
	/**
	 * Method create dialog which add task 
	 */
	 private void createDialog() {
		 	String title = getString(R.string.new_task);
		 	
	        final Dialog dialog = new Dialog(this);
	        dialog.setContentView(R.layout.dialog_layout);
	        dialog.setTitle(title);
	 
	        final EditText etDescription = (EditText) dialog.findViewById(R.id.etSignature);
	        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
	        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
	 
	        btnCancel.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dialog.dismiss();
	            }
	        });
	        
	        btnOk.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                if (etDescription != null) {
	                    Editable etDescEditable = etDescription.getText();
	                   
	                    if (etDescEditable != null) {
	                        String description = etDescEditable.toString();
	                        Date date = new Date();
                            
                            Task task = new Task(0,description, dateFormat.format(date), 0);
                            dateManager.addTaskToDb(task);
                            
                            updateTaskList();
                            
                            dialog.dismiss();
	                    }
	                }
	            }
	        });
	        dialog.show();
	    }
	private void updateTaskList(){
		
		dateManager.getAllTaskFromDb(new DataManager.OnDataLoadedListener() {
			
			@Override
			public void onDataLoaded(List<Task> task) {
				tasksList = task;
				taskAdapter = new TaskAdapter(MainActivity.this, tasksList);
				lvTask.setAdapter(taskAdapter);
				taskAdapter.notifyDataSetChanged();
			}
		});
	
	}
	 
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lvTasks) {
			menu.add(getString(R.string.end_task));
			menu.add(getString(R.string.delete_task));
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

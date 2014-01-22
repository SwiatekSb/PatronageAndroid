package pl.ps.patronagetodo.manage;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.ps.patronagetodo.adapter.Task;
import pl.ps.patronagetodo.database.DbHelper;
import android.content.Context;

public class DataManager {

	private static DataManager dataManager;
	
	private DbHelper mDbHelper;
	private Executor executor;
	
	private DataManager(Context context) {
		mDbHelper = new DbHelper(context);
		mDbHelper.openDateBase();
		
		executor = Executors.newFixedThreadPool(5);
	}
	
	public static DataManager getInstance(Context context) {
		if (dataManager != null) {
			dataManager = new DataManager(context);
		}
		
		return dataManager;
	}
	
	public void addTaskToDb(final Task task) {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				mDbHelper.addTask(task);		
			}
			
		};
		executor.execute(runnable);
	}
		
	
	public void updateTaskInDb(final Task task) {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				mDbHelper.updateTask(task);		
			}
			
		};
		executor.execute(runnable);
	}
	
	public void deleteTaskInDb(final Task task) {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				mDbHelper.deleteTask(task);	
			}
			
		};
		executor.execute(runnable);
	}
	
	public ArrayList<Task> getAllTaskFromDb(Task task) {
		return mDbHelper.getAllTask();
	}
	
	public Task getTaskByIdFromDb(String id) {
		return mDbHelper.getTaskById(id);
	}
	
}

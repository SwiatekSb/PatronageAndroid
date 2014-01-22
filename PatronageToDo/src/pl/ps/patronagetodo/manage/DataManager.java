package pl.ps.patronagetodo.manage;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.ps.patronagetodo.adapter.Task;
import pl.ps.patronagetodo.database.DbHelper;
import android.content.Context;

public class DataManager {
	
	public interface OnDataLoadedListener {
		void onDataLoaded(List<Task> task);
	}
	
	public interface OnTaskLoadedListener {
		void onTaskLoaded(Task task);
	}

	private static DataManager dataManager;
	
	private DbHelper mDbHelper;
	private Executor executor;
	
	private DataManager(Context context) {
		mDbHelper = new DbHelper(context);
		mDbHelper.openDateBase();
		executor = Executors.newFixedThreadPool(5);
	}
	
	public static DataManager getInstance(Context context) {
		if (dataManager == null) {
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
	
	public void getAllTaskFromDb(final OnDataLoadedListener listener) {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				if (listener != null) {
					listener.onDataLoaded(mDbHelper.getAllTask());
				}
			}
		};
		executor.execute(runnable);
	}
	
	public void getTaskByIdFromDb(final OnTaskLoadedListener listener,final String id) {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				if (listener != null) {
					listener.onTaskLoaded(mDbHelper.getTaskById(id));
				}
			}
		};
		executor.execute(runnable);
		
	}
	
}

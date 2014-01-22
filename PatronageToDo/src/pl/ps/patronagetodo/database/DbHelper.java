package pl.ps.patronagetodo.database;

import java.util.ArrayList;

import pl.ps.patronagetodo.adapter.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "task.sqlite";
	public static final String DB_TASK_TABLE = "Task";
	
	public static final String CREATE_TASK_TABLE = "create table"
			+ DB_TASK_TABLE + "(" +
			TaskDB.TaskTable.Column.ID +			"integer primary key autoincrement, " +
			TaskDB.TaskTable.Column.DESCRPTION +	"TEXT NOT NULL, " +
			TaskDB.TaskTable.Column.DATE +			"DATETIME DEFAULT CURRENT_TIMESTAMP, " +
			TaskDB.TaskTable.Column.STATUS +		"INTEGER DEFAULT 0);";
	
	private SQLiteDatabase sqliteDB;
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}
	
	public void openDateBase() throws SQLiteException {
		if (sqliteDB != null) {
			sqliteDB.close();
		}
		
		sqliteDB = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatebase) {
		sqLiteDatebase.execSQL(CREATE_TASK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatebase, int oldVersion, int newVersion) {
		sqLiteDatebase.execSQL("DROP TABLE IF EXISTS " + DB_TASK_TABLE);
		onCreate(sqLiteDatebase);
	}
	/**
	 * Method add Task to database 
	 * @param task 
	 * @return id of insert task
	 */
	public long addTask(Task task) {
		ContentValues values = new ContentValues();
		
		values.put(TaskDB.TaskTable.Column.ID, task.getId());
		values.put(TaskDB.TaskTable.Column.DESCRPTION, task.getDescription());
		values.put(TaskDB.TaskTable.Column.DATE, task.getDate());
		values.put(TaskDB.TaskTable.Column.STATUS, task.getStatus());
		
		long id = sqliteDB.insert(DB_TASK_TABLE, null, values);
		Log.d("DATEBASE", "Added new task id: " + id);
		return id;
	}
	/**
	 * Method update task
	 * @param task
	 */
	public void updateTask(Task task) {
		ContentValues values = new ContentValues();
		
		values.put(TaskDB.TaskTable.Column.DESCRPTION, task.getDescription());
		values.put(TaskDB.TaskTable.Column.DATE, task.getDate());
		values.put(TaskDB.TaskTable.Column.STATUS, task.getStatus());
		
		sqliteDB.update(DB_TASK_TABLE, values, TaskDB.TaskTable.Column.ID + " = " + task.getId(), null);
		
		Log.d("DATEBASE", "Update task id: " + task.getId());
	}
	/**
	 * Method delete task
	 * @param task
	 */
	public void deleteTask(Task task) {
		sqliteDB.delete(DB_TASK_TABLE, TaskDB.TaskTable.Column.ID + " = " + task.getId(), null);
	}
	/**
	 * Method add all task to array and return all
	 * @return array of tasks
	 */
	public ArrayList<Task> getAllTask() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		Cursor cursor = sqliteDB.query(DB_TASK_TABLE, null, null, null, null, null, null);
		
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					
					Task taskToAdd = new Task();
					taskToAdd.setId(Long.parseLong(cursor.getString(TaskDB.TaskTable.ColumnID.ID)));
					taskToAdd.setDescription(cursor.getString(TaskDB.TaskTable.ColumnID.DESCRIPTION));
					taskToAdd.setDate(cursor.getString(TaskDB.TaskTable.ColumnID.DATE));
					taskToAdd.setStatus(Integer.parseInt(cursor.getString(TaskDB.TaskTable.ColumnID.STATUS)));
					
					tasks.add(taskToAdd);
					
				} while (cursor.moveToNext());
			}
		}
		return tasks;
	}
	/**
	 * Method find task by id and return it
	 * @param id of task
	 * @return task if exists or return null
	 */
	public Task getTaskById(String id){
		Task task = null;
		
		Cursor cursor = sqliteDB.query(DB_TASK_TABLE, null, TaskDB.TaskTable.Column.ID + " = " + id, null, null, null, null);
		
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					
					task = new Task();
					task.setId(Long.parseLong(cursor.getString(TaskDB.TaskTable.ColumnID.ID)));
					task.setDescription(cursor.getString(TaskDB.TaskTable.ColumnID.DESCRIPTION));
					task.setDate(cursor.getString(TaskDB.TaskTable.ColumnID.DATE));
					task.setStatus(Integer.parseInt(cursor.getString(TaskDB.TaskTable.ColumnID.STATUS)));
					
				} while (cursor.moveToNext());
			}
		}
		return task;
		
	}
	
}

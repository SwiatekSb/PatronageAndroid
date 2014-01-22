package pl.ps.patronagetodo.manage;

import android.content.Context;
import pl.ps.patronagetodo.adapter.Task;

public class TaskRunnableFactory {


	public static Runnable addTask( Task task) {
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
					
			
			}
		};
		
		
		return runnable;
	}
	
	public static  Runnable deleteTask(Task task) {
		
		
		 Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		return runnable;
	}
	
	public static Runnable updateTask(Task task) {
		 Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		return runnable;
	}
	
	public static Runnable getAllTask(Task task) {
		 Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		return runnable;
	}
	
}

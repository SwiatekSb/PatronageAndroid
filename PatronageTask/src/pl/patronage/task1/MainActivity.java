/**
 * Patronage Zadanie 1 - Android
 * @author Piotrek Swiatowski
 * 
 */
package pl.patronage.task1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import pl.patronage.task1.manager.FileManager;
import pl.patronage.task1.parser.DataXmlParser;
import pl.patronage.task1.parser.Items;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.ArrayUtils;

public class MainActivity extends Activity {

	//private Button btStart;
	private FileManager fmanager;
	private String error;
	
	LinearLayout ll;
	DrawView drawView;
	
	private List<Items> items;
	private ManageXmlTask taskManager = null;
	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		initVariables();
	}
	
	private void initVariables() {
		fmanager = new FileManager();
		progress = new ProgressDialog(MainActivity.this);
		ll = (LinearLayout) findViewById(R.id.layoutDrawView);
	//	btStart = (Button) findViewById(R.id.btStart);
	//	btStart.setOnClickListener(new OnClickListener() {
	//		@Override
	//		public void onClick(View v) {
	//			if(checkSDAvailability()){
					//start processing
	//				taskManager = new ManageXmlTask();
	//				taskManager.execute();		
	//			}
	//		}
	//	});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.press_button:
			if(checkSDAvailability()){
				taskManager = new ManageXmlTask();
				taskManager.execute();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class ManageXmlTask extends AsyncTask<Void, String, Void>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			progress.setCancelable(false);			
			progress.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			try{
				publishProgress(getString(R.string.read_and_parse));
				loadData();
				if(isCancelled())return null;
				//publishProgress(getString(R.string.save_file));
			//	saveData();
			}catch(Exception e){
				progress.dismiss();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			progress.setMessage(values[0]);
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			showToast(Toast.makeText(MainActivity.this, getString(R.string.succes_process), Toast.LENGTH_SHORT));
				
			//////////////////////////////////////////////////////////////
			//******* list point do tablicy, potem przeslac do funkcji onDraw i dodac widok
			/////////////////////////////////////////////////////////////////
			float [] numb;
			List<Float> numbers = new ArrayList<Float>();
			for (Items it : items) {
				numbers.addAll(it.getPointList());
			}
			
			numb = ArrayUtils.toPrimitive(numbers.toArray(new Float[numbers.size()]));
			
			drawView = new DrawView(MainActivity.this,numb);
			ll.addView(drawView);
			//////////////////////////////////////////////////////////////////////	
		}
		@Override
		protected void onCancelled() {
			super.onCancelled();
			progress.dismiss();
			if(!error.equals(getString(R.string.file_not_found))){
				showToast(Toast.makeText(MainActivity.this,getString(R.string.error)+ error,  Toast.LENGTH_LONG));
			}else{	
				createAlertDialog();
			}
		}
	}
	/**
	 * 
	 */
	private void createAlertDialog() {	
		final CharSequence[] options = {getString(R.string.generate_file), getString(R.string.locate_file), getString(R.string.abandon)};
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
		dialogBuilder.setTitle(getString(R.string.file_not_found));
		dialogBuilder.setItems(options,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					InputStream is = MainActivity.this.getResources().openRawResource(R.raw.in);
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					try {
						fmanager.copyFile("/patronage/task1","in.xml",br);
					}catch (IOException e) {
						showToast(Toast.makeText(getBaseContext(),getString(R.string.not_in_raw), Toast.LENGTH_SHORT));
						e.printStackTrace();
						return;
					}
					 showToast(Toast.makeText(getBaseContext(),getString(R.string.save_sample), Toast.LENGTH_SHORT));
					return;
				case 1:
					Intent intent_load_file = new Intent();
					intent_load_file.setType("text/plain");
					intent_load_file.setAction(Intent.ACTION_GET_CONTENT); 
					startActivityForResult(Intent.createChooser(intent_load_file,"Wybierz plik"), 2);
					return;
				case 2:
					dialog.dismiss();
					return;
				}
				
			}
		} );
		
		AlertDialog alertDialog = dialogBuilder.create();
		alertDialog.show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == 2){
				String FilePath = data.getData().getPath();
				if(FilePath != null){
					File f = new File(FilePath);
					try {
						BufferedReader br = new BufferedReader(new FileReader(f));
					
							if(FilePath.contains(".xml")){
								fmanager.copyFile("/patronage/task1","in.xml",br);
								showToast(Toast.makeText(getBaseContext(),getString(R.string.save_sample), Toast.LENGTH_SHORT));
							}else{
								showToast(Toast.makeText(getBaseContext(),getString(R.string.is_not_xml), Toast.LENGTH_SHORT));
							}
						}catch(IOException e){
							e.printStackTrace();
							showToast(Toast.makeText(getBaseContext(),getString(R.string.is_error), Toast.LENGTH_SHORT));
					} 
				}
			}
		}
	}

	private void saveData() {
		if(checkSDAvailability()){	
			try {
					fmanager.saveXmlFileSDCard("/patronage/task1","out.xml", items);
				} catch (IOException e) {
					e.printStackTrace();
					
				}	
		}
	}
	/**
	 * 
	 * @param in
	 */
	private void parseData(FileInputStream in) {
		//parse load data
		DataXmlParser dataXmlParser = new DataXmlParser();
		try {
			items = dataXmlParser.parse(in);
		}  catch (Exception e) {
			//foreach Exception - XMLPullParserException and IOException
			e.printStackTrace();
			handleParserExceptions(in);
			return;
		}
		
	
	}
	

	private void loadData() {
		
		FileInputStream in = null;
		//load file from SD Card 
		try {
			in = fmanager.loadFileSDCard("/patronage/task1","in.xml");	
			parseData(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		    error = getString(R.string.file_not_found);
			taskManager.cancel(true);
		} catch (IOException e) {
			e.printStackTrace();
			  error = getString(R.string.file_not_found);
				taskManager.cancel(true);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				//file must be used by other stuff
			}
		}
		
		
	}
	
	private void handleParserExceptions(FileInputStream fIn) {
		try {
			fIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		error = getString(R.string.error_parse);
		taskManager.cancel(true);
	}
	/**
	 * 
	 * @return - status of External Storage State
	 * 		   : true if SD Card is Available
	 */
	private boolean checkSDAvailability() {
		
		boolean success = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		
		if(!success){
			showToast(Toast.makeText(getApplicationContext(), getString(R.string.sd_not_mounted), Toast.LENGTH_SHORT));
		}
		return success;
	}	

	private void showToast(Toast toast) {
		toast.setGravity(Gravity.TOP, 0, 60);
		toast.show();
	}

}

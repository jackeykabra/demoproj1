/*package com.android.workingday;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.workingday.sqlite.CustomHolidaysDb;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class CustomCalendar extends Activity {
	
	  //private static final String TAG = "SampleTimesSquareActivity";
	  private CalendarPickerView calendar;
	  List<Date> datesFinal = new ArrayList<Date>();
	  CustomHolidaysDb chd;
	  Calendar nextYear, lastYear;
	  SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd yyyy");
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_custom_cal);

	    chd = new CustomHolidaysDb(CustomCalendar.this);
	    
	    nextYear = Calendar.getInstance();
	    nextYear.add(Calendar.YEAR, 1);

	    lastYear = Calendar.getInstance();
	    lastYear.add(Calendar.YEAR, -1);

	    calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
	    calendar.init(lastYear.getTime(), nextYear.getTime()) //
	        .inMode(SelectionMode.SINGLE) //
	        .withSelectedDate(new Date());


	    findViewById(R.id.done_button).setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View view) {
	        //Log.d(TAG, "Selected time in millis: " + calendar.getSelectedDate().getTime());
	        //String toast = "Selected: " + calendar.getSelectedDate().toGMTString();
	        datesFinal = calendar.getSelectedDates();
	        addToDb();
	        //Toast.makeText(CustomCalendar.this, toast, LENGTH_SHORT).show();
	        
	      }
	    });
	    
	  openCalFromDb();
	  
	  Calendar today = Calendar.getInstance();
      ArrayList<Date> dates = new ArrayList<Date>();
      today.add(Calendar.DAY_OF_MONTH, 0);
      dates.add(today.getTime());
      
      calendar.init(new Date(), nextYear.getTime()) //
          .inMode(SelectionMode.MULTIPLE) //
          .withSelectedDates(dates);
	    
	  }
	  
	  private void openCalFromDb() {

			final ProgressDialog dialog;
			dialog = new ProgressDialog(this);
			dialog.setTitle("");
			dialog.setMessage("Loading...");
			dialog.setCancelable(false);
			
			AsyncTask<Void, Void, Boolean> waitForCompletion = new AsyncTask<Void, Void, Boolean>() {

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					dialog.show();
					chd.open();
				}

				@SuppressWarnings("deprecation")
				@Override
				protected Boolean doInBackground(Void... params) {

					try{
						
						Cursor cur;
						ArrayList<Date> dates = new ArrayList<Date>();
		            	Calendar today = Calendar.getInstance();
		            	
		                today.add(Calendar.DAY_OF_MONTH, 0);
		                dates.add(today.getTime());
		                   
		            	cur = chd.ItemList();
		            	startManagingCursor(cur);
		            	cur.moveToFirst();
		            	
		            	while(!cur.isAfterLast())
		            	{
		            		try{
		            			
		            			dates.add(dateformat.parse(cur.getString(cur.getColumnIndex(CustomHolidaysDb.DATE))));
		            			cur.moveToNext();
		            		}catch(Exception e)
		            		{
		            			
		            		}
		            	}
						
		                calendar.init(new Date(), nextYear.getTime()) //
		                    .inMode(SelectionMode.MULTIPLE) //
		                    .withSelectedDates(dates);
		                
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
	          	 
					return null;
				};

				protected void onPostExecute(Boolean result) {
					
					dialog.dismiss();
					chd.close();
				}

			};
			
			waitForCompletion.execute();
		}
	  
	  private void addToDb() {

			final ProgressDialog dialog;
			dialog = new ProgressDialog(this);
			dialog.setTitle("");
			dialog.setMessage("Loading...");
			dialog.setCancelable(false);
			
			AsyncTask<Void, Void, Boolean> waitForCompletion = new AsyncTask<Void, Void, Boolean>() {

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					dialog.show();
					chd.open();
					chd.emptyTable();
				}

				@Override
				protected Boolean doInBackground(Void... params) {

					int n = datesFinal.size();
					//Cursor cur;
					
					
					for(int i=0;i<n;i++)
					{
						StringBuilder dateS = new StringBuilder(dateformat.format(datesFinal.get(i)));
				        
						chd.insertItem(dateS.toString());
					}
					
					return null;
				};

				protected void onPostExecute(Boolean result) {
					
					dialog.dismiss();
					chd.close();
					finish();
					overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
				}

			};
			
			waitForCompletion.execute();
		}
	}*/
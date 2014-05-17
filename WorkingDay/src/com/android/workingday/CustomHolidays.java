package com.android.workingday;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class CustomHolidays  extends Activity {
	
	private Button mBackBtn, doneBtn, resetBtn;
	private EditText mEnterButton1, mEnterButton2;
	Calendar todayC, date1, date2, date3;
    static final int DATE_PICKER_ID_0 = 0; 
    static final int DATE_PICKER_ID_1 = 1;
    String toIntent;
    SimpleDateFormat dateformat = new SimpleDateFormat("dd MMMM yyyy");
    private int day, month, year;
    boolean bool;
    SharedPreferences handle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customholiday);
		
		initWidget();
		
		mEnterButton1.setOnTouchListener(new OnTouchListener()
	    {
	        @SuppressWarnings("deprecation")
			public boolean onTouch(View v, MotionEvent event)
	        {
	            showDialog(DATE_PICKER_ID_0);
	            return false;
	        }
	    });
		
		mEnterButton2.setOnTouchListener(new OnTouchListener()
	    {
	        @SuppressWarnings("deprecation")
			public boolean onTouch(View v, MotionEvent event)
	        {
	        	showDialog(DATE_PICKER_ID_1);
	        	bool = true;
	            return false;
	        }
	    });
		
		mBackBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				onBackPressed();
				
			}
		});
		
		doneBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!mEnterButton1.getText().toString().trim().equals("") && !mEnterButton2.getText().toString().trim().equals(""))
	            {
	            	bool = false;
	            	int n = (int) ((date2.getTime().getTime() - date1.getTime().getTime()) / (1000 * 60 * 60 * 24));
	            		
	            	if(n>=0)
	            	{
	            		addToDb();
	            		finish();
	            		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	            	}
	            		
	            	else
	            	Toast.makeText(getApplicationContext(), "End Date must be later than the Start Date", Toast.LENGTH_SHORT).show();
	            }
	            else
	            {
	            	onBackPressed();
	            }
	           
			}
		});
		
		resetBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mEnterButton1.setText("");
				mEnterButton2.setText("");
				
				// Empty SharedPreference
				Editor custom_holidays = handle.edit();
				custom_holidays.clear();
				custom_holidays.commit();
			}
		});
	}
	
	private void initWidget() {
		// TODO Auto-generated method stub
		mBackBtn = (Button) findViewById(R.id.buttonback);
		mEnterButton1 = (EditText) findViewById(R.id.editText1);
		mEnterButton2 = (EditText) findViewById(R.id.editText2);
		
		doneBtn = (Button) findViewById(R.id.done);
		resetBtn = (Button) findViewById(R.id.reset);
		
		mEnterButton1.setInputType(InputType.TYPE_CLASS_DATETIME);
		mEnterButton2.setInputType(InputType.TYPE_CLASS_DATETIME);
		
		handle = getApplicationContext().getSharedPreferences("CUSTOM_HOLIDAYS", 0);
		//chd = new CustomHolidaysDb(CustomHolidays.this);
		
		//SharedPreferences handle = getApplicationContext().getSharedPreferences("SETTING_SCREEN", 0);
		long startL = handle.getLong("START_DATE", 0);
		long endL = handle.getLong("END_DATE", 0);
		
		if(startL>0 && endL>0)
		{
			Date start = new Date(handle.getLong("START_DATE", 0));
			Date end = new Date(handle.getLong("END_DATE", 0));
			
			mEnterButton1.setText(new StringBuilder(dateformat.format(start.getTime())).toString());
			mEnterButton2.setText(new StringBuilder(dateformat.format(end.getTime())).toString());
			
			date1 = Calendar.getInstance();
			date1.setTime(start);
			  
			date2 = Calendar.getInstance();
			date2.setTime(end);
		}
	}
	
	private void addToDb() {

		AsyncTask<Void, Void, Boolean> waitForCompletion = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				/*chd.open();
				chd.emptyTable();*/
				Editor custom_holidays = handle.edit();
				custom_holidays.clear();
				custom_holidays.commit();
		        
			}

			@Override
			protected Boolean doInBackground(Void... params) {

				/*int n = (int) ((date2.getTime().getTime() - date1.getTime().getTime()) / (1000 * 60 * 60 * 24));
				date3 = (Calendar) date1.clone();
				
				for(int i=0;i<(n+1);i++)
				{
					StringBuilder dateS = new StringBuilder(dateformat.format(date3.getTime()));
					chd.insertItem(dateS.toString());
					
					date3.add(Calendar.DATE, 1);
				}*/
				
				//SharedPreferences Setting = getApplicationContext().getSharedPreferences("SETTING_SCREEN", 0); // 0 - for private mode
				
				Editor custom_holidays = handle.edit();
				custom_holidays.putLong("START_DATE", date1.getTime().getTime());
				custom_holidays.putLong("END_DATE", date2.getTime().getTime());
				custom_holidays.commit();
		        
				return null;
			};

			protected void onPostExecute(Boolean result) {
					
				//chd.close();
			}

		};
		
		waitForCompletion.execute();
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_PICKER_ID_0:
        	SetDate(0);
            return new DatePickerDialog(this, pickerListener, year, month,day);
            
        case DATE_PICKER_ID_1:
        	SetDate(1);
            return new DatePickerDialog(this, pickerListener1, year, month,day);
            
        }
        return null;
    }
	
	
	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
		 
        // when dialog box is closed, below method will be called.
       	@Override
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int dayOfMonth) {
			// TODO Auto-generated method stub
			year  = selectedYear;
            month = selectedMonth;
            day   = dayOfMonth;
           
            date1 = Calendar.getInstance();
            date1.set(Calendar.YEAR, year);
            date1.set(Calendar.MONTH, month);
            date1.set(Calendar.DAY_OF_MONTH, day);
            
            date1.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            date1.set(Calendar.HOUR, 0);            // set hour to midnight
            date1.set(Calendar.MINUTE, 0);                 // set minute in hour
            date1.set(Calendar.SECOND, 0);                 // set second in minute
            date1.set(Calendar.MILLISECOND, 0);
            date1.set(Calendar.AM_PM, 0);
		    
		    String temp = convertDateString(day+"-"+(month+1)+"-"+year);
            // Show selected date
           //mEnterButton1.setText(new StringBuilder().append(month + 1).append("/").append(day).append("/").append(year).append(" "));
		    mEnterButton1.setText(temp);
		    
		}
     };
        
     private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {
		 
         // when dialog box is closed, below method will be called.
        	@Override
 		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int dayOfMonth) {
 			// TODO Auto-generated method stub
 			year  = selectedYear;
            month = selectedMonth;
            day   = dayOfMonth;
             
            date2 = Calendar.getInstance();
            date2.set(Calendar.YEAR, year);
            date2.set(Calendar.MONTH, month);
            date2.set(Calendar.DAY_OF_MONTH, day);

            date2.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            date2.set(Calendar.HOUR, 0);            // set hour to midnight
            date2.set(Calendar.MINUTE, 0);                 // set minute in hour
            date2.set(Calendar.SECOND, 0);                 // set second in minute
            date2.set(Calendar.MILLISECOND, 0);
            date2.set(Calendar.AM_PM, 0);
            
            String temp = convertDateString(day+"-"+(month+1)+"-"+year);
            // Show selected date
           //mEnterButton2.setText(new StringBuilder().append(month + 1).append("/").append(day).append("/").append(year).append(" "));
		    mEnterButton2.setText(temp);
		    
 		}
      };
      
      public String convertDateString(String dateI)
      {
      	SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
      	java.util.Date date = null;
      	
      	try {
  				date = form.parse(dateI);
  		} catch (java.text.ParseException e) {
  				// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
      	
      	SimpleDateFormat postFormater = new SimpleDateFormat("dd MMMMM yyyy");
      	return postFormater.format(date);
      }
      
  	private void SetDate(int n) {
  		// TODO Auto-generated method stub
  		 
         todayC = Calendar.getInstance();
         
         //SharedPreferences handle = getApplicationContext().getSharedPreferences("SETTING_SCREEN", 0);
  		 long startL = handle.getLong("START_DATE", 0);
  		 long endL = handle.getLong("END_DATE", 0);
  		
  		 if(startL>0 && endL>0)
  		 {
  			Date date;
  			 if(n==0)
  				date = new Date(handle.getLong("START_DATE", 0));
  			 
  			 else
  				date = new Date(handle.getLong("END_DATE", 0));
  			
  			//todayC = Calendar.getInstance();
  			todayC.setTime(date);
  		 }
  		 
  		 year  = todayC.get(Calendar.YEAR);
  	     month = todayC.get(Calendar.MONTH);
  	     day   = todayC.get(Calendar.DAY_OF_MONTH);
  		
  	}
  	
  	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		
		finish();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}
}

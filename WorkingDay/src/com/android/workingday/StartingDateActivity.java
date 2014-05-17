package com.android.workingday;

//COMPANY NAME : SILICON IT HUB PVT LTD

//DEVELOPER NAME : Nilay Sheth

//PROJECT NAME : workingday 

//DEVELOPING DATE :21-11-2013

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.workingday.beanclass.HolidayClass;

public class StartingDateActivity extends Activity {

	private Button mBackBtn;
	private EditText mEnterButton1, mEnterButton2;
	TextView tv1, tv2, tv3, tv4;
	private EditText mResult;
	private int day, month, year;
	Calendar todayC, date1, date2, date3;
	int var = 0;
	ProgressDialog pd;
	boolean isSunday, isSaturday, isHolidayWorking, bool;

	static final int DATE_PICKER_ID_0 = 0;
	static final int DATE_PICKER_ID_1 = 1;
	
	String toIntent;
	SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd yyyy");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editdatescreen);

		initWidget();

		toIntent = getIntent().getStringExtra("to");

		mResult.setHint("Result");
		if (toIntent.equals("mEndDate")) {
			mEnterButton1.setHint("Enter Start Date");
			mEnterButton2.setHint("Enter Number of Working Days");
			mEnterButton2.setInputType(InputType.TYPE_CLASS_NUMBER);
			tv1.setText("Start Date");
			tv2.setText("Number of Working Days");
			tv4.setText(R.string.DaysStartDateBtn);
			var = 1;
			
		} else if (toIntent.equals("mBetweenDate")) {
			mEnterButton1.setHint("Enter Start Date");
			mEnterButton2.setHint("Enter End Date");
			mEnterButton2.setInputType(InputType.TYPE_CLASS_DATETIME);
			tv1.setText("Start Date");
			tv2.setText("End Date");
			tv4.setText(R.string.DaysBtTwoDatesBtn);
			var = 2;
			
		} else if (toIntent.equals("mStartingDate")) {
			mEnterButton1.setHint("Enter End Date");
			mEnterButton2.setHint("Enter Number of Working Days");
			mEnterButton2.setInputType(InputType.TYPE_CLASS_NUMBER);
			tv1.setText("End Date");
			tv2.setText("Number of Working Days");
			tv4.setText(R.string.StartWithEndDateBtn);
			var = 3;
			
		}

		mEnterButton1.setInputType(InputType.TYPE_CLASS_DATETIME);

		mEnterButton1.setOnTouchListener(new OnTouchListener() {
			@SuppressWarnings("deprecation")
			public boolean onTouch(View v, MotionEvent event) {
				showDialog(DATE_PICKER_ID_0);
				return false;
			}
		});

		mEnterButton2.setOnTouchListener(new OnTouchListener() {
			@SuppressWarnings("deprecation")
			public boolean onTouch(View v, MotionEvent event) {
				if (var == 2) {
					showDialog(DATE_PICKER_ID_1);
					bool = true;
				}
				return false;
			}
		});

		mEnterButton2.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				
				if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
						|| (actionId == EditorInfo.IME_ACTION_DONE)) {
					
					if (!mEnterButton1.getText().toString().trim().equals("")
							&& !mEnterButton2.getText().toString().trim()
									.equals("")) {
						if (var == 1) {
							
							countEndDate();

						} else if (var == 3) {
							
							countStartDate();
						}
					}
				}
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
	}

	private void initWidget() {
		// TODO Auto-generated method stub
		
		mBackBtn = (Button) findViewById(R.id.buttonback);
		mEnterButton1 = (EditText) findViewById(R.id.editText1);
		mEnterButton2 = (EditText) findViewById(R.id.editText2);
		mResult = (EditText) findViewById(R.id.editText3);

		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.editdate_top);
		
		tv3.setText("Result");
		//tv4.setText("Starting with the end date,work backwards by xx days to find the starting date");
		SharedPreferences handle = getApplicationContext()
				.getSharedPreferences("SETTING_SCREEN", 0);
		isSaturday = handle.getBoolean("SATURDAY", false);
		isSunday = handle.getBoolean("SUNDAY", false);
		isHolidayWorking = handle.getBoolean("HOLIDAY", false);

		pd = new ProgressDialog(this);
		// chd = new CustomHolidaysDb(StartingDateActivity.this);
	}

	private void countEndDate() {
		
		AsyncTask<Void, Void, String> waitForCompletion = new AsyncTask<Void, Void, String>() {
 
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				if(!pd.isShowing())
					pd = ProgressDialog.show(StartingDateActivity.this, "", "Calculating Dates", true, true);
			}
			
			@Override
			protected String doInBackground(Void... params) {

				int totalDays = Integer.parseInt(mEnterButton2.getText().toString()
						.trim());
				int count = 0;
				date3 = (Calendar) date1.clone();

				while (count < totalDays) {
					if (isWeekend(date3) || isPublicHoliday(date3)
							|| isCustomHoliday(date3)) {
						date3.add(Calendar.DATE, 1);
					} else {
						date3.add(Calendar.DATE, 1);
						count++;
					}
				}
				date3.add(Calendar.DATE, -1);
				date3 = checkEndDateAsHoliday(date3);

				int year = date3.get(Calendar.YEAR);
				int month = date3.get(Calendar.MONTH) + 1;
				int day = date3.get(Calendar.DAY_OF_MONTH);

				String temp = convertDateString(day + "-" + month + "-" + year);
			        
				return temp;
			};

			@Override
			protected void onPostExecute(String finalResult) {
				
				mResult.setText(finalResult);
				
				if(pd.isShowing())
				{
					pd.dismiss();
				}
			}
		};
		
		waitForCompletion.execute();
		
	}
	
	private void countTotalDays(final int n) {
		
		AsyncTask<Void, Void, String> waitForCompletion = new AsyncTask<Void, Void, String>() {
 
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				if(!pd.isShowing())
					pd = ProgressDialog.show(StartingDateActivity.this, "", "Calculating Dates", true, true);
			}
			
			@Override
			protected String doInBackground(Void... params) {

				int count = 0;
				date3 = (Calendar) date1.clone();

				for (int i = 0; i < n; i++) {
					if (isWeekend(date3) || isPublicHoliday(date3)
							|| isCustomHoliday(date3)) {
						date3.add(Calendar.DATE, 1);
					} else {
						date3.add(Calendar.DATE, 1);
						count++;
					}
				}
				count++;
			        
				return String.valueOf(count);
			};

			@Override
			protected void onPostExecute(String finalResult) {
				
				mResult.setText(finalResult);
				
				if(pd.isShowing())
				{
					pd.dismiss();
				}
			}
		};
		
		waitForCompletion.execute();
		
	}	

	private void countStartDate() {
		
		AsyncTask<Void, Void, String> waitForCompletion = new AsyncTask<Void, Void, String>() {
	
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				if(!pd.isShowing())
					pd = ProgressDialog.show(StartingDateActivity.this, "", "Calculating  Dates", true, true);
			}
			
			@Override
			protected String doInBackground(Void... params) {
	
				int totalDays = Integer.parseInt(mEnterButton2.getText().toString()
						.trim());
				int count = 0;
				date3 = (Calendar) date1.clone();

				while (count < totalDays) {
					if (isWeekend(date3) || isPublicHoliday(date3)
							|| isCustomHoliday(date3)) {
						date3.add(Calendar.DATE, -1);
					} else {
						date3.add(Calendar.DATE, -1);
						count++;
					}
				}
				date3.add(Calendar.DATE, 1);
				date3 = checkEndDateAsHoliday(date3);

				int year = date3.get(Calendar.YEAR);
				int month = date3.get(Calendar.MONTH) + 1;
				int day = date3.get(Calendar.DAY_OF_MONTH);

				String temp = convertDateString(day + "-" + month + "-" + year);
			        
				return temp;
			};
	
			@Override
			protected void onPostExecute(String finalResult) {
				
				mResult.setText(finalResult);
				
				if(pd.isShowing())
				{
					pd.dismiss();
				}
			}
		};
		
		waitForCompletion.execute();
		
	}	
	/*public void countEndDate() {
		
		int totalDays = Integer.parseInt(mEnterButton2.getText().toString()
				.trim());
		int count = 0;
		date3 = (Calendar) date1.clone();

		while (count < totalDays) {
			if (isWeekend(date3) || isPublicHoliday(date3)
					|| isCustomHoliday(date3)) {
				date3.add(Calendar.DATE, 1);
			} else {
				date3.add(Calendar.DATE, 1);
				count++;
			}
		}
		date3.add(Calendar.DATE, -1);
		date3 = checkEndDateAsHoliday(date3);

		int year = date3.get(Calendar.YEAR);
		int month = date3.get(Calendar.MONTH) + 1;
		int day = date3.get(Calendar.DAY_OF_MONTH);

		String temp = convertDateString(day + "-" + month + "-" + year);
		// Show selected date
		// mEnterButton2.setText(new StringBuilder().append(month +
		// 1).append("/").append(day).append("/").append(year).append(" "));
		mResult.setText(temp);
		
		if(pd.isShowing())
			pd.dismiss();
	}*/

	/*public void countTotalDays(int n) {

		// int n = (int) ((date2.getTime().getTime() -
		// date1.getTime().getTime()) / (1000 * 60 * 60 * 24));

		if(!pd.isShowing())
			pd = ProgressDialog.show(StartingDateActivity.this, "", "Processing Dates", true, false);
		
		int count = 0;
		date3 = (Calendar) date1.clone();

		for (int i = 0; i < n; i++) {
			if (isWeekend(date3) || isPublicHoliday(date3)
					|| isCustomHoliday(date3)) {
				date3.add(Calendar.DATE, 1);
			} else {
				date3.add(Calendar.DATE, 1);
				count++;
			}
		}
		count++;
		mResult.setText(String.valueOf(count));
		
		if(pd.isShowing())
			pd.dismiss();
	}*/

	/*public void countStartDate() {
		
		if(!pd.isShowing())
			pd = ProgressDialog.show(StartingDateActivity.this, "", "Processing Dates", true, false);
		
		int totalDays = Integer.parseInt(mEnterButton2.getText().toString()
				.trim());
		int count = 0;
		date3 = (Calendar) date1.clone();

		while (count < totalDays) {
			if (isWeekend(date3) || isPublicHoliday(date3)
					|| isCustomHoliday(date3)) {
				date3.add(Calendar.DATE, -1);
			} else {
				date3.add(Calendar.DATE, -1);
				count++;
			}
		}
		date3.add(Calendar.DATE, 1);
		date3 = checkEndDateAsHoliday(date3);

		int year = date3.get(Calendar.YEAR);
		int month = date3.get(Calendar.MONTH) + 1;
		int day = date3.get(Calendar.DAY_OF_MONTH);

		String temp = convertDateString(day + "-" + month + "-" + year);
		// Show selected date
		// mEnterButton2.setText(new StringBuilder().append(month +
		// 1).append("/").append(day).append("/").append(year).append(" "));
		mResult.setText(temp);
		
		if(pd.isShowing())
			pd.dismiss();
	}*/

	public boolean isWeekend(Calendar date) {
		int i = date.get(Calendar.DAY_OF_WEEK);

		if (!isSaturday && i == 7) {
			Log.i("Saturday", date.getTime().toString());
			return true;

		}

		if (!isSunday && i == 1) {
			Log.i("Sunday", date.getTime().toString());
			return true;
		}
		return false;
	}

	public boolean isPublicHoliday(Calendar date) {
		//if (isHolidayWorking) {
			int n = MainActivity.DatesList.size();
			// int year = date1.get(Calendar.YEAR);
			for (int i = 0; i < n; i++) {
				HolidayClass uHC = MainActivity.DatesList.get(i);

				/*
				 * if(Integer.parseInt(uHC.getYear()) == year) {
				 */
				ArrayList<String> dateuHC = uHC.getDate();
				int k = dateuHC.size();

				for (int j = 0; j < k; j++) {
					String datefinal = dateuHC.get(j).trim()
							.concat(" " + uHC.getYear()); /* "November 27 2013" */
					SimpleDateFormat form = new SimpleDateFormat("MMMM dd yyyy");
					Date d1 = null;
					Calendar tdy1;

					try {
						d1 = form.parse(datefinal);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tdy1 = Calendar.getInstance();
					tdy1.setTime(d1);

					tdy1.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
					tdy1.set(Calendar.MINUTE, 0); // set minute in hour
					tdy1.set(Calendar.SECOND, 0); // set second in minute
					tdy1.set(Calendar.MILLISECOND, 0);
					tdy1.set(Calendar.AM_PM, 0);

					if (date.compareTo(tdy1) == 0) {
						Log.i("Holiday", date.getTime().toString());
						return true;
					}
				}
				// }
			}
		//}

		return false;
	}

	public boolean isCustomHoliday(Calendar date) {
		if (isHolidayWorking) {
			SharedPreferences handle = getApplicationContext()
					.getSharedPreferences("CUSTOM_HOLIDAYS", 0);

			long startL = handle.getLong("START_DATE", 0);
			long endL = handle.getLong("END_DATE", 0);

			if (startL > 0 && endL > 0) {
				Calendar calStart = Calendar.getInstance();
				Calendar calEnd = Calendar.getInstance();

				calStart.setTimeInMillis(startL);
				calEnd.setTimeInMillis(endL);

				int n = (int) ((calEnd.getTime().getTime() - calStart.getTime()
						.getTime()) / (1000 * 60 * 60 * 24));
				for (int i = 0; i <= n; i++) {
					if (date.compareTo(calStart) == 0) {
						Log.i("Holiday", date.getTime().toString());
						return true;
					} else {
						calStart.add(Calendar.DATE, 1);
					}
				}

			}
		}
		return false;
	}

	public Calendar checkEndDateAsHoliday(Calendar date) {
		int value = var == 1 ? 1 : -1;

		Calendar tempDate;
		tempDate = (Calendar) date.clone();
		if (isWeekend(tempDate) || isPublicHoliday(tempDate)) {
			tempDate.add(Calendar.DATE, value);
			checkEndDateAsHoliday(tempDate);
		}

		return tempDate;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID_0:
			SetDate();
			return new DatePickerDialog(this, pickerListener, year, month, day);

		case DATE_PICKER_ID_1:
			SetDate();
			return new DatePickerDialog(this, pickerListener1, year, month, day);

		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int dayOfMonth) {
			// TODO Auto-generated method stub
			year = selectedYear;
			month = selectedMonth;
			day = dayOfMonth;

			date1 = Calendar.getInstance();
			date1.set(Calendar.YEAR, year);
			date1.set(Calendar.MONTH, month);
			date1.set(Calendar.DAY_OF_MONTH, day);

			date1.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
			date1.set(Calendar.HOUR, 0); // set hour to midnight
			date1.set(Calendar.MINUTE, 0); // set minute in hour
			date1.set(Calendar.SECOND, 0); // set second in minute
			date1.set(Calendar.MILLISECOND, 0);
			date1.set(Calendar.AM_PM, 0);

			String temp = convertDateString(day + "-" + (month + 1) + "-"
					+ year);
			// Show selected date
			// mEnterButton1.setText(new StringBuilder().append(month +
			// 1).append("/").append(day).append("/").append(year).append(" "));
			mEnterButton1.setText(temp);

		}
	};

	private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int dayOfMonth) {
			// TODO Auto-generated method stub
			year = selectedYear;
			month = selectedMonth;
			day = dayOfMonth;

			date2 = Calendar.getInstance();
			date2.set(Calendar.YEAR, year);
			date2.set(Calendar.MONTH, month);
			date2.set(Calendar.DAY_OF_MONTH, day);

			date2.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
			date2.set(Calendar.HOUR, 0); // set hour to midnight
			date2.set(Calendar.MINUTE, 0); // set minute in hour
			date2.set(Calendar.SECOND, 0); // set second in minute
			date2.set(Calendar.MILLISECOND, 0);
			date2.set(Calendar.AM_PM, 0);

			String temp = convertDateString(day + "-" + (month + 1) + "-"
					+ year);
			// Show selected date
			// mEnterButton2.setText(new StringBuilder().append(month +
			// 1).append("/").append(day).append("/").append(year).append(" "));
			mEnterButton2.setText(temp);

			if (var == 2 && bool) {
				if (!mEnterButton1.getText().toString().trim().equals("")
						&& !mEnterButton2.getText().toString().trim()
								.equals("")) {
					bool = false;
					int n = (int) ((date2.getTime().getTime() - date1.getTime()
							.getTime()) / (1000 * 60 * 60 * 24));

					if (n >= 0)
					{
						if(!pd.isShowing())
							pd = ProgressDialog.show(StartingDateActivity.this, "", "Processing Dates", true, false);
						
						countTotalDays(n);
					}

					else
						Toast.makeText(getApplicationContext(),
								"End Date must be later than the Start Date",
								Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Enter the Dates", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
	};

	public String convertDateString(String dateI) {
		SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = null;

		try {
			date = form.parse(dateI);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat postFormater = new SimpleDateFormat("dd MMMMM yyyy");
		// String newDateStr = postFormater.format(date);
		return postFormater.format(date);
	}

	private void SetDate() {
		// TODO Auto-generated method stub
		// Get current date by calender

		todayC = Calendar.getInstance();
		year = todayC.get(Calendar.YEAR);
		month = todayC.get(Calendar.MONTH);
		day = todayC.get(Calendar.DAY_OF_MONTH);

		// Show current date

		// mEnterDate.setText(new StringBuilder().append(month +
		// 1).append("-").append(day).append("-").append(year).append(" "));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		finish();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}
}
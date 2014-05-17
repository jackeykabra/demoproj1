package com.android.workingday;

//COMPANY NAME : SILICON IT HUB PVT LTD

//DEVELOPER NAME : Nilay Sheth

//PROJECT NAME : workingday 

//DEVELOPING DATE :21-11-2013

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.android.workingday.beanclass.HolidayClass;
import com.android.workingday.parse.ParserPlist;

public class MainActivity extends Activity {

	// ScrollView mainScrollView ;
	private Button mStartingDate, mSetting, mBetweenDate, mEndDate;
	public static ArrayList<HolidayClass> DatesList = new ArrayList<HolidayClass>();
	public static String plistName = "England and Wales";
	TextView textSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initWidget();

		mEndDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainActivity.this,
						StartingDateActivity.class);
				intent.putExtra("to", "mEndDate");
				startActivity(intent);
				overridePendingTransition(R.anim.trans_left_in,
						R.anim.trans_left_out);
			}
		});

		mBetweenDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainActivity.this,
						StartingDateActivity.class);
				intent.putExtra("to", "mBetweenDate");
				startActivity(intent);
				overridePendingTransition(R.anim.trans_left_in,
						R.anim.trans_left_out);
				// finish();
			}
		});

		mStartingDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainActivity.this,
						StartingDateActivity.class);
				intent.putExtra("to", "mStartingDate");
				startActivity(intent);
				overridePendingTransition(R.anim.trans_left_in,
						R.anim.trans_left_out);
				// finish();
			}
		});

		mSetting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.trans_left_in,
						R.anim.trans_left_out);
				// finish();
			}
		});
	}

	private void initWidget() {
		// TODO Auto-generated method stub
		mStartingDate = (Button) findViewById(R.id.startDate);
		mBetweenDate = (Button) findViewById(R.id.betweenDate);
		mEndDate = (Button) findViewById(R.id.endDate);
		mSetting = (Button) findViewById(R.id.setting);

		// textSetting = (TextView) findViewById(R.id.textSetting);
		// textSetting.setText("The default for all options above counts weekends & public holidays as non working days. Go to settings to include weekends as working days, include your own holidays as non working days or to change the country");
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */

	private String readPlistFromAssets(String plistString) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(getAssets().open(
					plistString))); // plistString = "USA.plist"
			String temp;
			while ((temp = br.readLine()) != null)
				sb.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void readPlistContents(String plistString) {
		String xml = readPlistFromAssets(plistString);

		ParserPlist pp = new ParserPlist();

		DatesList = pp.parsePlist(xml);
	}

	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences handle = getApplicationContext()
				.getSharedPreferences("SETTING_SCREEN", 0);
		plistName = handle.getString("COUNTRY", "England and Wales");
		readPlistContents(plistName + ".plist");
	}
}

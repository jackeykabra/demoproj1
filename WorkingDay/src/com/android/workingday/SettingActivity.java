package com.android.workingday;

//COMPANY NAME : SILICON IT HUB PVT LTD

//DEVELOPER NAME : Nilay Sheth

//PROJECT NAME : workingday 

//DEVELOPING DATE :21-11-2013

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class SettingActivity extends Activity implements OnClickListener {

	private Button mBackBtn, enterHoliday;

	private ToggleButton btn_englend, btn_scotland, btn_Northern, btn_Ireland, btn_USA,
							btn_count_saturdays, btn_count_sunday, btn_count_holiday;
	
	String country;
	boolean saturdays, sunday, holiday;
	
	//private TextView tvStateofToggleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
			initWidget();
			
			SharedPreferences handle = getApplicationContext().getSharedPreferences("SETTING_SCREEN", 0);
			country = handle.getString("COUNTRY", "England and Wales");
			saturdays = handle.getBoolean("SATURDAY", false);
			sunday = handle.getBoolean("SUNDAY", false);
			holiday = handle.getBoolean("HOLIDAY", false);
			
			if(country.equals("England and Wales")){
				
				btn_englend.setChecked(true);
				btn_scotland.setChecked(false);
				btn_Northern.setChecked(false);
				btn_Ireland.setChecked(false);
				btn_USA.setChecked(false);
			}else if (country.equals("Scotland")) {
				
				btn_englend.setChecked(false);
				btn_scotland.setChecked(true);
				btn_Northern.setChecked(false);
				btn_Ireland.setChecked(false);
				btn_USA.setChecked(false);
				
			}else if (country.equals("Northen Ireland")) {
				
				btn_englend.setChecked(false);
				btn_scotland.setChecked(false);
				btn_Northern.setChecked(true);
				btn_Ireland.setChecked(false);
				btn_USA.setChecked(false);
				
			}else if (country.equals("Ireland")) {
				
				btn_englend.setChecked(false);
				btn_scotland.setChecked(false);
				btn_Northern.setChecked(false);
				btn_Ireland.setChecked(true);
				btn_USA.setChecked(false);
				
			}else if (country.equals("USA")) {
				
				btn_englend.setChecked(false);
				btn_scotland.setChecked(false);
				btn_Northern.setChecked(false);
				btn_Ireland.setChecked(false);
				btn_USA.setChecked(true);
			}
			
			if(saturdays){
				btn_count_saturdays.setChecked(true);
			}else {
				btn_count_saturdays.setChecked(false);
			}
			
			if(sunday){
				btn_count_sunday.setChecked(true);
			}else {
				btn_count_sunday.setChecked(false);
			}
			
			if(holiday){
				btn_count_holiday.setChecked(true);
			}else {
				btn_count_holiday.setChecked(false);
			}

			enterHoliday.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent i = new Intent(SettingActivity.this, CustomHolidays.class);
					startActivity(i);
					overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
				}
			});
		}

	private void initWidget() {
		
		mBackBtn = (Button) findViewById(R.id.button_back);
		mBackBtn.setOnClickListener(this);
		
		enterHoliday = (Button) findViewById(R.id.enterHoliday);
		
		btn_englend = (ToggleButton) findViewById(R.id.toggle_btn__englend);
		btn_englend.setOnClickListener(this);
		btn_scotland = (ToggleButton) findViewById(R.id.toggle_btn_scotland);
		btn_scotland.setOnClickListener(this);
		btn_Northern = (ToggleButton) findViewById(R.id.toggle_btn_Northern);
		btn_Northern.setOnClickListener(this);
		btn_Ireland = (ToggleButton) findViewById(R.id.toggle_btn_Republic);
		btn_Ireland.setOnClickListener(this);
		btn_USA = (ToggleButton) findViewById(R.id.toggle_btn_USA);
		btn_USA.setOnClickListener(this);
		
		btn_count_saturdays = (ToggleButton) findViewById(R.id.toggle_btn_count_saturdays);
		btn_count_saturdays.setOnClickListener(this);
		btn_count_sunday = (ToggleButton) findViewById(R.id.toggle_btn_count_sunday);
		btn_count_sunday.setOnClickListener(this);
		btn_count_holiday = (ToggleButton) findViewById(R.id.toggle_btn_count_holiday);
		btn_count_holiday.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.button_back:
			
			onBackPressed();
			
			break;
			
		case R.id.toggle_btn__englend:
			if (btn_englend.isChecked() == true) {
				 
				btn_scotland.setChecked(false);
				btn_Northern.setChecked(false);
				btn_Ireland.setChecked(false);
				btn_USA.setChecked(false);
				
				country = "England and Wales";
				
				//Toast.makeText(this, "btn_USA switch is On" ,Toast.LENGTH_SHORT).show();
				 
			}else {
				 btn_englend.setChecked(true);
			}
			break;
			
		case R.id.toggle_btn_scotland:
			if (btn_scotland.isChecked()  == true) {
				 
				btn_englend.setChecked(false);
				btn_Northern.setChecked(false);
				btn_Ireland.setChecked(false);
				btn_USA.setChecked(false);
				
				country = "Scotland";
				
			}else {
				btn_scotland.setChecked(true);
			}
			break;
		case R.id.toggle_btn_Northern:
			if (btn_Northern.isChecked()  == true) {
				 
				btn_englend.setChecked(false);
				btn_scotland.setChecked(false);
				btn_Ireland.setChecked(false);
				btn_USA.setChecked(false);
				
				country = "Northen Ireland";
		        
			}else {
				btn_Northern.setChecked(true);
			}
			break;
			
		case R.id.toggle_btn_Republic:
			if (btn_Ireland.isChecked()  == true) {
				 
				btn_englend.setChecked(false);
				btn_scotland.setChecked(false);
				btn_Northern.setChecked(false);
				btn_USA.setChecked(false);
				
				country = "Ireland";
		       
			}else {
				btn_Ireland.setChecked(true);
			}
			break;
			
		case R.id.toggle_btn_USA:
			if (btn_USA.isChecked()  == true) {
				 
				btn_englend.setChecked(false);
				btn_scotland.setChecked(false);
				btn_Northern.setChecked(false);
				btn_Ireland.setChecked(false);
				
				country = "USA";
		       
			}else {
				btn_USA.setChecked(true);
			}
			break;
			
		case R.id.toggle_btn_count_saturdays:
			if (btn_count_saturdays.isChecked()  == true) {
				 saturdays  = true;
			}else {
				saturdays  = false;
			}
			break;
			
		case R.id.toggle_btn_count_sunday:
			if (btn_count_sunday.isChecked()  == true) {
				 sunday  = true;
			}else {
				sunday  = false;
			}
			break;
			
		case R.id.toggle_btn_count_holiday:
			if (btn_count_holiday.isChecked()  == true) {
				holiday  = true;
			}else {
				holiday  = false;
			}
			break;
						
		}
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();

		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		SharedPreferences Setting = getApplicationContext().getSharedPreferences("SETTING_SCREEN", 0); // 0 - for private mode
		
		Editor SETTING_SCREEN = Setting.edit();
		SETTING_SCREEN.putString("COUNTRY", country);
		SETTING_SCREEN.putBoolean("SATURDAY", saturdays);
        SETTING_SCREEN.putBoolean("SUNDAY", sunday);
        SETTING_SCREEN.putBoolean("HOLIDAY", holiday);
        SETTING_SCREEN.commit();
		
	}
}

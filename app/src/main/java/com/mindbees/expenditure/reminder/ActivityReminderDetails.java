package com.mindbees.expenditure.reminder;



import java.util.ArrayList;
import java.util.Locale;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.adapter.AdapterReminderList;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.model.ReminderSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class ActivityReminderDetails extends Activity {
	
	MyDataBase db;
	ArrayList<ReminderSet> reminderDesc;
//	public  MediaPlayer mMediaPlayer;
	public String time,datee;
	ListView reminderList;
	AdapterReminderList adapter;
	Ringtone r;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ringing_dialog);
		
		View footview= View.inflate(this, R.layout.alarm_footer, null);
		
		Intent i = getIntent();
		db = new MyDataBase(this);
		
		
		time= i.getStringExtra("time");
		datee= i.getStringExtra("date");
//		int pref=i.getIntExtra("pref",111111);
		
		reminderDesc = db.getReminders(datee, time);
		
		reminderList = (ListView) findViewById(R.id.reminderList);
		
		reminderList.addFooterView(footview);
		adapter = new AdapterReminderList(this, reminderDesc);
		reminderList.setAdapter(adapter);
		
		footview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					if(r.isPlaying())
						r.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				db.clearDb();
				finish();
			}
		});
		
	}
	
	
	
	


}

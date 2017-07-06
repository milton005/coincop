package com.mindbees.expenditure.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.mindbees.expenditure.MainActivity;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.reminder.ActivityReminderDetails;
import com.mindbees.expenditure.reminder.RingingDialog;
import com.mindbees.expenditure.util.Tools;

public class AlaramReciver extends BroadcastReceiver{

//	final public static String ONE_ENTER="One time ring";
	Tools tools;
	private static int numMessages = 0;
	int notificationId;
	
	
	Context cont;
	 MyDataBase db;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		db = new MyDataBase(context);
		cont=context;
		tools = new Tools(context);
		
		String time = intent.getStringExtra("time");
		String datee = intent.getStringExtra("date");
		
		if (db.getReminders(datee, time).size() > 0) {

			int pref = intent.getIntExtra("pref", 111111);

			try {
				Intent send1 = new Intent(context, RingingDialog.class);
				send1.putExtra("time", time);
				send1.putExtra("date", datee);
				// send1.putExtra("pref", pref);
				send1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(send1);

			} catch (Exception e) {

				/*Toast.makeText(context, "in reciver" + e, Toast.LENGTH_LONG)
						.show();*/
			}
			notificationId = (int) System.currentTimeMillis();

			// alarmNotification(context, time,pref);
			generateNotification_On_AboveJelly(context, notificationId, time,
					datee, pref);

		}
	}
	/*
	public void alarmNotification(Context con,String time,int pref){
		
		 Intent trigger=new Intent(con,MainActivity.class);
		 	trigger.putExtra("hai", time);
		 	trigger.putExtra("pref", pref);
		 	trigger.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		NotificationManager mgr  = (NotificationManager) con.getSystemService(con.NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.ic_launcher,
				"COIN COP", System.currentTimeMillis());
		note.flags = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;
	 	 PendingIntent pIntent=PendingIntent.getActivity(con, pref, trigger, 0);
		note.setLatestEventInfo(con, "Coin Cop",
				"", pIntent);
		mgr.notify(pref, note);
		}*/
	  



public static void generateNotification_On_AboveJelly(Context context, int notificationId, String time, String datee, int pref){
	
	

//      Invoking the default notification service 
     NotificationCompat.Builder  mBuilder = 
     new NotificationCompat.Builder(context);	

     mBuilder.setContentTitle("Coin Cop");
//     mBuilder.setContentText(message);
//     mBuilder.setTicker(message);
     mBuilder.setSmallIcon(R.drawable.ic_launcher).setAutoCancel(true);

//      Increase notification number every time a new notification arrives 
     mBuilder.setNumber(++numMessages);


//      Add Big View Specific Configuration 
     NotificationCompat.BigTextStyle inboxStyle =
            new NotificationCompat.BigTextStyle();

//     inboxStyle.bigText(message);
     mBuilder.setStyle(inboxStyle);
      
     
//      Creates an explicit intent for an Activity in your app 
     Intent resultIntent = new Intent(context, ActivityReminderDetails.class);
     resultIntent.putExtra("time", time);
     resultIntent.putExtra("date", datee);
//     resultIntent.putExtra("pref", pref);
     
     resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
               Intent.FLAG_ACTIVITY_SINGLE_TOP);
     
     PendingIntent intent =
               PendingIntent.getActivity(context, notificationId, resultIntent, 0);

     mBuilder.setContentIntent(intent);

    NotificationManager mNotificationManager =
     (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);

//      notificationID allows you to update the notification later on. 
   
     mNotificationManager.notify(notificationId, mBuilder.build());
     
}	
	
}
	


package com.fortuna.android.mobilecustomer.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static int nShowMsg = 0;
	public static void showmsg(Context context, String sTitle, String sMsg) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(sTitle)
		.setMessage(sMsg)
		.setCancelable(false)
		.setNegativeButton("Close",new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				nShowMsg = 10;
			}
		});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

	public static void showmsg2(Context context, String sTitle, String sMsg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(sTitle)
		.setMessage(sMsg)
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				nShowMsg = 10;
			}
		})
		
		.setNegativeButton("Close", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				nShowMsg = 11;
			}
		});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

	public static void popup(Context context, String sMsg) {
		Toast.makeText(context, sMsg, Toast.LENGTH_LONG).show();
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}

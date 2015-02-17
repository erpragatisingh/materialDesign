package com.ldrawer.library.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import ldrawer.com.pragati.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

	public static String TAG = "Utils";

	public static void addFragment(FragmentActivity context, Fragment fragment,
			String fragmentTag, int resLayout, Bundle args) {
		if (fragment == null || context == null) {
			Log.d(TAG, "Fragment or context passed is null");
			return;
		}
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		// fragmentTransaction.setCustomAnimations(R.anim.in_right,R.anim.out_left,
		// R.anim.in_left, R.anim.out_right);
		if (args != null) {
			fragment.setArguments(args);
		}

		if (resLayout == -1 || resLayout == 0) {
			fragmentTransaction.replace(R.id.fragment_container, fragment,
					fragmentTag);
		} else {
			fragmentTransaction.replace(resLayout, fragment, fragmentTag);
		}
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commitAllowingStateLoss();

	}

	/*
	 * Add a fragment. Tag will be used as id to find the fragment later.
	 */

	public static void addFragment(FragmentActivity context, Fragment fragment,	String fragmentTag, int resLayout) {
		if (fragment == null || context == null) {
			Log.d(TAG, "Fragment or context passed is null");
			return;
		}
		addFragment(context, fragment, fragmentTag, resLayout, null);
	}

	public static void RemoveAndaddFragment(FragmentActivity context,
			Fragment fragment, String fragmentTag, int resLayout) {
		if (fragment == null || context == null) {
			Log.d(TAG, "Fragment or context passed is null");
			return;
		}
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		fragmentManager.popBackStack();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		if (resLayout == -1 || resLayout == 0) {
			fragmentTransaction.replace(R.id.fragment_container, fragment,
					fragmentTag);
		} else {
			fragmentTransaction.replace(resLayout, fragment, fragmentTag);
		}
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	public static void addFragment(FragmentActivity context, Fragment fragment,
			String fragmentTag) {
		if (fragment == null || context == null) {
			Log.d(TAG, "Fragment or context passed is null");
			return;
		}
		addFragment(context, fragment, fragmentTag, -1);
	}

	public static void changeFragment(FragmentActivity context,
			Fragment fragment) {
		if (fragment == null || context == null) {
			Log.d(TAG, "Fragment or context passed is null");
			return;
		}
		addFragment(context, fragment, null, -1);
	}

	/*
	 * Remove the fragment added using fragmentTag
	 */
	public static void removeFragment(FragmentActivity context,
			String fragmentTag) {
		if (fragmentTag == null || context == null) {
			Log.d(TAG, "Fragment or context passed is null");
			return;
		}
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
		if (fragment != null) {
			FragmentTransaction fragtrans = fragmentManager.beginTransaction();
			fragtrans.remove(fragment);
			fragtrans.commit();
		} else {
			Log.d(TAG, "No Fragment found to delete with tag " + fragmentTag);
		}

	}

	public static void popBackStack(FragmentActivity context) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		fragmentManager.popBackStack();
	}

	public static void removeTillMainFragment(FragmentActivity context) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		for (int i = 0; i < fragmentManager.getBackStackEntryCount() - 1; ++i) {
			fragmentManager.popBackStack();
		}
	}

	public static void showToast(Context context, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.toast_layout, null);

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(message);

		Toast toast = new Toast(context.getApplicationContext());

		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	/**
	 * for IOUtils.copy replacement Commons-IO.jar
	 * 
	 * @param is
	 * @param fos
	 * @throws Exception
	 */
	public static void copyStream(InputStream is, FileOutputStream fos)
			throws Exception {
		byte[] byteArray = new byte[64 * 1024];
		int len = 0;
		while ((len = is.read(byteArray)) != -1) {
			fos.write(byteArray, 0, len);
		}
		fos.flush();
		fos.close();
		is.close();
	}

	/**
	 * @param virusName
	 * @return
	 */

	public static int dpToPx(int dp) {
		if (dp <= 0)
			return dp;
		else
			return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public static int pxToDp(int px) {
		if (px <= 0)
			return px;
		else
			return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

	public static int pxToSp(int px) {
		if (px <= 0)
			return px;
		else
			return (int) (px / Resources.getSystem().getDisplayMetrics().scaledDensity);
	}

	public static String getDateString(Context ctx, long date) {
		DateFormat df = new SimpleDateFormat("EEE MMM d, yyyy hh:mm a", ctx
				.getResources().getConfiguration().locale);
		Date dateobj = new Date(date);
		return df.format(dateobj);
	}

	public static int getNumberOfDaysSince(Context ctx, long lastDate) {
		long currentdate = System.currentTimeMillis();
		long differenceDays = currentdate - lastDate;
		if (differenceDays < 0)
			return 0;

		return (int) (differenceDays / (24 * 60 * 60 * 1000));
	}

	public static String updateLastBackupDetail() {
		Calendar calendar = new GregorianCalendar();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		String dateFormate;
		String backupedDay = null;
		if (hourOfDay >= 12) {
			dateFormate = "PM";
			if (hourOfDay > 12) {
				hourOfDay = (hourOfDay - 12);
			}
		} else {
			dateFormate = "AM";
		}
		switch (dayOfWeek) {
		case 1:
			backupedDay = "Sunday";
			break;
		case 2:
			backupedDay = "Monday";
			break;
		case 3:
			backupedDay = "Tuesday";
			break;
		case 4:
			backupedDay = "Wednesday";
			break;
		case 5:
			backupedDay = "Thursday";
			break;
		case 6:
			backupedDay = "Friday";
			break;
		case 7:
			backupedDay = "Saturday";
			break;
		}
		return backupedDay + " at " + hourOfDay + ":"
				+ returnDoubleDigits(minutes) + " " + dateFormate;

	}

	/**
	 * @param type
	 * @param mContext
	 * @param URL
	 * @param jObj
	 * @return
	 */

	private static String returnDoubleDigits(int digits) {
		String returnValue;
		if (digits < 10) {
			returnValue = "0" + digits;
		} else {
			returnValue = String.valueOf(digits);
		}
		return returnValue;

	}

	public static boolean isNetworkAvailable(Activity activity) {
		ConnectivityManager connectivity = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

 

}

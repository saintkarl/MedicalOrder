package com.karlchu.medicalorder.core;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.karlchu.medicalorder.R;
import com.karlchu.medicalorder.core.utils.DataBinder;
import com.karlchu.medicalorder.core.utils.K;


public final class A extends MultiDexApplication
{


	private static A                        app;
	private static String name;
	private static ContentResolver contentResolver;
	private static SharedPreferences sharedPrefs;
	private static Resources resources;
	private static SharedPreferences.Editor editor;

	private static LayoutInflater inflater;

	private static NotificationManager notifMan;
	private static AudioManager audioMan;
	private static PowerManager powerMan;
	private static AlarmManager alarmMan;
	private static ConnectivityManager connMan;

	private static UserPrincipal principal;

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		name = getString(R.string.app_name);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(app);
		editor = sharedPrefs.edit();
	}

	public static A app() {
		return app;
	}

	public static String name() {
		return name;
	}

	public static SharedPreferences sharedPrefs() {
		return sharedPrefs;
	}

	public static SharedPreferences.Editor prefEditor() {
		return editor;
	}

	public static UserPrincipal getPrincipal() {
		if (principal == null) {
			String json = A.gets(K.PRINCIPAL_JSON);
			if (!TextUtils.isEmpty(json)) {
				principal = DataBinder.readUserPrincipal(json);
			}
		}
		return principal;
	}

	public static void setPrincipal(UserPrincipal principal) {
		A.principal = principal;
	}

	public static ContentResolver contentResolver() {
		if (contentResolver == null) {
			contentResolver = app.getContentResolver();
		}
		return contentResolver;
	}
	
	public static Resources resources() {
		if(resources == null) {
			resources = app.getResources(); 
		}
		return resources;
	}
	
	public static String s(int id) {
		return app.getString(id);
	}
	
	public static String s(int id, Object[] args) {
		return app.getString(id, args);
	}

	public static boolean is(String key) {
		return sharedPrefs.getBoolean(key, false);
	}
		
	public static boolean is(String key, boolean def)   {
		return sharedPrefs.getBoolean(key, def);
	}
	
	public static String gets(String key) {
		return sharedPrefs.getString(key, "");
	}
	
	public static String gets(String key, String def)  {
		return sharedPrefs.getString(key, def);
	}
	
	public static int geti(String key) {
		return sharedPrefs.getInt(key, 0);
	}
	
	public static int geti(String key, int def) {
		return sharedPrefs.getInt(key, def); 
	}
	
	public static long getl(String key) {
		return sharedPrefs.getLong(key, 0l);
	}
	
	public static boolean has(String key) {
		return sharedPrefs.contains(key);
	}

	public static void del(String key) {
		editor.remove(key);
	}
	
	public static void delc(String... keys) {
		for(String key : keys) {
			editor.remove(key);
		}
		editor.commit();
	}

	public static void delc(String key) {
		editor.remove(key).commit();
	}

	public static void put(String key, boolean val) {
		editor.putBoolean(key, val);
	}

	public static void putc(String key, boolean val) {
		editor.putBoolean(key, val).commit();
	}

	public static void put(String key, int val) {
		editor.putInt(key, val);
	}

	public static void putc(String key, int val) {
		editor.putInt(key, val).commit();
	}

	public static void put(String key, long val) {
		editor.putLong(key, val);
	}

	public static void putc(String key, long val) {
		editor.putLong(key, val).commit();
	}

	public static void put(String key, float val) {
		editor.putFloat(key, val);
	}

	public static void putc(String key, float val) {
		editor.putFloat(key, val).commit();
	}

	public static void put(String key, String val) {
		editor.putString(key, val);
	}

	public static void putc(String key, String val) {
		editor.putString(key, val).commit();
	}

	public static void putc(String key, Object val) {
		 put(key, val).commit();
	}

	public static A put(String key, Object val) {
		if (val instanceof Boolean)
			editor.putBoolean(key, (Boolean) val);
		else if (val instanceof Integer)
			editor.putInt(key, (Integer) val);
		else if (val instanceof Float)
			editor.putFloat(key, (Float) val);
		else if (val instanceof Long)
			editor.putLong(key, (Long) val);
		else
			editor.putString(key, val.toString());
		return app;
	}


	public static void commit() {
		editor.commit();
	}

	public static NotificationManager notifMan() {
		if (notifMan == null)
			notifMan = (NotificationManager) app
					.getSystemService(NOTIFICATION_SERVICE);
		return notifMan;
	}

	public static AudioManager audioMan() {
		if (audioMan == null)
			audioMan = (AudioManager) app.getSystemService(AUDIO_SERVICE);
		return audioMan;
	}

	public static PowerManager powerMan() {
		if (powerMan == null)
			powerMan = (PowerManager) app.getSystemService(POWER_SERVICE);
		return powerMan;
	}

	public static AlarmManager alarmMan() {
        if (alarmMan == null)
		    alarmMan = (AlarmManager) app.getSystemService(ALARM_SERVICE);
        return alarmMan;
	}

	public static ConnectivityManager connMan() {
		if(connMan == null) {
			connMan = (ConnectivityManager) app.getSystemService(CONNECTIVITY_SERVICE);
		}
		return connMan;
	}

	public static LayoutInflater getInflater() {
		if(inflater == null) {
			inflater = (LayoutInflater) app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return inflater;
	}

}

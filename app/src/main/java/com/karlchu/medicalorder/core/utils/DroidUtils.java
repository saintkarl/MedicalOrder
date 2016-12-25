package com.karlchu.medicalorder.core.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hieu on 7/4/2016.
 */
public class DroidUtils {
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static String unaccent(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        } else {
            String result = Normalizer.normalize(input, Normalizer.Form.NFD);
            result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            result = result.replace('đ', 'd');
            result = result.replace('Đ', 'D');
            return result;
        }
    }

    public static Date string2Date(String input, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(input);
    }

    public static String date2String(Date input, String format) throws IllegalArgumentException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(input);
    }
}

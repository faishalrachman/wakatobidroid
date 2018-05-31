package com.aruna.kliknelayanwakatobi.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aruna.kliknelayanwakatobi.KlikNelayanWakatobiApp;

/**
 * Created by marzellaalfamega on 6/23/15.
 *
 */
public class SPHelper {
    private static SPHelper instance;
    private static KlikNelayanWakatobiApp context;

    public static SPHelper getInstance() {
        if (instance == null) {
            instance = new SPHelper();
        }
        context = KlikNelayanWakatobiApp.Companion.getInstance();
        return instance;
    }

    //
    private final String preferenceName = "paket_id_driver_preference";

    public int getSharedPreferences(String key, int defValue) {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return p.getInt(key, defValue);
    }

    public String getSharedPreferences(String key, String defValue) {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return p.getString(key, defValue);
    }

    public boolean getSharedPreferences(String key, boolean defValue) {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return p.getBoolean(key, defValue);
    }

    public double getSharedPreferences(String key, double defValue) {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return Double.longBitsToDouble(p.getLong(key, Double.doubleToLongBits(defValue)));
    }

    public void setPreferences(String key, boolean value) {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putBoolean(key, value);
        e.commit();
    }

    public void setPreferences(String key, CharSequence value) {
        if (!TextUtils.isEmpty(value)) {
            SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor e = p.edit();
            e.putString(key, value.toString());
            e.commit();
        }
    }

    public void setPreferences(String key, int value) {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putInt(key, value);
        e.commit();
    }

    public void setPreferences(String key, double value) {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putLong(key, Double.doubleToRawLongBits(value));
        e.commit();
    }

    public void clearData() {
        SharedPreferences p = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit().clear();
        e.commit();
    }
}
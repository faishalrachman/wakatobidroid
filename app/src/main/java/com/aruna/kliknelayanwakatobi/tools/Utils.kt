package com.aruna.kliknelayanwakatobi.tools

import ControllerFile
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.aruna.kliknelayanwakatobi.services.SyncService
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode

object Utils {

    val TAG = "Aruna"
    private val ANIM_DUR: Long = 500
    private val height = 400
    private var resultList: MutableList<Array<String>>? = null

    //    private static final LinkedList<PopupWindow> popupSnack = new LinkedList<>();

    //    public static boolean isEmpty(String str) {
    //        return str == null || str.trim().equals("") || str.trim().toLowerCase().equals("null");
    //    }

    //    public static boolean isNetworkConnected(Context context) {
    //        ConnectivityManager cm = (ConnectivityManager) context
    //                .getSystemService(Context.CONNECTIVITY_SERVICE);
    //        NetworkInfo ni = cm.getActiveNetworkInfo();
    //
    //        Utils.Logs('i', TAG, "Network info: " + String.valueOf(ni));
    //        boolean isConnected = ni != null && ni.isConnectedOrConnecting();
    //        Utils.Logs('i', TAG, "Is not connected: " + isConnected);
    //
    //        // Log connection type
    ////        MyLog.FabricLog(Log.INFO, "Driver Network type: " + String.valueOf(
    ////                isConnected ? ni.getTypeName() : " NO CONNECTION"
    ////        ));
    //
    //        return isConnected;
    //    }

    fun createCameraIntent(context: Context, photoFileName: String): Intent? {
        val imageFile = ControllerFile.getDefaultPath(photoFileName)
        if (imageFile != null) {
            Utils.Log("Utils", "Absolute file path $imageFile")
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val imageUri = FileProvider.getUriForFile(context, context.applicationContext.packageName +
                    ".provider", File(imageFile))
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//        }else {
//
//            val imageUri = Uri.fromFile(File(filePath))
//            // Start default camera
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        }

            return cameraIntent
        }
        return null
    }

    fun numberOnly(number_string: String): String {
        return number_string.replace("[^0-9]".toRegex(), "")
    }

    /**
     * Different Logging for INFO, DEBUG, ERROR.
     *
     * @param logAttr : Character to indicate log type.
     * @param msg     : String message to print
     * @param value   : Value string
     */
    fun Logs(logAttr: Char, msg: String, valueUnmute: String) {
        var value = valueUnmute
        //        if (BuildConfig.IS_DEBUG) {
        // Null value state when no value found
//        value = value.toString()
        try {
            if (value.length > 15000) {
                value = value.substring(0, 15000)
            }

            val maxLength = 1000
            if (!TextUtils.isEmpty(value)) {
                if (value.length > maxLength) {
                    when (logAttr) {
                        'e' -> {
                            Log.e(TAG, msg + " " + value.substring(0, maxLength))
                            Logs('e', msg, value.substring(maxLength))
                        }
                        'w' -> {
                            Log.w(TAG, msg + " " + value.substring(0, maxLength))
                            Logs('w', msg, value.substring(maxLength))
                        }
                        'i' -> {
                            Log.i(TAG, msg + " " + value.substring(0, maxLength))
                            Logs('i', msg, value.substring(maxLength))
                        }
                        'd' -> {
                            Log.d(TAG, msg + " " + value.substring(0, maxLength))
                            Logs('d', msg, value.substring(maxLength))
                            Log.v(TAG, msg + " " + value.substring(0, maxLength))
                            Logs('v', msg, value.substring(maxLength))
                        }
                        else -> {
                            Log.v(TAG, msg + " " + value.substring(0, maxLength))
                            Logs('v', msg, value.substring(maxLength))
                        }
                    }
                } else {
                    when (logAttr) {
                        'e' -> Log.e(TAG, msg + " " + value.substring(0, value.length))
                        'w' -> Log.w(TAG, msg + " " + value.substring(0, value.length))
                        'i' -> Log.i(TAG, msg + " " + value.substring(0, value.length))
                        'd' -> Log.d(TAG, msg + " " + value.substring(0, value.length))
                        else -> Log.v(TAG, msg + " " + value.substring(0, value.length))
                    }
                }
            }

        } catch (exception: NullPointerException) {
            exception.printStackTrace()
        }

        //        }
    }

    fun Log(msg: String, value: String) {
        var value = value
        //        if (BuildConfig.IS_DEBUG) {
        val maxLength = 1000
        value = value.toString()

        if (!TextUtils.isEmpty(value)) {
            if (value.length > maxLength) {
                Log.e(TAG, msg + " " + value.substring(0, maxLength))
                Log(msg, value.substring(maxLength))
            } else {
                Log.e(TAG, msg + " " + value.substring(0, value.length))
            }
        }
        //        }
    }

    fun contains(source: String, contain: String): Boolean {
        return source.toLowerCase().contains(contain.toLowerCase())
    }

    fun hideKeyboardFrom(context: Context, view: View?) {
        if (view != null) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard(context: Context, view: View?) {
        if (view != null) {
            Utils.Log("show keyboard", "try to show keyboard")
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInputFromWindow(view.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
            //            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    //    public static void snackbarDismissImmediately() {
    //        try {
    //            PopupWindow popupWindow;
    //            while ((popupWindow = popupSnack.poll()) != null) {
    //                popupWindow.dismiss();
    //            }
    //        } catch (Exception ex) {
    //            snackbarDismissImmediately();
    //        }
    //    }

    fun changeFragment(fragmentActivity: FragmentActivity,
                       layoutFragment: Int, fragment: Fragment, tag: String,
                       toBackStack: Boolean) {
        val ft = fragmentActivity.supportFragmentManager
                .beginTransaction()
        ft.replace(layoutFragment, fragment, tag)
        if (toBackStack) {
            ft.addToBackStack(tag)
        }
        ft.setTransition(FragmentTransaction.TRANSIT_NONE)
        ft.commit()
//        fragmentActivity.supportInvalidateOptionsMenu()
    }

    fun changeFragment(fragmentActivity: FragmentActivity,
                       layoutFragment: Int, fragment: Fragment) {
        val ft = fragmentActivity.supportFragmentManager
                .beginTransaction()
        ft.replace(layoutFragment, fragment)
        ft.setTransition(FragmentTransaction.TRANSIT_NONE)
        ft.commit()
//        fragmentActivity.supportInvalidateOptionsMenu()
    }


    fun getCurrentFragment(fragmentActivity: FragmentActivity, layoutFragment: Int): Fragment {
        return fragmentActivity.supportFragmentManager.findFragmentById(layoutFragment)
    }

    fun addFragment(fragmentActivity: FragmentActivity,
                    layoutFragment: Int, vararg fragment: Fragment) {
        val ft = fragmentActivity.supportFragmentManager
                .beginTransaction()
        for (fragment1 in fragment) {
            ft.add(layoutFragment, fragment1)
        }
        //        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit()
    }

    fun isTelephonyEnabled(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)
    }

    fun showFragment(fragmentActivity: FragmentActivity,
                     fragmentToShow: Fragment, vararg fragment: Fragment) {
        val ft = fragmentActivity.supportFragmentManager
                .beginTransaction()
        for (fragment1 in fragment) {
            ft.hide(fragment1)
        }
        ft.show(fragmentToShow)
        ft.commit()
    }

    fun findFragmentByTag(fragmentActivity: FragmentActivity,
                          tag: String): Fragment {

        return fragmentActivity.supportFragmentManager.findFragmentByTag(tag)
    }


    fun CapsFirst(str: String): String {
        val words = str.trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val ret = StringBuilder()
        for (i in words.indices) {
            ret.append(Character.toUpperCase(words[i][0]))
            ret.append(words[i].substring(1).toLowerCase())
            if (i < words.size - 1) {
                ret.append(' ')
            }
        }
        return ret.toString()
    }

    fun round(value: Double, places: Int): Double {
        if (places < 0) throw IllegalArgumentException()

        var bd = BigDecimal(value)
        bd = bd.setScale(places, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun isNeedRuntimePermissions(): Boolean {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
    }

    fun isServiceRunning(serviceClass: Class<*>, context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun startSync(activity: AppCompatActivity) {
        if (!Utils.isServiceRunning(SyncService::class.java, activity)) {
            val intentServiceSync = Intent(activity, SyncService::class.java)
            activity.startService(intentServiceSync)
        }
    }
}
package com.project.xetnghiem.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.project.xetnghiem.models.Patient;;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.ResponseBody;


/**
 * Created by lucky on 14-Sep-17.
 */

public class Utils {

    private static final String PREF_NAME = "ACCOUNT";
    private static final String STAFF_KEY = "STAFF_KEY";
    private static final String FINGER_AUTH_KEY = "FINGER_AUTH_KEY";
    private static final String PATIENTS_KEY = "PATIENTS_KEY";
    public static final String CURRENT_UNIT = "đ";
    public static final String STATUS_DONE = "Hoàn Thành";
    public static final String STATUS_NOT_DONE = "Hoàn Thành";
    public static final String PATTERN_DATE = "dd-MM-yyyy";
    public static final int DENTIST = 2;
    public static final int RECEIPTION = 3;
    public static String LIST_TREATMENT = "LIST_TREATMENT";
    public static String LIST_PAYMENT = "LIST_PAYMENT";
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86");
    }
    public static String linkServer = "http://150.95.104.237/";

    public static void savePatientInSharePref(Context context, Patient user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(STAFF_KEY, gson.toJson(user));
        editor.apply();
    }
    public static <T> void saveInSharePref (Context context,Object t, String prefName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(t));
        editor.apply();
    }
    public static Patient getPatientInSharePref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString(STAFF_KEY, null);
        Gson gson = new Gson();
        Patient u = gson.fromJson(jsonUser, Patient.class);
        return u;
    }
    public static <T> T getInSharePref (Context context,Class<T> t, String prefName, String key ) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString(key, null);
        Gson gson = new Gson();
        T u = gson.fromJson(jsonUser, t);
        return u;
    }
    public static String getErrorMsg(ResponseBody responseBody) {
        try {
            if (responseBody != null) {
                JSONObject errorObject = new JSONObject(responseBody.string());
                if (errorObject != null) {
                    return errorObject.getString("error");
                } else {
                    Log.d(AppConst.DEBUG_TAG, "ResponseBody or ResponseBody.getErrorMsg() null");
                    return "";
                }
            } else {
                Log.d(AppConst.DEBUG_TAG, "ResponseBody or ResponseBody.string() null");
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    /// Tên thuốc: hello .......... 30 viên
    public static String getMedicineLine(String medicineName, int quantity, int numDot) {
        String dots = "";
        for (int i = 0; i < numDot - medicineName.length() - Integer.toString(quantity).length(); i++) {
            dots += ".";
        }
        return medicineName + " " + dots + " " + quantity + " viên";
    }

    public static String formatMoney(Long money) {
        NumberFormat formatter = new DecimalFormat("#,###");
//        double myNumber = 1000000;
        String formattedNumber = formatter.format(money);
//            return (String.format("%,d", money)).replace(',', '.');
//            return money.toString();
        return  formattedNumber;
    }

    public static <T> T parseJson(String source, Class<T> c) throws JsonSyntaxException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(source, c);
    }


    public static void setVNLocale(Context context) {
        Resources res = context.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("vi_VN")); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }




    public static void subscribeReloadClinicAppointment() {
        FirebaseMessaging.getInstance().subscribeToTopic(AppConst.TOPIC_RELOAD_APPOINTMENT);
    }

    public static void unsubscribeReloadClinicAppointment() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(AppConst.TOPIC_RELOAD_APPOINTMENT);
    }
    public static String getTimeInStr(int minute) {
        int hourInt = minute / 60;
        int minInt = minute % 60;
        String hourStr, minuteStr;
        hourStr = hourInt + "";
        if (hourInt < 10) {
            hourStr = "0" + hourInt;
        }
        minuteStr = minInt + "";
        if (minInt < 10) {
            minuteStr = "0" + minInt;
        }
        return hourStr + ":" + minuteStr;
    }public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

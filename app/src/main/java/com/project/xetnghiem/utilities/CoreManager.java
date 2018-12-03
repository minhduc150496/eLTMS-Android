package com.project.xetnghiem.utilities;

import android.content.Context;
import android.util.Log;

import com.project.xetnghiem.models.Patient;


/**
 * Created by lucky on 15-Oct-17.
 */

public class CoreManager {
    private static Patient mPatient = null;
    //    private static Patient mCurrentPatient = null;
    private CoreManager() {

    }

    public static Patient getPatient(Context context) {
        mPatient = Utils.getPatientInSharePref(context);
        if (mPatient == null) {
            Log.d(AppConst.DEBUG_CORE_TAG, "get User from share prefef Null");
        }
        return mPatient;
    }

    public static void setPatient(Context context, Patient patient) {
        Utils.savePatientInSharePref(context, patient);

    }

    public static void clearPatient(Context context){
        Utils.savePatientInSharePref(context, null);
//         setCurrentPatient(-1, context);
         mPatient=null;
    }



}

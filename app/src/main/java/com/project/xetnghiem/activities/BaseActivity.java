package com.project.xetnghiem.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.project.xetnghiem.R;
import com.project.xetnghiem.api.responseObj.ErrorResponse;
import com.project.xetnghiem.models.BaseContext;
import com.project.xetnghiem.models.Patient;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.Utils;

import java.io.IOException;
import java.net.InetAddress;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

public abstract class BaseActivity extends AppCompatActivity implements BaseContext {
    private ProgressDialog progressDialog;
    private static int numNotification = 0;

    // Tool bar
    private ImageButton btnNotification;
    private ImageButton btnBack;
    private TextView txtNumNotification;
    private CircleImageView btnAvatar;

    public void setPatient(Patient patient) {
        CoreManager.setPatient(this, patient);
    }

    public void clearPatient() {
        CoreManager.clearPatient(this);
    }

    public Patient getPatient() {
        return CoreManager.getPatient(this);
    }

    protected abstract int getLayoutView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getMainTitle());
//        showMessNetword();
        setContentView(getLayoutView());
        bindView();
        initToolbar();
        callDataResource();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar);
        setToolbarAvatarClickListener(toolbar);
    }

    protected void setToolbarAvatarClickListener(Toolbar toolbar) {
        CircleImageView btnAvatar = toolbar.findViewById(R.id.imgAvatar);
        TextView txtTitle = toolbar.findViewById(R.id.txt_title_toolbar);
        txtTitle.setText(getMainTitle());
        btnAvatar.setOnClickListener((v) -> {
            redirectToActivityWithTop(AccountActivity.class, false);
        });
        btnBack = toolbar.findViewById(R.id.btn_toolbar_back);
        btnBack.setOnClickListener((v) -> {
            finish();
        });
        txtNumNotification = toolbar.findViewById(R.id.txt_noti_number);
        btnNotification = toolbar.findViewById(R.id.btn_toolbar_notification);
        btnNotification.setOnClickListener((v) -> {
            if (this instanceof AppointmentResultActivity) {
                //refresh
                logInfo("SetToolbar method", this.getClass().getSimpleName());
            } else {
                btnNotification.setEnabled(false);
                redirectToActivityWithTop(AppointmentResultActivity.class, false);
                clearNotiNumber();
                hideNotiNumber();
                logInfo("SetToolbar method", this.getClass().getSimpleName());
            }

        });//set visible item

        if (this instanceof LoginActivity || this instanceof RegisterActivity || this instanceof AppointmentResultActivity) {
            btnNotification.setVisibility(View.INVISIBLE);
            btnAvatar.setVisibility(View.INVISIBLE);
            hideNotiNumber();
        } else {
            btnNotification.setVisibility(View.VISIBLE);
            displayNotiNumber();
        }
        if (this instanceof LoginActivity || this instanceof RegisterActivity || this instanceof AccountActivity) {
            btnAvatar.setVisibility(View.INVISIBLE);
        } else {
            btnAvatar.setVisibility(View.VISIBLE);
        }
        if (numNotification != 0) {
            setTextNotiNumber(numNotification);
        } else {
            hideNotiNumber();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (btnNotification != null) {
            btnNotification.setEnabled(true);
        }
        if (btnBack != null) {
            btnBack.setEnabled(true);

        }
    }

    public void hideNotiNumber() {
        if (txtNumNotification != null) {
            txtNumNotification.setVisibility(View.INVISIBLE);
        }
    }

    public void displayNotiNumber() {
        if (txtNumNotification != null) {
            txtNumNotification.setVisibility(View.VISIBLE);
        }
    }

    public void clearNotiNumber() {
        numNotification = 0;
        setTextNotiNumber(numNotification);
    }

    private void setTextNotiNumber(int num) {
        if (txtNumNotification != null) {
            txtNumNotification.setText(num + "");
        }
    }

    public void increaseNotiNumber() {
        numNotification++;
        setTextNotiNumber(numNotification);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public abstract String getMainTitle();

    public abstract void bindView();

    protected void callDataResource() {

    }

    public  void updateUIData(Object obj){};
    public  void updateUIData(){};

    public void redirectToActivity(Class<?> tClass, boolean forceFinish) {
        Intent intent = new Intent(this, tClass);
        startActivity(intent);
        if (forceFinish) {
            finish();
        }
    }

    public void redirectToActivityWithTop(Class<?> tClass, boolean forceFinish) {
        Intent intent = new Intent(this, tClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        if (forceFinish) {
            finish();
        }
    }

    public void showDialog(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Thử lại", (DialogInterface dialogInterface, int i) -> {
                });
        alertDialog.show();
    }

    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showWarningMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.waiting_msg));
            progressDialog.setOnCancelListener((DialogInterface dialog) -> {
                showMessage(getString(R.string.dialog_cancel));
                onCancelLoading();
                hideLoading();
            });
            progressDialog.show();
        }
    }

    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void onCancelLoading() {
        showMessage(getString(R.string.dialog_cancel));
    }

    public void logInfo(String activity, String method, String message) {
        Log.e(AppConst.DEBUG_TAG, activity + "." + method + "(): " + message);
    }

    public void logInfo(Class t, String method, String message) {
        Log.e(AppConst.DEBUG_TAG, t.getSimpleName() + "." + method + "(): " + message);
    }

    public void logInfo(String method, String message) {
        Log.e(AppConst.DEBUG_TAG, "Activity" + this.getClass().getSimpleName() + method + "(): " + message);
//        Log.e(AppConst.DEBUG_TAG, this.getClass().getSimpleName() + "." + method + "(): " + message);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public void showMessNetword() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
//For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        System.out.println(is3g + " net " + isWifi);
        if (!is3g && !isWifi) {
            showWarningMessage("Vui lòng kiểm tra kết nối mạng của bạn đã được bật.");
        }
    }

    public void showFatalError(ResponseBody errorBody, String method) {
        if (errorBody != null) {
            showErrorMessage("Lỗi server");
            try {
                String error = errorBody.string();
                logInfo("showFatalError: " + method, error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logInfo("showFatalError: " + method, "errorBody is null");
        }
    }

    public void showErrorUnAuth() {
//        CoreManager.clearStaff(this);
//        showErrorMessage("Phiên đăng nhập hết hạn, vui lòng đăng nhập lại");
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }

    public void showBadRequestError(ResponseBody errorBody, String method) {
        if (errorBody != null) {
            try {
                String error = errorBody.string();
                ErrorResponse errorResponse = Utils.parseJson(error, ErrorResponse.class);
                showErrorMessage(errorResponse.getErrorMessage());
                logInfo(method, errorResponse.getExceptionMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Context getContextBase() {
        return this;
    }
}

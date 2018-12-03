package com.project.xetnghiem.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.project.xetnghiem.R;
import com.project.xetnghiem.api.responseObj.ErrorResponse;
import com.project.xetnghiem.models.BaseContext;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

public abstract class BaseFragment extends Fragment implements BaseContext {
    private ProgressDialog mProgressDialog;

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage(getString(R.string.waiting_msg));
            mProgressDialog.show();
        }
    }

    protected void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showSuccessMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showWarningMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showDialog(String message) {
        if (getContext() != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext())
                    .setMessage(message)
                    .setPositiveButton("Xong", (DialogInterface dialogInterface, int i) -> {
                    });
            alertDialog.show();
        }
    }

    public void logError(Class t, String method, String message) {
        Log.e(AppConst.DEBUG_TAG, t.getSimpleName() + "." + method + "(): " + message);
    }

    public void logError(String method, String message) {
//        Log.e(AppConst.DEBUG_TAG, this.getClass().getSimpleName() + "." + method + "(): " + message);
        Log.e(AppConst.DEBUG_TAG, "Fragment" + "." + method + "(): " + message);
    }

    public void showFatalError(ResponseBody errorBody, String method) {
        if (errorBody != null) {
            showErrorMessage("Lỗi server");
            try {
                String error = errorBody.string();
                logError("showFatalError: " + method, error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logError("showFatalError: " + method, "errorBody is null");
        }
    }

    public void showErrorUnAuth() {
        showErrorMessage("401 Unauthentication");
    }

    public void showBadRequestError(ResponseBody errorBody, String method) {
        if (errorBody != null) {
            try {
                String error = errorBody.string();
                ErrorResponse errorResponse = Utils.parseJson(error, ErrorResponse.class);
                showErrorMessage(errorResponse.getErrorMessage());
                logError(method, errorResponse.getExceptionMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void bindView();

    protected void callDataResource() {

    }

    public abstract void updateUIData(Object obj);

    @Override
    public Context getContextBase() {
        return getContext();
    }
}

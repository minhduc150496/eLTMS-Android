package com.project.xetnghiem.api;

import android.util.Log;
import android.widget.Toast;

import com.project.xetnghiem.models.BaseContext;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public abstract class MySingleObserver<T> implements SingleObserver<Response<T>> {
    private BaseContext baseContext;

    public MySingleObserver(BaseContext baseContext) {
        this.baseContext = baseContext;
    }

    @Override
    public void onSuccess(Response<T> tResponse) {
        if (baseContext != null) {
            baseContext.hideLoading();
        }
        if (!tResponse.isSuccessful() && tResponse.errorBody() != null) {
            switch (tResponse.code()) {
                case 500:
                    showToast("Error code 500");
                    break;
                case 400:
                    showToast("Error code 400");
                    break;
                case 404:
                    showToast("Error code 404");
                    break;
                case 403:
                    showToast("Error code 403");
                    break;
                case 503:
                    showToast("Error code 503");
                    break;
            }
        } else {
            onResponseSuccess(tResponse);
        }

    }

    public void showToast(String message) {
        if (baseContext != null) {
            Toast.makeText(baseContext.getContextBase(), message, Toast.LENGTH_SHORT).show();
        }
        logInfo(message);
    }

    private void logInfo(String message) {
        if (baseContext != null) {
            message = "Class: " + baseContext.getClass().getSimpleName() + "_" + message;
        }
        Log.d("DEBUG_TAG", message);
    }

    protected abstract void onResponseSuccess(Response<T> tResponse);

    @Override
    public void onError(Throwable e) {
        logInfo(e.getMessage());
        if (baseContext != null) {
            showToast("In Class MySingleObserver: Error when connect API");
            baseContext.hideLoading();
        }
        e.printStackTrace();

    }
}

package com.project.xetnghiem.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;


import com.project.xetnghiem.R;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.requestObj.RegisterRequest;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.PatientService;
import com.project.xetnghiem.models.District;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.Validation;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private Disposable addrServiceDisposable;
    private Disposable registerServiceDisposable;
    private Disposable districtServiceDisposable;

    private EditText edtFullname;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtCardNumber;
    private Button btnRegister;


    @Override
    protected int getLayoutView() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void attemptRegister() {
        edtFullname.setError(null);
        edtPhone.setError(null);
        edtPassword.setError(null);
        View focusView = null;
        boolean cancel = false;
        String name = edtFullname.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String identityCardNumber = edtCardNumber.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        if (!Validation.isNameValid(name)) {
            cancel = true;
            edtFullname.setError(getString(R.string.error_invalid_name));
            focusView = edtFullname;
        }else if (!Validation.isPhoneValid(phone)) {
            cancel = true;
            edtPhone.setError(getString(R.string.error_invalid_phone));
            focusView = edtPhone;

        } else if (!Validation.isPasswordValid(password)) {
            edtPassword.setError(getString(R.string.error_invalid_password));
            cancel = true;
            focusView = edtPassword;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setAvatar("");
            registerRequest.setEmail(email);
            registerRequest.setPhone(phone);
            registerRequest.setPassword(password);
            registerRequest.setFullName(name);
            registerRequest.setIdentityCardNumber(identityCardNumber);
            callApiRegister(registerRequest);
        }
    }

    public String getGenderValue(int id) {
        String value;
        switch (id) {
            case R.id.rbt_male_register:
                value = AppConst.GENDER_MALE;
                break;
            case R.id.rbt_female_register:
                value = AppConst.GENDER_FEMALE;
                break;
            default:
                value = AppConst.GENDER_OTHER;
                break;
        }
        return value;
    }

    public void callApiRegister(RegisterRequest requestObj) {
        APIServiceManager.getService(PatientService.class)
                .register(requestObj)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<SuccessResponse>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<SuccessResponse> response) {
                        if (response.body().isSuccess()) {
                            finish();
                        }
                        showMessage(response.body().getMessage());
                    }
                }) ;
    }

    @Override
    public String getMainTitle() {
        return "Đăng kí tài khoản";
    }

    @Override
    public void bindView() {

        edtFullname = findViewById(R.id.edt_fullname_register);
        edtPhone = findViewById(R.id.edt_phone_register);
        edtPassword = findViewById(R.id.edt_password_register);
        edtCardNumber = findViewById(R.id.edt_card_number);
        edtEmail = findViewById(R.id.edt_email_register);
        btnRegister = findViewById(R.id.btn_register);
        ActionBar actionBar =getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btnRegister.setOnClickListener((view) -> {
            attemptRegister();
        });

    }

    @Override
    public void updateUIData(Object obj) {

    }

    @Override
    public void onCancelLoading() {
        //do nothing
        if (addrServiceDisposable != null) {
            addrServiceDisposable.dispose();
        }
        if (districtServiceDisposable != null) {
            districtServiceDisposable.dispose();
        }
        if (registerServiceDisposable != null) {
            registerServiceDisposable.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addrServiceDisposable != null) {
            addrServiceDisposable.dispose();
        }
        if (districtServiceDisposable != null) {
            districtServiceDisposable.dispose();
        }
        if (registerServiceDisposable != null) {
            registerServiceDisposable.dispose();
        }
    }
}

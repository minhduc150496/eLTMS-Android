package com.project.xetnghiem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;
import com.project.xetnghiem.R;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.requestObj.LoginRequest;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.PatientService;
import com.project.xetnghiem.models.Patient;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.Validation;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

//import com.dentalclinic.capstone.firebase.FirebaseDataReceiver;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    // UI references.
    private AutoCompleteTextView txtPhone;
    private EditText txtPassword;
    private View mProgressView;
    private View mLoginFormView;
    private View btnLinkAppointment;
    private View btnSingin;
    private TextView tvLinkForgotPassword;
    private TextView txtErrorServer;
    private TextView tvLinkRegister;


    private Disposable disposable;

    @Override
    public String getMainTitle() {
        return "Đăng nhập";
    }

    @Override
    public void bindView() {
        txtPhone = findViewById(R.id.txt_phone_loginact);
        txtErrorServer = findViewById(R.id.txt_error_server_loginact);
        txtPassword = findViewById(R.id.password);
        tvLinkForgotPassword = findViewById(R.id.tv_link_forgot_password);
        mLoginFormView = findViewById(R.id.login_form);
        btnSingin = findViewById(R.id.btn_signin_loginact);
        tvLinkRegister = findViewById(R.id.tv_link_register);


        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageButton backBtn = toolbar.findViewById(R.id.btn_toolbar_back);
        CircleImageView cicleAvatar = toolbar.findViewById(R.id.imgAvatar);
        backBtn.setVisibility(View.INVISIBLE);
        cicleAvatar.setVisibility(View.INVISIBLE);
        tvLinkRegister.setOnClickListener((v) -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        btnSingin.setOnClickListener((view) ->
        {
            attemptLogin();
//            showLoading();
        });
        txtPassword.clearFocus();
        txtPhone.clearFocus();
    }

    @Override
    public void updateUIData(Object obj) {

    }

    @Override
    public void onCancelLoading() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CoreManager.getPatient(this) != null) {
            redirectToMain();
        }
    }


    private void attemptLogin() {
        // Reset errors.
        txtErrorServer.setText("");
        txtPhone.setError(null);
        txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String phone = txtPhone.getText().toString();
        String password = txtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (!Validation.isPhoneValid(phone)) {
            txtPhone.setError(getString(R.string.error_invalid_phone));
            focusView = txtPhone;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            txtPassword.setError(getString(R.string.error_invalid_password));
            focusView = txtPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            showMessage("Số điện thoại không hợp lệ!");
        } else {
            LoginRequest request = new LoginRequest();
            request.setPhone(phone);
            request.setPassword(password);
            callApiLogin(request);
        }
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.matches("^\\d{9,}$");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void callApiLogin(LoginRequest requestObj) {
        showLoading();
        APIServiceManager.getService(PatientService.class)
                .login(requestObj)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<SuccessResponse>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    protected void onResponseSuccess(Response<SuccessResponse> response) {
                        if (response.body().isSuccess()) {
                            Patient patient = parseToPatient(response.body().getData());
                            CoreManager.clearPatient(LoginActivity.this);
                            CoreManager.setPatient(LoginActivity.this, patient);
                            redirectToMain();
                        } else {
                            showMessage(response.body().getMessage());
                        }
                    }
                });
    }

    private Patient parseToPatient(Object obj) {
        LinkedTreeMap<Object, Object> t = (LinkedTreeMap) obj;
        String name = t.get("FullName") == null ? "" : t.get("FullName").toString();
        String phoneNumber = t.get("PhoneNumber").toString();
        String avatar = t.get("AvatarURL") == null ? "" : t.get("AvatarURL").toString();
        String email = t.get("Email") == null ? "" : t.get("Email").toString();
        String cardNumber = t.get("IdentityCardNumber") == null ? "" : t.get("IdentityCardNumber").toString();
        int patientId = (int) Double.parseDouble(t.get("PatientId").toString());
        Patient p = new Patient();
        p.setName(name);
        p.setPhone(phoneNumber);
        p.setAvatar(avatar);
        p.setEmail(email);
        p.setId(patientId);
        p.setIdentityCardNumber(cardNumber);
        return p;
    }

    public void redirectToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    public void redirectToNurseMain() {
//        Intent intent = new Intent(LoginActivity.this, DoneApptActivity.class);
//        startActivity(intent);
//        finish();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

}


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

import com.project.xetnghiem.R;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.services.PatientService;
import com.project.xetnghiem.models.Patient;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.GenderUtils;
import com.project.xetnghiem.utilities.Validation;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
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
        tvLinkRegister = findViewById(R.id.tv_link_quickregister);
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageButton backBtn = toolbar.findViewById(R.id.btn_toolbar_back);
        CircleImageView cicleAvatar = toolbar.findViewById(R.id.imgAvatar);
        backBtn.setVisibility(View.INVISIBLE);
        cicleAvatar.setVisibility(View.INVISIBLE);
        if (CoreManager.getPatient(this) != null && CoreManager.getPatient((this)).getPhone().equals("1234567789")) {
            redirectToNurseMain();
        } else if(CoreManager.getPatient(this)!=null){
            redirectToMain();
        }
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


    private void attemptLogin() {
        // Reset errors.
        txtErrorServer.setText("");
        txtPhone.setError(null);
        txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = txtPhone.getText().toString();
        String password = txtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (!Validation.isUsernameValid(username)) {
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
            showMessage("Số điện thoại hoặc email không hợp lệ!");
        } else {
            callApiLogin(username, password);
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

    public void callApiLogin(String username, String password) {

        Patient patient = new Patient();
        patient.setAddress("VIet nam");
        patient.setGender("FEMALE");
        patient.setName("Pro");
        patient.setPhone(txtPhone.getText().toString().trim());
        patient.setId(1);
        CoreManager.setPatient(this, patient);
        if (txtPhone.getText().toString().trim().equals("123456789")) {
            redirectToNurseMain();
        } else {
            redirectToMain();
        }
    }

    public void redirectToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void redirectToNurseMain() {
        Intent intent = new Intent(LoginActivity.this, DoneApptActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

}


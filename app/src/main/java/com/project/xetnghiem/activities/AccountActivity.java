package com.project.xetnghiem.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xetnghiem.R;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.PatientService;
import com.project.xetnghiem.models.Patient;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.GenderUtils;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AccountActivity extends BaseActivity implements View.OnClickListener {
    public static int REQUEST_CHANGE_PASSWORD = 10000;
    Button btnChangeAvatar, btnEdit, btnLogout, btnChangePassword;
    CircleImageView cvAvatar;
    TextView txtName, txtPhone,txtEmail,txtCardNumber;
    Patient patient;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_account;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getMainTitle() {
        return "Tài khoản";
    }

    @Override
    public void bindView() {
        btnChangeAvatar = findViewById(R.id.btn_change_avatar);
        btnChangeAvatar.setOnClickListener(this);
        btnEdit = findViewById(R.id.btn_edit_accout);
        btnLogout = findViewById(R.id.btn_logout);
        btnEdit.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
//        btnChangePhone =  findViewById(R.id.btn_edit_phone);
//        btnChangePhone.setOnClickListener(this);
        btnChangePassword = findViewById(R.id.btn_edit_password);
        btnChangePassword.setOnClickListener(this);
        cvAvatar = findViewById(R.id.img_avatar_user);
        txtName = findViewById(R.id.txt_name);
        txtPhone = findViewById(R.id.txt_phone);
        txtEmail = findViewById(R.id.txt_email_account);
        txtCardNumber = findViewById(R.id.txt_card_number);
    }

    @Override
    public void callDataResource() {
        Patient p = CoreManager.getPatient(this);
        if (p == null) {
//            callApiCheck(CoreManager.getPatient(this).getPhone());
            showMessage("Patient is null");
        } else {
            updateUIData(p);
        }

    }

    @Override
    public void updateUIData(Object obj) {
        Patient patient = (Patient) obj;
        setData(patient);
    }


    private void setData(Patient patient) {
        if (patient != null) {
            if (patient.getAvatar() != null && patient.getAvatar().trim().length() > 0) {
//                Picasso.get().invalidate(patient.getAvatar());
                Picasso.get().load(patient.getAvatar()).into(cvAvatar);
            }
            if (patient.getName() != null) {
                txtName.setText(patient.getName());
            }
            if (patient.getEmail() != null) {
                txtEmail.setText(patient.getEmail());
            }
            if (patient.getPhone() != null) {
                txtPhone.setText(patient.getPhone());
            }if (patient.getIdentityCardNumber() != null) {
                txtCardNumber.setText(patient.getIdentityCardNumber());
            }
        }
    }


    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private PatientService patientService = APIServiceManager.getService(PatientService.class);
    private Disposable patientServiceDisposal;

    private void uploadImage(byte[] imageBytes) {

//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
//        MultipartBody.Part image = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
//        MultipartBody.Part id = MultipartBody.Part.createFormData("id", CoreManager.getCurrentPatient(getContext()).getId() + "");
//        //cột username đang bị null hết chỉ có 2 record dc add vào: luc2, luc12345678
//        patientService.changeAvatar(image, id).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Response<SuccessResponse>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        patientServiceDisposal = d;
//                    }
//
//                    @Override
//                    public void onSuccess(Response<SuccessResponse> response) {
//                        if (response.isSuccessful()) {
//                            if (response.body() != null) {
//                                CoreManager.saveAvatar(getContext(), (String) response.body().getData());
//                                MainActivity.resetHeader(getContext());
//                                showSuccessMessage(getResources().getString(R.string.success_message_api));
//                            }
//                        } else if (response.code() == 500) {
//                            showFatalError(response.errorBody(), "uploadImage");
//                        } else if (response.code() == 401) {
//                            showErrorUnAuth();
//                        } else if (response.code() == 400) {
//                            showBadRequestError(response.errorBody(), "uploadImage");
//                        } else {
//                            showDialog(getContext().getResources().getString(R.string.error_message_api));
//                        }
//                        hideLoading();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        hideLoading();
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), getResources().getString(R.string.error_on_error_when_call_api), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.btn_change_avatar:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
                        .start(AccountActivity.this);
                break;
            case R.id.btn_edit_accout:
                intent = new Intent(this, EditAccoutActivity.class);
                bundle = new Bundle();
//                bundle.putSerializable(AppConst.PATIENT_OBJ, CoreManager.getCurrentPatient(this ));
                intent.putExtra(AppConst.BUNDLE, bundle);
                startActivityForResult(intent, REQUEST_CHANGE_PASSWORD);
                break;
            case R.id.btn_logout:
                clearPatient();
                redirectToActivity(LoginActivity.class, true);
                break;
            case R.id.btn_edit_password:
                intent = new Intent(this, EditPasswordActivity.class);
                bundle = new Bundle();
//                bundle.putSerializable(AppConst.PATIENT_OBJ, CoreManager.getCurrentPatient(getContext()));
                intent.putExtra(AppConst.BUNDLE, bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    InputStream is = getContentResolver().openInputStream(resultUri);
                    showLoading();
                    uploadImage(getBytes(is));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cvAvatar.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CHANGE_PASSWORD) {
            if (resultCode == RESULT_OK) {
//                setData(CoreManager.getCurrentPatient(getContext()));
            }
        }
    }

    private Disposable disposable;

    public void callApiCheck(String phone) {

    }

}

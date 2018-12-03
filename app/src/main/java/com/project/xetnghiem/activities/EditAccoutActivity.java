package com.project.xetnghiem.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.CitySpinnerAdapter;
import com.project.xetnghiem.adapter.DistrictSpinnerAdapter;
import com.project.xetnghiem.api.requestObj.UpdatePatientRequest;
import com.project.xetnghiem.models.City;
import com.project.xetnghiem.models.District;
import com.project.xetnghiem.models.Patient;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.DatabaseHelper;
import com.project.xetnghiem.utilities.DistrictDatabaseHelper;
import com.project.xetnghiem.utilities.Validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class EditAccoutActivity extends BaseActivity implements View.OnClickListener {
    private EditText txtName;
    private TextView txtDateOfBirth, txtDateError;
    private AutoCompleteTextView txtAddress;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale, rbOther;
    private Spinner spDistrict, spCity;
    private Button btnUpdate;
    private Patient patient;
    private Disposable addrServiceDisposable;
    private Disposable registerServiceDisposable;
    private Disposable districtServiceDisposable;
    private DistrictDatabaseHelper districtDatabaseHelper = new DistrictDatabaseHelper(EditAccoutActivity.this);
    private DatabaseHelper cityDatabaseHelper = new DatabaseHelper(EditAccoutActivity.this);
    private DistrictSpinnerAdapter districtSpinnerAdapter;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_edit_accout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getBundleExtra(AppConst.BUNDLE);

        if (bundle.getSerializable(AppConst.PATIENT_OBJ) != null) {
            patient = (Patient) bundle.getSerializable(AppConst.PATIENT_OBJ);
            setDataPatient(patient);
        } else {
            patient = new Patient();
            patient.setId(-1);
        }

//        setEvenForCityDistrict();
        if (cityDatabaseHelper.getAllCity().isEmpty()) {
            cityDatabaseHelper.insertDataCity();
        }
        if (cityDatabaseHelper.getAllDistrict().isEmpty()) {
            cityDatabaseHelper.insertDataDistrict();
        }
        spCity.setAdapter(new CitySpinnerAdapter(
                EditAccoutActivity.this,
                android.R.layout.simple_spinner_item, cityDatabaseHelper.getAllCity()));
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                City city = (City) spCity.getSelectedItem();
                if (city != null) {
                    spDistrict.setAdapter(new DistrictSpinnerAdapter(EditAccoutActivity.this,
                            android.R.layout.simple_spinner_item, cityDatabaseHelper.getDistrictOfCity(city.getId())));
                    if (patient.getDistrict() != null) {
                        spDistrict.setSelection(cityDatabaseHelper.getPositionDistrictById(patient.getDistrict()));

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(patient.getCity()!=null){spCity.setSelection(cityDatabaseHelper.getPositionCityById(patient.getCity().getId()));}

    }

    @Override
    public String getMainTitle() {
        return getResources().getString(R.string.edit_acc_title);
    }

    @Override
    public void bindView() {
        txtName = findViewById(R.id.edt_name);
        rgGender = findViewById(R.id.rg_gender_register);
        rbMale = findViewById(R.id.rbt_male_register);
        rbFemale = findViewById(R.id.rbt_female_register);
        rbOther = findViewById(R.id.rbt_others_register);
        txtDateOfBirth = findViewById(R.id.txt_birthday);
        txtDateError = findViewById(R.id.txt_error_date);
        txtAddress = findViewById(R.id.edt_address);
        spDistrict = findViewById(R.id.spinner_district);
        spCity = findViewById(R.id.spinner_city);
        btnUpdate = findViewById(R.id.btn_register);
        btnUpdate.setOnClickListener(this);
        txtDateOfBirth.setOnClickListener(this);
    }

    @Override
    public void updateUIData(Object obj) {
        if (patient != null) {
            if (patient.getName() != null) {
                txtName.setText(patient.getName());
            }
            if (patient.getAddress() != null) {
                txtAddress.setText(patient.getAddress());
            }
            if (patient.getDateOfBirth() != null) {
                txtDateOfBirth.setText(patient.getDateOfBirth());
            }
            if (patient.getGender() != null) {
                checkedGender(patient.getGender());
            }
        }
    }

    @Override
    public void onCancelLoading() {
        if (registerServiceDisposable != null) {
            registerServiceDisposable.dispose();
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (registerServiceDisposable != null) {
//            registerServiceDisposable.dispose();
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                attemptRegister();
                break;
            case R.id.txt_birthday:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(EditAccoutActivity.this,
                        (DatePicker datePicker, int iYear, int iMonth, int iDay) -> {
                            txtDateOfBirth
                                    .setText(iYear + "-" + iMonth + "-" + iDay);
                            calendar.set(iYear, iMonth, iDay);
                            Calendar currentDay = Calendar.getInstance();
                            if (currentDay.before(calendar)) {
                                txtDateError.setText(getString(R.string.label_error_birthday));
                            } else {
                                txtDateError.setText("");

                            }
                        }, 2000, month, day);
                dialog.setButton(DatePickerDialog.BUTTON_POSITIVE,getString(R.string.OK), dialog);
                dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.Cancel), (DialogInterface.OnClickListener)null);
                dialog.show();
                break;
        }
    }


    public void callDistrictAPI(int id) {
    }

    public void attemptRegister() {
        txtName.setError(null);
        txtAddress.setError(null);
        View focusView = null;
        boolean cancel = false;
        String name = txtName.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String birthdayStr = txtDateOfBirth.getText().toString().trim();
        District district = (District) spDistrict.getSelectedItem();
        int districtID = -1;
        if (district != null) {
            districtID = district.getId();
        }
        if (!Validation.isNameValid(name)) {
            cancel = true;
            txtName.setError(getString(R.string.error_invalid_name));
            focusView = txtName;
        } else if (!Validation.isAddressValid(address)) {
            cancel = true;
            txtAddress.setError(getString(R.string.error_invalid_address));
            focusView = txtAddress;
        } else if (Validation.isNullOrEmpty(birthdayStr)) {
            cancel = true;
            txtDateOfBirth.setText(getString(R.string.label_error_birthday));
            focusView = txtDateOfBirth;
        } else if (birthdayStr != null && birthdayStr.equals(getString(R.string.label_birthday_register))) {
            cancel = true;
            txtDateError.setText(getString(R.string.label_error_birthday));
            focusView = txtDateError;
        }
        String gender = getGenderValue(rgGender.getCheckedRadioButtonId());
        if (cancel) {
            focusView.requestFocus();
        } else {
            UpdatePatientRequest request = new UpdatePatientRequest();
            request.setPatientId(patient.getId());
            request.setName(name);
            request.setAddress(address);
            request.setGender(gender);
            request.setDistrictId(districtID);
            request.setBirthday(birthdayStr);
            callApiUpdate(request);
        }
    }

    public void setDataPatient(Patient patient) {

    }

    public void checkedGender(String gender) {
        switch (gender) {
            case AppConst.GENDER_MALE:
                rbMale.setChecked(true);
                break;
            case AppConst.GENDER_FEMALE:
                rbFemale.setChecked(true);
                break;
            case AppConst.GENDER_OTHER:
                rbOther.setChecked(true);
                break;
            default:
                rbOther.setChecked(true);
                break;
        }
    }

    public String getGenderValue(int gender) {
        String value;
        switch (gender) {
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

    public void callApiUpdate(UpdatePatientRequest requestObj) {

    }


}

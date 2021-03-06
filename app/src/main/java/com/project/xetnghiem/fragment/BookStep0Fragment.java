package com.project.xetnghiem.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.flags.impl.DataUtils;
import com.project.xetnghiem.R;
import com.project.xetnghiem.api.requestObj.ApptPatientDto;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.Validation;

import java.util.Calendar;

public class BookStep0Fragment extends BaseFragment {
    private EditText txtFulname;
    private RadioGroup rgGender;
    private TextView txtDateOfBirth;
    private EditText txtPhoneNumber;
    private EditText txtHomeAddress;
    private Button btnNext;
    private ApptPatientDto patientDto;
    private String selectedGender;
    private boolean validationError = true;
    final Calendar c = Calendar.getInstance();

    public BookStep0Fragment() {
        // Required empty public constructor
    }

    public static BookStep0Fragment newInstance(String param1, String param2) {
        BookStep0Fragment fragment = new BookStep0Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_step0, container, false);
        txtFulname = v.findViewById(R.id.edt_fullname_createappt_step0);
        rgGender = v.findViewById(R.id.rg_gender_createappt_step0);
        txtDateOfBirth = v.findViewById(R.id.txt_birthday);
        txtPhoneNumber = v.findViewById(R.id.edt_phone_createappt_step0);
        txtHomeAddress = v.findViewById(R.id.edt_address);
        patientDto = new ApptPatientDto();
        c.set(Calendar.YEAR, 2000);
        Calendar maxDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        minDate.set(Calendar.YEAR, 1920);
        txtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        (DatePicker datePicker, int iYear, int iMonth, int iDay) -> {
                            c.set(iYear, iMonth, iDay, 23, 59);
                            txtDateOfBirth.setText(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_TIME_DB_2));
//                            dto.setDateStr(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_TIME_DB_2));
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                dialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                dialog.show();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String getGenderValue(int id) {
        String value;
        switch (id) {
            case R.id.rbt_male_createappt_step0:
                value = AppConst.GENDER_MALE;
                break;
            case R.id.rbt_female_createappt_step0:
                value = AppConst.GENDER_FEMALE;
                break;
            default:
                value = AppConst.GENDER_OTHER;
                break;
        }
        return value;
    }

    @Override
    public void bindView() {

    }

    @Override
    public void updateUIData(Object obj) {

    }

    public void triggerValidation() {
        String fullname = txtFulname.getText().toString();
        String dateOfBirth = txtDateOfBirth.getText().toString();
        String gender = getGenderValue(rgGender.getCheckedRadioButtonId());
        String address = txtHomeAddress.getText().toString();
        String phoneNumber = txtPhoneNumber.getText().toString();
        boolean valid = !Validation.isPhoneValid(phoneNumber);
        if (Validation.isNullOrEmpty(fullname)) {
            showMessage("Vui lòng nhập tên");
        } else if (Validation.isNullOrEmpty(dateOfBirth) || dateOfBirth.equals("Ngày sinh")) {
            showMessage("Vui lòng chọn ngày sinh");
        } else if (Validation.isNullOrEmpty(gender)) {
            showMessage("Vui lòng chọn giới tính");
        } else if (Validation.isNullOrEmpty(phoneNumber)) {
            showMessage("Vui lòng nhập số điện thoại");
        } else if (!Validation.isPhoneValid(phoneNumber)) {
            showMessage("Định dạng số điện thoại không hợp lệ!");
        } else if (Validation.isNullOrEmpty(address)) {
            showMessage("Vui lòng nhập địa chỉ");
        }
    }

    public ApptPatientDto getPatientDto() {
        String fullname = txtFulname.getText().toString();
        String dateOfBirth = txtDateOfBirth.getText().toString();
        String gender = getGenderValue(rgGender.getCheckedRadioButtonId());
        String address = txtHomeAddress.getText().toString();
        String phoneNumber = txtPhoneNumber.getText().toString();
        if (Validation.isNullOrEmpty(fullname) ||
                Validation.isNullOrEmpty(dateOfBirth) ||
                dateOfBirth.equals("Ngày sinh") ||
                Validation.isNullOrEmpty(gender) ||
                Validation.isNullOrEmpty(address) ||
                Validation.isNullOrEmpty(phoneNumber) ||
                !Validation.isPhoneValid(phoneNumber))
            return null;
        patientDto.setAddress(address.trim());
        patientDto.setDateOfBirth(dateOfBirth.trim());
        patientDto.setGender(gender.trim());
        patientDto.setName(fullname.trim());
        patientDto.setPhone(phoneNumber.trim());

        return patientDto;
    }
}

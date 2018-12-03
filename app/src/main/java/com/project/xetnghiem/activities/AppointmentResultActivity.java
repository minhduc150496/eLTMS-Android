package com.project.xetnghiem.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.ResultHeaderAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.services.ResultService;
import com.project.xetnghiem.models.LabTestingIndex;
import com.project.xetnghiem.models.LabTestting;
import com.project.xetnghiem.models.Result;
import com.project.xetnghiem.models.ResultOfAppointmentDto;
import com.project.xetnghiem.models.ResultView;
import com.project.xetnghiem.models.SampleGetting;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AppointmentResultActivity extends BaseActivity {
    private ListView listView;
    private ResultHeaderAdapter adapter;
    private List<ResultView> listResultView;
    private ResultOfAppointmentDto resultOfAppointmentDto;

    private TextView tvName;
    private TextView tvPatientYear;
    private TextView tvDateReg;
    private TextView tvTimeReg;
    private TextView tvAddress;
    private TextView tvGender;
    private TextView txtLink;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_appointment_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getMainTitle() {
        return "Kết quả xét nghiệm";
    }

    @Override
    public void bindView() {
        listResultView = new ArrayList<>();
        listView = findViewById(R.id.listview_result);
        adapter = new ResultHeaderAdapter(this, listResultView);
        listView.setAdapter(adapter);
        tvAddress = findViewById(R.id.tv_address);
        tvGender = findViewById(R.id.tv_gender);
        tvName = findViewById(R.id.tv_name);
        tvDateReg = findViewById(R.id.tv_date_register);
        tvTimeReg = findViewById(R.id.tv_time_register);
        tvPatientYear = findViewById(R.id.tv_birth_year);
        txtLink = findViewById(R.id.link);
        txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(AppointmentResultActivity.this,
                        "http://eltms.azurewebsites.net/UserWeb/Appointment/Suggest");
            }
        });
    }

    // Perform calling APIs...
    @Override
    protected void callDataResource() {
        showLoading();
        Intent intent = getIntent();
        int apptId = intent.getIntExtra(ShowAppointmentActivity.APPT_ID, -1);
        if (apptId == -1) {
            showMessage("Appointment id doesnt exists");
            return;
        }
        APIServiceManager.getService(ResultService.class)
                .getByAppointment(apptId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<ResultOfAppointmentDto>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<ResultOfAppointmentDto> response) {
                        resultOfAppointmentDto = response.body();
                        updateUIData(response.body());
                    }
                });

    }

    private List<ResultView> convertToListResultView(ResultOfAppointmentDto dto) {
        List<ResultView> list = new ArrayList<>();
        for (SampleGetting sampleGetting : dto.getListSampleGetting()) {
            for (LabTestting labTestting : sampleGetting.getListLabTesting()) {
                ResultView resultView = new ResultView();
                resultView.setHeader(true);
                resultView.setHeaderName(labTestting.getLabTestName());
                list.add(resultView);
                for (LabTestingIndex labTestingIndex : labTestting.getListLabTestingIndex()) {
                    ResultView rs = new ResultView();
                    rs.setHeaderName("");
                    rs.setHeader(false);
                    rs.setIndexName(labTestingIndex.getIndexName());
                    rs.setLabTestingIndexId(labTestingIndex.getLabTestingIndexId());
                    rs.setLowNormalHigh(labTestingIndex.getLowNormalHigh());
                    rs.setNormalRange(labTestingIndex.getNormalRange());
                    rs.setUnit(labTestingIndex.getUnit());
                    rs.setIndexValue(labTestingIndex.getIndexValue());
                    list.add(rs);
                }
            }
        }
        return list;
    }

    @Override
    public void updateUIData(Object obj) {
        ResultOfAppointmentDto dto = (ResultOfAppointmentDto) obj;
        tvAddress.setText("Địa chỉ: " + dto.getPatientAddress());
        tvGender.setText("Giới tính: " + getGenderInVN(dto.getPatientGender()));
        tvName.setText("Họ tên: " + dto.getPatientName());
        String regDate = DateUtils.changeDateFormat(dto.getEnterTime(), DateTimeFormat.DATE_TIME_DB_3, DateTimeFormat.DATE_TIME_DB_2);
        tvDateReg.setText("Ngày ĐK: " + regDate);
        String regTime = DateUtils.changeDateFormat(dto.getEnterTime(), DateTimeFormat.DATE_TIME_DB_3, DateTimeFormat.TIME_APP_2);
        tvTimeReg.setText("Giờ ĐK: " + regTime);
        tvPatientYear.setText("Năm sinh: " + dto.getPatientBirthYear());
        listResultView.clear();
        listResultView.addAll(convertToListResultView(dto));
        adapter.notifyDataSetChanged();
    }

    private String getGenderInVN(String val){
        switch (val){
            case "MALE":return "Nam";
            case "FEMALE" : return "Nữ";
            default: return "Khác";
        }
    }
    void open(Activity activity, String url) {
        Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        if (i.resolveActivity(activity.getPackageManager()) == null) {
            i.setData(Uri.parse(url));
        }
        activity.startActivity(i);
    }
}

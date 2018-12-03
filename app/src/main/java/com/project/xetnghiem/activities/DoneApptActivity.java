package com.project.xetnghiem.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.responseObj.ResponseMessage;
import com.project.xetnghiem.api.services.SampleService;
import com.project.xetnghiem.models.SampleGettingNurse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DoneApptActivity extends BaseActivity {
    Button btnChangeStatus;
    Button btnSearch;
    EditText edtSearch;
    TextView txtSampleCode;
    TextView txtSampleType;
    TextView txtCustomerName;
    TextView txtError;
    private int sampleId = -1;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_done_appt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeState(true);
    }

    @Override
    public String getMainTitle() {
        return "Y tá";
    }

    @Override
    public void bindView() {
        btnChangeStatus = findViewById(R.id.btn_change_status);
        btnSearch = findViewById(R.id.btn_search_sample);
        edtSearch = findViewById(R.id.edt_search_sample);
        txtSampleCode = findViewById(R.id.txt_sample_code);
        txtSampleType = findViewById(R.id.txt_sample_type);
        txtCustomerName = findViewById(R.id.txt_cust_name);
        txtError = findViewById(R.id.txt_error);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(edtSearch.getText().toString().trim());
            }
        });
        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DoneApptActivity.this);
                dialogBuilder.setMessage("Bạn có chắc chắn không?")
                        .setPositiveButton("Có", (dialogInterface, i) -> {
                            if (sampleId != -1) {
                                update(sampleId);
                            }
                        }).setNegativeButton("Không",null);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }

    public void changeState(boolean resultNull) {
        if (!resultNull) {
            txtError.setVisibility(View.INVISIBLE);
            txtCustomerName.setVisibility(View.VISIBLE);
            txtSampleCode.setVisibility(View.VISIBLE);
            txtSampleType.setVisibility(View.VISIBLE);
        } else {
            txtError.setVisibility(View.VISIBLE);
            txtCustomerName.setVisibility(View.INVISIBLE);
            txtSampleCode.setVisibility(View.INVISIBLE);
            txtSampleType.setVisibility(View.INVISIBLE);
        }
    }

    public void setData(int code, String type, String name) {
        txtSampleCode.setText("Mã vạch: " + code);
        txtCustomerName.setText("Tên bệnh nhân: " + name);
        txtSampleType.setText("Loại mẫu: " + type);
    }

    public void search(String searchVal) {
        showLoading();
        APIServiceManager.getService(SampleService.class)
                .searchSample(searchVal)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<SampleGettingNurse>(this) {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    protected void onResponseSuccess(Response<SampleGettingNurse> response) {
                        changeState(response.body() == null);
                        if(response.body()!=null){
                        sampleId = response.body().getSampleGettingId();
                        setData(
                                response.body().getSampleGettingCode(),
                                response.body().getSampleName(),
                                response.body().getPatientName()
                        );
                    }
                    }
                });
    }

    public void update(int id) {
        showLoading();
        APIServiceManager.getService(SampleService.class)
                .updateStatus(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<ResponseMessage>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    protected void onResponseSuccess(Response<ResponseMessage> response) {
                        if (response.body() != null) {
//                            showMessage(response.body().getMessage());
                            AlertDialog.Builder builder = new AlertDialog.Builder(DoneApptActivity.this);
                            builder.setMessage("Cập nhật thành công!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            setData(0, "", "");
                            changeState(true);
                        }
                    }
                });

    }

    @Override
    public void updateUIData(Object obj) {

    }
}

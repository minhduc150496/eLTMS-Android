package com.project.xetnghiem.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.AppointmentAdapter;
import com.project.xetnghiem.adapter.AppointmentHeaderAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.responseObj.ResponseMessage;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.AppointmentService;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.models.Patient;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.Utils;

import java.io.Serializable;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ShowAppointmentActivity extends BaseActivity implements AppointmentAdapter.AppointmentAdapterListener {
//    private SwipeMenuListView swipeMenuListView;
    private ListView listView;
    private AppointmentAdapter adapter;
    private List<Appointment> listAppointment;
    private final int EDIT_INDEX = 0;
    private final int DELETE_INDEX = 1;
    private final int SHOW_INDEX = 2;
    public static final String LIST_LABTEST_ID = "LIST_LABTEST_ID";
    public static final String APPT_ID = "APPT_ID";
    public static final String LIST_APPT_DETAIL = "LIST_APPT_DETAIL";
    public static final String APPT_DETAIL = "APPT_DETAIL";

    @Override
    protected int getLayoutView() {
        return R.layout.activity_show_patient_appt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (listAppointment == null) {
            listAppointment = new ArrayList<>();
        }
        adapter = new AppointmentAdapter(this, listAppointment,this);
        listView.setAdapter(adapter);
    }

    @Override
    public String getMainTitle() {
        return "Lịch hẹn";
    }

    @Override
    public void bindView() {
        listView = findViewById(R.id.list_appointment);
    }

    public void deleteAppt(int appointmentId) {
        showLoading();
        APIServiceManager.getService(AppointmentService.class).
                deleteAppointment(appointmentId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<ResponseMessage>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<ResponseMessage> response) {
                        if (response.body().isSuccess()) {
                            showMessage(response.body().getMessage());
                            callDataResource();
                        } else {
                            showMessage(response.body().getMessage());
                        }

                    }
                });
    }

    @Override
    protected void callDataResource() {
        showLoading();
        Patient patient = CoreManager.getPatient(this);
        if (patient == null) {
            showMessage("Patient null");
            return;
        }
        APIServiceManager.getService(AppointmentService.class)
                .getPatientAppointment(patient.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<List<Appointment>>(this) {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               protected void onResponseSuccess(Response<List<Appointment>> listResponse) {
                                   if (listResponse.body() != null && listResponse.body().size()>0) {
                                       listAppointment.clear();
                                       listAppointment.addAll(listResponse.body());
                                       adapter.notifyDataSetChanged();
                                   }else{
                                       showMessage("Không có dữ liệu");
                                   }

                               }
                           }
                );
    }

    @Override
    public void onDeleteClick(View v, Appointment appt, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowAppointmentActivity.this);
        builder.setMessage("Bạn có muốn xóa?")
                .setNegativeButton("Hủy",(dialogInterface, i) -> {} )
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAppt(appt.getAppointmentId());
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onViewClick(View v, Appointment appt, int position) {
        Intent intentShow = new Intent(ShowAppointmentActivity.this,
                AppointmentResultActivity.class);
        intentShow.putExtra(APPT_ID, appt.getAppointmentId());
//        intentShow.putExtra(APPT_ID,418);
        startActivity(intentShow);
    }

    @Override
    public void onEditClick(View v, Appointment appt, int position) {
        Intent intent = new Intent(ShowAppointmentActivity.this,
                EditAppointmentActivity.class);
        ArrayList<Integer> listLabtestId = new ArrayList<>();
        for (AppointmentDetail d : appt.getListApptDetail()) {
            if (d.getLabTestIds() != null) {
                listLabtestId.addAll(d.getLabTestIds());
            }
        }
        intent.putExtra(LIST_LABTEST_ID, listLabtestId);
        Bundle b = new Bundle();
        b.putSerializable(LIST_APPT_DETAIL, appt);
        intent.putExtras(b);

        startActivity(intent);
    }

    @Override
    public void onDetailClick(View v, Appointment appt, int position) {
        Intent intent = new Intent(ShowAppointmentActivity.this,
                ApptDetailActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(APPT_DETAIL, appt);
        intent.putExtras(b);
        startActivity(intent);
    }
}

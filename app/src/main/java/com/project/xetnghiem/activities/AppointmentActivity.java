//package com.project.xetnghiem.activities;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import com.project.xetnghiem.R;
//import com.project.xetnghiem.adapter.AppointmentAdapter;
//import com.project.xetnghiem.models.Appointment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AppointmentActivity extends BaseActivity {
//    private RecyclerView rcvAppointment;
//private AppointmentAdapter appointmentAdapter;
//private List<Appointment> appointmentList;
//    @Override
//    protected int getLayoutView() {
//        return (R.layout.activity_appointment);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        updateUIData(3);
//    }
//
//    @Override
//    public String getMainTitle() {
//        return "Lịch hẹn";
//    }
//
//    @Override
//    public void bindView() {
//        rcvAppointment = findViewById(R.id.rcv_appointment);
//
//        rcvAppointment.setNestedScrollingEnabled(true);
//        rcvAppointment.setLayoutManager(new LinearLayoutManager(this));
//    }
//
//    @Override
//    public void callDataResource() {
//
//    }
//
//    @Override
//    public void updateUIData(Object obj) {
//        appointmentList = new ArrayList<>();
////        appointmentList.add(new Appointment("time 1", "type 1", "finish"));
////        appointmentList.add(new Appointment("time 2", "type 2", "finish"));
////        appointmentList.add(new Appointment("time 3", "type 3", "finish"));
////        appointmentList.add(new Appointment("time 4", "type 4 ", "finish"));
////        appointmentList.add(new Appointment("time 5", "type 5", "finish"));
////        appointmentList.add(new Appointment("time 6", "type 6", "finish"));
////        if (appointmentAdapter == null) {
////            appointmentAdapter = new AppointmentAdapter(this, appointmentList);
////            rcvAppointment.setAdapter(appointmentAdapter);
////
////        }else {
////            appointmentAdapter.notifyDataSetChanged();
////        }
//    }
//}

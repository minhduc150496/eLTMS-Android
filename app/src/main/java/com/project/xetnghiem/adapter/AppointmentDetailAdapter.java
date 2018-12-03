package com.project.xetnghiem.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.AppointmentDetail;

import java.net.ConnectException;
import java.util.List;

public class AppointmentDetailAdapter  extends BaseAdapter {
    private List<AppointmentDetail> list =null;
private Context context;
    public AppointmentDetailAdapter(List<AppointmentDetail> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)list.get(i).getSampleId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_appointment_detail,viewGroup,false );
        }
        TextView textSampleName = view.findViewById(R.id.txt_sample_type);
        TextView textStartTime = view.findViewById(R.id.txt_start_time);
        AppointmentDetail appointmentDetail = list.get(position);
        textSampleName.setText(appointmentDetail.getSampleName());
        textStartTime.setText(appointmentDetail.getStartTime());
        return view;
    }
}

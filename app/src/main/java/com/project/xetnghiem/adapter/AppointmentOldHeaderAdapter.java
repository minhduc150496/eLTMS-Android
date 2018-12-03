package com.project.xetnghiem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentOldHeaderAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private List<AppointmentDetail> data = new ArrayList<>();
    private LayoutInflater inflater;

    public AppointmentOldHeaderAdapter(Context context, List<AppointmentDetail> list) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = list;
    }

//    public void addItem(final AppointmentDetail appointment) {
//        data.add(appointment);
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getSampleId();
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    String tmp = "";

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        AppointmentDetail appointment = (AppointmentDetail) getItem(i);
        String date = appointment.getStartTime();
//        String dateAfterFormat = DateUtils.changeDateFormat(date, DateTimeFormat.TIME_DB_1, DateTimeFormat.TIME_APP_1);
        int rowType = appointment.isHeader() ? TYPE_SEPARATOR : TYPE_ITEM;
//        if (!tmp.equals(dateAfterFormat)) {
//            tmp = dateAfterFormat;
//            rowType = 1;
//        }
        if (view == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    view = inflater.inflate(R.layout.item_appointment_detail, null);
                    holder.textSampleName = view.findViewById(R.id.txt_sample_type);
                    holder.textStartTime = view.findViewById(R.id.txt_start_time);
//                    holder.btnDelete = view.findViewById(R.id.btn_delete_appt);
//                    holder.btnUpdate = view.findViewById(R.id.btn_edit_appt);
                    break;
                case TYPE_SEPARATOR:
                    view = inflater.inflate(R.layout.sniplet_item2, null);
                    holder.textView = view.findViewById(R.id.textSeparator);
                    holder.textSampleName = view.findViewById(R.id.txt_sample_type);
                    holder.textStartTime = view.findViewById(R.id.txt_start_time);
                    break;
            }
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder.textView != null) {
            holder.textView.setText(date);
        }
        if (holder.textSampleName != null) {
            holder.textSampleName.setText(appointment.getSampleName());
        }
        if (holder.textStartTime != null) {
            holder.textStartTime.setText(appointment.getStartTime());
        }
//        if (holder.btnDelete != null) {
//            holder.btnDelete.setOnClickListener((v)->{});
//        }
//        if (holder.btnUpdate != null) {
//            holder.btnUpdate.setOnClickListener((v)->{});
//        }
        return view;
    }

    private static class ViewHolder {
        public TextView textView;
        public TextView textSampleName;
        public TextView textStartTime;
//        public ImageButton btnDelete;
//        public ImageButton btnUpdate;
    }
}

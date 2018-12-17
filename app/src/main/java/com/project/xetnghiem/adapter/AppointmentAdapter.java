package com.project.xetnghiem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends BaseAdapter {
    public static final int TYPE_NEW = 0;
    public static final int TYPE_DONE = 1;
    public static final int TYPE_PROCESS = 2;
    private AppointmentAdapterListener appointmentAdapterListener;

    private Context context;
    private List<Appointment> data;
    private LayoutInflater inflater;

    public AppointmentAdapter(Context context, List<Appointment> list,
                              AppointmentAdapterListener appointmentAdapterListener) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = list;
        this.context = context;
        this.appointmentAdapterListener = appointmentAdapterListener;
    }

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
        return data.get(i).getAppointmentId();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
      return (((Appointment) getItem(position))
                .getStatus().equals(AppConst.APPOINTMENT_STATUS_NEW)) ? TYPE_NEW :
              (((Appointment) getItem(position))
                        .getStatus().equals(AppConst.APPOINTMENT_STATUS_DONE) ? TYPE_DONE : TYPE_PROCESS);
    }

    String tmp = "";

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        AppointmentAdapter.ViewHolder holder = null;
        Appointment appointment = (Appointment) getItem(position);
        if (view == null) {
            holder = new AppointmentAdapter.ViewHolder();
            view = inflater.inflate(R.layout.item_appointment, null);
            holder.txtAppointmentCode = view.findViewById(R.id.txt_appointment_code);
            holder.labtestLinear = view.findViewById(R.id.labtest_linear);
            holder.txtStatus = view.findViewById(R.id.txt_status);
            holder.btnDelete = view.findViewById(R.id.btn_delete);
            holder.btnEdit = view.findViewById(R.id.btn_edit);
            holder.btnView = view.findViewById(R.id.btn_view_result);
            holder.btnDetail = view.findViewById(R.id.btn_detail);

//            switch (rowType) {
//                case TYPE_NEW:
//                    view = inflater.inflate(R.layout.item_appointment_detail, null);
//                    holder.textSampleName = view.findViewById(R.id.txt_sample_type);
//                    holder.textStartTime = view.findViewById(R.id.txt_start_time);
//                    view.setEnabled(false);
////                    holder.btnDelete = view.findViewById(R.id.btn_delete_appt);
////                    holder.btnUpdate = view.findViewById(R.id.btn_edit_appt);
//                    break;
//                case TYPE_OLD:
//
//                    view = inflater.inflate(R.layout.sniplet_item2, null);
////                    view.setEnabled(false);
//                    holder.textView = view.findViewById(R.id.textSeparator);
//                    holder.textSampleName = view.findViewById(R.id.txt_sample_type);
//                    holder.textStartTime = view.findViewById(R.id.txt_start_time);
//                    break;
//            }
            view.setTag(holder);
        } else {
            holder = (AppointmentAdapter.ViewHolder) view.getTag();
        }
        holder.btnDelete.setOnClickListener((v) -> {
            appointmentAdapterListener.onDeleteClick(v, appointment, position);
        });
        holder.btnEdit.setOnClickListener((v) -> {
            appointmentAdapterListener.onEditClick(v, appointment, position);
        });
        holder.btnView.setOnClickListener((v) -> {
            appointmentAdapterListener.onViewClick(v, appointment, position);
        });
        holder.btnDetail.setOnClickListener((v) -> {
            appointmentAdapterListener.onDetailClick(v, appointment, position);
        });

        switch (getItemViewType(position)) {
            case TYPE_NEW:
                holder.btnView.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.txtStatus.setText("Mới tạo");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.color_green_500));
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_positive));
                break;
            case TYPE_DONE:
                holder.btnView.setVisibility(View.VISIBLE);
                holder.btnDelete.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.txtStatus.setText("Hoàn tất");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.color_deep_orange_500));
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_negative));
                break;
            case TYPE_PROCESS:
                holder.btnView.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.GONE);
                holder.txtStatus.setText("Đang xử lí");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.color_blue_grey_300));
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_neutral));
                break;

        }
        if (holder.txtAppointmentCode != null) {
            holder.txtAppointmentCode.setText("Mã: " + appointment.getAppointmentCode());
        }
        int index = 0;
        holder.labtestLinear.removeAllViews();
        for (AppointmentDetail ad : appointment.getListApptDetail()) {
            TextView t = new TextView(context);
            t.setPadding(0, (int) Utils.convertDpToPixel(5, context), 0, 0);
            if(index >= 3){
                t.setText("...");
                break;
            }else
            {
                t.setText(ad.getSampleName());
            }
            t.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fiber_manual_record_black_24dp, 0, 0, 0);
            holder.labtestLinear.addView(t);
            index++;
        }
        return view;
    }

    private static class ViewHolder {
        public TextView txtAppointmentCode;
        public TextView txtStatus;
        public ImageButton btnEdit;
        public ImageButton btnDetail;
        public ImageButton btnView;
        public ImageButton btnDelete;
        public LinearLayout labtestLinear;
    }

    public interface AppointmentAdapterListener {
        void onDeleteClick(View v, Appointment appt, int position);

        void onViewClick(View v, Appointment appt, int position);

        void onEditClick(View v, Appointment appt, int position);

        void onDetailClick(View v, Appointment appt, int position);
    }

}

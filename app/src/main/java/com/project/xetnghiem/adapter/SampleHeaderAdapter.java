package com.project.xetnghiem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class SampleHeaderAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private List<LabTest> data  ;
    private LayoutInflater inflater;

    public SampleHeaderAdapter(Context context, List<LabTest> list, OnChangeChkListener onChangeChkListener) {
        this.onChangeChkListener = onChangeChkListener;
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
        return data.get(i).getLabTestId();
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    String tmp = "";

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LabTest labTest= (LabTest) getItem(i);
        String sampleName =labTest.getSampleName();
//        String dateAfterFormat = DateUtils.changeDateFormat(date, DateTimeFormat.DATE_TIME_DB_3, DateTimeFormat.DATE_APP_2);
        int rowType = labTest.isHeader() ? TYPE_SEPARATOR : TYPE_ITEM;
//        if (!tmp.equals(dateAfterFormat)) {
//            tmp = dateAfterFormat;
//            rowType = 1;
//        }
//        if (view == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    view = inflater.inflate(R.layout.item_sample_appointment, null);
                    holder.chkSampleItem = view.findViewById(R.id.chk_sample_item);
                    holder.textHeader = view.findViewById(R.id.txt_header_sample);
                    break;
                case TYPE_SEPARATOR:
                    view = inflater.inflate(R.layout.item_header_sample, null);
                    holder.textHeader = view.findViewById(R.id.txt_header_sample);
                    holder.chkSampleItem = view.findViewById(R.id.chk_sample_item);
                    break;
            }
            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
        if (holder.textHeader != null) {
            holder.textHeader.setText(sampleName);
        }
        if (holder.chkSampleItem != null) {
            holder.chkSampleItem.setText(labTest.getLabTestName());
            holder.chkSampleItem.setChecked(labTest.isChecked());
            holder.chkSampleItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    labTest.setChecked(b);
                    onChangeChkListener.onChange(labTest.getLabTestId(), b);
                }
            });
        }
        return view;
    }

    private static class ViewHolder {
        public TextView textHeader;
        public CheckBox chkSampleItem;
    }
 public  OnChangeChkListener onChangeChkListener;
    public interface OnChangeChkListener{
        void onChange(int position, boolean status);
    }
}

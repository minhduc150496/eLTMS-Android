package com.project.xetnghiem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.xetnghiem.models.District;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.models.Slot;

import java.util.List;

public class TimeSpinnerAdapter extends ArrayAdapter { List<Slot> list;
    public TimeSpinnerAdapter(@NonNull Context context,  List<Slot> list) {
        super(context,android.R.layout.simple_spinner_item, list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    public int getPosition(@Nullable int slotId) {
        for(Slot s : list){
            if(s.getSlotId() == slotId){
                return super.getPosition(s);
            }
        }
        return -1;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    public View getCustomView(int position, View converView, ViewGroup parent){
        View v = LayoutInflater.from(getContext())
                .inflate(android.R.layout.simple_spinner_item, parent, false);
        TextView textView = v.findViewById(android.R.id.text1);
//        SampleDto d = (SampleDto) getItem(position);
        Slot crrRow =(Slot) getItem(position);
        if (crrRow != null) {
            textView.setText(crrRow.getFmStartTime() + " - " + crrRow.getFmFinishTime());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
//    BookSampleAdapter.SpinnerSelectLisenter lisenter
//    interface SpinnerSelectListener{
//        void onSelect(String row);
//    }
}

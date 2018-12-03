package com.project.xetnghiem.adapter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.models.Slot;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookSampleAdapter extends BaseAdapter {
    private List<SampleDto> sampleDtoList;
    private LayoutInflater inflater;
    private Context context;

    public BookSampleAdapter(Context context, List<SampleDto> list, SpinnerSelectLisenter lisenter) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sampleDtoList = list;
        this.lisenter = lisenter;
    }

    public int getCount() {
        return sampleDtoList.size();
    }

    @Override
    public Object getItem(int i) {
        return sampleDtoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return sampleDtoList.get(i).getSampleId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        SampleDto dto = (SampleDto) getItem(i);
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_book_sampletest, null);
            holder.txtDate = view.findViewById(R.id.txt_sample_date
            );
            holder.txtSampleName = view.findViewById(R.id.txt_sample_name);
            holder.spnTime = view.findViewById(R.id.spn_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder.txtSampleName != null) {
            holder.txtSampleName.setText(dto.getSampleName());

        }
        if (holder.txtDate != null) {
            final TextView txtDateFinal = holder.txtDate;
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            holder.txtDate.setText(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_APP));

            holder.txtDate.setOnClickListener((vw) ->
            {
                DatePickerDialog dialog = new DatePickerDialog(context,
                        (DatePicker datePicker, int iYear, int iMonth, int iDay) -> {
                            String date = iDay + "/" + (iMonth + 1) + "/" + iYear;
                            c.set(iYear, iMonth, iDay, 23, 59);
                            txtDateFinal.setText(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_APP));
                            dto.setDateStr(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_TIME_DB_2));
                            int a = 0;
                        }, year, month, day);
                dialog.show();
            });

        }
        if (holder.spnTime != null) {
            int duration = dto.getSampleDuration()/60;
            int startTime = (int) (dto.getOpenTime()/60);
            int endTime = (int) (dto.getCloseTime()/60);
            List<String> lst = new ArrayList<>();
            for (int index = startTime; index < endTime; index += duration) {
                String time = Utils.getTimeInStr(index);
                String time2 = Utils.getTimeInStr(index + duration);
//                System.out.println("X " + index);
//                System.out.println("Time: " + time + " - " +time2);
                String tmp = time + " - " + time2;
                lst.add(tmp);
            }
            TimeSpinnerAdapter adapter = new TimeSpinnerAdapter(context, dto.getSlotDtos());
            holder.spnTime.setAdapter(adapter);
            holder.spnTime.setSelection(adapter.getPosition(dto.getSelectedSlotId()));
            holder.spnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    lisenter.onClick((Slot ) adapter.getItem(i), dto.getSampleId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        return view;
    }

    private static class ViewHolder {
        public TextView txtDate;
        public TextView txtSampleName;
        public Spinner spnTime;
    }

    SpinnerSelectLisenter lisenter;

    public interface SpinnerSelectLisenter {
        void onClick(Slot data, int sampleId);
    }

}

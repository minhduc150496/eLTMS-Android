package com.project.xetnghiem.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.models.ResultView;

import java.util.List;

public class ResultHeaderAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private List<ResultView> list;
    private Context context;
    private LayoutInflater inflater;

    public ResultHeaderAdapter(Context context, List<ResultView> list) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.list = list;
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
        return list.get(i).getLabTestingIndexId();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return ((ResultView) getItem(position)).isHeader() ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    String tmp = "";

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ResultHeaderAdapter.MyViewHolder holder = null;
        ResultView resultView = (ResultView) getItem(position);
//        String dateAfterFormat = DateUtils.changeDateFormat(date, DateTimeFormat.TIME_DB_1, DateTimeFormat.TIME_APP_1);
        int rowType = getItemViewType(position);

//        if (view == null) {
            holder = new ResultHeaderAdapter.MyViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    view = inflater.inflate(R.layout.item_result, null);
                    holder.txtLow = view.findViewById(R.id.txt_lower_result);
                    holder.txtHigh = view.findViewById(R.id.txt_high_result);
                    holder.txtNormal = view.findViewById(R.id.txt_normal_result);
                    holder.txtIndexName = view.findViewById(R.id.txt_result_name);
                    holder.txtRange = view.findViewById(R.id.txt_result_range);
                    break;
                case TYPE_SEPARATOR:
                    view = inflater.inflate(R.layout.item_result_header, null);
                    view.setEnabled(false);
                    holder.txtHeaderName = view.findViewById(R.id.txt_headername_result);
                    break;
            }
//            view.setTag(holder);
//        } else {
//            holder = (ResultHeaderAdapter.MyViewHolder) view.getTag();
//        }
        if (holder.txtIndexName != null) {
            holder.txtIndexName.setText(resultView.getIndexName());
        }
        if (holder.txtHeaderName != null) {
            holder.txtHeaderName.setText(resultView.getHeaderName());
        }

        if (resultView.getLowNormalHigh()!= null && resultView.getLowNormalHigh().equals("L")) {
            if (holder.txtLow != null) {
                holder.txtLow.setText(resultView.getIndexValue());
                holder.txtLow.setPaintFlags(holder.txtLow.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
        } else if (resultView.getLowNormalHigh()!= null && resultView.getLowNormalHigh().equals("N")) {
            if (holder.txtNormal != null) {
                holder.txtNormal.setText(resultView.getIndexValue());
            }
        } else if (resultView.getLowNormalHigh()!= null && resultView.getLowNormalHigh().equals("H")) {
            if (holder.txtHigh != null) {
                holder.txtHigh.setText(resultView.getIndexValue());
                holder.txtHigh.setPaintFlags( holder.txtHigh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
        }
        if (holder.txtRange != null) {
            holder.txtRange.setText(resultView.getNormalRange()+resultView.getUnit());
        }
        return view;
    }

    public class MyViewHolder {
        public TextView txtIndexName;
        public TextView txtHeaderName;
        public TextView txtLow;
        public TextView txtNormal;
        public TextView txtHigh;
        public TextView txtRange;
    }
}

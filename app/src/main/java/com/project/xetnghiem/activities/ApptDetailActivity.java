package com.project.xetnghiem.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.utilities.Utils;

public class ApptDetailActivity extends BaseActivity {
    private Appointment appt;
    private LinearLayout linearSampleList;
    private LinearLayout linearLabTestList;
    private TextView txtTotalPrice;

    @Override
    protected int getLayoutView() {
        return (R.layout.activity_appt_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        appt =(Appointment)b.get(ShowAppointmentActivity.APPT_DETAIL);
        updateUIData();
    }

    @Override
    public String getMainTitle() {
        return "Chi tiết lịch hẹn";
    }

    @Override
    public void bindView() {
        linearLabTestList = findViewById(R.id.linear_lab_test_list);
        linearSampleList = findViewById(R.id.linear_sample_list);
        txtTotalPrice = findViewById(R.id.txt_total_price);
    }

    @Override
    public void updateUIData() {
        float total = 0;
        for (AppointmentDetail ad : appt.getListApptDetail()) {
            TextView tvStartDate, tvSampleName, tvTime, tvDateText, tvAt;
            tvStartDate = new TextView(this);
            tvSampleName = new TextView(this);
            tvTime = new TextView(this);
            tvDateText = new TextView(this);
            tvAt = new TextView(this);

            tvSampleName.setText(ad.getSampleName() + ": ");
            tvSampleName.setTypeface(null, Typeface.BOLD);
            tvSampleName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fiber_manual_record_black_24dp, 0, 0, 0);
            tvDateText.setText(" ngày ");
            tvStartDate.setText(ad.getGettingDate());
            tvStartDate.setTypeface(null, Typeface.BOLD);;
            tvStartDate.setTextColor(ContextCompat.getColor(this, R.color.color_red_500));
            tvAt.setText(" lúc ");
            tvTime.setText(ad.getFmStartTime() + " - " + ad.getFmFinishTime());
            tvTime.setTypeface(null, Typeface.BOLD);;
            tvTime.setTextColor(ContextCompat.getColor(this, R.color.color_red_500));

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.addView(tvSampleName);
            linearLayout.addView(tvDateText);
            linearLayout.addView(tvStartDate);
            linearLayout.addView(tvAt);
            linearLayout.addView(tvTime);

            linearSampleList.addView(linearLayout);
            for (LabTest labTest : ad.getLabTests()) {
                if(labTest == null) {return;}
                LinearLayout linearLabtest = new LinearLayout(this);
                TextView tvLabtestName = new TextView(this);
                TextView tvLabPrice = new TextView(this);
                tvLabtestName.setText(labTest.getLabTestName() + ": ");
                tvLabPrice.setText(Utils.formatMoney((long)labTest.getPrice())+" đ");
                tvLabPrice.setTextColor(ContextCompat.getColor(this, R.color.color_blue_500));
                tvLabtestName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fiber_manual_record_black_24dp, 0, 0, 0);
                linearLabtest.addView(tvLabtestName);
                linearLabtest.addView(tvLabPrice);
                linearLabTestList.addView(linearLabtest);
                total += labTest.getPrice();
            }
        }
        txtTotalPrice.setText(Utils.formatMoney((long)total)+" đ");
    }


}

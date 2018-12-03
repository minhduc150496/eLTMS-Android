package com.project.xetnghiem.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.project.xetnghiem.R;

public class MainActivity extends BaseActivity {
    private View gridBookAppt;
    private View gridAccount;
    private View gridTreatHistory;
    private View gridApptHistory;
    private View gridResult;
    private ImageButton btnBack;


    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public String getMainTitle() {
        return "Trang chủ ";
    }

    @Override
    public void bindView() {

        gridBookAppt = findViewById(R.id.grid_book_appt);
        gridAccount = findViewById(R.id.grid_account);
        gridTreatHistory = findViewById(R.id.grid_treatment_history);
        gridApptHistory = findViewById(R.id.grid_appt_history);
        gridResult = findViewById(R.id.grid_result);
        btnBack = findViewById(R.id.btn_toolbar_back);

        gridBookAppt.setOnClickListener((v)->{
            redirectToActivity(BookApptActivity.class, false);
        });
        gridAccount.setOnClickListener((v)->{
            redirectToActivity(AccountActivity.class, false);

        });
        gridTreatHistory.setOnClickListener((v)-> {
//            redirectToActivity(BookApptActivity.class, false);});
                    showMessage("Lịch sử xét nghiệm");
                });
        gridApptHistory.setOnClickListener((v)->{
            redirectToActivity(ShowAppointmentActivity.class, false);});
        gridResult.setOnClickListener((v)->{
            showMessage("Kết quả xn");
            redirectToActivity(FeedbackActivity.class, false);});


        btnBack.setOnClickListener(listener);
    }

    @Override
    public void updateUIData(Object obj) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}

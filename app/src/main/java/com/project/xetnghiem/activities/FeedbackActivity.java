package com.project.xetnghiem.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xetnghiem.R;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FeedbackActivity extends Activity {

    Dialog dialog = null;

    private TextView txtName;
    private CircleImageView imgAvatar;
    private TextView txtTreatmentContent;
    private RatingBar ratingBar;
    private AutoCompleteTextView contentFeedback;
    private Button btnOK;
    private Button btnCancel;
    private Disposable apiDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // Make us non-modal, so that others can receive touch events.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_feedback);
        setTitle("Đánh giá dịch vụ");

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
//            finish();
            return false;
        }

        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }


    public void attemptFeedback() {
        float rating = ratingBar.getRating();
        String content = contentFeedback.getText().toString().trim();
        String dateCreated = DateUtils.getCurrentDate(DateTimeFormat.DATE_TIME_DB);
        if (rating == 0) {
            Toast.makeText(FeedbackActivity.this, "Vui lòng chọn sao", Toast.LENGTH_SHORT).show();
            return;
        } else {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiDisposable != null) {
            apiDisposable.dispose();
        }
    }
}

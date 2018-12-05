package com.project.xetnghiem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.Button;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.CustomViewPager;
import com.project.xetnghiem.fragment.EditApptStep1Fragment;
import com.project.xetnghiem.fragment.EditApptStep2Fragment;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.models.SampleDto;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class EditAppointmentActivity extends BaseActivity implements EditApptStep1Fragment.DataListener {
    private Disposable appointmentDisposable;
    //    private User user;
//    private Patient patient;
    private boolean isDateValid = true;
    private CustomViewPager viewPager;
    private List<LabTest> tmpLabTest;
    private List<SampleDto> listTmpSampleDto;
    private Button btnNextStep;
    private Button btnPrevStep;
    private ArrayList<Integer> listLabTestIds = null;
    private Appointment modifiedAppointment = null;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_edit_appointment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setBackgroundDrawable(
//                    ContextCompat.getDrawable(BookApptActivity.this, R.drawable.side_nav_bar)
//            );
//        }
//        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        String mPhoneNumber = tMgr.getLine1Number();
//        tvPhone.setText(mPhoneNumber);
//        img.requestFocus();
//        tvFullname.clearFocus();

        tmpLabTest = new ArrayList<>();
        listTmpSampleDto = new ArrayList<>();

    }

    @Override
    public String getMainTitle() {
        return "Đặt lịch";
    }

    EditApptStep1Fragment fragment1;
    EditApptStep2Fragment fragment2;

    @Override
    public void bindView() {
        viewPager = findViewById(R.id.pager_sample);
        btnNextStep = findViewById(R.id.btn_next_step);
        btnPrevStep = findViewById(R.id.btn_prev_step);
        EditAppointmentActivity.ViewPagerAdapter adapter = new EditAppointmentActivity.ViewPagerAdapter(getSupportFragmentManager());
        fragment1 = new EditApptStep1Fragment();
        fragment2 = new EditApptStep2Fragment();
        Intent intent = getIntent();
        listLabTestIds = intent.getIntegerArrayListExtra(ShowAppointmentActivity.LIST_LABTEST_ID);
        Bundle b = intent.getExtras();
        modifiedAppointment =(Appointment)b.get(ShowAppointmentActivity.LIST_APPT_DETAIL);
        fragment1.setListLabTestIds(listLabTestIds);
        fragment1.setModifiedAppt(modifiedAppointment);
        adapter.addFrag(fragment1, "1");
        adapter.addFrag(fragment2, "2");
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
        btnPrevStep.setVisibility(View.INVISIBLE);
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                if (fragment2 != null) {
                    fragment2.setDataSample(listTmpSampleDto);
                    fragment2.setModifiedAppt(modifiedAppointment);
                }
                btnPrevStep.setVisibility(View.VISIBLE);
                btnNextStep.setVisibility(View.INVISIBLE);
            }
        });
        btnPrevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                btnNextStep.setVisibility(View.VISIBLE);
                btnPrevStep.setVisibility(View.INVISIBLE);
            }
        });

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    @Override
    public void callDataResource() {

    }

    @Override
    public void updateUIData(Object obj) {

    }

    private Disposable apiDisposable;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onCancelLoading() {
        if (appointmentDisposable != null) {
            appointmentDisposable.dispose();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public void onDateReceiver(List<LabTest> data, List<SampleDto> listTmpSampleDto) {
        tmpLabTest.clear();
        tmpLabTest.addAll(data);
        this.listTmpSampleDto.clear();

        for (SampleDto dto : listTmpSampleDto) {
            SampleDto d = new SampleDto();
            d.setSampleId(dto.getSampleId());
            d.setDateStr(dto.getDateStr());
            d.setTimeStr(dto.getTimeStr());
            d.setSampleDuration(dto.getSampleDuration());
            d.setOpenTime(dto.getOpenTime());
            d.setCloseTime(dto.getCloseTime());
            d.setSampleName(dto.getSampleName());
            d.setSlotDtos(dto.getSlotDtos());
            d.setSelectedSlotId(dto.getSelectedSlotId());
            this.listTmpSampleDto.add(d);
        }
        for (SampleDto dto : this.listTmpSampleDto) {
            for (LabTest labTest : tmpLabTest) {
                if (dto.getSampleId() == labTest.getSampleId()) {
                    if (dto.getLabTests() == null) {
                        dto.setLabTests(new ArrayList<>());
                    }
                    dto.getLabTests().add(labTest);
                }
            }
        } 
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

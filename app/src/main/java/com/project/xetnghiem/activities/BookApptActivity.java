package com.project.xetnghiem.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.Button;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.CustomViewPager;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.services.SlotService;
import com.project.xetnghiem.fragment.BookStep1Fragment;
import com.project.xetnghiem.fragment.BookStep2Fragment;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.models.Slot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BookApptActivity extends BaseActivity implements BookStep1Fragment.DataListener {

    private Disposable appointmentDisposable;
    //    private User user;
//    private Patient patient;
    private boolean isDateValid = true;
    private CustomViewPager viewPager;
    private List<LabTest> tmpLabTest;
    private List<SampleDto> listTmpSampleDto;
    private List<Slot> listAvailableSlots;
    private Button btnNextStep;
    private Button btnPrevStep;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_quick_register;
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

    BookStep1Fragment fragment1;
    BookStep2Fragment fragment2;

    @Override
    public void bindView() {
        viewPager = findViewById(R.id.pager_sample);
        btnNextStep = findViewById(R.id.btn_next_step);
        btnPrevStep = findViewById(R.id.btn_prev_step);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragment1 = new BookStep1Fragment();
        fragment2 = new BookStep2Fragment();
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
        if (listAvailableSlots == null) {
            listAvailableSlots = new ArrayList<>();
        }

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    @Override
    public void callDataResource() {
        SlotService slotService = APIServiceManager.getService(SlotService.class);
        slotService.getAvaiableSlots().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<Slot>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<List<Slot>> listResponse) {
                        listAvailableSlots.clear();
                        listAvailableSlots.addAll(listResponse.body());
                        fragment1.setAvailableSlots(listAvailableSlots);
                       if (fragment2 != null) {
                            fragment2.setAvailableSlots(listAvailableSlots);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
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
//call when user click on checkbox, it will add data to current list lay on the BookAppActivity
    //       FragmentStep1  ->     FragmentStep2
    //               BookAppActivity
    @Override
    public void onDateReceiver(List<LabTest> data, List<SampleDto> listTmpSampleDto) {
        tmpLabTest.clear();
        tmpLabTest.addAll(data);
        this.listTmpSampleDto.clear();
//        this.listTmpSampleDto.addAll(listTmpSampleDto);

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
        int a = 1;
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

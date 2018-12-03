package com.project.xetnghiem.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.project.xetnghiem.R;
import com.project.xetnghiem.activities.EditAppointmentActivity;
import com.project.xetnghiem.adapter.AppointmentAdapter;
import com.project.xetnghiem.adapter.AppointmentHeaderAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.responseObj.ResponseMessage;
import com.project.xetnghiem.api.services.AppointmentService;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.models.BaseContext;
import com.project.xetnghiem.models.Patient;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewAppointmentFragment extends BaseFragment implements BaseContext {
    private SwipeMenuListView listView;
    private List<AppointmentDetail> listAppt;
    private AppointmentHeaderAdapter adapter;
    private final int EDIT_INDEX = 0;
    private final int DELETE_INDEX = 1;
    public  static final  String LIST_LABTEST_ID = "LIST_LABTEST_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        listView = v.findViewById(R.id.rcv_new_appointment);
        if (listAppt == null)
            listAppt = new ArrayList<>();
        if (adapter == null) {
            adapter = new AppointmentHeaderAdapter(getContext(), listAppt);
        }
        ///test
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case AppointmentHeaderAdapter.TYPE_ITEM:
                        break;
                    case AppointmentHeaderAdapter.TYPE_SEPARATOR:
                        // create "open" item
                        SwipeMenuItem openItem = new SwipeMenuItem(
                                getContext());
                        // set item background
                        openItem.setBackground(new ColorDrawable(Color.BLUE));
                        // set item width
                        openItem.setWidth((int) Utils.convertDpToPixel((float) 90, getContext()));
                        // set item title
                        // set item title fontsize
                        openItem.setTitleSize(18);
                        openItem.setIcon(R.drawable.ic_edit_black_24dp);
                        // set item title font color
//                    openItem.setTitleColor(Color.WHITE);
                        // add to menu
                        menu.addMenuItem(openItem);

                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getContext());
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.RED));
                        // set item width
                        deleteItem.setWidth((int) Utils.convertDpToPixel((float) 90, getContext()));
                        // set a icon
                        deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                        // add to menu
                        menu.addMenuItem(deleteItem);
                        // create menu of type 1
                        break;
                }
            }
        };

// set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case EDIT_INDEX:
                        AppointmentDetail ad = listAppt.get(position);
//                        callApiCancel(ad.getAppointmentCode(), position);
                        Intent intent = new Intent(getContext(), EditAppointmentActivity.class);
                        ArrayList<Integer> listLabtestId = new ArrayList<>();
                        List<AppointmentDetail> listAd = getListApptDetailByCode(ad.getAppointmentCode());
                        for (AppointmentDetail d : listAd) {
                            if (d.getLabTestIds() != null) {
                                 listLabtestId.addAll(d.getLabTestIds());
                            }
                        }
                        intent.putExtra( LIST_LABTEST_ID , listLabtestId);
                        startActivity(intent);
                        showMessage("edit");
                        break;
                    case DELETE_INDEX:
                        AppointmentDetail ad2 = listAppt.get(position);
                        callApiCancel(ad2.getAppointmentCode(), position);
                        showMessage("delete");
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        //endtest


        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        listView.setAdapter(adapter);
//        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        callDataResource();
        return v;
    }

    public List<AppointmentDetail> getListApptDetailByCode(String code) {
        List<AppointmentDetail> list = new ArrayList<>();
        for (AppointmentDetail ad : listAppt) {
            if (!ad.isHeader() && ad.getAppointmentCode().equals(code)) {
                list.add(ad);
            }
        }
        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void bindView() {

    }

    @Override
    protected void callDataResource() {
        showLoading();
        AppointmentService service = APIServiceManager.getService(AppointmentService.class);
        Patient patient = CoreManager.getPatient(getContext());
        service.getNewApptByPatientId(patient.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<List<Appointment>>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        apiDisposable = d;
                    }

                    @Override
                    protected void onResponseSuccess(Response<List<Appointment>> appointmentResponse) {
                        if (appointmentResponse.body() != null) {
                            for (Appointment appointment : appointmentResponse.body()) {
                                if (appointment.getListApptDetail() != null && appointment.getListApptDetail().size() > 0) {
                                    listAppt.add(new AppointmentDetail(true, appointment.getAppointmentCode()));
                                    for (AppointmentDetail d : appointment.getListApptDetail()) {
                                        d.setAppointmentCode(appointment.getAppointmentCode());
                                    }
                                    listAppt.addAll(appointment.getListApptDetail());
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void callApiCancel(String appointmentCode, int position) {
        AppointmentService service = APIServiceManager.getService(AppointmentService.class);
        service.cancelAppointment(appointmentCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<ResponseMessage>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<ResponseMessage> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ResponseMessage message = response.body();
                            if (message.isSuccess()) {
                                showMessage(message.getMessage());
                                AppointmentDetail d = listAppt.get(position);
                                listAppt.removeIf(item -> item.getAppointmentCode().equals(d.getAppointmentCode()));
                                adapter.notifyDataSetChanged();
                            } else {
                                showMessage(message.getMessage());
                            }
                        }
                    }
                });
    }

    public String getDateFormat(String src) {
        String dateTmp = DateUtils.changeDateFormat(src,
                DateTimeFormat.DATE_TIME_DB_2, DateTimeFormat.TIME_APP_1);
        return dateTmp;
    }

    @Override
    public void updateUIData(Object obj) {
    }

    class AppointmentDetailSort implements Comparator<AppointmentDetail> {

        @Override
        public int compare(AppointmentDetail a1, AppointmentDetail a2) {
            String dateFormat1 = DateUtils.changeDateFormat(a1.getGettingDate(),
                    DateTimeFormat.DATE_TIME_DB_2, DateTimeFormat.DATE_APP_2);
            String dateFormat2 = DateUtils.changeDateFormat(a2.getGettingDate(),
                    DateTimeFormat.DATE_TIME_DB_2, DateTimeFormat.DATE_APP_2);
            if (dateFormat2 != null && dateFormat1 != null) {
                return dateFormat2.compareTo(dateFormat1);
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }

    private Disposable apiDisposable;

    @Override
    public void onResume() {
        super.onResume();
//        if (apiDisposable != null) {
//            apiDisposable.dispose();
//        }
    }
}

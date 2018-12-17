package com.project.xetnghiem.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.EditSampleAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.requestObj.ApptCreateRequest;
import com.project.xetnghiem.api.requestObj.ApptUpdateRequest;
import com.project.xetnghiem.api.responseObj.ResponseMessage;
import com.project.xetnghiem.api.services.AppointmentService;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.models.Slot;
import com.project.xetnghiem.utilities.Validation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class EditApptStep2Fragment extends BaseFragment {
    private AutoCompleteTextView tvPhone;
    private AutoCompleteTextView tvFullname;
    private boolean isDateValid = true;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvPrice;
    private ListView listSampleBook;
    private TextView tvDateError;
    private Button btnBook;
    View mainView;
    private Appointment modifiedAppt;
    private EditSampleAdapter adapter;
    private List<SampleDto> listSampleDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        mainView = inflater.inflate(R.layout.fragment_book_step2, container, false);
        bindView();
        if (listSampleDto == null) {
            listSampleDto = new ArrayList<>();
        }
        if (adapter == null) {
            if (getContext() != null) {

                adapter = new EditSampleAdapter(getContext(), listSampleDto, new EditSampleAdapter.SpinnerSelectLisenter() {
                    @Override
                    public void onClick(Slot data, int sampleId) {
                        SampleDto dto = findInList(sampleId);
                        if (dto != null) {
//                            dto.setSlot(data);
                            dto.setSelectedSlotId(data.getSlotId());
                        }
                    }
                });
            }
        }
        listSampleBook.setAdapter(adapter);
        return mainView;
    }

    public SampleDto findInList(int sampleId) {
        for (SampleDto dto : listSampleDto) {
            if (dto.getSampleId() == sampleId) {
                return dto;
            }
        }
        return null;
    }

    public void setDataSample(List<SampleDto> list) {
        listSampleDto.clear();
        listSampleDto.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void setModifiedAppt(Appointment modifiedAppt) {
        this.modifiedAppt = modifiedAppt;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void bindView() {
        btnBook = mainView.findViewById(R.id.btn_book);
        listSampleBook = mainView.findViewById(R.id.list_view_book_sample);
        TextView txtStep2Title = mainView.findViewById(R.id.title_appt_step2);
        txtStep2Title.setText("Chọn thời gian khám:");
        btnBook.setText("Sửa lịch");
        btnBook.setOnClickListener((view) -> {
            callApiBookAppointment();
        });
    }

    public void callApiBookAppointment() {
        ApptUpdateRequest request = new ApptUpdateRequest();
        request.setPatientId(71);
        request.setAppointmentId(modifiedAppt.getAppointmentId());
        List<ApptCreateRequest.SampleGettingDtos> list = new ArrayList<>();
        for (SampleDto dto : listSampleDto) {
            ApptCreateRequest.SampleGettingDtos dtos = new ApptCreateRequest.SampleGettingDtos();
            String dateFormat = dto.getDateStr();
            List<Integer> listIdLabTests = new ArrayList<>();
            for (LabTest labTest : dto.getLabTests()) {
                listIdLabTests.add(labTest.getLabTestId());
            }
//            dtos.setStartTime(dateFormat + " " + dtimes[0].trim());
//            dtos.setStartTime(dateFormat + " " + dtimes[1].trim());
            dtos.setLabTestIds(listIdLabTests);
            dtos.setSampleId(dto.getSampleId());
            dtos.setGetttingDate(dto.getDateStr());
            dtos.setSlotId(dto.getSelectedSlotId());
            list.add(dtos);
        }
        request.setList(list);
showLoading();
        AppointmentService service = APIServiceManager.getService(AppointmentService.class);
        service.updateAppointment(request).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<ResponseMessage>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<ResponseMessage> response) {
                        int a = 1;
                        getActivity().finish();
                    }
                });
    }

    @Override
    public void updateUIData(Object obj) {

    }

    public boolean isValidateForm() {
        boolean isAllFieldValid = true;
//        String phone = tvPhone.getText().toString().trim();
//        String note = comtvNote.getText().toString().trim();
        String txtDate = tvDate.getText().toString().trim();
        View viewFocus = null;
        if (Validation.isNullOrEmpty(txtDate)
                || (txtDate != null && txtDate.equals(getString(R.string.label_date_bookapt)))) {
            viewFocus = tvDateError;
            tvDateError.setText("Vui lòng chọn ngày");
            isAllFieldValid = false;
        }
        if (!isAllFieldValid) {
            viewFocus.requestFocus();
        }
        return isAllFieldValid && isDateValid;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

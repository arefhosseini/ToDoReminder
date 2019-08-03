package com.fearefull.todoreminder.ui.alarm_manager.picker.hour_time_picker;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.databinding.FragmentHourTimePickerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

public class HourTimePickerFragment extends BaseFragment<FragmentHourTimePickerBinding, HourTimePickerViewModel>
        implements HourTimePickerNavigator, BaseRepeatCaller {

    public static final String TAG = HourTimePickerFragment.class.getSimpleName();
    private static final String MINUTE_KEY = "minute_key";
    private static final String HOUR_KEY = "hour_key";

    @Inject
    ViewModelProviderFactory factory;
    private HourTimePickerViewModel viewModel;
    private HourTimePickerCallBack callBackForOnceRepeat, callBackForDailyRepeat, callBackForWeeklyRepeat,
            callBackForMonthlyRepeat, callBackForYearlyRepeat;

    public static HourTimePickerFragment newInstance(int minute, int hour) {
        Bundle args = new Bundle();
        HourTimePickerFragment fragment = new HourTimePickerFragment();
        args.putSerializable(MINUTE_KEY, minute);
        args.putSerializable(HOUR_KEY, hour);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hour_time_picker;
    }

    @Override
    public HourTimePickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HourTimePickerViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        assert getArguments() != null;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        viewModel.init(getArguments().getInt(MINUTE_KEY), getArguments().getInt(HOUR_KEY));
    }

    public void setCallBackForOnceRepeat(HourTimePickerCallBack callBackForOnceRepeat) {
        this.callBackForOnceRepeat = callBackForOnceRepeat;
    }

    public void setCallBackForDailyRepeat(HourTimePickerCallBack callBackForDailyRepeat) {
        this.callBackForDailyRepeat = callBackForDailyRepeat;
    }

    public void setCallBackForWeeklyRepeat(HourTimePickerCallBack callBackForWeeklyRepeat) {
        this.callBackForWeeklyRepeat = callBackForWeeklyRepeat;
    }

    public void setCallBackForMonthlyRepeat(HourTimePickerCallBack callBackForMonthlyRepeat) {
        this.callBackForMonthlyRepeat = callBackForMonthlyRepeat;
    }

    public void setCallBackForYearlyRepeat(HourTimePickerCallBack callBackForYearlyRepeat) {
        this.callBackForYearlyRepeat = callBackForYearlyRepeat;
    }

    @Override
    public void call(Repeat repeat) {
        if (repeat == Repeat.ONCE)
            callBackForOnceRepeat.getHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.DAILY)
            callBackForDailyRepeat.getHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.WEEKLY)
            callBackForWeeklyRepeat.getHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.MONTHLY)
            callBackForMonthlyRepeat.getHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.YEARLY)
            callBackForYearlyRepeat.getHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
    }

    public interface HourTimePickerCallBack {
        void getHourTimePickerResult(int minute, int hour);
    }
}

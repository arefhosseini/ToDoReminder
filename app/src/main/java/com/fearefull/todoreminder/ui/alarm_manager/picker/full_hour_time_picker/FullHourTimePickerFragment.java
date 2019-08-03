package com.fearefull.todoreminder.ui.alarm_manager.picker.full_hour_time_picker;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.databinding.FragmentFullHourTimePickerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

public class FullHourTimePickerFragment extends BaseFragment<FragmentFullHourTimePickerBinding, FullHourTimePickerViewModel>
        implements FullHourTimePickerNavigator, BaseRepeatCaller {

    public static final String TAG = FullHourTimePickerFragment.class.getSimpleName();
    private static final String MINUTE_KEY = "minute_key";
    private static final String HOUR_KEY = "hour_key";

    @Inject
    ViewModelProviderFactory factory;
    private FullHourTimePickerViewModel viewModel;
    private FullHourTimePickerCallBack callBackForOnceRepeat, callBackForDailyRepeat, callBackForWeeklyRepeat,
            callBackForMonthlyRepeat, callBackForYearlyRepeat;

    public static FullHourTimePickerFragment newInstance(int minute, int hour) {
        Bundle args = new Bundle();
        FullHourTimePickerFragment fragment = new FullHourTimePickerFragment();
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
        return R.layout.fragment_full_hour_time_picker;
    }

    @Override
    public FullHourTimePickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(FullHourTimePickerViewModel.class);
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

    public void setCallBackForOnceRepeat(FullHourTimePickerCallBack callBackForOnceRepeat) {
        this.callBackForOnceRepeat = callBackForOnceRepeat;
    }

    public void setCallBackForDailyRepeat(FullHourTimePickerCallBack callBackForDailyRepeat) {
        this.callBackForDailyRepeat = callBackForDailyRepeat;
    }

    public void setCallBackForWeeklyRepeat(FullHourTimePickerCallBack callBackForWeeklyRepeat) {
        this.callBackForWeeklyRepeat = callBackForWeeklyRepeat;
    }

    public void setCallBackForMonthlyRepeat(FullHourTimePickerCallBack callBackForMonthlyRepeat) {
        this.callBackForMonthlyRepeat = callBackForMonthlyRepeat;
    }

    public void setCallBackForYearlyRepeat(FullHourTimePickerCallBack callBackForYearlyRepeat) {
        this.callBackForYearlyRepeat = callBackForYearlyRepeat;
    }

    @Override
    public void call(Repeat repeat) {
        if (repeat == Repeat.ONCE)
            callBackForOnceRepeat.getFullHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.DAILY)
            callBackForDailyRepeat.getFullHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.WEEKLY)
            callBackForWeeklyRepeat.getFullHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.MONTHLY)
            callBackForMonthlyRepeat.getFullHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.YEARLY)
            callBackForYearlyRepeat.getFullHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
    }

    public interface FullHourTimePickerCallBack {
        void getFullHourTimePickerResult(int minute, int hour);
    }
}

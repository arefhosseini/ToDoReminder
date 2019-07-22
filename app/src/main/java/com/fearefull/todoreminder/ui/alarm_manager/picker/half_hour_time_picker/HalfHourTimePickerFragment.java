package com.fearefull.todoreminder.ui.alarm_manager.picker.half_hour_time_picker;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.databinding.FragmentHalfHourTimePickerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

public class HalfHourTimePickerFragment extends BaseFragment<FragmentHalfHourTimePickerBinding, HalfHourTimePickerViewModel>
        implements HalfHourTimePickerNavigator, BaseRepeatCaller {

    public static final String TAG = HalfHourTimePickerFragment.class.getSimpleName();
    private static final String MINUTE_KEY = "minute_key";
    private static final String HOUR_KEY = "hour_key";

    @Inject
    ViewModelProviderFactory factory;
    private HalfHourTimePickerViewModel viewModel;
    private FragmentHalfHourTimePickerBinding binding;
    private HalfHourTimePickerCallBack callBackForOnceRepeat, callBackForDailyRepeat;

    public static HalfHourTimePickerFragment newInstance(int minute, int hour) {
        Bundle args = new Bundle();
        HalfHourTimePickerFragment fragment = new HalfHourTimePickerFragment();
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
        return R.layout.fragment_half_hour_time_picker;
    }

    @Override
    public HalfHourTimePickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HalfHourTimePickerViewModel.class);
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
        binding = getViewDataBinding();
        viewModel.init(getArguments().getInt(MINUTE_KEY), getArguments().getInt(HOUR_KEY));
    }

    public void setCallBackForOnceRepeat(HalfHourTimePickerCallBack callBackForOnceRepeat) {
        this.callBackForOnceRepeat = callBackForOnceRepeat;
    }

    public void setCallBackForDailyRepeat(HalfHourTimePickerCallBack callBackForDailyRepeat) {
        this.callBackForDailyRepeat = callBackForDailyRepeat;
    }

    @Override
    public void call(Repeat repeat) {
        if (repeat == Repeat.ONCE)
            callBackForOnceRepeat.getHalfHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
        else if (repeat == Repeat.DAILY)
            callBackForDailyRepeat.getHalfHourTimePickerResult(viewModel.getMinute(), viewModel.getHour());
    }

    public interface HalfHourTimePickerCallBack {
        void getHalfHourTimePickerResult(int minute, int hour);
    }
}

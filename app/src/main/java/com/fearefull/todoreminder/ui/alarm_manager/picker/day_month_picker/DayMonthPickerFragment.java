package com.fearefull.todoreminder.ui.alarm_manager.picker.day_month_picker;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.databinding.FragmentDayMonthPickerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

public class DayMonthPickerFragment extends BaseFragment<FragmentDayMonthPickerBinding, DayMonthPickerViewModel>
        implements DayMonthPickerNavigator, BaseRepeatCaller {

    public static final String TAG = DayMonthPickerFragment.class.getSimpleName();
    private static final String DAY_KEY = "day_key";

    @Inject
    ViewModelProviderFactory factory;
    private DayMonthPickerViewModel viewModel;
    private DayMonthPickerCallBack callBackForMonthly;

    public static DayMonthPickerFragment newInstance(int day) {
        Bundle args = new Bundle();
        DayMonthPickerFragment fragment = new DayMonthPickerFragment();
        args.putSerializable(DAY_KEY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_day_month_picker;
    }

    @Override
    public DayMonthPickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(DayMonthPickerViewModel.class);
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
        viewModel.init(getArguments().getInt(DAY_KEY));
    }

    public void setCallBackForMonthly(DayMonthPickerCallBack callBackForMonthly) {
        this.callBackForMonthly = callBackForMonthly;
    }

    @Override
    public void call(Repeat repeat) {
        if (repeat == Repeat.MONTHLY)
            callBackForMonthly.getDayMonthPickerResult(viewModel.getDay());
    }

    public interface DayMonthPickerCallBack {
        void getDayMonthPickerResult(int day);
    }
}

package com.fearefull.todoreminder.ui.alarm_manager.time_picker;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.databinding.FragmentTimePickerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerActivity;
import com.fearefull.todoreminder.ui.base.BaseBottomSheetFragment;
import com.fearefull.todoreminder.utils.ViewUtils;

import java.util.Date;

import javax.inject.Inject;

public class TimePickerFragment extends BaseBottomSheetFragment<FragmentTimePickerBinding, TimePickerViewModel> implements TimePickerNavigator {

    public static final String TAG = TimePickerFragment.class.getSimpleName();
    private static final String DEFAULT_TIME_KEY = "default_time";

    @Inject
    ViewModelProviderFactory factory;
    private TimePickerViewModel viewModel;
    private FragmentTimePickerBinding binding;

    public static TimePickerFragment newInstance(MyTime myTime) {
        Bundle args = new Bundle();
        TimePickerFragment fragment = new TimePickerFragment();
        args.putSerializable(DEFAULT_TIME_KEY, myTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_time_picker;
    }

    @Override
    public TimePickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(TimePickerViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        assert getArguments() != null;
        viewModel.setDefaultTime((MyTime) getArguments().getSerializable(DEFAULT_TIME_KEY));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        setUpNumberPicker(binding.hoursPicker, viewModel.getHours(), viewModel.getMyTime().getHourIndex());
        setUpNumberPicker(binding.minutesPicker, viewModel.getMinutes(), viewModel.getMyTime().getMinute());
        setUpNumberPicker(binding.typePicker, viewModel.getStringTimeTypes(), viewModel.getMyTime().getTimeType().getIndex());
    }

    private void setUpNumberPicker(NumberPicker picker, String[] data, int defaultIndex) {
        ViewUtils.setDividerColor(picker, getResources().getColor(R.color.secondaryColorLightTheme));
        picker.setMaxValue(data.length - 1);
        picker.setValue(defaultIndex);
        picker.setDisplayedValues(data);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    public interface TimePickerCallBack {
        void onGetTime(MyTime myTime);
    }

    @Override
    public void onDestroy() {
        AlarmManagerActivity activity = (AlarmManagerActivity) getBaseActivity();
        activity.onGetTime(viewModel.getMyTime());
        super.onDestroy();
    }
}
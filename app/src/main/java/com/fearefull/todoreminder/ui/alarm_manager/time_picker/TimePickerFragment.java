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
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.databinding.FragmentTimePickerBinding;
import com.fearefull.todoreminder.ui.base.BaseBottomSheetFragment;
import com.fearefull.todoreminder.utils.ViewUtils;

import javax.inject.Inject;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class TimePickerFragment extends BaseBottomSheetFragment<FragmentTimePickerBinding, TimePickerViewModel> implements TimePickerNavigator {

    public static final String TAG = TimePickerFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private TimePickerViewModel viewModel;
    private FragmentTimePickerBinding binding;

    public static TimePickerFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        TimePickerFragment fragment = new TimePickerFragment();
        args.putSerializable(ALARM_KEY, alarm);
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
        viewModel.setAlarm((Alarm) getArguments().getSerializable(ALARM_KEY));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        setUpNumberPicker(binding.hoursPicker, viewModel.getHours(), viewModel.getAlarm().getTime().getHourIndex());
        setUpNumberPicker(binding.minutesPicker, viewModel.getMinutes(), viewModel.getAlarm().getTime().getMinuteIndex());
        setUpNumberPicker(binding.typePicker, viewModel.getTimeTypes(), viewModel.getAlarm().getTime().getTimeTypeIndex());
    }

    private void setUpNumberPicker(NumberPicker picker, String[] data, int defaultIndex) {
        ViewUtils.setDividerColor(picker, getResources().getColor(R.color.secondaryColorLightTheme));
        picker.setMaxValue(data.length - 1);
        picker.setValue(defaultIndex);
        picker.setDisplayedValues(data);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    @Override
    public void onDestroy() {
        //AlarmManagerActivity activity = (AlarmManagerActivity) getBaseActivity();
        //activity.onUpdateAlarm(viewModel.getAlarm(), TAG);
        super.onDestroy();
    }
}
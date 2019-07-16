package com.fearefull.todoreminder.ui.alarm_manager.date_picker;

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
import com.fearefull.todoreminder.databinding.FragmentDatePickerBinding;
import com.fearefull.todoreminder.ui.base.BaseBottomSheetFragment;
import com.fearefull.todoreminder.utils.ViewUtils;

import javax.inject.Inject;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class DatePickerFragment extends BaseBottomSheetFragment<FragmentDatePickerBinding, DatePickerViewModel> implements DatePickerNavigator {

    public static final String TAG = DatePickerFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private DatePickerViewModel viewModel;
    private FragmentDatePickerBinding binding;

    public static DatePickerFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        DatePickerFragment fragment = new DatePickerFragment();
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
        return R.layout.fragment_date_picker;
    }

    @Override
    public DatePickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(DatePickerViewModel.class);
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
        setUpNumberPicker(binding.yearsPicker, viewModel.getYears(), viewModel.getAlarm().getDate().getYearIndex());
        setUpNumberPicker(binding.monthsPicker, viewModel.getMonths(), viewModel.getAlarm().getDate().getMonthIndex());
        setUpNumberPicker(binding.daysPicker, viewModel.getDays(), viewModel.getAlarm().getDate().getDayIndex());
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

    @Override
    public void onMonthChanged() {
        if (binding.daysPicker.getMaxValue() < viewModel.getAlarm().getDate().getMonth().getDays()) {
            binding.daysPicker.setDisplayedValues(viewModel.getDays());
            binding.daysPicker.setMaxValue(viewModel.getDays().length - 1);
            binding.daysPicker.setValue(viewModel.getAlarm().getDate().getDayIndex());
        }
        else {
            binding.daysPicker.setMaxValue(viewModel.getDays().length - 1);
            binding.daysPicker.setValue(viewModel.getAlarm().getDate().getDayIndex());
            binding.daysPicker.setDisplayedValues(viewModel.getDays());
        }
    }
}

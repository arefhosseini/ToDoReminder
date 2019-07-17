package com.fearefull.todoreminder.ui.alarm_manager.once_repeat.date_picker;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.other.HalfHourType;
import com.fearefull.todoreminder.databinding.FragmentDatePickerBinding;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.utils.ViewUtils;

import javax.inject.Inject;

public class DatePickerFragment extends BaseFragment<FragmentDatePickerBinding, DatePickerViewModel>
        implements DatePickerNavigator {

    public static final String TAG = DatePickerFragment.class.getSimpleName();
    private static final String DAY_KEY = "day_key";
    private static final String MONTH_KEY = "month_key";

    @Inject
    ViewModelProviderFactory factory;
    private DatePickerViewModel viewModel;
    private FragmentDatePickerBinding binding;
    private DatePickerCallBack callBack;

    public static DatePickerFragment newInstance(int day, int month) {
        Bundle args = new Bundle();
        DatePickerFragment fragment = new DatePickerFragment();
        args.putSerializable(DAY_KEY, day);
        args.putSerializable(MONTH_KEY, month);
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
        viewModel.setMonth(getArguments().getInt(MONTH_KEY));
        viewModel.setDay(getArguments().getInt(DAY_KEY));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        ViewUtils.setUpNumberPicker(binding.monthPicker, viewModel.getMonths(), viewModel.getMonthIndex());
        ViewUtils.setUpNumberPicker(binding.dayPicker, viewModel.getDays(), viewModel.getDayIndex());
    }

    @Override
    public void onMonthChanged() {
        if (binding.dayPicker.getMaxValue() < viewModel.getDay()) {
            binding.dayPicker.setDisplayedValues(viewModel.getDays());
            binding.dayPicker.setMaxValue(viewModel.getDays().length - 1);
            binding.dayPicker.setValue(viewModel.getDayIndex());
        }
        else {
            binding.dayPicker.setMaxValue(viewModel.getDays().length - 1);
            binding.dayPicker.setValue(viewModel.getDayIndex());
            binding.dayPicker.setDisplayedValues(viewModel.getDays());
        }
        callBack.onMonthChanged(viewModel.getMonth());
    }

    @Override
    public void onDayChanged() {
        callBack.onDayChanged(viewModel.getDay());
    }

    public void setCallBack(DatePickerCallBack callBack) {
        this.callBack = callBack;
    }
    
    public interface DatePickerCallBack {
        void onDayChanged(int day);
        void onMonthChanged(int month);
    }
}

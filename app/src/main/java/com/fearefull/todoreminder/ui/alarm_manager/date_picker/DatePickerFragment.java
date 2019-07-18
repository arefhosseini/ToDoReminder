package com.fearefull.todoreminder.ui.alarm_manager.date_picker;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.databinding.FragmentDatePickerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.OnceRepeatCaller;
import com.fearefull.todoreminder.ui.base.BaseFragment;

import javax.inject.Inject;

public class DatePickerFragment extends BaseFragment<FragmentDatePickerBinding, DatePickerViewModel>
        implements DatePickerNavigator, OnceRepeatCaller {

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
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.init(getArguments().getInt(DAY_KEY), getArguments().getInt(MONTH_KEY));
    }

    public void setCallBack(DatePickerCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void call() {
        callBack.getDatePickerResult(viewModel.getDay(), viewModel.getMonth());
    }

    public interface DatePickerCallBack {
        void getDatePickerResult(int day, int month);
    }
}

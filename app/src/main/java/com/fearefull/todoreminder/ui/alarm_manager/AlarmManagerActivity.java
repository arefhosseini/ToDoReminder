package com.fearefull.todoreminder.ui.alarm_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.databinding.ActivityAlarmManagerBinding;
import com.fearefull.todoreminder.ui.base.BaseActivity;

import javax.inject.Inject;

public class AlarmManagerActivity extends BaseActivity<ActivityAlarmManagerBinding, AlarmManagerViewModel> implements AlarmManagerNavigator {
    @Inject
    ViewModelProviderFactory factory;
    private AlarmManagerViewModel viewModel;
    private ActivityAlarmManagerBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AlarmManagerActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alarm_manager;
    }

    @Override
    public AlarmManagerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(AlarmManagerViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
    }
}

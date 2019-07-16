package com.fearefull.todoreminder.ui.alarm_notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.databinding.ActivityAlarmNotificationBinding;
import com.fearefull.todoreminder.ui.base.BaseActivity;

import javax.inject.Inject;

public class AlarmNotificationActivity extends BaseActivity<ActivityAlarmNotificationBinding, AlarmNotificationViewModel>
        implements AlarmNotificationNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private AlarmNotificationViewModel viewModel;
    private ActivityAlarmNotificationBinding binding;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AlarmNotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alarm_notification;
    }

    @Override
    public AlarmNotificationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(AlarmNotificationViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }
}

package com.fearefull.todoreminder.ui.alarm_manager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.databinding.ActivityAlarmManagerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.date_picker.DatePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.time_picker.TimePickerFragment;
import com.fearefull.todoreminder.ui.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class AlarmManagerActivity extends BaseActivity<ActivityAlarmManagerBinding, AlarmManagerViewModel>
        implements AlarmManagerNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        //viewModel.setDefaultDate(AlarmUtils.getTime(new MyTime("12", "59", TimeType.PM)));
        viewModel.setAlarm(new Alarm());
        viewModel.updateAlarm();
    }

    @Override
    public void openTimePickerFragment() {
        TimePickerFragment.newInstance(viewModel.getAlarm()).show(getSupportFragmentManager(), TimePickerFragment.TAG);
    }

    @Override
    public void openDatePickerFragment() {
        DatePickerFragment.newInstance(viewModel.getAlarm()).show(getSupportFragmentManager(), DatePickerFragment.TAG);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void openRepeatPickerFragment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle(R.string.repeat_dialog_title);
        builder.setSingleChoiceItems(
                viewModel.getRepeats(),
                viewModel.getRepeatDialogDefaultIndex(),
                viewModel.repeatPickerOnClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onFragmentDetached(String tag) {
        super.onFragmentDetached(tag);
    }

    @Override
    public void onUpdateAlarm(Alarm alarm, String fragmentTag) {
        Timber.d(fragmentTag);
        viewModel.setAlarm(alarm);
        if (fragmentTag.equals(TimePickerFragment.TAG))
            viewModel.updateTime();
        else if (fragmentTag.equals(DatePickerFragment.TAG))
            viewModel.updateDate();
    }
}

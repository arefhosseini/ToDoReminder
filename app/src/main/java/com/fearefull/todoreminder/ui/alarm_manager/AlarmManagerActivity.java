package com.fearefull.todoreminder.ui.alarm_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.other.MyDate;
import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.databinding.ActivityAlarmManagerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.date_picker.DatePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.time_picker.TimePickerFragment;
import com.fearefull.todoreminder.ui.base.BaseActivity;

import java.util.Date;

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
        //viewModel.setDefaultDate(TimeUtils.getTime(new MyTime("12", "59", TimeType.PM)));
        viewModel.setMyTime(new MyTime(new Date()));
        viewModel.setMyDate(new MyDate(new Date()));
    }

    @Override
    public void openTimePickerFragment() {
        TimePickerFragment.newInstance(viewModel.getMyTime()).show(getSupportFragmentManager(), TimePickerFragment.TAG);
    }

    @Override
    public void openDatePickerFragment() {
        DatePickerFragment.newInstance(viewModel.getMyDate()).show(getSupportFragmentManager(), DatePickerFragment.TAG);
    }

    @Override
    public void onFragmentDetached(String tag) {
        super.onFragmentDetached(tag);
    }

    @Override
    public void onGetTime(MyTime myTime) {
        Timber.d(myTime.toString());
        viewModel.setMyTime(myTime);
    }

    @Override
    public void onGetDate(MyDate myDate) {
        Timber.d(myDate.toString());
        viewModel.setMyDate(myDate);
    }
}

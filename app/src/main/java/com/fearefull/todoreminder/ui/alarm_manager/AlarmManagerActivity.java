package com.fearefull.todoreminder.ui.alarm_manager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.fearefull.todoreminder.utils.CommonUtils;

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
    public void goBack() {

    }

    @Override
    public void openDatePickerFragment() {
        DatePickerFragment.newInstance(viewModel.getAlarm()).show(getSupportFragmentManager(), DatePickerFragment.TAG);
    }

    @Override
    public void openRepeatPickerFragment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle(R.string.repeat_dialog_title);
        builder.setSingleChoiceItems(
                viewModel.getRepeats(),
                viewModel.getRepeatDialogDefaultIndex(),
                viewModel.repeatPickerOnClickListener);
        AlertDialog dialog = CommonUtils.showSingleChoiceItemDialog(this, R.string.repeat_dialog_title,
                viewModel.getRepeats(), viewModel.getRepeatDialogDefaultIndex(),
                viewModel.repeatPickerOnClickListener);
        dialog.show();
    }

    @Override
    public void openCustomRepeatPickerFragment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle(R.string.custom_repeat_dialog_title);
        @SuppressLint("InflateParams") View customAlarmItem = getLayoutInflater().inflate(R.layout.custom_repeat_alarm, null);
        TextView everyRepeat = customAlarmItem.findViewById(R.id.every_repeat);
        TextView onRepeat = customAlarmItem.findViewById(R.id.on_repeat);
        everyRepeat.setText(viewModel.getEveryCustomRepeat());
        onRepeat.setText(viewModel.getOnCustomRepeat());
        builder.setView(customAlarmItem);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void openEveryCustomRepeatPickerFragment() {

    }

    @Override
    public void openOnCustomRepeatPickerFragment() {

    }

    @Override
    public void closeAllExpansions() {

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

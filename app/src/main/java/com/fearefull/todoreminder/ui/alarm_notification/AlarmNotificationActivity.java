package com.fearefull.todoreminder.ui.alarm_notification;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.databinding.ActivityAlarmNotificationBinding;
import com.fearefull.todoreminder.ui.base.BaseActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class AlarmNotificationActivity extends BaseActivity<ActivityAlarmNotificationBinding, AlarmNotificationViewModel>
        implements AlarmNotificationNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private AlarmNotificationViewModel viewModel;
    private ActivityAlarmNotificationBinding binding;
    private Ringtone ringtone;

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
        Timber.e("triggerTime %d", System.currentTimeMillis());
        setUp();
    }

    private void setUp() {
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null){
            // alert is null, using backup
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null){
                // alert backup is null, using 2nd backup
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alert);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ringtone.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());
        } else {
            ringtone.setStreamType(AudioManager.STREAM_ALARM);
        }
        ringtone.play();

        Handler handler = new Handler();
        final Runnable runnable = this::finish;

        handler.postDelayed(runnable, 1000 * 10);
    }

    @Override
    protected void onDestroy() {
        ringtone.stop();
        super.onDestroy();
    }
}

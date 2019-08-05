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
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.databinding.ActivityAlarmNotificationBinding;
import com.fearefull.todoreminder.ui.base.BaseActivity;
import com.fearefull.todoreminder.utils.AppConstants;

import java.util.Objects;

import javax.inject.Inject;

public class AlarmNotificationActivity extends BaseActivity<ActivityAlarmNotificationBinding, AlarmNotificationViewModel>
        implements AlarmNotificationNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private ActivityAlarmNotificationBinding binding;
    private AlarmNotificationViewModel viewModel;
    private Ringtone ringtone;
    private Vibrator vibrator;

    public static Intent newIntent(Context context, String snoozeJson) {
        Intent intent = new Intent(context, AlarmNotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent.putExtra(Snooze.SNOOZE_KEY, snoozeJson);
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
        viewModel.init(Snooze.jsonToSnooze(getIntent().getStringExtra(Snooze.SNOOZE_KEY)));
    }

    @Override
    public void setUp() {
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
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (viewModel.getAlarm().isVibrate()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(AppConstants.VIBRATE_PATTERN, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                Objects.requireNonNull(vibrator).vibrate(AppConstants.VIBRATE_PATTERN, -1);
            }
        }

        ringtone.play();

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_slow_animation_infinite);
        binding.bellIcon.startAnimation(shake);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void destroy() {
        ringtone.stop();
        if (vibrator != null)
            vibrator.cancel();
        finish();
    }

    @Override
    public void onBackPressed() {
        viewModel.onDismissClick();
    }
}

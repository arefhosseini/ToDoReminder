package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Ringtone;
import com.fearefull.todoreminder.data.model.other.type.RingtoneType;
import com.fearefull.todoreminder.databinding.DialogFargmentRingtonePickerBinding;
import com.fearefull.todoreminder.ui.base.BaseDialogFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.utils.AppConstants;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Named;

public class RingtonePickerDialogFragment extends BaseDialogFragment<DialogFargmentRingtonePickerBinding, RingtonePickerViewModel>
        implements RingtonePickerNavigator, RingtonePickerAdapter.RingtonePickerAdapterListener {

    public static final String TAG = RingtonePickerDialogFragment.class.getSimpleName();

    @Inject
    RingtonePickerAdapter adapter;
    @Inject
    @Named("RingtonePicker")
    LinearLayoutManager layoutManager;
    @Inject
    ViewModelProviderFactory factory;
    private RingtonePickerViewModel viewModel;
    private DialogFargmentRingtonePickerBinding binding;
    private RingtonePickerCallBack callBack;
    private android.media.Ringtone ringtoneSound;
    private Timer timer;

    public static RingtonePickerDialogFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        RingtonePickerDialogFragment fragment = new RingtonePickerDialogFragment();
        args.putSerializable(AppConstants.ALARM_KEY, alarm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fargment_ringtone_picker;
    }

    @Override
    public RingtonePickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RingtonePickerViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        assert getArguments() != null;
        viewModel.init((Alarm) getArguments().getSerializable(AppConstants.ALARM_KEY));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        adapter.setListener(this);
        setUp();
    }

    private void setUp() {
        adapter.setHasStableIds(true);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Ringtone ringtone) {
        if (ringtoneSound != null)
            ringtoneSound.stop();
        if (timer != null)
            timer.cancel();

        viewModel.setRingtone(ringtone);

        if (ringtone.getType() != RingtoneType.SILENT) {
            if (ringtone.getUriString() == null) {
                Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alert == null){
                    // alert is null, using backup
                    alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    if (alert == null){
                        // alert backup is null, using 2nd backup
                        alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    }
                }
                ringtoneSound = RingtoneManager.getRingtone(getContext(), alert);
            }
            else
                ringtoneSound = RingtoneManager.getRingtone(getContext(), Uri.parse(ringtone.getUriString()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ringtoneSound.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());
            } else {
                ringtoneSound.setStreamType(AudioManager.STREAM_ALARM);
            }
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (!ringtoneSound.isPlaying()) {
                        ringtoneSound.play();
                    }
                }
            }, 1000, 1000);
            ringtoneSound.play();
        }
    }

    @Override
    public void finish() {
        dismiss();
    }

    @Override
    public void finishAndSave() {
        callBack.onAlarmChangedByRingtonePicker(viewModel.getAlarm());
        dismiss();
    }

    @Override
    public void onDestroy() {
        if (ringtoneSound != null)
            ringtoneSound.stop();
        if (timer != null)
            timer.cancel();
        super.onDestroy();
    }

    public void setCallBack(RingtonePickerCallBack callBack) {
        this.callBack = callBack;
    }

    public interface RingtonePickerCallBack {
        void onAlarmChangedByRingtonePicker(Alarm alarm);
    }
}

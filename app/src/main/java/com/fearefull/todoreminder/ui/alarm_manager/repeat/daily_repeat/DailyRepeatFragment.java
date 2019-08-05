package com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.databinding.FragmentDailyRepeatBinding;
import com.fearefull.todoreminder.ui.alarm_manager.picker.hour_time_picker.HourTimePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class DailyRepeatFragment extends BaseRepeatFragment<FragmentDailyRepeatBinding, DailyRepeatViewModel>
        implements DailyRepeatNavigator, HourTimePickerFragment.HourTimePickerCallBack {

    public static final String TAG = DailyRepeatFragment.class.getSimpleName();

    @Inject
    @Named("DailyRepeat")
    BaseViewPagerAdapter pagerAdapter;
    private DailyRepeatViewModel viewModel;
    private FragmentDailyRepeatBinding binding;
    private BaseRepeatCaller callerHalfHourTimePicker;

    public static DailyRepeatFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        DailyRepeatFragment fragment = new DailyRepeatFragment();
        args.putSerializable(ALARM_KEY, alarm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily_repeat;
    }

    @Override
    public DailyRepeatViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, getFactory()).get(DailyRepeatViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        assert getArguments() != null;
        viewModel.setAlarm((Alarm) getArguments().getSerializable(ALARM_KEY));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
    }

    @Override
    public void setUp() {
        binding.viewPager.setEnableSwipe(false);

        HourTimePickerFragment
                hourTimePickerFragment = HourTimePickerFragment.newInstance(
                viewModel.getAlarm().getDefaultMinute(), viewModel.getAlarm().getDefaultHour());
        hourTimePickerFragment.setCallBackForDailyRepeat(this);
        callerHalfHourTimePicker = hourTimePickerFragment;

        pagerAdapter.addFragment(hourTimePickerFragment, "hourTimePicker");
        binding.viewPager.setAdapter(pagerAdapter);

        timePickerClick();
        viewModel.getCurrentTabPager().setValue(0);
    }

    @Override
    public void timePickerClick() {
        binding.timeLayout.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.quarter_top_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.timeIcon, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));
    }

    @Override
    public void onAddRepeat() {
        callerHalfHourTimePicker.call(Repeat.DAILY);
    }

    @Override
    public void getHourTimePickerResult(int minute, int hour) {
        viewModel.getRepeatModel().setMinute(minute);
        viewModel.getRepeatModel().setHour(hour);
        viewModel.checkForSend(Repeat.DAILY);
    }

    @Override
    public void shake() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        binding.timeIcon.setAnimation(shake);
        shake.start();
    }

    @Override
    public void call() {
        onAddRepeat();
    }
}

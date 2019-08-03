package com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat;

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
import com.fearefull.todoreminder.databinding.FragmentOnceRepeatBinding;
import com.fearefull.todoreminder.ui.alarm_manager.picker.date_picker.DatePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.picker.hour_time_picker.HourTimePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class OnceRepeatFragment extends BaseRepeatFragment<FragmentOnceRepeatBinding, OnceRepeatViewModel>
        implements OnceRepeatNavigator, HourTimePickerFragment.HourTimePickerCallBack, DatePickerFragment.DatePickerCallBack {

    public static final String TAG = OnceRepeatFragment.class.getSimpleName();

    @Inject
    @Named("OnceRepeat")
    BaseViewPagerAdapter pagerAdapter;
    private OnceRepeatViewModel viewModel;
    private FragmentOnceRepeatBinding binding;
    private BaseRepeatCaller callerHalfHourTimePicker, callerDatePicker;

    public static OnceRepeatFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        OnceRepeatFragment fragment = new OnceRepeatFragment();
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
        return R.layout.fragment_once_repeat;
    }

    @Override
    public OnceRepeatViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, getFactory()).get(OnceRepeatViewModel.class);
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
        hourTimePickerFragment.setCallBackForOnceRepeat(this);
        callerHalfHourTimePicker = hourTimePickerFragment;

        DatePickerFragment datePickerFragment =
                DatePickerFragment.newInstance(viewModel.getAlarm().getDefaultDayMonth(),
                        viewModel.getAlarm().getDefaultMonth());
        datePickerFragment.setCallBackForOnceRepeat(this);
        callerDatePicker = datePickerFragment;

        pagerAdapter.addFragment(hourTimePickerFragment, "hourTimePicker");
        pagerAdapter.addFragment(datePickerFragment, "datePicker");
        binding.viewPager.setAdapter(pagerAdapter);

        timePickerClick();
        viewModel.getCurrentTabPager().setValue(0);
    }

    @Override
    public void timePickerClick() {
        binding.timeLayout.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.quarter_top_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.timeIcon, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));

        binding.dateLayout.setBackground(getContext().getDrawable(R.drawable.quarter_bottom_right_circle_unselected_bg));
        ImageViewCompat.setImageTintList(binding.dateIcon, ColorStateList.valueOf(getResources().getColor(R.color.primaryColorLightTheme)));
    }

    @Override
    public void datePickerClick() {
        binding.dateLayout.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.quarter_bottom_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.dateIcon, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));

        binding.timeLayout.setBackground(getContext().getDrawable(R.drawable.quarter_top_right_circle_unselected_bg));
        ImageViewCompat.setImageTintList(binding.timeIcon, ColorStateList.valueOf(getResources().getColor(R.color.primaryColorLightTheme)));
    }

    @Override
    public void onAddRepeat() {
        callerHalfHourTimePicker.call(Repeat.ONCE);
        callerDatePicker.call(Repeat.ONCE);
    }

    @Override
    public void getHourTimePickerResult(int minute, int hour) {
        viewModel.getRepeatModel().setMinute(minute);
        viewModel.getRepeatModel().setHour(hour);
        viewModel.checkForSend(Repeat.ONCE);
    }

    @Override
    public void getDatePickerResult(int day, int month) {
        viewModel.getRepeatModel().setDayMonth(day);
        viewModel.getRepeatModel().setMonth(month);
        viewModel.checkForSend(Repeat.ONCE);
    }

    @Override
    public void shake() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        binding.timeIcon.setAnimation(shake);
        binding.dateIcon.setAnimation(shake);
        shake.start();
    }

    @Override
    public void call() {
        onAddRepeat();
    }
}

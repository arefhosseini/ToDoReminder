package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.FragmentOnceRepeatBinding;
import com.fearefull.todoreminder.ui.alarm_manager.date_picker.DatePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.half_hour_time_picker.HalfHourTimePickerFragment;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class OnceRepeatFragment extends BaseFragment<FragmentOnceRepeatBinding, OnceRepeatViewModel>
        implements HasSupportFragmentInjector, OnceRepeatNavigator,
        HalfHourTimePickerFragment.HalfHourTimePickerCallBack, DatePickerFragment.DatePickerCallBack {

    public static final String TAG = OnceRepeatFragment.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    @Named("OnceRepeat")
    BaseViewPagerAdapter pagerAdapter;
    private OnceRepeatViewModel viewModel;
    private FragmentOnceRepeatBinding binding;
    private OnceRepeatCallBack callBack;
    private OnceRepeatCaller callerHalfHourTimePicker, callerDatePicker;

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
        viewModel = ViewModelProviders.of(this, factory).get(OnceRepeatViewModel.class);
        return viewModel;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
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

    private void setUp() {
        binding.viewPager.setEnableSwipe(false);

        HalfHourTimePickerFragment
                halfHourTimePickerFragment = HalfHourTimePickerFragment.newInstance(
                        viewModel.getAlarm().getDefaultMinute(), viewModel.getAlarm().getDefaultHour());
        halfHourTimePickerFragment.setCallBack(this);
        callerHalfHourTimePicker = halfHourTimePickerFragment;

        DatePickerFragment datePickerFragment =
                DatePickerFragment.newInstance(viewModel.getAlarm().getDefaultDayMonth(),
                        viewModel.getAlarm().getDefaultMonth());
        datePickerFragment.setCallBack(this);
        callerDatePicker = datePickerFragment;

        pagerAdapter.addFragment(halfHourTimePickerFragment, "halfHourTimePicker");
        pagerAdapter.addFragment(datePickerFragment, "datePicker");
        binding.viewPager.setAdapter(pagerAdapter);

        timePickerClick();
        viewModel.getCurrentTabPager().setValue(0);
    }

    public void setCallBack(OnceRepeatCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void timePickerClick() {
        binding.timeLayout.setBackground(getContext().getDrawable(R.drawable.quarter_top_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.timeIcon, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));

        binding.dateLayout.setBackground(getContext().getDrawable(R.drawable.quarter_bottom_right_circle_unselected_bg));
        ImageViewCompat.setImageTintList(binding.dateIcon, ColorStateList.valueOf(getResources().getColor(R.color.primaryColorLightTheme)));
    }

    @Override
    public void datePickerClick() {
        binding.dateLayout.setBackground(getContext().getDrawable(R.drawable.quarter_bottom_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.dateIcon, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));

        binding.timeLayout.setBackground(getContext().getDrawable(R.drawable.quarter_top_right_circle_unselected_bg));
        ImageViewCompat.setImageTintList(binding.timeIcon, ColorStateList.valueOf(getResources().getColor(R.color.primaryColorLightTheme)));
    }

    @Override
    public void onAddRepeat() {
        callerHalfHourTimePicker.call();
        callerDatePicker.call();
    }

    @Override
    public void getHalfHourTimePickerResult(int minute, int hour) {
        viewModel.getRepeatModel().setMinute(minute);
        viewModel.getRepeatModel().setHour(hour);
        viewModel.checkForSend();
    }

    @Override
    public void getDatePickerResult(int day, int month) {
        viewModel.getRepeatModel().setDayMonth(day);
        viewModel.getRepeatModel().setMonth(month);
        viewModel.checkForSend();
    }

    @Override
    public void send() {
        callBack.onAlarmChangedByOnceRepeat(viewModel.getAlarm());
    }

    @Override
    public void showError() {
        shake();
        Toast.makeText(getContext(), R.string.not_updated_time_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDuplicate() {
        shake();
        Toast.makeText(getContext(), R.string.duplicate_time_error, Toast.LENGTH_SHORT).show();
    }

    private void shake() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        binding.timeIcon.setAnimation(shake);
        binding.dateIcon.setAnimation(shake);
        shake.start();
    }

    public interface OnceRepeatCallBack {
        void onAlarmChangedByOnceRepeat(Alarm alarm);
    }
}

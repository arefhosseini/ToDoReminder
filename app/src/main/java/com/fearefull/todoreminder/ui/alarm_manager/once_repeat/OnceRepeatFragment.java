package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.FragmentOnceRepeatBinding;
import com.fearefull.todoreminder.ui.alarm_manager.RepeatCallBack;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.date_picker.DatePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.half_hour_time_picker.HalfHourTimePickerFragment;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Inject;

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
    BaseViewPagerAdapter pagerAdapter;
    private OnceRepeatViewModel viewModel;
    private FragmentOnceRepeatBinding binding;
    private RepeatCallBack callBack;
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
                        viewModel.getAlarm().getMinute(0), viewModel.getAlarm().getHour(0), viewModel.getAlarm().getHalfHourType());
        halfHourTimePickerFragment.setCallBack(this);
        callerHalfHourTimePicker = halfHourTimePickerFragment;

        DatePickerFragment datePickerFragment =
                DatePickerFragment.newInstance(viewModel.getAlarm().getDayMonth(0),
                        viewModel.getAlarm().getMonth(0));
        datePickerFragment.setCallBack(this);
        callerDatePicker = datePickerFragment;

        pagerAdapter.addFragment(halfHourTimePickerFragment, "halfHourTimePicker");
        pagerAdapter.addFragment(datePickerFragment, "datePicker");
        binding.viewPager.setAdapter(pagerAdapter);

        showTimePickerFragment();
    }

    public void setCallBack(RepeatCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void showTimePickerFragment() {
        binding.timeLayout.setBackground(getContext().getDrawable(R.drawable.quarter_top_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.timeImageView, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));

        binding.dateLayout.setBackground(getContext().getDrawable(R.drawable.quarter_bottom_right_circle_unselected_bg));
        ImageViewCompat.setImageTintList(binding.dateImageView, ColorStateList.valueOf(getResources().getColor(R.color.primaryColorLightTheme)));

        binding.viewPager.setCurrentItem(0);
    }

    @Override
    public void showDatePickerFragment() {
        binding.dateLayout.setBackground(getContext().getDrawable(R.drawable.quarter_bottom_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.dateImageView, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));

        binding.timeLayout.setBackground(getContext().getDrawable(R.drawable.quarter_top_right_circle_unselected_bg));
        ImageViewCompat.setImageTintList(binding.timeImageView, ColorStateList.valueOf(getResources().getColor(R.color.primaryColorLightTheme)));

        binding.viewPager.setCurrentItem(1);
    }

    @Override
    public void onAddRepeat() {
        callerHalfHourTimePicker.call();
        callerDatePicker.call();
    }

    @Override
    public void getHalfHourTimePickerResult(int minute, int hour) {
        viewModel.getModel().setMinute(minute);
        viewModel.getModel().setHour(hour);
        viewModel.checkForSend();
    }

    @Override
    public void getDatePickerResult(int day, int month) {
        viewModel.getModel().setDay(day);
        viewModel.getModel().setMonth(month);
        viewModel.checkForSend();
    }

    @Override
    public void send() {
        callBack.onAlarmChanged(viewModel.getAlarm());
    }
}

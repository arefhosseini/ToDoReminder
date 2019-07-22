package com.fearefull.todoreminder.ui.alarm_manager.daily_repeat;

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
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.FragmentDailyRepeatBinding;
import com.fearefull.todoreminder.ui.alarm_manager.half_hour_time_picker.HalfHourTimePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.OnceRepeatFragment;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class DailyRepeatFragment extends BaseFragment<FragmentDailyRepeatBinding, DailyRepeatViewModel>
        implements HasSupportFragmentInjector, DailyRepeatNavigator, HalfHourTimePickerFragment.HalfHourTimePickerCallBack {

    public static final String TAG = DailyRepeatFragment.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    @Named("DailyRepeat")
    BaseViewPagerAdapter pagerAdapter;
    private DailyRepeatViewModel viewModel;
    private FragmentDailyRepeatBinding binding;
    private DailyRepeatCallBack callBack;
    private DailyRepeatCaller callerHalfHourTimePicker;

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
        viewModel = ViewModelProviders.of(this, factory).get(DailyRepeatViewModel.class);
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
        halfHourTimePickerFragment.setCallBackForDailyRepeat(this);
        callerHalfHourTimePicker = halfHourTimePickerFragment;

        pagerAdapter.addFragment(halfHourTimePickerFragment, "halfHourTimePicker");
        binding.viewPager.setAdapter(pagerAdapter);

        timePickerClick();
        viewModel.getCurrentTabPager().setValue(0);
    }

    public void setCallBack(DailyRepeatCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void timePickerClick() {
        binding.timeLayout.setBackground(getContext().getDrawable(R.drawable.quarter_top_right_circle_selected_bg));
        ImageViewCompat.setImageTintList(binding.timeIcon, ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));
    }

    @Override
    public void onAddRepeat() {
        callerHalfHourTimePicker.callByDailyRepeat();
    }

    @Override
    public void getHalfHourTimePickerResult(int minute, int hour) {
        viewModel.getRepeatModel().setMinute(minute);
        viewModel.getRepeatModel().setHour(hour);
        viewModel.checkForSend();
    }

    @Override
    public void send() {
        callBack.onAlarmChangedByDailyRepeat(viewModel.getAlarm());
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
        shake.start();
    }

    public interface DailyRepeatCallBack {
        void onAlarmChangedByDailyRepeat(Alarm alarm);
    }
}

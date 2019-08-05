package com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.item.DayWeekItem;
import com.fearefull.todoreminder.databinding.FragmentWeeklyRepeatBinding;
import com.fearefull.todoreminder.ui.alarm_manager.picker.hour_time_picker.HourTimePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Inject;
import javax.inject.Named;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class WeeklyRepeatFragment extends BaseRepeatFragment<FragmentWeeklyRepeatBinding, WeeklyRepeatViewModel>
        implements WeeklyRepeatNavigator, HourTimePickerFragment.HourTimePickerCallBack,
        DayWeekAdapter.DayWeekAdapterListener {

    public static final String TAG = WeeklyRepeatFragment.class.getSimpleName();

    @Inject
    @Named("WeeklyRepeat")
    BaseViewPagerAdapter pagerAdapter;
    @Inject
    DayWeekAdapter dayWeekAdapter;
    @Inject
    @Named("WeeklyRepeat")
    GridLayoutManager layoutManager;
    private WeeklyRepeatViewModel viewModel;
    private FragmentWeeklyRepeatBinding binding;
    private BaseRepeatCaller callerHalfHourTimePicker;

    public static WeeklyRepeatFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        WeeklyRepeatFragment fragment = new WeeklyRepeatFragment();
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
        return R.layout.fragment_weekly_repeat;
    }

    @Override
    public WeeklyRepeatViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, getFactory()).get(WeeklyRepeatViewModel.class);
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
        dayWeekAdapter.setListener(this);
        viewModel.init();
        setUp();
    }

    @Override
    public void setUp() {
        binding.dayWeekRecyclerView.setLayoutManager(layoutManager);
        binding.dayWeekRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.dayWeekRecyclerView.setAdapter(dayWeekAdapter);

        binding.viewPager.setEnableSwipe(false);

        HourTimePickerFragment
                hourTimePickerFragment = HourTimePickerFragment.newInstance(
                viewModel.getAlarm().getDefaultMinute(), viewModel.getAlarm().getDefaultHour());
        hourTimePickerFragment.setCallBackForWeeklyRepeat(this);
        callerHalfHourTimePicker = hourTimePickerFragment;

        pagerAdapter.addFragment(hourTimePickerFragment, "hourTimePicker");
        binding.viewPager.setAdapter(pagerAdapter);

        viewModel.getCurrentTabPager().setValue(0);
    }

    @Override
    public void timePickerClick() {

    }

    @Override
    public void onAddRepeat() {
        callerHalfHourTimePicker.call(Repeat.WEEKLY);
    }

    @Override
    public void getHourTimePickerResult(int minute, int hour) {
        viewModel.sendDaysWeek(minute, hour);
    }

    @Override
    public void shake() {

    }

    @Override
    public void onDayWeekItemClick(DayWeekItem dayWeekItem) {
        viewModel.changeDayWeekItem(dayWeekItem);
    }

    @Override
    public void call() {
        onAddRepeat();
    }

}

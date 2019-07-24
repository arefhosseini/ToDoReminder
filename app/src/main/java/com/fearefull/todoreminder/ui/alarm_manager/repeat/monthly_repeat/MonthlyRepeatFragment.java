package com.fearefull.todoreminder.ui.alarm_manager.repeat.monthly_repeat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.databinding.FragmentMonthlyRepeatBinding;
import com.fearefull.todoreminder.ui.about.AboutFragment;
import com.fearefull.todoreminder.ui.alarm_manager.picker.day_month_picker.DayMonthPickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.picker.half_hour_time_picker.HalfHourTimePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatCaller;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatFragment;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class MonthlyRepeatFragment extends BaseRepeatFragment<FragmentMonthlyRepeatBinding, MonthlyRepeatViewModel>
implements MonthlyRepeatNavigator, DayMonthPickerFragment.DayMonthPickerCallBack,
        HalfHourTimePickerFragment.HalfHourTimePickerCallBack {

    public static final String TAG = MonthlyRepeatFragment.class.getSimpleName();

    private MonthlyRepeatViewModel viewModel;
    private FragmentMonthlyRepeatBinding binding;
    private BaseRepeatCaller callerHalfHourTimePicker, callerDayMonth;

    public static MonthlyRepeatFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        MonthlyRepeatFragment fragment = new MonthlyRepeatFragment();
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
        return R.layout.fragment_monthly_repeat;
    }

    @Override
    public MonthlyRepeatViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, getFactory()).get(MonthlyRepeatViewModel.class);
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

        HalfHourTimePickerFragment halfHourTimePickerFragment =
                HalfHourTimePickerFragment.newInstance(viewModel.getAlarm().getDefaultMinute(), viewModel.getAlarm().getDefaultHour());
        halfHourTimePickerFragment.setCallBackForMonthlyRepeat(this);
        callerHalfHourTimePicker = halfHourTimePickerFragment;
        getChildFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.time_picker_root_view, halfHourTimePickerFragment, HalfHourTimePickerFragment.TAG)
                .commit();


        DayMonthPickerFragment dayMonthPickerFragment = DayMonthPickerFragment.newInstance(viewModel.getDefaultDayMonth());
        dayMonthPickerFragment.setCallBackForMonthly(this);
        callerDayMonth = dayMonthPickerFragment;
        getChildFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.day_month_picker_root_view, dayMonthPickerFragment, DayMonthPickerFragment.TAG)
                .commit();
    }

    @Override
    public void timePickerClick() {}

    @Override
    public void onAddRepeat() {
        callerHalfHourTimePicker.call(Repeat.MONTHLY);
        callerDayMonth.call(Repeat.MONTHLY);
    }

    @Override
    public void getHalfHourTimePickerResult(int minute, int hour) {
        viewModel.getRepeatModel().setMinute(minute);
        viewModel.getRepeatModel().setHour(hour);
        viewModel.checkForSend(Repeat.MONTHLY);
    }

    @Override
    public void getDayMonthPickerResult(int day) {
        viewModel.getRepeatModel().setDayMonth(day);
        viewModel.checkForSend(Repeat.MONTHLY);
    }

    @Override
    public void shake() {}

    @Override
    public void call() {
        onAddRepeat();
    }
}

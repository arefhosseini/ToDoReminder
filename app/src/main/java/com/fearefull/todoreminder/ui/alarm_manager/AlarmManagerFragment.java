package com.fearefull.todoreminder.ui.alarm_manager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.item.AlarmTitleItem;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.databinding.FragmentAlarmManagerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker.RingtonePickerDialogFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat.DailyRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.monthly_repeat.MonthlyRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat.OnceRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat.WeeklyRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.yearly_repeat.YearlyRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerDialogFragment;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.github.florent37.expansionpanel.ExpansionLayout;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class AlarmManagerFragment extends BaseFragment<FragmentAlarmManagerBinding, AlarmManagerViewModel>
        implements AlarmManagerNavigator, HasSupportFragmentInjector,
        RepeatAdapter.RepeatAdapterListener, AlarmTitleAdapter.AlarmTitleAdapterListener,
        BaseRepeatFragment.RepeatCallBack, RepeatManagerDialogFragment.RepeatManagerCallBack,
        RingtonePickerDialogFragment.RingtonePickerCallBack {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    RepeatAdapter repeatAdapter;
    @Inject
    @Named("AlarmManager")
    LinearLayoutManager layoutManager;
    @Inject
    @Named("AlarmManager")
    GridLayoutManager gridLayoutManager;
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    @Named("AlarmManager")
    BaseViewPagerAdapter pagerAdapter;
    @Inject
    AlarmTitleAdapter titleAdapter;

    public static final String TAG = AlarmManagerFragment.class.getSimpleName();

    private AlarmManagerViewModel viewModel;
    private FragmentAlarmManagerBinding binding;
    private AlarmManagerCallBack callBack;
    private AlarmManagerCaller callerOnceRepeat, callerDailyRepeat, callerWeeklyRepeat, callerMonthlyRepeat,
            callerYearlyRepeat;
    private boolean shouldShowRingtoneDialog = false;
    public static AlarmManagerFragment newInstance(long alarmId) {
        Bundle args = new Bundle();
        AlarmManagerFragment fragment = new AlarmManagerFragment();
        args.putSerializable(ALARM_KEY, alarmId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_alarm_manager;
    }

    @Override
    public AlarmManagerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(AlarmManagerViewModel.class);
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
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        repeatAdapter.setListener(this);
        titleAdapter.setListener(this);

        assert getArguments() != null;
        viewModel.setAlarmById(getArguments().getLong(ALARM_KEY));
        callBack.alarmManagerIsSetUp();
    }

    @Override
    public void onSetUp() {
        setUp();
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.repeatContent.repeatRecyclerView.setLayoutManager(layoutManager);
        binding.repeatContent.repeatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.repeatContent.repeatRecyclerView.setAdapter(repeatAdapter);

        binding.titleContent.titleRecyclerView.setLayoutManager(gridLayoutManager);
        binding.titleContent.titleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.titleContent.titleRecyclerView.setAdapter(titleAdapter);

        binding.repeatContent.viewPager.setEnableSwipe(false);

        OnceRepeatFragment onceRepeatFragment = OnceRepeatFragment.newInstance(viewModel.getAlarm());
        onceRepeatFragment.setCallBack(this);
        callerOnceRepeat = onceRepeatFragment;

        DailyRepeatFragment dailyRepeatFragment = DailyRepeatFragment.newInstance(viewModel.getAlarm());
        dailyRepeatFragment.setCallBack(this);
        callerDailyRepeat = dailyRepeatFragment;

        WeeklyRepeatFragment weeklyRepeatFragment = WeeklyRepeatFragment.newInstance(viewModel.getAlarm());
        weeklyRepeatFragment.setCallBack(this);
        callerWeeklyRepeat = weeklyRepeatFragment;

        MonthlyRepeatFragment monthlyRepeatFragment = MonthlyRepeatFragment.newInstance(viewModel.getAlarm());
        monthlyRepeatFragment.setCallBack(this);
        callerMonthlyRepeat = monthlyRepeatFragment;

        YearlyRepeatFragment yearlyRepeatFragment = YearlyRepeatFragment.newInstance(viewModel.getAlarm());
        yearlyRepeatFragment.setCallBack(this);
        callerYearlyRepeat = yearlyRepeatFragment;

        pagerAdapter.addFragment(onceRepeatFragment, "once");
        pagerAdapter.addFragment(dailyRepeatFragment, "daily");
        pagerAdapter.addFragment(weeklyRepeatFragment, "weekly");
        pagerAdapter.addFragment(monthlyRepeatFragment, "monthly");
        pagerAdapter.addFragment(yearlyRepeatFragment, "yearly");
        binding.repeatContent.viewPager.setAdapter(pagerAdapter);

        ExpansionLayout.Listener expansionLayoutListener = (expansionLayout, expanded) -> {
            if (binding.titleContent.titleExpansionLayout == expansionLayout) {
                if (expanded) {
                    binding.titleContent.titleEditText.requestFocus();
                } else {
                    hideKeyboard();
                    binding.titleContent.titleEditText.clearFocus();
                }
            }
            if (!expanded && shouldShowRingtoneDialog)
                showRingtonePickerDialog();
        };

        binding.titleContent.titleExpansionLayout.addListener(expansionLayoutListener);
        binding.repeatContent.repeatExpansionLayout.addListener(expansionLayoutListener);
        binding.snoozeContent.snoozeExpansionLayout.addListener(expansionLayoutListener);
        binding.otherOptionsContent.otherOptionsExpansionLayout.addListener(expansionLayoutListener);

        new Handler().postDelayed(this::changeExpansionRepeatLayout, 1000L);
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void save() {
        if(callBack.onReloadAlarms()) {
            hideKeyboard();
            goBack();
        }
    }

    @Override
    public void openCustomRepeatPickerFragment() {

    }

    @Override
    public void onRepeatItemClick(RepeatItem repeatItem) {
        viewModel.setSelectedRepeat(repeatItem.getRepeat());
        viewModel.getCurrentTabPager().setValue(repeatItem.getRepeat().getValue());
    }

    @Override
    public void onAlarmTitleItemClick(AlarmTitleItem item) {
        viewModel.updateAlarmTitle(item.getType());
        binding.titleContent.titleEditText.requestFocus();
    }

    @Override
    public void openRingtonePickerDialog() {
        shouldShowRingtoneDialog = true;
        if (binding.titleContent.titleExpansionLayout.isExpanded())
            binding.titleContent.titleExpansionLayout.collapse(true);
        else if (binding.repeatContent.repeatExpansionLayout.isExpanded())
            binding.repeatContent.repeatExpansionLayout.collapse(true);
        else if (binding.snoozeContent.snoozeExpansionLayout.isExpanded())
            binding.snoozeContent.snoozeExpansionLayout.collapse(true);
        else if (binding.otherOptionsContent.otherOptionsExpansionLayout.isExpanded())
            binding.otherOptionsContent.otherOptionsExpansionLayout.collapse(true);
        else
            showRingtonePickerDialog();
    }

    @Override
    public void changeExpansionTitleLayout() {
        if (binding.titleContent.titleExpansionLayout.isExpanded()) {
            binding.titleContent.titleExpansionLayout.collapse(true);
        }
        else
            binding.titleContent.titleExpansionLayout.expand(true);
    }

    @Override
    public void changeExpansionRepeatLayout() {
        if (binding.repeatContent.repeatExpansionLayout.isExpanded()) {
            binding.repeatContent.repeatExpansionLayout.collapse(true);
        }
        else
            binding.repeatContent.repeatExpansionLayout.expand(true);
    }

    @Override
    public void changeExpansionSnoozeLayout() {
        if (binding.snoozeContent.snoozeExpansionLayout.isExpanded()) {
            binding.snoozeContent.snoozeExpansionLayout.collapse(true);
        }
        else
            binding.snoozeContent.snoozeExpansionLayout.expand(true);
    }

    @Override
    public void changeExpansionOtherOptionsLayout() {
        if (binding.otherOptionsContent.otherOptionsExpansionLayout.isExpanded()) {
            binding.otherOptionsContent.otherOptionsExpansionLayout.collapse(true);
        }
        else
            binding.otherOptionsContent.otherOptionsExpansionLayout.expand(true);
    }

    private void showRingtonePickerDialog() {
        RingtonePickerDialogFragment dialogFragment = RingtonePickerDialogFragment.newInstance(viewModel.getAlarm());
        dialogFragment.setCallBack(this);
        dialogFragment.show(getChildFragmentManager(), RingtonePickerDialogFragment.TAG);

        shouldShowRingtoneDialog = false;
    }

    public void setCallBack(AlarmManagerCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onAlarmChanged(Alarm alarm) {
        viewModel.setAlarm(alarm);
        viewModel.updateAlarm();
    }

    @Override
    public void onShowRepeatManagerDialog() {
        RepeatManagerDialogFragment dialogFragment = RepeatManagerDialogFragment.newInstance(viewModel.getAlarm());
        dialogFragment.setCallBack(this);
        dialogFragment.show(getChildFragmentManager(), RepeatManagerDialogFragment.TAG);
    }

    @Override
    public void onAlarmChangedByRepeatManager(Alarm alarm) {
        viewModel.setAlarm(alarm);
        viewModel.updateAlarm();
    }

    @Override
    public void onAlarmChangedByRingtonePicker(Alarm alarm) {
        viewModel.setAlarm(alarm);
        viewModel.updateAlarm();
    }

    @Override
    public void createWithShakeBell() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        binding.repeatContent.repeatManagerIcon.setImageResource(R.drawable.selected_bell);
        binding.repeatContent.repeatManagerIcon.startAnimation(shake);
    }

    @Override
    public void shakeBell() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        binding.repeatContent.repeatManagerIcon.startAnimation(shake);
    }

    @Override
    public void clearBell() {
        binding.repeatContent.repeatManagerIcon.setImageResource(R.drawable.unselected_bell);
    }

    @Override
    public void getLastRepeat(Repeat repeat) {
        if (repeat == Repeat.ONCE)
            callerOnceRepeat.call();
        else if (repeat == Repeat.DAILY)
            callerDailyRepeat.call();
        else if (repeat == Repeat.WEEKLY)
            callerWeeklyRepeat.call();
        else if (repeat == Repeat.MONTHLY)
            callerMonthlyRepeat.call();
        else if (repeat == Repeat.YEARLY)
            callerYearlyRepeat.call();
    }

    public interface AlarmManagerCallBack {
        boolean onReloadAlarms();
        void alarmManagerIsSetUp();
    }
}

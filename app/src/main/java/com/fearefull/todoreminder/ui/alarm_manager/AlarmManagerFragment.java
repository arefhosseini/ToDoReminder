package com.fearefull.todoreminder.ui.alarm_manager;

import android.os.Bundle;
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
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.item.AlarmTitleItem;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat.DailyRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.monthly_repeat.MonthlyRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat.WeeklyRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.yearly_repeat.YearlyRepeatFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.databinding.FragmentAlarmManagerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat.OnceRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerDialogFragment;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;
import com.fearefull.todoreminder.utils.CommonUtils;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class AlarmManagerFragment extends BaseFragment<FragmentAlarmManagerBinding, AlarmManagerViewModel>
        implements AlarmManagerNavigator, HasSupportFragmentInjector,
        RepeatAdapter.RepeatAdapterListener, AlarmTitleAdapter.AlarmTitleAdapterListener,
        BaseRepeatFragment.RepeatCallBack, RepeatManagerDialogFragment.RepeatManagerCallBack {

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

    public static AlarmManagerFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        AlarmManagerFragment fragment = new AlarmManagerFragment();
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
        assert getArguments() != null;
        viewModel.setAlarm((Alarm) getArguments().getSerializable(ALARM_KEY));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        repeatAdapter.setListener(this);
        titleAdapter.setListener(this);
        setUp();
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.repeatRecyclerView.setLayoutManager(layoutManager);
        binding.repeatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.repeatRecyclerView.setAdapter(repeatAdapter);

        binding.titleRecyclerView.setLayoutManager(gridLayoutManager);
        binding.titleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.titleRecyclerView.setAdapter(titleAdapter);

        binding.viewPager.setEnableSwipe(false);

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

        //SimpleFragment simpleFragment3 = SimpleFragment.newInstance(viewModel.getAlarm());

        pagerAdapter.addFragment(onceRepeatFragment, "once");
        pagerAdapter.addFragment(dailyRepeatFragment, "daily");
        pagerAdapter.addFragment(weeklyRepeatFragment, "weekly");
        pagerAdapter.addFragment(monthlyRepeatFragment, "monthly");
        pagerAdapter.addFragment(yearlyRepeatFragment, "yearly");
        //pagerAdapter.addFragment(simpleFragment3, "custom");
        binding.viewPager.setAdapter(pagerAdapter);

        viewModel.initAlarm();
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void save() {
        if(callBack.onReloadAlarms()) {
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
        viewModel.updateTitleString(item.getType().getText());
    }

    @Override
    public void closeAllExpansions() {
        if (binding.titleExpansionLayout.isExpanded())
            binding.titleExpansionLayout.collapse(true);
        else if (binding.repeatExpansionLayout.isExpanded())
            binding.repeatExpansionLayout.collapse(true);

        CommonUtils.showRingtonePicker(this, viewModel.getDefaultRingtone(), viewModel.ringtonePickerListener);
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
    public void createWithShakeBell() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        binding.repeatManagerIcon.setImageResource(R.drawable.selected_bell);
        binding.repeatManagerIcon.startAnimation(shake);
    }

    @Override
    public void shakeBell() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        binding.repeatManagerIcon.startAnimation(shake);
    }

    @Override
    public void clearBell() {
        binding.repeatManagerIcon.setImageResource(R.drawable.unselected_bell);
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
    }
}

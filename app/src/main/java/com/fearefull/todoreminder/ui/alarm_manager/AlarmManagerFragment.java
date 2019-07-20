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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.databinding.FragmentAlarmManagerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.OnceRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerDialogFragment;
import com.fearefull.todoreminder.ui.alarm_manager.simple.SimpleFragment;
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
        implements AlarmManagerNavigator, HasSupportFragmentInjector, RepeatAdapter.RepeatAdapterListener,
        OnceRepeatFragment.OnceRepeatCallBack, RepeatManagerDialogFragment.RepeatManagerCallBack {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    RepeatAdapter repeatAdapter;
    @Inject
    @Named("AlarmManager")
    LinearLayoutManager layoutManager;
    public static final String TAG = AlarmManagerFragment.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    @Named("AlarmManager")
    BaseViewPagerAdapter pagerAdapter;
    private AlarmManagerViewModel viewModel;
    private FragmentAlarmManagerBinding binding;
    private AlarmManagerCallBack callBack;

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
        setUp();
    }

    private void setUp() {
        binding.viewPager.setEnableSwipe(false);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.repeatRecyclerView.setLayoutManager(layoutManager);
        binding.repeatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.repeatRecyclerView.setAdapter(repeatAdapter);

        OnceRepeatFragment onceRepeatFragment = OnceRepeatFragment.newInstance(viewModel.getAlarm());
        onceRepeatFragment.setCallBack(this);

        SimpleFragment simpleFragment = SimpleFragment.newInstance(viewModel.getAlarm());
        SimpleFragment simpleFragment2 = SimpleFragment.newInstance(viewModel.getAlarm());
        SimpleFragment simpleFragment3 = SimpleFragment.newInstance(viewModel.getAlarm());
        SimpleFragment simpleFragment4 = SimpleFragment.newInstance(viewModel.getAlarm());
        SimpleFragment simpleFragment5 = SimpleFragment.newInstance(viewModel.getAlarm());
        SimpleFragment simpleFragment6 = SimpleFragment.newInstance(viewModel.getAlarm());

        pagerAdapter.addFragment(onceRepeatFragment, "once");
        pagerAdapter.addFragment(simpleFragment, "simple");
        pagerAdapter.addFragment(simpleFragment2, "simple2");
        pagerAdapter.addFragment(simpleFragment3, "simple3");
        pagerAdapter.addFragment(simpleFragment4, "simple4");
        pagerAdapter.addFragment(simpleFragment5, "simple5");
        pagerAdapter.addFragment(simpleFragment6, "simple6");
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
        viewModel.updateRepeatString(repeatItem.getRepeat());
        viewModel.getCurrentTabPager().setValue(repeatItem.getRepeat().getValue());
    }

    @Override
    public void closeAllExpansions() {
        if (binding.noteExpansionLayout.isExpanded())
            binding.noteExpansionLayout.collapse(true);
        else if (binding.repeatExpansionLayout.isExpanded())
            binding.repeatExpansionLayout.collapse(true);

        CommonUtils.showRingtonePicker(this, viewModel.getDefaultRingtone(), viewModel.ringtonePickerListener);
    }

    public void setCallBack(AlarmManagerCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onAlarmChangedByOnceRepeat(Alarm alarm) {
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

    public interface AlarmManagerCallBack {
        boolean onReloadAlarms();
    }
}

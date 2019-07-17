package com.fearefull.todoreminder.ui.alarm_manager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.RepeatItem;
import com.fearefull.todoreminder.databinding.FragmentAlarmManagerBinding;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.OnceRepeatFragment;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerDialogFragment;
import com.fearefull.todoreminder.ui.alarm_manager.simple.SimpleFragment;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.utils.CommonUtils;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class AlarmManagerFragment extends BaseFragment<FragmentAlarmManagerBinding, AlarmManagerViewModel>
        implements AlarmManagerNavigator, HasSupportFragmentInjector, RepeatAdapter.RepeatAdapterListener,
        RepeatCallBack, RepeatManagerDialogFragment.RepeatManagerCallBack {

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
    private String CHILD_TAG;
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
        viewModel.initAlarm();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        repeatAdapter.setListener(this);
        setUp();
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.repeatRecyclerView.setLayoutManager(layoutManager);
        binding.repeatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.repeatRecyclerView.setAdapter(repeatAdapter);
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
    public void openOnceRepeatFragment() {
        OnceRepeatFragment onceRepeatFragment = OnceRepeatFragment.newInstance(viewModel.getAlarm());
        onceRepeatFragment.setCallBack(this);
        CHILD_TAG = OnceRepeatFragment.TAG;
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.repeatSubRootView, onceRepeatFragment, CHILD_TAG)
                .commit();
        viewModel.setIsLoading(false);
    }

    @Override
    public void openDatePickerFragment() {

    }

    @Override
    public void openRepeatPickerFragment() {

    }

    @Override
    public void openCustomRepeatPickerFragment() {

    }

    @Override
    public void openEveryCustomRepeatPickerFragment() {

    }

    @Override
    public void openOnCustomRepeatPickerFragment() {

    }

    @Override
    public void onUpdateAlarm(Alarm alarm, String fragmentTag) {

    }

    @Override
    public void onRepeatItemClick(RepeatItem repeatItem) {
        viewModel.updateRepeatString(repeatItem.getRepeat());
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(CHILD_TAG);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            /*if (repeatItem.getRepeat() == Repeat.ONCE)
                openOnceRepeatFragment();*/
        }
        if (repeatItem.getRepeat() == Repeat.ONCE)
            openOnceRepeatFragment();
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

    public interface AlarmManagerCallBack {
        boolean onReloadAlarms();
    }
}

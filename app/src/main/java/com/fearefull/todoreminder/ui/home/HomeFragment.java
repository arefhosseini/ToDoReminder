package com.fearefull.todoreminder.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.FragmentHomeBinding;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.main.MainCaller;
import com.fearefull.todoreminder.utils.CommonUtils;

import javax.inject.Inject;
import javax.inject.Named;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel>
        implements HomeNavigator, AlarmAdapter.AlarmAdapterListener, MainCaller {

    public static final String TAG = HomeFragment.class.getSimpleName();
    @Inject
    AlarmAdapter alarmAdapter;
    @Inject
    @Named("Home")
    LinearLayoutManager layoutManager;
    @Inject
    ViewModelProviderFactory factory;
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private HomeCallBack callBack;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        return viewModel;
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
        alarmAdapter.setListener(this);
        alarmAdapter.setSettings(viewModel.getSettings());
        setUp();
    }

    public void setCallBack(HomeCallBack callBack) {
        this.callBack = callBack;
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.alarmRecyclerView.setLayoutManager(layoutManager);
        binding.alarmRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.alarmRecyclerView.setAdapter(alarmAdapter);

        // hide fab on scroll recycler view
        binding.scrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                binding.fab.hide();
            } else {
                binding.fab.show();
            }
        });
    }

    @Override
    public void onAlarmClick(Alarm alarm) {
        callBack.onOpenAlarmManager(alarm);
    }

    @Override
    public void onAlarmLongClick(Alarm alarm) {
        viewModel.setDeletingAlarm(alarm);
        CommonUtils.show2ButtonDialogNoTitle(getBaseActivity(), R.string.alarm_delete_message,
                viewModel.getDeleteAlarmOnClickListener()).show();
    }

    @Override
    public void onAlarmSwitchClick(Alarm alarm) {
        viewModel.updateAlarm(alarm);
    }

    public boolean reloadAlarmData() {
        alarmAdapter.clearItems();
        viewModel.reloadAlarmData();
        return true;
    }

    @Override
    public void settingsChanged() {
        alarmAdapter.settingsUpdated();
    }

    @Override
    public void showAlarmManagerFragment(Alarm alarm) {
        callBack.onOpenAlarmManager(alarm);
    }

    public interface HomeCallBack {
        void onOpenAlarmManager(Alarm alarm);
    }
}
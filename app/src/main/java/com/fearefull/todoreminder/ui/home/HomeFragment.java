package com.fearefull.todoreminder.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel>
        implements HomeNavigator, AlarmAdapter.AlarmAdapterListener, MainCaller {

    public static final String TAG = HomeFragment.class.getSimpleName();
    @Inject
    AlarmAdapter alarmAdapter;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    ViewModelProviderFactory factory;
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

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
        setUp();
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.alarmRecyclerView.setLayoutManager(layoutManager);
        binding.alarmRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.alarmRecyclerView.setAdapter(alarmAdapter);
    }

    @Override
    public void onAlarmClick(Alarm alarm) {

    }

    public boolean reloadAlarmData() {
        alarmAdapter.clearItems();
        viewModel.reloadAlarmData();
        return true;
    }
}
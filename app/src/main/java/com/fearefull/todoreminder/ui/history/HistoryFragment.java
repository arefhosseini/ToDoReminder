package com.fearefull.todoreminder.ui.history;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.databinding.FragmentHistoryBinding;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding, HistoryViewModel>
        implements HistoryNavigator, HistoryAdapter.HistoryAdapterListener{

    public static final String TAG = HistoryFragment.class.getSimpleName();
    @Inject
    HistoryAdapter historyAdapter;
    @Inject
    @Named("History")
    LinearLayoutManager layoutManager;
    @Inject
    ViewModelProviderFactory factory;

    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;


    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public HistoryViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HistoryViewModel.class);
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
        historyAdapter.setListener(this);
        setUp();
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.historyRecyclerView.setLayoutManager(layoutManager);
        binding.historyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.historyRecyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onHistoryLongClick(History history) {
        Toast.makeText(getContext(), "LONG", Toast.LENGTH_LONG).show();
    }
}

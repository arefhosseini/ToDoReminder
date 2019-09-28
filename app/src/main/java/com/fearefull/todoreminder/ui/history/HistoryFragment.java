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
import com.fearefull.todoreminder.ui.main.MainCaller;
import com.fearefull.todoreminder.utils.CommonUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

import static com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerFragment.TAG;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding, HistoryViewModel>
        implements HistoryNavigator, HistoryAdapter.HistoryAdapterListener, MainCaller {

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

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.w(task.getException(), TAG, "getInstanceId failed");
                        return;
                    }

                    // Get new Instance ID token
                    String token = Objects.requireNonNull(task.getResult()).getToken();

                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Timber.d(TAG, msg);
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        historyAdapter.setListener(this);
        historyAdapter.setSettings(viewModel.getSettings());
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
        viewModel.setDeletingHistory(history);
         CommonUtils.show2ButtonDialogNoTitle(getBaseActivity(), R.string.history_delete_message,
                 viewModel.getDeleteHistoryOnClickListener()).show();
    }

    @Override
    public void onHistoryIsDoneClick(History history) {
        viewModel.setIsDoneHistory(history);
        CommonUtils.show2ButtonDialogNoTitle(getBaseActivity(), R.string.history_is_done_message,
                viewModel.getIsDoneHistoryOnClickListener()).show();
    }

    @Override
    public void setDoneHistory(History history) {
        historyAdapter.setDoneHistory(history);
    }

    @Override
    public boolean reloadAlarmData() {
        return false;
    }

    @Override
    public void settingsChanged() {
        historyAdapter.settingsUpdated();
    }
}

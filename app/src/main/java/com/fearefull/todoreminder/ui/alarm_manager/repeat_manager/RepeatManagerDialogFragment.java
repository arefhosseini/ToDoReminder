package com.fearefull.todoreminder.ui.alarm_manager.repeat_manager;

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
import com.fearefull.todoreminder.data.model.other.RepeatManagerItem;
import com.fearefull.todoreminder.databinding.DialogFargmentRepeatManagerBinding;
import com.fearefull.todoreminder.ui.base.BaseDialogFragment;

import javax.inject.Inject;
import javax.inject.Named;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class RepeatManagerDialogFragment extends BaseDialogFragment<DialogFargmentRepeatManagerBinding, RepeatManagerViewModel>
        implements RepeatManagerNavigator, RepeatManagerAdapter.RepeatManagerAdapterListener {

    public static final String TAG = RepeatManagerDialogFragment.class.getSimpleName();

    @Inject
    RepeatManagerAdapter adapter;
    @Inject
    @Named("RepeatManager")
    LinearLayoutManager layoutManager;
    @Inject
    ViewModelProviderFactory factory;
    private RepeatManagerViewModel viewModel;
    private DialogFargmentRepeatManagerBinding binding;
    private RepeatManagerCallBack callBack;

    public static RepeatManagerDialogFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        RepeatManagerDialogFragment fragment = new RepeatManagerDialogFragment();
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
        return R.layout.dialog_fargment_repeat_manager;
    }

    @Override
    public RepeatManagerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RepeatManagerViewModel.class);
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
        adapter.setListener(this);
        setUp();
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRemoveClick(RepeatManagerItem repeatManagerItem) {
        viewModel.removeRepeatManagerItem(repeatManagerItem);
    }

    @Override
    public void finish() {
        dismiss();
    }

    @Override
    public void onDestroy() {
        callBack.onAlarmChangedByRepeatManager(viewModel.getAlarm());
        super.onDestroy();
    }

    public void setCallBack(RepeatManagerCallBack callBack) {
        this.callBack = callBack;
    }

    public interface RepeatManagerCallBack {
        void onAlarmChangedByRepeatManager(Alarm alarm);
    }
}

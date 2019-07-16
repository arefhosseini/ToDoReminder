package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.FragmentOnceRepeatBinding;
import com.fearefull.todoreminder.ui.alarm_manager.RepeatCallBack;
import com.fearefull.todoreminder.ui.base.BaseFragment;

import javax.inject.Inject;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class OnceRepeatFragment extends BaseFragment<FragmentOnceRepeatBinding, OnceRepeatViewModel>
        implements OnceRepeatNavigator {

    public static final String TAG = OnceRepeatFragment.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    private OnceRepeatViewModel viewModel;
    private FragmentOnceRepeatBinding binding;
    private RepeatCallBack callBack;

    public static OnceRepeatFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        OnceRepeatFragment fragment = new OnceRepeatFragment();
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
        return R.layout.fragment_once_repeat;
    }

    @Override
    public OnceRepeatViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(OnceRepeatViewModel.class);
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

    private void setUp() {

    }

    public void setCallBack(RepeatCallBack callBack) {
        this.callBack = callBack;
    }
}

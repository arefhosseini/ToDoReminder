package com.fearefull.todoreminder.ui.alarm_manager.simple;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.FragmentSimpleBinding;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat.OnceRepeatFragment;
import com.fearefull.todoreminder.ui.base.BaseFragment;

import javax.inject.Inject;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class SimpleFragment extends BaseFragment<FragmentSimpleBinding, SimpleViewModel>
        implements SimpleNavigator {

    public static final String TAG = SimpleFragment.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    private SimpleViewModel viewModel;
    private FragmentSimpleBinding binding;
    private BaseRepeatFragment.RepeatCallBack callBack;

    public static SimpleFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        SimpleFragment fragment = new SimpleFragment();
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
        return R.layout.fragment_simple;
    }

    @Override
    public SimpleViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SimpleViewModel.class);
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

    public void setCallBack(BaseRepeatFragment.RepeatCallBack callBack) {
        this.callBack = callBack;
    }
}

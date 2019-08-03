package com.fearefull.todoreminder.ui.settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.databinding.FragmentSettingsBinding;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

public class SettingsFragment extends BaseFragment<FragmentSettingsBinding, SettingsViewModel>
        implements SettingsNavigator {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;
    private SettingsFragmentCallBack callBack;

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SettingsViewModel.class);
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
        setUp();
    }

    private void setUp() {

    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    public void setCallBack(SettingsFragmentCallBack callBack) {
        this.callBack = callBack;
    }

    public interface SettingsFragmentCallBack {
        void onSettingsChanged();
    }
}

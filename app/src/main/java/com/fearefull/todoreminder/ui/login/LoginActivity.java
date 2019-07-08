package com.fearefull.todoreminder.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.databinding.ActivityLoginBinding;
import com.fearefull.todoreminder.ui.main.MainActivity;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.ui.base.BaseActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);
        return viewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void login() {
        String username = binding.editTextUsername.getText().toString();
        String password = binding.editTextPassword.getText().toString();
        if (viewModel.isUsernameAndPasswordValid(username, password)) {
            hideKeyboard();
            viewModel.login(username, password);
        } else {
            Toast.makeText(this, getString(R.string.invalid_username_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        binding.editTextPassword.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS); // No suggestions
        binding.editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}
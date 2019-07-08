package com.fearefull.todoreminder.ui.main;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.BuildConfig;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.databinding.ActivityMainBinding;
import com.fearefull.todoreminder.databinding.NavigationHeaderMainBinding;
import com.fearefull.todoreminder.ui.base.BaseActivity;
import com.fearefull.todoreminder.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel.class);
        return viewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawer != null) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    @Override
    public void onFragmentDetached(String tag) {
        unlockDrawer();
    }

    private void lockDrawer() {
        if (drawer != null) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void unlockDrawer() {
        if (drawer != null) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void setUp() {
        drawer = binding.drawerView;
        Toolbar toolbar = binding.toolbar;
        navigationView = binding.navigationView;

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        setupNavigationMenu();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        viewModel.updateAppVersion(version);
        viewModel.onNavigationMenuCreated();
    }

    private void setupNavigationMenu() {
        NavigationHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.navigation_header_main, binding.navigationView, false);
        binding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());
        navHeaderMainBinding.setViewModel(viewModel);

        navigationView.setNavigationItemSelectedListener(
                item -> {
                    drawer.closeDrawer(Gravity.RIGHT);
                    navigationView.setCheckedItem(item.getItemId());
                    switch (item.getItemId()) {
                        case R.id.navigationItemHome:
                            return true;
                        case R.id.navigationItemHistory:
                            //
                            return true;
                        case R.id.navigationItemSettings:
                            //
                            return true;
                        case R.id.navigationItemAbout:
                            //
                            return true;
                        case R.id.navigationItemLogout:
                            viewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
    }
}
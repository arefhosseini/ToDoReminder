package com.fearefull.todoreminder.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.ActivityMainBinding;
import com.fearefull.todoreminder.databinding.NavigationHeaderMainBinding;
import com.fearefull.todoreminder.ui.about.AboutFragment;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerFragment;
import com.fearefull.todoreminder.ui.base.BaseActivity;
import com.fearefull.todoreminder.ui.home.HomeCallBack;
import com.fearefull.todoreminder.ui.home.HomeFragment;
import com.fearefull.todoreminder.ui.login.LoginActivity;
import com.fearefull.todoreminder.utils.CommonUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator, HasSupportFragmentInjector, AlarmManagerFragment.AlarmManagerCallBack {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProviderFactory factory;
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private HomeCallBack homeCallBack;

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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
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
    public void openAlarmManagerActivity() {
        //startActivity(AlarmManagerActivity.newIntent(this));
        lockDrawer();
        AlarmManagerFragment fragment = AlarmManagerFragment.newInstance(new Alarm("Alarm"));
        fragment.setCallBack(this);
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.mainRootView, fragment, AlarmManagerFragment.TAG)
                .commit();
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(AboutFragment.TAG);
        if (fragment == null) {
            if (drawer.isDrawerOpen(Gravity.RIGHT))
                lockDrawer();
            else
                super.onBackPressed();
        }
        else
            onFragmentDetached(AboutFragment.TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
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

        viewModel.updateAppVersion(CommonUtils.getAppVersionString(getApplicationContext()));
        viewModel.onNavigationMenuCreated();

        showHomeFragment();
    }

    @SuppressLint("RtlHardcoded")
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
                            showAboutFragment();
                            return true;
                        case R.id.navigationItemLogout:
                            viewModel.removeAlarms();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void showAboutFragment() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.mainRootView, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }

    private void showHomeFragment() {
        lockDrawer();
        HomeFragment homeFragment = HomeFragment.newInstance();
        homeCallBack = homeFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.mainSubRootView, homeFragment, HomeFragment.TAG)
                .commit();
    }

    @Override
    public boolean onReloadAlarms() {
        return homeCallBack.reloadAlarmData();
    }
}
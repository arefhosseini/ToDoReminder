package com.fearefull.todoreminder.di.builder;

import com.fearefull.todoreminder.ui.about.AboutFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerActivity;
import com.fearefull.todoreminder.ui.home.HomeFragmentProvider;
import com.fearefull.todoreminder.ui.login.LoginActivity;
import com.fearefull.todoreminder.ui.main.MainActivity;
import com.fearefull.todoreminder.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(
            modules = {
                    HomeFragmentProvider.class,
                    AboutFragmentProvider.class,
            })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract AlarmManagerActivity bindAlarmManagerActivity();
}

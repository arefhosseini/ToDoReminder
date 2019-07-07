package com.fearefull.todoreminder.di.builder;

import com.fearefull.todoreminder.ui.main.MainActivity;
import com.fearefull.todoreminder.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();
}

package com.fearefull.todoreminder.ui.home;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment provideHomeFragmentFactory();
}
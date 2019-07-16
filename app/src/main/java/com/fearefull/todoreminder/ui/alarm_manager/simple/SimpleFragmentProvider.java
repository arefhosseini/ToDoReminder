package com.fearefull.todoreminder.ui.alarm_manager.simple;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SimpleFragmentProvider {

    @ContributesAndroidInjector
    abstract SimpleFragment provideSimpleFragmentFactory();
}

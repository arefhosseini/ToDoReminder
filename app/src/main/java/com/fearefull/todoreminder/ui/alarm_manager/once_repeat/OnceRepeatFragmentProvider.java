package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OnceRepeatFragmentProvider {

    @ContributesAndroidInjector
    abstract OnceRepeatFragment provideOnceRepeatFragmentFactory();
}
